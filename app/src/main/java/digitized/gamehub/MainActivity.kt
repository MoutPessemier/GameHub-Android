package digitized.gamehub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.yuyakaido.android.cardstackview.*
import digitized.gamehub.cardStack.CardStackAdapter
import digitized.gamehub.cardStack.CardStackViewModel
import digitized.gamehub.cardStack.PartyDiffCallback
import digitized.gamehub.databinding.ActivityMainBinding
import digitized.gamehub.domain.GameParty
import timber.log.Timber

class MainActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewModel: CardStackViewModel
    private lateinit var cardStackView: CardStackView
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(CardStackViewModel::class.java)
        cardStackView = findViewById(R.id.card_stack_view)
        manager = CardStackLayoutManager(this, this)
        // I need to make sure these are not null
        adapter = CardStackAdapter(viewModel.parties.value, viewModel.games.value)

        initialize()

        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.nav_hostView)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        navController.addOnDestinationChangedListener { navCont: NavController, navDest: NavDestination, _ ->
            if (navDest.id == navCont.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_hostView)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Timber.d("position = $position")
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Timber.d("onCardDragging: d = ${direction?.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction?) {
        Timber.d("onCardDragging: d = ${direction?.name}")
        Timber.d("listSize: ${adapter.getParties()?.size}")
        if(direction?.name == "left") {
            viewModel.declineParty()
        }
        if(direction?.name == "right"){
            viewModel.joinParty()
        }
        if (manager.topPosition == 5) { // if 5 left, fetch new (max 25) parties --> always a max of 30 parties at once
            paginate()
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
//        var old = adapter.getParties()
//        if(old == null) old = listOf()
//        viewModel.refreshPartiesNearYou()
//        val new = old.plus(viewModel.parties.value)
//        val callback = PartyDiffCallback(old, new)
//        val result = DiffUtil.calculateDiff(callback)
//        adapter.setParties(new)
//        result.dispatchUpdatesTo(adapter)
    }
}
