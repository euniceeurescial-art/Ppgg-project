# iBarangay – Barangay Document Request System (backend)
Java 21, Spring Boot 3, PostgreSQL 18, Flyway.

## Run
1. Create the database: `createdb ppgg_projectdb` (or set `DB_NAME`).
2. Set credentials: `export DB_USERNAME=postgres DB_PASSWORD=<your password>` (Windows: `set`).
3. `mvn spring-boot:run` (or `./mvnw spring-boot:run` if the wrapper is present). Flyway builds the 7 tables on first start.

## Demo accounts (fictional, created on first run)
admin / Admin@123 (ADMIN) · staff / Staff@123 (STAFF) · juan@example.com / Citizen@123 (CITIZEN)

## Try the API (curl)
```
curl -c c.txt -d "username=juan@example.com&password=Citizen@123" localhost:8080/api/login
curl localhost:8080/api/document-types
curl -b c.txt -H "Content-Type: application/json" -d '{"documentTypeId":1,"purpose":"Job application"}' localhost:8080/api/citizen/requests
curl -b c.txt localhost:8080/api/citizen/requests
curl -c a.txt -d "username=admin&password=Admin@123" localhost:8080/api/login
curl -b a.txt localhost:8080/api/admin/requests
curl -b a.txt -X PUT -H "Content-Type: application/json" -d '{"status":"APPROVED"}' localhost:8080/api/admin/requests/1/status
```
## Status flow
PENDING → APPROVED/REJECTED/CANCELLED → READY → RELEASED. Rejecting needs remarks.

## Changes from Activity 2
- V2 adds `password_hash` to `citizens` and `admins` (needed for login).
- `request_status_history.admin_id` is NOT NULL, so history starts at the first admin action (old_status = PENDING).
## Not yet done
HTML/CSS/JS pages, announcements, complaints.
