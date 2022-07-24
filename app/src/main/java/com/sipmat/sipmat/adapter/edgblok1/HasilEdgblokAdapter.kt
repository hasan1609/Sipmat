package com.sipmat.sipmat.adapter.edgblok1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.ffblok.FFBlokModel


class HasilEdgblokAdapter(
    private val notesList: MutableList<EdgBlokModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<HasilEdgblokAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : EdgBlokModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var status: TextView
        internal var jadwal: TextView
        internal var tw: TextView
        internal var tahun: TextView
        internal var tgl: TextView
        init {
            tgl = view.findViewById(R.id.txttanggal)
            tw = view.findViewById(R.id.txttw)
            tahun = view.findViewById(R.id.txttahun)
            status = view.findViewById(R.id.txtstatus)
            jadwal = view.findViewById(R.id.txtjadwalcek)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_edgblok1_pelaksana, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        if (note.isStatus ==1){
            holder.status.text = "Status : Silahkan Approve"
        }else if (note.isStatus ==2){
            holder.status.text = "Status : Selesai"
        }else if (note.isStatus ==0){
            holder.status.text = "Status : belum di cek"
        }
        else if (note.isStatus ==3){
            holder.status.text = "Status : direturn"
        }
        holder.jadwal.text = "Hari  : ${note.hari}"
        holder.tgl.text = "Tanggal Pengecekan  : ${note.tanggalCek}"
        holder.tw.text = "TW  : ${note.tw}"
        holder.tahun.text = "Tahun  : ${note.tahun}"

        holder.itemView.setOnClickListener {
            if (dialog!=null){
                dialog!!.onClick(position,note)
            }
        }
    }

}
