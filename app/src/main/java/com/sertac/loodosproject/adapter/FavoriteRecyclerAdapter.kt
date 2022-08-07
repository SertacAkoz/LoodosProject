package com.sertac.loodosproject.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sertac.loodosproject.R
import com.sertac.loodosproject.databinding.FavoriteRowBinding
import com.sertac.loodosproject.model.Coin
import javax.inject.Inject

class FavoriteRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(var view : FavoriteRowBinding) : RecyclerView.ViewHolder(view.root){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var favoriteList : List<Coin>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = DataBindingUtil.inflate<FavoriteRowBinding>(LayoutInflater.from(parent.context), R.layout.favorite_row, parent, false )
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.view.textCoinName.text = favoriteList[position].name
        holder.view.textCurrentPrice.text = "Price -> ${favoriteList[position].currentPrice} USD"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            holder.view.textLastUpdate.text = Utils.formatDate(coinList[position].lastUpdate)
            holder.view.textLastUpdate.text = "Last Update -> ${favoriteList[position].lastUpdate}"
        }else{
            holder.view.textLastUpdate.text = "Last Update -> ${favoriteList[position].lastUpdate}"
        }

        holder.view.parent.setOnClickListener {
            println("ListRecyclerAdapter-Parent --> Clicked")
        }

        glide.load(favoriteList[position].imageUrl).into(holder.view.coinImage)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}