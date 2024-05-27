package com.example.loginfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class sigin : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sigin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val intent = Intent(this, MainActivity::class.java)

        var et_siginid : EditText = findViewById(R.id.et_siginid)
        var et_siginpw : EditText = findViewById(R.id.et_siginpw)
        var btn_sigin : Button = findViewById(R.id.btn_sigin)
        var btn_suchId : Button = findViewById(R.id.btn_suchId)


        btn_suchId.setOnClickListener {
            val email = et_siginid.text.toString()

            if(email.isNotEmpty()) {
                db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
                        var CKemail = task.result

                        if(CKemail != null && !CKemail.isEmpty) {
                            Toast.makeText(this, "이미 등록된 아이디가 있습니다.", Toast.LENGTH_SHORT).show()
                            btn_sigin.isEnabled = false
                        }
                        else {
                            Toast.makeText(this, "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                            btn_sigin.isEnabled = true
                        }
                    }
                    else {
                        Toast.makeText(this, "등신아 또 코드 오류냐 ㅅㅂ.", Toast.LENGTH_SHORT).show()
                        btn_sigin.isEnabled = false
                    }
                }
            }
        }

            btn_sigin.setOnClickListener {
                val email = et_siginid.text.toString()
                val pw = et_siginpw.text.toString()

                auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let {
                            val uid = user.uid
                            val userMap = hashMapOf(
                                "email" to email,
                                "pw" to pw
                            )
                            db.collection("users").document(uid).set(userMap).addOnSuccessListener {
                                Toast.makeText(this, "파이어 스토어 회원가입 성공", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }

            }

    }
}