package digitized.gamehub

import android.view.View
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import digitized.gamehub.domain.ApiStatus

@BindingAdapter("apiStatus")
fun LottieAnimationView.bindStatus(status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setAnimation(R.raw.loading_infinity)
        }
        ApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setAnimation(R.raw.error)
        }
        ApiStatus.DONE -> {
            visibility = View.GONE
        }
    }
}
