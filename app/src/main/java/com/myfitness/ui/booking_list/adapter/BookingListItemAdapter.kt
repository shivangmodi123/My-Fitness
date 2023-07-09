package com.myfitness.ui.booking_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myfitness.databinding.ItemBookingListBinding
import com.myfitness.ui.booking_list.model.User


class BookingListItemAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<BookingListItemAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemBookingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(private val binding: ItemBookingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.tvUserName.text = user.name.first + " " + user.name.last
            binding.tvBirthDate.text = user.dob.date
            binding.tvAddress.text = user.location.city + ", " + user.location.state

            Glide.with(binding.ivUserImage.context)
                .load(user.picture.medium)
                .into(binding.ivUserImage)
        }
    }
}