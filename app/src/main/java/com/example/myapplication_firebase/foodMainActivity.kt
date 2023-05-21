package com.example.myapplication_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class foodMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_main)

        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)



        button7.setOnClickListener {

            val intent = Intent(this, MapMainActivity::class.java)


            startActivity(intent)
        }

        button8.setOnClickListener {

            val intent1 = Intent(this, bookingMainActivity::class.java)


            startActivity(intent1)
        }













    }


}