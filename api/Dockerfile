# Use an official Maven image with Java 21
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    ca-certificates \
    unzip \
    && wget -qO - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y \
    google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

RUN CHROME_VERSION=$(google-chrome --version | awk '{print $3}' | cut -d '.' -f 1) \
    && wget -q -O LATEST_RELEASE_VERSION https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_${CHROME_VERSION} \
    && CHROMEDRIVER_VERSION=$(cat LATEST_RELEASE_VERSION) \
    && wget -q https://storage.googleapis.com/chrome-for-testing-public/${CHROMEDRIVER_VERSION}/linux64/chromedriver-linux64.zip \
    && unzip chromedriver-linux64.zip -d /usr/local/bin/ \
    && rm chromedriver-linux64.zip LATEST_RELEASE_VERSION \
    && chmod +x /usr/local/bin/chromedriver-linux64/chromedriver

ENV PATH="/usr/local/bin:$PATH"
COPY --from=build /app/target/nbafantasy-1.1.0-api.jar /app/nbafantasy-api.jar
EXPOSE 8080

CMD ["java", "-jar", "/app/nbafantasy-api.jar"]
