# Spring CRM REST API

A comprehensive Customer Relationship Management (CRM) REST API built with Spring Boot 3.x, featuring user management, contact management, account management, task tracking, lead management, opportunity tracking, and campaign management with JWT authentication and role-based authorization.

## Features

### Core Functionality
- **User Management**: Registration, authentication, role-based access control
- **Contact Management**: CRUD operations for contacts
- **Account Management**: Business account management with detailed information
- **Task Management**: Task creation, assignment, and tracking
- **Lead Management**: Lead tracking and conversion
- **Opportunity Management**: Sales opportunity tracking
- **Campaign Management**: Marketing campaign management
- **Email Management**: Email communication tracking
- **Notification System**: User notifications and alerts

### Security
- **JWT Authentication**: Secure token-based authentication
- **Role-Based Authorization**: Admin, Manager, User, and Guest roles
- **Password Hashing**: BCrypt password encryption
- **CORS Support**: Cross-origin resource sharing configuration

### Observability & Monitoring
- **Prometheus Metrics**: Application metrics collection
- **Grafana Dashboards**: Visual monitoring and alerting
- **Loki Log Aggregation**: Centralized log management
- **Spring Boot Actuator**: Health checks and metrics endpoints
- **Jaeger Tracing**: Distributed request tracing

### Documentation
- **Swagger/OpenAPI**: Interactive API documentation
- **Comprehensive DTOs**: Well-defined request/response objects
- **Validation**: Input validation with Hibernate Validator

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: Microsoft SQL Server
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with JWT
- **Documentation**: Swagger/OpenAPI 3
- **Monitoring**: Prometheus, Grafana, Loki
- **Build Tool**: Maven
- **Testing**: JUnit 5, TestContainers

## Project Structure

```
spring-crm/
├── src/main/java/com/springcrm/
│   ├── config/              # Configuration classes
│   ├── controllers/         # REST controllers
│   ├── services/           # Business logic services
│   ├── repositories/       # JPA repositories
│   ├── models/            # JPA entities
│   ├── security/          # Security configuration
│   ├── dto/               # Data transfer objects
│   └── exceptions/        # Custom exceptions
├── src/main/resources/
│   └── application.yml    # Application configuration
├── monitoring/            # Telemetry configuration
│   ├── prometheus.yml
│   ├── loki.yml
│   ├── promtail.yml
│   └── grafana/
└── docker-compose.telemetry.yml
```

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Microsoft SQL Server
- Docker and Docker Compose (for telemetry stack)

### 1. Database Setup
1. Install and configure Microsoft SQL Server
2. Create a database named `maindb`
3. Update database connection details in `application.yml`

### 2. Application Configuration
Update `src/main/resources/application.yml` with your configuration:

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=maindb;trustServerCertificate=true;encrypt=false
    username: your_username
    password: your_password
  security:
    jwt:
      secret: your_jwt_secret_key
```

### 3. Build and Run
```bash
# Build the application
mvn clean package

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access Points
- **API Base URL**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`
- **Actuator Health**: `http://localhost:8080/api/actuator/health`
- **Prometheus Metrics**: `http://localhost:8080/api/actuator/prometheus`

## Telemetry Stack Setup

### Start Monitoring Stack
```bash
# Start Prometheus, Grafana, Loki, and Jaeger
docker-compose -f docker-compose.telemetry.yml up -d
```

### Access Monitoring Tools
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **Jaeger**: http://localhost:16686
- **Loki**: http://localhost:3100

## API Endpoints

### Authentication
- `POST /api/users/login` - User login
- `POST /api/users/register` - User registration

### User Management
- `GET /api/users` - Get all users (Admin/Manager)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user (Admin/Manager)
- `DELETE /api/users/{id}` - Delete user (Admin)

### Contact Management
- `GET /api/contacts` - Get all contacts
- `POST /api/contacts` - Create contact
- `GET /api/contacts/{id}` - Get contact by ID
- `PUT /api/contacts/{id}` - Update contact
- `DELETE /api/contacts/{id}` - Delete contact

