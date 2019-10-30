package digitized.gamehub.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AccountSettingsViewModel(application: Application) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        application.applicationContext.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun logout() {
        sharedPreferences.edit().apply {
            putBoolean("isLoggedIn", false)
            putString("userId", null)
        }.apply()
    }

    fun updateAccount() {

    }
}