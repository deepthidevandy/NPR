package com.stations

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stations.adapter.DataAdapter
import com.stations.data.RadioStation
import com.stations.data.RequestStations
import com.stations.databinding.ActivityStationsBinding
import com.stations.response.Response
import com.stations.util.ConnectionUtils
import com.stations.viewmodel.StationsListViewModel
import com.stations.viewmodel.ViewModelFactory

class StationsListActivity : AppCompatActivity() {


    private lateinit var viewModel: StationsListViewModel

    private var adapter: DataAdapter? = null

    lateinit var binding: ActivityStationsBinding

    lateinit var requestStations: RequestStations

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_stations
        )
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelFactory()).get(
                StationsListViewModel::class.java
            )
        latestStationsLiveData()
        requestStations =
            this.intent.extras?.getParcelable<RequestStations>("requestStations") as RequestStations
        initViews()
    }


    private fun initViews() {
        settingLayoutManager()
        settingAdapter()
        if (ConnectionUtils.isConnected(applicationContext)) {
            viewModel.getStationsList(requestStations)
        } else
            displayError()
    }

    private fun displayError() {
        Toast.makeText(this, "Please check your internet settings", Toast.LENGTH_LONG).show()
    }

    private fun settingAdapter() {
        adapter = DataAdapter()
        adapter?.onClickListener = {
            val intent = Intent(this, StationsDetailsActivity::class.java)
            intent.putExtra("name", it.attributes.brand.name)
            intent.putExtra("frequency", it.attributes.brand.frequency)
            intent.putExtra("marketCity", it.attributes.brand.marketCity)
            intent.putExtra("tagline", it.attributes.brand.tagline)
            startActivity(intent)
        }
        binding.recyclerviewBlocksList.adapter = adapter
    }

    private fun settingLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerviewBlocksList.layoutManager = layoutManager
    }

    private fun latestStationsLiveData() {
        viewModel.liveDataAccessToken.observe(this, Observer {
            when (it) {
                is Response.SUCCESS_STATIONS -> {
                    when (it.stationResponse.items.size) {
                        0 -> {
                            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else -> notifyAdapter(it.stationResponse.items)
                    }
                }
            }
        })
    }

    private fun notifyAdapter(list: List<RadioStation>) {
        adapter?.stationsList = list
        adapter?.notifyDataSetChanged()
        binding.progressBar.visibility = View.GONE
    }


}
