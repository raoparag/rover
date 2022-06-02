Design Considerations
- Since we are building a Spring Boot Microservice, I have created a rover API instead of command line inputs for more intuitive experience and better interfacing
- for more details on the design, refer to the Design.txt file

Project Contents
- Source Code
- Unit Tests
- Functional Tests


To run the Project
- Clone the project to a folder eg. 'rover'
- On a terminal window, go to the 'rover' folder
- Use the below command to start running the Spring Boot Application
  `./mvnw spring-boot:run`
- You can see on the terminal window that the application has started running and the below line printed
  `Started RoverApplication`
- Once the application is running, you can use the below commands in another terminal window to test the API
- for a single rover move `curl --location --request POST 'http://localhost:8080/rover?startFresh=true' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "rovers":[
  {
  "name":"rover1",
  "x":3,
  "y":4,
  "facing":"N",
  "command":"f,f,r,f,f"
  }
  ]
  }'`
- for multiple rover moves without collision `curl --location --request POST 'http://localhost:8080/rover?startFresh=true' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "rovers":[
  {
  "name":"rover1",
  "x":3,
  "y":4,
  "facing":"N",
  "command":"f,f,r,f,f"
  },
  {
  "name":"rover2",
  "x":1,
  "y":2,
  "facing":"N",
  "command":"f,f,r,f,f"
  }
  ]
  }'`
- for multiple rover moves with collision `curl --location --request POST 'http://localhost:8080/rover?startFresh=true' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "rovers":[
  {
  "name":"rover1",
  "x":3,
  "y":4,
  "facing":"N",
  "command":"f,f,r,f,f"
  },
  {
  "name":"rover2",
  "x":1,
  "y":2,
  "facing":"N",
  "command":"f,f,r,f,f,l,f,f,r,f,f"
  }
  ]
  }'`

Functional Tests Using IDE
- you can also import the project in your IDE and run the Functional tests located at this location `com.mars.rover.FunctionalTests`
- these functional tests cover and validate all the scenarios mentioned above