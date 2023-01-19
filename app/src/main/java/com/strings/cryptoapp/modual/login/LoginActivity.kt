package com.strings.cryptoapp.modual.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.widget.*
import com.strings.airqualityvisualizer.Constants.Companion.showToast
import com.strings.airqualityvisualizer.R
import com.strings.airqualityvisualizer.Response.LoginResponse
import com.strings.airqualityvisualizer.model.LoginPayload
import com.strings.airqualityvisualizer.network.RestApi
import com.strings.airqualityvisualizer.network.ServiceBuilder
import com.strings.airqualityvisualizer.preferences.MyPreferences
import com.google.gson.Gson
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), OnClickListener, OnTouchListener {

    lateinit var login_signUp: TextView
    lateinit var login_emailNumber: EditText
    private var isMobile: Boolean = false

    lateinit var view: View
    lateinit var register_progressBar: ProgressBar
    lateinit var resent: TextView
    lateinit var progressBar_cardView: RelativeLayout

    private lateinit var password: String

    lateinit var forgot_password: TextView
    lateinit var login_create: TextView
    lateinit var cb_remember_me: CheckBox

    val PASSWORD = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    )
    lateinit var pwd_password: EditText
//    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
//        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//                "\\@" +
//                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//                "(" +
//                "\\." +
//                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
//                ")+"
//    )


    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )

    val PHONE_NUMBER_PATTERN = Pattern.compile(
        "[0-9]{10}"
    )

    lateinit var preferences: MyPreferences
    var passwordVisiable = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    fun init() {
        preferences = MyPreferences(this)

        login_signUp = findViewById(R.id.txt_sign_in_here)
        login_emailNumber = findViewById(R.id.login_emailNumber)
        pwd_password = findViewById(R.id.pwd_password)
        cb_remember_me = findViewById(R.id.cb_remember_me)
        login_signUp.setOnClickListener(this)

        view = findViewById(R.id.btn_progressBar)
        register_progressBar = view.findViewById(R.id.register_progressBar)

        progressBar_cardView = view.findViewById(R.id.progressBar_cardView)
        forgot_password = findViewById(R.id.forgot_password)
        login_create = findViewById(R.id.txt_otp_resend)

        register_progressBar.visibility = View.GONE
        resent = view.findViewById(R.id.resent)
        resent.text = getString(R.string.login)
        progressBar_cardView.setOnClickListener(this)

        forgot_password.setOnClickListener(this)
        login_create.setOnClickListener(this)
        pwd_password.setOnTouchListener(this)

        pwd_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var pwd = pwd_password.text.toString().trim()

                if (!(PASSWORD.toRegex().matches(pwd))) {
                    if (pwd.length > 5) {
                        showToast(this@LoginActivity, getString(R.string.valid_password))
                    }
                } else {
                    showToast(this@LoginActivity, getString(R.string.password_verify_done))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }


    override fun onClick(p0: View?) {
        val id = p0!!.id
        when (id) {
            R.id.progressBar_cardView -> {

                password = pwd_password.text.toString()
                if (btLogin() == true) {
                    addLogin(login_emailNumber.text.toString())
                }
            }
            R.id.txt_sign_in_here -> {
                btSignup()
            }
            R.id.forgot_password -> {
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
            R.id.txt_otp_resend -> {
                btSignup()
            }

        }
    }


    private fun addLogin(email: String) {
        register_progressBar.visibility = View.VISIBLE

        val response = ServiceBuilder(this@LoginActivity).buildService(RestApi::class.java)

        var str_email = ""
        var str_mobile = ""

        if (isMobile == false) {
            str_email = email
        } else {
            str_mobile = email
        }

        val payload = LoginPayload(
            str_email, password, str_mobile, false
        )

//        val payload = LoginPayload(
//            "apurva.skyttus@gmail.com", "Test@123", "9714675391", false
//        )

        response.addLogin(payload).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>
            ) {
                showToast(this@LoginActivity, response.body()?.message.toString())
                register_progressBar.visibility = GONE
                if (response.body()?.isSuccess == true) {
                    var intent = Intent(this@LoginActivity, LoginOtpActivity::class.java)
                    intent.putExtra("data", Gson().toJson(response.body()?.data))
                    intent.putExtra("isChecked", cb_remember_me.isChecked)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                register_progressBar.visibility = GONE
                showToast(this@LoginActivity, getString(R.string.login_failed))
            }

        })
    }


    fun btLogin(): Boolean {

        if (login_emailNumber.length() == 0) {
            login_emailNumber.setError(getString(R.string.valid_error));
            return false
        }

        val str_email = login_emailNumber.text.toString().trim()
        if (str_email.contains("@")) {
            isMobile = false
            if (!(EMAIL_ADDRESS_PATTERN.toRegex().matches(str_email))) {
                login_emailNumber.setError(getString(R.string.email_error));
                return false
            }
        } else {
            if (TextUtils.isDigitsOnly(str_email)) {
                isMobile = true
                if (!(PHONE_NUMBER_PATTERN.toRegex().matches(str_email))) {
                    login_emailNumber.setError(getString(R.string.phone_error));
                    return false
                }
            } else if (!(EMAIL_ADDRESS_PATTERN.toRegex().matches(str_email))) {
                login_emailNumber.setError(getString(R.string.email_error));
                return false
            }
        }

        if (pwd_password.length() == 0) {
            pwd_password.setError(getString(R.string.password_error));
            return false;
        }

        return true
    }

    fun btSignup() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        val Right = 2
        if (p1?.action == MotionEvent.ACTION_UP) {
            if (p1.getRawX() >= pwd_password?.right!!.minus(
                    pwd_password.compoundDrawables[Right].getBounds().width()
                )
            ) {
                var selecion = pwd_password.selectionEnd

                if (passwordVisiable) {
                    pwd_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_lock, 0, R.drawable.ic_eye, 0
                    )
                    pwd_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                    passwordVisiable = false
                } else {
                    pwd_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        R.drawable.ic_lock, 0, R.drawable.ic_eye_off, 0
                    )
                    pwd_password.setTransformationMethod(PasswordTransformationMethod.getInstance())
                    passwordVisiable = true
                }

                pwd_password.setSelection(selecion)
                return true
            }
        }

        return false
    }
}