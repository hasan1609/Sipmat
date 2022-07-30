package com.sipmat.sipmat.adapter.hydrant

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
import com.sipmat.sipmat.model.apat.HasilApatModel
import com.sipmat.sipmat.model.hydrant.HasilHydrantModel
import com.sipmat.sipmat.model.hydrant.ScheduleHydrantPelaksanaModel

class HasilHydrantAdapter(
    private val notesList: MutableList<ScheduleHydrantPelaksanaModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<HasilHydrantAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : ScheduleHydrantPelaksanaModel)
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
        internal var status: TextView
        internal var jadwal: TextView
        internal var no: TextView

        init {
            no = view.findViewById(R.id.txtnobak)
            kode = view.findViewById(R.id.txtkode)
            gedung = view.findViewById(R.id.txtlokasi)
            status = view.findViewById(R.id.txtstatus)
            jadwal = view.findViewById(R.id.txttglcek)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_hasil_hydrant, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.kode.text = "Kode Hydrant  : ${note.kodeHydrant}"
        holder.gedung.text = "Lokasi Penempatan : ${note.hydrant!!.lokasi}"
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
        holder.jadwal.text = "Jadwal : ${note.tanggalCek}"
        holder.no.text ="No. Box Hydrant : ${note.hydrant.noBox}"
        holder.itemView.setOnClickListener {
            if (dialog!=null){
                dialog!!.onClick(position,note)
            }
        }
    }

}
