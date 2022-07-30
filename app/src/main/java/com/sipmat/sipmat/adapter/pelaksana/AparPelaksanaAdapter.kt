package com.sipmat.sipmat.adapter.pelaksana

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.ScheduleAparPelaksanaModel
import com.sipmat.sipmat.model.ScheduleModel
import com.sipmat.sipmat.model.UsersModel

class AparPelaksanaAdapter(
    private val notesList: MutableList<ScheduleAparPelaksanaModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<AparPelaksanaAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : ScheduleAparPelaksanaModel)
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
        lateinit var jenis : TextView

        init {
            jenis = view.findViewById(R.id.txtjenis)
            kode = view.findViewById(R.id.txtkode)
            gedung = view.findViewById(R.id.txtgedung)
            status = view.findViewById(R.id.txtstatus)
            jadwal = view.findViewById(R.id.txtjadwalcek)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_aparpelaksana, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.jenis.text = "Jenis APAR : ${note.apar!!.jenis}"
        holder.kode.text = "Kode : ${note.kodeApar}"
        holder.gedung.text = "Lokasi Penempatan : ${note.apar!!.lokasi}"
        if (note.isStatus ==0){
            holder.status.text = "Status : Belum di cek"
        }else if (note.isStatus ==2){
            holder.status.text = "Status : Sudah di cek"
        }
        else if(note.isStatus ==1){
            holder.status.text = "Status : Sedang Proses "
        }else{
            holder.status.text = "Status : Di return "
        }
        holder.jadwal.text = "Jadwal : ${note.tanggalCek}"

        holder.itemView.setOnClickListener {
            if (dialog!=null){
                dialog!!.onClick(position,note)
            }
        }
    }

}
