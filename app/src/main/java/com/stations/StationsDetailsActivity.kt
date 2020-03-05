package com.stations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.stations.databinding.ActivityDetailBinding

class StationsDetailsActivity : AppCompatActivity() {


    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_detail
        )
        setContentView(binding.root)

        binding.txtIdValue.text =
            intent.extras?.getString("name") + "\n\n" + intent.extras?.getString("tagline") + "\n\n" + intent.extras?.getString(
                "frequency") + "\n\n" + intent.extras?.getString("marketCity")
    }

}
