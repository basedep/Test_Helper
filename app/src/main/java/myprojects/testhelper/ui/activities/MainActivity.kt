package myprojects.testhelper.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import myprojects.testhelper.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}