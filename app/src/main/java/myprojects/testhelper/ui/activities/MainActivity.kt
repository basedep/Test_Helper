package myprojects.testhelper.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import myprojects.testhelper.R

class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView?.setupWithNavController(navigationController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.logout_menu_item ->{
               Toast.makeText(this, "Work", Toast.LENGTH_SHORT).show()
           }
           else -> false
       }

        return super.onOptionsItemSelected(item)
    }

    fun setVisibilityForBottomNavigationView(visibility: Int){
        bottomNavigationView?.visibility = visibility
    }

}