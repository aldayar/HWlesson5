package com.example.hwlesson5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

  class MainActivity  : AppCompatActivity()  {
      val list: MutableList<NoteModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .addToBackStack(null)
            .commit()
    }
}