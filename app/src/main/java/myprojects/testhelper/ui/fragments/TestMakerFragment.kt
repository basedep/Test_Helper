package myprojects.testhelper.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import myprojects.testhelper.R
import myprojects.testhelper.adapter.QuestionsAdapter
import myprojects.testhelper.model.Questions
import myprojects.testhelper.model.Tests
import myprojects.testhelper.utils.SessionUtil
import myprojects.testhelper.viewmodels.MyViewModel
import java.util.UUID


class TestMakerFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.GONE
    override var toolbarVisibility: Int = View.GONE

    private var fab: FloatingActionButton? = null
    private var recyclerQuestions: RecyclerView? = null
    private var questionsAdapter: QuestionsAdapter? = null
    private var uploadTestButton: Button? = null
    private var testName: EditText? = null
    private val questionsList = mutableListOf<Questions>()
    private var viewModel: MyViewModel? = null

    private var test: Tests? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test_maker, container, false)

        arguments?.let { bundle ->
            test = bundle.getSerializable("test") as Tests
        }

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        fab = view.findViewById(R.id.fabAddQuestion)
        uploadTestButton = view.findViewById(R.id.button_finish_test)
        testName = view.findViewById(R.id.test_name_editText)
        testName?.setText(test?.nameOfTest)

        recyclerQuestions = view.findViewById(R.id.recycler_test_maker_fragment)
        recyclerQuestions?.layoutManager = LinearLayoutManager(requireContext())
        questionsAdapter = QuestionsAdapter(questionsList)
        recyclerQuestions?.adapter = questionsAdapter

        if (test != null)
            viewModel?.requireQuestionsById(test!!.questionsIds)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       viewModel?.questions?.observe(viewLifecycleOwner){
           questionsAdapter?.setUpdatedQuestions(it)

       }

        fab?.setOnClickListener {
            val newQuestion = Questions(
                id = UUID.randomUUID().toString(),
                question = "",
                option1 = "",
                option2 = "",
                option3 = "",
                option4 = "",
                answer = ""
            )
            questionsAdapter?.addQuestion(newQuestion)
        }


        uploadTestButton?.setOnClickListener {

            if(test == null) {
                val newTest = Tests(
                    id = UUID.randomUUID().toString(),
                    nameOfTest = testName?.text.toString(),
                    questionsIds = questionsAdapter?.getListOfQuestions()!!.map { it.id },
                    author = SessionUtil(requireContext()).getPreference("userName")
                )
                viewModel?.uploadTest(newTest, questionsAdapter?.getListOfQuestions()!!)
                Toast.makeText(requireContext(), "Тест создан", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_testMakerFragment_to_testsFragment)
            }else{

                val originalQuestionIds = test?.questionsIds
                val newQuestions = questionsAdapter?.getListOfQuestions()?.filter { question ->
                    !originalQuestionIds!!.contains(question.id)
                }
                if (newQuestions != null) {
                    viewModel?.addQuestions(newQuestions)
                }

                val updatedTest = test?.copy(nameOfTest = testName?.text.toString(),
                                            questionsIds = questionsAdapter?.getListOfQuestions()!!.map { it.id })

                viewModel?.updateTest(updatedTest!!, questionsAdapter?.getListOfQuestions()!!)
                Toast.makeText(requireContext(), "Тест обновлен", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_testMakerFragment_to_testsFragment)
            }
        }
    }

}