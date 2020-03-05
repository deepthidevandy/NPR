package com.stations

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stations.data.RequestAccessToken
import com.stations.data.RequestStations
import com.stations.databinding.ActivitySearchBinding
import com.stations.response.Response
import com.stations.util.Constants
import com.stations.viewmodel.AuthViewModel
import com.stations.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    private val viewModelDetails: AuthViewModel? by lazy {
        ViewModelProvider(this, ViewModelFactory()).get(AuthViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_search
        )
        setContentView(binding.root)
        viewModelDetails?.liveDataAccessToken?.observe(this, Observer {
            when (it) {
                is Response.SUCCESS -> {
                    Constants.responseAccessToken = it.token
                    binding.submit.isEnabled = true
                    binding.submit.text = getString(R.string.search)
                }
            }
        })
        val code = intent.extras?.getString("code")
        code?.let { _code ->
            viewModelDetails?.getAccessToken(
                RequestAccessToken(
                    grant_type = Constants.authorization_code,
                    client_id = BuildConfig.NPR_APPLICATION_ID,
                    client_secret = BuildConfig.NPR_APPLICATION_SECRET,
                    code = _code,
                    redirect_uri = Constants.REDIRECT_URI
                )
            )
        }
        binding.submit.setOnClickListener {
            var requestStations = RequestStations(
                Authorization = Constants.responseAccessToken?.token_type + " " + Constants.responseAccessToken?.access_token,
                city = cityEditText.text.toString(),
                lat = latEditText.text.toString(),
                lon = lonEditText.text.toString()
            )
            val intent = Intent(this, StationsListActivity::class.java)
            intent.putExtra("requestStations", requestStations)
            startActivity(intent)
        }
    }
}
