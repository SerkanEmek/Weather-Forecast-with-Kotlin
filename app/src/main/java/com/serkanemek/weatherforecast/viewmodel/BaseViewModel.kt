package com.serkanemek.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serkanemek.weatherforecast.model.WeatherModel
import com.serkanemek.weatherforecast.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class BaseViewModel : ViewModel() {

    private val weatherApiService = WeatherAPIService()
    private val disposable =CompositeDisposable()
    val weather = MutableLiveData<WeatherModel>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    fun updateData(){

        getDataFromApi()
    }

    private fun getDataFromApi() {
        loading.value = true
        disposable.add(
            weatherApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weather.value = t
                        loading.value = false
                        error.value = false
                        println(t.location.name.toString())
                        println("this is in BasevieModel getdatafromapi")
                    }

                    override fun onError(e: Throwable) {
                       error.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

}