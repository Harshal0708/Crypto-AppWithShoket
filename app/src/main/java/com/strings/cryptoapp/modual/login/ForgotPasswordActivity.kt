package com.strings.cryptoapp.modual.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.strings.airqualityvisualizer.Constants.Companion.showToast
import com.strings.airqualityvisualizer.R
import com.strings.airqualityvisualizer.Response.ForgotResponse
import com.strings.airqualityvisualizer.model.ForgotPayload
import com.strings.airqualityvisualizer.network.RestApi
import com.strings.airqualityvisualizer.network.ServiceBuilder
import retrofit2.Call
import java.util.regex.Pattern

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var fp_et_email: EditText

    lateinit var view: View
    lateinit var register_progressBar: ProgressBar
    lateinit var forgot: TextView
    lateinit var progressBar_cardView: RelativeLayout

    private lateinit var email: String

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        init()
    }

    fun init() {
        fp_et_email = findViewById(R.id.fp_et_email)

        view = findViewById(R.id.btn_progressBar)
        register_progressBar = view.findViewById(R.id.register_progressBar)

        progressBar_cardView = view.findViewById(R.id.progressBar_cardView)
        register_progressBar.visibility = View.GONE
        forgot = view.findViewById(R.id.resent)
        forgot.text = getString(R.string.forgot)

        fp_et_email.visibility = View.VISIBLE
        progressBar_cardView.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        val id = p0!!.id
        when (id) {
            R.id.progressBar_cardView -> {
                email = fp_et_email.text.toString()
                if (validation() == true) {
                    forgotPassword()
                }
            }
        }
    }

    private fun validation(): Any {
        if (fp_et_email.length() == 0) {
            fp_et_email.setError(getString(R.string.valid_error));
            return false;
        }

        email = fp_et_email.text.toString().trim()
        if (!(EMAIL_ADDRESS_PATTERN.toRegex().matches(email))) {
            fp_et_email.setError(getString(R.string.email_error));
            return false;
        }

        return true
    }


    fun forgotPassword() {

        progressBar_cardView.visibility = View.VISIBLE
        val response = ServiceBuilder(this@ForgotPasswordActivity).buildService(RestApi::class.java)

        val payload = ForgotPayload(email)
        response.addForgotPassword(payload).enqueue(object : retrofit2.Callback<ForgotResponse> {
                override fun onResponse(
                    call: Call<ForgotResponse>, response: retrofit2.Response<ForgotResponse>
                ) {
                    if (response.body()?.isSuccess == true) {
                        progressBar_cardView.visibility = View.GONE

                        response.body()?.message?.let {
                            showToast(
                                this@ForgotPasswordActivity, it
                            )
                        }
                        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        progressBar_cardView.visibility = View.GONE

                        response.body()?.message?.let {
                            showToast(
                                this@ForgotPasswordActivity, it
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<ForgotResponse>, t: Throwable) {
                    progressBar_cardView.visibility = View.GONE
                    showToast(this@ForgotPasswordActivity, getString(R.string.forgot_password_not_completed))
                }

            })


    }

}