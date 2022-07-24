package com.sipmat.sipmat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.UsersModel

class AparAdapter(
    private val notesList: MutableList<AparModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<AparAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : AparModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var kode: TextView
        internal var gedung: TextView
        internal var jenis: TextView
        internal var kadaluarsa: TextView


        init {
            kode = view.findViewById(R.id.txtkode)
            gedung = view.findViewById(R.id.txtgedung)
            jenis = view.findViewById(R.id.txtjenis)
            kadaluarsa = view.findViewById(R.id.txtkadaluarsa)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_apar, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.kode.text = "Kode : ${note.kode}"
        holder.gedung.text = "Gedung : ${note.lokasi}"
        holder.jenis.text = "Jenis : ${note.jenis}"
        holder.kadaluarsa.text = "Kadaluarsa : ${note.tglKadaluarsa}"
        holder.itemView.setOnClickListener {
            if(dialog != null){
                dialog!!.onClick(holder.layoutPosition,note)
            }
        }
    }

}
