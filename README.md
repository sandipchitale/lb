# lb

A simple implementation of HAProxy based load balancer with two proxies

First running at port 8080 and balancing two Springboot rest app Gateways running at:

- `host.docker.internal:8081`
- `host.docker.internal:8082`

Second running at port 8085 and balancing two Springboot rest app Microservice running at:

- `host.docker.internal:8086`
- `host.docker.internal:8087`

The same app `dev.sandipchitale.lb.LbApplication` plays the role of Gateway and Microservice. Simply run it four times with different port numbers:

- --server.port=8081
- --server.port=8082
- --server.port=8086
- --server.port=8087

The Gateway uses Spring Cloud Gateway MVC.fn to proxy requests with path `/microservice` to load balancer at `http://localhost:8085`. 

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.

However, no services were found. As of now, the application won't start!

Please make sure to add at least one service in the `compose.yaml` file.

