import { Injectable } from "@angular/core";
import { IMessage, RxStomp } from "@stomp/rx-stomp";
import { SessionService } from "../../../core/services/session.service";
import { rxStompConfig } from "../config/rx-stomp.config";
import { VisioSignalMessage } from "../interfaces/visio-signal-message";
import { SignalingMessageTypeEnum } from "../enums/signaling-message-type.enum";
import { Subject } from "rxjs";

@Injectable({ providedIn: 'root' })
export class WebrtcService {
  private peer!: RTCPeerConnection; // todo: for support multiple peers use an map with the user id for the key.
  private localStream!: MediaStream;
  private remoteStream!: MediaStream;
  private rxStomp = new RxStomp();
  private pendingIceCandidates: RTCIceCandidate[] = [];

  public incomingCall$: Subject<VisioSignalMessage> = new Subject();
  public answeringCall$: Subject<VisioSignalMessage> = new Subject();


  constructor(private sessionService: SessionService) {
    this.rxStomp.configure(rxStompConfig);
  }

  init(){
    this.rxStomp.activate();
  }

  cleanUp() : void {
    if (this.rxStomp.connected()) {
      this.rxStomp.deactivate();
    }
    // Stop all local tracks (caméra, micro)
    if (this.localStream) {
      this.localStream.getTracks().forEach(track => track.stop());
    }
  
    // Stop all remote tracks
    if (this.remoteStream) {
      this.remoteStream.getTracks().forEach(track => track.stop());
    }
  
    // Close WebRTC connexion
    if (this.peer) {
      this.peer.close();
      this.peer = undefined as any;
    }
  }

  async initPeer(receiverId: number, onRemoteStream: (stream: MediaStream) => void): Promise<void> {
    this.peer = new RTCPeerConnection({
      iceServers: [
        { urls: 'stun:stun.l.google.com:19302' }
      ]
    });

    try {
      //Set local stream with the user media and add track to the peer connexion.
      this.localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
      this.localStream.getTracks().forEach(track => this.peer.addTrack(track, this.localStream));

      // Listen track added by remote stream
      this.peer.ontrack = (event) => {
        this.remoteStream = event.streams[0];
        onRemoteStream(this.remoteStream);
      };

      /**
       * Listen generated local ice candidate 
       * for send to the remote peer (receiver) 
       * by the signaling server.
       * */
      this.peer.onicecandidate = (event) => {
        if (event.candidate) {
          this.sendSignal({
            type: SignalingMessageTypeEnum.ICE,
            payload: JSON.stringify(event.candidate),
            receiverId: receiverId,
            senderId: this.sessionService.getUser()!.id
          });
        }
      };
     
      return Promise.resolve();
    } catch (error) {
      return Promise.reject("Erreur lors de l'accès à la caméra ou au micro");
    }
  }

  async createOffer(receiverId: number): Promise<void> {
    //Create an  SDP offer and set local description
    const offer: RTCSessionDescriptionInit = await this.peer.createOffer();
    await this.peer.setLocalDescription(offer);

    // Send the offer to the signaling server for the receiver.
    this.sendSignal({
      type: SignalingMessageTypeEnum.OFFER,
      payload: JSON.stringify(offer),
      receiverId: receiverId,
      senderId: this.sessionService.getUser()!.id
    });
  }

  async createAnswer(signal: VisioSignalMessage): Promise<void> {
    // Remote origin is the payload
    await this.peer.setRemoteDescription(new RTCSessionDescription(JSON.parse(signal.payload)));
    const answer = await this.peer.createAnswer();
    await this.peer.setLocalDescription(answer);
    this.sendSignal({
      type: SignalingMessageTypeEnum.ANSWER,
      payload: JSON.stringify(answer),
      receiverId: signal.senderId,
      senderId: signal.receiverId
    });
  }
  

  async handleSignalMessage(msg: IMessage): Promise<void> {
    const signal: VisioSignalMessage = JSON.parse(msg.body);
    switch (signal.type) {
      case SignalingMessageTypeEnum.OFFER:
        await this.setOffer(signal)
        break;

      case SignalingMessageTypeEnum.ANSWER:
        await this.setAnswer(signal);
        break;

      case SignalingMessageTypeEnum.ICE:
        let candidate: RTCIceCandidate = new RTCIceCandidate(JSON.parse(signal.payload));
        if (!this.peer) {
          this.pendingIceCandidates.push(candidate);
          return;
        }
        await this.peer.addIceCandidate(candidate);
        break;
    }
  }

  listenToSignals(userId: number): void {
    this.rxStomp.watch(`/topic/visio/${userId}`).subscribe((m) => this.handleSignalMessage(m));
  }

  async setOffer(signal: VisioSignalMessage) {
    // Add pending ice candidate and clean the array
    for (const candidate of this.pendingIceCandidates) {
      await this.peer.addIceCandidate(candidate);
    }
    this.pendingIceCandidates = [];
    this.incomingCall$.next(signal);
  }

  async setAnswer(signal: VisioSignalMessage) {
    await this.peer.setRemoteDescription(new RTCSessionDescription(JSON.parse(signal.payload)));
    this.answeringCall$.next(signal);
  }



  private sendSignal(signal: VisioSignalMessage): void {
    this.rxStomp.publish({ destination: '/app/visio.send', body: JSON.stringify(signal) });
  }

  getLocalStream(): MediaStream {
    return this.localStream;
  }
}
