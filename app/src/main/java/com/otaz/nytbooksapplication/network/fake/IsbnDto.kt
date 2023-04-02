//package com.otaz.nytbooksapplication.network.fake
//
//import com.google.gson.annotations.SerializedName
//import com.otaz.nytbooksapplication.domain.model_fake.Isbn
//
//data class IsbnDto(
//    @SerializedName("isbn13") var isbn13: String
//)
//
//fun IsbnDto.toIsbn(): Isbn {
//    return Isbn(
//        isbn13 = isbn13
//    )
//}