package myprojects.testhelper.application

import android.app.Application
import myprojects.testhelper.database.remote.Appwrite

class TestHelperApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Appwrite.getInstance().initialize(applicationContext)
    }

}