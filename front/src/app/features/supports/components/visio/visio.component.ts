import { Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { WebrtcService } from '../../services/webrtc.service';
import { SessionService } from '../../../../core/services/session.service';
import { ToastrService } from 'ngx-toastr';
import { VisioSignalMessage } from '../../interfaces/visio-signal-message';

@Component({
  selector: 'app-visio',
  standalone: true,
  imports: [],
  templateUrl: './visio.component.html',
  styleUrl: './visio.component.scss'
})
export class VisioComponent implements OnInit,OnDestroy {
  @Component({
    selector: 'app-visio',
    templateUrl: './visio.component.html',
  })
  @ViewChild('local') local!: ElementRef<HTMLVideoElement>;
  @ViewChild('remote') remote!: ElementRef<HTMLVideoElement>;
  @Input({required:true}) receiverId!: number;

  incomingCall: VisioSignalMessage | null = null;
  isInCall: boolean = false;


  constructor(private webrtc: WebrtcService,
              private sessionService: SessionService, 
              private toastr: ToastrService) { }
  
    ngOnInit(): void {
      this.webrtc.init();
      const user = this.sessionService.getUser()!;
      this.webrtc.incomingCall$.subscribe((signal) => {
        this.incomingCall = signal;
      });
      this.webrtc.answeringCall$.subscribe(() => {
        this.toastr.success("L'utilisateur a rejoints l'appel.");
      }
      );
      this.webrtc.listenToSignals(user.id);
      
    }

    ngOnDestroy(): void {
      this.webrtc.cleanUp();
    }
  
    async startCall() {
      try {
        await this.webrtc.initPeer(this.receiverId, (remoteStream: MediaStream) => {
          if (this.remote?.nativeElement) {
            this.remote.nativeElement.srcObject = remoteStream;
          }
        });
    
        const localStream = this.webrtc.getLocalStream();
        if (this.local?.nativeElement) {
          this.local.nativeElement.srcObject = localStream;
        }
    
        await this.webrtc.createOffer(this.receiverId);
        this.isInCall = true;
    
      } catch (error: any) {  
        this.handleCallError(error);
      }
    }
  
    async acceptCall() {
      if (!this.incomingCall) return;
      
      try {
        await this.webrtc.initPeer(this.incomingCall.senderId, (remoteStream) => {
          if (this.remote?.nativeElement) {
            this.remote.nativeElement.srcObject = remoteStream;
          }
        });
  
        const localStream = this.webrtc.getLocalStream();
        if (this.local?.nativeElement) {
          this.local.nativeElement.srcObject = localStream;
        }
  
        await this.webrtc.createAnswer(this.incomingCall);
        this.incomingCall = null;
        this.isInCall = true;
      } catch (error) {
        this.handleCallError(error);
      }
      
    }
    handleCallError(error: any) {
      if (error.name === 'NotAllowedError') {
        this.toastr.error("L'accès à la caméra ou au micro a été refusé.");
      } else if (error.name === 'NotFoundError') {
        this.toastr.error("Aucun périphérique vidéo ou audio détecté.");
      } else if (error.name === 'NotReadableError') {
        this.toastr.error("Le périphérique est déjà utilisé par une autre application.");
      } else if (error.name === 'OverconstrainedError') {
        this.toastr.error("La configuration demandée n'est pas supportée.");
      } else if (error.name === 'AbortError') {
        this.toastr.error("Erreur système lors de l'accès à la caméra.");
      } else {
        this.toastr.error("Erreur inconnue lors de l'initialisation de la visio.");
      }
    }

    exitCall() {
      this.isInCall = false;
      this.webrtc.cleanUp();
    }
    
    
}
