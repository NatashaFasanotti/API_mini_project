# API MINI Project - [Swagger API - Pet Store 3](https://github.com/swagger-api/swagger-petstore)
###### Java, Junit, Postman, Docker

### Project Goal:
- to test 3 endpoints
- to test all the data in the endpoint response

### Get Requests
- GET inventory
- GET pet by ID
- GET pet by status

### Endpoints that we are specifcally testing
- POST add a pet
- UPDATE update a pet
- DELETE delete a pet

### Setting up Docker

#### Docker supports various operating systems like Windows, macOS, and Linux. Check the official Docker documentation for detailed system requirements specific to your OS.

#### Docker Installation: 

- For Windows: Visit Docker Desktop for Windows and download the installer: https://docs.docker.com/desktop/install/windows-install/
- For macOS: Visit Docker Desktop for Mac and download the installer: https://docs.docker.com/desktop/install/mac-install/
- Run the downloaded installer and follow the on-screen instructions.
- Docker Desktop will install Docker Engine, Docker CLI, Docker Compose, and Docker Machine.
- Resources: Official Docker Documentation: https://docs.docker.com/

#### Project Set Up in Docker: 

- Fork the Repository:

  Go to Swagger Petstore Repository:
  ##### https://github.com/swagger-api/swagger-petstore.
  
  Click on "Fork" to create a copy of the repository in your GitHub account.

- Clone the Repository to Your Computer:

  Create a new folder on your computer.
  
  Open a terminal or command prompt in this folder.
  
  Use the git clone command to clone your forked repository:
  
  ##### git clone https://github.com/your-username/swagger-petstore.git

  Register on Docker Hub:

- Visit Docker Hub and sign up for an account.
- Log in to Docker from Terminal:

  ##### docker login
  
- Enter your Docker Hub username and password (password characters will not be visible as you type).
- Pull and Run Docker Image:

  ##### docker pull swaggerapi/petstore3:unstable
  
- Run the Docker container:

  ##### docker run --name swagger-petstore -d -p 8080:8080 swaggerapi/petstore3:unstable

- Verify Installation:

  Open a web browser and navigate to http://localhost:8080/ to access the Swagger Petstore running locally on your machine.
  
#### By following these steps, you'll have a local instance of the Swagger Petstore set up and running using Docker. This allows you to experiment with the petstore application without affecting the original repository.

### Setting up Postman

#### Making API Requests:

- Create a New Request:

  Click on the "New" button in Postman.
  
  Choose "Request" to create a new API request.
  
  Enter Request Details:

  ##### Enter the request URL in the request field (e.g., https://api.example.com/users).
  ##### Choose the HTTP method (GET, POST, PUT, DELETE, etc.) from the dropdown.

- Add Request Parameters:

  ##### Specify request headers, query parameters, request body (for POST and PUT requests), etc., as required by the API endpoint you are testing.

- Send the Request:

  Click on the "Send" button to execute the API request.
  
  Postman will display the response status, headers, and body returned by the API.

#### Testing and Validation:

- Validate Responses:

  Inspect the response body to ensure it matches the expected format and contains the required data.
  
  Use assertions in Postman tests to validate response content, status codes, headers, etc.

#### Additional Features:

- Explore Postman Features:

  Explore Postman's features such as collections, environments, tests, scripts, and more to enhance your API testing workflow.

###### Team Members
###### [Natasha](https://github.com/NatashaFasanotti), [Sharmin](https://github.com/sharminakth), [Mahlet](https://github.com/mahletjoseph9), [Rita](https://github.com/ritaqmiranda) & [Ellen](https://github.com/annwyl21)


