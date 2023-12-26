package io.nopecho.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleApi

fun main(args: Array<String>) {
    runApplication<SampleApi>(*args)
}