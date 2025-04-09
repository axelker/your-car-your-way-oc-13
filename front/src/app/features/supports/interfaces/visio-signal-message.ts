import { SignalingMessageTypeEnum } from "../enums/signaling-message-type.enum";

export interface VisioSignalMessage {
    type:SignalingMessageTypeEnum;
    payload: string;
    senderId:number;
    receiverId:number;
}