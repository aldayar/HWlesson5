package com.example.hwlesson5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.hwlesson5.databinding.ItemNoteBinding

class NoteAdapter(private val listener: IOnItem) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    val list : MutableList<NoteModel> = ArrayList()
    fun setList(list: MutableList<NoteModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addNote(note: NoteModel) {
        list.add(note)
        notifyDataSetChanged()
    }
    fun edit(pos: Int, note: NoteModel) {
        list.set(pos,note)
        notifyItemChanged(pos)
    }


    fun delete(pos: Int) {
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun getItem(pos: Int): NoteModel {
        return list[pos]
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate( LayoutInflater.from(parent.context), parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(val item: ItemNoteBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(position: Int) {
            item.itemTitle.text = list[position].title
            item.itemDesc.text = list[position].desc
            Glide.with(itemView)
                .load(list[position].image)
                .transform(CenterCrop(), RoundedCorners(25))
                .into(item.itemImage)

            item.delete.setOnClickListener {
                listener.delete(adapterPosition) }

            item.editNote.setOnClickListener {
                listener.edit(adapterPosition, list[position]) }

            item.share.setOnClickListener{
                listener.share(adapterPosition)}
        }
    }

    interface IOnItem {
        fun delete(pos: Int)
        fun edit(pos: Int, note: NoteModel)
        fun share(pos: Int)
    }
}
