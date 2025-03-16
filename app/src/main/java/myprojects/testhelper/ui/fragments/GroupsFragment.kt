package myprojects.testhelper.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import myprojects.testhelper.R
import myprojects.testhelper.utils.SessionUtil


class GroupsFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.VISIBLE
    override var toolbarVisibility: Int = View.VISIBLE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val userSession = SessionUtil(requireContext()).getPreference("sessionId")
        if (userSession.isBlank())
            findNavController().navigate(R.id.action_groupsFragment_to_loginFragment)

        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

}