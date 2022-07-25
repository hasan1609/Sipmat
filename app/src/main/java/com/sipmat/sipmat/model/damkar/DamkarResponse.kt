package com.sipmat.sipmat.model.damkar

import com.google.gson.annotations.SerializedName
import com.sipmat.sipmat.model.ffblok.FFBlokModel

data class DamkarResponse(
    @field:SerializedName("data")
    val data: List<DamkarModel>? = null,

    @field:SerializedName("message")
    val message: String? = null
)
