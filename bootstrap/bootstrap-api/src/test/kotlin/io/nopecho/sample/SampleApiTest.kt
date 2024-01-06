package io.nopecho.sample

import io.nopecho.sample.domain.Sample
import io.nopecho.sample.infrastructure.SampleInMemoryAdapter
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.emptyString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration
import kotlin.reflect.full.memberProperties
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper as API

@ExtendWith(RestDocumentationExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleApiTest(
    @LocalServerPort var port: Int
) {

    @Autowired
    private lateinit var repository: SampleInMemoryAdapter

    private lateinit var spec: RequestSpecification

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        spec = RequestSpecBuilder()
            .setPort(port)
            .addFilter(
                documentationConfiguration(restDocumentation).operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            )
            .build()
    }

    @Test
    fun `샘플 단건 조회`() {
        val sample = Sample(
            id = "1",
            name = "sample",
            description = ""
        )
        Given(spec) {
            repository.save(sample)
        } When {
            documents("샘플 단건을 조회 합니다.")
            contentType(ContentType.JSON)
            get("/samples/{sampleId}", "1")
        } Then {
            log().all()
            statusCode(200)
            body("id", `is`("1"))
            body("name", `is`("sample"))
            body("description", emptyString())
        }
    }
}

fun Given(spec: RequestSpecification, block: () -> Unit): RequestSpecification = block().run { given(spec).log().all() }

fun RequestSpecification.documents(description: String? = null): RequestSpecification = this.filter(
    API.document(
        identifier = "{method-name}",
        description = description,
        snippets = arrayOf(
            pathParameters(
                parameterWithName("sampleId").description("샘플 아이디")
            ),
//            queryParameters(),
//            requestHeaders(),
//            requestFields(),
            responseFields(
                fieldWithPath("id").description("샘플 아이디").type(STRING),
                fieldWithPath("name").description("샘플 이름").type(STRING),
                fieldWithPath("description").description("샘플 설명").type(STRING)
            )
        )
    )
)

fun convertMap(any: Any): Map<String, Any?> {
    return any::class.memberProperties
        .associate { it.name to it.call(any) }
}