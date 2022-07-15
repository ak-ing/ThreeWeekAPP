package com.rick.thridweekapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rick.thridweekapp.R
import com.rick.thridweekapp.bean.CityBean
import com.rick.thridweekapp.databinding.ItemCityBinding

/**
 * Created by Rick on 2022/7/15 14:48.
 * God bless my code!
 */
class CitySelectAdapter : ListAdapter<CityBean, CitySelectAdapter.InnerHolder>(object : DiffUtil.ItemCallback<CityBean>() {
    override fun areItemsTheSame(oldItem: CityBean, newItem: CityBean): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: CityBean, newItem: CityBean): Boolean {
        return oldItem == newItem
    }
}) {
    private var currentIndex = 0

    class InnerHolder(val bind: ItemCityBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return InnerHolder(ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    private lateinit var itemClick: (CityBean) -> Unit

    fun setOnItemClickListener(block: (CityBean) -> Unit) {
        itemClick = block
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.bind.item = getItem(position)
        holder.itemView.setBackgroundResource(if (position == currentIndex) R.drawable.ripple_city_select else R.drawable.ripple_city_normal)
        holder.itemView.setOnClickListener {
            val oldIndex = currentIndex
            currentIndex = holder.adapterPosition
            notifyItemChanged(oldIndex)
            notifyItemChanged(currentIndex)
            if (this::itemClick.isInitialized) {
                itemClick(getItem(position))
            }
        }
    }


}