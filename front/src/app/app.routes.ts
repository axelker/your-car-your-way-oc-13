import { Routes } from '@angular/router';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';

export const routes: Routes = [

    {
        path: "support",
        loadChildren: () =>
            import('./features/supports/support.routes').then((v) => v.routes),
    },
    { path: '404', component: NotFoundComponent },
    { path: '**', redirectTo: '404' },
];
