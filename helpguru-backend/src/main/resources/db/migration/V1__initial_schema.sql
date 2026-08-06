-- =========================================================
-- HelpGuru Emergency Response Platform
-- Migration: V1__initial_schema.sql
-- Description: Complete Database Schema for Modular Monolith
-- Author: HelpGuru Architecture Team
-- =========================================================

-- Enable Extension for UUID Generation if needed
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =========================================================
-- 1. AUTH & USER MANAGEMENT MODULE SCHEMA
-- =========================================================

-- Roles Table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Regions Table (National Emergency Grid Divisions)
CREATE TABLE regions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    center_latitude DOUBLE PRECISION NOT NULL,
    center_longitude DOUBLE PRECISION NOT NULL,
    radius_km DOUBLE PRECISION DEFAULT 50.0 NOT NULL,
    status VARCHAR(30) DEFAULT 'ACTIVE' NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    phone_number VARCHAR(30),
    region_id BIGINT REFERENCES regions(id) ON DELETE SET NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE,
    version BIGINT DEFAULT 0 NOT NULL, -- Optimistic locking
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- User Roles Mapping Table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


-- =========================================================
-- 2. HOSPITAL MODULE SCHEMA
-- =========================================================

CREATE TABLE hospitals (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    region_id BIGINT NOT NULL REFERENCES regions(id),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    total_beds INT NOT NULL DEFAULT 0,
    available_beds INT NOT NULL DEFAULT 0,
    icu_total INT NOT NULL DEFAULT 0,
    icu_available INT NOT NULL DEFAULT 0,
    emergency_contact VARCHAR(50),
    status VARCHAR(30) DEFAULT 'OPERATIONAL' NOT NULL, -- OPERATIONAL, FULL_CAPACITY, DERT
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    version BIGINT DEFAULT 0 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_hospital_beds CHECK (available_beds <= total_beds),
    CONSTRAINT chk_hospital_icu CHECK (icu_available <= icu_total)
);


-- =========================================================
-- 3. RESOURCE MODULE SCHEMA
-- =========================================================

-- Emergency Resources Table (Ambulances, Rescue Teams, Helicopters, Fire Units, Police)
CREATE TABLE resources (
    id BIGSERIAL PRIMARY KEY,
    resource_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    resource_type VARCHAR(50) NOT NULL, -- AMBULANCE, HELICOPTER, RESCUE_TEAM, FIRE_TRUCK, POLICE_UNIT
    region_id BIGINT NOT NULL REFERENCES regions(id),
    hospital_id BIGINT REFERENCES hospitals(id) ON DELETE SET NULL,
    current_latitude DOUBLE PRECISION NOT NULL,
    current_longitude DOUBLE PRECISION NOT NULL,
    status VARCHAR(30) DEFAULT 'AVAILABLE' NOT NULL, -- AVAILABLE, DISPATCHED, ON_SCENE, MAINTENANCE, UNVALLABLE
    capacity INT DEFAULT 1 NOT NULL,
    speed_kmh DOUBLE PRECISION DEFAULT 60.0 NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    version BIGINT DEFAULT 0 NOT NULL, -- Critical for Optimistic Locking & Race Condition Prevention
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Vehicles Detail Table
CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    resource_id BIGINT NOT NULL UNIQUE REFERENCES resources(id) ON DELETE CASCADE,
    license_plate VARCHAR(50) NOT NULL UNIQUE,
    model VARCHAR(100),
    fuel_level_percent DOUBLE PRECISION DEFAULT 100.0,
    equipment_level VARCHAR(50) DEFAULT 'ADVANCED_LIFE_SUPPORT',
    last_serviced_at TIMESTAMP WITH TIME ZONE
);


-- =========================================================
-- 4. INCIDENT MODULE SCHEMA
-- =========================================================

CREATE TABLE incidents (
    id BIGSERIAL PRIMARY KEY,
    incident_code VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    severity_level INT NOT NULL CHECK (severity_level BETWEEN 1 AND 10), -- 1 (Low) to 10 (Critical)
    affected_people_count INT DEFAULT 1 NOT NULL,
    time_sensitivity_level VARCHAR(30) DEFAULT 'HIGH' NOT NULL, -- CRITICAL, HIGH, MEDIUM, LOW
    required_resource_type VARCHAR(50) NOT NULL,
    region_id BIGINT NOT NULL REFERENCES regions(id),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    address_text TEXT,
    status VARCHAR(30) DEFAULT 'REPORTED' NOT NULL, -- REPORTED, EVALUATING, ASSIGNED, IN_PROGRESS, RESOLVED, CANCELLED
    reported_by_user_id BIGINT REFERENCES users(id),
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    version BIGINT DEFAULT 0 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);


-- =========================================================
-- 5. DISPATCH & ASSIGNMENT MODULE SCHEMA
-- =========================================================

