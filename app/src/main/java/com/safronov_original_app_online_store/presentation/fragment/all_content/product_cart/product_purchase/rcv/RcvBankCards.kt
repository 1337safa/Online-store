package com.safronov_original_app_online_store.presentation.fragment.all_content.product_cart.product_purchase.rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.safronov_original_app_online_store.databinding.BankCardRcvItemBinding
import com.safronov_original_app_online_store.domain.model.bank_card.BankCard

class RcvBankCards(
    private val rcvBankCardsInt: RcvBankCardsInt
): ListAdapter<BankCard, RcvBankCards.BankCardsViewHolder>(RcvBankCards.BankCardsDiffUtil()) {

    private var selectedBankCard: BankCard? = null

    class BankCardsViewHolder(
        val binding: BankCardRcvItemBinding
    ): RecyclerView.ViewHolder(binding.root)

    class BankCardsDiffUtil(): DiffUtil.ItemCallback<BankCard>() {
        override fun areItemsTheSame(oldItem: BankCard, newItem: BankCard): Boolean {
            return oldItem.cardNumber == newItem.cardNumber
        }

        override fun areContentsTheSame(oldItem: BankCard, newItem: BankCard): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankCardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BankCardRcvItemBinding.inflate(inflater, parent, false)
        return BankCardsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: BankCardsViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            holder.binding.checkBox.isChecked = selectedBankCard == currentList[holder.adapterPosition]
            holder.binding.tvCardNumber.text = currentList[holder.adapterPosition].cardNumber.toString()
            holder.binding.layoutRoot.setOnClickListener {
                if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                    selectedBankCard = currentList[holder.adapterPosition]
                    rcvBankCardsInt.onBankCardClick(currentList[holder.adapterPosition])
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun setSelectedBankCard(selectedBankCard: BankCard) {
        this.selectedBankCard = selectedBankCard
        notifyDataSetChanged()
    }

}