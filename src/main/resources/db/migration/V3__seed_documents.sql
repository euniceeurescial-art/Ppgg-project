INSERT INTO document_types (name, description, processing_days, fee) VALUES
 ('Barangay Clearance','Proof of good standing in the barangay',3,50),
 ('Certificate of Residency','Proof of residence',2,30),
 ('Certificate of Indigency','For financial assistance',2,0);
INSERT INTO requirements (name, description) VALUES
 ('Valid ID','Any government-issued ID'),('Proof of Residency','Utility bill or similar'),('Application Form','Filled-out request form');
INSERT INTO document_type_requirements (document_type_id, requirement_id) VALUES (1,1),(1,3),(2,1),(2,2),(3,1),(3,2);
