import { Component, ElementRef, ViewChild } from '@angular/core';
import { AuthService } from '../../../../core/services/auth.service';
import { WebrtcService } from '../../services/webrtc.service';
import { SessionService } from '../../../../core/services/session.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-visio',
  standalone: true,
  imports: [],
  templateUrl: './visio.component.html',
  styleUrl: './visio.component.scss'
})
export class VisioComponent {
  @Component({
    selector: 'app-visio',
    templateUrl: './visio.component.html',
  })
    @ViewChild('local') local!: ElementRef<HTMLVideoElement>;
    @ViewChild('remote') remote!: ElementRef<HTMLVideoElement>;

    constructor(private webrtc: WebrtcService, private auth: AuthService,private sessionService: SessionService,private toastr: ToastrService) {}
  
    ngOnInit(): void {
      //TODO: update session service with behavior sub
      const user = this.sessionService.getUser()!;
      this.webrtc.listenToSignals(user.id);
    }
  
    async startCall() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
  
        if (this.local?.nativeElement) {
          this.local.nativeElement.srcObject = stream;
        }
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
}
