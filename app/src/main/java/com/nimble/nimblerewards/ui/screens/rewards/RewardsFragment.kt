package com.nimble.nimblerewards.ui.screens.rewards

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

    override fun viewModel(): RewardsViewModel =
        ViewModelProviders.of(this, viewModelFactory)
            .get(RewardsViewModel::class.java)

    override fun configure() {
        rvRewards.adapter = rewardsAdapter
        rvRewards.layoutManager = LinearLayoutManager(requireContext())

        vpPromotions.adapter = promotionsAdapter
        populateTestData()
    }

    @Deprecated("To be removed")
    private fun populateTestData() {
        rewardsAdapter.rewards = listOf(
            Reward(
                name = "Discount Voucher"
            ),
            Reward(
                name = "Fast Food Coupon"
            ),
            Reward(
                name = "Movie Ticket"
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
}
