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
import com.miitdiplomasoft.forgotintransport.databinding.FragmentCardBinding
import com.miitdiplomasoft.forgotintransport.viewmodels.CardFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.http.HttpStatusCode
import timber.log.Timber

@AndroidEntryPoint
class CardFragment : Fragment() {

    private val cardFragmentViewModel: CardFragmentViewModel by viewModels()

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private val args: CardFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        cardFragmentViewModel.item.observe(viewLifecycleOwner) { result ->
            Glide.with(this).load(result.imageUrl).into(binding.itemCardImg)
            binding.itemName.text = result.name
            binding.isLikedCheckbox.isChecked = result.isLiked!!
            binding.itemLocationInfo.text = result.locationInfo
            binding.itemNumbersInfo.text = result.numbersInfo
        }

        cardFragmentViewModel.getItem(Token.getAuthorizationToken(requireContext())!!, args.id)

        cardFragmentViewModel.favoriteStatusChangeResult.observe(viewLifecycleOwner) { result ->
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

        binding.isLikedCheckbox.setOnClickListener {
            cardFragmentViewModel.changeFavoriteStatus(args.id)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(CardFragmentDirections.actionCardFragmentToMenuFragment())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}