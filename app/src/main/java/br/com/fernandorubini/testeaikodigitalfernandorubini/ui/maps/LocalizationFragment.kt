package br.com.fernandorubini.testeaikodigitalfernandorubini.ui.maps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.fernandorubini.testeaikodigitalfernandorubini.model.PositionBus
import br.com.fernandorubini.testeaikodigitalfernandorubini.service.ApiService
import br.com.fernandorubini.testeaikofernandorubini.R
import br.com.fernandorubini.testeaikofernandorubini.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LocalizationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private lateinit var mMap: GoogleMap
    private val client = OkHttpClient()
    private var markersList: ArrayList<Marker> = ArrayList()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val localizationViewModel =
            ViewModelProvider(this).get(LocalizationViewModel::class.java)

        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        localizationViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fetchAndShowBusPositions()
        moveCameraToSaoPaulo()
        setupCameraListener()
    }

    private fun moveCameraToSaoPaulo() {
        val saoPaulo = LatLng(-23.5489, -46.6388)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 17f))
    }

    private fun fetchAndShowBusPositions() {
        val request = Request.Builder()
            .url(ApiService.BASE_URL_POSITION)
            .addHeader("Authorization",ApiService.AUTH_TOKEN)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, "Falha ao obter dados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseBody ->
                    val type = object : TypeToken<PositionBus>() {}.type
                    val posicaoResponse: PositionBus = Gson().fromJson(responseBody, type)
                    activity?.runOnUiThread {
                        addMarkers(posicaoResponse)
                    }
                }
            }
        })
    }

    private fun addMarkers(posicaoResponse: PositionBus) {
        val customMarkerIcon = bitmapDescriptorFromVector(R.drawable.bus_position)

        for (linha in posicaoResponse.l) {
            for (veiculo in linha.vs) {
                val position = LatLng(veiculo.py, veiculo.px)
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title("Veículo: ${veiculo.p}")
                        .icon(customMarkerIcon)
                )
                if (marker != null) {
                    markersList.add(marker)
                }
            }
        }

        posicaoResponse.l.firstOrNull()?.vs?.firstOrNull()?.let { veiculo ->
            val firstPosition = LatLng(veiculo.py, veiculo.px)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPosition, 17f))
        }
    }

    private fun setupCameraListener() {

        val MIN_DISTANCE_METERS = 500

        mMap.setOnCameraIdleListener {
            val cameraPosition = mMap.cameraPosition
            val cameraTarget = cameraPosition.target

            for (marker in markersList) {
                val markerPosition = marker.position

                val distance = calculateDistance(cameraTarget, markerPosition)

                marker.isVisible = distance <= MIN_DISTANCE_METERS
            }
        }
    }

    private fun calculateDistance(latLng1: LatLng, latLng2: LatLng): Float {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results)
        return results[0]
    }

    private fun bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
        val vectorDrawable: Drawable? = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}



