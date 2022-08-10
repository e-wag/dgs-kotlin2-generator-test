package com.example

import com.jayway.jsonpath.TypeRef
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.example.dgs.generated.client.TestGraphQLQuery
import org.example.dgs.generated.client.TestProjectionRoot
import org.example.dgs.generated.types.RequiredTestType
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class SpringBootIntegrationTest

class TestControllerTest(
    private val dgsQueryExecutor: DgsQueryExecutor
): SpringBootIntegrationTest() {

    @Test
    fun `receive test`() {
        val query = TestGraphQLQuery.Builder().build()
        val projection = TestProjectionRoot().isRequired
        val request = GraphQLQueryRequest(
            query = query,
            projection = projection,
        )

        val result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
            request.serialize(),
            "data.test",
            object : TypeRef<RequiredTestType>() {},
        )

        assert(result.isRequired)
    }
}
