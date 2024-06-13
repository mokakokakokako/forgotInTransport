package com.miitdiplomasoft.forgotintransport.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.miitdiplomasoft.forgotintransport.R
import com.miitdiplomasoft.forgotintransport.databinding.FragmentAddItemBinding
import com.miitdiplomasoft.forgotintransport.viewmodels.AddItemFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.http.HttpStatusCode
import timber.log.Timber

@AndroidEntryPoint
class AddItemFragment : Fragment() {

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.itemCardImg.setImageURI(it)
        }
    }

    private val addItemFragmentViewModel: AddItemFragmentViewModel by viewModels()

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        addItemFragmentViewModel.addResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                HttpStatusCode.OK -> {
                    findNavController().navigate(R.id.action_addItemFragment_to_adminMenuFragment)
                }

                else -> {
                    Toast.makeText(context, "Добавить новую вещь не удалось", Toast.LENGTH_SHORT).show()
                    Timber.tag("EXCEPTION_TAG").d("$result")
                }
            }
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.inputName.text.toString()
            val location = binding.inputLocation.text.toString()
            val numbers = binding.inputNumbers.text.toString()
            if (name.isEmpty() || location.isEmpty() || numbers.isEmpty()) {
                Toast.makeText(context, R.string.empty_fields_error, Toast.LENGTH_SHORT).show()
            }
            else {
                addItemFragmentViewModel.add(name, location, numbers)
            }
        }

        binding.itemCardImg.setOnClickListener {
            getContent.launch("image/*")
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}