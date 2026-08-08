import React from 'react';
import { Incident, Hospital, Resource, Assignment } from '../types';
import { AlertTriangle, Ambulance, Building2, Clock, CheckCircle } from 'lucide-react';

interface MetricsBarProps {
  incidents: Incident[];
  hospitals: Hospital[];
  resources: Resource[];
  assignments: Assignment[];
}

export const MetricsBar: React.FC<MetricsBarProps> = ({
  incidents,
  hospitals,
  resources,
  assignments
}) => {
  const activeIncidentsCount = incidents.filter(i => i.status !== 'RESOLVED').length;
  const dispatchedCount = resources.filter(r => r.status === 'DISPATCHED').length;
  
  const totalBedsAvailable = hospitals.reduce((acc, h) => acc + h.availableBeds, 0);
  const totalIcuAvailable = hospitals.reduce((acc, h) => acc + h.icuAvailable, 0);

  return (
    <footer className="glass-panel px-6 py-2.5 border-t border-slate-800/80 flex flex-wrap items-center justify-between gap-4 text-xs">
      <div className="flex flex-wrap items-center gap-6">
        {/* Active Incidents Metric */}
        <div className="flex items-center gap-2 font-mono">
          <div className="w-2.5 h-2.5 rounded-full bg-red-500 animate-ping" />
          <span className="text-slate-400">Active Disasters:</span>
          <strong className="text-red-400 font-extrabold text-sm">{activeIncidentsCount}</strong>
        </div>

        {/* Dispatched Ambulances Metric */}
        <div className="flex items-center gap-2 font-mono">
          <Ambulance className="w-4 h-4 text-cyan-400" />
          <span className="text-slate-400">Units Dispatched:</span>
          <strong className="text-cyan-400 font-extrabold text-sm">{dispatchedCount} / {resources.length}</strong>
        </div>

        {/* ICU & Hospital Bed Availability */}
        <div className="flex items-center gap-2 font-mono">
          <Building2 className="w-4 h-4 text-emerald-400" />
          <span className="text-slate-400">ICU Beds Free:</span>
          <strong className="text-emerald-400 font-extrabold text-sm">{totalIcuAvailable} (General Beds: {totalBedsAvailable})</strong>
        </div>

        {/* Avg Response Time */}
        <div className="hidden lg:flex items-center gap-2 font-mono">
          <Clock className="w-4 h-4 text-amber-400" />
          <span className="text-slate-400">Avg AI Calculated ETA:</span>
          <strong className="text-amber-300 font-extrabold text-sm">~ 0.2 min</strong>
        </div>
      </div>

      <div className="flex items-center gap-2 text-[11px] text-slate-400 font-mono">
        <CheckCircle className="w-3.5 h-3.5 text-emerald-400" />
        <span>HelpGuru Engine: <strong className="text-white">Multi-Factor Objective Scoring Active</strong></span>
      </div>
    </footer>
  );
};
