
# Social API

This project is a simple social media API built with **Spring Boot**. It allows users to create, list, and manage posts and comments. The data is stored in an embedded **H2** database. The API is secured using an **API Key** mechanism.

---

## Features

- Create, read, delete posts
- Manage comments related to posts
- Filter posts by user
- Embedded H2 database for easy development and testing
- Simple API Key based authorization
- Basic security configured with Spring Security

---

## Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- Spring Security (API Key Authentication)
- H2 Database (in-memory)
- Maven

---

## Setup and Run

1. Clone the repository or download:
   ```bash
   git clone https://github.com/yourusername/social-api.git
   cd social-api
   ```

2. Build and run the project:
   ```bash
   ./mvnw spring-boot:run
   ```

3. The application will start on **http://localhost:8080**.

4. To access the H2 Console:
   - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (leave empty)

   > Note: The API Key security filter is disabled for the H2 console.

---

## API Usage

### API Key Requirement

All requests to `/api/**` endpoints must include a valid API key in the `X-API-KEY` header.

Example header:
```
X-API-KEY: your-api-key-here
```

### Sample Endpoints

- **POST /api/posts**

  Create a new post.

  Request body example:
  ```json
  {
    "title": "My Post Title",
    "content": "The content of the post.",
    "user": {
      "id": 1
    }
  }
  ```

- **GET /api/posts**

  Retrieve a paginated list of posts.

- **GET /api/posts/{id}**

  Get a post by its ID.

- **DELETE /api/posts/{id}**

  Delete a post by ID.

- **GET /api/posts/user/{userId}**

  Get posts by a specific user.

---

## Security

- The API is secured by a simple API Key filter.
- The filter is disabled for the H2 console access.
- This basic security is for development only and should be improved for production use.

---

## Development & Testing

- The database resets on every application restart (in-memory H2).
- Use tools like Postman to test API endpoints.

---

