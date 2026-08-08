import React, { useState, useEffect } from 'react';
import { Header } from './components/Header';
import { IncidentList } from './components/IncidentList';
import { EmergencyMap } from './components/EmergencyMap';
import { DispatchPanel } from './components/DispatchPanel';
import { CreateIncidentModal } from './components/CreateIncidentModal';
import { NotificationFeed } from './components/NotificationFeed';
import { MetricsBar } from './components/MetricsBar';
import { Incident, Hospital, Resource, Assignment, NotificationItem } from './types';
import { apiService } from './services/api';
import { wsService } from './services/websocket';

export function App() {
  const [incidents, setIncidents] = useState<Incident[]>([]);
  const [hospitals, setHospitals] = useState<Hospital[]>([]);
  const [resources, setResources] = useState<Resource[]>([]);
  const [assignments, setAssignments] = useState<Assignment[]>([]);
  const [notifications, setNotifications] = useState<NotificationItem[]>([]);
  const [selectedIncident, setSelectedIncident] = useState<Incident | null>(null);
  const [isReportModalOpen, setIsReportModalOpen] = useState(false);
  const [wsConnected, setWsConnected] = useState(false);
  const [activeRole] = useState('ROLE_OPERATOR');
  const [loading, setLoading] = useState(true);

  // Initial Load from Spring Boot Backend REST APIs
  useEffect(() => {
    loadAllData();
    // Connect WebSocket
    wsService.connect(1);
    setWsConnected(true);

    // Subscribe to STOMP WebSocket push events
    const unsubscribe = wsService.subscribe((event: any) => {
      if (event.title && event.message) {
        // Incoming notification
        setNotifications(prev => [event, ...prev]);
      } else if (event.incidentCode) {
        // Incoming incident update
        setIncidents(prev => [event, ...prev.filter(i => i.id !== event.id)]);
      }
    });

    return () => unsubscribe();
  }, []);

  const loadAllData = async () => {
    try {
      setLoading(true);
      const [incData, hospData, resData, asnData, notifData] = await Promise.all([
        apiService.getIncidents().catch(() => []),
        apiService.getHospitals().catch(() => []),
        apiService.getResources().catch(() => []),
        apiService.getAssignments().catch(() => []),
        apiService.getNotifications(1).catch(() => [])
      ]);

      setIncidents(incData);
      setHospitals(hospData);
      setResources(resData);
      setAssignments(asnData);
      setNotifications(notifData);

      if (incData.length > 0) {
        setSelectedIncident(incData[0]);
      }
    } catch (e) {
      console.error('Failed to initialize dashboard data', e);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateIncident = async (newIncidentData: any): Promise<Incident> => {
    const created = await apiService.createIncident(newIncidentData);
    setIncidents(prev => [created, ...prev]);
    setSelectedIncident(created);
    return created;
  };

  const handleAssignmentCreated = (newAssignment: Assignment) => {
    setAssignments(prev => [newAssignment, ...prev]);
    // Update resource status in UI state
    setResources(prev => prev.map(r => r.id === newAssignment.resource.id ? { ...r, status: 'DISPATCHED' } : r));
    // Update incident status in UI state
    setIncidents(prev => prev.map(i => i.id === newAssignment.incident.id ? { ...i, status: 'ASSIGNED' } : i));
  };

  const handleMarkNotificationRead = async (id: number) => {
    try {
      const updated = await apiService.markNotificationRead(id);
      setNotifications(prev => prev.map(n => n.id === id ? updated : n));
    } catch (e) {
      console.error('Failed to mark read', e);
    }
  };

  return (
    <div className="flex flex-col h-screen overflow-hidden bg-[#0B0F19] text-slate-100">
      {/* Top Header Navigation */}
      <Header wsConnected={wsConnected} activeRole={activeRole} />

      {/* Main 3-Column Command Center Grid */}
      <main className="flex-1 p-4 grid grid-cols-1 lg:grid-cols-12 gap-4 overflow-hidden">
        {/* Left Column: Active Incidents List (3 cols) */}
        <div className="lg:col-span-3 h-full overflow-hidden">
          <IncidentList
            incidents={incidents}
            selectedIncident={selectedIncident}
            onSelectIncident={setSelectedIncident}
            onOpenReportModal={() => setIsReportModalOpen(true)}
          />
        </div>

        {/* Center Column: Interactive Emergency Command Map (6 cols) */}
        <div className="lg:col-span-6 h-full overflow-hidden relative">
          <EmergencyMap
            incidents={incidents}
            hospitals={hospitals}
            resources={resources}
            selectedIncident={selectedIncident}
            onSelectIncident={setSelectedIncident}
          />
          {/* Floating WebSocket Notification Bell */}
          <div className="absolute right-4 bottom-4 z-[450]">
            <NotificationFeed
              notifications={notifications}
              onMarkRead={handleMarkNotificationRead}
            />
          </div>
        </div>

        {/* Right Column: AI Decision Engine & Dispatch Optimizer (3 cols) */}
        <div className="lg:col-span-3 h-full overflow-hidden">
          <DispatchPanel
            selectedIncident={selectedIncident}
            hospitals={hospitals}
            assignments={assignments}
            onAssignmentCreated={handleAssignmentCreated}
          />
        </div>
      </main>

      {/* Bottom Key Metrics Footer */}
      <MetricsBar
        incidents={incidents}
        hospitals={hospitals}
        resources={resources}
        assignments={assignments}
      />

      {/* Report New Disaster Incident Modal */}
      <CreateIncidentModal
        isOpen={isReportModalOpen}
        onClose={() => setIsReportModalOpen(false)}
        onSubmit={handleCreateIncident}
      />
    </div>
  );
}

export default App;
