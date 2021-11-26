# epam-order-processing-web-1

## Running the service
java -jar -Dspring.profiles.active=mysql target/web-service-1-1.0-SNAPSHOT.war

## Make sure port is available
lsof -nP -iTCP:8093

## Implemented rest end points:
[get] http://localhost:8093/health