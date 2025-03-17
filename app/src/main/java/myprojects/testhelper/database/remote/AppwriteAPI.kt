package myprojects.testhelper.database.remote

import io.appwrite.models.Document
import io.appwrite.models.Session
import myprojects.testhelper.model.Questions
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

}