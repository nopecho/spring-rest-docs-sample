package io.nopecho.sample

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Then
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration


@ExtendWith(RestDocumentationExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleApiTest(
    @LocalServerPort
    private var port: Int,
) {
    private lateinit var spec: RequestSpecification
    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        spec = RequestSpecBuilder()
            .setPort(port)
            .addFilter(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
            .build()
    }

    @Test
    fun get() {
        //given

        //when
        val response = given(spec).log().all()
            .filter(RestAssuredRestDocumentationWrapper.document(
                identifier = "{method-name}",
                snippets = arrayOf(ResourceDocumentation.resource())
            ))
            .contentType(ContentType.JSON)
            .get("/samples/{sampleId}", "1")

        //then
        response.Then {
            log().all()
                .assertThat().statusCode(500)
        }
    }
}
