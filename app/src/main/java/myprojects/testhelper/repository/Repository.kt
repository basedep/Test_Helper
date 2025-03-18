package myprojects.testhelper.repository

import io.appwrite.Query
import io.appwrite.models.Document
import io.appwrite.models.Session
import myprojects.testhelper.database.remote.Appwrite
import myprojects.testhelper.database.remote.AppwriteAPI
import myprojects.testhelper.model.Questions
import myprojects.testhelper.model.Students
import myprojects.testhelper.model.TestResults
import myprojects.testhelper.model.Tests
import myprojects.testhelper.utils.Constants.COLLECTION_GROUPS
import myprojects.testhelper.utils.Constants.COLLECTION_QUESTIONS
import myprojects.testhelper.utils.Constants.COLLECTION_STUDENTS
import myprojects.testhelper.utils.Constants.COLLECTION_TESTS
import myprojects.testhelper.utils.Constants.COLLECTION_TEST_RESULTS
import myprojects.testhelper.utils.Constants.DATABASE_ID

class Repository : AppwriteAPI {

    private val appwriteDatabase = Appwrite.getInstance().getDatabaseInstance()
    private val appwriteAccount = Appwrite.getInstance().getAccountInstance()

    //Логирование и сессии
    override suspend fun login(email: String, password: String): Session =
        appwriteAccount.createEmailSession(email, password)

    override suspend fun getUserCredential(): Map<String, String> {
        val credentials = appwriteAccount.get()
        return mapOf(
            "email" to credentials.email,
            "name" to credentials.name
        )
    }

    override suspend fun deleteSession(sessionId: String) {
        appwriteAccount.deleteSession(sessionId = sessionId)
    }


    //Отображение групп и студеннтов
    override suspend fun getAllGroups(): List<Document<Map<String, Any>>> {
        return appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_GROUPS).documents
    }

    override suspend fun getAllGroupsUsers(ids: List<String>): List<Document<Map<String, Any>>> {
        return appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_STUDENTS,
            queries = listOf(Query.equal( "id", ids))).documents
    }

    override suspend fun uploadTest(test: Tests, questions: List<Questions>){
        appwriteDatabase.createDocument(DATABASE_ID, COLLECTION_TESTS, test.id, test)

        for(question in questions)
            appwriteDatabase.createDocument(DATABASE_ID, COLLECTION_QUESTIONS, question.id, question)
    }

    override suspend fun getAllCreatedTestByAuthor(author: String): List<Document<Map<String, Any>>> {
        return  appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_TESTS,
            queries = listOf(Query.equal("author", author))).documents
    }

    override suspend fun requireQuestionsById(ids: List<String>): List<Document<Map<String, Any>>> {
        return  appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_QUESTIONS,
            queries = listOf(Query.equal("id", ids))).documents
    }

    override suspend fun updateTest(test: Tests, questions: List<Questions>) {
        appwriteDatabase.updateDocument(DATABASE_ID, COLLECTION_TESTS, test.id, test)

        for(question in questions)
            appwriteDatabase.updateDocument(DATABASE_ID, COLLECTION_QUESTIONS, question.id, question)
    }

    override suspend fun deleteTest(test: Tests, questionsIds: List<String>) {
        appwriteDatabase.deleteDocument(DATABASE_ID, COLLECTION_TESTS, test.id)

        for(question in questionsIds)
            appwriteDatabase.deleteDocument(DATABASE_ID, COLLECTION_QUESTIONS, question)
    }

    suspend fun addQuestions(questions: List<Questions>){
        for(question in questions)
            appwriteDatabase.createDocument(DATABASE_ID, COLLECTION_QUESTIONS, question.id, question)
    }

    override suspend fun designateTestForGroup(studentsOfGroup: List<Students>) {
        for(student in studentsOfGroup)
            appwriteDatabase.updateDocument(DATABASE_ID, COLLECTION_STUDENTS, student.id, student)
    }

    override suspend fun getStudentById(id: String): List<Document<Map<String, Any>>> {
        return appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_STUDENTS,
            queries = listOf(Query.equal("id", id))).documents
    }

    override suspend fun updateDataForStudent(student: Students) {
        appwriteDatabase.updateDocument(DATABASE_ID, COLLECTION_STUDENTS, student.id, student)
    }

    override suspend fun getAllAvailableTests(ids: List<String>): List<Document<Map<String, Any>>> {
        return  appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_TESTS,
            queries = listOf(Query.equal("id", ids))).documents
    }

    override suspend fun uploadTestResult(testResults: TestResults) {
        appwriteDatabase.createDocument(DATABASE_ID, COLLECTION_TEST_RESULTS, testResults.testId, testResults)
    }

    override suspend fun getTestsResultsByStudentId(id: String): List<Document<Map<String, Any>>> {
        return appwriteDatabase.listDocuments(DATABASE_ID, COLLECTION_TEST_RESULTS,
            queries = listOf(Query.equal("studentId", id))).documents
    }


}