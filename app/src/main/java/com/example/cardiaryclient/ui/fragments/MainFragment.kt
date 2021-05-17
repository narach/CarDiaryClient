package com.example.cardiaryclient.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.cardiaryclient.R
import com.example.cardiaryclient.databinding.FragmentMainBinding
import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.UserCredentials
import com.example.cardiaryclient.ui.viewmodels.CarsViewModel
import com.example.cardiaryclient.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val carsViewModel: CarsViewModel by viewModels()
    private var authToken: AuthToken? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnGoToList.setOnClickListener {
                val animOptions = navOptions {
                    anim {
                        enter = R.anim.slide_in_right
                        exit = R.anim.slide_out_left
                        popEnter = R.anim.slide_in_left
                        popExit = R.anim.slide_out_right
                    }
                }
                findNavController().navigate(R.id.action_main_to_list, null, animOptions)
            }

            btnAuthenticate.setOnClickListener {
                val login = etEmail.text.toString()
                val password = etPassword.text.toString()
                val credentials = UserCredentials(login, password)
                carsViewModel.authenticateUser(credentials)
                carsViewModel.auth.observe(viewLifecycleOwner, { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { response ->
                                authToken = response
                                btnGoToList.isEnabled = true
                            }
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                root,
                                "User authentication failed!",
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.e("AuthError", resource.message.toString())
                        }
                    }
                })
            }
        }
    }
}