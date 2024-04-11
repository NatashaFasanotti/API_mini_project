# API MINI Project - [Swagger API - Pet Store 3](https://github.com/swagger-api/swagger-petstore)
###### Java, Junit, Postman, Docker, RestAssured, POJO, Java Record, Hamcrest

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

Download [Docker](https://www.docker.com/products/docker-desktop/)

- Pull *swaggerapi/petstore3:unstable* Image:
  
```
docker pull swaggerapi/petstore3:unstable
```
- Run the Docker container:

```
docker run --name petrehomingservice -d -p 8080:8080 swaggerapi/petstore3:unstable
```

- Verify Installation:

##### Open a web browser and navigate to http://localhost:8080/ to access the Swagger Petstore running locally on your machine.
  
#### By following these steps, you'll have a local instance of the Swagger Petstore set up and running using Docker. This allows you to experiment with the petstore application without affecting the original repository.

### Setting up Postman

#### Making API Requests:

##### Create a New Request:

- Click on the "New" button in Postman.
- Choose "Request" to create a new API request.
- Enter Request Details:

  ##### Enter the request URL in the request field (e.g., https://api.example.com/users).
  ##### Choose the HTTP method (GET, POST, PUT, DELETE, etc.) from the dropdown.

- Add Request Parameters:

  ##### Specify request headers, query parameters, request body (for POST and PUT requests), etc., as required by the API endpoint you are testing.

 - Click on the "Send" button to execute the API request.
 - Postman will display the response status, headers, and body returned by the API.

#### Testing and Validation:

- Inspect the response body to ensure it matches the expected format and contains the required data.
-  Use assertions in Postman tests to validate response content, status codes, headers, etc.

#### Additional Features:

- Explore Postman's features such as collections, environments, tests, scripts, and more to enhance your API testing workflow.

## Project Structure

#### The ApiConfig class provides links into a local config.properties file where secret information is stored (typically api-keys):

- getBaseUri(): Retrieve the base URI of the API.
- getBasePath(): Retrieve the base path for API endpoints.
- getCommonBasePath(): Retrieve the common base path for pet-related endpoints.

#### The FindPetTest class demonstrates how to interact with a pet-related API to perform tests on pet endpoints.

#### Main functionalities: 

- checkId(): Verify that the ID of a pet retrieved from the API matches the expected value.
- checkCategory_Id(): Ensure that the category ID of a pet is as expected.
- checkCategory_Name(): Validate that the category name of a pet matches a predefined value.
- checkName(): Verify that the name of a pet matches a predefined value.
- checkStatus(): Validate that the status of a pet is in an expected state.

### Java Dependencies: 

##### io.restassured:rest-assured:5.3.1
##### org.hamcrest:hamcrest-core:2.2
##### org.junit.jupiter:junit-jupiter:5.10.1

###### Team Members
###### [Natasha](https://github.com/NatashaFasanotti), [Sharmin](https://github.com/sharminakth), [Mahlet](https://github.com/mahletjoseph9), [Rita](https://github.com/ritaqmiranda) & [Ellen](https://github.com/annwyl21)


