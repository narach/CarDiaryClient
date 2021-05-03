package com.example.cardiaryclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardiaryclient.R
import com.example.cardiaryclient.databinding.ActivityMainBinding
import com.example.cardiaryclient.network.RetrofitInstance
import com.example.cardiaryclient.ui.adapters.CarDtoAdapter
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val LOG_TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private lateinit var carsAdapter: CarDtoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        with(binding) {
            btnLoadData.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    val response = try {
                        RetrofitInstance.carsApi.getCars()
                    } catch (e: IOException) {
                        tvErrors.text = "Error: ${e.message}"
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        tvErrors.text = "Error: ${e.message}"
                        return@launchWhenCreated
                    }

                    if(response.isSuccessful && response.body() != null) {
                        response.body()?.let { carsList ->
                            carsAdapter.cars = carsList
                        }
                    } else {
                        tvErrors.text = "Server error. Status code: ${response.code()}, error: ${response.errorBody()}"
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.rvCarsList.apply {
        carsAdapter = CarDtoAdapter()
        adapter = carsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}