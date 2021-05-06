package com.example.cardiaryclient.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardiaryclient.R
import com.example.cardiaryclient.databinding.ActivityMainBinding
import com.example.cardiaryclient.network.RetrofitInstance
import com.example.cardiaryclient.ui.adapters.CarDtoAdapter
import com.example.cardiaryclient.ui.viewmodels.CarsViewModel
import com.example.cardiaryclient.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val LOG_TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var carsAdapter: CarDtoAdapter
    private val carsViewModel: CarsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        with(binding) {
            btnLoadData.setOnClickListener {
                carsViewModel.getCars()
                carsViewModel.res.observe(this@MainActivity, Observer { resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            progress.visibility = View.GONE
                            rvCarsList.visibility = View.VISIBLE
                            resource.data?.let { response ->
                                carsAdapter.submitList(response.content)
                            }
                        }
                        Status.LOADING -> {
                            progress.visibility = View.VISIBLE
                            rvCarsList.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            progress.visibility = View.GONE
                            rvCarsList.visibility = View.VISIBLE
                            Snackbar.make(
                                root,
                                "Something went wrong when requesting cars list",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            }
        }
    }

    private fun setupRecyclerView() = binding.rvCarsList.apply {
        carsAdapter = CarDtoAdapter()
        adapter = carsAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}