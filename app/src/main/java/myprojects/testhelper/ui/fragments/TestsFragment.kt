package myprojects.testhelper.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import myprojects.testhelper.R


class TestsFragment : BaseFragment() {

    override var bottomNavigationVisibility: Int = View.VISIBLE
    override var toolbarVisibility: Int = View.VISIBLE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tests, container, false)
    }


}