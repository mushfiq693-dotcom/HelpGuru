import React, { useState, useEffect } from 'react';
import { ShieldAlert, Activity, Radio, PhoneCall, UserCheck, Clock, WifiOff } from 'lucide-react';

interface HeaderProps {
  wsConnected: boolean;
  activeRole: string;
}

export const Header: React.FC<HeaderProps> = ({ wsConnected, activeRole }) => {
  const [timeStr, setTimeStr] = useState('');

  useEffect(() => {
    const updateTime = () => {
      const now = new Date();
      setTimeStr(now.toLocaleTimeString('en-US', { timeZone: 'Asia/Dhaka', hour12: true }) + ' (BST)');
    };
    updateTime();
    const interval = setInterval(updateTime, 1000);
    return () => clearInterval(interval);
  }, []);

  return (
    <header className="glass-panel sticky top-0 z-50 px-6 py-3 border-b border-slate-800/80 flex flex-wrap items-center justify-between gap-4">
      {/* Brand & Platform Identity */}
      <div className="flex items-center gap-3">
        <div className="relative flex items-center justify-center w-11 h-11 rounded-xl bg-gradient-to-br from-red-600 to-amber-600 shadow-lg shadow-red-900/40 border border-red-400/30">
          <ShieldAlert className="w-6 h-6 text-white animate-pulse" />
          <span className="absolute -top-1 -right-1 flex h-3 w-3">
            <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-cyan-400 opacity-75"></span>
            <span className="relative inline-flex rounded-full h-3 w-3 bg-cyan-500"></span>
          </span>
        </div>
        <div>
          <div className="flex items-center gap-2">
            <h1 className="text-xl font-extrabold tracking-tight text-white font-sans bg-clip-text text-transparent bg-gradient-to-r from-white via-slate-100 to-slate-400">
              HelpGuru <span className="text-xs px-2 py-0.5 rounded-full bg-cyan-500/10 text-cyan-400 border border-cyan-500/30 font-mono">v1.0 LIVE</span>
            </h1>
          </div>
          <p className="text-xs text-slate-400 font-medium">National Emergency Response & AI Multi-Factor Dispatch Engine</p>
        </div>
      </div>

      {/* Center Status Indicators */}
      <div className="flex items-center gap-4 text-xs">
        {/* Task 5: Defined STOMP WebSocket Live vs Offline Stale Data indicator */}
        <div className={`flex items-center gap-2 px-3 py-1.5 rounded-full border ${
          wsConnected 
            ? 'bg-emerald-950/40 border-emerald-500/30 text-emerald-400 shadow-[0_0_12px_rgba(16,185,129,0.15)]' 
            : 'bg-red-950/40 border-red-500/40 text-red-400 shadow-[0_0_12px_rgba(239,68,68,0.15)] animate-pulse'
        }`}>
          {wsConnected ? (
            <>
              <Radio className="w-3.5 h-3.5 animate-pulse text-emerald-400" />
              <span className="font-bold">STOMP WebSocket Live</span>
            </>
          ) : (
            <>
              <WifiOff className="w-3.5 h-3.5 text-red-400" />
              <span className="font-bold">Offline / Showing Stale Data</span>
            </>
          )}
        </div>

        {/* System Health Status */}
        <div className="hidden md:flex items-center gap-2 px-3 py-1.5 rounded-full bg-cyan-950/30 border border-cyan-500/20 text-cyan-300">
          <Activity className="w-3.5 h-3.5 text-cyan-400 animate-spin" style={{ animationDuration: '4s' }} />
          <span>Postgres DB UP (Supabase)</span>
        </div>

        {/* Live BST Clock */}
        <div className="hidden lg:flex items-center gap-2 px-3 py-1.5 rounded-full bg-slate-900/80 border border-slate-700/60 text-slate-300 font-mono">
          <Clock className="w-3.5 h-3.5 text-cyan-400" />
          <span>{timeStr || '12:00:00 PM (BST)'}</span>
        </div>
      </div>

      {/* Right User & Emergency Hotline Badges */}
      <div className="flex items-center gap-3">
        {/* National Hotlines */}
        <div className="hidden xl:flex items-center gap-2 text-[11px] text-slate-400 border-r border-slate-800 pr-3">
          <PhoneCall className="w-3.5 h-3.5 text-red-400" />
          <span>Emergency: <strong className="text-white font-mono">999</strong> | Disaster: <strong className="text-amber-300 font-mono">1090</strong></span>
        </div>

        {/* Active Operator Profile */}
        <div className="flex items-center gap-2.5 px-3 py-1.5 rounded-xl bg-slate-900/90 border border-slate-800 text-xs">
          <div className="w-7 h-7 rounded-lg bg-gradient-to-br from-cyan-500 to-blue-600 flex items-center justify-center font-bold text-white shadow">
            <UserCheck className="w-4 h-4" />
          </div>
          <div>
            <div className="font-bold text-slate-200">Dhaka Central Dispatcher</div>
            <div className="text-[10px] text-cyan-400 font-mono font-semibold uppercase">{activeRole}</div>
          </div>
        </div>
      </div>
    </header>
  );
};
