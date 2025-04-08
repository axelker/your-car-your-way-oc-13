import { Message } from "./message";

export interface Conversation {
    id:number;
    name:string;
    messages: Message[];
}