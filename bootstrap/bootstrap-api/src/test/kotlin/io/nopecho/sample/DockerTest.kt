package io.nopecho.sample

import org.junit.jupiter.api.Test


class DockerTest {

    @Test
    fun name() {
        println(DB_SERVICE.dockerFilePath)
    }
}