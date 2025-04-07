import { RxStompConfig } from '@stomp/rx-stomp';

export const rxStompConfig: RxStompConfig = {
  brokerURL: 'ws://localhost:8080/api/ws',
  reconnectDelay: 5000,
  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
};
