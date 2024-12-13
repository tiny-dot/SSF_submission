# need .mvn, mvmw, pom.xml
FROM eclipse-temurin:23-noble AS builder

WORKDIR /src

COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src


RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true 

FROM eclipse-temurin:23-jre-noble

WORKDIR /app


COPY --from=builder /src/target/noticeboard-0.0.1-SNAPSHOT.jar.original noticeboardapp.jar

ENV PORT=8080


EXPOSE ${PORT}
EXPOSE ${HOST_URL}

#HEALTHCHECK --interval=60s --timeout=120s --start-period=120s --retries=3 \
   #CMD curl -s -f {HOST_URL+/notice/health} || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar noticeboardapp.jar


