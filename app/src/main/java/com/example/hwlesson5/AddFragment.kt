package com.example.hwlesson5

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hwlesson5.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private val GALLERY_REQUEST_CODE = 1
    private lateinit var imageView: ImageView

    private lateinit var binding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null) {
            val note = arguments?.getSerializable("edit") as NoteModel
            val pos = arguments?.getSerializable("position")
            binding.editTitle .setText(note.title)
            binding.editDesc.setText(note.desc)
            binding.editDate.setText(note.date)

            binding.addBtn.setOnClickListener {
                val newNote = NoteModel ("${imageView}","${binding.editTitle.text}",
                    "${binding.editDesc.text}","${binding.editDate.text}")
                if (binding.editDesc.text.toString().isEmpty()) {
                    binding.editDesc.error = "The field mustn't be empty"
                    if (binding.editTitle.text.toString().isEmpty())
                        binding.editTitle.error = "The field mustn't be empty"
                    if (binding.editDate.text.toString().isEmpty()) {
                        binding.editDate.error = "The field mustn't be empty"

                    } else {
                        Toast.makeText(context?.applicationContext ,"The note successfully added",Toast.LENGTH_SHORT).show()
                        (requireActivity() as MainActivity).list.add(newNote)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, MainFragment()).commit()
                    }
                }
            }
        }

        if(arguments != null) {
            val textTitle = arguments?.getString("key")
            binding.editTitle.setText(textTitle)
        }

        binding.cardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
        binding.editDesc.addTextChangedListener(textWatcher)
        binding.editTitle.addTextChangedListener(textWatcher)
        binding.editDate.addTextChangedListener(textWatcher)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageView = data!!.data as ImageView
            binding.cardView.setBackground(Drawable.createFromPath(imageView.toString()))

        }
    }
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            val inputtedTitle = binding.editTitle.text.toString().trim()
            val inputtedDesc = binding.editDesc.text.toString().trim()
            val inputtedDate = binding.editDate.text.toString().trim()
            binding.addBtn.isEnabled = (!inputtedTitle.isEmpty() &&
                    !inputtedDesc.isEmpty() && !inputtedDate.isEmpty())
        }
        override fun afterTextChanged(editable: Editable) {}
    }


}

