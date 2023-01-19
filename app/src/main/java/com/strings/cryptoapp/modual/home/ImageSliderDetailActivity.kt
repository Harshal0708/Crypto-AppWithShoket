package com.strings.cryptoapp.modual.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strings.cryptoapp.Constants.Companion.showToast
import com.strings.cryptoapp.Response.CmsAdsAddResponse
import com.strings.cryptoapp.Response.DataXX
import com.strings.cryptoapp.model.CmsAdsAddPayload
import com.strings.cryptoapp.network.RestApi
import com.strings.cryptoapp.network.ServiceBuilder
import com.strings.cryptoapp.preferences.MyPreferences
import com.google.gson.Gson
import com.strings.cryptoapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageSliderDetailActivity : AppCompatActivity() {

    lateinit var imageListId :String
    lateinit var preferences: MyPreferences

    lateinit var data: DataXX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider_detail)

        init()
    }

    fun init() {
        //imageListId = intent.getStringExtra("imageListId").toString()
        preferences = MyPreferences(this)
        imageListId = "938fb7e9-f50d-44d1-f3e0-08daef072a64"
        data = Gson().fromJson(preferences.getLogin(), DataXX::class.java)

        getSubscriptionHistoryList(imageListId)
    }
    fun getSubscriptionHistoryList(userId: String) {
       // viewLoader.visibility = View.VISIBLE
        val response = ServiceBuilder(this@ImageSliderDetailActivity).buildService(RestApi::class.java)

        val payload = CmsAdsAddPayload(userId,false,data.userId)

        response.addCmsAdsAdd(payload)
            .enqueue(
                object : Callback<CmsAdsAddResponse> {
                    override fun onResponse(
                        call: Call<CmsAdsAddResponse>,
                        response: Response<CmsAdsAddResponse>
                    ) {

                        if (response.body()?.isSuccess == true) {
                           //viewLoader.visibility = View.GONE
                            showToast(this@ImageSliderDetailActivity,response.body()?.code.toString())
                        } else {
                            //viewLoader.visibility = View.GONE
                            showToast(
                                this@ImageSliderDetailActivity,
                                getString(R.string.data_not_found)
                            )
                        }

                    }

                    override fun onFailure(call: Call<CmsAdsAddResponse>, t: Throwable) {
                        //viewLoader.visibility = View.GONE
                        showToast(
                            this@ImageSliderDetailActivity,
                            getString(R.string.data_not_found)
                        )
                    }
                }
            )
    }
}