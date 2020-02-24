# email-sender


## Build & run
use Maven to compile and build the project: `mvn clean package`

To run the project simply run one of the following:
 * `mvn spring-boot:run`
 * `java -jar .\target\target\email-sender-0.0.1-SNAPSHOT.jar`

To use local properties (and in memory DB):  
`-Dspring.profiles.active=local`  
examples:  
* `java -jar -Dspring.profiles.active=local path-to.jar`
* `mvn spring-boot:run -Dspring-boot.run.profiles=local`
* `java -jar path-to.jar --spring.profiles.active=local`

check for the following line in the logs to verify activation:  
`INFO  i.f.e.EmailSenderApplication - The following profiles are active: local`

the running application can be found at: localhost:8080/ 

## Configuration
Mail server configuration can be set in the `application.properties` or passed 
as an env_vars/java properties.  
`local` profile is preconfigured to use a [Papercut](https://github.com/ChangemakerStudios/Papercut) local server.


mysql DB is supported (driver), set the properties in the `application.properties` 
and disable the local profile. 
 
An `.sql` script can be found in `sql/create-email-schema.sql` to create the requested table.

## Online documentation
run the app and navigate to:
http://localhost:8080/swagger-ui.htm

## Performance test (Gatling)
* `mvn gatling:test` all tests   

* `mvn gatling:test -Dgatling.simulationClass=GetSimulation` or single test

### Troubleshoot
in case of compiling failure in IDE please check: https://immutables.github.io/apt.html