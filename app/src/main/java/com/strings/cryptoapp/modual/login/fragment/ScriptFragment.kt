package com.strings.cryptoapp.modual.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.strings.cryptoapp.Constants
import com.strings.cryptoapp.R
import com.strings.cryptoapp.Response.*
import com.strings.cryptoapp.model.UserSubscriptionModel
import com.strings.cryptoapp.modual.subscription.adapter.SubscriptionAdapter
import com.strings.cryptoapp.network.RestApi
import com.strings.cryptoapp.network.ServiceBuilder
import com.strings.cryptoapp.preferences.MyPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScriptFragment : Fragment(), View.OnClickListener {

    lateinit var txt_sub_monthly: TextView
    lateinit var txt_sub_quartly: TextView
    lateinit var txt_sub_yearly: TextView
    lateinit var rv_subsribtion: RecyclerView

    var subscriptionModelList: List<UserSubscriptionDataResponse> = ArrayList()
    lateinit var subscriptionAdapter: SubscriptionAdapter
    lateinit var one: String
    lateinit var two: String
    lateinit var three: String

    lateinit var preferences: MyPreferences
    lateinit var userDetail: DataXX

    lateinit var viewLoader: View
    lateinit var animationView: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_script, container, false)

        init(view)
        return view
    }

    private fun init(view: View) {

        preferences = MyPreferences(requireContext())
        userDetail = Gson().fromJson(preferences.getLogin(), DataXX::class.java)
        viewLoader = view.findViewById(R.id.loader_animation)
        animationView = viewLoader.findViewById(R.id.lotti_img)
        txt_sub_monthly = view.findViewById(R.id.txt_sub_monthly)
        txt_sub_quartly = view.findViewById(R.id.txt_sub_quartly)
        txt_sub_yearly = view.findViewById(R.id.txt_sub_yearly)
        rv_subsribtion = view.findViewById(R.id.rv_subsribtion)

        txt_sub_monthly.setOnClickListener(this)
        txt_sub_quartly.setOnClickListener(this)
        txt_sub_yearly.setOnClickListener(this)
        setupAnim()
        getPlans()
        //getUserSubscriptionDetail()

    }
    private fun setupAnim() {
        animationView.setAnimation(R.raw.currency)
        animationView.repeatCount = LottieDrawable.INFINITE
        animationView.playAnimation()
    }


    private fun getPlans() {

        viewLoader.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            var response = ServiceBuilder(requireContext()).buildService(RestApi::class.java).getPlans()
            withContext(Dispatchers.Main) {
                viewLoader.visibility = View.GONE
                txt_sub_monthly.text = response.body()?.data?.get(0)?.planName
                txt_sub_quartly.text = response.body()?.data?.get(1)?.planName
                txt_sub_yearly.text = response.body()?.data?.get(2)?.planName
                one = response.body()?.data?.get(0)?.id.toString()
                two = response.body()?.data?.get(1)?.id.toString()
                three = response.body()?.data?.get(2)?.id.toString()
                subscriptionModelList.isNullOrEmpty()
                getUserSubscription(one)
            }
        }
    }

    fun getUserSubscription(id: String?) {

        viewLoader.visibility = View.VISIBLE
        val response = ServiceBuilder(requireContext()).buildService(RestApi::class.java)
        var payload = UserSubscriptionModel(
            id.toString(),
            "",
            userDetail.userId
        )

        response.addUserSubscription(payload)
            .enqueue(
                object : Callback<UserSubscriptionResponse> {
                    override fun onResponse(
                        call: Call<UserSubscriptionResponse>,
                        response: Response<UserSubscriptionResponse>
                    ) {
                        viewLoader.visibility = View.GONE
                        subscriptionModelList = response.body()!!.data
                        ScriptAdapter(subscriptionModelList, id.toString())
                    }

                    override fun onFailure(call: Call<UserSubscriptionResponse>, t: Throwable) {
                        // register_progressBar?.visibility = View.GONE
                        Constants.showToast(requireContext(), getString(R.string.data_not_found))
                    }

                }
            )

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txt_sub_monthly -> {

                txt_sub_monthly.setBackgroundResource(R.drawable.background_tab_primary_color)
                txt_sub_quartly.setBackgroundResource(0)
                txt_sub_yearly.setBackgroundResource(0)

                txt_sub_monthly.setTextColor(resources.getColor(R.color.white))
                txt_sub_quartly.setTextColor(resources.getColor(R.color.primary_color))
                txt_sub_yearly.setTextColor(resources.getColor(R.color.primary_color))

                subscriptionModelList.isNullOrEmpty()
                getUserSubscription(one)

            }
            R.id.txt_sub_quartly -> {

                txt_sub_monthly.setBackgroundResource(0)
                txt_sub_quartly.setBackgroundResource(R.drawable.background_tab_primary_color)
                txt_sub_yearly.setBackgroundResource(0)

                txt_sub_monthly.setTextColor(resources.getColor(R.color.primary_color))
                txt_sub_quartly.setTextColor(resources.getColor(R.color.white))
                txt_sub_yearly.setTextColor(resources.getColor(R.color.primary_color))

                subscriptionModelList.isNullOrEmpty()
                getUserSubscription(two)

            }
            R.id.txt_sub_yearly -> {

                txt_sub_monthly.setBackgroundResource(0)
                txt_sub_quartly.setBackgroundResource(0)
                txt_sub_yearly.setBackgroundResource(R.drawable.background_tab_primary_color)

                txt_sub_monthly.setTextColor(resources.getColor(R.color.primary_color))
                txt_sub_quartly.setTextColor(resources.getColor(R.color.primary_color))
                txt_sub_yearly.setTextColor(resources.getColor(R.color.white))

                subscriptionModelList.isNullOrEmpty()
                getUserSubscription(three)
            }
        }
    }

    private fun ScriptAdapter(
        subscriptionModelList: List<UserSubscriptionDataResponse>,
        planId: String
    ) {
        rv_subsribtion.layoutManager = LinearLayoutManager(requireContext())
        subscriptionAdapter = SubscriptionAdapter(
            requireContext(),
            subscriptionModelList,
            planId
        )
        rv_subsribtion.adapter = subscriptionAdapter

    }
}