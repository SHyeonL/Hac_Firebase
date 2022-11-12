package com.example.forfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.forfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    var lng = "NULL"
    var lat = "NULL"
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("tree")

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            btnPost.setOnClickListener {
                readXY()
//                val name = editName.text.toString()
//                val password = editPassword.text.toString()
//                //val lng = myRef.child("777").get()
//                Log.d("파이어베이스: POST버튼 누름", lng)
//                readXY()
//                Log.d("파이어베이스: readXY() 실행완료", lng)
//                val user = User(name, password, lng, lat)
//                Log.d("파이어베이스: user 개체 생성", lng)
//                addItem(user)
                Log.d("파이어베이스: addItem", lng)
            }
            btnTest.setOnClickListener {
                readOnce()
            }
        }

//        myRef.addValueEventListener( object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // 먼저 textList를 지운다
//                with(binding) {
//                    textList.setText("")
//                    for (item in snapshot.children) {
//                        item.getValue(User::class.java)?.let { user ->
//                            textList.append("${user.id} / ${user.lat} / ${user.lng}\n")
//                            textList.append("${user.tree_name} : ${user.user_name} \n")
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

        // write()
        readOnce()
        // readData()
    }
    // 목록 형태로 추가하기
    fun addItem(user:User) {
        val id = "0"
        user.id = "0"
        myRef.child(id).setValue(user)
    }
    // 계속 읽기
    fun readData() {
        myRef.child("name").addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("파이어베이스", "${snapshot.value}")
                print( snapshot.value ) // 값이 변경 될때마다 매번 호출
            }
            override fun onCancelled(error: DatabaseError) {
                print( error.message )
            }
        })
    }
    // 한번 읽기
    fun readOnce() {
        myRef.child("777").get().addOnSuccessListener {
            Log.d("파이어베이스", "name=${it.child("lng").value}") // 한 번 호출
        }.addOnFailureListener {
            Log.d("파이어베이스", "error=${it}")
        }
    }

    fun readXY() {
        myRef.child("0").get().addOnSuccessListener {
            lng = it.child("lng").value.toString()
            Log.d("파이어베이스", lng) // 한 번 호출
            lat = it.child("lat").value.toString()
            Log.d("파이어베이스", lat) // 한 번 호출
            val name = binding.editName.text.toString()
            val password = binding.editPassword.text.toString()
            //val lng = myRef.child("777").get()
//            Log.d("파이어베이스: POST버튼 누름", lng)
//            Log.d("파이어베이스: readXY() 실행완료", lng)
            val user = User(name, password, lng, lat)
            Log.d("파이어베이스: user 개체 생성", lng)
            addItem(user)
            Log.d("파이어베이스: addItem", lng)
        }.addOnFailureListener {
            Log.d("파이어베이스", "error=${it}")
        }
    }
    // 쓰기
    fun write() {
        myRef.child("name").setValue("Scott")
        myRef.child("age").setValue(19)
    }
}


class User {
    var id:String = ""
    var lat:String = ""
    var lng:String = ""
    var tree_name:String = ""
    var user_name:String = ""

    constructor() // 파이어베이스에서 데이터 변환을 위해서 필요

    constructor(tree_name:String, user_name:String, lng:String, lat:String) {
        this.tree_name = tree_name
        this.user_name = user_name
        this.lng = lng
        this.lat = lat
    }
}