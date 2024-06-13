package com.miitdiplomasoft.forgotintransport.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.miitdiplomasoft.forgotintransport.R
import com.miitdiplomasoft.forgotintransport.databinding.FragmentFavoritesBinding
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import com.miitdiplomasoft.forgotintransport.ui.adapters.FavoritesGridItemsAdapter
import com.miitdiplomasoft.forgotintransport.viewmodels.FavoritesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoritesFragmentViewModel: FavoritesFragmentViewModel by viewModels()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.thingsGridRecycler.layoutManager = GridLayoutManager(binding.root.context, 3)

        favoritesFragmentViewModel.items.observe(viewLifecycleOwner) { items ->
            when (items) {
                ArrayList<MenuItemResponse>() -> {
                    binding.thingsGridRecycler.adapter = FavoritesGridItemsAdapter(items)
                }
                else -> {
                    Toast.makeText(context, "Повторите попытку позже", Toast.LENGTH_SHORT).show()
                }
            }
        }
        favoritesFragmentViewModel.getFavItems()

        binding.bottomNavigation.selectedItemId = R.id.navigation_favorites
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_menu_admin -> {
                    findNavController().navigate(R.id.action_favoritesFragment_to_menuFragment)
                    true
                }
                R.id.navigation_favorites -> {
                    true
                }
                R.id.navigation_search_admin -> {
                    findNavController().navigate(R.id.action_favoritesFragment_to_searchFragment)
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
