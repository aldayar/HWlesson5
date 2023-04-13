package com.example.hwlesson5

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class AddFragment : Fragment() {
    private val GALLERY_REQUEST_CODE = 1
    private lateinit var textTitle: String
    private lateinit var addButton: MaterialButton
    private lateinit var editTitle: EditText
    private lateinit var cardView: CardView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView=view.findViewById(R.id.cardview)
        editTitle=view.findViewById(R.id.editTitle)
        addButton=view.findViewById(R.id.add_btn)


        addButton.setOnClickListener{

        }



        val bundle = arguments
        textTitle = bundle!!.getString("key")!!
        editTitle.setText(textTitle)

        cardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageview = data!!.data
            cardView.setBackground(Drawable.createFromPath(imageview.toString()))

        }
    }


 }
