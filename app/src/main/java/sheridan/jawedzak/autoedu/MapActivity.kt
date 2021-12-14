package sheridan.jawedzak.autoedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import java.io.IOException


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap?) {
//        googleMap.myLocation
//        // Check if we were successful in obtaining the map.
//        // Check if we were successful in obtaining the map.
//        if (googleMap != null) {
//            googleMap.setOnMyLocationChangeListener(OnMyLocationChangeListener { arg0 ->
//                mMap.addMarker(
//                    MarkerOptions().position(LatLng(arg0.latitude, arg0.longitude))
//                        .title("It's Me!")
//                )
//            })
//        }


        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder().url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=43.6858146%2C-79.7599337&radius=1500&keyword=autoshop&key=AIzaSyCtCAwdktjLFNStUu-m_uLUQM6qoUyQFlg").method(
            "GET",
            null
        ).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val responseBody = response.body()?.string()
                    println(responseBody)
                    if (responseBody != null) {
                        renderMap(googleMap, responseBody)
                    }

                }
            }
        })
    }

    fun renderMap(googleMap: GoogleMap?, response: String) {

        runOnUiThread {
            googleMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(43.7147702, -79.7984136), 12.5f)
            )
            googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(43.7147702, -79.7984136))
                    .title("Speedy Auto Service Brampton North")
            )
        }
    }
}