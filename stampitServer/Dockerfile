# 빌드 스테이지
FROM openjdk:17-alpine AS build

WORKDIR /app

COPY . /app/stampitServer

# gradlew를 이용한 프로젝트 필드
RUN chmod +x gradlew
RUN ./gradlew clean build

# 실행 스테이지
FROM openjdk:17-oracle

WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일을 복사
COPY --from=build /app/stampitServer/build/libs/stampitServer-0.0.1-SNAPSHOT.jar /app/build/libs/stampitServer-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/build/libs/stampitServer-0.0.1-SNAPSHOT.jar"]
