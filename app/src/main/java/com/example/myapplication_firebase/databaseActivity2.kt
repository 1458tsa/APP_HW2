package com.example.myapplication_firebase


import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class databaseActivity2 : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw1: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database2)

        val button = findViewById<Button>(R.id.button80)
        dbrw1 = MyDBHelper1(this).writableDatabase

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, items
        )
        findViewById<ListView>(R.id.listView3).adapter = adapter
        setListener()
    }

        private fun setListener() {
            intent?.extras?.let {

                val value2 = it.getString("Key4")
                val value3 = it.getString("Key5")
                val value4 = it.getString("Key6")
                val value5 = it.getString("Key7")
                val cv1 = ContentValues()
                cv1.put("name", value2)
                cv1.put("email", value3)
                cv1.put("date", value4)
                cv1.put("people", value5)
                dbrw1.insert("myTable1", null, cv1)
                val editText87 = findViewById<EditText>(R.id.EditText87)

                findViewById<Button>(R.id.button84).setOnClickListener {

                    val queryString = if (editText87.length() < 1)
                        "SELECT * FROM myTable1"
                    else
                        "SELECT * FROM myTable1 WHERE email LIKE '${editText87.text}'"

                    val c = dbrw1.rawQuery(queryString, null)
                    c.moveToFirst()
                    items.clear()
                    showToast1("共有${1}筆資料")
                    for (i in 0 until 1) {

                        items.add(
                            "名字:${c.getString(0)}\t\t\t Email:${c.getString(1)}\t\t\t date:${c.getString(2)}\t\t\t people:${c.getString(3)}"
                        )

                    }
                    adapter.notifyDataSetChanged()
                    c.close()
                }
                val Button80 = findViewById<Button>(R.id.button80)
                Button80.setOnClickListener {

                    val intent = Intent(this, foodMainActivity::class.java)
                    startActivity(intent)
                }
            }
        }


    override fun onDestroy() {
        dbrw1.close()
        super.onDestroy()
    }
    private fun showToast1(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
}


