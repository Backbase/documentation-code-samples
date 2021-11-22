## payment-batch extension.

[Fill out this file with some information about your service extension.]

## How to use

To use your service extension, you include the JAR build from this artifact to the CLASSPATH used when the service is 
started.


When you run a service as an [executable JAR](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#executable-jar-property-launcher-features), 
use the `loader.path` command line argument to add JARs or directories of JARs to the classpath. For example:

```
./rtn-blocklist-batch-validation-rule-service-extension.jar -Dloader.path=/path/to/my.jar,/path/to/my/other.jar,/path/to/lib-dir
```

If you are not running the Service as a bootable jar, use the mechanism available in your application server.

### Docker

To extend the Backbase Docker images
with the Behaviour Extension jar built from this project.

    mvn package -Pdocker-image

The build creates a Docker image with the extension added and ready to use.

To build the image to a local Docker daemon without pushing to the repository.

    mvn package -Pdocker-image,local-client

## Community Documentation

* [Extend the behavior of a service](https://community.backbase.com/documentation/ServiceSDK/latest/extend_service_behavior)

Add links to documentation including setup, config, etc.

## Jira Project

Add link to Jira project.

## Confluence Links
Links to relevant confluence pages (design etc).

## Support

Slack, Email, Jira etc.