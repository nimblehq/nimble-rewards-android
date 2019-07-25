package com.nimble.nimblerewards.ui.screens.rewards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.data.models.Reward
import kotlinx.android.synthetic.main.item_reward.view.*

class RewardsAdapter : RecyclerView.Adapter<RewardsAdapter.Holder>() {

    var rewards = emptyList<Reward>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_reward, parent, false)
        )
    }

    override fun getItemCount() = rewards.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindData(rewards[position])
    }

    class Holder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(reward: Reward) {
            view.tvReward.text = reward.name
        }
    }
}
