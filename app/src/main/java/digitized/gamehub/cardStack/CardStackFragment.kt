package digitized.gamehub.cardStack

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yuyakaido.android.cardstackview.*
import digitized.gamehub.R
import digitized.gamehub.databinding.CardStackFragmentBinding
import timber.log.Timber

class CardStackFragment : Fragment(), CardStackListener, LocationListener {

    private lateinit var binding: CardStackFragmentBinding
    private lateinit var viewModel: CardStackViewModel
    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager

    /**
     * The permission request code needed to let google maps function
     */
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.card_stack_fragment, container, false)

        binding.lifecycleOwner = this

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, CardStackViewModel.Factory(application))
            .get(CardStackViewModel::class.java)
        binding.viewModel = viewModel

        // CardStack
        cardStackView = binding.cardStackView
        initializeCardStackView()

        // current location
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        // updating the location
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // supported screen orientaion
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // check if location service is allowd, if not ask for permission
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

        // get current (last) location and if the user exists, update this location
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { newLocation ->
            if (newLocation != null) {
                viewModel.currentLocation = newLocation
                if (viewModel.usr != null) {
                    viewModel.updateUserLocation(newLocation.latitude, newLocation.longitude)
                }
            }
        }

        // keep watching for new location updates every 3 minutes or 100 m
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 100f, this)
        } catch (e: SecurityException) {
            Timber.d(e.localizedMessage)
        }

        // if user exists (comes back from backend) and his location has been set, get his parties
        viewModel.user.observe(this, Observer {
            viewModel.usr = it
            if (viewModel.usr!!.latitude != null && viewModel.usr!!.longitude != null) {
                viewModel.getPartiesNearYou()
            }
        })

        // get all games
        viewModel.games.observe(this, Observer {
            adapter.games = it
        })

        // update the adapter to show the new parties
        viewModel.parties.observe(this, Observer {
            adapter.setParties(it)
            var callback = PartyDiffCallback(listOf(), it)
            var result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(adapter)

        })

        // set up the buttons (join and decline)
        setupButtons()
    }

    /**
     * Called when card disappears
     */
    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("onCardDissapeared: position = $position")
    }

    /**
     * Called when draging a card around the screen
     */
    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Timber.d("onCardDragging: d = ${direction?.name}, r = $ratio")
    }

    /**
     * Called when the card is swiped away
     */
    override fun onCardSwiped(direction: Direction?) {
        Timber.d("onCardDragging: d = ${direction?.name}")
        Timber.d("listSize: ${adapter.getParties().size}")
        if (direction?.name == "Left") {
            viewModel.declineParty(adapter.currentParty!!.id!!, viewModel.usr!!.id)
        }
        if (direction?.name == "Right") {
            viewModel.joinParty(adapter.currentParty!!.id!!, viewModel.usr!!.id)
        }
    }

    /**
     * Sets up the function of the like and dislike button
     */
    private fun setupButtons() {
        val skip = binding.skipButton
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        val like = binding.likeButton
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }

    /**
     * Called when the card gets released and thus canceled
     */
    override fun onCardCanceled() {
        Timber.d("onCardCanceled: ${manager.topPosition}")
    }

    /**
     * Called when a card is drawn on the screen
     */
    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("onCardAppeared: position = $position")
    }

    /**
     * Called when the rewound button is pressed
     */
    override fun onCardRewound() {
        Timber.d("onCardRewound: ${manager.topPosition}")
    }


    /**
     * Sets up thje CardStackView. It adds the required dependencies
     * and sets up the Visuals for the component.
     */
    private fun initializeCardStackView() {
        manager = CardStackLayoutManager(context, this)
        adapter = CardStackAdapter()
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter

        manager.setStackFrom(StackFrom.Top)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //deprecated
    }

    /**
     * Called if permission is granted to use location service
     */
    override fun onProviderEnabled(provider: String?) {}

    /**
     * Called if no permission is gratned to use location service
     */
    override fun onProviderDisabled(provider: String?) {
        Toast.makeText(context, "Enable your location please.", Toast.LENGTH_LONG).show()
    }

    /**
     * Called each interval the location changes, updates user's current location
     */
    override fun onLocationChanged(location: Location?) {
        val currentLocationLat = location?.latitude
        val currentLocationLong = location?.longitude
        viewModel.updateUserLocation(currentLocationLat, currentLocationLong)
    }
}