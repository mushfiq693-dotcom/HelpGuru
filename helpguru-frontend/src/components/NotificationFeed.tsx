import React, { useState } from 'react';
import { NotificationItem } from '../types';
import { Bell, Check, Radio, Volume2, ShieldAlert } from 'lucide-react';

interface NotificationFeedProps {
  notifications: NotificationItem[];
  onMarkRead: (id: number) => void;
}

export const NotificationFeed: React.FC<NotificationFeedProps> = ({
  notifications,
  onMarkRead
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const unreadCount = notifications.filter(n => !n.isRead).length;

  return (
    <div className="relative">
      {/* Floating Bell Button */}
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="relative flex items-center justify-center w-10 h-10 rounded-xl bg-slate-900 border border-slate-700/80 text-cyan-400 hover:text-white hover:bg-slate-800 transition-all cursor-pointer shadow-lg"
        title="Real-Time WebSocket Notifications"
      >
        <Bell className="w-5 h-5" />
        {unreadCount > 0 && (
          <span className="absolute -top-1.5 -right-1.5 flex items-center justify-center min-w-[20px] h-5 px-1 rounded-full bg-red-500 text-white font-extrabold text-[10px] shadow-lg animate-bounce">
            {unreadCount}
          </span>
        )}
      </button>

      {/* Dropdown Panel */}
      {isOpen && (
        <div className="absolute right-0 bottom-12 z-[500] w-80 sm:w-96 glass-panel rounded-2xl border border-slate-700/80 shadow-2xl overflow-hidden bg-slate-900/95 animate-fadeIn">
          <div className="p-3.5 border-b border-slate-800 flex items-center justify-between bg-slate-950/80">
            <div className="flex items-center gap-2">
              <Radio className="w-4 h-4 text-emerald-400 animate-pulse" />
              <h3 className="text-xs font-bold text-white font-mono uppercase">STOMP WebSocket Feed</h3>
            </div>
            <span className="text-[10px] text-cyan-400 font-mono font-bold px-2 py-0.5 rounded bg-cyan-950/60 border border-cyan-800">
              {unreadCount} Unread
            </span>
          </div>

          <div className="max-h-80 overflow-y-auto p-2 space-y-2">
            {notifications.length === 0 ? (
              <div className="text-center py-6 text-xs text-slate-500">
                No active notifications in feed.
              </div>
            ) : (
              notifications.map(n => (
                <div
                  key={n.id}
                  className={`p-3 rounded-xl border transition-all text-xs space-y-1 ${
                    !n.isRead
                      ? 'bg-cyan-950/20 border-cyan-500/50 shadow-[0_0_12px_rgba(6,182,212,0.1)]'
                      : 'bg-slate-950/40 border-slate-800 opacity-75'
                  }`}
                >
                  <div className="flex items-center justify-between gap-2">
                    <span className="font-bold text-white flex items-center gap-1.5">
                      <ShieldAlert className="w-3.5 h-3.5 text-red-400" />
                      {n.title}
                    </span>
                    {!n.isRead && (
                      <button
                        onClick={() => onMarkRead(n.id)}
                        className="p-1 rounded hover:bg-slate-800 text-cyan-400 transition-colors cursor-pointer"
                        title="Mark as Read"
                      >
                        <Check className="w-3.5 h-3.5" />
                      </button>
                    )}
                  </div>
                  <p className="text-[11px] text-slate-300 leading-relaxed">{n.message}</p>
                  <div className="text-[10px] text-slate-500 font-mono flex justify-between pt-1 border-t border-slate-800/40">
                    <span>Channel: {n.channel}</span>
                    <span>{new Date(n.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      )}
    </div>
  );
};
