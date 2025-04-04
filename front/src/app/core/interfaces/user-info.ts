import { roles } from "../enums/roles.enum";

export interface UserInfo {
    email:string;
    role: roles;
}