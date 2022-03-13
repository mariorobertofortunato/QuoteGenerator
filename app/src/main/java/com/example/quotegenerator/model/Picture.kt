package com.example.quotegenerator.model

data class Picture (
    val id : Int,
    val author : String,
    val width : Int,
    val height : Int,
    val url : String,
    val download_url : String)