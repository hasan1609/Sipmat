package com.sipmat.sipmat.model.seawater

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class UpdateSeaWater(

	@field:SerializedName("md_discharge_press_sebelum_start")
	val mdDischargePressSebelumStart: RequestBody? = null,

	@field:SerializedName("de_battery_ampere_sesudah_start")
	val deBatteryAmpereSesudahStart: RequestBody? = null,

	@field:SerializedName("tw")
	val tw: RequestBody? = null,

	@field:SerializedName("operator_ttd")
	val operatorTtd: MultipartBody.Part? = null,

	@field:SerializedName("md_discharge_press_sesudah_start")
	val mdDischargePressSesudahStart: RequestBody? = null,

	@field:SerializedName("shift")
	val shift: RequestBody? = null,

	@field:SerializedName("de_battery_ampere_sebelum_start")
	val deBatteryAmpereSebelumStart: RequestBody? = null,

	@field:SerializedName("de_engine_coolant_tank_sesudah_start")
	val deEngineCoolantTankSesudahStart: RequestBody? = null,

	@field:SerializedName("tanggal_cek")
	val tanggalCek: RequestBody? = null,

	@field:SerializedName("tanggal_pemeriksa")
	val tanggalPemeriksa: String? = null,

	@field:SerializedName("de_engine_coolant_tank_sebelum_start")
	val deEngineCoolantTankSebelumStart: RequestBody? = null,

	@field:SerializedName("de_battery_voltage_sesudah_start")
	val deBatteryVoltageSesudahStart: RequestBody? = null,

	@field:SerializedName("de_oil_pressure_sebelum_start")
	val deOilPressureSebelumStart: RequestBody? = null,

	@field:SerializedName("de_coolant_temperature_sesudah_start")
	val deCoolantTemperatureSesudahStart: RequestBody? = null,

	@field:SerializedName("k3_nama")
	val k3Nama: RequestBody? = null,

	@field:SerializedName("de_cooling_water_pressure_after_regulator_sebelum_start")
	val deCoolingWaterPressureAfterRegulatorSebelumStart: RequestBody? = null,

	@field:SerializedName("de_discharge_press_sesudah_start")
	val deDischargePressSesudahStart: RequestBody? = null,

	@field:SerializedName("is_status")
	val isStatus: RequestBody? = null,

	@field:SerializedName("de_battery_level_sesudah_start")
	val deBatteryLevelSesudahStart: RequestBody? = null,

	@field:SerializedName("de_suction_press_sebelum_start")
	val deSuctionPressSebelumStart: RequestBody? = null,

	@field:SerializedName("de_oil_pressure_sesudah_start")
	val deOilPressureSesudahStart: RequestBody? = null,

	@field:SerializedName("de_cooling_water_pressure_after_regulator_sesudah_start")
	val deCoolingWaterPressureAfterRegulatorSesudahStart: RequestBody? = null,

	@field:SerializedName("de_waktu_pencatatan_sebelum_start")
	val deWaktuPencatatanSebelumStart: RequestBody? = null,

	@field:SerializedName("de_speed_sesudah_start")
	val deSpeedSesudahStart: RequestBody? = null,

	@field:SerializedName("de_battery_level_sebelum_start")
	val deBatteryLevelSebelumStart: RequestBody? = null,

	@field:SerializedName("supervisor_ttd")
	val supervisorTtd: MultipartBody.Part? = null,

	@field:SerializedName("de_coolant_temperature_sebelum_start")
	val deCoolantTemperatureSebelumStart: RequestBody? = null,

	@field:SerializedName("md_full_level_sebelum_start")
	val mdFullLevelSebelumStart: RequestBody? = null,

	@field:SerializedName("de_engine_operating_hours_sebelum_start")
	val deEngineOperatingHoursSebelumStart: RequestBody? = null,

	@field:SerializedName("k3_ttd")
	val k3Ttd: MultipartBody.Part? = null,

	@field:SerializedName("de_fuel_level_sebelum_start")
	val deFuelLevelSebelumStart: RequestBody? = null,

	@field:SerializedName("tahun")
	val tahun: RequestBody? = null,

	@field:SerializedName("de_engine_operating_hours_sesudah_start")
	val deEngineOperatingHoursSesudahStart: RequestBody? = null,

	@field:SerializedName("de_cooling_water_inlet_valve_sesudah_start")
	val deCoolingWaterInletValveSesudahStart: RequestBody? = null,

	@field:SerializedName("catatan")
	val catatan: RequestBody? = null,

	@field:SerializedName("de_fuel_level_sesudah_start")
	val deFuelLevelSesudahStart: RequestBody? = null,

	@field:SerializedName("md_waktu_pencatatan_sesudah_start")
	val mdWaktuPencatatanSesudahStart: RequestBody? = null,

	@field:SerializedName("md_waktu_pencatatan_sebelum_start")
	val mdWaktuPencatatanSebelumStart: RequestBody? = null,

	@field:SerializedName("md_full_level_sesudah_start")
	val mdFullLevelSesudahStart: RequestBody? = null,

	@field:SerializedName("operator_nama")
	val operatorNama: RequestBody? = null,

	@field:SerializedName("supervisor_nama")
	val supervisorNama: RequestBody? = null,

	@field:SerializedName("de_battery_voltage_sebelum_start")
	val deBatteryVoltageSebelumStart: RequestBody? = null,

	@field:SerializedName("de_speed_sebelum_start")
	val deSpeedSebelumStart: RequestBody? = null,

	@field:SerializedName("de_suction_press_sesudah_start")
	val deSuctionPressSesudahStart: RequestBody? = null,

	@field:SerializedName("de_discharge_press_sebelum_start")
	val deDischargePressSebelumStart: RequestBody? = null,

	@field:SerializedName("de_waktu_pencatatan_sesudah_start")
	val deWaktuPencatatanSesudahStart: RequestBody? = null,

	@field:SerializedName("de_cooling_water_inlet_valve_sebelum_start")
	val deCoolingWaterInletValveSebelumStart: RequestBody? = null,

	@field:SerializedName("de_crankcase_sebelum_start")
	val de_crankcase_sebelum_start: RequestBody? = null,

	@field:SerializedName("de_crankcase_sesudah_start")
	val de_crankcase_sesudah_start: RequestBody? = null
)
