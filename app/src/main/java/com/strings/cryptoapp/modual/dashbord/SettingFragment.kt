package com.strings.cryptoapp.modual.dashbord

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.strings.cryptoapp.Constants.Companion.showToast
import com.strings.cryptoapp.R
import com.strings.cryptoapp.Response.DataXX
import com.strings.cryptoapp.modual.login.LoginActivity
import com.strings.cryptoapp.modual.login.ProfileActivity
import com.strings.cryptoapp.modual.login.ResetPasswordActivity
import com.strings.cryptoapp.modual.subscription.SubscriptionActivity
import com.strings.cryptoapp.preferences.MyPreferences
import com.google.gson.Gson


class SettingFragment : Fragment(), View.OnClickListener {

    lateinit var txt_setting_subscription: TextView
    lateinit var txt_setting_password: TextView
    lateinit var txt_setting_share_app: TextView
    lateinit var txt_setting_privacy_policy: TextView
    lateinit var txt_setting_logout: TextView
    lateinit var txt_setting_username: TextView
    lateinit var img_setting_profile: ImageView
    lateinit var preferences: MyPreferences

    lateinit var data : DataXX

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_setting, container, false)

        InIt(view)
        return view
    }

    private fun InIt(view: View) {
        preferences = MyPreferences(requireContext())
        txt_setting_subscription = view.findViewById(R.id.txt_setting_subscription)
        txt_setting_password = view.findViewById(R.id.txt_setting_password)
        txt_setting_share_app = view.findViewById(R.id.txt_setting_share_app)
        txt_setting_privacy_policy = view.findViewById(R.id.txt_setting_privacy_policy)
        txt_setting_logout = view.findViewById(R.id.txt_setting_logout)
        txt_setting_username = view.findViewById(R.id.txt_setting_username)
        img_setting_profile = view.findViewById(R.id.img_setting_profile)

        data= Gson().fromJson(preferences.getLogin(), DataXX::class.java)
        txt_setting_username.text= data.name.toString()

        if (data.profilePicture != null && data.profilePicture != "") {
            img_setting_profile.setImageBitmap(byteArrayToBitmap(data.profilePicture.toByteArray()))
        }

        img_setting_profile.setOnClickListener(this)
        txt_setting_subscription.setOnClickListener(this)
        txt_setting_password.setOnClickListener(this)
        txt_setting_share_app.setOnClickListener(this)
        txt_setting_privacy_policy.setOnClickListener(this)
        txt_setting_logout.setOnClickListener(this)
    }
    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        val decodeResponse: ByteArray = Base64.decode(data, Base64.DEFAULT or Base64.NO_WRAP)
        val bitmap = BitmapFactory.decodeByteArray(decodeResponse, 0, decodeResponse.size)
        return bitmap
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.img_setting_profile -> {
                val intent = Intent(activity, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.txt_setting_subscription -> {
                val intent = Intent(activity, SubscriptionActivity::class.java)
                startActivity(intent)
            }
            R.id.txt_setting_password -> {
                val intent = Intent(activity, ResetPasswordActivity::class.java)
                startActivity(intent)
                activity?.finish()

            }
            R.id.txt_setting_share_app -> {
                showToast(requireContext(),"Share App")
//                val intent = Intent(activity, PaymentActivity::class.java)
//                startActivity(intent)
            }
            R.id.txt_setting_privacy_policy -> {
                showToast(requireContext(),"Privacy Policy")
            }
            R.id.txt_setting_logout -> {
                preferences.setRemember(false)
                preferences.setToken("")
                preferences.setLogin(null)
                var intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
                showToast(requireContext(), getString(R.string.logout_successfully))
            }
        }
    }

}