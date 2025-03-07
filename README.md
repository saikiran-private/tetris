# Tetris Web Application

A Java web-based implementation of the classic Tetris game that can be deployed on Apache Tomcat server.

## Features

- Classic Tetris gameplay
- Responsive web interface
- Score tracking and level progression
- Keyboard controls
- Pause/resume functionality
- Mobile-friendly design

## Technologies Used

- Java 8
- Servlet API 4.0
- JSP (JavaServer Pages)
- HTML5, CSS3, JavaScript
- Maven for build management
- JUnit for unit testing
- Apache Tomcat for deployment

## Project Structure
```
tetris/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tetris/
│   │   │           ├── model/      # Game logic classes
│   │   │           ├── controller/ # Servlet controllers
│   │   │           └── util/       # Utility classes
│   │   ├── webapp/
│   │   │   ├── WEB-INF/
│   │   │   │   ├── web.xml         # Web application configuration
│   │   │   │   └── tetris.jsp      # Main game JSP
│   │   │   ├── css/
│   │   │   │   └── tetris.css      # Game styling
│   │   │   ├── js/
│   │   │   │   └── tetris.js       # Client-side game logic
│   │   │   └── index.html          # Redirect page
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/
│               └── tetris/         # Unit tests
├── pom.xml                         # Maven configuration
└── README.md                       # This file

```
## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven 3.6 or higher
- Apache Tomcat 8 or higher

## Building and Running

### Building the WAR file

```bash
mvn clean package
This will create a tetris.war file in the target directory.
```

Deploying to Tomcat
Copy the tetris.war file to Tomcat's webapps directory
Start Tomcat if it's not already running
Access the application at http://localhost:8080/tetris


This completes our Tetris web application for Tomcat. The application includes:

Core game logic (model classes)
Web interface (JSP, CSS, JavaScript)
Server-side controller (Servlet)
Unit tests for the model classes
Build configuration (Maven)
Documentation (README)
To deploy this application:

Make sure you have all the files in the correct directory structure
Build the project using Maven: mvn clean package
Deploy the resulting WAR file to your Tomcat server
Access the application at http://localhost:8080/tetris (or the appropriate URL for your Tomcat configuration)
The application provides a complete, playable Tetris game with all the standard features, including scoring, levels, and game controls.
