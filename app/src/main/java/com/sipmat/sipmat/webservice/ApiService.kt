package com.sipmat.sipmat.webservice

import com.sipmat.sipmat.model.*
import com.sipmat.sipmat.model.apar.CekAparModel
import com.sipmat.sipmat.model.apat.*
import com.sipmat.sipmat.model.damkar.DamkarResponse
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
import com.sipmat.sipmat.model.ffblok.FFBlokResponse
import com.sipmat.sipmat.model.ffblok2.FFBlok2Response
import com.sipmat.sipmat.model.hydrant.*
import com.sipmat.sipmat.model.kebisingan.*
import com.sipmat.sipmat.model.pencahayaan.*
import com.sipmat.sipmat.model.postdata.PostScheduleApar
import com.sipmat.sipmat.model.postdata.UpdateScheduleApat
import com.sipmat.sipmat.model.postdata.UpdateScheduleHydrant
import com.sipmat.sipmat.model.seawater.HasilSeaWaterResponse
import com.sipmat.sipmat.model.seawater.PostSeaWaterSchedule
import com.sipmat.sipmat.model.seawater.SeaWaterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.sql.Array

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("token_id") token_id: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("role") role: Int
    ): Call<RegisterResponse>


    @GET("getusers")
    fun getusers(
        @Query("role") role: Int
    ): Call<UsersResponse>

    //=========================APAR==============================
    @FormUrlEncoded
    @POST("apar")
    fun tambahapar(
        @Field("kode") kode: String,
        @Field("jenis") jenis: String,
        @Field("lokasi") lokasi: String,
        @Field("tgl_pengisian") tgl_pengisian: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("updateapar")
    fun updateapar(
        @Field("id") id: Int,
        @Field("kode") kode: String,
        @Field("jenis") jenis: String,
        @Field("lokasi") lokasi: String,
        @Field("tgl_pengisian") tgl_pengisian: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("deleteapar")
    fun hapusapar(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @GET("apar")
    fun getapar(): Call<AparResponse>

    @GET("apar_kadaluarsa")
    fun apar_kadaluarsa(): Call<AparResponse>

    @GET("apar")
    fun getapar_pick(): Call<AparPickResponse>

    //Schedule
    @FormUrlEncoded
    @POST("schedule_apar")
    fun schedule_apar(
        @Field("kode_apar") kode_apar: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String,
        @Field("tanggal_cek") tanggal_cek: String
    ): Call<PostDataResponse>

    //Hapus Schedule
    @FormUrlEncoded
    @POST("hapus_schedule_apar")
    fun hapus_schedule_apar(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @GET("getschedule")
    fun getapar_pick(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<ScheduleResponse>

    @GET("gethasil")
    fun getapar_hasil(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<ScheduleResponse>

    @GET("getschedule_pelaksana")
    fun getschedule_pelaksana(): Call<ScheduleAparPelaksanaResponse>

    @GET("cekapar")
    fun cekapar(@Query("kode") kode: String): Call<CekAparModel>

    @Headers("Content-Type: application/json")
    @POST("update_schedule_apar")
    fun update_schedule_apar(@Body post: PostScheduleApar): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_apar")
    fun acc_apar(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_apar")
    fun return_apar(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @Multipart
    @POST("apar_pdf")
    fun create_pdf(
        @Part image: MultipartBody.Part?,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("jabatan") jabatan: RequestBody,
        @Part("nama") nama: RequestBody,
        ): Call<PostDataResponse>


    //==========================APAAT =======================
    //update apat
    @FormUrlEncoded
    @POST("apat")
    fun apat(
        @Field("kode") kode: String,
        @Field("no_bak") no_bak: String,
        @Field("lokasi") lokasi: String,
    ): Call<PostDataResponse>

    //update apat
    @FormUrlEncoded
    @POST("updateapat")
    fun updateapat(
        @Field("id") id: Int,
        @Field("kode") kode: String,
        @Field("no_bak") no_bak: String,
        @Field("lokasi") lokasi: String,
    ): Call<PostDataResponse>

    @GET("getapat")
    fun getapat(): Call<ApatResponse>

    @GET("getapat")
    fun getapat_pick(): Call<ApatPickResponse>

    @FormUrlEncoded
    @POST("deleteapat")
    fun hapusapat(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //==========================Schedule APAAT =======================
    //Schedule
    @FormUrlEncoded
    @POST("schedule_apat")
    fun schedule_apat(
        @Field("kode_apat") kode_apat: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String,
        @Field("tanggal_cek") tanggal_cek: String
    ): Call<PostDataResponse>

    @Headers("Content-Type: application/json")
    @POST("update_schedule_apat")
    fun update_schedule_apat(@Body post: UpdateScheduleApat): Call<PostDataResponse>

    @GET("cekapat")
    fun cekapat(@Query("kode") kode: String): Call<ApatModel>

    @GET("getschedule_apat")
    fun getschedule_apat(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<ScheduleApatResponse>

    @GET("getschedule_pelaksana_apat")
    fun getschedule_pelaksana_apat(): Call<ScheduleApatPelaksanaResponse>

    @GET("gethasil_apat")
    fun getapat_hasil(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<HasilApatResponse>

    //Hapus Schedule
    @FormUrlEncoded
    @POST("hapus_schedule_apat")
    fun hapus_schedule_apat(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_apat")
    fun acc_apat(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_apat")
    fun return_apat(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @Multipart
    @POST("apat_pdf")
    fun apat_pdf(
        @Part image: MultipartBody.Part?,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("jabatan") jabatan: RequestBody,
        @Part("nama") nama: RequestBody,
    ): Call<PostDataResponse>

    //==========================Hydrant =======================
    @GET("gethydrant")
    fun itemhydrant(): Call<ItemHydrantResponse>

    @GET("gethydrant")
    fun gethydrant_pick(): Call<HydrantPickResponse>

    //Post Hydrant
    @FormUrlEncoded
    @POST("hydrant")
    fun hydrant(
        @Field("kode") kode: String,
        @Field("no_box") no_box: String,
        @Field("lokasi") lokasi: String,
    ): Call<PostDataResponse>
    //Update Hydrant
    @FormUrlEncoded
    @POST("updatehydrant")
    fun updatehydrant(
        @Field("kode") kode: String,
        @Field("no_box") no_box: String,
        @Field("lokasi") lokasi: String,
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //delete
    @FormUrlEncoded
    @POST("deletehydrant")
    fun deletehydrant(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //==========================End Hydrant =======================
    //==========================Schedule Hydrant =======================
    @GET("getschedule_hydrant")
    fun getschedule_hydrant(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<ScheduleHydrantResponse>

    //Tambah Schedule
    @FormUrlEncoded
    @POST("schedule_hydrant")
    fun schedule_hydrant(
        @Field("kode_hydrant") kode_hydrant: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String,
        @Field("tanggal_cek") tanggal_cek: String
    ): Call<PostDataResponse>

    //Hapus Schedule
    @FormUrlEncoded
    @POST("hapus_schedule_hydrant")
    fun hapus_schedule_hydrant(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @GET("getschedule_pelaksana_hydrant")
    fun getschedule_pelaksana_hydrant(): Call<ScheduleHydrantPelaksanaResponse>

    @Headers("Content-Type: application/json")
    @POST("update_schedule_hydrant")
    fun update_schedule_hydrant(@Body post: UpdateScheduleHydrant): Call<PostDataResponse>

    @GET("gethasil_hydrant")
    fun gethasil_hydrant(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<HasilHydrantResponse>

    @FormUrlEncoded
    @POST("acc_hydrant")
    fun acc_hydrant(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_hydrant")
    fun return_hydrant(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @Multipart
    @POST("hydrant_pdf")
    fun hydrant_pdf(
        @Part image: MultipartBody.Part?,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("jabatan") jabatan: RequestBody,
        @Part("nama") nama: RequestBody,
    ): Call<PostDataResponse>
    //==========================End Schedule Hydrant =======================

    //==========================Kebisingan =======================
    @GET("getkebisingan")
    fun getkebisingan(): Call<KebisinganResponse>

    @GET("getkebisingan")
    fun getkebisingan_pick(): Call<KebisinganPickResponse>

    //delete
    @FormUrlEncoded
    @POST("deletekebisingan")
    fun deletekebisingan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    //Post
    @FormUrlEncoded
    @POST("kebisingan")
    fun kebisingan(
        @Field("kode") kode: String,
        @Field("lokasi") lokasi: String,
    ): Call<PostDataResponse>

    //Update
    @FormUrlEncoded
    @POST("updatekebisingan")
    fun updatekebisingan(
        @Field("lokasi") lokasi: String,
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //==========================End Kebisingan =======================

    //==========================Schedule Kebisingan =======================
    @GET("getschedule_kebisingan")
    fun getschedule_kebisingan(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<ScheduleKebisinganResponse>


    //Hapus Schedule
    @FormUrlEncoded
    @POST("hapus_schedule_kebisingan")
    fun hapus_schedule_kebisingan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    //Tambah Schedule
    @Headers("Content-Type: application/json")
    @POST("schedule_kebisingan")
    fun schedule_kebisingan(@Body post: PostScheduleKebisingan): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_kebisingan")
    fun acc_kebisingan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_kebisingan")
    fun return_kebisingan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @GET("getschedule_pelaksana_kebisingan")
    fun getschedule_pelaksana_kebisingan(): Call<ScheduleKebisinganResponse>

    @Headers("Content-Type: application/json")
    @POST("update_schedule_kebisingan")
    fun update_schedule_kebisingan(@Body post: UpdateScheduleKebisingan): Call<PostDataResponse>

    @GET("gethasil_kebisingan")
    fun gethasil_kebisingan(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<HasilKebisinganResponse>

    @Multipart
    @POST("kebisingan_pdf")
    fun kebisingan_pdf(
        @Part image: MultipartBody.Part?,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("jabatan") jabatan: RequestBody,
        @Part("nama") nama: RequestBody,
    ): Call<PostDataResponse>
    //========================== ENDSchedule Kebisingan =======================
    //========================== Pencahayaan =======================
    @GET("getpencahayaan")
    fun getpencahayaan(): Call<PencahayaanResponse>

    //dropwdown pencahayaan
    @GET("getpencahayaan")
    fun getpencahayaan_pick(): Call<PencahayaanPickResponse>

    //delete
    @FormUrlEncoded
    @POST("deletepencahayaan")
    fun deletepencahayaan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    //Post
    @FormUrlEncoded
    @POST("pencahayaan")
    fun pencahayaan(
        @Field("kode") kode: String,
        @Field("lokasi") lokasi: String,
    ): Call<PostDataResponse>

    //Update
    @FormUrlEncoded
    @POST("updatepencahayaan")
    fun updatepencahayaan(
        @Field("lokasi") lokasi: String,
        @Field("id") id: Int
    ): Call<PostDataResponse>


    //========================== ENDPencahayaan =======================

    //==========================Schedule Pencahayaan =======================
    @GET("getschedule_pencahayaan")
    fun getschedule_pencahayaan(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<SchedulePencahayaanResponse>

    //Tambah Schedule
    @Headers("Content-Type: application/json")
    @POST("schedule_pencahayaan")
    fun schedule_pencahayaan(@Body post: PostSchedulePencahayaanResponse): Call<PostDataResponse>

    //Hapus Schedule
    @FormUrlEncoded
    @POST("hapus_schedule_pencahayaan")
    fun hapus_schedule_pencahayaan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    //Return Pencahayaan
    @FormUrlEncoded
    @POST("return_pencahayaan")
    fun return_pencahayaan(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //ACC Pencahayaan
    @FormUrlEncoded
    @POST("acc_pencahayaan")
    fun acc_pencahayaan(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @GET("gethasil_pencahayaan")
    fun gethasil_pencahayaan(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<HasilPencahayaanResponse>

    @Multipart
    @POST("pencahayaan_pdf")
    fun pencahayaan_pdf(
        @Part image: MultipartBody.Part?,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("jabatan") jabatan: RequestBody,
        @Part("nama") nama: RequestBody,
    ): Call<PostDataResponse>

    @GET("getschedule_pelaksana_pencahayaan")
    fun getschedule_pelaksana_pencahayaan(): Call<SchedulePencahayaanResponse>

    @Headers("Content-Type: application/json")
    @POST("update_schedule_pencahayaan")
    fun update_schedule_pencahayaan(@Body post: UpdateSchedulePencahayaan): Call<PostDataResponse>

    //==========================END Schedule Pencahayaan =======================
    //==========================Schedule SeaWater =======================
    @Headers("Content-Type: application/json")
    @POST("seawater")
    fun schedule_seawater(@Body post: PostSeaWaterSchedule): Call<PostDataResponse>

    @GET("seawater")
    fun gethasil_seawater(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<HasilSeaWaterResponse>

    @GET("seawater")
    fun get_seawater(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<SeaWaterResponse>

    //Hapus SeaWater
    @FormUrlEncoded
    @POST("hapus_seawater")
    fun hapus_seawater(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @GET("seawater_pelaksana")
    fun seawater_pelaksana(): Call<HasilSeaWaterResponse>


    @Multipart
    @POST("update_seawater")
    fun update_seawater(
        @Part("id") id: RequestBody,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("shift") shift: RequestBody,
        @Part("is_status") is_status: RequestBody,
        @Part("tanggal_pemeriksa") tanggal_pemeriksa: RequestBody,
        @Part("md_waktu_pencatatan_sebelum_start") md_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("md_waktu_pencatatan_sesudah_start") md_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("md_discharge_press_sebelum_start") md_discharge_press_sebelum_start: RequestBody,
        @Part("md_discharge_press_sesudah_start") md_discharge_press_sesudah_start: RequestBody,
        @Part("md_full_level_sebelum_start") md_full_level_sebelum_start: RequestBody,
        @Part("md_full_level_sesudah_start") md_full_level_sesudah_start: RequestBody,
        @Part("de_waktu_pencatatan_sebelum_start") de_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("de_waktu_pencatatan_sesudah_start") de_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("de_engine_operating_hours_sebelum_start") de_engine_operating_hours_sebelum_start: RequestBody,
        @Part("de_engine_operating_hours_sesudah_start") de_engine_operating_hours_sesudah_start: RequestBody,
        @Part("de_battery_voltage_sebelum_start") de_battery_voltage_sebelum_start: RequestBody,
        @Part("de_battery_voltage_sesudah_start") de_battery_voltage_sesudah_start: RequestBody,
        @Part("de_battery_ampere_sebelum_start") de_battery_ampere_sebelum_start: RequestBody,
        @Part("de_battery_ampere_sesudah_start") de_battery_ampere_sesudah_start: RequestBody,
        @Part("de_battery_level_sebelum_start") de_battery_level_sebelum_start: RequestBody,
        @Part("de_battery_level_sesudah_start") de_battery_level_sesudah_start: RequestBody,
        @Part("de_fuel_level_sebelum_start") de_fuel_level_sebelum_start: RequestBody,
        @Part("de_fuel_level_sesudah_start") de_fuel_level_sesudah_start: RequestBody,
        @Part("de_engine_coolant_tank_sebelum_start") de_engine_coolant_tank_sebelum_start: RequestBody,
        @Part("de_engine_coolant_tank_sesudah_start") de_engine_coolant_tank_sesudah_start: RequestBody,
        @Part("de_cooling_water_inlet_valve_sebelum_start") de_cooling_water_inlet_valve_sebelum_start: RequestBody,
        @Part("de_cooling_water_inlet_valve_sesudah_start") de_cooling_water_inlet_valve_sesudah_start: RequestBody,
        @Part("de_oil_pressure_sebelum_start") de_oil_pressure_sebelum_start: RequestBody,
        @Part("de_oil_pressure_sesudah_start") de_oil_pressure_sesudah_start: RequestBody,
        @Part("de_cooling_water_pressure_after_regulator_sebelum_start") de_cooling_water_pressure_after_regulator_sebelum_start: RequestBody,
        @Part("de_cooling_water_pressure_after_regulator_sesudah_start") de_cooling_water_pressure_after_regulator_sesudah_start: RequestBody,
        @Part("de_coolant_temperature_sebelum_start") de_coolant_temperature_sebelum_start: RequestBody,
        @Part("de_coolant_temperature_sesudah_start") de_coolant_temperature_sesudah_start: RequestBody,
        @Part("de_speed_sebelum_start") de_speed_sebelum_start: RequestBody,
        @Part("de_speed_sesudah_start") de_speed_sesudah_start: RequestBody,
        @Part("de_suction_press_sebelum_start") de_suction_press_sebelum_start: RequestBody,
        @Part("de_suction_press_sesudah_start") de_suction_press_sesudah_start: RequestBody,
        @Part("de_discharge_press_sebelum_start") de_discharge_press_sebelum_start: RequestBody,
        @Part("de_discharge_press_sesudah_start") de_discharge_press_sesudah_start: RequestBody,
        @Part("catatan") catatan: RequestBody,
        @Part("k3_nama") k3_nama: RequestBody,
        @Part k3_ttd: MultipartBody.Part?,
        @Part("operator_nama") operator_nama: RequestBody,
        @Part operator_ttd: MultipartBody.Part?,
        @Part("supervisor_nama") supervisor_nama: RequestBody,
        @Part supervisor_ttd: MultipartBody.Part?,
        @Part("de_crankcase_sebelum_start") de_crankcase_sebelum_start: RequestBody,
        @Part("de_crankcase_sesudah_start") de_crankcase_sesudah_start: RequestBody

        ): Call<PostDataResponse>


    @FormUrlEncoded
    @POST("acc_seawater")
    fun acc_seawater(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_seawater")
    fun return_seawater(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("seawater_pdf")
    fun seawater_pdf(
        @Field("id") id: Int): Call<PostDataResponse>
    //==========================END Schedule SeaWater =======================
    //==========================Schedule FFBLOCK =======================
    @GET("ffblok")
    fun get_ffblok(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<FFBlokResponse>

    //Hapus SeaWater
    @FormUrlEncoded
    @POST("hapus_ffblok")
    fun hapus_ffblok(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //tambah schedule
    @FormUrlEncoded
    @POST("schedule_ffblok")
    fun schedule_ffblok(
        @Field("hari") hari: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_ffblok")
    fun return_ffblok(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_ffblok")
    fun acc_ffblok(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("ffblok_pdf")
    fun ffblok_pdf(
        @Field("id") id: Int): Call<PostDataResponse>

    @GET("ffblok")
    fun gethasil_ffblok(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<FFBlokResponse>

    @GET("ffblok_pelaksana")
    fun ffblok_pelaksana(): Call<FFBlokResponse>

    @Multipart
    @POST("update_ffblok")
    fun update_ffblok(
        @Part("id") id: RequestBody,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("tanggal_pemeriksa") tanggal_pemeriksa: RequestBody,
        @Part("shift") shift: RequestBody,
        @Part("is_status") is_status: RequestBody,

        @Part("sp_waktu_pencatatan_sebelum_start") sp_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("sp_waktu_pencatatan_sesudah_start") sp_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("sp_suction_press_sebelum_start") sp_suction_press_sebelum_start: RequestBody,
        @Part("sp_suction_press_sesudah_start") sp_suction_press_sesudah_start: RequestBody,
        @Part("sp_discharge_press_sebelum_start") sp_discharge_press_sebelum_start: RequestBody,
        @Part("sp_discharge_press_sesudah_start") sp_discharge_press_sesudah_start: RequestBody,
        @Part("sp_fuel_level_sebelum_start") sp_fuel_level_sebelum_start: RequestBody,
        @Part("sp_fuel_level_sesudah_start") sp_fuel_level_sesudah_start: RequestBody,
        @Part("sp_auto_start_sebelum_start") sp_auto_start_sebelum_start: RequestBody,
        @Part("sp_auto_start_sesudah_start") sp_auto_start_sesudah_start: RequestBody,

        @Part("md_waktu_pencatatan_sebelum_start") md_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("md_waktu_pencatatan_sesudah_start") md_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("md_suction_press_sebelum_start") md_suction_press_sebelum_start: RequestBody,
        @Part("md_suction_press_sesudah_start") md_suction_press_sesudah_start: RequestBody,
        @Part("md_discharge_press_sebelum_start") md_discharge_press_sebelum_start: RequestBody,
        @Part("md_discharge_press_sesudah_start") md_discharge_press_sesudah_start: RequestBody,
        @Part("md_full_level_sebelum_start") md_full_level_sebelum_start: RequestBody,
        @Part("md_full_level_sesudah_start") md_full_level_sesudah_start: RequestBody,
        @Part("md_auto_start_sebelum_start") md_auto_start_sebelum_start: RequestBody,
        @Part("md_auto_start_sesudah_start") md_auto_start_sesudah_start: RequestBody,

        @Part("de_waktu_pencatatan_sebelum_start") de_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("de_waktu_pencatatan_sesudah_start") de_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("de_lube_oil_press_sebelum_start") de_lube_oil_press_sebelum_start: RequestBody,
        @Part("de_lube_oil_press_sesudah_start") de_lube_oil_press_sesudah_start: RequestBody,
        @Part("de_battery_voltage_sebelum_start") de_battery_voltage_sebelum_start: RequestBody,
        @Part("de_battery_voltage_sesudah_start") de_battery_voltage_sesudah_start: RequestBody,
        @Part("de_battery_ampere_sebelum_start") de_battery_ampere_sebelum_start: RequestBody,
        @Part("de_battery_ampere_sesudah_start") de_battery_ampere_sesudah_start: RequestBody,
        @Part("de_battery_level_sebelum_start") de_battery_level_sebelum_start: RequestBody,
        @Part("de_battery_level_sesudah_start") de_battery_level_sesudah_start: RequestBody,
        @Part("de_fuel_level_sebelum_start") de_fuel_level_sebelum_start: RequestBody,
        @Part("de_fuel_level_sesudah_start") de_fuel_level_sesudah_start: RequestBody,
        @Part("de_lube_oil_level_sebelum_start") de_lube_oil_level_sebelum_start: RequestBody,
        @Part("de_lube_oil_level_sesudah_start") de_lube_oil_level_sesudah_start: RequestBody,
        @Part("de_water_cooler_level_sebelum_start") de_water_cooler_level_sebelum_start: RequestBody,
        @Part("de_water_cooler_level_sesudah_start") de_water_cooler_level_sesudah_start: RequestBody,
        @Part("de_speed_sebelum_start") de_speed_sebelum_start: RequestBody,
        @Part("de_speed_sesudah_start") de_speed_sesudah_start: RequestBody,
        @Part("de_suction_press_sebelum_start") de_suction_press_sebelum_start: RequestBody,
        @Part("de_suction_press_sesudah_start") de_suction_press_sesudah_start: RequestBody,
        @Part("de_discharge_press_sebelum_start") de_discharge_press_sebelum_start: RequestBody,
        @Part("de_discharge_press_sesudah_start") de_discharge_press_sesudah_start: RequestBody,
        @Part("de_auto_start_sebelum_start") de_auto_start_sebelum_start: RequestBody,
        @Part("de_auto_start_sesudah_start") de_auto_start_sesudah_start: RequestBody,

        @Part("catatan") catatan: RequestBody,
        @Part("k3_nama") k3_nama: RequestBody,
        @Part k3_ttd: MultipartBody.Part?,
        @Part("operator_nama") operator_nama: RequestBody,
        @Part  operator_ttd: MultipartBody.Part?,
        @Part("supervisor_nama") supervisor_nama: RequestBody,
        @Part supervisor_ttd: MultipartBody.Part?
    ): Call<PostDataResponse>
    //==========================END Schedule FFBLOCK =======================
    //==========================Schedule FFBLOCK2 =======================
    @GET("ffblok2")
    fun get_ffblok2(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<FFBlok2Response>

    //Hapus SeaWater
    @FormUrlEncoded
    @POST("hapus_ffblok2")
    fun hapus_ffblok2(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //tambah schedule
    @FormUrlEncoded
    @POST("schedule_ffblok2")
    fun schedule_ffblok2(
        @Field("hari") hari: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_ffblok2")
    fun return_ffblok2(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_ffblok2")
    fun acc_ffblok2(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("ffblok2_pdf")
    fun ffblok2_pdf(
        @Field("id") id: Int): Call<PostDataResponse>

    @GET("ffblok2")
    fun gethasil_ffblok2(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<FFBlok2Response>

    @GET("ffblok2_pelaksana")
    fun ffblok2_pelaksana(): Call<FFBlok2Response>

    @Multipart
    @POST("update_ffblok2")
    fun update_ffblok2(
        @Part("id") id: RequestBody,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("tanggal_pemeriksa") tanggal_pemeriksa: RequestBody,
        @Part("shift") shift: RequestBody,
        @Part("is_status") is_status: RequestBody,

        @Part("sp_waktu_pencatatan_sebelum_start") sp_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("sp_waktu_pencatatan_sesudah_start") sp_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("sp_suction_press_sebelum_start") sp_suction_press_sebelum_start: RequestBody,
        @Part("sp_suction_press_sesudah_start") sp_suction_press_sesudah_start: RequestBody,
        @Part("sp_discharge_press_sebelum_start") sp_discharge_press_sebelum_start: RequestBody,
        @Part("sp_discharge_press_sesudah_start") sp_discharge_press_sesudah_start: RequestBody,
        @Part("sp_auto_start_sebelum_start") sp_auto_start_sebelum_start: RequestBody,
        @Part("sp_auto_start_sesudah_start") sp_auto_start_sesudah_start: RequestBody,
        @Part("sp_auto_stop_sebelum_start") sp_auto_stop_sebelum_start: RequestBody,
        @Part("sp_auto_stop_sesudah_start") sp_auto_stop_sesudah_start: RequestBody,

        @Part("md_waktu_pencatatan_sebelum_start") md_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("md_waktu_pencatatan_sesudah_start") md_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("md_suction_press_sebelum_start") md_suction_press_sebelum_start: RequestBody,
        @Part("md_suction_press_sesudah_start") md_suction_press_sesudah_start: RequestBody,
        @Part("md_discharge_press_sebelum_start") md_discharge_press_sebelum_start: RequestBody,
        @Part("md_discharge_press_sesudah_start") md_discharge_press_sesudah_start: RequestBody,
        @Part("md_full_level_sebelum_start") md_full_level_sebelum_start: RequestBody,
        @Part("md_full_level_sesudah_start") md_full_level_sesudah_start: RequestBody,
        @Part("md_auto_start_sebelum_start") md_auto_start_sebelum_start: RequestBody,
        @Part("md_auto_start_sesudah_start") md_auto_start_sesudah_start: RequestBody,

        @Part("de_waktu_pencatatan_sebelum_start") de_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("de_waktu_pencatatan_sesudah_start") de_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("de_lube_oil_press_sebelum_start") de_lube_oil_press_sebelum_start: RequestBody,
        @Part("de_lube_oil_press_sesudah_start") de_lube_oil_press_sesudah_start: RequestBody,
        @Part("de_battery_voltage_sebelum_start") de_battery_voltage_sebelum_start: RequestBody,
        @Part("de_battery_voltage_sesudah_start") de_battery_voltage_sesudah_start: RequestBody,
        @Part("de_battery_ampere_sebelum_start") de_battery_ampere_sebelum_start: RequestBody,
        @Part("de_battery_ampere_sesudah_start") de_battery_ampere_sesudah_start: RequestBody,
        @Part("de_battery_level_sebelum_start") de_battery_level_sebelum_start: RequestBody,
        @Part("de_battery_level_sesudah_start") de_battery_level_sesudah_start: RequestBody,
        @Part("de_fuel_level_sebelum_start") de_fuel_level_sebelum_start: RequestBody,
        @Part("de_fuel_level_sesudah_start") de_fuel_level_sesudah_start: RequestBody,
        @Part("de_lube_oil_level_sebelum_start") de_lube_oil_level_sebelum_start: RequestBody,
        @Part("de_lube_oil_level_sesudah_start") de_lube_oil_level_sesudah_start: RequestBody,
        @Part("de_water_cooler_level_sebelum_start") de_water_cooler_level_sebelum_start: RequestBody,
        @Part("de_water_cooler_level_sesudah_start") de_water_cooler_level_sesudah_start: RequestBody,
        @Part("de_speed_sebelum_start") de_speed_sebelum_start: RequestBody,
        @Part("de_speed_sesudah_start") de_speed_sesudah_start: RequestBody,
        @Part("de_suction_press_sebelum_start") de_suction_press_sebelum_start: RequestBody,
        @Part("de_suction_press_sesudah_start") de_suction_press_sesudah_start: RequestBody,
        @Part("de_discharge_press_sebelum_start") de_discharge_press_sebelum_start: RequestBody,
        @Part("de_discharge_press_sesudah_start") de_discharge_press_sesudah_start: RequestBody,
        @Part("de_auto_start_sebelum_start") de_auto_start_sebelum_start: RequestBody,
        @Part("de_auto_start_sesudah_start") de_auto_start_sesudah_start: RequestBody,


        @Part("catatan") catatan: RequestBody,
        @Part("k3_nama") k3_nama: RequestBody,
        @Part k3_ttd: MultipartBody.Part?,
        @Part("operator_nama") operator_nama: RequestBody,
        @Part operator_ttd: MultipartBody.Part?,
        @Part("supervisor_nama") supervisor_nama: RequestBody,
        @Part supervisor_ttd: MultipartBody.Part?
    ): Call<PostDataResponse>
    //==========================END Schedule FFBLOCK2 =======================
    //==========================Schedule EDGBLOK1 =======================
    @GET("edgblok1")
    fun get_edgblok1(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<EdgBlokResponse>

    //Hapus SeaWater
    @FormUrlEncoded
    @POST("hapus_edgblok1")
    fun hapus_edgblok1(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //tambah schedule
    @FormUrlEncoded
    @POST("schedule_edgblok1")
    fun schedule_edgblok1(
        @Field("hari") hari: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_edgblok1")
    fun return_edgblok1(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_edgblok1")
    fun acc_edgblok1(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("edgblok1_pdf")
    fun edgblok1_pdf(
        @Field("id") id: Int): Call<PostDataResponse>

    @GET("edgblok1")
    fun gethasil_edgblok1(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<EdgBlokResponse>

    @GET("edgblok1_pelaksana")
    fun edgblok1_pelaksana(): Call<EdgBlokResponse>

    @Multipart
    @POST("update_edgblok1")
    fun update_edgblok1(
        @Part("id") id: RequestBody,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("tanggal_pemeriksa") tanggal_pemeriksa: RequestBody,
        @Part("shift") shift: RequestBody,
        @Part("is_status") is_status: RequestBody,

        @Part("p_waktu_pencatatan_sebelum_start") sp_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("p_waktu_pencatatan_sesudah_start") sp_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("p_oil_pressure_sebelum_start") p_oil_pressure_sebelum_start: RequestBody,
        @Part("p_oil_pressure_sesudah_start") p_oil_pressure_sesudah_start: RequestBody,
        @Part("p_oil_temprature_sebelum_start") p_oil_temprature_sebelum_start: RequestBody,
        @Part("p_oil_temprature_sesudah_start") p_oil_temprature_sesudah_start: RequestBody,
        @Part("p_water_temprature_sebelum_start") p_water_temprature_sebelum_start: RequestBody,
        @Part("p_water_temprature_sesudah_start") p_water_temprature_sesudah_start: RequestBody,
        @Part("p_speed_sebelum_start") p_speed_sebelum_start: RequestBody,
        @Part("p_speed_sesudah_start") p_speed_sesudah_start: RequestBody,
        @Part("p_hour_meter_1_sebelum_start") p_hour_meter_1_sebelum_start: RequestBody,
        @Part("p_hour_meter_1_sesudah_start") p_hour_meter_1_sesudah_start: RequestBody,
        @Part("p_hour_meter_2_sebelum_start") p_hour_meter_2_sebelum_start: RequestBody,
        @Part("p_hour_meter_2_sesudah_start") p_hour_meter_2_sesudah_start: RequestBody,
        @Part("p_main_line_voltage_sebelum_start") p_main_line_voltage_sebelum_start: RequestBody,
        @Part("p_main_line_voltage_sesudah_start") p_main_line_voltage_sesudah_start: RequestBody,
        @Part("p_main_line_frequency_sebelum_start") p_main_line_frequency_sebelum_start: RequestBody,
        @Part("p_main_line_frequency_sesudah_start") p_main_line_frequency_sesudah_start: RequestBody,
        @Part("p_generator_breaker_sebelum_start") p_generator_breaker_sebelum_start: RequestBody,
        @Part("p_generator_breaker_sesudah_start") p_generator_breaker_sesudah_start: RequestBody,
        @Part("p_generator_voltage_sebelum_start") p_generator_voltage_sebelum_start: RequestBody,
        @Part("p_generator_voltage_sesudah_start") p_generator_voltage_sesudah_start: RequestBody,
        @Part("p_generator_frequency_sebelum_start") p_generator_frequency_sebelum_start: RequestBody,
        @Part("p_generator_frequency_sesudah_start") p_generator_frequency_sesudah_start: RequestBody,
        @Part("p_load_current_sebelum_start") p_load_current_sebelum_start: RequestBody,
        @Part("p_load_current_sesudah_start") p_load_current_sesudah_start: RequestBody,
        @Part("p_daya_sebelum_start") p_daya_sebelum_start: RequestBody,
        @Part("p_daya_sesudah_start") p_daya_sesudah_start: RequestBody,
        @Part("p_cos_sebelum_start") p_cos_sebelum_start: RequestBody,
        @Part("p_cos_sesudah_start") p_cos_sesudah_start: RequestBody,
        @Part("p_battery_charge_voltase_sebelum_start") p_battery_charge_voltase_sebelum_start: RequestBody,
        @Part("p_battery_charge_voltase_sesudah_start") p_battery_charge_voltase_sesudah_start: RequestBody,
        @Part("p_hour_sebelum_start") p_hour_sebelum_start: RequestBody,
        @Part("p_hour_sesudah_start") p_hour_sesudah_start: RequestBody,
        @Part("p_lube_oil_level_sebelum_start") p_lube_oil_level_sebelum_start: RequestBody,
        @Part("p_lube_oil_level_sesudah_start") p_lube_oil_level_sesudah_start: RequestBody,
        @Part("p_accu_level_sebelum_start") p_accu_level_sebelum_start: RequestBody,
        @Part("p_accu_level_sesudah_start") p_accu_level_sesudah_start: RequestBody,
        @Part("p_radiator_level_sebelum_start") p_radiator_level_sebelum_start: RequestBody,
        @Part("p_radiator_level_sesudah_start") p_radiator_level_sesudah_start: RequestBody,
        @Part("p_fuel_oil_level_sebelum_start") p_fuel_oil_level_sebelum_start: RequestBody,
        @Part("p_fuel_oil_level_sesudah_start") p_fuel_oil_level_sesudah_start: RequestBody,

        @Part("catatan") catatan: RequestBody,
        @Part("k3_nama") k3_nama: RequestBody,
        @Part k3_ttd: MultipartBody.Part?,
        @Part("operator_nama") operator_nama: RequestBody,
        @Part operator_ttd: MultipartBody.Part?,
        @Part("supervisor_nama") supervisor_nama: RequestBody,
        @Part supervisor_ttd: MultipartBody.Part?
    ): Call<PostDataResponse>
    //==========================END Schedule EDGBLOK1 =======================
    //==========================Schedule EDGBLOK2 =======================
    @GET("edgblok2")
    fun get_edgblok2(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<EdgBlok2Response>

    //Hapus SeaWater
    @FormUrlEncoded
    @POST("hapus_edgblok2")
    fun hapus_edgblok2(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //tambah schedule
    @FormUrlEncoded
    @POST("schedule_edgblok2")
    fun schedule_edgblok2(
        @Field("hari") hari: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_edgblok2")
    fun return_edgblok2(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_edgblok2")
    fun acc_edgblok2(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("edgblok2_pdf")
    fun edgblok2_pdf(
        @Field("id") id: Int): Call<PostDataResponse>

    @GET("edgblok2")
    fun gethasil_edgblok2(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<EdgBlok2Response>

    @GET("edgblok2_pelaksana")
    fun edgblok2_pelaksana(): Call<EdgBlok2Response>

    @Multipart
    @POST("update_edgblok2")
    fun update_edgblok2(
        @Part("id") id: RequestBody,
        @Part("tw") tw: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("tanggal_pemeriksa") tanggal_pemeriksa: RequestBody,
        @Part("shift") shift: RequestBody,
        @Part("is_status") is_status: RequestBody,

        @Part("p_waktu_pencatatan_sebelum_start") sp_waktu_pencatatan_sebelum_start: RequestBody,
        @Part("p_waktu_pencatatan_sesudah_start") sp_waktu_pencatatan_sesudah_start: RequestBody,
        @Part("p_oil_pressure_sebelum_start") p_oil_pressure_sebelum_start: RequestBody,
        @Part("p_oil_pressure_sesudah_start") p_oil_pressure_sesudah_start: RequestBody,
        @Part("p_fuel_pressure_sebelum_start") p_fuel_pressure_sebelum_start: RequestBody,
        @Part("p_fuel_pressure_sesudah_start") p_fuel_pressure_sesudah_start: RequestBody,
        @Part("p_fuel_temprature_sebelum_start") p_fuel_temprature_sebelum_start: RequestBody,
        @Part("p_fuel_temprature_sesudah_start") p_fuel_temprature_sesudah_start: RequestBody,
        @Part("p_coolant_pressure_sebelum_start") p_coolant_pressure_sebelum_start: RequestBody,
        @Part("p_coolant_pressure_sesudah_start") p_coolant_pressure_sesudah_start: RequestBody,
        @Part("p_coolant_temprature_sebelum_start") p_coolant_temprature_sebelum_start: RequestBody,
        @Part("p_coolant_temprature_sesudah_start") p_coolant_temprature_sesudah_start: RequestBody,
        @Part("p_speed_sebelum_start") p_speed_sebelum_start: RequestBody,
        @Part("p_speed_sesudah_start") p_speed_sesudah_start: RequestBody,
        @Part("p_inlet_temprature_sebelum_start") p_inlet_temprature_sebelum_start: RequestBody,
        @Part("p_inlet_temprature_sesudah_start") p_inlet_temprature_sesudah_start: RequestBody,
        @Part("p_turbo_pleasure_sebelum_start") p_turbo_pleasure_sebelum_start: RequestBody,
        @Part("p_turbo_pleasure_sesudah_start") p_turbo_pleasure_sesudah_start: RequestBody,
        @Part("p_generator_breaker_sebelum_start") p_generator_breaker_sebelum_start: RequestBody,
        @Part("p_generator_breaker_sesudah_start") p_generator_breaker_sesudah_start: RequestBody,
        @Part("p_generator_voltage_sebelum_start") p_generator_voltage_sebelum_start: RequestBody,
        @Part("p_generator_voltage_sesudah_start") p_generator_voltage_sesudah_start: RequestBody,
        @Part("p_generator_frequency_sebelum_start") p_generator_frequency_sebelum_start: RequestBody,
        @Part("p_generator_frequency_sesudah_start") p_generator_frequency_sesudah_start: RequestBody,
        @Part("p_generator_current_sebelum_start") p_generator_current_sebelum_start: RequestBody,
        @Part("p_generator_current_sesudah_start") p_generator_current_sesudah_start: RequestBody,
        @Part("p_load_sebelum_start") p_load_current_sebelum_start: RequestBody,
        @Part("p_load_sesudah_start") p_load_current_sesudah_start: RequestBody,
        @Part("p_battery_charge_voltase_sebelum_start") p_battery_charge_voltase_sebelum_start: RequestBody,
        @Part("p_battery_charge_voltase_sesudah_start") p_battery_charge_voltase_sesudah_start: RequestBody,
        @Part("p_lube_oil_level_sebelum_start") p_lube_oil_level_sebelum_start: RequestBody,
        @Part("p_lube_oil_level_sesudah_start") p_lube_oil_level_sesudah_start: RequestBody,
        @Part("p_accu_level_sebelum_start") p_accu_level_sebelum_start: RequestBody,
        @Part("p_accu_level_sesudah_start") p_accu_level_sesudah_start: RequestBody,
        @Part("p_radiator_level_sebelum_start") p_radiator_level_sebelum_start: RequestBody,
        @Part("p_radiator_level_sesudah_start") p_radiator_level_sesudah_start: RequestBody,
        @Part("p_fuel_oil_level_sebelum_start") p_fuel_oil_level_sebelum_start: RequestBody,
        @Part("p_fuel_oil_level_sesudah_start") p_fuel_oil_level_sesudah_start: RequestBody,

        @Part("catatan") catatan: RequestBody,
        @Part("k3_nama") k3_nama: RequestBody,
        @Part k3_ttd: MultipartBody.Part?,
        @Part("operator_nama") operator_nama: RequestBody,
        @Part operator_ttd: MultipartBody.Part?,
        @Part("supervisor_nama") supervisor_nama: RequestBody,
        @Part supervisor_ttd: MultipartBody.Part?
    ): Call<PostDataResponse>
    //==========================END Schedule EDGBLOK2 =======================
    //==========================Schedule DAMKAR =======================
    @GET("damkar")
    fun get_damkar(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<DamkarResponse>

    //Hapus SeaWater
    @FormUrlEncoded
    @POST("hapus_damkar")
    fun hapus_damkar(
        @Field("id") id: Int
    ): Call<PostDataResponse>
    //tambah schedule
    @FormUrlEncoded
    @POST("schedule_damkar")
    fun schedule_damkar(
        @Field("hari") hari: String,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("return_damkar")
    fun return_damkar(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("acc_damkar")
    fun acc_damkar(
        @Field("id") id: Int
    ): Call<PostDataResponse>

    @FormUrlEncoded
    @POST("damkar_pdf")
    fun damkar_pdf(
        @Field("id") id: Int): Call<PostDataResponse>

    @GET("damkar")
    fun gethasil_damkar(
        @Query("tw") tw: String,
        @Query("tahun") tahun: String
    ): Call<DamkarResponse>

    @GET("damkar_pelaksana")
    fun damkar_pelaksana(): Call<DamkarResponse>

    @Multipart
    @POST("update_damkar")
    fun update_damkar(
        @Field("id") id: Int,
        @Field("tw") tw: String,
        @Field("tahun") tahun: String,
        @Field("tanggal_pemeriksa") tanggal_pemeriksa: RequestBody,
        @Field("shift") shift: String,
        @Field("is_status") is_status: Int,

        @Field("start") start: String,
        @Field("stop") stop: String,
        @Field("air_accu") air_accu: String,
        @Field("level_air_radiator") level_air_radiator: String,
        @Field("tempratur_mesin") tempratur_mesin: String,
        @Field("level_oil") level_oil: String,
        @Field("filter_solar") filter_solar: String,
        @Field("level_minyak_rem") level_minyak_rem: String,
        @Field("suara_mesin") suara_mesin: String,
        @Field("lampu_depan") lampu_depan: String,
        @Field("lampu_rem") lampu_rem: String,
        @Field("lampu_sein_kanan_depan") lampu_sein_kanan_depan: String,
        @Field("lampu_sein_kanan_depan") lampu_sein_kanan_belakang: String,
        @Field("lampu_sein_kiri_depan") lampu_sein_kiri_depan: String,
        @Field("lampu_sein_kiri_belakang") lampu_sein_kiri_belakang: String,
        @Field("lampu_hazard") lampu_hazard: String,
        @Field("lampu_sorot") lampu_sorot: String,
        @Field("lampu_dalam_depan") lampu_dalam_depan: String,
        @Field("lampu_dalam_tengah") lampu_dalam_tengah: String,
        @Field("wiper") wiper: String,
        @Field("spion") spion: String,
        @Field("sirine") sirine: String,
        @Field("catatan") catatan: String,

        ): Call<PostDataResponse>
    //==========================END Schedule EDGBLOK2 =======================
}

