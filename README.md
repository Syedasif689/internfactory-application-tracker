# ApplyTrack – InternFactory Application Tracker

**Domain:** Software Development and Full-Stack Engineering  
**Project Type:** Full-Stack Web Application  
**Purpose:** Internship Application Management System

## 1. PROJECT OVERVIEW

ApplyTrack is a full-stack web application developed to help InternFactory administrators review and manage internship applications efficiently.

The application imports 50 practice applications from a CSV file into a MySQL database. Administrators can view applicant information, search applications, filter them by preferred domain, open individual applicant details and update application statuses.

The main objective is to reduce manual application-review work through a simple, organized and functional web-based dashboard.

## 2. TECHNOLOGY STACK

| Layer | Technologies |
|---|---|
| Frontend | HTML5, CSS3, JavaScript |
| Backend | Java, Spring Boot |
| Database | MySQL |
| ORM | Spring Data JPA, Hibernate |
| CSV Processing | OpenCSV |
| API | REST APIs |
| Build Tool | Maven |
| Development Environment | Visual Studio Code / Eclipse |

## 3. FEATURES IMPLEMENTED

### Core Features

1. **CSV Data Import**
   - Imports 50 internship applications from the provided CSV file.
   - Stores applicant information in MySQL.
   - Uses application ID as the unique identifier.
   - Avoids importing duplicate application IDs.

2. **Application Dashboard**
   - Displays applicant name, college, academic year, preferred domain and application status.
   - Presents applications in an organized table.

3. **Search Functionality**
   - Search applications using applicant name or college name.
   - Supports case-insensitive searching.

4. **Domain Filtering**
   - Filters applications according to the applicant's preferred domain.

5. **Applicant Details**
   - Provides an individual details page for each application.
   - Displays personal, academic and technical information.

6. **Application Status Management**
   - Supports three application statuses:
     - NEW
     - SHORTLISTED
     - REJECTED
   - Updated statuses are saved in the database and remain unchanged after refreshing the page.

### Bonus Features

1. **Application Status Statistics**
   - Displays total applications.
   - Displays the number of new, shortlisted and rejected applications.

2. **Responsive Interface**
   - Includes responsive styling to support smaller screen sizes.

## 4. PROJECT ARCHITECTURE

The application follows a simple three-layer architecture:

- **Presentation Layer:** HTML, CSS and JavaScript provide the dashboard and applicant details interface.
- **Application Layer:** Spring Boot controllers expose REST APIs and handle application-related operations.
- **Data Layer:** Spring Data JPA and Hibernate communicate with the MySQL database.

### Application Flow

1. The CSV file is loaded by the CSV importer.
2. Applicant records are stored in MySQL.
3. The frontend requests application data through REST APIs.
4. Spring Boot retrieves the required records from the database.
5. The dashboard displays the returned information.
6. When an administrator changes an application status, the backend updates the database.

## 5. HOW TO RUN THE PROJECT

### Prerequisites

Install the following software:

- Java JDK 25
- MySQL Server 8.0 or compatible version
- Git (optional, for cloning the repository)
- A web browser

### Step 1: Clone the Repository

```bash
git clone https://github.com/Syedasif689/internfactory-application-tracker
cd application-tracker
```

Replace the repository URL with your actual GitHub repository URL.

### Step 2: Create the Database

Open MySQL Workbench or the MySQL command line and execute:

```sql
CREATE DATABASE internfactory_tracker;
```

### Step 3: Configure Database Credentials

Open:

`src/main/resources/application.properties`

Configure your MySQL connection:

```properties
spring.application.name=application-tracker

spring.datasource.url=jdbc:mysql://localhost:3306/internfactory_tracker
spring.datasource.username=root
spring.datasource.password=MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password.

### Step 4: Check the CSV File

Ensure the provided CSV file is located at:

`src/main/resources/applications.csv`

The application uses this file to import the practice application records.

### Step 5: Run the Application

On Windows, open a terminal in the project root directory and execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, run the main Spring Boot application class from your IDE.

### Step 6: Open the Application

Open your browser and visit:

`http://localhost:8080`

The application dashboard should appear.

**Note:** The CSV importer skips application IDs that already exist in the database. If you want to perform a fresh import, use an empty database.

## 6. DATABASE DESIGN

The application uses a MySQL table named `applications`.

The primary key is `application_id`.

The table stores information such as:

- Application ID
- Submission date
- Full name
- Email
- Mobile number
- College
- Degree program
- Academic year
- Preferred domain
- Technical skills
- Resume link
- Statement of interest
- Application status

The database is managed through Spring Data JPA and Hibernate.

## 7. IMPORTANT DESIGN CHOICES

**1. MySQL as the database**

MySQL was selected because it is a reliable relational database and integrates well with Spring Boot and Spring Data JPA.

**2. Spring Boot REST APIs**

The backend uses REST APIs to separate application logic from the frontend and make communication straightforward.

**3. OpenCSV for importing data**

OpenCSV was selected to read CSV records and map them into Java application entities.

**4. Application ID as the primary key**

Application IDs uniquely identify records. Email addresses were not used as unique identifiers because different applications may contain repeated email addresses.

**5. Simple frontend technologies**

HTML, CSS and JavaScript were selected to build a lightweight interface without introducing an additional frontend framework.

## 8. WHAT I WOULD DO WITH MORE TIME

If given more development time, I would improve the application by adding:

- Secure administrator authentication and authorization.
- Free cloud deployment.
- Pagination for handling larger application datasets.
- Export functionality for shortlisted and rejected applications.
- More comprehensive input validation and error handling.
- Unit and integration tests.
- Improved dashboard analytics and application reporting.

## 9. AI USAGE

AI assistants were used as development-support tools during this project.

**Where AI assistance was used:**

- Understanding Spring Boot concepts and project structure.
- Getting guidance on entity, repository and controller implementation.
- Troubleshooting compilation errors and database connection issues.
- Improving frontend layout and responsive styling.
- Reviewing API integration and application functionality.
- Preparing project documentation.

AI was used as an assistant, not as a replacement for understanding the implementation. The project was run and tested locally, and I am prepared to explain the architecture, implementation choices and functionality during the interview.

## 10. PROJECT OUTCOME

The completed application successfully imports all 50 practice applications into MySQL and provides the required application-review functionality.

It supports searching, domain filtering, viewing individual application details, updating application statuses and displaying status statistics.

The project demonstrates the integration of frontend technologies, Java Spring Boot, REST APIs and relational database management in a practical full-stack application.

---

**Developed for:** InternFactory Private Limited  
**Domain:** Software Development and Full-Stack Engineering  
**Project:** ApplyTrack – Application Tracker  
**Developer:** Syed Asif