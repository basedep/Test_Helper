package myprojects.testhelper.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AlertDialog
import myprojects.testhelper.R
import myprojects.testhelper.model.Questions
import myprojects.testhelper.model.Tests
import myprojects.testhelper.viewmodels.MyViewModel
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import myprojects.testhelper.model.TestResults
import myprojects.testhelper.utils.SessionUtil

class StartedTestFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.GONE
    override var toolbarVisibility: Int = View.GONE

    private var test: Tests? = null
    private var questions: MutableList<Questions> = mutableListOf()
    private var viewModel: MyViewModel? = null
    private var currentQuestionIndex = 0
    private val correctAnswers = mutableListOf<Questions>()
    private val incorrectAnswers = mutableListOf<Questions>()

    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_strated_test, container, false)

        arguments?.let { bundle ->
            test = bundle.getSerializable("selectedTest") as Tests
        }

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel?.requireQuestionsById(test?.questionsIds!!)

        // Привязка элементов интерфейса
        questionTextView = view.findViewById(R.id.questionTextView)
        option1Button = view.findViewById(R.id.option1Button)
        option2Button = view.findViewById(R.id.option2Button)
        option3Button = view.findViewById(R.id.option3Button)
        option4Button = view.findViewById(R.id.option4Button)
        nextButton = view.findViewById(R.id.nextButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.questions?.observe(viewLifecycleOwner) { questionList ->
            questions.addAll(questionList)
            Log.d("myLog", "onViewCreated: $questions")
            displayQuestion()
        }

        // Устанавливаем обработчики нажатий для кнопок
        option1Button.setOnClickListener { onAnswerSelected(option1Button.text.toString()) }
        option2Button.setOnClickListener { onAnswerSelected(option2Button.text.toString()) }
        option3Button.setOnClickListener { onAnswerSelected(option3Button.text.toString()) }
        option4Button.setOnClickListener { onAnswerSelected(option4Button.text.toString()) }
        nextButton.setOnClickListener { onNextClicked() }
    }

    private fun displayQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            questionTextView.text = currentQuestion.question
            option1Button.text = currentQuestion.option1
            option2Button.text = currentQuestion.option2
            option3Button.text = currentQuestion.option3
            option4Button.text = currentQuestion.option4
        } else {
            showResults()
        }
    }

    private fun onAnswerSelected(selectedAnswer: String) {
        val currentQuestion = questions[currentQuestionIndex]
        if (selectedAnswer == currentQuestion.answer) {
            correctAnswers.add(currentQuestion)
        } else {
            incorrectAnswers.add(currentQuestion)
        }
    }

    private fun onNextClicked() {
        currentQuestionIndex++
        displayQuestion()
    }

    private fun showResults() {
        val correctCount = correctAnswers.size
        val incorrectCount = incorrectAnswers.size

        val resultMessage = "Правильные ответы: $correctCount\nНеправильные ответы: $incorrectCount"

        lifecycleScope.launch {

            val testResults = TestResults(
                testId = test?.id!!,
                studentId = SessionUtil(requireContext()).getPreference("userId"),
                correct = correctCount,
                wrong = incorrectCount,
                testName = test?.nameOfTest!!
            )
            val student = viewModel?.getStudentById(SessionUtil(requireContext()).getPreference("userId"))?.await()

            student?.let {
                val updatedPassedTests = it.passedTests.toMutableList()
                updatedPassedTests.add(testResults.testId)
                val updatedStudent = it.copy(passedTests = updatedPassedTests)

                viewModel?.uploadTestResult(testResults)
                viewModel?.updateDataForStudent(updatedStudent)
            }
        }


        AlertDialog.Builder(requireContext())
            .setTitle("Результаты теста")
            .setMessage(resultMessage)
            .setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_startedTestFragment_to_availableTestsFragment)
            }
            .show()
    }
}


