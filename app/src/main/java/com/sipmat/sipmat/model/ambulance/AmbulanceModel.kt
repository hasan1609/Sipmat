package com.sipmat.sipmat.model.ambulance

import com.google.gson.annotations.SerializedName

data class AmbulanceModel (
    @field:SerializedName("hari")
    val hari: String? = null,

    @field:SerializedName("tisu_kering")
    val tisuKering: Any? = null,

    @field:SerializedName("tw")
    val tw: String? = null,

    @field:SerializedName("tanggal_pemeriksa")
    val tanggalPemeriksa: Any? = null,

    @field:SerializedName("shift")
    val shift: Any? = null,

    @field:SerializedName("kondisi_fisik")
    val kondisiFisik: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("tanggal_cek")
    val tanggalCek: String? = null,

    @field:SerializedName("apar")
    val apar: Any? = null,

    @field:SerializedName("roda")
    val roda: Any? = null,

    @field:SerializedName("is_status")
    val isStatus: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("spons_kasur")
    val sponsKasur: Any? = null,

    @field:SerializedName("tabung_oksigen")
    val tabungOksigen: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("oxycan")
    val oxycan: Any? = null,

    @field:SerializedName("air_wastafel")
    val airWastafel: Any? = null,

    @field:SerializedName("kebersihan")
    val kebersihan: Any? = null,

    @field:SerializedName("antiseptic_gel")
    val antisepticGel: Any? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("catatan")
    val catatan: Any? = null,

    @field:SerializedName("a_tekanan")
    val aTekanan: Any? = null,

    @field:SerializedName("tandu_dorong")
    val tanduDorong: Any? = null,

    @field:SerializedName("to_tekanan")
    val toTekanan: Any? = null,

    @field:SerializedName("tandu_lipat")
    val tanduLipat: Any? = null,

    @field:SerializedName("tas_p3k")
    val tasP3k: Any? = null,

    @field:SerializedName("perlak")
    val perlak: Any? = null,

    @field:SerializedName("cairan_infus")
    val cairanInfus: Any? = null
)