import { Routes } from '@angular/router';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';
import { canActivateAuth } from './core/guards/auth.guard';
import { canActivateUnAuth } from './core/guards/unauth.guard';
import { AuthLayoutComponent } from './core/layout/auth-layout/auth-layout.component';

export const routes: Routes = [
    {
        path: '',
        canActivate: [canActivateAuth],
        component: AuthLayoutComponent,
        children: [
            {
                path: '',
                redirectTo: 'supports',
                pathMatch: 'full',
            },
            {
                path: "support",
                loadChildren: () =>
                    import('./features/supports/support.routes').then((v) => v.routes),
            },
        ],
    },
    {
        path: 'auth',
        loadChildren: () =>
            import('./features/auth/auth.routes').then((v) => v.routes),
        canActivate: [canActivateUnAuth],
    },
    { path: '404', component: NotFoundComponent },
    { path: '**', redirectTo: '404' },

];
