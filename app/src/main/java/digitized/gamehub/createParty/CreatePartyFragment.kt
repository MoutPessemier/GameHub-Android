package  digitized.gamehub.createParty

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CreatePartyFragment : Fragment() {
    private lateinit var viewModel: CreatePartyViewModel
    private lateinit var binding: CreateGamePartyBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

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
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { newLocation ->
            if (newLocation != null) {
                val currentLatLng = LatLng(newLocation.latitude, newLocation.longitude)
                viewModel.currentLocation = currentLatLng
            }
        }

        viewModel.games.observe(viewLifecycleOwner, Observer {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it.map { game -> game.name })
        })

        binding.btnCreateGameParty.setOnClickListener { view: View ->
            if (binding.txtPartyName.text.toString() == "" ||
                binding.txtMaxSize.text.toString() == "" ||
                binding.txtPartyDate.text.toString() == ""
            ) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                try {
                    val succes = viewModel.createParty(
                        binding.txtPartyName.text.toString(),
                        SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.ENGLISH
                        ).parse(binding.txtPartyDate.text.toString())!!,
                        binding.txtMaxSize.text.toString().toInt()
                    )
                    if (succes) {
                        binding.txtPartyName.text.clear()
                        binding.txtMaxSize.text.clear()
                        binding.txtPartyDate.text.clear()
                        view.findNavController()
                            .navigate(CreatePartyFragmentDirections.actionCreatePartyFragmentToCardStackFragment())
                    }
                } catch (e: ParseException) {
                    binding.subscript.setTextColor(resources.getColor(R.color.ruby_red))
                }
            }
        }
    }
}


