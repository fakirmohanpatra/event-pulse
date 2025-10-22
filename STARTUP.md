# Event Pulse Startup Guide

Welcome to Event Pulse! This guide will help you set up and run the real-time event management system from scratch. As a Spring Boot application, it uses MongoDB for data storage, Kafka for notifications, and JWT for authentication.

## Prerequisites

Before starting, ensure you have the following installed on your system:

### 1. Java 17
- Download from [Oracle JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://openjdk.java.net/install/).
- Verify: Open a terminal and run `java -version`. You should see Java 17.x.x.

### 2. Maven 3.6+
- Download from [Maven Apache](https://maven.apache.org/download.cgi).
- Add to PATH: Follow the installation guide to set up environment variables.
- Verify: Run `mvn -version` in terminal.

### 3. MongoDB
- Download from [MongoDB Community Server](https://www.mongodb.com/try/download/community).
- Install and start MongoDB on default port 27017.
- Verify: Run `mongod --version` or check if MongoDB is running (it starts automatically on install).

### 4. Kafka
- Download Kafka binary from [Apache Kafka](https://kafka.apache.org/downloads) (e.g., kafka_2.13-3.7.0.zip).
- Extract to a folder like `C:\kafka` (Windows) or `/opt/kafka` (Linux/Mac).

## Step-by-Step Setup and Run

### Step 1: Clone or Navigate to the Project
- If not already done, clone the repository: `git clone <repo-url>`
- Navigate to the project folder: `cd event-pulse`

### Step 2: Start MongoDB
- Ensure MongoDB is running on localhost:27017.
- On Windows: Start MongoDB service from Services or run `mongod` in a terminal.
- On Linux/Mac: Run `sudo systemctl start mongod` or `mongod`.
- Verify: Open a new terminal and run `mongo` or `mongosh` to connect.

### Step 3: Start Kafka in KRaft Mode
Kafka needs to run in KRaft mode (no Zookeeper required).

1. Open a terminal and navigate to your Kafka folder (e.g., `cd C:\kafka\kafka_2.13-3.7.0`).
2. Generate a Cluster ID (one-time):
   ```
   bin\windows\kafka-storage.bat random-uuid
   ```
   - Copy the generated UUID.
3. Format Storage:
   ```
   bin\windows\kafka-storage.bat format -t <UUID-from-step-2> -c config\kraft\server.properties
   ```
4. Start Kafka Server:
   ```
   bin\windows\kafka-server-start.bat config\kraft\server.properties
   ```
   - This starts Kafka on localhost:9092. Leave this terminal open.
5. Verify: In a new terminal:
   ```
   bin\windows\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092
   bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
   ```
   - You should see the "test" topic.

**Note**: On Linux/Mac, use `bin/kafka-*.sh` instead of `bin\windows\kafka-*.bat`.

### Step 4: Build and Run the Application
1. In the project root (event-pulse), clean and install dependencies:
   ```
   mvn clean install
   ```
   - This compiles the code and runs tests. Should show "BUILD SUCCESS".
2. Run the Spring Boot app:
   ```
   mvn spring-boot:run
   ```
   - The app starts on http://localhost:8080. Leave this terminal open.

### Step 5: Test the Application
Use a browser or tools like Postman/curl for testing.

#### 1. Test Public Endpoint
- Visit: http://localhost:8080/hello
- Expected: "Hello, World!" (no login required).

#### 2. Register a User
- Use Postman: POST to http://localhost:8080/auth/register
- Body (JSON):
  ```json
  {
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }
  ```
- Expected: User object returned.

#### 3. Login and Get JWT Token
- POST to http://localhost:8080/auth/login
- Body:
  ```json
  {
    "username": "testuser",
    "password": "password123"
  }
  ```
- Expected: JSON with "token" field. Copy the token.

#### 4. Create an Event
- POST to http://localhost:8080/events
- Headers: `Authorization: Bearer <your-token>`
- Body:
  ```json
  {
    "title": "Sample Event",
    "description": "This is a test event",
    "category": "Tech",
    "date": "2025-10-25T10:00:00"
  }
  ```
- Expected: Event object returned. Kafka sends a notification to "event-notifications" topic.

#### 5. List Events
- GET to http://localhost:8080/events
- Headers: `Authorization: Bearer <your-token>`
- Expected: Array of events.

#### 6. Subscribe to an Event
- POST to http://localhost:8080/subscriptions/event/{eventId}
- Replace {eventId} with an actual event ID from step 4.
- Headers: `Authorization: Bearer <your-token>`
- Expected: Subscription object.

#### 7. Check Kafka Messages (Optional)
- In a new terminal, consume messages:
  ```
  bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic event-notifications --from-beginning
  ```
- Update an event (PUT to /events/{id}) and see notifications appear.

## Troubleshooting

- **Port Issues**: Ensure ports 27017 (MongoDB), 9092 (Kafka), and 8080 (App) are free.
- **Build Failures**: Run `mvn clean` then `mvn install`.
- **Kafka Errors**: Ensure KRaft mode is set up correctly. Try restarting Kafka.
- **App Won't Start**: Check logs in the terminal for errors (e.g., connection issues).
- **Hello Redirects to Login**: This might be a config issue; for now, focus on authenticated endpoints.

## What's Next?
- Add WebSocket for real-time notifications.
- Build a frontend (React/Vue) to interact with the API.
- Enhance security and add more features.

If you encounter issues, share the error messages for help!