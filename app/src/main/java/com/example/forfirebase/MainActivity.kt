package com.example.forfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.forfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef: DatabaseReference = database.getReference("tree")
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        readOnce()

//        myRef.addValueEventListener( object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // 먼저 textList를 지운다
//                with(binding) {
//                    textList.setText("")
//                    for (item in snapshot.children) {
//                        item.getValue(User::class.java)?.let { user ->
//                            textList.append("${user.name} : ${user.password} \n")
//                        }
//                    }
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                print(error.message)
//            }
//        })
    }

    fun readData() {
        myRef.child("name").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("파이어베이스", "${snapshot.value}")
                print(snapshot.value) // 값이 변경 될때마다 매번 호출
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })
    }

    fun readOnce() {
        myRef.get().addOnSuccessListener {
            Log.d("파이어베이스", "name=${it.value}") // 한 번 호출
        }.addOnFailureListener {
            Log.d("파이어베이스", "error=${it}")
        }
    }
}

class User {
    var id:String = ""
    var name:String = ""
    var password:String = ""

    constructor() // 파이어베이스에서 데이터 변환을 위해서 필요

    constructor(name:String, password:String) {
        this.name = name
        this.password = password
    }
}