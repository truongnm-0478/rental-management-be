FROM maven:3-openjdk-17 AS build
WORKDIR /app

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Build ứng dụng và tạo file WAR
RUN mvn clean package -DskipTests

# Stage chạy ứng dụng
FROM openjdk:17-jdk-slim
WORKDIR /app

# Sao chép file WAR từ build stage
COPY --from=build /app/target/rental-management-0.0.1-SNAPSHOT.war drcomputer.war

# Mở cổng 8080
EXPOSE 8080

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "drcomputer.war"]
