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
import com.miitdiplomasoft.forgotintransport.data.authorization.Token
import com.miitdiplomasoft.forgotintransport.databinding.FragmentAuthorizationBinding
import com.miitdiplomasoft.forgotintransport.viewmodels.AuthorizationFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private val authorizationFragmentViewModel: AuthorizationFragmentViewModel by viewModels()

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        authorizationFragmentViewModel.authorizationResultUser.observe(viewLifecycleOwner) { result ->
            when (result) {
                "-1" -> {
                    Toast.makeText(context, "Авторизация не удалась", Toast.LENGTH_SHORT).show()
                    Timber.tag("EXCEPTION_TAG").d("$result")
                }
                else -> {
                    Token.saveAuthorizationToken(requireContext(), result)
                    findNavController().navigate(R.id.action_authorizationFragment_to_menuFragment)
                }
            }
        }

        binding.btnLogIn.setOnClickListener {
            val login = binding.inputLogin.text.toString()
            val password = binding.inputPassword.text.toString()
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, R.string.empty_fields_error, Toast.LENGTH_SHORT).show()
            } else {
                authorizationFragmentViewModel.authorizeAsUser(login, password)
            }
        }

        binding.btnLogInAsAdmin.setOnClickListener {
            findNavController().navigate(AuthorizationFragmentDirections.actionAuthorizationFragmentToAdminAuthorizationFragment())
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
