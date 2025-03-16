package myprojects.testhelper.model

data class Tests(
    val id: String,
    val nameOfTest: String,
    val questionsIds: List<String>
)
