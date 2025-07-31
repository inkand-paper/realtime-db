package com.example.realtimedb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimedb.databinding.ItemListBinding

class UserAdapter(
    private val userList: List<User>,
    private val onEditClick: (User) -> Unit,
    private val onDeleteClick: (User) -> Unit
    ) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.NameTV.text = user.name
        holder.binding.EmailTV.text = user.email
        holder.binding.EditBtn.setOnClickListener {
            onEditClick(user)
        }
        holder.binding.DeleteBtn.setOnClickListener {
            onDeleteClick(user)
        }
    }

    override fun getItemCount(): Int = userList.size
}