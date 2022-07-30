package com.sipmat.sipmat.adapter.apar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel

class AparKadaluarsaAdapter(
    private val notesList: MutableList<AparModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<AparKadaluarsaAdapter.ViewHolder>() {

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
        internal var gedung: TextView
        internal var jenis: TextView
        internal var kadaluarsa: TextView
        internal var kode: TextView


        init {
            gedung = view.findViewById(R.id.txtgedung)
            jenis = view.findViewById(R.id.txtjenis)
            kadaluarsa = view.findViewById(R.id.txtkadaluarsa)
            kode = view.findViewById(R.id.txtkode)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_apar_kadaluarsa, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.gedung.text = "Lokasi Penempatan : ${note.lokasi}"
        holder.jenis.text = "Jenis : ${note.jenis}"
        holder.kadaluarsa.text = "Tanggal Kadaluarsa : ${note.tglKadaluarsa}"
        holder.kode.text = "Kode Apar : ${note.kode}"
        holder.itemView.setOnClickListener {
            if(dialog != null){
                dialog!!.onClick(holder.layoutPosition,note)
            }
        }
    }

}
