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

class databaseActivity : AppCompatActivity() {

    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val button = findViewById<Button>(R.id.button5)
        dbrw = MyDBHelper(this).writableDatabase

        adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView1).adapter = adapter

        intent?.extras?.let {

            val value = it.getString("Key")
            val value1 = it.getString("Key1")
            val cv = ContentValues()
            cv.put("gmail", value)
            cv.put("password", value1)
            dbrw.insert("myTable",null,cv)
            setListener()
            button.setOnClickListener{


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        dbrw.close()
        super.onDestroy()
    }

    private  fun  setListener() {

        val vt_name = findViewById<EditText>(R.id.vt_name1)
        val vt_price = findViewById<EditText>(R.id.vt_price2)


        findViewById<Button>(R.id.button7).setOnClickListener {

            if (vt_name.length() < 1 || vt_price.length() < 1)
                showToast("欄位請勿留空")
            else
                try {

                    dbrw.execSQL("UPDATE myTable SET password = ${vt_price.text}WHERE gmail LIKE '${vt_name.text}'")
                    showToast("更新:${vt_name.text},價格:${vt_price.text}")
                    cleanEditText()

                } catch (e: Exception) {
                    showToast("更新失敗: $e")

                }
        }
        findViewById<Button>(R.id.button8).setOnClickListener {

            if (vt_name.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    dbrw.execSQL("DELETE FROM myTable WHERE gmail LIKE '${vt_name.text}'")
                    showToast("刪除:${vt_name.text}")
                    cleanEditText()

                } catch (e: Exception) {
                    showToast("刪除失敗: $e")

                }
        }

        findViewById<Button>(R.id.button9).setOnClickListener {

            val queryString = if (vt_name.length() < 1)
                "SELECT * FROM myTable"
            else
                "SELECT * FROM myTable WHERE gmail LIKE '${vt_name.text}'"

            val c = dbrw.rawQuery(queryString, null)
            c.moveToFirst()
            items.clear()
            showToast("共有${c.count}筆資料")
            for (i in 0 until c.count) {

                items.add("帳號:${c.getString(0)}\t\t\t\t 密碼:${c.getString(1)}")
                c.moveToNext()
            }
            adapter.notifyDataSetChanged()
            c.close()
        }

    }
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()

    private fun  cleanEditText() {

        findViewById<EditText>(R.id.vt_name1).setText("")
        findViewById<EditText>(R.id.vt_price2).setText("")
    }

}