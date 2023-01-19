package com.strings.cryptoapp.modual.dashbord

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.strings.airqualityvisualizer.Constants
import com.strings.airqualityvisualizer.R
import com.strings.airqualityvisualizer.Response.DataXX
import com.strings.airqualityvisualizer.Response.UserSubscriptionsResponse
import com.strings.airqualityvisualizer.model.GetOrderHistoryListPayload
import com.strings.airqualityvisualizer.modual.history.adapter.SubscriptionHistoryAdapter
import com.strings.airqualityvisualizer.modual.home.HomeDetailActivity
import com.strings.airqualityvisualizer.modual.home.adapter.HomeAdapter
import com.strings.airqualityvisualizer.modual.home.adapter.SliderViewPagerAdapter
import com.strings.airqualityvisualizer.modual.login.ResetPasswordActivity
import com.strings.airqualityvisualizer.modual.login.adapter.ViewPagerAdapter
import com.strings.airqualityvisualizer.network.*
import com.strings.airqualityvisualizer.preferences.MyPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), View.OnClickListener {

    lateinit var strategies_rv: RecyclerView
    lateinit var homeAdapter: HomeAdapter
    lateinit var viewLoader: View
    lateinit var animationView: LottieAnimationView
    lateinit var preferences: MyPreferences

    lateinit var login_ViewPager: ViewPager
    lateinit var viewPagerAdapter: SliderViewPagerAdapter

    lateinit var data: DataXX

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        init(view)
        return view
    }

    private fun init(view: View) {
       preferences =MyPreferences(requireContext())
        data = Gson().fromJson(preferences.getLogin(), DataXX::class.java)

        strategies_rv = view.findViewById(R.id.strategies_rv)
        viewLoader = view.findViewById(R.id.loader_animation)
        animationView = viewLoader.findViewById(R.id.lotti_img)
     //   Toast.makeText(activity,preferences.getLogin(),Toast.LENGTH_SHORT).show()

        login_ViewPager = view.findViewById(R.id.login_ViewPager)

        setupAnim()
        getStrategy()
        getCMSList()

    }

    private fun getCMSList() {
        viewLoader.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            var response = ServiceBuilder(requireContext()).buildService(RestApi::class.java).getCmsAdsList(data.userId)
            withContext(Dispatchers.Main) {

                viewLoader.visibility = View.GONE

                viewPagerAdapter = SliderViewPagerAdapter(requireContext(), response.body()!!.data)
                login_ViewPager.adapter = viewPagerAdapter

            }
        }
    }


    private fun setupAnim() {
        animationView.setAnimation(R.raw.currency)
        animationView.repeatCount = LottieDrawable.INFINITE
        animationView.playAnimation()
    }

    private fun getStrategy() {

        viewLoader.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            var response = ServiceBuilder(requireContext()).buildService(RestApi::class.java).getStrategy()
            withContext(Dispatchers.Main) {
                viewLoader.visibility = View.GONE
                strategies_rv.layoutManager = LinearLayoutManager(requireContext())
                homeAdapter = HomeAdapter(requireContext(), response.body()!!)
                strategies_rv.adapter = homeAdapter
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

        }
    }
}

