package com.example

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import org.example.dgs.generated.types.RequiredTestType

@DgsComponent
class TestController {

    @DgsQuery
//    fun test(): RequiredTestType = RequiredTestType(isRequired = true) // Working case
    fun test(): RequiredTestType = RequiredTestType.Builder().withIsRequired(true).build() // Non working case
}
