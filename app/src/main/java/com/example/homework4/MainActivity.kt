package com.example.homework4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.widget.Toast
import com.example.homework4.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val root = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.ORDERS_PATH)
    private val adapter = OrderRecyclerAdapter(::deleteOrder)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ordersRecyclerView.adapter = adapter
        addListener()
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddOrderActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addListener() {
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orders = snapshot.children.map {
                    it.getValue(Order::class.java)!!
                }
                adapter.setData(orders)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteOrder(orderNumber: String) {
        root.child(orderNumber).removeValue()
    }

}