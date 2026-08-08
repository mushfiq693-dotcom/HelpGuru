import React, { useState } from 'react';
import { X, AlertTriangle, MapPin, CheckCircle, Flame, Waves, ShieldAlert } from 'lucide-react';
import { Incident } from '../types';

interface CreateIncidentModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (data: any) => Promise<Incident>;
}

const PRESET_LOCATIONS = [
  { name: 'Barishal Sadar Coastal Zone', lat: 22.7050, lng: 90.3580, regionId: 2, address: 'Sadat Road, Barishal Sadar' },
  { name: 'Dhaka Gulshan Emergency Zone', lat: 23.7925, lng: 90.4078, regionId: 1, address: 'Gulshan Circle 2, Dhaka' },
  { name: 'Chittagong Port Terminal', lat: 22.3350, lng: 91.8320, regionId: 3, address: 'Port Access Road, Chittagong' },
  { name: 'Sylhet Haor Flash Flood Zone', lat: 24.8949, lng: 91.8687, regionId: 4, address: 'Zindabazar, Sylhet' },
  { name: 'Khulna Mongla Cyclone Zone', lat: 22.4833, lng: 89.6000, regionId: 5, address: 'Mongla Port, Khulna' },
];

export const CreateIncidentModal: React.FC<CreateIncidentModalProps> = ({
  isOpen,
  onClose,
  onSubmit
}) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [severityLevel, setSeverityLevel] = useState(8);
  const [affectedPeopleCount, setAffectedPeopleCount] = useState(15);
  const [timeSensitivityLevel, setTimeSensitivityLevel] = useState('CRITICAL');
  const [requiredResourceType, setRequiredResourceType] = useState('AMBULANCE');
  const [regionId, setRegionId] = useState(2);
  const [latitude, setLatitude] = useState(22.7050);
  const [longitude, setLongitude] = useState(90.3580);
  const [addressText, setAddressText] = useState('Sadat Road, Barishal Sadar');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  if (!isOpen) return null;

  const handleSelectPreset = (preset: typeof PRESET_LOCATIONS[0]) => {
    setLatitude(preset.lat);
    setLongitude(preset.lng);
    setRegionId(preset.regionId);
    setAddressText(preset.address);
  };

  const handleSubmitForm = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim() || !description.trim()) {
      setError('Please fill in both disaster title and description.');
      return;
    }

    try {
      setLoading(true);
      setError('');
      await onSubmit({
        title,
        description,
        severityLevel,
        affectedPeopleCount,
        timeSensitivityLevel,
        requiredResourceType,
        regionId,
        latitude,
        longitude,
        addressText
      });
      onClose();
    } catch (err: any) {
      setError(err.message || 'Failed to report incident');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/75 backdrop-blur-sm animate-fadeIn">
      <div className="glass-panel rounded-2xl w-full max-w-2xl overflow-hidden border border-slate-700/80 shadow-2xl bg-slate-900/95">
        {/* Header */}
        <div className="p-5 border-b border-slate-800 flex items-center justify-between bg-slate-950/80">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-red-500/10 border border-red-500/30 flex items-center justify-center text-red-400">
              <AlertTriangle className="w-5 h-5 animate-pulse" />
            </div>
            <div>
              <h2 className="text-base font-bold text-white font-sans">Report Emergency Incident</h2>
              <p className="text-xs text-slate-400">Broadcast disaster report into HelpGuru AI Decision Pipeline</p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-2 rounded-xl text-slate-400 hover:text-white hover:bg-slate-800 transition-colors cursor-pointer"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Form Body */}
        <form onSubmit={handleSubmitForm} className="p-6 space-y-4 max-h-[80vh] overflow-y-auto">
          {error && (
            <div className="p-3 rounded-xl bg-red-950/60 border border-red-500/40 text-red-300 text-xs flex items-center gap-2">
              <AlertTriangle className="w-4 h-4 shrink-0" />
              <span>{error}</span>
            </div>
          )}

          {/* Quick Preset Selector */}
          <div>
            <label className="block text-xs font-bold text-slate-300 mb-2 font-mono uppercase">
              Quick Select Disaster Zone:
            </label>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
              {PRESET_LOCATIONS.map((preset, idx) => (
                <button
                  type="button"
                  key={idx}
                  onClick={() => handleSelectPreset(preset)}
                  className={`p-2.5 rounded-xl text-left border transition-all text-xs flex items-center justify-between cursor-pointer ${
                    latitude === preset.lat && longitude === preset.lng
                      ? 'bg-cyan-950/60 border-cyan-500 text-cyan-200 shadow-[0_0_12px_rgba(6,182,212,0.2)]'
                      : 'bg-slate-950/40 border-slate-800 text-slate-400 hover:text-slate-200 hover:border-slate-700'
                  }`}
                >
                  <span className="truncate font-medium">{preset.name}</span>
                  <MapPin className="w-3.5 h-3.5 text-cyan-400 shrink-0 ml-1" />
                </button>
              ))}
            </div>
          </div>

          {/* Title & Type */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div className="sm:col-span-2">
              <label className="block text-xs font-bold text-slate-300 mb-1">Disaster Title *</label>
              <input
                type="text"
                value={title}
                onChange={e => setTitle(e.target.value)}
                placeholder="e.g. Barishal Sadar Coastal Flood Trapped Victims"
                className="w-full px-3 py-2 rounded-xl bg-slate-950 border border-slate-800 text-white text-xs focus:outline-none focus:border-cyan-500 transition-colors"
                required
              />
            </div>
            <div>
              <label className="block text-xs font-bold text-slate-300 mb-1">Required Unit</label>
              <select
                value={requiredResourceType}
                onChange={e => setRequiredResourceType(e.target.value)}
                className="w-full px-3 py-2 rounded-xl bg-slate-950 border border-slate-800 text-white text-xs focus:outline-none focus:border-cyan-500 transition-colors"
              >
                <option value="AMBULANCE">AMBULANCE (Medical)</option>
                <option value="RESCUE_BOAT">RESCUE BOAT (Water)</option>
                <option value="FIRE_TRUCK">FIRE TRUCK (Hazard)</option>
                <option value="HELICOPTER">HELICOPTER (Air)</option>
              </select>
            </div>
          </div>

          {/* Description */}
          <div>
            <label className="block text-xs font-bold text-slate-300 mb-1">Detailed Situation Description *</label>
            <textarea
              value={description}
              onChange={e => setDescription(e.target.value)}
              rows={3}
              placeholder="Describe victims, trapped count, medical urgency..."
              className="w-full px-3 py-2 rounded-xl bg-slate-950 border border-slate-800 text-white text-xs focus:outline-none focus:border-cyan-500 transition-colors"
              required
            />
          </div>

          {/* Severity & Trapped Count sliders */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 bg-slate-950/60 p-3.5 rounded-xl border border-slate-800">
            <div>
              <div className="flex justify-between items-center mb-1 text-xs">
                <span className="font-bold text-slate-300">Severity Level (1 to 10)</span>
                <span className="font-mono font-bold text-red-400">{severityLevel} / 10</span>
              </div>
              <input
                type="range"
                min={1}
                max={10}
                value={severityLevel}
                onChange={e => setSeverityLevel(Number(e.target.value))}
                className="w-full accent-red-500 cursor-pointer"
              />
            </div>
            <div>
              <div className="flex justify-between items-center mb-1 text-xs">
                <span className="font-bold text-slate-300">Trapped Victims Count</span>
                <span className="font-mono font-bold text-amber-300">{affectedPeopleCount} Persons</span>
              </div>
              <input
                type="range"
                min={1}
                max={100}
                value={affectedPeopleCount}
                onChange={e => setAffectedPeopleCount(Number(e.target.value))}
                className="w-full accent-amber-500 cursor-pointer"
              />
            </div>
          </div>

          {/* Location & Address Text */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div>
              <label className="block text-xs font-bold text-slate-300 mb-1">Latitude</label>
              <input
                type="number"
                step="any"
                value={latitude}
                onChange={e => setLatitude(Number(e.target.value))}
                className="w-full px-3 py-2 rounded-xl bg-slate-950 border border-slate-800 text-white text-xs font-mono focus:outline-none focus:border-cyan-500"
              />
            </div>
            <div>
              <label className="block text-xs font-bold text-slate-300 mb-1">Longitude</label>
              <input
                type="number"
                step="any"
                value={longitude}
                onChange={e => setLongitude(Number(e.target.value))}
                className="w-full px-3 py-2 rounded-xl bg-slate-950 border border-slate-800 text-white text-xs font-mono focus:outline-none focus:border-cyan-500"
              />
            </div>
            <div>
              <label className="block text-xs font-bold text-slate-300 mb-1">Street Address</label>
              <input
                type="text"
                value={addressText}
                onChange={e => setAddressText(e.target.value)}
                className="w-full px-3 py-2 rounded-xl bg-slate-950 border border-slate-800 text-white text-xs focus:outline-none focus:border-cyan-500"
              />
            </div>
          </div>

          {/* Submit Footer */}
          <div className="pt-3 border-t border-slate-800 flex justify-end gap-3">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded-xl bg-slate-900 border border-slate-800 text-slate-300 text-xs font-bold hover:bg-slate-800 transition-colors cursor-pointer"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading}
              className="px-5 py-2 rounded-xl bg-gradient-to-r from-red-600 to-amber-600 hover:from-red-500 hover:to-amber-500 text-white text-xs font-bold shadow-lg shadow-red-900/40 transition-all cursor-pointer flex items-center gap-2"
            >
              {loading ? (
                <span>Registering...</span>
              ) : (
                <>
                  <CheckCircle className="w-4 h-4" />
                  <span>Submit Disaster Report</span>
                </>
              )}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
