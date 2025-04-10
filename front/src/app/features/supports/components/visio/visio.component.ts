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


  constructor(private webrtc: WebrtcService,
              private sessionService: SessionService, 
              private toastr: ToastrService) { }
  
    ngOnInit(): void {
      this.webrtc.init();
      const user = this.sessionService.getUser()!;
      this.webrtc.incomingOffer$.subscribe((signal) => {
        this.incomingCall = signal;
      });
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
    
      } catch (error: any) {  
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
    }
    // TODO : need to add function for base code (init peer, getlocalstream and error toastr) //
    async acceptCall() {
      if (!this.incomingCall) return;
      
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
    }

    exitCall() {
      this.webrtc.cleanUp();
    }
    
}
