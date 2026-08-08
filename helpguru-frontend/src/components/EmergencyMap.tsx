import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, Circle, useMap } from 'react-leaflet';
import L from 'leaflet';
import { Incident, Hospital, Resource } from '../types';
import { Ambulance, Building2, MapPin, Sparkles, Navigation, Layers, Eye } from 'lucide-react';

interface EmergencyMapProps {
  incidents: Incident[];
  hospitals: Hospital[];
  resources: Resource[];
  selectedIncident: Incident | null;
  onSelectIncident: (incident: Incident) => void;
}

// Custom Leaflet DivIcons for rich aesthetics
const createCustomIcon = (type: 'INCIDENT' | 'RESOURCE' | 'HOSPITAL', label?: string, subLabel?: string, isSelected = false) => {
  let iconHtml = '';

  if (type === 'INCIDENT') {
    iconHtml = `
      <div class="relative flex items-center justify-center w-10 h-10 rounded-full ${isSelected ? 'bg-red-500 scale-125 z-50 ring-4 ring-cyan-400' : 'bg-red-600/90'} text-white font-extrabold text-xs shadow-lg shadow-red-900/60 border-2 border-red-300 animate-radar">
        <span class="font-mono font-bold">${label || '!'}</span>
        <span class="absolute -bottom-5 bg-slate-900/95 text-red-300 px-1.5 py-0.2 rounded border border-red-500/40 text-[9px] font-mono whitespace-nowrap shadow">
          SEV ${subLabel || '8'}
        </span>
      </div>
    `;
  } else if (type === 'RESOURCE') {
    iconHtml = `
      <div class="relative flex items-center justify-center w-9 h-9 rounded-full bg-cyan-600/90 text-white font-extrabold text-xs shadow-lg shadow-cyan-900/60 border-2 border-cyan-300 animate-pulse-cyan">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"/></svg>
        <span class="absolute -bottom-5 bg-slate-900/95 text-cyan-300 px-1.5 py-0.2 rounded border border-cyan-500/40 text-[9px] font-mono whitespace-nowrap shadow">
          ${subLabel || 'AMB'}
        </span>
      </div>
    `;
  } else {
    iconHtml = `
      <div class="relative flex items-center justify-center w-9 h-9 rounded-full bg-emerald-600/90 text-white font-extrabold text-xs shadow-lg shadow-emerald-900/60 border-2 border-emerald-300">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"/></svg>
        <span class="absolute -bottom-5 bg-slate-900/95 text-emerald-300 px-1.5 py-0.2 rounded border border-emerald-500/40 text-[9px] font-mono whitespace-nowrap shadow">
          ${subLabel || '120 Beds'}
        </span>
      </div>
    `;
  }

  return L.divIcon({
    html: iconHtml,
    className: 'custom-leaflet-marker',
    iconSize: [40, 40],
    iconAnchor: [20, 20]
  });
};

// Map Recenter Controller when Incident is selected
const MapController: React.FC<{ selectedIncident: Incident | null }> = ({ selectedIncident }) => {
  const map = useMap();
  useEffect(() => {
    if (selectedIncident) {
      map.flyTo([selectedIncident.latitude, selectedIncident.longitude], 13, {
        duration: 1.5
      });
    }
  }, [selectedIncident, map]);
  return null;
};

