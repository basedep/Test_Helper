package myprojects.testhelper.database.remote

import io.appwrite.models.Document
import io.appwrite.models.Session
import myprojects.testhelper.model.Questions
import myprojects.testhelper.model.Students
import myprojects.testhelper.model.TestResults
import myprojects.testhelper.model.Tests

interface AppwriteAPI {

    suspend fun login(email: String, password: String): Session
    suspend fun getUserCredential(): Map<String, String>
    suspend fun deleteSession(sessionId: String)

    suspend fun getAllGroups(): List<Document<Map<String, Any>>>
    suspend fun getAllGroupsUsers(ids: List<String>): List<Document<Map<String, Any>>>

    suspend fun uploadTest(test: Tests, questions: List<Questions>)

    suspend fun getAllCreatedTestByAuthor(author: String): List<Document<Map<String, Any>>>

    suspend fun requireQuestionsById(ids: List<String>): List<Document<Map<String, Any>>>
    suspend fun updateTest(test: Tests, questions: List<Questions>)

    suspend fun deleteTest(test: Tests, questionsIds: List<String>)

    suspend fun designateTestForGroup(studentsOfGroup: List<Students>)

    suspend fun getStudentById(id: String): List<Document<Map<String, Any>>>
    suspend fun updateDataForStudent(student: Students)
    suspend fun getAllAvailableTests(ids: List<String>): List<Document<Map<String, Any>>>

    suspend fun uploadTestResult(testResults: TestResults)
    suspend fun getTestsResultsByStudentId(id: String): List<Document<Map<String, Any>>>


}