package com.miitdiplomasoft.forgotintransport.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miitdiplomasoft.forgotintransport.R
import com.miitdiplomasoft.forgotintransport.databinding.ItemCardBinding
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import com.miitdiplomasoft.forgotintransport.ui.fragments.AdminMenuFragmentDirections
import com.miitdiplomasoft.forgotintransport.ui.fragments.MenuFragmentDirections

class AdminMenuGridItemsAdapter(
    private val items: ArrayList<MenuItemResponse>
) : RecyclerView.Adapter<AdminMenuGridItemsAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItemResponse: MenuItemResponse) {
            Glide.with(binding.root)
                .load(menuItemResponse.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.itemCardImg)
            with(binding) {
                itemName.text = menuItemResponse.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemCardBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(AdminMenuFragmentDirections.actionAdminMenuFragmentToAdminCardFragment(item.id!!))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
