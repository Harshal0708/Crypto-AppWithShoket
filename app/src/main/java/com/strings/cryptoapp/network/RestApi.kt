package com.strings.cryptoapp.network

import com.strings.cryptoapp.Constants
import com.strings.cryptoapp.Response.*
import com.strings.cryptoapp.model.*
import com.strings.cryptoapp.modual.card.CardPaymentIntentResponse
import com.strings.cryptoapp.modual.payment.CreateCustomerIdResponse
import com.strings.cryptoapp.modual.payment.EphemeralKeyResponse
import com.strings.cryptoapp.modual.payment.PaymentIntentRespomse
import com.strings.cryptoapp.modual.payment.StripeConfirmationResponse
import com.strings.cryptoapp.modual.payment.createcustomer.CreateCustomerResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RestApi {
    @POST(Constants.login)
    fun addLogin(@Body loginPayload: LoginPayload): Call<LoginResponse>

    @POST(Constants.verifyLoginOtp)
    fun addVerifyLoginOtp(@Body verifyLoginOtpPayload: VerifyLoginOtpPayload): Call<SendRegistrationOtpResponce>

    @POST(Constants.sendLoginOtp)
    fun addSendLoginOtp(@Body sendLoginOtpPayload: SendLoginOtpPayload): Call<SendRegistrationOtpResponce>


//    @POST(Constants.register)
//    fun addRegister(@Body registerPayload: RegisterPayload): Call<RegisterResponse>

    @FormUrlEncoded
    @POST(Constants.register)
    fun addRegister(
        @Field("FirstName") FirstName: String,
        @Field("LastName") LastName: String,
        @Field("Password") Password: String,
        @Field("Email") Email: String,
        @Field("PhoneNumber") PhoneNumber: String,
        @Field("ProfileImage") ProfileImage: String,

    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST(Constants.updateProfileDetail)
    fun updateProfileDetail(
        @Field("id") id: String,
        @Field("email") email: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("profileImage") profileImage: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("apiKey") apiKey: String,
        @Field("secretKey") secretKey: String,
    ): Call<Userupdatedsuccessfully>

    @POST(Constants.sendRegistrationOtp)
    fun addSendRegistrationOtp(@Body sendRegistrationOtpPayload: SendRegistrationOtpPayload): Call<SendRegistrationOtpResponce>


    @POST(Constants.verifyRegistrationOtp)
    fun addVerifyRegistrationOtpp(@Body verifyRegistrationOtpPayload: VerifyRegistrationOtpPayload): Call<SendRegistrationOtpResponce>


    @POST(Constants.forgotpassword)
    fun addForgotPassword(@Body forgotPayload: ForgotPayload): Call<ForgotResponse>


    @POST(Constants.resetpassword)
    fun addResetpassword(@Body resetPayload: ResetPayload): Call<ResetResponse>


    @POST(Constants.registrationconfirm)
    fun addOtp(
        @Query("emailOtp") emailOtp: String,
        @Query("mobileOtp") mobileOtp: String,
        @Query("email") email: String,
        @Query("mobile") mobile: String,
    ): Call<OtpResponse>

    @GET(Constants.strategy)
    suspend fun getStrategy(): Response<StrategyRes>

    @GET(Constants.getProfileDetail)
    suspend fun getUserDetails(@Query("email") email: String): Response<UserDetailsResponse>

    @GET("${Constants.strategy}/{id}")
    suspend fun getStrategyById(@Path("id") id: String): Response<StrategyDetailRes>

    @GET(Constants.getplans)
    suspend fun getPlans(): Response<GetPlanResponse>

    @POST(Constants.getusersubscription)
    fun addUserSubscription(@Body userSubscriptionModel: UserSubscriptionModel): Call<UserSubscriptionResponse>

    @POST(Constants.getSubscriptionDetails)
    fun addSubscriptionDetails(@Body userSubscriptionModel: UserSubscriptionModel): Call<UserSubscriptionDetail>

    @POST(Constants.getOrderHistoryList)
    fun addOrderHistoryList(@Body getOrderHistoryListPayload: GetOrderHistoryListPayload): Call<OrderHistoriesResponse>

    @POST(Constants.getSubscriptionHistoryList)
    fun addSubscriptionHistoryList(@Body getOrderHistoryListPayload: GetOrderHistoryListPayload): Call<UserSubscriptionsResponse>

    @GET(Constants.cmsAdsList)
    suspend fun getCmsAdsList(@Query("userId") userId: String): Response<CmsAdsListResponse>

    @POST(Constants.cmsAdsAdd)
    fun addCmsAdsAdd(@Body cmsAdsAddPayload: CmsAdsAddPayload): Call<CmsAdsAddResponse>

    @POST(Constants.stripe_payment_customers_id)
    fun addCustomerIdCreate(): Call<CreateCustomerIdResponse>

    @Headers("Stripe-Version: 2022-11-15")
    @POST(Constants.stripe_payment_ephemeral_keys)
    fun addEhemeralkeys(@Query("customer") customer: String): Call<EphemeralKeyResponse>

    @POST(Constants.stripe_payment_intents)
    fun addStripePaymentIntents(
        @Query("customer") customer: String,
        @Query("amount") amount: String,
        @Query("currency") currency: String,
        @Query("setup_future_usage") setup_future_usage: String,
        @Query("automatic_payment_methods[enabled]") automatic_payment_methods: Boolean

    ): Call<PaymentIntentRespomse>

    @GET("${Constants.stripe_payment_intents}/{id}")
    suspend fun getStripePaymentId(@Path("id") id: String): Response<StripeConfirmationResponse>

    @POST(Constants.stripe_payment_intents)
    fun addCardPaymentIntents(
        @Query("amount") amount: String,
        @Query("currency") currency: String
    ): Call<CardPaymentIntentResponse>

    //CreateCustomer

//    @Headers({
//        "Content-Type:application/x-www-form-urlencode"
//        "Authorization:Bearer sk_test_51MFAOfSHmxsQH4CHc0B63ccrQu8tu1m9ynAXYaEydRrSQwp4nhBqKtJFaEYZn9aTYhsdw1Ti8VHA9Cw4ZRcZR8Lg00qUCjkGZk"
//    })
@Headers("Content-Type:application/x-www-form-urlencode","Authorization: Bearer sk_test_51MFAOfSHmxsQH4CHc0B63ccrQu8tu1m9ynAXYaEydRrSQwp4nhBqKtJFaEYZn9aTYhsdw1Ti8VHA9Cw4ZRcZR8Lg00qUCjkGZk")
@POST(Constants.stripe_payment_customers_id)
fun addCreateCustomer(
    @Query("email") email: String,
    @Query("name") name: String

):Call<CreateCustomerResponse>
}