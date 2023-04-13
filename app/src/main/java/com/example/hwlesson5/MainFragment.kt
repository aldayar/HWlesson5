package com.example.hwlesson5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment(),NoteAdapter.IOnItem{
    private lateinit var recyclerView: RecyclerView
    private lateinit var addBtn: Button
    private lateinit var sortBtn: Button
    private lateinit var inputtedText: String
    private lateinit var mainEditText: EditText
    private lateinit var adapter:NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBtn=view.findViewById(R.id.add)
        sortBtn=view.findViewById(R.id.sort_btn)
        mainEditText=view.findViewById(R.id.search_edit)
        recyclerView=view.findViewById(R.id.recycler)

        adapter = NoteAdapter(this)
        recyclerView.adapter = adapter

        addBtn.setOnClickListener{
            val addFragment = AddFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_container, addFragment).commit()
        }
        sortBtn.setOnClickListener{
            inputtedText=mainEditText.text.toString()
            val bundle = Bundle()
            bundle.putString("key", inputtedText)
            val addFragment1 = AddFragment()
            addFragment1.arguments = bundle

            val addFragment2 = AddFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_container, addFragment2).commit()
        }
          val list :List<Note> = arrayListOf(
          Note("", "title2", ":description2", ""),
          Note("", "title3", ":", ""),
          Note("", "title4", ":descrip", ""),
          Note("", "title5", ":description2 fg fdsg fdg ", ""))
          adapter.setList(list)
    }

    override fun delete(pos: Int) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Warnig")
        alertDialog.setMessage("Are sure to delete the note?")
        alertDialog.setPositiveButton("Delete") { dialogInterface: DialogInterface?, i: Int ->
            adapter.delete(pos)
        }
        alertDialog.setNegativeButton("Cancel",null).show()
    }

    override fun edit(pos: Int, note: Note) {
        requireActivity().supportFragmentManager
            .beginTransaction().replace(R.id.main_container, AddFragment()).commit()


    }
    override fun share(pos: Int) {



        
    }

}