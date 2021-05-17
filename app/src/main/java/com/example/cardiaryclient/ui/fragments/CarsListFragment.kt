package com.example.cardiaryclient.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardiaryclient.databinding.FragmentCarsListBinding
import com.example.cardiaryclient.ui.adapters.CarDtoAdapter
import com.example.cardiaryclient.ui.viewmodels.CarsViewModel
import com.example.cardiaryclient.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarsListFragment : Fragment() {

    private lateinit var binding: FragmentCarsListBinding
    private lateinit var carDtoAdapter: CarDtoAdapter
    private lateinit var fContext: Context
    private val carsViewModel: CarsViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carDtoAdapter = CarDtoAdapter(fContext)
        binding.rvCars.layoutManager = LinearLayoutManager(fContext)
        binding.rvCars.adapter = carDtoAdapter

        with(binding) {
            carsViewModel.getCars()
            carsViewModel.res.observe(viewLifecycleOwner, Observer { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        progress.visibility = View.GONE
                        rvCars.visibility = View.VISIBLE
                        resource.data?.let { response ->
                            carDtoAdapter.submitList(response.content)
                        }
                    }
                    Status.LOADING -> {
                        progress.visibility = View.VISIBLE
                        rvCars.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        progress.visibility = View.GONE
                        rvCars.visibility = View.VISIBLE
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