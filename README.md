# spring boot 3 opentelemtry jaeger sample project
In this repository, I created a sample java project that shows you how to send traces of your requests to `Jaeger`.

## Introduction
If you want to observe the precise flow of interactions between your microservices, you require a tool that captures the traces of requests as they traverse through the various microservices, as well as the time taken at each step.

There are numerous methods for sending traces from an application to `Jaeger`. In the case of a Java application, you have different options, such as `Micrometer`, `Spring Cloud Sleuth` and `OpenTelemetry Java Agent`. In this article, I will explain the differences between them and provide guidance on how to send traces from a Java application to a `Jaeger` server.

## Micrometer VS Sleuth VS OpenTelemetry agent
`Spring Cloud Sleuth` has been discontinued in Spring Boot 3.x, and it can only be used until the end of Spring Boot 2. To send your traces to `Jaeger`, you need to migrate to the `Micrometer` library.

Alternatively, you can use the `OpenTelemetry Java Agent Instrumentor`, but I do not recommend it due to potential security problems that a Java Agent may introduce to your code. Additionally, it may cause performance issues as it can inject interceptors before and after each method call within your application.

Therefore, I recommend using Micrometer as a superior alternative to `Sleuth` or the `OpenTelemetry Java Agent`.

The Jaeger All-In-One can be easily set up with all the necessary components to test your Java application and ensure that it correctly sends traces. It also includes an OpenTelemetry Collector module for collecting OpenTelemetry traces. You can bring it up simply by using a docker-compose.yaml file like the following:

```bash
version: '3.7'
services:
  jaeger:
    image: jaegertracing/all-in-one:latest
    ports:
      - "16686:16686" # the jaeger UI 
      - "4317:4317" # the OpenTelemetry collector grpc 
    environment:
      - COLLECTOR_OTLP_ENABLED=true
```

run the following command to bring up the docker-compose file:

```bash
$ docker-compose up -d 
```


Send sample requests
Now you can send a request to microservice 1, and it will internally send a request to microservice 2 in the background:

```
$ curl http://localhost:8080/hello/path1
```
