package com.strings.cryptoapp.modual.subscription

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strings.airqualityvisualizer.R
import com.strings.airqualityvisualizer.modual.login.fragment.ScriptFragment

class SubscriptionActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        onSubscription()
    }


    fun onSubscription() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fram_subscription, ScriptFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}