package digitized.gamehub.partyOverview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import digitized.gamehub.R
import digitized.gamehub.databinding.PartyOverviewFragmentBinding

class PartyOverviewFragment : Fragment() {

    private lateinit var binding: PartyOverviewFragmentBinding
    // private lateinit var viewModel: PartyOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.party_overview_fragment, container, false
        )

        // ViewModel
        val application = requireNotNull(activity).application
/*        viewModel = ViewModelProviders.of(this, PartyOverviewViewModel.Factory(application))
            .get(PartyOverviewViewModel::class.java)
        binding.viewModel = viewModel*/

        // val adapter = PartyOverviewAdapter()
        // binding.listOverview.adapter = adapter
//        viewModel.parties.observe(this, Observer {
//            it.let {
//                adapter.submitList(it)
//            }
//        })
        return binding.root
    }
}