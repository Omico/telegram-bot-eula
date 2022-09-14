FROM gradle:jdk18-alpine as builder

WORKDIR /eula

COPY . .

RUN gradle clean assemble --no-daemon

FROM openjdk:18-slim-buster

WORKDIR /eula

COPY --from=builder /eula/build/distributions/EulaBot*.tar EulaBot.tar

RUN tar -xvf EulaBot.tar

CMD ["sh", "-c", "./EulaBot*/bin/EulaBot --token $BOT_TOKEN"]
