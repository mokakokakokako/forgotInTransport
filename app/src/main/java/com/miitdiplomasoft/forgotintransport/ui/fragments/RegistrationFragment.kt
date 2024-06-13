package com.miitdiplomasoft.forgotintransport.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.miitdiplomasoft.forgotintransport.R
import com.miitdiplomasoft.forgotintransport.databinding.FragmentRegistrationBinding
import com.miitdiplomasoft.forgotintransport.viewmodels.RegistrationFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.http.HttpStatusCode
import timber.log.Timber

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private val registrationFragmentViewModel: RegistrationFragmentViewModel by viewModels()

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        registrationFragmentViewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                HttpStatusCode.OK -> {
                    findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
                }
                else -> {
                    Toast.makeText(context, "Авторизация не удалась", Toast.LENGTH_SHORT).show()
                    Timber.tag("EXCEPTION_TAG").d("$result")
                }
            }
        }

        binding.btnLogIn.setOnClickListener {
            val login = binding.inputLogin.text.toString()
            val password = binding.inputPassword.text.toString()
            val passwordConfirm = binding.inputConfirmPassword.text.toString()
            if (login.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(context, R.string.empty_fields_error, Toast.LENGTH_SHORT).show()
            }
            else if (password != passwordConfirm) {
                Toast.makeText(context, R.string.mismatch_fields_error, Toast.LENGTH_SHORT).show()
            } else {
                registrationFragmentViewModel.register(login, password)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToAuthorizationFragment())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
