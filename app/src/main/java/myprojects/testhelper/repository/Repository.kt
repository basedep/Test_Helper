package myprojects.testhelper.repository

import io.appwrite.models.Session
import myprojects.testhelper.database.remote.Appwrite
import myprojects.testhelper.database.remote.AppwriteAPI

class Repository : AppwriteAPI {

    private val appwriteDatabase = Appwrite.getInstance().getDatabaseInstance()
    private val appwriteAccount = Appwrite.getInstance().getAccountInstance()

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

}