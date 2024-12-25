package ddwu.com.mobile.finalproject

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ddwu.com.mobile.finalproject.data.network.Naver.LocationDetail
import ddwu.com.mobile.finalproject.data.network.Naver.NVService
import ddwu.com.mobile.finalproject.databinding.ActivityMapBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MapActivity : AppCompatActivity() {

    val TAG = "MapActivity"

    val binding by lazy {
        ActivityMapBinding.inflate(layoutInflater)
    }

//    lateinit var fusedLocationClient : FusedLocationProviderClient
//    lateinit var locationRequest : LocationRequest
//    lateinit var locationCallback : LocationCallback

    private lateinit var googleMap: GoogleMap
    private lateinit var nvService: NVService
    private lateinit var geocoder : Geocoder

    private val mapReadyCallback = object : OnMapReadyCallback{
        override fun onMapReady(map: GoogleMap) {
            googleMap = map

            //마커 클릭시 상세 정보 표시
            googleMap.setOnMarkerClickListener { marker ->
                val location = marker.tag as LocationDetail

                //위치 상세 페이지로 이동
                val intent = Intent(this@MapActivity, LocationDetailActivity::class.java)
                intent.putExtra("locationDetail", location) // 클릭한 마커의 DTO 전달
                startActivity(intent)

                true
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //권한 확인
//        checkPermissions()

        // 네이버 서비스 객체 초기화
        nvService = NVService(this)

        //지도 객체 코드로 가져오기
        val mapFrgment : SupportMapFragment
             = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrgment.getMapAsync(mapReadyCallback) //map 정보 가져옴

        // 위치 권한 요청 (권한이 없으면 요청)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //locationRequest(위치 정보 요청 클래스) 객체 생성
//        locationRequest = LocationRequest.Builder(3000)
//            .setMinUpdateIntervalMillis(5000)
//            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
//            .build()
//
//        //위치 수신 정보 클래스
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {//위치 정보들을 보관하는 클래스
//                val currentLocation : Location = locationResult.locations[0]//위치 정보를 표현
//                Log.d(TAG, "위도: ${currentLocation.latitude}, " +
//                        "경도: ${currentLocation.longitude}")
//
//                // 현재 위치를 구글 맵에 표시
//                val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
//                centerMarker?.tag = currentLatLng
//            }
//        }

        //지오 코딩 : 현재 위치 기준
        geocoder = Geocoder(this, Locale.getDefault())

        //검색 버튼 클릭
        binding.button.setOnClickListener {
            val query = binding.etKeyword.text.toString()
            searchAndMarkLocation(query)
        }
    }

//    override fun onResume() {
//        super.onResume()
//
//        // 위치 권한 확인 후 위치 업데이트 시작
//        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//            || checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            startLocationRequest()
//        } else {
//            // 권한 요청
//            locationPermissionRequest.launch(
//                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
//            )
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        //위치 확인 종료
//        fusedLocationClient.removeLocationUpdates(locationCallback)
//    }
//
//    private fun startLocationRequest() {
//        super.onResume()
//        // 위치 권한이 승인된 후에 위치 업데이트를 요청
//        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//            || checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        }  else {
//            Log.d(TAG, "위치 권한이 없습니다. 권한을 요청합니다.")
//            locationPermissionRequest.launch(
//                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
//            )
//        }
//    }
//
//    val locationPermissionRequest = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions() ) { permissions ->
//        when {
//            permissions.getOrDefault(ACCESS_FINE_LOCATION, false)-> {
//                Log.d(TAG, "정확한 위치 사용 권한 승인됨")
//                startLocationRequest()  // 권한 승인 후 위치 업데이트 시작
//            }
//            permissions.getOrDefault(ACCESS_COARSE_LOCATION, false)-> {
//                Log.d(TAG, "근사 위치 사용 권한 승인됨")
//                startLocationRequest()  // 권한 승인 후 위치 업데이트 시작
//            }
//            else ->
//                Log.d(TAG, "권한 미승인")
//        }
//    }
//
//    private fun checkPermissions() {
//        if ( checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//            && checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
//            Log.d(TAG, "필요 권한 있음")
//            startLocationRequest()
//        } else {
//            locationPermissionRequest.launch(
//                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
//            )
//        }
//    }

    private fun searchAndMarkLocation(query: String) {
        lifecycleScope.launch {
            try {
                val locationsData = nvService.getLocationData(query)
                Log.d(TAG, "검색 결과: $locationsData")

                if (locationsData != null && locationsData.isNotEmpty()) {
                    googleMap.clear() // 기존 마커 제거
                    val geocoder = Geocoder(this@MapActivity, Locale.getDefault())

                    for (data in locationsData) {
                        // 장소 이름 기반으로 좌표 찾기
                        val addressList = geocoder.getFromLocationName(data.title, 1)
                        if (!addressList.isNullOrEmpty()) {
                            val address = addressList[0]
                            val targetLoc = LatLng(address.latitude, address.longitude)
                            Log.d("MapActivity", "Geocoded 좌표: 위도=${targetLoc.latitude}, 경도=${targetLoc.longitude}")

                            // 유효한 범위 내 좌표인지 확인
                            if (targetLoc.latitude in -90.0..90.0 && targetLoc.longitude in -180.0..180.0) {
                                val markerOptions = MarkerOptions().apply {
                                    position(targetLoc)
                                    title(data.title)
                                    snippet(data.category)
                                }
                                val marker = googleMap.addMarker(markerOptions)
                                marker?.tag = data // 마커에 데이터 추가
                            } else {
                                Log.e("MapActivity", "Invalid coordinates: 위도=${targetLoc.latitude}, 경도=${targetLoc.longitude}")
                            }
                        } else {
                            Log.e("MapActivity", "Geocoding 실패: ${data.title}")
                        }
                    }

                    // 첫 번째 위치로 지도 이동
                    val firstLocation = locationsData[0]
                    val firstAddressList = geocoder.getFromLocationName(firstLocation.title, 1)
                    if (!firstAddressList.isNullOrEmpty()) {
                        val firstAddress = firstAddressList[0]
                        googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(LatLng(firstAddress.latitude, firstAddress.longitude), 15f)
                        )
                    } else {
                        Log.e("MapActivity", "첫 번째 장소의 Geocoding 실패")
                    }
                } else {
                    Toast.makeText(this@MapActivity, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("MapActivity", "검색 실패: ${e.message}")
                Toast.makeText(this@MapActivity, "검색 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //마커 표시
    private lateinit var markerOptions : MarkerOptions
    private var centerMarker : Marker? = null

    fun addMarker(location : LocationDetail) {
        val targetLoc = LatLng(location.mapy / 1e6, location.mapx / 1e6)
        val markerOptions = MarkerOptions().apply {
            position(targetLoc)
            title(location.title)
            snippet(location.category)
        }
        val marker = googleMap.addMarker(markerOptions)
        marker?.tag = location // 마커에 데이터 추가
        marker?.showInfoWindow()
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 15f))
    }

}