package myprojects.testhelper.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import myprojects.testhelper.R
import myprojects.testhelper.utils.SessionUtil
import myprojects.testhelper.viewmodels.MyViewModel

class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null
    private var toolbar: Toolbar? = null
    private var viewModel: MyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navigationController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView?.setupWithNavController(navigationController)


    }

    override fun onResume() {
        super.onResume()
        toolbar?.title = SessionUtil(this).getPreference("userName").ifBlank { "TestHelper" }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.logout_menu_item ->{
               viewModel?.deleteSession(SessionUtil(this).getPreference("sessionId"))
               SessionUtil(this).clearSessionPreferences()
               Toast.makeText(this, "Вы вышли из системы", Toast.LENGTH_SHORT).show()
               finish()
           }
       }

        return super.onOptionsItemSelected(item)
    }

    fun setVisibilityForBottomNavigationView(visibility: Int){
        bottomNavigationView?.visibility = visibility
    }

    fun setVisibilityForToolbar(visibility: Int){
        toolbar?.visibility = visibility
    }

}