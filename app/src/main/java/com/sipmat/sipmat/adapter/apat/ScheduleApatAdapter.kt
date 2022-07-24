package com.sipmat.sipmat.adapter.apat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.ScheduleModel
import com.sipmat.sipmat.model.UsersModel
import com.sipmat.sipmat.model.apat.ScheduleApatModel

class ScheduleApatAdapter(
    private val notesList: MutableList<ScheduleApatModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<ScheduleApatAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : ScheduleApatModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var kode: TextView
        internal var lokasi: TextView
        internal var tanggal: TextView
        internal var status: TextView


        init {
            kode = view.findViewById(R.id.txtkode)
            lokasi = view.findViewById(R.id.txtlokasi)
            tanggal = view.findViewById(R.id.txttglcek)
            status = view.findViewById(R.id.txtstatus)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_scheduleapat, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.kode.text = "Kode : ${note.kodeApat}"
        holder.lokasi.text = "Lokasi : ${note.lokasi}"
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
        holder.tanggal.text = "Tanggal : ${note.tanggalCek}"

        holder.itemView.setOnClickListener {
            if (dialog!=null){
                dialog!!.onClick(position,note)
            }
        }
    }

}
