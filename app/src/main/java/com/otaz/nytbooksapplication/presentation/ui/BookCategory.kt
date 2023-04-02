package com.otaz.nytbooksapplication.presentation.ui

import com.otaz.nytbooksapplication.presentation.ui.BookCategory.*

enum class BookCategory(val value: String){
    GET_HARDCOVER_FICTION("Hardcover Fiction"),
    GET_HARDCOVER_NONFICTION("Hardcover Nonfiction"),
    GET_HARDCOVER_ADVICE("Hardcover Advice"),
    GET_MANGA("Manga"),
    GET_CHAPTER_BOOKS("Chapter Books"),
    GET_CHILDRENS_MIDDLE_GRADE("Childrens Middle Grade"),
    GET_SERIES_BOOKS("Series Books"),
    GET_YOUNG_ADULT("Young Adult"),
    GET_ANIMALS("Animals"),
    GET_AUDIO_FICTION("Audio Fiction"),
    GET_AUDIO_NONFICTION("Audio Nonfiction"),
    GET_BUSINESS_BOOKS("Business Books"),
    GET_CELEBRITIES("Celebrities"),
    GET_CRIME_AND_PUNISHMENT("Crime and Punishment"),
    GET_EDUCATION("Education"),
    GET_ESPIONAGE("Espionage"),
    GET_EXPEDITIONS_DISASTERS_AND_ADVENTURES("Expeditions Disasters and Adventures"),
    GET_FASHION_MANNERS_AND_CUSTOMS("Fashion Manners and Customs"),
    GET_FOOD_AND_FITNESS("Food and Fitness"),
    GET_GAMES_AND_ACTIVITIES("Games and Activities"),
    GET_GRAPHICS_BOOKS_AND_MANGA("Graphic Books and Manga"),
    GET_HEALTH("Health"),
    GET_HUMOR("Humor"),
    GET_INDIGENOUS_AMERICANS("Indigenous Americans"),
    GET_RELATIONSHIPS("Relationships"),
    GET_MASS_MARKET_MONTHLY("Mass Market Monthly"),
    GET_FAMILY("Family"),
    GET_RACE_AND_CIVIL_RIGHTS("Race and Civil Rights"),
    GET_RELIGION_SPIRITUALITY_AND_FAITH("Religion Spirituality and Faith"),
    GET_SCIENCE("Science"),
    GET_SPORTS("Sports"),
    GET_TRAVEL("Travel"),
}

fun getAllBookCategories(): List<BookCategory>{
    return listOf(
        GET_HARDCOVER_FICTION,
        GET_HARDCOVER_NONFICTION,
        GET_HARDCOVER_ADVICE,
        GET_MANGA,
        GET_CHAPTER_BOOKS,
        GET_CHILDRENS_MIDDLE_GRADE,
        GET_SERIES_BOOKS,
        GET_YOUNG_ADULT,
        GET_ANIMALS,
        GET_AUDIO_FICTION,
        GET_AUDIO_NONFICTION,
        GET_BUSINESS_BOOKS,
        GET_CELEBRITIES,
        GET_CRIME_AND_PUNISHMENT,
        GET_EDUCATION,
        GET_ESPIONAGE,
        GET_EXPEDITIONS_DISASTERS_AND_ADVENTURES,
        GET_FASHION_MANNERS_AND_CUSTOMS,
        GET_FOOD_AND_FITNESS,
        GET_GAMES_AND_ACTIVITIES,
        GET_GRAPHICS_BOOKS_AND_MANGA,
        GET_HEALTH,
        GET_HUMOR,
        GET_INDIGENOUS_AMERICANS,
        GET_RELATIONSHIPS,
        GET_MASS_MARKET_MONTHLY,
        GET_FAMILY,
        GET_RACE_AND_CIVIL_RIGHTS,
        GET_RELIGION_SPIRITUALITY_AND_FAITH,
        GET_SCIENCE,
        GET_SPORTS,
        GET_TRAVEL
    )
}

fun getBookCategory(value: String): BookCategory? {
    val map = values().associateBy(BookCategory::value)
    return map[value]
}