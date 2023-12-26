package io.nopecho.sample.infrastructure

import io.nopecho.sample.domain.Sample
import io.nopecho.sample.domain.SampleRepository
import org.springframework.stereotype.Component
import java.util.NoSuchElementException

@Component
class SampleInMemoryAdapter(
    private val storage: MutableMap<String, Sample> = mutableMapOf()
): SampleRepository {

    override fun save(sample: Sample): Sample {
        storage[sample.id] = sample
        return sample
    }

    override fun findById(id: String): Sample {
        return storage[id] ?: throw NoSuchElementException()
    }
}