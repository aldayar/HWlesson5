package com.example.hwlesson5

import android.app.AlertDialog
import android.app.Notification.Action
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment(),NoteAdapter.IOnItem{

    private lateinit var addBtn: Button
    private lateinit var sortBtn: Button
    private lateinit var mainEditText: EditText
    private lateinit var adapter:NoteAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBtn=view.findViewById(R.id.add)
        sortBtn=view.findViewById(R.id.sort_btn)
        mainEditText=view.findViewById(R.id.search_edit)
        recyclerView=view.findViewById(R.id.recycler)



        adapter = NoteAdapter(this)
        recyclerView.adapter = adapter

        adapter.setList((requireActivity() as MainActivity).list)

        addBtn.setOnClickListener{
            val addFragment = AddFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, addFragment).commit()
        }
        sortBtn.setOnClickListener{
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Sort")
            alertDialog.setMessage("You want sort by date or title?")
            alertDialog.setPositiveButton("Date") { _: DialogInterface?, _: Int ->

            }
            alertDialog.setNegativeButton("Title"){_:DialogInterface?,_: Int ->

            }
        }

    }

    override fun delete(pos: Int) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Warnig")
        alertDialog.setMessage("Are sure to delete the note?")
        alertDialog.setPositiveButton("Delete") { _: DialogInterface?, _: Int ->
            (requireActivity() as MainActivity).list.removeAt(pos)
            adapter.delete(pos)

        }
        alertDialog.setNegativeButton("Cancel",null).show()
    }

    override fun edit(pos: Int, note: NoteModel) {
        val addFragment = Fragment()
        val bundle = Bundle()
        bundle.putSerializable("edit",note)
        addFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,addFragment).commit()
    }
    override fun share(pos: Int) {
        val intent = Intent()
        intent.action =Intent.ACTION_SEND
        intent.putExtra("Look! This is my note!",Intent.EXTRA_TEXT)
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent,"Share with note by:"))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_main, container, false)
    }
}