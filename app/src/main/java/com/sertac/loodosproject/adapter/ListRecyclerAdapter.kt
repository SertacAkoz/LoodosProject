package com.sertac.loodosproject.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.sertac.loodosproject.R
import com.sertac.loodosproject.databinding.ListRowBinding
import com.sertac.loodosproject.model.Coin
import com.sertac.loodosproject.navigation.HamburgerNavigationActions
import com.sertac.loodosproject.util.Utils
import com.sertac.loodosproject.view.fragment.hamburger.ListFragment
import com.sertac.loodosproject.view.fragment.hamburger.ListFragmentDirections
import javax.inject.Inject

class ListRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ListRecyclerAdapter.ListViewHolder>(){
    class ListViewHolder(var view : ListRowBinding) : RecyclerView.ViewHolder(view.root){

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

    var coinList : List<Coin>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = DataBindingUtil.inflate<ListRowBinding>(LayoutInflater.from(parent.context), R.layout.list_row, parent, false )
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.view.textCoinName.text = coinList[position].name
        holder.view.textCurrentPrice.text = "Price -> ${coinList[position].currentPrice} USD"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            holder.view.textLastUpdate.text = Utils.formatDate(coinList[position].lastUpdate)
            holder.view.textLastUpdate.text = "Last Update -> ${coinList[position].lastUpdate}"
        }else{
            holder.view.textLastUpdate.text = "Last Update -> ${coinList[position].lastUpdate}"
        }

        holder.view.parent.setOnClickListener {
            println("ListRecyclerAdapter-Parent --> Clicked")
        }

        glide.load(coinList[position].imageUrl).into(holder.view.coinImage)

        holder.view.parent.setOnClickListener {
//            HamburgerNavigationActions.actionListFragmentToDetailFragment(coinList[position].id)

            val navDirection : NavDirections = ListFragmentDirections.actionListFragmentToDetailFragment(coinList[position].id)
            ListFragment.getView()?.let {
                Navigation.findNavController(it).navigate(navDirection)
            }
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }
}