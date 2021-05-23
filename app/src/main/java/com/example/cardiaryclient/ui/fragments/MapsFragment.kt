package com.example.cardiaryclient.ui.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cardiaryclient.R
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        // Добавляем маркер с текущим местоположением
        val itAcademyPos = LatLng(52.40533197184692, 30.920708495998014)
        map.addMarker(MarkerOptions().position(itAcademyPos).title("ItAcademy Gomel"))
//        map.moveCamera(CameraUpdateFactory.newLatLng(itAcademyPos))

        // Двигаем камеру и приближаем
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(itAcademyPos, 14f))

        // Задаем границы отображаемой области карты: ( Город Гомель )
        val southWest = LatLng(52.402930382994, 30.906903486026984)
        val northEast = LatLng(52.49806879410871, 31.052204820104112)
        var gomelBounds = LatLngBounds(southWest, northEast)
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(gomelBounds, 0))
    }
}