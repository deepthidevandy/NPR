package com.stations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stations.data.RadioStation
import com.stations.databinding.AdapterRowBinding
import com.stations.viewholder.StationHolder

typealias  OnClickListenerAlias = (RadioStation) -> Unit

class DataAdapter : RecyclerView.Adapter<StationHolder>() {

    var stationsList: List<RadioStation> = ArrayList()

    lateinit var binding: AdapterRowBinding

    var onClickListener: OnClickListenerAlias? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationHolder {

        binding = AdapterRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StationHolder(binding)
    }

    override fun getItemCount(): Int = stationsList.size

    override fun onBindViewHolder(holder: StationHolder, position: Int) {
        holder.bindView(stationsList[position])
        Glide.with(binding.root.context).load(getUrl(position)).into(binding.image);
        binding.root.setOnClickListener {
            onClickListener?.invoke(stationsList[position])
        }
    }

    fun getUrl(position: Int): String {

        stationsList[position].links.brand.forEach { image ->
            if (image.href.endsWith(".png")) {
                return image.href
            }
        }

        return "no URL"
    }
}