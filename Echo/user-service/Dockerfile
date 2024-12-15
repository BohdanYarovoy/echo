# Використовуємо офіційний образ Java (JDK 21, slim - полегшена версія)
FROM openjdk:21-jdk-slim

# Встановлюємо робочу директорію всередині контейнера
WORKDIR /app

# Копіюємо зібраний JAR-файл сервісу (з директорії target) у контейнер
COPY target/user-service-1.0-SNAPSHOT.jar /app/user-service.jar

# Вказуємо команду для запуску додатка
CMD ["java", "-jar", "/app/user-service.jar"]
