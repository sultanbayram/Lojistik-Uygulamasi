package com.example.staj

import java.io.Serializable

data class MusteriIlan(
    val id: String?,
    val nereden: String?,
    val nereye: String?,
    val olculer: String?,
    val fiyat: String?
) : Serializable

