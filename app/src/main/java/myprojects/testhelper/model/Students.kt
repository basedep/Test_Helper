package myprojects.testhelper.model

data class Students (
    val id: String,
    val name: String,
    val availableTests: List<String>,
    val passedTests: List<String>,
    val group: String
)