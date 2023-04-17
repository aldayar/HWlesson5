package com.example.hwlesson5

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class AddFragment : Fragment() {
    private val GALLERY_REQUEST_CODE = 1
    private lateinit var addButton: MaterialButton
    private lateinit var editTitle: EditText
    private lateinit var editDesc: EditText
    private lateinit var editDate: EditText
    private lateinit var cardView: CardView
    private lateinit var imageview: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView=view.findViewById(R.id.cardview)
        editTitle=view.findViewById(R.id.editTitle)
        editDesc=view.findViewById(R.id.editDesc)
        editDate=view.findViewById(R.id.editDate)
        addButton=view.findViewById(R.id.add_btn)

        if (arguments!=null) {
            val note = arguments?.getSerializable("edit") as NoteModel
            editDate.setText(note.title)
            editDesc.setText(note.desc)
            editDate.setText(note.date)

            addButton.setOnClickListener {
                val newNote = NoteModel ("${imageview}","${editTitle.text}",
                    "${editDesc.text}","${editDate.text}")

                if (editDesc.text.toString().isEmpty() || editDate.text.toString().isEmpty() || editTitle.text.toString().isEmpty()){
                    Toast.makeText(requireContext().applicationContext,"The note mustn't be empty. Pplease fill and try again!",
                        Toast.LENGTH_SHORT).show()
                }else {
                    (requireActivity() as MainActivity).list.add(newNote)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MainFragment()).commit()
                }
            }
        }

        if(arguments != null) {
            val textTitle = arguments?.getString("key")
            editTitle.setText(textTitle)
        }

        cardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageview = data!!.data as ImageView
            cardView.setBackground(Drawable.createFromPath(imageview.toString()))

        }
    }

    }

