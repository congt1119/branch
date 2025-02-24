# Getting Started

This is a simple springboot application.

To run the application from the command line.
Run: `mvn spring-boot:run`

Else if using intellij you should be able to import the project and start the application from the class `ExerciseApplication`
Make a rest call to the endpoint: [http://localhost:8080/user/octocat](http://localhost:8080/user/octocat)
You should see the expected return values

# File Structure
* api.model - contains the api pojo that is externally facing
* config - Any spring config setup or bean creation
* controller - The rest endpoint definitions
* handler - Spring handler classes. Currently only one.
* integration - 3rd party or clients specific code. Light weight and should be true to the client code as possible
  * github - github is the only current integration
* service - The application logic. This is where most of the heavy lifting should happen

# Design Decisions
* The application is relatively simple so there aren't many complicated design decisions to be made. But some highlights are listed below.
* Use lombok to skip the boiler plating for pojos
* Use ControllerAdvice ApiExceptionHandler to centralize error handling
  * Note that the class simply just forward possible client errors from the integration (github) to the caller for now.
* Testing is done e2e with Wiremock
  * Currently, handle 3 simple use-case
    * success response from github for username octocat
    * 404 Not Found which just resurface to the caller
    * 500 Server error which just resurface to the caller
* Caching is done using guava instead of spring cache

# Note
* RestClient retry is currently not setup
* No custom logging configs. Default logging by springboot.
* Api metrics collection is not in scope.