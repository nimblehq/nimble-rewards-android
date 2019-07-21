package com.nimble.nimblerewards.ui.screens.rewards

import androidx.lifecycle.ViewModelProviders
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.ui.common.BaseFragment

class RewardsFragment : BaseFragment<RewardsViewModel>() {

    override val layoutResource = R.layout.fragment_rewards

    override val hasSupportActionBar = true

    override fun viewModel(): RewardsViewModel =
        ViewModelProviders.of(this, viewModelFactory)
            .get(RewardsViewModel::class.java)

    override fun configure() {

    }
}
