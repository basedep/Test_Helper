package myprojects.testhelper.model

data class TestResults (
    val testId: String,
    val studentId: String,
    val correct: Int,
    val wrong: Int,
    val testName: String
)