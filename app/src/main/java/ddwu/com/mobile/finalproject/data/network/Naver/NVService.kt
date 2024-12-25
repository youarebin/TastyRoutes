package ddwu.com.mobile.finalproject.data.network.Naver

import android.content.Context
import android.util.Log
import ddwu.com.mobile.finalproject.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NVService(val context: Context) {
    private val TAG = "NVSerivce"
    val service:NVLocationApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.naver_url)) // 네이버 API의 기본 URL
            .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 추가
            .build()

        service = retrofit.create(NVLocationApi::class.java)
    }

    // Naver OpenAPI 를 이용하여 식당 정보 검색결과 반환
    suspend fun getLocationData(query: String) : List<LocationDetail>? {
        val clientID = context.resources.getString(R.string.client_id)
        val clientSecret = context.resources.getString(R.string.client_secret)

        val locationRoot = service.getLocationByKeyword(query, "random", clientID, clientSecret)
        Log.d(TAG, "getLocationData ${locationRoot.items}")
        return locationRoot.items
    }

}