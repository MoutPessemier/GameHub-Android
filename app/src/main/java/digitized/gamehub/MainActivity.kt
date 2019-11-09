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
        adapter = CardStackAdapter(viewModel.getPartiesNearYouMock())

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
        if (manager.topPosition == adapter.itemCount - 10) {
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
        manager.setStackFrom(StackFrom.None)
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
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getParties()
        val new = old.plus(viewModel.getPartiesNearYouMock())
        val callback = PartyDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setParties(new)
        result.dispatchUpdatesTo(adapter)
    }
}
