package myprojects.testhelper.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import myprojects.testhelper.R
import myprojects.testhelper.adapter.AvailableTestAdapter
import myprojects.testhelper.model.Tests
import myprojects.testhelper.utils.SessionUtil
import myprojects.testhelper.viewmodels.MyViewModel
import kotlin.math.log


class AvailableFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.VISIBLE
    override var toolbarVisibility: Int = View.VISIBLE

    private var viewModel: MyViewModel? = null

    private lateinit var adapter: AvailableTestAdapter
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_available, container, false)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]


        recyclerView = view.findViewById(R.id.availableTestsRecycler)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val student = viewModel?.getStudentById(SessionUtil(requireContext()).getPreference("userId"))?.await()
            val tests = viewModel?.getAllAvailableTests(student?.availableTests!!)?.await()
            Log.d("myLog", "onViewCreated: $tests")

            adapter = AvailableTestAdapter(requireContext(), tests!!) { selectedTest ->
                val bundle = Bundle().apply {
                    putSerializable("selectedTest", selectedTest)
                }
                findNavController().navigate(R.id.action_availableFragment_to_startedTestFragment, bundle)
            }
            recyclerView?.adapter = adapter
        }


    }

}