export const EmergencyMap: React.FC<EmergencyMapProps> = ({
  incidents,
  hospitals,
  resources,
  selectedIncident,
  onSelectIncident
}) => {
  const [viewMode, setViewMode] = useState<'STANDARD' | 'HEATMAP'>('STANDARD');

  const centerLat = 22.7050;
  const centerLng = 90.3580;

  const handleMarkerClick = (inc: Incident) => {
    onSelectIncident(inc);
    // Task 1: Bidirectional scroll to card in left panel
    setTimeout(() => {
      const cardEl = document.getElementById(`incident-card-${inc.id}`);
      if (cardEl) {
        cardEl.scrollIntoView({ behavior: 'smooth', block: 'center' });
      }
    }, 100);
  };

  return (
    <div className="glass-panel rounded-2xl h-full overflow-hidden border border-slate-800/80 relative shadow-2xl flex flex-col">
      {/* Top Map Header Overlay */}
      <div className="absolute top-4 left-4 z-[400] glass-panel px-4 py-2 rounded-xl border border-slate-700/80 flex items-center gap-3 bg-slate-900/90 shadow-xl">
        <Navigation className="w-4 h-4 text-cyan-400 animate-spin" style={{ animationDuration: '6s' }} />
        <div>
          <div className="text-xs font-bold text-white flex items-center gap-2">
            <span>Bangladesh Emergency Response Grid</span>
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-ping"></span>
          </div>
          <div className="text-[10px] text-slate-400 font-mono">
            Incidents: <span className="text-red-400 font-bold">{incidents.length}</span> | Units: <span className="text-cyan-400 font-bold">{resources.length}</span> | Hospitals: <span className="text-emerald-400 font-bold">{hospitals.length}</span>
          </div>
        </div>
      </div>

      {/* Task 1: View Mode Toggle Button (Standard vs Heatmap / Cluster View) */}
      <div className="absolute top-4 right-4 z-[400] flex items-center gap-1 glass-panel p-1 rounded-xl border border-slate-700/80 bg-slate-900/95 shadow-xl text-xs font-mono">
        <button
          onClick={() => setViewMode('STANDARD')}
          className={`flex items-center gap-1.5 px-2.5 py-1 rounded-lg transition-all cursor-pointer ${
            viewMode === 'STANDARD'
              ? 'bg-cyan-500/20 text-cyan-300 border border-cyan-500/50 shadow font-bold'
              : 'text-slate-400 hover:text-white'
          }`}
        >
          <Eye className="w-3.5 h-3.5" />
          <span>Standard Pins</span>
        </button>
        <button
          onClick={() => setViewMode('HEATMAP')}
          className={`flex items-center gap-1.5 px-2.5 py-1 rounded-lg transition-all cursor-pointer ${
            viewMode === 'HEATMAP'
              ? 'bg-red-500/20 text-red-300 border border-red-500/50 shadow font-bold'
              : 'text-slate-400 hover:text-white'
          }`}
        >
          <Layers className="w-3.5 h-3.5" />
          <span>Density Heatmap</span>
        </button>
      </div>

      {/* Map Element */}
      <div className="flex-1 w-full h-full">
        <MapContainer
          center={[centerLat, centerLng] as [number, number]}
          zoom={8}
          scrollWheelZoom={true}
          style={{ width: '100%', height: '100%', borderRadius: '1rem' }}
        >
          <MapController selectedIncident={selectedIncident} />

          <TileLayer
            attribution='&copy; <a href="https://carto.com/">CARTO</a> HelpGuru GIS Engine'
            url="https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png"
          />

          {/* Task 1: Heatmap Density Circles View */}
          {viewMode === 'HEATMAP' && incidents.map(inc => (
            <Circle
              key={`heat-${inc.id}`}
              center={[inc.latitude, inc.longitude] as [number, number]}
              radius={inc.severityLevel * 1200}
              pathOptions={{
                color: '#EF4444',
                fillColor: '#EF4444',
                fillOpacity: 0.25,
                weight: 2
              }}
            />
          ))}

          {/* Render All Incidents (Red Radar Markers with Bidirectional Click) */}
          {incidents.map(inc => {
            const isSelected = selectedIncident?.id === inc.id;
            return (
              <Marker
                key={`inc-${inc.id}`}
                position={[inc.latitude, inc.longitude] as [number, number]}
                icon={createCustomIcon('INCIDENT', '!', `${inc.severityLevel}`, isSelected)}
                eventHandlers={{
                  click: () => handleMarkerClick(inc)
                }}
              >
                <Popup>
                  <div className="p-1 space-y-2 max-w-xs font-sans">
                    <div className="flex items-center justify-between gap-2 border-b border-slate-800 pb-1">
                      <span className="text-[10px] font-mono text-cyan-400 font-bold">{inc.incidentCode}</span>
                      <span className="text-[10px] font-bold text-red-400 bg-red-950 px-1.5 py-0.5 rounded border border-red-800">
                        SEV {inc.severityLevel}/10
                      </span>
                    </div>
                    <h4 className="text-xs font-bold text-white">{inc.title}</h4>
                    <p className="text-[11px] text-slate-300 line-clamp-2">{inc.description}</p>
                    <div className="text-[10px] text-slate-400 flex items-center gap-1 font-mono">
                      <MapPin className="w-3 h-3 text-cyan-400" />
                      <span>{inc.addressText}</span>
                    </div>
                    <button
                      onClick={() => handleMarkerClick(inc)}
                      className="w-full mt-2 py-1.5 px-3 rounded-lg bg-gradient-to-r from-red-600 to-amber-600 hover:from-red-500 hover:to-amber-500 text-white text-xs font-bold shadow transition-all cursor-pointer flex items-center justify-center gap-1.5"
                    >
                      <Sparkles className="w-3.5 h-3.5" />
                      <span>Analyze AI Dispatch</span>
                    </button>
                  </div>
                </Popup>
              </Marker>
            );
          })}

          {/* Task 1: Render ALL Resources / Ambulances on Map at All Times */}
          {resources.map(res => (
            <Marker
              key={`res-${res.id}`}
              position={[res.currentLatitude, res.currentLongitude] as [number, number]}
              icon={createCustomIcon('RESOURCE', 'AMB', res.resourceCode)}
            >
              <Popup>
                <div className="p-1 space-y-1 max-w-xs font-sans">
                  <div className="flex items-center justify-between text-[10px] font-mono text-cyan-400 font-bold">
                    <span>{res.resourceCode}</span>
                    <span className="text-emerald-400 bg-emerald-950 px-1.5 py-0.5 rounded border border-emerald-800">
                      {res.status}
                    </span>
                  </div>
                  <h4 className="text-xs font-bold text-white flex items-center gap-1.5">
                    <Ambulance className="w-4 h-4 text-cyan-400" />
                    <span>{res.name}</span>
                  </h4>
                  <div className="text-[11px] text-slate-300 font-mono">
                    Speed: <strong className="text-cyan-400">{res.speedKmh} km/h</strong> | Capacity: {res.capacity}
                  </div>
                </div>
              </Popup>
            </Marker>
          ))}

          {/* Task 1: Render ALL Hospitals on Map at All Times */}
          {hospitals.map(hosp => (
            <Marker
              key={`hosp-${hosp.id}`}
              position={[hosp.latitude, hosp.longitude] as [number, number]}
              icon={createCustomIcon('HOSPITAL', 'HOSP', `${hosp.availableBeds} Beds`)}
            >
              <Popup>
                <div className="p-1 space-y-1 max-w-xs font-sans">
                  <div className="flex items-center justify-between text-[10px] font-mono text-emerald-400 font-bold">
                    <span>{hosp.code}</span>
                    <span className="text-emerald-300 bg-emerald-950 px-1.5 py-0.5 rounded border border-emerald-800">
                      {hosp.status}
                    </span>
                  </div>
                  <h4 className="text-xs font-bold text-white flex items-center gap-1.5">
                    <Building2 className="w-4 h-4 text-emerald-400" />
                    <span>{hosp.name}</span>
                  </h4>
                  <div className="text-[11px] text-slate-300 font-mono">
                    Beds: <strong className="text-emerald-400">{hosp.availableBeds} / {hosp.totalBeds}</strong> | ICU: {hosp.icuAvailable}/{hosp.icuTotal}
                  </div>
                </div>
              </Popup>
            </Marker>
          ))}
        </MapContainer>
      </div>
    </div>
  );
};
