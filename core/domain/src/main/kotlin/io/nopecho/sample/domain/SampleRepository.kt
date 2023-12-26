package io.nopecho.sample.domain

interface SampleRepository {
    fun save(sample: Sample): Sample
    fun findById(id: String): Sample
}