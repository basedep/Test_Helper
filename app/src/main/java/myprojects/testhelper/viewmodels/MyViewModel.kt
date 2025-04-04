package myprojects.testhelper.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.appwrite.models.Document
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import myprojects.testhelper.model.Groups
import myprojects.testhelper.model.ParentItem
import myprojects.testhelper.model.Questions
import myprojects.testhelper.model.Students
import myprojects.testhelper.model.TestResults
import myprojects.testhelper.model.Tests
import myprojects.testhelper.repository.Repository
import myprojects.testhelper.utils.Constants

class MyViewModel : ViewModel(){

    private val repository: Repository = Repository()
    var groups: MutableLiveData<List<ParentItem>> = MutableLiveData<List<ParentItem>>()
    var tests: MutableLiveData<List<Tests>> = MutableLiveData<List<Tests>>()
    var questions: MutableLiveData<MutableList<Questions>> = MutableLiveData<MutableList<Questions>>()

    init {
        getAllGroups()
    }

    fun login(email: String, password: String) = viewModelScope.async{
        repository.login(email, password)
    }

    fun getUserCredentials() = viewModelScope.async {
        repository.getUserCredential()
    }

    fun deleteSession(sessionId: String) = viewModelScope.launch {
        repository.deleteSession(sessionId)
    }

    fun uploadTest(test: Tests, questions: List<Questions>) = viewModelScope.launch {
        repository.uploadTest(test, questions)
    }

    fun getAllTestsByAuthor(author: String) = viewModelScope.launch{
        val testsResult = extractData(repository.getAllCreatedTestByAuthor(author), Tests::class.java)
        tests.postValue(testsResult)
    }


    fun requireQuestionsById(ids: List<String>) = viewModelScope.launch{
        val questionsResult = extractData(repository.requireQuestionsById(ids), Questions::class.java)
        questions.postValue(questionsResult.toMutableList())
    }

    fun updateTest(test: Tests, questions: List<Questions>) = viewModelScope.launch{
        repository.updateTest(test, questions)
    }

    fun deleteTest(test: Tests, questionsIds: List<String>) = viewModelScope.launch {
        repository.deleteTest(test, questionsIds)
    }

    fun addQuestions(questions: List<Questions>) = viewModelScope.launch{
        repository.addQuestions(questions)
    }

    fun designateTestForGroup(studentsOfGroup: List<Students>) = viewModelScope.launch{
        repository.designateTestForGroup(studentsOfGroup)
    }


    fun getStudentById(id: String) = viewModelScope.async{
        val student = extractData(repository.getStudentById(id), Students::class.java)[0]
        student
    }

    fun getAllAvailableTests(ids: List<String>) = viewModelScope.async{
        val testsResult = extractData(repository.getAllAvailableTests(ids), Tests::class.java)
        testsResult
    }

    fun updateDataForStudent(student: Students)  = viewModelScope.launch{
       repository.updateDataForStudent(student)
    }

    fun uploadTestResult(testResults: TestResults) = viewModelScope.launch {
        repository.uploadTestResult(testResults)
    }

    fun getTestResults(id: String)= viewModelScope.async{
        val testsResult = extractData(repository.getTestsResultsByStudentId(id), TestResults::class.java)
        testsResult
    }

    fun getAllGroups() = viewModelScope.launch {

        val allGroups = extractData(repository.getAllGroups(), Groups::class.java)
        val items: MutableList<ParentItem> = mutableListOf()

        for (group in allGroups) {
            // Извлекаем идентификаторы пользователей группы
            val userIds = group.students.map {
                it
            }
            // Получаем студентов группы
            val groupStudents = extractData(repository.getAllGroupsUsers(userIds), Students::class.java)

            val groupName = group.groupName

            items.add(ParentItem(groupName, groupStudents))
        }
        groups.postValue(items)
    }


    private fun <T> extractData(documents: List<Document<Map<String, Any>>>, clazz: Class<T>): List<T> {
        val gson = Gson()
        val dataList = mutableListOf<T>()

        for (document in documents) {
            val dataMap: Map<String, Any> = document.toMap()
            val json = gson.toJson(dataMap["data"])

            val data: T = gson.fromJson(json, clazz)

            dataList.add(data)
        }

        return dataList
    }


}

