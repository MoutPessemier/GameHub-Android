package digitized.gamehub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import digitized.gamehub.databinding.MapBinding

class MapActivity : AppCompatActivity() {
    private lateinit var binding: MapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.map)
    }
}