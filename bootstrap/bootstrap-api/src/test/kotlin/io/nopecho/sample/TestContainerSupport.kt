package io.nopecho.sample

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.images.builder.ImageFromDockerfile
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
open class TestContainerSupport {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        @Container
        @JvmStatic
        var postgres = GenericContainer(
            ImageFromDockerfile(DB_SERVICE.name, false)
                .withFileFromClasspath(".", DB_SERVICE.dockerFilePath)
        )
            .withExposedPorts(DB_SERVICE.port)
            .waitingFor(Wait.forLogMessage("database system is ready",1))
    }
}