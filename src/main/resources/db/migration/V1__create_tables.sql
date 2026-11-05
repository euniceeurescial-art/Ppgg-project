CREATE TABLE citizens (
  citizen_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL, middle_name VARCHAR(50), last_name VARCHAR(50) NOT NULL,
  date_of_birth DATE, address VARCHAR(255) NOT NULL, contact_no VARCHAR(20) NOT NULL,
  email VARCHAR(100) UNIQUE, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE admins (
  admin_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL, username VARCHAR(50) NOT NULL UNIQUE, role VARCHAR(30) NOT NULL,
  is_active BOOLEAN NOT NULL DEFAULT TRUE, created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE document_types (
  document_type_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE, description TEXT,
  processing_days INTEGER NOT NULL CHECK (processing_days > 0),
  fee NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (fee >= 0), is_active BOOLEAN NOT NULL DEFAULT TRUE);
CREATE TABLE requirements (
  requirement_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE, description TEXT, is_active BOOLEAN NOT NULL DEFAULT TRUE);
CREATE TABLE document_type_requirements (
  document_type_id BIGINT NOT NULL REFERENCES document_types(document_type_id),
  requirement_id BIGINT NOT NULL REFERENCES requirements(requirement_id),
  is_required BOOLEAN NOT NULL DEFAULT TRUE, PRIMARY KEY (document_type_id, requirement_id));
CREATE TABLE document_requests (
  request_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  citizen_id BIGINT NOT NULL REFERENCES citizens(citizen_id),
  document_type_id BIGINT NOT NULL REFERENCES document_types(document_type_id),
  request_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, purpose VARCHAR(255) NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'PENDING', remarks TEXT,
  approved_by BIGINT REFERENCES admins(admin_id), processed_at TIMESTAMP, released_at TIMESTAMP,
  CONSTRAINT chk_request_status CHECK (status IN ('PENDING','APPROVED','REJECTED','READY','RELEASED','CANCELLED')),
  CONSTRAINT chk_rejected_has_remarks CHECK (status <> 'REJECTED' OR remarks IS NOT NULL),
  CONSTRAINT chk_release_after_request CHECK (released_at IS NULL OR released_at >= request_date));
CREATE TABLE request_status_history (
  history_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  request_id BIGINT NOT NULL REFERENCES document_requests(request_id),
  admin_id BIGINT NOT NULL REFERENCES admins(admin_id),
  old_status VARCHAR(20), new_status VARCHAR(20) NOT NULL,
  changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, remarks TEXT,
  CHECK (new_status IN ('PENDING','APPROVED','REJECTED','READY','RELEASED','CANCELLED')));
