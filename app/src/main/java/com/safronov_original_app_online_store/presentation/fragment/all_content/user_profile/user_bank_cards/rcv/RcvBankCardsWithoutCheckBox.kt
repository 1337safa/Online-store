package com.safronov_original_app_online_store.presentation.fragment.all_content.user_profile.user_bank_cards.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.BankCardWithoutCheckBoxRcvBinding
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard

class RcvBankCardsWithoutCheckBox() :
    ListAdapter<BankCard, RcvBankCardsWithoutCheckBox.BankCardsViewHolder>(BankCardsDiffUtil()) {

    class BankCardsViewHolder(
        val binding: BankCardWithoutCheckBoxRcvBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class BankCardsDiffUtil() : DiffUtil.ItemCallback<BankCard>() {
        override fun areItemsTheSame(oldItem: BankCard, newItem: BankCard): Boolean {
            return oldItem.cardNumber == newItem.cardNumber
        }

        override fun areContentsTheSame(oldItem: BankCard, newItem: BankCard): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankCardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BankCardWithoutCheckBoxRcvBinding.inflate(inflater, parent, false)
        return BankCardsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: BankCardsViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            holder.binding.tvCardNumber.text =
                currentList[holder.adapterPosition].cardNumber.toString()
        }
    }

}