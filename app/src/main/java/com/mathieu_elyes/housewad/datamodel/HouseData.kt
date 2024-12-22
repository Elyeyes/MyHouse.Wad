package com.mathieu_elyes.housewad.datamodel

data class HouseData(
    val houseId: Int, //le JSON a une propriété houseId donc on prend le meme nom de propriété
    val owner: Boolean
    )
