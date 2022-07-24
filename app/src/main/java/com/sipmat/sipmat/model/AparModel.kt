package com.sipmat.sipmat.model

import com.google.gson.annotations.SerializedName

data class AparModel(

    @field:SerializedName("tgl_pengisian")
    val tglPengisian: String? = null,

    @field:SerializedName("tgl_kadaluarsa")
    val tglKadaluarsa: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("kode")
    val kode: String? = null,

    @field:SerializedName("jenis")
    val jenis: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
){
    var isSelected: Boolean = false
}
