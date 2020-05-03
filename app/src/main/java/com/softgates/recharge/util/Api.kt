package com.softgates.recharge.util

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.softgates.recharge.network.ResponseModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}

private const val BASE_URL = " http://enetwork.esmartg.com/api/"
//private const val BASE_URL = "http://softgates.ae/recharge/api/"

enum class ApiStatus { LOADING, ERROR, DONE }


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()
/**
 * A public interface that exposes the [getProperties] method
 */

interface RechargeApiService {
    //
    /**
     * Returns a Coroutine [Deferred] [List] of [MarsProperty] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
//    @GET("everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
//    fun getNews(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Single<WishListResponse>

    @GET("plans")
    fun getPlan(
    ): Deferred<ResponseModel>

    // @FieldMap
  //  @Multipart
 //   @FormUrlEncoded

    @POST("login")
    fun login(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    @POST("search")
    fun search(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    @POST("capture_info")
    fun selectPlan(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>


    @POST("renew")
    fun selectrevenue(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    /*@Headers("Accept: application/vnd.yourapi.v1.full+json", "User-Agent: Your-App-Name")
    @GET("/tasks/{task_id}")
    fun getTask(@Path("task_id") taskId: Long): Task*/

    /*  companion object {
        private const val BASE_URL = "https://www.reddit.com/"
        fun create(): HotShelfApiService = create(HttpUrl.parse(BASE_URL)!!)
        fun create(httpUrl: HttpUrl): HotShelfApiService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(HotShelfApiService::class.java)
        }
    }*/

/*    companion object {
        private const val BASE_URL = "https://www.reddit.com/"
        fun create(): HotShelfApiService = create(HttpUrl.parse(BASE_URL)!!)
        fun create(httpUrl: HttpUrl): HotShelfApiService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HotShelfApiService::class.java)
        }
    }*/

    companion object {
        fun getService(): RechargeApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
//                .baseUrl("http://18.218.168.60/hotshelf/api/")
                .baseUrl("https://www.hotshelf.com/dev/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RechargeApiService::class.java)
            // .baseUrl("https://androidwave.com")
        }
    }
}

object RechargeApi {
    val retrofitService: RechargeApiService by lazy { retrofit.create(RechargeApiService::class.java) }
}


