package com.example.contactapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.ItemRvBinding
import com.example.contactapp.models.MyContact

class RvAdapter(var rvAction: RvAction, var list: ArrayList<MyContact>) : RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(myContact: MyContact, position: Int) {
            itemRv.tvName.text = myContact.name
            itemRv.tvPhone.text = myContact.phone
            itemRv.btnMore.setOnClickListener {
                rvAction.moreClick(myContact, position, itemRv.btnMore)
            }
            itemRv.root.setOnClickListener {
                rvAction.itemClick(myContact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RvAdapter.Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction {
        fun moreClick(myContact: MyContact, position: Int, imageView: ImageView)
        fun itemClick(myContact: MyContact)
    }
}

