# Event Pulse

A Spring Boot-based event management and notification service using MongoDB for data storage, Kafka for event notifications, and JWT for authentication.

## Features

- User registration and login with JWT authentication
- Event creation, update, retrieval, and deletion
- Subscription to events or categories
- Real-time notifications via Kafka

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (running on localhost:27017)
- Kafka (with Zookeeper, running on localhost:9092)

## Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd event-pulse

2. Install dependecies
    mvn clean install









Steps

kafka

Start Kafka broker
.\bin\windows\kafka-server-start.bat .\config\kraft\server.properties


Keep this window open; it runs the broker.

You’ll see logs indicating Kafka is running in KRaft mode.

4️⃣ Open a new PowerShell window → verify broker
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list


Should return empty if no topics exist yet.

5️⃣ Create a topic
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic test-topic --partitions 1 --replication-factor 1

6️⃣ Produce messages
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test-topic

7️⃣ Consume messages
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test-topic --from-beginning