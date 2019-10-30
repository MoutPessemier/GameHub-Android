package digitized.gamehub.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class RegisterViewModel(application: Application) : ViewModel() {

    private val sharedPreferences: SharedPreferences = application.applicationContext.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}