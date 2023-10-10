
# ORDER-SERVICE

This service allows customers to place orders. Additionally, the service communicates asynchronously with Notification-service and synchronously with Product-Service nad Payment-Service



## Technologies
- Java 17
- Spring Boot 3.1.2
- Spring Cloud 
- Docker
- PostgreSQL
- Kafka
- Open Feign
## Features
#### Current:
- placing orders
- paying for orders via payment-service (HTTP communication)
- sending notification via notification-service (Kafka)
- saving orders in the database
#### To-do
- communication with the courier company's API
- creating a pdf label

