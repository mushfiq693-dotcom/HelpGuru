import React, { useState } from 'react';
import { Incident } from '../types';
import { AlertTriangle, Users, MapPin, Plus, Flame, Waves, ShieldAlert, Sparkles, Search, ArrowUpDown } from 'lucide-react';

interface IncidentListProps {
  incidents: Incident[];
  selectedIncident: Incident | null;
  onSelectIncident: (incident: Incident) => void;
  onOpenReportModal: () => void;
}

export const IncidentList: React.FC<IncidentListProps> = ({
  incidents,
  selectedIncident,
  onSelectIncident,
  onOpenReportModal
}) => {
  const [filterType, setFilterType] = useState<string>('ALL');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [sortOrder, setSortOrder] = useState<'SEVERITY_DESC' | 'NEWEST'>('SEVERITY_DESC');

  // Task 3: Combinable Filtering & Auto-sorting by severity
  const processedIncidents = incidents
    .filter(inc => {
      const matchesType = filterType === 'ALL' || inc.requiredResourceType === filterType;
      const matchesSearch = !searchQuery.trim() || 
        inc.title.toLowerCase().includes(searchQuery.toLowerCase()) || 
        inc.addressText.toLowerCase().includes(searchQuery.toLowerCase()) ||
        inc.incidentCode.toLowerCase().includes(searchQuery.toLowerCase());
      return matchesType && matchesSearch;
    })
    .sort((a, b) => {
      if (sortOrder === 'SEVERITY_DESC') {
        return b.severityLevel - a.severityLevel;
      }
      return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
    });

  const getResourceTypeIcon = (type: string) => {
    switch (type) {
      case 'AMBULANCE': return <AlertTriangle className="w-4 h-4 text-red-400" />;
      case 'RESCUE_BOAT': return <Waves className="w-4 h-4 text-cyan-400" />;
      case 'FIRE_TRUCK': return <Flame className="w-4 h-4 text-amber-400" />;
      default: return <ShieldAlert className="w-4 h-4 text-emerald-400" />;
    }
  };

  // Task 7: Non-color accessibility signals (Icon + Text Label + Level)
  const getSeverityBadge = (level: number) => {
    if (level >= 8) {
      return (
        <span className="px-2 py-0.5 rounded-full text-[10px] font-extrabold bg-red-500/20 text-red-300 border border-red-500/50 flex items-center gap-1 animate-pulse">
          <AlertTriangle className="w-3 h-3 text-red-400" />
          <span>CRITICAL ({level}/10)</span>
        </span>
      );
    }
    if (level >= 5) {
      return (
        <span className="px-2 py-0.5 rounded-full text-[10px] font-extrabold bg-amber-500/20 text-amber-300 border border-amber-500/50 flex items-center gap-1">
          <ShieldAlert className="w-3 h-3 text-amber-400" />
          <span>HIGH ({level}/10)</span>
        </span>
      );
    }
    return (
      <span className="px-2 py-0.5 rounded-full text-[10px] font-extrabold bg-emerald-500/20 text-emerald-300 border border-emerald-500/50 flex items-center gap-1">
        <Sparkles className="w-3 h-3 text-emerald-400" />
        <span>MODERATE ({level}/10)</span>
      </span>
    );
  };

  return (
    <div className="glass-panel rounded-2xl flex flex-col h-full overflow-hidden border border-slate-800/80 shadow-2xl">
      {/* Header & Quick Action */}
      <div className="p-4 border-b border-slate-800/80 flex items-center justify-between gap-2 bg-slate-900/60">
        <div>
          <h2 className="text-sm font-bold text-white uppercase tracking-wider flex items-center gap-2 font-mono">
            <AlertTriangle className="w-4 h-4 text-red-500" />
            Active Disasters ({processedIncidents.length})
          </h2>
          <p className="text-[11px] text-slate-400">Click incident to trigger AI Dispatch Engine</p>
        </div>
        <button
          onClick={onOpenReportModal}
          className="flex items-center gap-1.5 px-3 py-1.5 rounded-xl bg-gradient-to-r from-red-600 to-amber-600 hover:from-red-500 hover:to-amber-500 text-white text-xs font-bold shadow-lg shadow-red-900/30 transition-all transform hover:scale-105 active:scale-95 cursor-pointer"
        >
          <Plus className="w-4 h-4" />
          <span>Report New</span>
        </button>
      </div>

      {/* Task 3: Search input & Sort Order Toggle */}
      <div className="p-3 border-b border-slate-800/60 space-y-2 bg-slate-950/40">
        <div className="relative">
          <Search className="w-3.5 h-3.5 text-slate-400 absolute left-3 top-2.5" />
          <input
            type="text"
            value={searchQuery}
            onChange={e => setSearchQuery(e.target.value)}
            placeholder="Search incident, location, code..."
            className="w-full pl-8 pr-3 py-1.5 rounded-xl bg-slate-900 border border-slate-800 text-white text-xs focus:outline-none focus:border-cyan-500 transition-colors"
          />
        </div>

        <div className="flex items-center justify-between gap-2">
          {/* Resource Filter Pills */}
          <div className="flex items-center gap-1 overflow-x-auto no-scrollbar">
            {['ALL', 'AMBULANCE', 'RESCUE_BOAT', 'FIRE_TRUCK'].map(type => (
              <button
                key={type}
                onClick={() => setFilterType(type)}
                className={`px-2 py-0.5 rounded-md text-[10px] font-medium transition-all whitespace-nowrap cursor-pointer ${
                  filterType === type
                    ? 'bg-cyan-500/20 text-cyan-300 border border-cyan-500/50 shadow font-bold'
                    : 'bg-slate-900 text-slate-400 hover:text-slate-200 border border-slate-800'
                }`}
              >
                {type.replace('_', ' ')}
              </button>
            ))}
          </div>

          {/* Sort order toggle */}
          <button
            onClick={() => setSortOrder(prev => prev === 'SEVERITY_DESC' ? 'NEWEST' : 'SEVERITY_DESC')}
            className="flex items-center gap-1 text-[10px] font-mono text-cyan-400 hover:text-cyan-300 px-2 py-0.5 rounded bg-slate-900 border border-slate-800 cursor-pointer shrink-0"
            title="Toggle sort order"
          >
            <ArrowUpDown className="w-3 h-3" />
            <span>{sortOrder === 'SEVERITY_DESC' ? 'Severity ↓' : 'Newest ↓'}</span>
          </button>
        </div>
      </div>

      {/* Incident List */}
      <div className="flex-1 overflow-y-auto p-3 space-y-2.5">
        {processedIncidents.length === 0 ? (
          <div className="text-center py-10 px-4 text-slate-500 text-xs">
            No active incidents match current search filters.
          </div>
        ) : (
          processedIncidents.map(inc => {
            const isSelected = selectedIncident?.id === inc.id;
            return (
              <div
                key={inc.id}
                id={`incident-card-${inc.id}`}
                onClick={() => onSelectIncident(inc)}
                className={`p-3.5 rounded-xl border transition-all cursor-pointer relative overflow-hidden group ${
                  isSelected
                    ? 'bg-slate-800/90 border-cyan-500 shadow-[0_0_20px_rgba(6,182,212,0.25)] transform translate-x-1 ring-1 ring-cyan-500/50'
                    : 'glass-panel-hover bg-slate-900/50 border-slate-800/80'
                }`}
              >
                {isSelected && (
                  <div className="absolute top-0 left-0 bottom-0 w-1.5 bg-gradient-to-b from-cyan-400 to-blue-500" />
                )}

                {/* Top Badge Row */}
                <div className="flex items-center justify-between gap-2 mb-2">
                  <span className="font-mono text-[10px] font-bold text-slate-400 px-1.5 py-0.5 rounded bg-slate-950/60 border border-slate-800">
                    {inc.incidentCode}
                  </span>
                  {getSeverityBadge(inc.severityLevel)}
                </div>

                {/* Title */}
                <h3 className="text-xs font-extrabold text-white group-hover:text-cyan-300 transition-colors mb-1 line-clamp-1">
                  {inc.title}
                </h3>

                {/* Description */}
                <p className="text-[11px] text-slate-400 line-clamp-2 mb-3 leading-relaxed">
                  {inc.description}
                </p>

                {/* Info Pills Footer */}
                <div className="flex flex-wrap items-center justify-between gap-2 text-[11px] text-slate-400 border-t border-slate-800/60 pt-2">
                  <div className="flex items-center gap-1 text-slate-300">
                    <MapPin className="w-3.5 h-3.5 text-cyan-400" />
                    <span className="truncate max-w-[130px] font-medium">{inc.addressText}</span>
                  </div>
                  <div className="flex items-center gap-1 text-amber-300 font-semibold font-mono">
                    <Users className="w-3.5 h-3.5 text-amber-400" />
                    <span>{inc.affectedPeopleCount} Trapped</span>
                  </div>
                </div>

                {/* Required Resource Indicator */}
                <div className="mt-2.5 pt-2 border-t border-slate-800/40 flex items-center justify-between text-[10px]">
                  <div className="flex items-center gap-1.5 text-slate-300 font-mono">
                    {getResourceTypeIcon(inc.requiredResourceType)}
                    <span>Needs: <strong className="text-white">{inc.requiredResourceType}</strong></span>
                  </div>
                  <span className="flex items-center gap-1 text-cyan-400 font-semibold group-hover:underline">
                    <Sparkles className="w-3 h-3 text-cyan-400 animate-spin" style={{ animationDuration: '3s' }} />
                    Analyze AI Score
                  </span>
                </div>
              </div>
            );
          })
        )}
      </div>
    </div>
  );
};
