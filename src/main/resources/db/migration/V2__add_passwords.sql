ALTER TABLE citizens ADD COLUMN password_hash VARCHAR(100);
ALTER TABLE admins ADD COLUMN password_hash VARCHAR(100);
