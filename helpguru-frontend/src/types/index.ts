export type Role = 'ROLE_ADMIN' | 'ROLE_OPERATOR' | 'ROLE_HOSPITAL_ADMIN' | 'ROLE_CITIZEN';

export interface User {
  userId: number;
  username: string;
  email: string;
  fullName: string;
  roles: Role[];
}

export interface AuthResponse {
  token: string;
  tokenType: string;
  userId: number;
  username: string;
  email: string;
  fullName: string;
  roles: Role[];
}

export type IncidentStatus = 'REPORTED' | 'VERIFIED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'CANCELLED';
export type ResourceType = 'AMBULANCE' | 'RESCUE_BOAT' | 'FIRE_TRUCK' | 'HELICOPTER' | 'MEDICAL_TEAM';

export interface Incident {
  id: number;
  incidentCode: string;
  title: string;
  description: string;
  severityLevel: number; // 1 to 10
  affectedPeopleCount: number;
  timeSensitivityLevel: 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
  requiredResourceType: ResourceType;
  regionId: number;
  latitude: number;
  longitude: number;
  addressText: string;
  status: IncidentStatus;
  reportedByUserId: number;
  createdAt: string;
  updatedAt: string;
}

export type HospitalStatus = 'OPERATIONAL' | 'FULL' | 'OFFLINE';

export interface Hospital {
  id: number;
  name: string;
  code: string;
  regionId: number;
  latitude: number;
  longitude: number;
  totalBeds: number;
  availableBeds: number;
  icuTotal: number;
  icuAvailable: number;
  emergencyContact: string;
  status: HospitalStatus;
  createdAt: string;
  updatedAt: string;
}

export type ResourceStatus = 'AVAILABLE' | 'DISPATCHED' | 'ON_SCENE' | 'MAINTENANCE' | 'OFFLINE';

export interface Resource {
  id: number;
  resourceCode: string;
  name: string;
  resourceType: ResourceType;
  regionId: number;
  hospitalId?: number;
  currentLatitude: number;
  currentLongitude: number;
  status: ResourceStatus;
  capacity: number;
  speedKmh: number;
  createdAt: string;
  updatedAt: string;
}

export interface DispatchRecommendation {
  resourceId: number;
  resourceCode: string;
  resourceName: string;
  resourceType: ResourceType;
  currentLatitude: number;
  currentLongitude: number;
  distanceKm: number;
  estimatedEtaMinutes: number;
  objectiveScore: number;
  nearestHospitalId?: number;
  nearestHospitalName?: string;
  nearestHospitalAvailableBeds?: number;
  speedKmh: number;
}

export type AssignmentStatus = 'DISPATCHED' | 'EN_ROUTE' | 'ON_SCENE' | 'COMPLETED' | 'CANCELLED';

export interface Assignment {
  id: number;
  assignmentCode: string;
  incident: Incident;
  resource: Resource;
  hospital?: Hospital;
  calculatedScore: number;
  estimatedEtaMinutes: number;
  assignedByUserId: number;
  status: AssignmentStatus;
  createdAt: string;
  updatedAt: string;
}

export interface NotificationItem {
  id: number;
  recipientUserId: number;
  title: string;
  message: string;
  channel: 'WEBSOCKET' | 'SMS' | 'EMAIL' | 'PUSH';
  status: 'PENDING' | 'SENT' | 'DELIVERED' | 'FAILED';
  referenceType?: string;
  referenceId?: number;
  isRead: boolean;
  createdAt: string;
}
