package com.sipmat.sipmat.model.ffblok

import com.google.gson.annotations.SerializedName

data class FFBlokResponse(

	@field:SerializedName("data")
	val data: List<FFBlokModel>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
