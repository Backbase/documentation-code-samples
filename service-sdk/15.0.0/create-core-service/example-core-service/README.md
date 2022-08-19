# example-service

_Fill out this file with some information about your Service._

## Dependencies

Requires a running Eureka registry, by default on port 8080.

## Configuration

Service configuration is under `src/main/resources/application.yaml`.

## Running

To run the service in development mode, use:
- `mvn spring-boot:run`

To run the service from the built binaries, use:
- `java -jar target/example-core-service-1.0.0-SNAPSHOT.war`

## Authorization

Requests to this service are authorized with a Backbase Internal JWT, therefore you must access this service via the Backbase Gateway after authenticating with the authentication service.

For local development, an internal JWT can be created from http://jwt.io, entering ```JWTSecretKeyDontUseInProduction!``` as the secret in the signature to generate a valid signed JWT.
