# DevConnect : 

A full-stack developer portfolio and collaboration platform where developers can create professional profiles, showcase projects, write blogs, connect with other developers, and interact through likes and comments.

##  Features

### Authentication

* User registration and login
* JWT-based authentication
* Protected API endpoints
* Role-based authentication structure

### Developer Profiles

* Custom developer profiles
* Profile headline and bio
* Skills
* GitHub, LinkedIn and personal website links
* Profile avatar upload
* Edit profile functionality
* Account deletion

### Projects

* Create and showcase developer projects
* GitHub and live demo links
* Technology stack information
* Browse projects from other developers
* Project search
* Like and comment functionality
* Delete your own projects

### Blogs

* Create and publish developer blogs
* Browse blogs from developers
* Blog likes and comments
* Edit and delete your own blogs

### Search

* Search developers by username and skills
* Search projects by title, description and technology stack

##  Tech Stack

### Frontend

* React.js
* React Router
* Axios
* Tailwind CSS
* Lucide React

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT Authentication
* REST APIs

### Database

* PostgreSQL

##  Architecture

```text
React.js Frontend
        │
        │ REST API + JWT
        ▼
Spring Boot Backend
        │
        │ JPA / Hibernate
        ▼
PostgreSQL Database
```

##  Project Structure

```text
DevConnect/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── ...
│
├── frontend/
│   ├── src/
│   ├── package.json
│   └── ...
│
├── .gitignore
└── README.md
```

## Running Locally :

### Prerequisites

Make sure you have:

* Java 17+
* Maven
* Node.js
* npm
* PostgreSQL

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/devconnect.git
cd devconnect
```

### 2. Configure the backend

Navigate to the backend:

```bash
cd backend
```

Configure your PostgreSQL database and application settings in:

```text
src/main/resources/application.properties
```

Do not commit database passwords, JWT secrets, or other private credentials to GitHub.

### 3. Start the Spring Boot backend

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

### 4. Start the React frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

The frontend will normally be available at:

```text
http://localhost:5173
```

##  Authentication

DevConnect uses JWT authentication.

After login, the frontend stores the authentication token and sends it with protected API requests using the `Authorization` header:

```text
Authorization: Bearer <JWT>
```

##  Main API Areas

```text
/api/auth
/api/profiles
/api/projects
/api/blogs
/api/search
/api/users
/api/uploads
```

##  Future Improvements

* Real-time developer messaging
* Developer connection/follow system
* Notifications
* Advanced project filtering
* Pagination
* Cloud image storage
* Production deployment
* Improved recommendation system

##  Author

Built as a full-stack software engineering project using React, Spring Boot and PostgreSQL.
