package com.miitdiplomasoft.forgotintransport.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.miitdiplomasoft.forgotintransport.R
import com.miitdiplomasoft.forgotintransport.data.authorization.Token
import com.miitdiplomasoft.forgotintransport.databinding.FragmentSearchBinding
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import com.miitdiplomasoft.forgotintransport.ui.adapters.SearchGridItemsAdapter
import com.miitdiplomasoft.forgotintransport.viewmodels.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchFragmentViewModel: SearchFragmentViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.thingsGridRecycler.layoutManager = GridLayoutManager(binding.root.context, 3)

        searchFragmentViewModel.items.observe(viewLifecycleOwner) { items ->

            when (items) {
                ArrayList<MenuItemResponse>() -> {
                    binding.thingsGridRecycler.adapter = SearchGridItemsAdapter(items)
                }
                else -> {
                    Toast.makeText(context, "Повторите попытку позже", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnFind.setOnClickListener {
            val requestName =  binding.inputRequest.text.toString()
            searchFragmentViewModel.getNamItems(Token.getAuthorizationToken(requireContext())!!, requestName)
        }

        binding.bottomNavigation.selectedItemId = R.id.navigation_search_admin
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_menu_admin -> {
                    findNavController().navigate(R.id.action_searchFragment_to_menuFragment)
                    true
                }
                R.id.navigation_favorites -> {
                    findNavController().navigate(R.id.action_searchFragment_to_favoritesFragment)
                    true
                }
                R.id.navigation_search_admin -> {
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
