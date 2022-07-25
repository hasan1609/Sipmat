package com.sipmat.sipmat.model.edgblok2

import com.google.gson.annotations.SerializedName
import com.sipmat.sipmat.model.ffblok.FFBlokModel

data class EdgBlok2Response(
    @field:SerializedName("data")
    val data: List<EdgBlok2Model>? = null,

    @field:SerializedName("message")
    val message: String? = null
)
