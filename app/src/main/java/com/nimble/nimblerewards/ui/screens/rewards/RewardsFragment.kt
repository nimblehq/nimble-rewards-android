package com.nimble.nimblerewards.ui.screens.rewards

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.data.models.Promotion
import com.nimble.nimblerewards.data.models.Reward
import com.nimble.nimblerewards.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_rewards.*


class RewardsFragment : BaseFragment<RewardsViewModel>() {

    override val layoutResource = R.layout.fragment_rewards

    override val hasSupportActionBar = true

    private val rewardsAdapter by lazy { RewardsAdapter() }

    private val promotionsAdapter by lazy { PromotionsPagerAdapter(requireContext()) }

    private val handler = Handler(Looper.getMainLooper())

    override fun viewModel(): RewardsViewModel =
        ViewModelProviders.of(this, viewModelFactory)
            .get(RewardsViewModel::class.java)

    override fun configure() {
        rvRewards.adapter = rewardsAdapter
        rvRewards.layoutManager = LinearLayoutManager(requireContext())

        vpPromotions.adapter = promotionsAdapter
        populateTestData()
        setupPromotionsAutoSwipe()
    }

    @Deprecated("To be refactored")
    private fun setupPromotionsAutoSwipe() {
        val runnable = object : Runnable {
            override fun run() {
                vpPromotions.currentItem =
                    (vpPromotions.currentItem + 1) % promotionsAdapter.count

                handler.postDelayed(this, 2000)
            }
        }
        handler.postDelayed(runnable, 2000)
    }

    @Deprecated("To be removed")
    private fun populateTestData() {
        rewardsAdapter.rewards = listOf(
            Reward(
              imageUrl = "https://i.pinimg.com/originals/53/4a/2e/534a2e82b00b647e39307cb7f06035ab.jpg"
            ),
            Reward(
                imageUrl = "https://vic.cfmeu.org.au/sites/vic.cfmeu.org.au/files/219460%20VILR%20CFMEU%20Member%20Discount%20Promotion_800x417px_FA.jpg"
            ),
            Reward(
                imageUrl = "https://cdn.grabon.in/gograbon/images/merchant/1543561736695.png"
            ),
            Reward(
                imageUrl = "https://media.istockphoto.com/vectors/cinema-ticket-isolated-on-white-background-vector-id921532564?k=6&m=921532564&s=612x612&w=0&h=SEhmd1DmtMZMrG9Qnx2ny1pnWys_YuCQO8Zt9TMy8YI="
            )
        )

        promotionsAdapter.promotions = listOf(
            Promotion(
                imageUrl = "https://www.bookeventonline.com/wp-content/uploads/2017/04/Brand-prmotion-1.png"
            ),
            Promotion(
                imageUrl = "https://dgivdslhqe3qo.cloudfront.net/careers/photos/94820/normal_photo_1546487984.jpg"
            ),
            Promotion(
                imageUrl = "https://dgivdslhqe3qo.cloudfront.net/careers/photos/94824/normal_photo_1546488012.jpg"
            ),
            Promotion(
                imageUrl = "https://dgivdslhqe3qo.cloudfront.net/careers/photos/94822/normal_photo_1546487989.JPG"
            )
        )

        indicators.setViewPager(vpPromotions)
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }
}
