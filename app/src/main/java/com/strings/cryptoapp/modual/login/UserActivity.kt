package com.strings.cryptoapp.modual.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.strings.airqualityvisualizer.MainActivity
import com.strings.airqualityvisualizer.R
//import com.strings.airqualityvisualizer.databinding.ActivityUserBinding
import com.strings.airqualityvisualizer.modual.login.adapter.ViewPagerAdapter
import com.strings.airqualityvisualizer.modual.login.adapter.ZoomOutPageTransformer
import com.strings.airqualityvisualizer.modual.login.fragment.DocumentFragment
import com.strings.airqualityvisualizer.modual.login.fragment.ScriptFragment
import com.strings.airqualityvisualizer.modual.login.fragment.UserFragment


class UserActivity : AppCompatActivity(), View.OnClickListener {

//    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

//        binding = ActivityUserBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setupViewPager2()
    }

    private fun setupViewPager2() {
        val colorList = arrayListOf(UserFragment(), DocumentFragment(), ScriptFragment())
//        binding.viewPager.adapter = ViewPagerAdapter(this, colorList)
//
//        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
//        binding.circleIndicator.setViewPager(binding.viewPager)
//        binding.userSkip.setOnClickListener(this)
    }

    override fun onBackPressed() {
//        val viewPager = binding.viewPager
//        if (viewPager.currentItem == 0) {
//            super.onBackPressed()
//        } else {
//            viewPager.currentItem = viewPager.currentItem - 1
//        }
    }

    override fun onClick(p0: View?) {
        val id = p0!!.id
//        when (id) {
//            binding.userSkip.id -> {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
    }
}