package digitized.gamehub.cardStack

import android.content.Context
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

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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

        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { newLocation ->
            if (newLocation != null) {
                viewModel.updateUserLocation(newLocation.latitude, newLocation.longitude)
            }
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 100f, this)
        } catch (e: SecurityException) {
            Timber.d(e.localizedMessage)
        }

        viewModel.getPartiesNearYou()

        viewModel.games.observe(this, Observer {
            adapter.games = it
        })

        viewModel.parties.observe(this, Observer {
            adapter.setParties(it)
            var callback = PartyDiffCallback(listOf(), it)
            var result = DiffUtil.calculateDiff(callback)
            result.dispatchUpdatesTo(adapter)

        })

        setupButtons()
    }

    /**
     * Called when card disappears
     */
    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("position = $position")
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
        if (direction?.name == "left") {
            viewModel.declineParty(viewModel.currentParty!!.id!!, viewModel.usr!!.id)
        }
        if (direction?.name == "right") {
            viewModel.joinParty(viewModel.currentParty!!.id!!, viewModel.usr!!.id)
        }
        if (manager.topPosition == adapter.itemCount - 5) {
            //paginate()
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
        Timber.d("position = $position")
        viewModel.currentParty = adapter.getParties()[position]
    }

    /**
     * Called when the rewound button is pressed
     */
    override fun onCardRewound() {
        Timber.d("onCardCanceled: ${manager.topPosition}")
    }

    /**
     * Sets up the function of the like and dislike button
     */
    private fun setupButtons() {
        val skip = view!!.findViewById<View>(R.id.skip_button)
        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            viewModel.declineParty(viewModel.currentParty!!.id!!, viewModel.usr!!.id)
            cardStackView.swipe()
        }

        val like = view!!.findViewById<View>(R.id.like_button)
        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            viewModel.joinParty(viewModel.currentParty!!.id!!, viewModel.usr!!.id)
            cardStackView.swipe()
        }
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

    /**
     * If the cardstack is almost empty, this will fetch new parties and add them to the stack
     */
    private fun paginate() {
        var old = adapter.getParties()
        //viewModel.refreshPartiesNearYou()
        val new = old.plus(adapter.getParties())
        val callback = PartyDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        //adapter.setParties(new)
        //result.dispatchUpdatesTo(adapter)
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