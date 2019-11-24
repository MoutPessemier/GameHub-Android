package digitized.gamehub.map

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import digitized.gamehub.R
import digitized.gamehub.databinding.MapFragmentBinding
import timber.log.Timber

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    LocationListener {

    private lateinit var map: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: MapViewModel
    private lateinit var locationManager: LocationManager


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding
        val binding: MapFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, MapViewModel.Factory(application))
            .get(MapViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // LocationManager
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        // asks for position updates
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 100f, this)
        } catch (e: SecurityException) {
            Timber.d(e.localizedMessage)
        }

    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map!!

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        configureMap()
        placeMarkersOnMap(map)
    }


    private fun configureMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true

        // current position
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { newLocation ->
            if (newLocation != null) {
                currentLocation = newLocation
                val currentLatLng = LatLng(newLocation.latitude, newLocation.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
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

    // EXTRA: onclick --> navigate to detail screen
    override fun onMarkerClick(p0: Marker?) = false

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //deprecated
    }

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {
        Toast.makeText(context, "Enable your location please.", Toast.LENGTH_LONG).show()
    }

    // updates the position
    override fun onLocationChanged(location: Location?) {
        val currentLocationLat = location?.latitude
        val currentLocationLong = location?.longitude
        viewModel.updateUserLocation(currentLocationLat, currentLocationLong)
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(this)
    }


}