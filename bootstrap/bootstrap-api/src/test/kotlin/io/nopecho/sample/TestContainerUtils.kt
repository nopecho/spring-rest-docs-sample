package io.nopecho.sample

import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy
import org.testcontainers.utility.MountableFile
import java.nio.file.Path
import java.nio.file.Paths

const val TEST_CLASSPATH = "src/test/resources"

val DB_SERVICE = DockerServiceModel(
    dockerFilePath = DockerFileLoader.extract("schema/postgres/Dockerfile"),
    port = 5559,
    wait = Wait.forLogMessage("ready for start up.",1)
)

val AWS_SERVICE = DockerServiceModel(
    dockerFilePath = Paths.get("$TEST_CLASSPATH/schema/localstack/Dockerfile-localstack"),
    port = 4569,
    wait = Wait.forLogMessage("Init Localstack", 1)
)

val ES_SERVICE = DockerServiceModel(
    dockerFilePath = Paths.get("$TEST_CLASSPATH/schema/elasticsearch/Dockerfile-elasticsearch"),
    port = 9222,
    wait = Wait.forLogMessage("Init Elasticsearch", 1)
)

data class DockerServiceModel(
    val dockerFilePath: Path,
    val port: Int,
    val wait: WaitStrategy
)

object DockerFileLoader {
    fun extract(path: String): Path {
        val resource = MountableFile.forClasspathResource(path)
        return Paths.get(resource.resolvedPath)
    }
}