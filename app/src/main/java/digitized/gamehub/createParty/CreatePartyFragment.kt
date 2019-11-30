package  digitized.gamehub.createParty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import digitized.gamehub.R
import digitized.gamehub.databinding.CreateGamePartyBinding
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class CreatePartyFragment : Fragment() {

    private lateinit var viewModel: CreatePartyViewModel
    private lateinit var binding: CreateGamePartyBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.create_game_party, container, false
            )

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, CreatePartyViewModel.Factory(application))
            .get(CreatePartyViewModel::class.java)
        binding.viewModel = viewModel

        spinnerAdapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item
        )

        if (!Places.isInitialized()) {
            Places.initialize(
                requireActivity().applicationContext,
                getString(R.string.google_maps_key)
            )
        }

        val placesClient = Places.createClient(requireActivity().applicationContext)
        placesClient.findAutocompletePredictions(FindAutocompletePredictionsRequest.newInstance("Berlin"))

        binding.sprPartyGame.adapter = spinnerAdapter
        binding.sprPartyGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val game = viewModel.games.value!![position]
                viewModel.game = game
            }

        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val autocompleteFragment =
            childFragmentManager
                .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )

        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS)

        autocompleteFragment.setActivityMode(AutocompleteActivityMode.OVERLAY)

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Timber.d("Place: %s, %s, %s, %s", place.name, place.id, place.latLng, place.address)
                viewModel.place = place
            }

            override fun onError(status: Status) {
                Timber.d("An error occurred: $status")
            }
        })

        viewModel.games.observe(viewLifecycleOwner, Observer {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it.map { game -> game.name })
        })

        binding.btnCreateGamParty.setOnClickListener { view: View ->
            val succes = viewModel.createParty(
                binding.txtPartyName.text.toString(),
                SimpleDateFormat().parse(binding.txtPartyDate.text.toString())!!,
                binding.txtMaxSize.text.toString().toInt()
            )
            if (succes) {
                binding.txtPartyName.text.clear()
                binding.txtMaxSize.text.clear()
                binding.txtPartyDate.text.clear()
            }
            view.findNavController()
                .navigate(CreatePartyFragmentDirections.actionCreatePartyFragmentToCardStackFragment())
        }
    }
}
