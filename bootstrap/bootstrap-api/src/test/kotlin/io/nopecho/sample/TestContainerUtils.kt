package io.nopecho.sample

import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy

val DB_SERVICE = DockerServiceModel(
    name = "test-db",
    dockerFilePath = "schema/postgresql",
    port = 5559,
    wait = Wait.forLogMessage("database system is ready to accept connections",1)
)

val AWS_SERVICE = DockerServiceModel(
    name = "test-aws",
    dockerFilePath = "schema/localstack",
    port = 4569,
    wait = Wait.forLogMessage("Init Localstack", 1)
)

val ES_SERVICE = DockerServiceModel(
    name = "test-es",
    dockerFilePath = "schema/elasticsearch",
    port = 9222,
    wait = Wait.forLogMessage("Init Elasticsearch", 1)
)

data class DockerServiceModel(
    val name: String,
    val dockerFilePath: String,
    val port: Int,
    val wait: WaitStrategy
)