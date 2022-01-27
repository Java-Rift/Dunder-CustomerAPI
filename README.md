# Dunder Mifflin Customer API

```
    SIMPLE SPRING BOOT REST CONTROLLER FOR CREATING, UPDATING, VIEWING AND DELETING CUSTOMER RECORD (By: Edom Mesfin)
==========================================================================================================================
```
- The service is a very simple one without extensive validation and security.
- The API contains all the requested [Customer API end-points.](http://localhost:9092/dunder/customers/)


- Has the following functionalities:-
  - Accepts customer record as`json`
  - Returns all previously registered customer records.
  - Queries a specific customer record.
  - Updates existing customer record.
  - Deletes existing customer record.
- Saves customer data to `embbeded H2 database` with `Spring data JPA`, and `generated UUID`.
- Unit and Integration testing provided for POST and GET end-points.
- UI testing can be done using the Swagger UI (see below).
 
### Build the project and run
Use the following commands to run the API (from the home dir.):

###### == Compile:-
```
mvn clean install          -OR-
mvn clean install -DskipTest
```
###### == Run:-
```
mvn spring-boot:run       -OR-
java -jar target/*.jar        -OR, with debugging-
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=40013,suspend=n -jar target/*.jar
```
- The human-readable HTML doc/testing end-point can be accessed from a browser ( http://localhost:9092/swagger-ui/ )
- The basic Swagger API documentation should be enough to do user testing, see samples below:
```
{
  "fname": "James",
  "lname": "Smith",
  "uuid": "b42cceb8-cb9e-4a2a-b6c9-ace66a1d9143"
}
{
  "fname": "Maria",
  "lname": "Martinez",
  "uuid": "ceaa6c44-80ac-4c44-812d-4bbba3bd83e1"
}
{
  "fname": "Sophia",
  "lname": "Anderson",
  "uuid": ""
}
{
  "fname": "Robert",
  "lname": "Johnson",
  "uuid": "d37caa13-4f3c-43ee-9da5-1d520ed87a7f"
}
```
## Design modifications to make this API scalable up to millions of customers?

Service Scalability of this magnitude is possible if the application is designed from the ground up to be horizontally scalable 
based on the Microservices Architecture – a distributed architecture with each modules handling specific task and handling it very well.

Horizontal scaling requires addition of new instances of a service quickly and with no downtime.

####What we already have with this Customer API?
  1. It’s a `microservice` with specific task – customer management.
####What needs to be added?
  1. It needs to be containerized with lightweight containers like `docker`, so that all the necessary files to spin up the service are packaged in one place.
  2. Configuration must be externalized into a separate service managing all configurations, and this can easily be achieved using `Spring Cloud Config Server` and `Spring Cloud Bus`.
      Config server gives us.
     - Versioning
     - Security
     - Isolation
     - Single codebase (robust profiling)
     - Automatic update visibility across army of services
  3. `Logs` need to be streamed to a centralized location
  4. Bind request execution-cycle with `correlation-ID` to better trace action logs
  5. Integrate a `service registry client`, so that the API registers itself with external `service discovery`.
  6. Service communication should be `Asynchronous` as much as possible
  7. For `synchronous` communication, the service should implement a `circuit breaker pattern` to mitigate partial failures gracefully.
  8. Take advantage of `cloud infrastructure` (like AWS) or `container orchestration frameworks` (like Kubernetes) for deploying the service so that it can get autoscaling for any traffic situation.
