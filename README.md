# Video Streaming Application

This project demonstrates a simple video streaming application built with Spring Boot. It allows users to stream video content from a server to a web client.

## Features

- **Video Streaming**: Stream video content to clients with support for range requests.
- **Thread Pool Configuration**: Custom thread pool configuration for handling HTTP requests.
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
spring.application.name=videostream
video.file-path=src/main/resources/video/sample.mp4
server.port=8080
spring.task.execution.pool.core-size=10
spring.task.execution.pool.max-size=20
spring.task.execution.pool.queue-capacity=100
spring.task.execution.thread-name-prefix=HttpThreadPool-
```

### API Endpoints
- `GET /`: Returns the home page.
- `GET /video`: Streams the video content
- `GET /video/info`: Provides information about the video file


### Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Apache Maven](https://maven.apache.org/)


   