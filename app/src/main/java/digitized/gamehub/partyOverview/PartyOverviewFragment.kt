package digitized.gamehub.partyOverview

import android.content.pm.ActivityInfo
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
    private lateinit var viewModel: PartyOverviewViewModel
    private lateinit var adapter: PartyOverviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.party_overview_fragment, container, false

        )
        binding.lifecycleOwner = this

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, PartyOverviewViewModel.Factory(application))
            .get(PartyOverviewViewModel::class.java)
        binding.viewModel = viewModel

        adapter = PartyOverviewAdapter()
        binding.listOverview.adapter = adapter

        // supported screen orientation
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.parties.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.games.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.games = it
            }
        })

        viewModel.user.observe(this, Observer {
            viewModel.getJoinedParties(it.id)
        })
    }
}
