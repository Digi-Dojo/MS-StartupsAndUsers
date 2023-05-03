FROM gradle:7.4.2-jdk17 AS base

USER gradle

WORKDIR ../../usr/src/digidojo

RUN mkdir .gradle

COPY --chown=gradle:gradle . ./

FROM base AS builder

RUN gradle clean bootJar



FROM eclipse-temurin:17-jdk AS runner

WORKDIR /digidojo

ENV PORT=8080

EXPOSE ${PORT}

COPY --from=builder /usr/src/digidojo/build/libs/*.jar ./demo.jar

ENTRYPOINT ["java", "-jar", "/digidojo/demo.jar"]