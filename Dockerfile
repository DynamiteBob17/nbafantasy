FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests && ls -l /app/target

FROM eclipse-temurin:21-jre-alpine
LABEL authors="janml"

RUN apk add --no-cache \
    curl \
    bash \
    ttf-freefont \
    chromium \
    chromium-chromedriver \
    libstdc++ \
    font-noto \
    && rm -rf /var/cache/apk/*

ENV CHROME_BIN=/usr/bin/chromium-browser
ENV CHROMEDRIVER_BIN=/usr/bin/chromedriver

WORKDIR /app
COPY --from=build /app/target/nbafantasy-1.1.0-api.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
