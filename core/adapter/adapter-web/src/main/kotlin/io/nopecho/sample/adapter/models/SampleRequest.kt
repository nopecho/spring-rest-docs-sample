package io.nopecho.sample.adapter.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

sealed class SampleRequest {
    data class CreateModel(
        @field:NotBlank
        val description: String? = null,
        @field:Length(min = 1, max = 255)
        val number: Long? = null,
        val bool: Boolean? = null,
        @field:NotNull
        val model: ObjectModel? = null
    )

    data class ObjectModel(
        val value: String? = null
    )
}