package com.stations.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.stations.data.RadioStation
import com.stations.databinding.AdapterRowBinding

class StationHolder(private val binding: AdapterRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(eachBlockInfo: RadioStation) {
        binding.txtId.text = eachBlockInfo.attributes.brand.name
    }
}