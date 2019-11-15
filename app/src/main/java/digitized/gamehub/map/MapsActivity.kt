package digitized.gamehub.map

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.location.*
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import digitized.gamehub.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit  var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        locationManager.requestLocationUpdates(0, 100f, Criteria(), {
//            location: Location -> Timber.i(location.toString())
//        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val ghent = LatLng(51.0543, 3.7174)
        mMap.addMarker(MarkerOptions().position(ghent).title("Marker in Ghent"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ghent))
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
       //deprecated
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        Toast.makeText(applicationContext, "Enable your location please.", Toast.LENGTH_LONG).show()
    }

    override fun onLocationChanged(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
