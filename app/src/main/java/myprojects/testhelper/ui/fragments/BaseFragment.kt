package myprojects.testhelper.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import myprojects.testhelper.ui.activities.MainActivity

abstract class BaseFragment : Fragment() {

    protected open var bottomNavigationVisibility = View.VISIBLE
    protected open var toolbarVisibility = View.VISIBLE

    private lateinit var mainActivity: MainActivity

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is MainActivity){
            mainActivity = activity as MainActivity
            mainActivity.setVisibilityForBottomNavigationView(bottomNavigationVisibility)
            mainActivity.setVisibilityForToolbar(toolbarVisibility)
        }
    }

    override fun onStart() {
        super.onStart()
        mainActivity.setVisibilityForBottomNavigationView(bottomNavigationVisibility)
        mainActivity.setVisibilityForToolbar(toolbarVisibility)
    }


}