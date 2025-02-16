# Sử dụng image OpenJDK làm base image
FROM openjdk:17-jdk-slim

# Cài đặt thư viện cần thiết và thiết lập thư mục làm việc
WORKDIR /app

# Copy file WAR vào container
COPY target/rental-management-0.0.1-SNAPSHOT.war /app/rental-management-0.0.1-SNAPSHOT.war

# Expose cổng mà ứng dụng Spring Boot sẽ chạy
EXPOSE 8080

# Lệnh để chạy ứng dụng khi container được khởi động
ENTRYPOINT ["java", "-jar", "/app/rental-management-0.0.1-SNAPSHOT.war"]
