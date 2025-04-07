import { roles } from "../enums/roles.enum";

export interface UserInfo {
    id:number;
    email:string;
    role: roles;
}