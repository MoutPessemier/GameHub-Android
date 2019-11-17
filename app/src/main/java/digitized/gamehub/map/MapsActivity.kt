package digitized.gamehub.map

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.location.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import digitized.gamehub.R
import digitized.gamehub.databinding.ActivityMapsBinding
import timber.log.Timber
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // ViewModel
        viewModel = ViewModelProviders.of(this, MapViewModel.Factory(application))
            .get(MapViewModel::class.java)


        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100f, this)
        } catch (e: SecurityException) {
            Timber.d(e.localizedMessage)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // get current location and zoom in on that location
        placeMarkersOnMap(mMap)
    }

    private fun placeMarkersOnMap(map: GoogleMap) {
        viewModel.parties.observe(this, Observer { it ->
            it.forEach {
                val position = LatLng(it.location.coordinates[0], it.location.coordinates[1])
                map.addMarker(
                    MarkerOptions().position(position).title(it.name).icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                )
            }
        })
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //deprecated
    }

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {
        Toast.makeText(applicationContext, "Enable your location please.", Toast.LENGTH_LONG).show()
    }

    override fun onLocationChanged(location: Location?) {
        val currentLocationLat = location?.latitude
        val currentLocationLong = location?.longitude
        viewModel.updateUserLocation(currentLocationLat, currentLocationLong)
    }
}
