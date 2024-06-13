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
import com.miitdiplomasoft.forgotintransport.data.authorization.Token
import com.miitdiplomasoft.forgotintransport.databinding.FragmentMenuBinding
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import com.miitdiplomasoft.forgotintransport.ui.adapters.MenuGridItemsAdapter
import com.miitdiplomasoft.forgotintransport.viewmodels.MenuFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val menuFragmentViewModel: MenuFragmentViewModel by viewModels()

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.thingsGridRecycler.layoutManager = GridLayoutManager(binding.root.context, 3)

        menuFragmentViewModel.items.observe(viewLifecycleOwner) { items ->

            when (items) {
                ArrayList<MenuItemResponse>() -> {
                    binding.thingsGridRecycler.adapter = MenuGridItemsAdapter(items)
                }
                else -> {
                    Toast.makeText(context, "Повторите попытку позже", Toast.LENGTH_SHORT).show()
                }
            }
        }
        menuFragmentViewModel.getAllItems(Token.getAuthorizationToken(requireContext())!!)

        binding.bottomNavigation.selectedItemId = R.id.navigation_menu_admin
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_menu_admin -> {
                    true
                }
                R.id.navigation_favorites -> {
                    findNavController().navigate(R.id.action_menuFragment_to_favoritesFragment)
                    true
                }
                R.id.navigation_search_admin -> {
                    findNavController().navigate(R.id.action_menuFragment_to_searchFragment)
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
