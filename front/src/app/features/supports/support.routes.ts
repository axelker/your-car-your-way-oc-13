import { Routes } from "@angular/router";
import { canActivateClient } from "../../core/guards/client.guards";
import { ContactPageComponent } from "./pages/contact-page/contact-page.component";
import { VisioConferencingPageComponent } from "./pages/visio-conferencing-page/visio-conferencing-page.component";
import { ChatPageComponent } from "./pages/chat-page/chat-page.component";
import { HomeSupportPageComponent } from "./pages/home-support-page/home-support-page.component";

export const routes: Routes = [

    {
        path:'',
        component:HomeSupportPageComponent
    },
    {
        path:'chat',
        component:ChatPageComponent

    },
    {
        path: 'visio',
        component:VisioConferencingPageComponent
    },
    {
        canActivate:[canActivateClient],
        path:'contact',
        component:ContactPageComponent
    }
];