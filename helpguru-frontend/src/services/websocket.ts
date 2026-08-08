import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

type NotificationCallback = (notification: any) => void;

class WebSocketService {
  private client: Client | null = null;
  private isConnected = false;
  private listeners: NotificationCallback[] = [];

  connect(userId = 1) {
    if (this.client && this.isConnected) return;

    try {
      this.client = new Client({
        webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: () => {
          this.isConnected = true;
          console.log('[WebSocket] Connected to HelpGuru STOMP Broker');

          // Subscribe to recipient's private notification channel
          this.client?.subscribe(`/topic/notifications/${userId}`, (message) => {
            if (message.body) {
              const notification = JSON.parse(message.body);
              this.notifyListeners(notification);
            }
          });

          // Subscribe to general incident update channel
          this.client?.subscribe('/topic/incidents', (message) => {
            if (message.body) {
              const event = JSON.parse(message.body);
              this.notifyListeners(event);
            }
          });
        },
        onDisconnect: () => {
          this.isConnected = false;
          console.log('[WebSocket] Disconnected');
        },
        onStompError: (frame) => {
          console.error('[WebSocket] Error:', frame.headers['message']);
        }
      });

      this.client.activate();
    } catch (e) {
      console.warn('[WebSocket] STOMP Connection failed, using API polling fallback', e);
    }
  }

  subscribe(callback: NotificationCallback) {
    this.listeners.push(callback);
    return () => {
      this.listeners = this.listeners.filter(cb => cb !== callback);
    };
  }

  private notifyListeners(data: any) {
    this.listeners.forEach(cb => cb(data));
  }

  getConnected() {
    return this.isConnected;
  }
}

export const wsService = new WebSocketService();
