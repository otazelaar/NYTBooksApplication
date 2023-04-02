package com.otaz.nytbooksapplication.network.fake

data class RanksHistory(
    val asterisk: Int,
    val bestsellers_date: String,
    val dagger: Int,
    val display_name: String,
    val list_name: String,
    val primary_isbn10: String,
    val primary_isbn13: String,
    val published_date: String,
    val rank: Int,
    val rank_last_week: Int,
    val weeks_on_list: Int
)