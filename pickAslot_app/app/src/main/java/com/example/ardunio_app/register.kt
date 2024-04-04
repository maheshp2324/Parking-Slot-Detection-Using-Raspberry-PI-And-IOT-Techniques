package com.example.ardunio_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ardunio_app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class register : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val name = binding.edname
        val number = binding.ednumber
        val email = binding.edemail
        val password = binding.edpassword
        val btn = binding.btnregister

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("register")

        btn.setOnClickListener {

            val mail = email.text.toString()

            if(name.text.isEmpty())
            {
                name.setError("Enter name")
                return@setOnClickListener
            }else if(password.text.isEmpty())
            {
                password.setError("Enter Password ")
                return@setOnClickListener
            }else if(number.text.length>10)
            {
                number.setError("Enter 10 Digit Number")
                return@setOnClickListener
            }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())
            {
                email.setError("Enter Email id")
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        val currentuser = auth.currentUser
                        val currentUserdb = databaseReference?.child((currentuser?.uid!!))
                        currentUserdb?.child("name")?.setValue(name.text.toString())


                        currentUserdb?.child("number")?.setValue(number.text.toString())

                        Toast.makeText(applicationContext,"Registration Successfully", Toast.LENGTH_LONG).show()

                    }
                    else
                    {
                        Toast.makeText(applicationContext,"failed", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}