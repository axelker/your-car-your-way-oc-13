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

  public incomingOffer$: Subject<VisioSignalMessage> = new Subject();


  constructor(private sessionService: SessionService) {
    this.rxStomp.configure(rxStompConfig);
    this.rxStomp.activate();
  }

  cleanUp() : void {
    if (this.rxStomp.connected()) {
      this.rxStomp.deactivate();
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
        this.incomingOffer$.next(signal);
        break;

      case SignalingMessageTypeEnum.ANSWER:
        await this.peer.setRemoteDescription(new RTCSessionDescription(JSON.parse(signal.payload)));
        break;

      case SignalingMessageTypeEnum.ICE:
        await this.peer.addIceCandidate(new RTCIceCandidate(JSON.parse(signal.payload)));
        break;
    }
  }

  listenToSignals(userId: number): void {
    this.rxStomp.watch(`/topic/visio/${userId}`).subscribe((msg: IMessage) => this.handleSignalMessage(msg));
  }

  close(): void {
    // Stop all local tracks (caméra, micro)
    if (this.localStream) {
      this.localStream.getTracks().forEach(track => track.stop());
      this.localStream = undefined as any;
    }
  
    // Stop all remote tracks
    if (this.remoteStream) {
      this.remoteStream.getTracks().forEach(track => track.stop());
      this.remoteStream = undefined as any;
    }
  
    // Stock WebRTC connexion
    if (this.peer) {
      this.peer.close();
      this.peer = undefined as any;
    }
  }

  private sendSignal(signal: VisioSignalMessage): void {
    this.rxStomp.publish({ destination: '/app/visio.send', body: JSON.stringify(signal) });
  }

  getLocalStream(): MediaStream {
    return this.localStream;
  }
}
