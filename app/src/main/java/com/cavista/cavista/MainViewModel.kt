package com.cavista.cavista

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cavista.cavista.adapter.ImageListAdapter
import com.cavista.cavista.data.ApiResponseModel
import com.cavista.cavista.data.Data
import com.cavista.cavista.data.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var apiClient = ApiClient.getClient().create(ApiInterface::class.java)

    var isLoading = MutableLiveData<Boolean>()
    var showToast = MutableLiveData(false)
    var showToastValue = ""
    var detailClick = MutableLiveData(false)
    var norecordVisibility = MutableLiveData(View.GONE)

    var searchValue = MutableLiveData("")
    var imageLink = ""
    var imageTitle = ""


    var imageList = ArrayList<Data>()
    var adapter = ImageListAdapter(this)



    /**
     * api call for searching & retriving images
     * */
    fun loadImageList(){

        isLoading.value = true
        imageList.clear()
        adapter.notifyDataSetChanged()

        val token = "Client-ID 137cda6b5008a7c"

        apiClient.loadImageList(searchValue.value?:"", token).enqueue(object : Callback<ApiResponseModel>{
            override fun onFailure(call: Call<ApiResponseModel>, t: Throwable) {

                isLoading.value = false
                Log.e("fail", t.message)
                showToast.value = true
                showToastValue = t.message?:""
            }

            override fun onResponse(call: Call<ApiResponseModel>, response: Response<ApiResponseModel>) {

                isLoading.value = false
                Log.e("success", response.toString())


                if (response.body()?.data?.isEmpty() != false){

                    norecordVisibility.value = View.VISIBLE
                }else{
                    norecordVisibility.value = View.GONE
                }

                response.body()?.data?.let { imageList.addAll(it) }
                adapter.notifyDataSetChanged()
            }
        })
    }


/**
 * adapter click to navigate next activity
 * */
    fun openDetail(model : Data, position : Int){

        imageLink = model.images[0].link
        imageTitle = model.title
        detailClick.value = true
    }
}