### Account Management
- `GET /api/accounts` - Get all accounts
- `POST /api/accounts` - Create account
- `GET /api/accounts/{id}` - Get account by ID
- `PUT /api/accounts/{id}` - Update account
- `DELETE /api/accounts/{id}` - Delete account

### Task Management
- `GET /api/tasks` - Get all tasks
- `POST /api/tasks` - Create task
- `GET /api/tasks/{id}` - Get task by ID
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Lead Management
- `GET /api/leads` - Get all leads
- `POST /api/leads` - Create lead
- `GET /api/leads/{id}` - Get lead by ID
- `PUT /api/leads/{id}` - Update lead
- `DELETE /api/leads/{id}` - Delete lead

### Opportunity Management
- `GET /api/opps` - Get all opportunities
- `POST /api/opps` - Create opportunity
- `GET /api/opps/{id}` - Get opportunity by ID
- `PUT /api/opps/{id}` - Update opportunity
- `DELETE /api/opps/{id}` - Delete opportunity

### Campaign Management
- `GET /api/campaigns` - Get all campaigns
- `POST /api/campaigns` - Create campaign
- `GET /api/campaigns/{id}` - Get campaign by ID
- `PUT /api/campaigns/{id}` - Update campaign
- `DELETE /api/campaigns/{id}` - Delete campaign

## Authentication & Authorization

### JWT Token
The API uses JWT tokens stored in HTTP-only cookies for authentication. Tokens include:
- User ID
- Role
- Tenant ID
- Expiration time (24 hours)

### Role-Based Access Control
- **Admin**: Full access to all operations
- **Manager**: Access to most operations except user deletion
- **User**: Limited access to assigned resources
- **Guest**: Read-only access

### Middleware Equivalents
The Spring Boot implementation replicates the Node.js middleware:
- `userAuth` → `@PreAuthorize("hasRole('USER')")`
- `adminAuth` → `@PreAuthorize("hasRole('ADMIN')")`
- `fuseAuth` → `@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")`

## Database Schema

The application uses the following main entities:
- **Users**: System users with roles and tenant association
- **Tenants**: Multi-tenant support
- **Accounts**: Business accounts/customers
- **Contacts**: Individual contacts associated with accounts
- **Tasks**: Task management and tracking
- **Leads**: Potential customer leads
- **Opportunities**: Sales opportunities
- **Campaigns**: Marketing campaigns
- **Emails**: Email communication tracking
- **Notifications**: User notifications

## Monitoring & Metrics

### Prometheus Metrics
- `crm_api_requests_total` - Total API requests
- `crm_api_response_time` - API response times
- `crm_auth_attempts_total` - Authentication attempts
- `crm_successful_logins_total` - Successful logins
- `crm_errors_total` - Error count
- Custom business metrics for each entity type

### Grafana Dashboards
- API performance metrics
- User activity tracking
- Business operation metrics
- Error rate monitoring
- System health indicators

## Development

### Running Tests
```bash
# Run all tests
mvn test

# Run integration tests
mvn verify
```

### Code Quality
- Follow Spring Boot best practices
- Use proper validation annotations
- Implement comprehensive error handling
- Write unit and integration tests

## Production Deployment

### Environment Variables
Set the following environment variables:
- `DB_USER` - Database username
- `DB_PASSWORD` - Database password
- `DB_SERVER` - Database server host
- `JWT_SECRET` - JWT signing secret
- `MAIL_HOST` - SMTP server host
- `MAIL_USERNAME` - SMTP username
- `MAIL_PASSWORD` - SMTP password

### Docker Deployment
```bash
# Build Docker image
docker build -t spring-crm .

# Run with Docker Compose
docker-compose up -d
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue in the repository
- Contact: support@crm.com
- Documentation: https://crm.com/docs
