package com.serkanemek.weatherforecast.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.serkanemek.weatherforecast.R
import com.serkanemek.weatherforecast.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : BaseViewModel
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        constraintLayout.visibility = View.VISIBLE
        println("We are in 1111111")

        viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)

        var cityName = GET.getString("cityName", "london")
        search_CityName.setText(cityName)

        println("We are in 222222")

        viewModel.updateData()

        getLiveData()

        swipeRefreshLayout.setOnRefreshListener {
            error.visibility = View.GONE
            loading.visibility = View.GONE
            constraintLayout.visibility = View.GONE

            var newCityName = GET.getString("cityName",cityName)
            search_CityName.setText(newCityName)
            viewModel.updateData()
            swipeRefreshLayout.isRefreshing = false
            println("We are in 333333")
        }


        imageView_search.setOnClickListener {
            var name = search_CityName.text.toString()
            SET.putString("cityName", name)
            SET.apply()
            viewModel.updateData()
            getLiveData()
            println("We are in 444444")
        }

    }

    private fun getLiveData() {
        println("we are in getlivedata from mainactivity")
        viewModel.weather.observe(this, Observer { info ->
            info?.let {
                constraintLayout.visibility = View.VISIBLE
                tempCText.text = info.current.tempC.toString()
                cityNameText.text = info.location.name.toString()
                countryNameText.text = info.location.country.toString()

                println("We are in 55555")

                Glide.with(this).load(info.current.condition.icon.toString()).into(imageView_icon)


                println("city is: ${info.location.name.toString()}")
            }
        })

        viewModel.loading.observe(this, Observer { load->
            load?.let {
                if (it){
                    loading.visibility = View.VISIBLE
                    error.visibility = View.GONE
                    constraintLayout.visibility = View.GONE
                }else{
                    loading.visibility = View.GONE
                }
            }
        })

        viewModel.error.observe(this, Observer { info->
            info?.let {
                if (it){
                    loading.visibility = View.GONE
                    error.visibility = View.VISIBLE
                    constraintLayout.visibility = View.GONE
                }else{
                    error.visibility = View.GONE
                }
            }
        })
    }
}