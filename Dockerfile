# 1-bosqich: Build qilish uchun GraalVM JDK ishlatamiz
FROM container-registry.oracle.com/graalvm/jdk:17-ol9 AS build
WORKDIR /app

# Maven wrapper va pom.xml fayllarini nusxalash
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Kutubxonalarni yuklab olish (bu build vaqtini tejaydi)
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Loyiha kodini nusxalash va build qilish
COPY src ./src
RUN ./mvnw clean package -DskipTests

# 2-bosqich: Ishga tushirish (Faqat Runtime qismi)
FROM container-registry.oracle.com/graalvm/jdk:17-ol9
WORKDIR /app

# Build bosqichidan tayyor bo'lgan .jar faylni olamiz
COPY --from=build /app/target/*.jar app.jar

# Railway yoki Render uchun port
EXPOSE 8082

# GraalVM-da Spring Boot-ni ishga tushirish
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]