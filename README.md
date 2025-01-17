# Performance Analysis of Monolithic and Microservice Architectures
***Created by: Ahmet Enes Zor***

This project uses Quarkus, the Supersonic Subatomic Java Framework.

### **_NOTE:_** **The commands mentioned below should be used within the service folders. In the monolithic architecture, 1 service and in the microservice architecture, 5 services should be stood up.**

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/<...>

## Load Testing with ApacheBench
An example test is given below:

    ab -n 1000 -c -1000 localhost:8080/api/stores

**-n specifies the number of requests**

**-c specifies the number of concurrencies (users)**

