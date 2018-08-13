## Java REST Web service with Spring Boot

### Notes
* The application uses the embedded Tomcat server starting by default on port `8080`, therefore the app web UI is available at `http://localhost:8080/`
* The in-memory database H2 is used as data storage. It is configured so that it does not persist the data and all the changes are lost after the application restart, but it can be easily configured as a persistent database if needed. H2 web console is available at: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, username: `sa`, empty password).
* REST API is documented using Swagger 2, a web UI is available at `http://localhost:8080/swagger-ui.html` 

### Build requirements
* JDK 8 and above
* Maven 3

### How to run
1. Build the project and run the tests.

    ```bash
    $ mvn clean package
    ```

2. Run the application.

    ```bash
    $ java -jar target/stocks.jar
    ```