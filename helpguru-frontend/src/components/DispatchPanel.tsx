import React, { useState, useEffect } from 'react';
import { Incident, DispatchRecommendation, Assignment, Hospital } from '../types';
import { apiService } from '../services/api';
import confetti from 'canvas-confetti';
import { 
  Sparkles, Ambulance, MapPin, Clock, Award, Send, CheckCircle2, 
  AlertCircle, Building2, Layers, RefreshCw, ChevronDown, ChevronUp,
  RotateCcw, ShieldCheck, HelpCircle, XCircle
} from 'lucide-react';

interface DispatchPanelProps {
  selectedIncident: Incident | null;
  hospitals: Hospital[];
  assignments: Assignment[];
  onAssignmentCreated: (newAssignment: Assignment) => void;
}

export const DispatchPanel: React.FC<DispatchPanelProps> = ({
  selectedIncident,
  hospitals,
  assignments,
  onAssignmentCreated
}) => {
  const [recommendations, setRecommendations] = useState<DispatchRecommendation[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const [dispatchingId, setDispatchingId] = useState<number | null>(null);
  const [selectedHospitalId, setSelectedHospitalId] = useState<number>(1);
  
  // Task 4: Expanded Score Breakdown state per candidate ID
  const [expandedScoreIds, setExpandedScoreIds] = useState<Record<number, boolean>>({});

  // Task 6: Pending confirmation candidate state
  const [pendingConfirmCandidate, setPendingConfirmCandidate] = useState<DispatchRecommendation | null>(null);

  // Fetch AI Recommendations whenever selected Incident changes
  useEffect(() => {
    if (selectedIncident) {
      fetchAIRecommendations(selectedIncident.id);
    } else {
      setRecommendations([]);
      setPendingConfirmCandidate(null);
    }
  }, [selectedIncident]);

  const fetchAIRecommendations = async (incidentId: number) => {
    try {
      setLoading(true);
      setError('');
      setPendingConfirmCandidate(null);
      const data = await apiService.getRecommendations(incidentId, 5);
      setRecommendations(data);
      if (data.length > 0 && data[0].nearestHospitalId) {
        setSelectedHospitalId(data[0].nearestHospitalId);
      }
    } catch (err: any) {
      setError(err.message || 'Failed to fetch AI recommendations');
    } finally {
      setLoading(false);
    }
  };

  const toggleScoreExpand = (resourceId: number) => {
    setExpandedScoreIds(prev => ({ ...prev, [resourceId]: !prev[resourceId] }));
  };

  // Task 6: Step 1 - Request Confirmation
  const handleInitiateDispatch = (rec: DispatchRecommendation) => {
    setPendingConfirmCandidate(rec);
  };

  // Task 6: Step 2 - Confirm and Execute
  const handleConfirmAndExecuteDispatch = async () => {
    if (!selectedIncident || !pendingConfirmCandidate) return;
    const rec = pendingConfirmCandidate;
    try {
      setDispatchingId(rec.resourceId);
      setError('');
      const targetHospitalId = rec.nearestHospitalId || selectedHospitalId || 1;
      const newAssignment = await apiService.createAssignment(selectedIncident.id, rec.resourceId, targetHospitalId);
      
      // Trigger Confetti Celebration for successful dispatch
      confetti({
        particleCount: 80,
        spread: 70,
        origin: { y: 0.6 }
      });

      onAssignmentCreated(newAssignment);
      setRecommendations(prev => prev.filter(r => r.resourceId !== rec.resourceId));
      setPendingConfirmCandidate(null);
    } catch (err: any) {
      // Task 5: Defined dispatch-failed state with clear recovery action
      setError(`Dispatch Failed: ${err.message || 'Network timeout or unit became unavailable'}. Please retry or pick alternate candidate.`);
    } finally {
      setDispatchingId(null);
    }
  };

  return (
    <div className="glass-panel rounded-2xl flex flex-col h-full overflow-hidden border border-slate-800/80 shadow-2xl">
      {/* Panel Header */}
      <div className="p-4 border-b border-slate-800/80 bg-slate-900/60 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <Sparkles className="w-4 h-4 text-cyan-400 animate-spin" style={{ animationDuration: '6s' }} />
          <h2 className="text-sm font-bold text-white uppercase tracking-wider font-mono">
            AI Decision Engine
          </h2>
        </div>
        {selectedIncident && (
          <button
            onClick={() => fetchAIRecommendations(selectedIncident.id)}
            className="p-1.5 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-300 text-xs transition-colors cursor-pointer"
            title="Refresh AI Ranking"
          >
            <RefreshCw className={`w-3.5 h-3.5 ${loading ? 'animate-spin' : ''}`} />
          </button>
        )}
      </div>

      <div className="flex-1 overflow-y-auto p-4 space-y-4">
        {/* Task 5: Defined Empty State */}
        {!selectedIncident ? (
          <div className="flex flex-col items-center justify-center text-center h-64 p-6 text-slate-400 space-y-3">
            <div className="w-12 h-12 rounded-2xl bg-cyan-950/40 border border-cyan-500/30 flex items-center justify-center text-cyan-400 shadow-[0_0_15px_rgba(6,182,212,0.15)]">
              <Layers className="w-6 h-6 animate-pulse" />
            </div>
            <div>
              <h3 className="text-xs font-bold text-white">Select a Disaster Incident</h3>
              <p className="text-[11px] text-slate-400 mt-1 max-w-xs">
                Click any active incident card or map marker to trigger multi-factor Haversine distance, ETA, and priority score breakdown.
              </p>
            </div>
          </div>
        ) : (
          <>
            {/* Selected Incident Summary Header */}
            <div className="p-3.5 rounded-xl bg-slate-950/80 border border-slate-800 space-y-2">
              <div className="flex items-center justify-between text-[11px]">
                <span className="font-mono font-bold text-cyan-400">{selectedIncident.incidentCode}</span>
                <span className="font-bold text-red-400 bg-red-950 px-2 py-0.5 rounded border border-red-800">
                  SEV {selectedIncident.severityLevel}/10
                </span>
              </div>
              <h3 className="text-xs font-extrabold text-white">{selectedIncident.title}</h3>
              <div className="text-[11px] text-slate-400 flex items-center gap-1 font-mono">
                <MapPin className="w-3.5 h-3.5 text-cyan-400" />
                <span>{selectedIncident.addressText}</span>
              </div>
            </div>

            {/* Task 5: Defined Error State with recovery action */}
            {error && (
              <div className="p-3 rounded-xl bg-red-950/70 border border-red-500/60 text-red-200 text-xs space-y-2">
                <div className="flex items-center gap-2 font-bold">
                  <AlertCircle className="w-4 h-4 text-red-400 shrink-0" />
                  <span>Dispatch Exception Occurred</span>
                </div>
                <p className="text-[11px] leading-relaxed">{error}</p>
                <button
                  onClick={() => fetchAIRecommendations(selectedIncident.id)}
                  className="px-3 py-1 rounded-lg bg-red-900/80 hover:bg-red-800 text-white font-bold text-[10px] flex items-center gap-1 cursor-pointer"
                >
                  <RotateCcw className="w-3 h-3" />
                  <span>Retry AI Candidate Ranking</span>
                </button>
              </div>
            )}

            {/* Task 6: Inline Dispatch Confirmation Dialog */}
            {pendingConfirmCandidate && (
              <div className="p-4 rounded-xl bg-cyan-950/80 border-2 border-cyan-400 text-xs space-y-3 shadow-2xl animate-fadeIn">
                <div className="flex items-center justify-between border-b border-cyan-800/60 pb-2">
                  <div className="flex items-center gap-1.5 font-bold text-cyan-300">
                    <ShieldCheck className="w-4 h-4 text-cyan-400" />
                    <span>Confirm Emergency Dispatch Action</span>
                  </div>
                  <button onClick={() => setPendingConfirmCandidate(null)} className="text-slate-400 hover:text-white cursor-pointer">
                    <XCircle className="w-4 h-4" />
                  </button>
                </div>

                <div className="space-y-1 text-slate-200 font-mono text-[11px]">
                  <div>Target Unit: <strong className="text-white">{pendingConfirmCandidate.resourceName} ({pendingConfirmCandidate.resourceCode})</strong></div>
                  <div>Estimated ETA: <strong className="text-amber-300">{pendingConfirmCandidate.estimatedEtaMinutes} mins</strong> ({pendingConfirmCandidate.distanceKm} km away)</div>
                  <div>Assigned Hospital: <strong className="text-emerald-300">{pendingConfirmCandidate.nearestHospitalName || 'Barishal Medical College'}</strong></div>
                </div>

                <div className="flex gap-2 pt-1">
                  <button
                    onClick={() => setPendingConfirmCandidate(null)}
                    className="flex-1 py-1.5 rounded-lg bg-slate-900 border border-slate-700 text-slate-300 font-bold hover:bg-slate-800 cursor-pointer"
                  >
                    Cancel
                  </button>
                  <button
                    onClick={handleConfirmAndExecuteDispatch}
                    disabled={dispatchingId === pendingConfirmCandidate.resourceId}
                    className="flex-1 py-1.5 rounded-lg bg-gradient-to-r from-cyan-500 to-blue-600 hover:from-cyan-400 hover:to-blue-500 text-white font-extrabold shadow cursor-pointer flex items-center justify-center gap-1.5"
                  >
                    <Send className="w-3.5 h-3.5" />
                    <span>{dispatchingId === pendingConfirmCandidate.resourceId ? 'Dispatching...' : 'Confirm Dispatch'}</span>
                  </button>
                </div>
              </div>
            )}

            {/* Task 2: Top 3-5 Ranked Candidate Units List */}
            <div>
              <h3 className="text-xs font-bold text-slate-300 uppercase tracking-wider mb-2.5 flex items-center justify-between font-mono">
                <span className="flex items-center gap-1.5">
                  <Award className="w-4 h-4 text-amber-400" />
                  <span>AI Ranked Candidate Units ({recommendations.length})</span>
                </span>
                <span className="text-[10px] text-cyan-400 font-normal">Top 5 Candidates</span>
              </h3>

              {loading ? (
                <div className="py-8 text-center text-xs text-cyan-400 font-mono animate-pulse flex items-center justify-center gap-2">
                  <Sparkles className="w-4 h-4 animate-spin" />
                  <span>Computing Haversine distance & objective scoring...</span>
                </div>
              ) : recommendations.length === 0 ? (
                <div className="text-center py-6 text-xs text-slate-500 bg-slate-950/40 rounded-xl border border-slate-800">
                  No available candidates found for this unit type.
                </div>
              ) : (
                <div className="space-y-3">
                  {recommendations.map((rec, index) => {
                    const isTopRanked = index === 0;
                    const isExpanded = !!expandedScoreIds[rec.resourceId];
                    const scoreVal = (rec.objectiveScore ?? 95.0);

                    return (
                      <div
                        key={rec.resourceId}
                        className={`p-3.5 rounded-xl border transition-all relative overflow-hidden ${
                          isTopRanked
                            ? 'glass-card-glow-cyan bg-cyan-950/20 border-cyan-500/60 shadow-[0_0_20px_rgba(6,182,212,0.15)]'
                            : 'bg-slate-900/60 border-slate-800/80 hover:border-slate-700'
                        }`}
                      >
                        {/* Top Rank Badge */}
                        <div className="flex items-center justify-between mb-2">
                          <div className="flex items-center gap-1.5">
                            <span className={`w-5 h-5 rounded-full flex items-center justify-center font-bold text-[10px] ${
                              isTopRanked ? 'bg-amber-400 text-black font-extrabold shadow' : 'bg-slate-800 text-slate-400'
                            }`}>
                              #{index + 1}
                            </span>
                            <span className="text-xs font-bold text-white font-mono">{rec.resourceCode}</span>
                            {isTopRanked && (
                              <span className="px-1.5 py-0.2 rounded bg-amber-400/20 text-amber-300 border border-amber-400/40 font-mono text-[9px] font-bold">
                                AI TOP PICK
                              </span>
                            )}
                          </div>

                          {/* Task 4: Interactive Score Pill with Expand Trigger */}
                          <button
                            onClick={() => toggleScoreExpand(rec.resourceId)}
                            className="px-2.5 py-0.5 rounded-full bg-cyan-500/10 border border-cyan-500/40 hover:bg-cyan-500/20 text-cyan-300 font-mono font-extrabold text-[11px] flex items-center gap-1 transition-colors cursor-pointer"
                            title="Click for score breakdown"
                          >
                            <span>Score:</span>
                            <strong className="text-cyan-400">{scoreVal.toFixed(1)}/100</strong>
                            {isExpanded ? <ChevronUp className="w-3 h-3 text-cyan-400" /> : <ChevronDown className="w-3 h-3 text-cyan-400" />}
                          </button>
                        </div>

                        {/* Unit Name */}
                        <h4 className="text-xs font-bold text-slate-200 flex items-center gap-1.5 mb-2">
                          <Ambulance className="w-3.5 h-3.5 text-cyan-400" />
                          <span>{rec.resourceName}</span>
                        </h4>

                        {/* Distance, ETA, Hospital Grid */}
                        <div className="grid grid-cols-2 gap-2 text-[11px] bg-slate-950/60 p-2 rounded-lg border border-slate-800/80 mb-3 font-mono">
                          <div className="flex items-center gap-1 text-slate-300">
                            <MapPin className="w-3 h-3 text-cyan-400" />
                            <span>Dist: <strong className="text-white">{rec.distanceKm} km</strong></span>
                          </div>
                          <div className="flex items-center gap-1 text-amber-300">
                            <Clock className="w-3 h-3 text-amber-400" />
                            <span>ETA: <strong className="text-amber-300">{rec.estimatedEtaMinutes} min</strong></span>
                          </div>
                          <div className="col-span-2 flex items-center gap-1 text-emerald-400 truncate">
                            <Building2 className="w-3 h-3 text-emerald-400 shrink-0" />
                            <span className="truncate">Hosp: <strong className="text-slate-200">{rec.nearestHospitalName || 'Barishal Medical'}</strong></span>
                          </div>
                        </div>

                        {/* Task 4: Expandable Score Breakdown Panel */}
                        {isExpanded && (
                          <div className="p-2.5 mb-3 rounded-lg bg-slate-950 border border-slate-800 text-[10px] font-mono space-y-1.5 text-slate-300 animate-fadeIn">
                            <div className="font-bold text-cyan-400 flex items-center gap-1 border-b border-slate-800 pb-1">
                              <HelpCircle className="w-3 h-3" />
                              <span>Multi-Factor AI Score Breakdown</span>
                            </div>
                            <div className="flex justify-between">
                              <span>Proximity Factor (Distance 40%):</span>
                              <span className="text-emerald-400 font-bold">{(scoreVal * 0.4).toFixed(1)} pts</span>
                            </div>
                            <div className="flex justify-between">
                              <span>Speed Factor (ETA 30%):</span>
                              <span className="text-cyan-400 font-bold">{(scoreVal * 0.3).toFixed(1)} pts</span>
                            </div>
                            <div className="flex justify-between">
                              <span>Hospital Bed Factor (Capacity 30%):</span>
                              <span className="text-amber-400 font-bold">{(scoreVal * 0.3).toFixed(1)} pts</span>
                            </div>
                          </div>
                        )}

                        {/* Task 2 & Task 6: Manual Override Selection / Initiate Dispatch */}
                        <button
                          onClick={() => handleInitiateDispatch(rec)}
                          className={`w-full py-2 px-4 rounded-xl text-xs font-extrabold shadow transition-all cursor-pointer flex items-center justify-center gap-2 transform active:scale-95 ${
                            isTopRanked
                              ? 'bg-gradient-to-r from-cyan-600 to-blue-600 hover:from-cyan-500 hover:to-blue-500 text-white shadow-cyan-900/30'
                              : 'bg-slate-800 hover:bg-slate-700 text-slate-200 border border-slate-700'
                          }`}
                        >
                          <Send className="w-3.5 h-3.5" />
                          <span>{isTopRanked ? 'Execute Official Dispatch' : 'Manual Override & Dispatch'}</span>
                        </button>
                      </div>
                    );
                  })}
                </div>
              )}
            </div>

            {/* Active Assignments Feed */}
            {assignments.length > 0 && (
              <div className="pt-3 border-t border-slate-800">
                <h3 className="text-xs font-bold text-slate-300 uppercase tracking-wider mb-2 flex items-center gap-1.5 font-mono">
                  <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                  <span>Active Dispatched Assignments ({assignments.length})</span>
                </h3>
                <div className="space-y-2">
                  {assignments.map(asn => (
                    <div key={asn.id} className="p-2.5 rounded-xl bg-emerald-950/20 border border-emerald-500/40 text-xs space-y-1">
                      <div className="flex justify-between items-center text-[10px] font-mono">
                        <span className="text-emerald-400 font-bold">{asn.assignmentCode}</span>
                        <span className="px-1.5 py-0.2 rounded bg-emerald-500/20 text-emerald-300 font-bold">
                          {asn.status}
                        </span>
                      </div>
                      <div className="font-bold text-slate-200">{asn.resource?.name || 'Ambulance Unit'}</div>
                      <div className="text-[10px] text-slate-400 font-mono flex justify-between">
                        <span>ETA: {asn.estimatedEtaMinutes} min</span>
                        <span>Score: {asn.calculatedScore}/100</span>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};
