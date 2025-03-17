package myprojects.testhelper.ui.fragments

import TestAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import myprojects.testhelper.R
import myprojects.testhelper.model.Tests
import myprojects.testhelper.utils.SessionUtil
import myprojects.testhelper.viewmodels.MyViewModel


class TestsFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.VISIBLE
    override var toolbarVisibility: Int = View.VISIBLE

    private var fab: FloatingActionButton? = null
    private var recyclerTests: RecyclerView? = null
    private var viewModel: MyViewModel? = null

    private lateinit var testsAdapter: TestAdapter
    private val testsList = mutableListOf<Tests>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_tests, container, false)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel?.getAllTestsByAuthor(SessionUtil(requireContext()).getPreference("userName"))
        fab = view.findViewById(R.id.floatingButtonCreateTest)
        recyclerTests = view.findViewById(R.id.recycler_test_fragment)

        recyclerTests?.layoutManager = LinearLayoutManager(requireContext())

        testsAdapter = TestAdapter(testsList,
            onEditClick = { test ->

                val bundle = Bundle().apply {
                    putSerializable("test", test)
                }
                findNavController().navigate(R.id.action_testsFragment_to_testMakerFragment, bundle)
            },
            onDeleteClick = { test ->
                Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerTests?.adapter = testsAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab?.setOnClickListener {
            findNavController().navigate(R.id.action_testsFragment_to_testMakerFragment)
        }

        viewModel?.tests?.observe(viewLifecycleOwner){
            testsAdapter.updateTests(it)
        }

    }

}