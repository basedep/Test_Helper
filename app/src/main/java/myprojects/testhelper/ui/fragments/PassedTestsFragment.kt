package myprojects.testhelper.ui.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import myprojects.testhelper.R
import myprojects.testhelper.adapter.TestResultsAdapter
import myprojects.testhelper.model.TestResults
import myprojects.testhelper.utils.SessionUtil
import myprojects.testhelper.viewmodels.MyViewModel

class PassedTestsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TestResultsAdapter
    private var testResultsList: List<TestResults> = listOf()
    private var viewModel: MyViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_passed_tests, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPassedTests)
        adapter = TestResultsAdapter(testResultsList)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            val results = viewModel?.getTestResults(SessionUtil(requireContext()).getPreference("userId"))?.await()

            Log.d("myLog", "onCreateView: $results")
            adapter.updateData(results!!)
        }

        return view
    }

}
