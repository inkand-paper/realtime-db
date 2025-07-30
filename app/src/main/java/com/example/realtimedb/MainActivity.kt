package com.example.realtimedb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realtimedb.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private var userList = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("USers")

        binding.AddBtn.setOnClickListener {
            val name = binding.NameET.text.toString()
            val email = binding.AddressET.text.toString()
            val userId = database.push().key!!

            val user = User(userId,name, email)
            database.child(userId).setValue(user).addOnSuccessListener {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                binding.NameET.text.clear()
                binding.AddressET.text.clear()
            }
        }

        database.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children){
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                binding.RecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.RecyclerView.adapter = UserAdapter(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        }

        )

    }
}