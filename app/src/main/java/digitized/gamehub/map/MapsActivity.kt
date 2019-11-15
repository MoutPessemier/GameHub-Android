package digitized.gamehub.map

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.location.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import digitized.gamehub.R
import timber.log.Timber

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000F, this)
        } catch (e: SecurityException) {
            Timber.d(e.localizedMessage)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val ghent = LatLng(51.0543, 3.7174)
        mMap.addMarker(MarkerOptions().position(ghent).title("Marker in Ghent"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ghent))
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
