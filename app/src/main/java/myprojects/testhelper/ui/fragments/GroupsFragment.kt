package myprojects.testhelper.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myprojects.testhelper.R
import myprojects.testhelper.adapter.GroupAdapter
import myprojects.testhelper.model.Groups
import myprojects.testhelper.model.Students
import myprojects.testhelper.utils.SessionUtil
import myprojects.testhelper.viewmodels.MyViewModel


class GroupsFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.VISIBLE
    override var toolbarVisibility: Int = View.VISIBLE

    private var recyclerViewGroups: RecyclerView? = null
    private var viewModel: MyViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        val userSession = SessionUtil(requireContext()).getPreference("sessionId")
        if (userSession.isBlank())
            findNavController().navigate(R.id.action_groupsFragment_to_loginFragment)

        recyclerViewGroups = view.findViewById(R.id.recycler_groups)
        recyclerViewGroups?.layoutManager = LinearLayoutManager(requireContext())
        val recyclerViewGroupsAdapter = GroupAdapter()
        recyclerViewGroups?.adapter = recyclerViewGroupsAdapter

        viewModel?.groups?.observe(viewLifecycleOwner){
            recyclerViewGroupsAdapter.differ.submitList(it)
        }

        return view
    }

}