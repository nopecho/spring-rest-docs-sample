package io.nopecho.sample.adapter

import io.nopecho.sample.adapter.models.SampleRequest
import io.nopecho.sample.application.SampleResult
import io.nopecho.sample.application.SampleService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class SampleController(
    private val service: SampleService
) {
    @GetMapping("/samples/{sampleId}")
    fun get(@PathVariable sampleId: String): ResponseEntity<SampleResult> {
        val result = service.get(id = sampleId)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/samples")
    fun create(@Valid @RequestBody request: SampleRequest.CreateModel): ResponseEntity<SampleResult> {
        val result = service.save(UUID.randomUUID().toString())
        return ResponseEntity.ok(result)
    }
}