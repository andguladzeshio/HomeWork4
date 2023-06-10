package com.example.homework4

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework4.databinding.OrderItemBinding

class OrderRecyclerAdapter(private val onDeleteOrder: (String)-> Unit):
    RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder>() {
    private var orders = listOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.onBind(orders[position], onDeleteOrder)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    class OrderViewHolder(private val binding: OrderItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(order: Order, onDeleteOrder: (String) -> Unit) {
            binding.addressTextView.text = order.address
            binding.mobileNumberTextView.text = order.mobileNumber
            binding.orderNumberTextView.text = order.orderNumber

            binding.deleteButton.setOnClickListener { onDeleteOrder(order.orderNumber) }
            binding.callButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${order.mobileNumber}")
                binding.root.context.startActivity(intent)
            }
        }

    }
}