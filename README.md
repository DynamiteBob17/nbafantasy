# About
- finds the best combination of players for the NBA fantasy league based on statistics

# How to use
## Prerequisites
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) and up
- for now, latest version of **Google Chrome** (recommended, otherwise using *Docker* is required)

## Running the project
(*on Windows use `.\mvnw.cmd` instead of `./mvnw`, and `\` instead of `/`*)
1. start the web scraper server:
    ### *Without* Docker
    - either with `./mvnw spring-boot:run -pl api` ***OR***
    - `./mvnw clean package -pl api` and then `java -jar ./api/target/nbafantasy-api.jar`
    ### *With* Docker
   - `docker build -t <docker-image-name> .`, then `docker run --name <app-name> -p 8080:8080 <docker-image-name>`
   
2. start the main program (in new shell/cmd if necessary):
   - either with `./mvnw clean compile exec:java -pl core` ***OR***
   - `./mvnw clean package -pl core` and then `java -jar ./api/target/nbafantasy-core.jar`
