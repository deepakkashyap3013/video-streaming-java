# Video Streaming Application

This project showcases a video streaming application built with Spring Boot. It enables users to stream video content from a server directly to their web browser, providing a simple yet effective way to deliver video streams over the internet.

## Features

- **Video Streaming**: Stream video content to clients with support for range requests.
- **Spring Boot Integration**: Utilizes Spring Boot for easy setup and configuration.
- **Resource Handling**: Serves static resources and video files efficiently.

## Getting Started

### Prerequisites

- Java 17
- Maven

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/videostream.git
   ```
   
2. Navigate to the project directory:
   ```sh
   cd videostream
   ```

3. Build the project:
   ```sh
    ./mvnw clean install
    ```

4. Run the application:
    ```sh
    ./mvnw spring-boot:run
    ```

5. Open your browser and navigate to http://localhost:8080 to access the video streaming application.

### Configuration
The application can be configured using the `application.properties` file located in `/resources` directory.

```
# Spring Boot Application Configuration
spring.application.name=videostream
video.file-path=src/main/resources/video/sample.mp4
server.port=8080

# Web server thread pool (Tomcat)
#default: 200
# server.tomcat.max-threads=200

#default: 100
# server.tomcat.accept-count=150

#default: 60000
# server.tomcat.connection-timeout=30000

```

### API Endpoints
- `GET /`: Returns the home page.
- `GET /video`: Streams the video content
- `GET /video/info`: Provides information about the video file

### Sample Load Testing result
- **Endpoint**: `GET /video`
- **Testing workspace**: JMeter
- **Configuration**: 2000rps

  <img width="1800" alt="Screenshot 2025-01-28 at 1 10 06 PM" src="https://github.com/user-attachments/assets/fd26a219-cb78-4d8b-b8e7-09f918142ec6" />



### Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Apache Maven](https://maven.apache.org/)


   
