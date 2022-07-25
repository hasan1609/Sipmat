package com.sipmat.sipmat.model.damkar

import com.google.gson.annotations.SerializedName

data class DamkarModel (
    @field:SerializedName("hari")
    val hari: String? = null,

    @field:SerializedName("spion")
    val spion: Any? = null,

    @field:SerializedName("tw")
    val tw: String? = null,

    @field:SerializedName("lampu_hazard")
    val lampuHazard: Any? = null,

    @field:SerializedName("tanggal_pemeriksa")
    val tanggalPemeriksa: Any? = null,

    @field:SerializedName("shift")
    val shift: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("tanggal_cek")
    val tanggalCek: String? = null,

    @field:SerializedName("lampu_rem")
    val lampuRem: Any? = null,

    @field:SerializedName("lampu_sorot")
    val lampuSorot: Any? = null,

    @field:SerializedName("lampu_dalam_tengah")
    val lampuDalamTengah: Any? = null,

    @field:SerializedName("level_minyak_rem")
    val levelMinyakRem: Any? = null,

    @field:SerializedName("is_status")
    val isStatus: Int? = null,

    @field:SerializedName("lampu_belakang")
    val lampuBelakang: Any? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("air_accu")
    val airAccu: Any? = null,

    @field:SerializedName("level_air_radiator")
    val levelAirRadiator: Any? = null,

    @field:SerializedName("level_oil")
    val levelOil: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("lampu_sein_kiri_belakang")
    val lampuSeinKiriBelakang: Any? = null,

    @field:SerializedName("tahun")
    val tahun: String? = null,

    @field:SerializedName("suara_mesin")
    val suaraMesin: Any? = null,

    @field:SerializedName("lampu_depan")
    val lampuDepan: Any? = null,

    @field:SerializedName("start")
    val start: Any? = null,

    @field:SerializedName("catatan")
    val catatan: Any? = null,

    @field:SerializedName("lampu_dalam_belakang")
    val lampuDalamBelakang: Any? = null,

    @field:SerializedName("filter_solar")
    val filterSolar: Any? = null,

    @field:SerializedName("lampu_sein_kanan_depan")
    val lampuSeinKananDepan: Any? = null,

    @field:SerializedName("stop")
    val stop: Any? = null,

    @field:SerializedName("lampu_sein_kanan_belakang")
    val lampuSeinKananBelakang: Any? = null,

    @field:SerializedName("wiper")
    val wiper: Any? = null,

    @field:SerializedName("lampu_dalam_depan")
    val lampuDalamDepan: Any? = null,

    @field:SerializedName("tempratur_mesin")
    val tempraturMesin: Any? = null,

    @field:SerializedName("lampu_sein_kiri_depan")
    val lampuSeinKiriDepan: Any? = null,

    @field:SerializedName("sirine")
    val sirine: Any? = null
)