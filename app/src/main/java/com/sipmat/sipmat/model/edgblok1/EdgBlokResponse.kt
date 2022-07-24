package com.sipmat.sipmat.model.edgblok1

import com.google.gson.annotations.SerializedName
import com.sipmat.sipmat.model.ffblok.FFBlokModel

data class EdgBlokResponse(
    @field:SerializedName("data")
    val data: List<EdgBlokModel>? = null,

    @field:SerializedName("message")
    val message: String? = null
)
