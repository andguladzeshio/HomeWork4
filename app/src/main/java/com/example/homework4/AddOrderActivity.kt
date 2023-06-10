package com.example.homework4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework4.databinding.ActivityAddOrderBinding
import com.google.firebase.database.FirebaseDatabase

class AddOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddOrderBinding
    private val root = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.ORDERS_PATH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addButton.setOnClickListener {
            addOrder(
                binding.orderAddressEditText.text.toString(),
                binding.mobileNumberEditText.text.toString(),
                binding.orderNumberEditText.text.toString()
            )
        }
    }

    private fun addOrder(address: String, mobileNumber: String, orderNumber: String) {
        if (address.isBlank() || mobileNumber.isBlank() || orderNumber.isBlank()) {
            Toast.makeText(this, "შეავსეთ ყველა ველი", Toast.LENGTH_SHORT).show()
            return
        }

        val order = Order(address, mobileNumber, orderNumber)
        root.child(orderNumber).setValue(order).addOnSuccessListener {
            finish()
        }
    }
}