package myprojects.testhelper.database.remote

import io.appwrite.models.Session

interface AppwriteAPI {

    suspend fun login(email: String, password: String): Session
    suspend fun getUserCredential(): Map<String, String>
    suspend fun deleteSession(sessionId: String)

}