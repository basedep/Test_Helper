package myprojects.testhelper.model

import java.io.Serializable

data class Tests(
    val id: String,
    val nameOfTest: String,
    val questionsIds: List<String>,
    val author: String
): Serializable
