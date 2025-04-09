import { Injectable } from "@angular/core";
import { RxStomp } from "@stomp/rx-stomp";
import { AuthService } from "../../../core/services/auth.service";
import { SessionService } from "../../../core/services/session.service";
import { rxStompConfig } from "../config/rx-stomp.config";
import { VisioSignalMessage } from "../interfaces/visio-signal-message";
import { SignalingMessageTypeEnum } from "../enums/signaling-message-type.enum";

@Injectable({ providedIn: 'root' })
export class WebrtcService {
  private peer!: RTCPeerConnection;
  private localStream!: MediaStream;
  private remoteStream!: MediaStream;

  private rxStomp = new RxStomp();

  constructor(private authService: AuthService, private sessionService: SessionService) {
    this.rxStomp.configure(rxStompConfig);
    this.rxStomp.activate();
  }

  async initPeer(receiverId: number, onRemoteStream: (stream: MediaStream) => void): Promise<void> {
    this.peer = new RTCPeerConnection();

    try {
      this.localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
      this.localStream.getTracks().forEach(track => this.peer.addTrack(track, this.localStream));

      this.peer.ontrack = (event) => {
        this.remoteStream = event.streams[0];
        onRemoteStream(this.remoteStream);
      };

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
    } catch (error) {
      console.error("Error accessing local media:", error);
      throw new Error("Erreur lors de l'accès à la caméra ou au micro");
    }
  }

  async createOffer(receiverId: number): Promise<void> {
    const offer = await this.peer.createOffer();
    await this.peer.setLocalDescription(offer);
    this.sendSignal({
      type: SignalingMessageTypeEnum.OFFER,
      payload: JSON.stringify(offer),
      receiverId: receiverId,
      senderId: this.sessionService.getUser()!.id
    });
  }

  async handleSignal(msg: any): Promise<void> {
    const signal: VisioSignalMessage = JSON.parse(msg.body);
    switch (signal.type) {
      case SignalingMessageTypeEnum.OFFER:
        await this.peer.setRemoteDescription(new RTCSessionDescription(JSON.parse(signal.payload)));
        const answer = await this.peer.createAnswer();
        await this.peer.setLocalDescription(answer);
        this.sendSignal({
          type: SignalingMessageTypeEnum.ANSWER,
          payload: JSON.stringify(answer),
          receiverId: signal.senderId,
          senderId: this.sessionService.getUser()!.id
        });
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
    this.rxStomp.watch(`/topic/visio/${userId}`).subscribe(msg => this.handleSignal(msg));
  }

  private sendSignal(signal: VisioSignalMessage): void {
    this.rxStomp.publish({ destination: '/app/visio.send', body: JSON.stringify(signal) });
  }

  getLocalStream(): MediaStream {
    return this.localStream;
  }
}
