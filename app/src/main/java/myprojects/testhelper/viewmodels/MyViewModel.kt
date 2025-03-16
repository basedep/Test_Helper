package myprojects.testhelper.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import myprojects.testhelper.repository.Repository

class MyViewModel() : ViewModel(){

    private val repository: Repository = Repository()

    fun login(email: String, password: String) = viewModelScope.async{
        repository.login(email, password)
    }

    fun getUserCredentials() = viewModelScope.async {
        repository.getUserCredential()
    }

    fun deleteSession(sessionId: String) = viewModelScope.launch {
        repository.deleteSession(sessionId)
    }
}