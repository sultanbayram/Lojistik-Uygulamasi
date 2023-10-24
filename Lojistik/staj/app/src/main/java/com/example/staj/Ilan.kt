package com.example.staj

import java.io.Serializable

data class Ilan(

    val id: String?,
    var kullaniciEmail: String?,
    val nereden: String?,
    val nereye: String?,
    val olculer: String?,
    val fiyat: String?,
    val tarih: String? = ""
) : Serializable
