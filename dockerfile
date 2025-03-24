#Użyj obrazu Javy (JDK 17) jako bazowego
FROM openjdk:17-jdk-slim

#Ustaw katalog roboczy wewnątrz kontenera
WORKDIR /app

#Skopiuj pliki aplikacji (np. zbudowane JAR)
#COPY build/libs/*.jar app.jar

#Ustawienie portu, na którym działa aplikacja
EXPOSE 8080

#Uruchomienie aplikacji Kotlin
#CMD ["java", "-jar", "myapp.jar"]