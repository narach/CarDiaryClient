package com.example.cardiaryclient.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cardiaryclient.R
import com.example.cardiaryclient.databinding.FragmentMapViewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.slider.Slider
import kotlin.math.roundToInt

class MapViewFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapViewBinding

    private lateinit var mapView: MapView

    private var googleMap: GoogleMap? = null

    private var sovietDistrictPolygon: Polygon? = null
    private var railwayDistrictPolygon: Polygon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapViewBinding.inflate(inflater, container, false)

        // Initialize mapView
        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView = binding.mapView
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().also {
            outState.putBundle(MAPVIEW_BUNDLE_KEY, it)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            cbShowDistricts.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    googleMap?.let { gMap ->
                        val railwayDistrictBorders = listOf(
                            LatLng(52.428079931403914, 30.888557864139507),
                            LatLng(52.437446805784646, 30.88578970396107),
                            LatLng(52.46207808630946, 30.911118369593773),
                            LatLng(52.485104533234114, 30.967835427948465),
                            LatLng(52.46814434142912, 30.97824608042651),
                            LatLng(52.443150529497146, 30.986022712468724)
                        )
                        railwayDistrictPolygon = gMap.addPolygon(
                            PolygonOptions().addAll(railwayDistrictBorders)
                                .fillColor(Color.GRAY)
                        )

                        val sovietDistrictBorders = listOf(
                            LatLng(52.42765469983529, 30.88870015786848),
                            LatLng(52.44247257655762, 30.98010765733915),
                            LatLng(52.43955030095464, 30.987504011203992),
                            LatLng(52.42092667871693, 30.993256730876638),
                            LatLng(52.410818191064145, 31.00626883489811),
                            LatLng(52.400289534465216, 30.983805834271575),
                            LatLng(52.390677852401026, 30.953261632154522),
                            LatLng(52.39101220690249, 30.910801082189725)
                        )
                        sovietDistrictPolygon = gMap.addPolygon(PolygonOptions()
                            .addAll(sovietDistrictBorders)
                            .strokeColor(Color.RED)
//                            .fillColor(Color.RED)
                        )
                    }
                } else {
                    sovietDistrictPolygon?.remove()
                    railwayDistrictPolygon?.remove()
                }
            }

            rgMapType.setOnCheckedChangeListener { _, checkedId ->
                switchMapType(checkedId)
            }

            slZoom.addOnChangeListener(object : Slider.OnChangeListener {
                override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                    val newZoomValue = slider.value
                    googleMap?.moveCamera(CameraUpdateFactory.zoomTo(newZoomValue))
                }
            })
        }
    }

    private fun switchMapType(mapTypeId: Int) {
        googleMap?.let { gMap ->
            when(mapTypeId) {
                R.id.rbHybrid -> gMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                R.id.rbRoadmap -> gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                R.id.rbSatellite -> gMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                R.id.rbTerrain -> gMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                else -> {
                    gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        map.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker"))
        val southWest = LatLng(52.34792498339976, 30.885439351718823)
        val northEast = LatLng(52.517307048671874, 31.089577892516164)
        val cityBounds = LatLngBounds(southWest, northEast)
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(cityBounds, 0))
//        drawPolygon(map)
        googleMap = map

        var zoomLevel = map.cameraPosition.zoom
        Log.d("Zoom level", "$zoomLevel")

        binding.slZoom.value = zoomLevel.roundToInt().toFloat()

        map.setOnCameraMoveListener(object : GoogleMap.OnCameraMoveListener {
            override fun onCameraMove() {
                var newZoomLevel = map.cameraPosition.zoom
                binding.slZoom.value = newZoomLevel.roundToInt().toFloat()
                Log.d("Zoom level", "$newZoomLevel")
            }
        })

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"

        private val sovietDistrcitBorder = listOf(
            LatLng(52.42814388774569, 30.88836725889775),
            LatLng(52.44255306087458, 30.980498264555948),
            LatLng(52.439337493892204, 30.988058260435),
            LatLng(52.424606378122704, 30.991957520247254),
            LatLng(52.41702664935201, 30.997159018332024),
            LatLng(52.412343319942345, 31.007574874167737),
            LatLng(52.40144713047637, 31.001255662648237),
            LatLng(52.39145756689086, 30.961289152273885),
            LatLng(52.397323598754355, 30.935887696558943),
            LatLng(52.39238666896649, 30.896129761634686)
        )
    }
}