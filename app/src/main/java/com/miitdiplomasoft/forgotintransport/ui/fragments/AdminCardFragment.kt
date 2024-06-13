package com.miitdiplomasoft.forgotintransport.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.miitdiplomasoft.forgotintransport.data.authorization.Token
import com.miitdiplomasoft.forgotintransport.databinding.FragmentAdminCardBinding
import com.miitdiplomasoft.forgotintransport.viewmodels.AdminCardFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.http.HttpStatusCode
import timber.log.Timber

@AndroidEntryPoint
class AdminCardFragment : Fragment() {

    private val adminCardFragmentViewModel: AdminCardFragmentViewModel by viewModels()

    private var _binding: FragmentAdminCardBinding? = null
    private val binding get() = _binding!!

    private val args: CardFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAdminCardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adminCardFragmentViewModel.item.observe(viewLifecycleOwner) { result ->
            Glide.with(this).load(result.imageUrl).into(binding.itemCardImg)
            binding.itemName.text = result.name
            binding.itemLocationInfo.text = result.locationInfo
            binding.itemNumbersInfo.text = result.numbersInfo
        }

        adminCardFragmentViewModel.getItem(Token.getAuthorizationToken(requireContext())!!, args.id)

        adminCardFragmentViewModel.deleteResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                HttpStatusCode.OK -> {
                    Toast.makeText(context, "Операция выполнена", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "Повторите позднее", Toast.LENGTH_SHORT).show()
                    Timber.tag("EXCEPTION_TAG").d("$result")
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            adminCardFragmentViewModel.delete(args.id)
            findNavController().navigate(AdminCardFragmentDirections.actionAdminCardFragmentToAdminMenuFragment())
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(AdminCardFragmentDirections.actionAdminCardFragmentToAdminMenuFragment())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}