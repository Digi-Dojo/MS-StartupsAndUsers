## BASE IMAGE
# defines basic configuration for both stages

FROM gradle:7.4.2-jdk17 AS base

USER gradle

WORKDIR ../../usr/src/digidojo

RUN mkdir .gradle

COPY --chown=gradle:gradle . ./



## DEVELOPMENT BUILDER IMAGE
# continuously builds the application

FROM base AS devbuilder

CMD gradle build --continuous



## DEVELOPMENT RUNNER IMAGE
# runs a development server

FROM base AS development

ENTRYPOINT ["gradle"]




## BUILDER IMAGE
# builds the project into an executable JAR

FROM base AS builder

RUN gradle clean bootJar



## RUNNER IMAGE
# copies the JAR and runs it

FROM eclipse-temurin:17-jdk AS runner

WORKDIR /digidojo

ENV PORT=8080

EXPOSE ${PORT}

COPY --from=builder /usr/src/digidojo/build/libs/*.jar ./demo.jar

ENTRYPOINT ["java", "-jar", "/digidojo/demo.jar"]