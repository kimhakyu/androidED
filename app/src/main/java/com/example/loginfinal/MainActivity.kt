package com.example.loginfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var et_initid : EditText = findViewById(R.id.et_initid)
        var et_initpw : EditText = findViewById(R.id.et_initpw)
        var btn_login : Button = findViewById(R.id.btn_login)
        var btn_sigin : Button = findViewById(R.id.btn_sigin)

        auth = FirebaseAuth.getInstance()

        val intent = Intent(this, subActivity::class.java)
        val subintent = Intent(this, sigin::class.java)
        btn_login.setOnClickListener {
            val email = et_initid.text.toString()
            val pw = et_initpw.text.toString()

            auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btn_sigin.setOnClickListener {
            startActivity(subintent)
        }
    }
}