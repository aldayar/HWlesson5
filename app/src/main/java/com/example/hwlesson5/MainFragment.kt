package com.example.hwlesson5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hwlesson5.databinding.FragmentMainBinding

class MainFragment : Fragment(),NoteAdapter.IOnItem{

    private lateinit var adapter:NoteAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = NoteAdapter(this)
        binding.recycler.adapter = adapter

        adapter.setList((requireActivity() as MainActivity).list)

        binding.add.setOnClickListener{
            val addFragment = AddFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, addFragment).commit()
        }
        binding.sortBtn.setOnClickListener{
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Sort")
            alertDialog.setMessage("You want sort by date or title?")
            alertDialog.setPositiveButton("Date") { _: DialogInterface?, _: Int ->
            }
            alertDialog.setNegativeButton("Title"){_:DialogInterface?,_: Int ->

            }
        }
        val pos = arguments?.getInt("position",)
        val note = arguments?.getSerializable("key") as NoteModel
        adapter.edit(pos!!, note)

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
        bundle.putInt("position",pos)
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
        binding = FragmentMainBinding.inflate(inflater, container , false)
        return binding.root
    }
}