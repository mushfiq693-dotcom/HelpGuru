import { 
  AuthResponse, Incident, Hospital, Resource, DispatchRecommendation, Assignment, NotificationItem 
} from '../types';

const API_BASE_URL = 'http://localhost:8080/api/v1';

// Default active JWT token for immediate demo session
let currentToken = localStorage.getItem('helpguru_jwt') || 'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJvcGVyYXRvcjEiLCJ1c2VySWQiOjEsImlhdCI6MTc4NjEzNjk4OCwiZXhwIjoxNzg2MjIzMzg4fQ.t_Z40zFiKaivgKcOGT1KBLj0j_tE1YyBsgagNh1O7ml0PhnvtrXvldIHoJ11RvRR';

export const setAuthToken = (token: string) => {
  currentToken = token;
  localStorage.setItem('helpguru_jwt', token);
};

export const getAuthToken = () => currentToken;

const getHeaders = (hasBody = true) => {
  const headers: Record<string, string> = {
    'Authorization': `Bearer ${currentToken}`
  };
  if (hasBody) {
    headers['Content-Type'] = 'application/json';
  }
  return headers;
};

export const apiService = {
  // Auth
  async login(usernameOrEmail = 'operator1', password = 'Password123!'): Promise<AuthResponse> {
    const res = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ usernameOrEmail, password })
    });
    if (!res.ok) throw new Error('Authentication failed');
    const data: AuthResponse = await res.json();
    setAuthToken(data.token);
    return data;
  },

  // Incidents
  async getIncidents(): Promise<Incident[]> {
    const res = await fetch(`${API_BASE_URL}/incidents`, {
      headers: getHeaders(false)
    });
    if (!res.ok) throw new Error('Failed to fetch incidents');
    return res.json();
  },

  async createIncident(incidentData: {
    title: string;
    description: string;
    severityLevel: number;
    affectedPeopleCount: number;
    timeSensitivityLevel: string;
    requiredResourceType: string;
    regionId: number;
    latitude: number;
    longitude: number;
    addressText: string;
  }): Promise<Incident> {
    const res = await fetch(`${API_BASE_URL}/incidents`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(incidentData)
    });
    if (!res.ok) throw new Error('Failed to create incident');
    return res.json();
  },

  // Hospitals
  async getHospitals(): Promise<Hospital[]> {
    const res = await fetch(`${API_BASE_URL}/hospitals`, {
      headers: getHeaders(false)
    });
    if (!res.ok) throw new Error('Failed to fetch hospitals');
    return res.json();
  },

  // Resources
  async getResources(): Promise<Resource[]> {
    const res = await fetch(`${API_BASE_URL}/resources`, {
      headers: getHeaders(false)
    });
    if (!res.ok) throw new Error('Failed to fetch resources');
    return res.json();
  },

  // Decision Engine & Dispatch Recommendations
  async getRecommendations(incidentId: number, maxResults = 5): Promise<DispatchRecommendation[]> {
    const res = await fetch(`${API_BASE_URL}/dispatch/recommendations`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify({ incidentId, maxResults })
    });
    if (!res.ok) throw new Error('Failed to compute dispatch recommendations');
    return res.json();
  },

  // Assignments
  async createAssignment(incidentId: number, resourceId: number, hospitalId: number): Promise<Assignment> {
    const res = await fetch(`${API_BASE_URL}/dispatch/assignments`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify({ incidentId, resourceId, hospitalId })
    });
    if (!res.ok) {
      const errJson = await res.json().catch(() => ({}));
      throw new Error(errJson.message || 'Failed to create emergency assignment');
    }
    return res.json();
  },

  async getAssignments(): Promise<Assignment[]> {
    const res = await fetch(`${API_BASE_URL}/dispatch/assignments`, {
      headers: getHeaders(false)
    });
    if (!res.ok) throw new Error('Failed to fetch active assignments');
    return res.json();
  },

  // Notifications
  async getNotifications(userId = 1): Promise<NotificationItem[]> {
    const res = await fetch(`${API_BASE_URL}/notifications/user/${userId}`, {
      headers: getHeaders(false)
    });
    if (!res.ok) throw new Error('Failed to fetch notifications');
    return res.json();
  },

  async markNotificationRead(id: number): Promise<NotificationItem> {
    const res = await fetch(`${API_BASE_URL}/notifications/${id}/read`, {
      method: 'PATCH',
      headers: getHeaders(false)
    });
    if (!res.ok) throw new Error('Failed to mark notification as read');
    return res.json();
  }
};
