import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideCore } from './core/core.providers';
import { provideToastr } from 'ngx-toastr';
import { heroArrowLeft,heroArrowDown,heroArrowUp,heroUser} from '@ng-icons/heroicons/outline';
import { provideIcons } from '@ng-icons/core';


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideRouter(routes), 
    provideCore(),
    provideIcons({ heroArrowLeft, heroArrowDown, heroArrowUp, heroUser }),
    provideToastr()
  ]
};
