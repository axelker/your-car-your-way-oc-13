import { roles } from "../enums/roles.enum";

export interface UserInfo {
    username:string;
    email:string;
    role: roles;
}