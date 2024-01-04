package edu.gatech.seclass.ecommerence_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import edu.gatech.seclass.ecommerence_app.R
import edu.gatech.seclass.ecommerence_app.data.User
import edu.gatech.seclass.ecommerence_app.databinding.FragmentRegisterBinding
import edu.gatech.seclass.ecommerence_app.util.Resource
import edu.gatech.seclass.ecommerence_app.viewmodel.RegisterViewModel

private val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    // Called to have the fragment instantiate its user interface view.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Apply scope function to binding to handle view interactions.
        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    edFirstNameRegister.text.toString().trim(),
                    edLastNameRegister.text.toString().trim(),
                    edEmailRegister.text.toString().trim()
                )

                val password = edPasswordRegister.text.toString()

                // Call ViewModel to create an account with email and password.
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        // Launch a coroutine that is scoped to the lifecycle of the fragment.
        lifecycleScope.launchWhenStarted {

            // Collect from the 'register' state flow in the ViewModel.
            viewModel.register.collect{

                // Handle different states
                when(it){
                    is Resource.Loading -> {
                        binding.buttonRegisterRegister.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("test", it.data.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }

                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }

                }
            }
        }
    }
}