package com.sipmat.sipmat.adapter.edgblok3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Model
import com.sipmat.sipmat.model.ffblok.FFBlokModel

class Edgblok3PelaksanaAdapter(
    private val notesList: MutableList<EdgBlok3Model>,
    private val context: Context,

    ) : RecyclerView.Adapter<Edgblok3PelaksanaAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : EdgBlok3Model)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var tw: TextView
        internal var tahun: TextView
        internal var hari: TextView
        internal var status: TextView
        internal var tgl: TextView

        init {
            tw = view.findViewById(R.id.txttw)
            tahun = view.findViewById(R.id.txttahun)
            hari = view.findViewById(R.id.txthari)
            status = view.findViewById(R.id.txtstatus)
            tgl = view.findViewById(R.id.txttanggal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_scheduleedgblok3, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        if (note.isStatus ==0){
            holder.status.text = "Status : Belum di cek"
        }else if (note.isStatus ==2){
            holder.status.text = "Status : Sudah di cek"
        }
        else if (note.isStatus ==1){
            holder.status.text = "Status : proses pengecekan"
        }
        else{
            holder.status.text = "Status : di return "
        }
        holder.tw.text = "TW : ${note.tw}"
        holder.tahun.text = "Tahun : ${note.tahun}"
        holder.hari.text = "Hari : ${note.hari}"
        holder.tgl.text = "Tanggal Pengecekan : ${note.tanggalCek}"

        holder.itemView.setOnClickListener {
            if (dialog!=null){
                dialog!!.onClick(position,note)
            }
        }

    }

}
