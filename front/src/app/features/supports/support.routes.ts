import { Routes } from "@angular/router";
import { canActivateClient } from "../../core/guards/client.guards";
import { ContactPageComponent } from "./pages/contact-page/contact-page.component";
import { VisioConferencingPageComponent } from "./pages/visio-conferencing-page/visio-conferencing-page.component";
import { ChatPageComponent } from "./pages/chat-page/chat-page.component";

export const routes: Routes = [

    {
        path:'chat',
        component:ChatPageComponent
    },
    {
        path: 'visioconferencing',
        component:VisioConferencingPageComponent
    },
    {
        canActivate:[canActivateClient],
        path:'contact',
        component:ContactPageComponent
    }
];