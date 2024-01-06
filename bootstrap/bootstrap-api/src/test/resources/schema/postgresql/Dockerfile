FROM postgres:13-alpine

#COPY ./ddl.sql /docker-entrypoint-initdb.d

ENV POSTGRES_DB=test
ENV POSTGRES_USER=test
ENV POSTGRES_PASSWORD=test

ENTRYPOINT ["postgres"]