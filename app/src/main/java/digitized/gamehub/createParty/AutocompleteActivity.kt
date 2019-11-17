package digitized.gamehub.createParty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import digitized.gamehub.R
import timber.log.Timber
import java.util.*


class AutocompleteActivity : AppCompatActivity() {
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var viewModel: CreatePartyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

        autocompleteFragment!!.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME))

        viewModel = ViewModelProviders.of(this).get(CreatePartyViewModel::class.java)


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Timber.d("Place: %s, %s, %s, %s", place.name, place.id, place.latLng, place.address)
                viewModel.setPlace(place)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Timber.d("An error occurred: $status")
            }
        })
    }

}