package com.sipmat.sipmat.admin.apar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel

class AparAdapter(
    private val notesList: MutableList<AparModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<AparAdapter.ViewHolder>() {
    var lista = mutableListOf<AparModel>()

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        internal var cb: CheckBox = view.findViewById(R.id.cb)
        lateinit var myClick: ItemClickListener
        init {
            cb.setOnClickListener(this)
        }
        fun setItemClickListener(ic: ItemClickListener) {
            this.myClick = ic
        }

        override fun onClick(v: View) {
            this.myClick.onItemClick(v, layoutPosition)
        }

        interface ItemClickListener {
            fun onItemClick(v: View, pos: Int)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_check_box, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.cb.text = note.kode
        holder.setItemClickListener(object : ViewHolder.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val cb = v as CheckBox
                val currentS = notesList[pos]
                if (cb.isChecked) {
                    currentS.isSelected = true
                    lista.add(currentS)
                } else if (!cb.isChecked) {
                    currentS.isSelected = false
                    lista.remove(currentS)
                }
            }
        })
    }
}