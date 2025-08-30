# Fitness Microservices Project

A **production-oriented, event-driven fitness application** built using a microservices architecture. The system leverages an **API Gateway**, **service discovery**, **centralized config**, **OAuth2/OpenID Connect authentication**, **Kafka for asynchronous messaging**, and a dedicated **AI service** integrating with **Google’s Gemini** for intelligent features like workout insights and recommendations.

---

## Current Status
- Core project scaffold and microservice boundaries established.
- User registration flow completed and tested end-to-end via the Gateway, secured with Bearer tokens (Keycloak).
- Additional features (activities domain, AI workflows, and analytics) scaffolded and will be implemented iteratively.

---

## Architecture Overview

**High-level components:**
- **Gateway:** Single entry point for frontend/Postman requests, routes to internal services with JWT Bearer authentication.
- **Keycloak:** Identity and Access Management (IAM) for OAuth2/OIDC, issues tokens consumed by Gateway and services.
- **User Service:** Manages user profiles and registration; persists to relational DB (Postgres).
- **Activity Service:** Manages user activities; integrates with DB and produces domain events to Kafka.
- **Kafka:** Event backbone for inter-service communication (user and activity events).
- **AI Service:** Consumes events and calls Google Gemini for AI-powered features; persists AI artifacts/results.
- **Config Server:** Centralized configuration for all services.
- **Eureka (Service Discovery):** Registers/locates services for dynamic routing.

---

## Repository Structure
gateway/                  # API Gateway service
services/user/            # User microservice (registration implemented)
services/activity/        # Activity microservice
services/ai/              # AI microservice integrating with Google Gemini
platform/keycloak/        # Keycloak realm/config/bootstrap scripts
platform/config-server/   # Spring Cloud Config Server & configs
platform/eureka/          # Eureka server for service discovery
platform/kafka/           # Kafka/ZooKeeper or Redpanda setup
infra/dbs/                # Database manifests/docker for services
docs/                     # Architecture diagrams & Postman collections

> Folder names can be adjusted to match the actual repo; this reflects a typical Spring Cloud layout.

---

## Tech Stack
- **Spring Boot**, **Spring Cloud Gateway**, **Spring Security (Resource Server)**
- **Spring Cloud Config**, **Eureka (Netflix)**
- **Keycloak (OIDC/OAuth2)**
- **Kafka** (event streaming)
- **PostgreSQL** (core services)
- **Google Gemini** (AI features)
- **Docker/Docker Compose** (local orchestration)

---

## Getting Started

### Prerequisites
- JDK 17+
- Docker & Docker Compose
- Maven or Gradle
- Node.js (optional, if frontend will be connected later)

### Clone the repository
```bash
git clone https://github.com/<username>/Fitness-micro-services-project.git
cd Fitness-micro-services-project

docker compose -f platform/docker-compose.yml up -d


	Keycloak, Kafka, Postgres, Config Server, Eureka will start.
	•	Verify Keycloak: http://localhost:8080 (import realm if needed)
	•	Verify Eureka: http://localhost:8761
	•	Verify Config Server: http://localhost:8888

Run services (recommended order)
	1.	Config Server
	2.	Eureka
	3.	Gateway
	4.	User Service
	5.	Activity Service
	6.	AI Service

For each service:


cd <service-module>
mvn spring-boot:run
# or
./mvnw spring-boot:run

Environmental Variables

KEYCLOAK_ISSUER_URI
KEYCLOAK_CLIENT_ID
KEYCLOAK_CLIENT_SECRET
SPRING_PROFILES_ACTIVE=dev
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
DB_URL, DB_USER, DB_PASSWORD (per service)
GEMINI_API_KEY (for AI service)


Security & Authentication
	•	Managed by Keycloak (OAuth2/OIDC)
	•	Obtain a Bearer token via Keycloak token endpoint (public or confidential client)
	•	Pass Authorization: Bearer <token> to the Gateway
	•	Downstream services validate JWTs

⸻

User Registration Flow (Implemented)
	•	Endpoint: POST /api/user/registration (via Gateway → User Service)
	•	Request: JSON payload with user details (email, password, first_name, last_name)
	•	Behavior:
	•	Creates a Keycloak user (or maps existing subject)
	•	Persists domain user in User DB
	•	Emits UserRegistered event to Kafka (optional)
	•	Validation: Standard constraints & error responses for conflicts/invalid data
	•	Testing: Use Postman with Bearer token
	•	Obtain token from Keycloak
	•	POST http://localhost:<gateway-port>/api/user/registration
	•	Check User DB and Kafka topic for events (if enabled)

⸻

Local Development Tips
	•	Use profiles (dev, test, prod) managed via Config Server
	•	Prefer testcontainers for integration tests with Kafka & Postgres
	•	Use Flyway or Liquibase for schema migrations
	•	Adopt contract-first APIs with OpenAPI and generate Postman collections automatically

⸻

Roadmap
	•	Complete Activity domain: CRUD, metrics, event publishing
	•	AI features: consume activity/user events & call Gemini for personalized insights
	•	Observability: centralized logging, tracing (OpenTelemetry), metrics dashboards
	•	CI/CD: pipeline, quality gates, SAST/DAST, multi-environment deployment

⸻

Scripts & Utilities
	•	docker-compose files under platform/ to run infra in one command
	•	Makefile or task runner aliases for common workflows (build, run, test)

⸻

Contributing
	•	Follow conventional commits, feature branches, and pull requests
	•	Run full test suite before opening PR

⸻

License
	•	MIT or Apache-2.0 (choose and update LICENSE accordingly)

⸻

Acknowledgments
	•	Spring Cloud ecosystem
	•	Keycloak community
	•	Apache Kafka
	•	Google Gemini APIs

