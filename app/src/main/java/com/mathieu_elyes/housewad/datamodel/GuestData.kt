package com.mathieu_elyes.housewad.datamodel

data class GuestData(
    val userLogin: String, //le JSON a une propriété houseId donc on prend le meme nom de propriété
    val owner: Int
)