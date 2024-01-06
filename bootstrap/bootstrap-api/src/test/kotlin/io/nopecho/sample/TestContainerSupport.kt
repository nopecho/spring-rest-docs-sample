package io.nopecho.sample

import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.ImageFromDockerfile
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class TestContainerSupport {
    companion object {
        @Container
        @JvmStatic
        var postgres = GenericContainer(
            ImageFromDockerfile().withDockerfile(DB_SERVICE.dockerFilePath)
        ).apply {
            withExposedPorts(DB_SERVICE.port)
            waitingFor(DB_SERVICE.wait)
        }

        @Container
        @JvmStatic
        var postgres2 = GenericContainer(
            ImageFromDockerfile().withFileFromClasspath("Dockerfile", "schema/postgresql/Dockerfile")
        ).apply {
            withExposedPorts(DB_SERVICE.port)
            waitingFor(DB_SERVICE.wait)
        }
    }
}