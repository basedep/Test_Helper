package myprojects.testhelper.repository

import io.appwrite.Query
import io.appwrite.models.Document
import io.appwrite.models.Session
import myprojects.testhelper.database.remote.Appwrite
import myprojects.testhelper.database.remote.AppwriteAPI
import myprojects.testhelper.utils.Constants.COLLECTION_GROUPS
import myprojects.testhelper.utils.Constants.COLLECTION_STUDENTS
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

}