package com.sipmat.sipmat.model.edgblok3

import com.google.gson.annotations.SerializedName
import com.sipmat.sipmat.model.ffblok.FFBlokModel

data class EdgBlok3Response(
    @field:SerializedName("data")
    val data: List<EdgBlok3Model>? = null,

    @field:SerializedName("message")
    val message: String? = null
)
