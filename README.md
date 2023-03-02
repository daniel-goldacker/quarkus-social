# Quarkus-social API's
A simple social network where users can send short texts and can follow each other.

**USERS:** If you want to register users of the social network, the user sends some basic information to register. There will be an endpoint for listing users.

**POSTS:** They represent user posts, which will be in the form of a short text and date-time. Only the user's followers can view the posts.

**FOLLOWERS:** Users will be able to have followers and also follow other users so that they can view their posts. There will be an option to unfollow the user.

**Obs:** This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Project premise
- Execute commanand in docker for install postgres: ```docker run --rm -it -p 5432:5432 -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -e POSTGRES_DB=quarkus-social postgres```
- Execute script in database postgres: ```./src/db/db.sql```
- Install jdk 11.0.17
- Install apache maven 3.8.3

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```script
mvn compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```script
mvn clean package -DskipTests
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar ./target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```script
mvn clean package -Dquarkus.package.type=uber-jar -DskipTests
```
The application, packaged as an _über-jar_, is now runnable using `java -jar ./target/quarkus-social-1.0-runner.jar`.

## Access API specifications
After running quarkus-social go to the URL [http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)