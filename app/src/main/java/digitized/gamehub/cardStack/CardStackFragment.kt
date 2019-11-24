package digitized.gamehub.cardStack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.yuyakaido.android.cardstackview.*
import digitized.gamehub.R
import digitized.gamehub.databinding.CardStackFragmentBinding
import timber.log.Timber

class CardStackFragment :Fragment(), CardStackListener {

    private lateinit var binding: CardStackFragmentBinding
    private lateinit var viewModel: CardStackViewModel
    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.card_stack_fragment, container, false)

        binding.lifecycleOwner = this

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, CardStackViewModel.Factory(application)).get(CardStackViewModel::class.java)
        binding.viewModel = viewModel

        // CardStack
        cardStackView = binding.cardStackView
        manager = CardStackLayoutManager(context, this)
        adapter = CardStackAdapter()
        initialize()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.parties.observe(this, Observer {
            adapter.setParties(it);
        })

        viewModel.games.observe(this, Observer {
            adapter.games = it
        })
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("position = $position")
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Timber.d("onCardDragging: d = ${direction?.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction?) {
        Timber.d("onCardDragging: d = ${direction?.name}")
        Timber.d("listSize: ${adapter.getParties().size}")
        if (direction?.name == "left") {
            viewModel.declineParty()
        }
        if (direction?.name == "right") {
            viewModel.joinParty()
        }
        if (manager.topPosition == 5) {
            //paginate()
        }
    }

    override fun onCardCanceled() {
        Timber.d("onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("position = $position")
    }

    override fun onCardRewound() {
        Timber.d("onCardCanceled: ${manager.topPosition}")
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.Top)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.FREEDOM)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        var old = adapter.getParties()
        viewModel.refreshPartiesNearYou()
        val new = old.plus(adapter.getParties())
        val callback = PartyDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        //adapter.setParties(new)
        //result.dispatchUpdatesTo(adapter)
    }
}