package io.nopecho.sample.application

import io.nopecho.sample.domain.Sample
import io.nopecho.sample.domain.SampleRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SampleService(
    private val repository: SampleRepository
) {

    fun save(name: String, description: String = ""): SampleResult {
        val sample = Sample(UUID.randomUUID().toString(), name, description)
        val saved = repository.save(sample)
        return SampleResult.from(saved)
    }

    fun get(id: String): SampleResult {
        val found = repository.findById(id)
        return SampleResult.from(found)
    }
}

data class SampleResult(
    val id: String,
    val name: String
) {
    companion object {
        fun from(sample: Sample):SampleResult {
            return SampleResult(sample.id, sample.name)
        }
    }
}