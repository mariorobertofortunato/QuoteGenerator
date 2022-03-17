package com.example.quotegenerator.model

/**The model for the Kotlin data object
 * q = quote text
 * a = author
 * provider = surprisingly...the provider!*/

data class Quote (var id: Int, var q: String, var a: String, var provider: String)