CREATE TABLE assignments (
    id BIGSERIAL PRIMARY KEY,
    assignment_code VARCHAR(50) NOT NULL UNIQUE,
    incident_id BIGINT NOT NULL REFERENCES incidents(id) ON DELETE RESTRICT,
    resource_id BIGINT NOT NULL REFERENCES resources(id) ON DELETE RESTRICT,
    assigned_by_user_id BIGINT REFERENCES users(id),
    status VARCHAR(30) DEFAULT 'DISPATCHED' NOT NULL, -- DISPATCHED, EN_ROUTE, ARRIVED, COMPLETED, CANCELLED, REASSIGNED
    priority_score DOUBLE PRECISION NOT NULL,
    estimated_travel_time_minutes INT NOT NULL,
    estimated_distance_km DOUBLE PRECISION NOT NULL,
    dispatched_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    arrived_at TIMESTAMP WITH TIME ZONE,
    completed_at TIMESTAMP WITH TIME ZONE,
    version BIGINT DEFAULT 0 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Unique Constraint to prevent double dispatching active assignments to the same incident
CREATE UNIQUE INDEX idx_uniq_active_incident_assignment 
ON assignments (incident_id) 
WHERE status IN ('DISPATCHED', 'EN_ROUTE', 'ARRIVED');


-- =========================================================
-- 6. ENVIRONMENTAL MONITORING SCHEMA (WEATHER & ROAD)
-- =========================================================

CREATE TABLE weather_snapshots (
    id BIGSERIAL PRIMARY KEY,
    region_id BIGINT NOT NULL REFERENCES regions(id),
    condition VARCHAR(50) NOT NULL, -- CLEAR, RAIN, HEAVY_STORM, CYCLONE, FOG
    risk_factor DOUBLE PRECISION DEFAULT 1.0 NOT NULL, -- Multiplier for travel time scoring
    wind_speed_kmh DOUBLE PRECISION DEFAULT 0.0,
    recorded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE road_conditions (
    id BIGSERIAL PRIMARY KEY,
    region_id BIGINT NOT NULL REFERENCES regions(id),
    road_name VARCHAR(150) NOT NULL,
    start_latitude DOUBLE PRECISION NOT NULL,
    start_longitude DOUBLE PRECISION NOT NULL,
    end_latitude DOUBLE PRECISION NOT NULL,
    end_longitude DOUBLE PRECISION NOT NULL,
    is_closed BOOLEAN DEFAULT FALSE NOT NULL,
    delay_multiplier DOUBLE PRECISION DEFAULT 1.0 NOT NULL,
    reason VARCHAR(255),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);


-- =========================================================
-- 7. GPS TELEMETRY & TRACKING SCHEMA
-- =========================================================

CREATE TABLE gps_tracking_logs (
    id BIGSERIAL PRIMARY KEY,
    resource_id BIGINT NOT NULL REFERENCES resources(id) ON DELETE CASCADE,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    speed_kmh DOUBLE PRECISION DEFAULT 0.0,
    heading_degrees DOUBLE PRECISION DEFAULT 0.0,
    recorded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);


-- =========================================================
-- 8. NOTIFICATION & AUDIT MODULE SCHEMA
-- =========================================================

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    recipient_user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) DEFAULT 'ALERT' NOT NULL,
    is_read BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL, -- INCIDENT_CREATED, ASSIGNMENT_DISPATCHED, RESOURCE_REALLOCATED, HOSPITAL_FULL
    entity_name VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    action_by_user_id BIGINT,
    details_json TEXT, -- JSON payload of the event snapshot
    ip_address VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE system_events (
    id BIGSERIAL PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    source_module VARCHAR(50) NOT NULL,
    payload_json TEXT NOT NULL,
    occurred_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);


-- =========================================================
-- 9. PERFORMANCE INDEXES
-- =========================================================

-- Geospatial Proximity Query Indexes
CREATE INDEX idx_resources_geo ON resources(current_latitude, current_longitude, status);
CREATE INDEX idx_hospitals_geo ON hospitals(latitude, longitude, status);
CREATE INDEX idx_incidents_geo ON incidents(latitude, longitude, status);
CREATE INDEX idx_gps_tracking_resource_time ON gps_tracking_logs(resource_id, recorded_at DESC);

-- Module Query Optimization Indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_incidents_region_status ON incidents(region_id, status);
CREATE INDEX idx_resources_region_status ON resources(region_id, status);
CREATE INDEX idx_assignments_incident_resource ON assignments(incident_id, resource_id);
CREATE INDEX idx_audit_logs_event_entity ON audit_logs(event_type, entity_name, entity_id);

-- Seed Default Roles
INSERT INTO roles (name, description) VALUES
('ROLE_ADMIN', 'National System Administrator with full access'),
('ROLE_OPERATOR', 'Regional Command Center Dispatcher'),
('ROLE_RESPONDER', 'Field Unit Emergency Responder'),
('ROLE_HOSPITAL_ADMIN', 'Hospital Staff Capacity Manager');

-- Seed Default Master Region (Dhaka National HQ)
INSERT INTO regions (code, name, center_latitude, center_longitude, radius_km) VALUES
('REG-DHAKA', 'Dhaka Metropolitan Region', 23.8103, 90.4125, 60.0),
('REG-BARISHAL', 'Barishal Coastal Region', 22.7010, 90.3535, 80.0),
('REG-SYLHET', 'Sylhet Northeast Region', 24.8949, 91.8687, 75.0),
('REG-CTG', 'Chittagong Southeast Region', 22.3569, 91.7832, 90.0);
