package com.sipmat.sipmat.adapter.ffblok2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model


class HasilFFblok2Adapter(
    private val notesList: MutableList<FFBlok2Model>,
    private val context: Context,

    ) : RecyclerView.Adapter<HasilFFblok2Adapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : FFBlok2Model)
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
            tgl = view.findViewById(R.id.txtjadwalcek)
            tw = view.findViewById(R.id.txttw)
            tahun = view.findViewById(R.id.txttahun)
            status = view.findViewById(R.id.txtstatus)
            jadwal = view.findViewById(R.id.txtjadwalcek)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_ffblok2_pelaksana, parent, false)

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
