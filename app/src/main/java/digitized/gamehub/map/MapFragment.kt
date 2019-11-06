package digitized.gamehub.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import digitized.gamehub.R
import digitized.gamehub.databinding.MapBinding

class MapFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: MapBinding =
            DataBindingUtil.inflate(inflater, R.layout.map, container, false)

        val mapActivity = Intent(context, MapsActivity::class.java)
        startActivity(mapActivity);

        return binding.root
    }
}