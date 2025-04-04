import { Routes } from '@angular/router';
import { HomePageComponent } from './pages/home/home-page.component';
import { LoginPageComponent } from './pages/login/login-page.component';
import { RegisterPageComponent } from './pages/register/register-page.component';

export const routes: Routes = [
    { 
        path: '', 
        component:HomePageComponent,
    },
    { 
        path: 'login', 
        component:LoginPageComponent,
    },
    { 
        path: 'register', 
        component:RegisterPageComponent,
    }
];
