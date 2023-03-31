package com.otaz.nytbooksapplication.network.mappers

import com.otaz.nytbooksapplication.domain.DomainMapper
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.network.model.BookDto

class BookDtoMapper : DomainMapper<BookDto, Book> {

    override fun mapToDomainModel(model: BookDto): Book {
        return Book(
            amazon_product_url = model.amazon_product_url,
            author = model.author,
            book_image = model.book_image,
            book_image_height = model.book_image_height,
            book_image_width = model.book_image_width,
            book_review_link = model.book_review_link,
            book_uri = model.book_uri,
            description = model.description,
            publisher = model.publisher,
            rank = model.rank,
            rank_last_week = model.rank_last_week,
            title = model.title,
            weeks_on_list = model.weeks_on_list,
        )
    }

    override fun mapToNetworkModel(domainModel: Book): BookDto {
        return BookDto(
            amazon_product_url = domainModel.amazon_product_url,
            author = domainModel.author,
            book_image = domainModel.book_image,
            book_image_height = domainModel.book_image_height,
            book_image_width = domainModel.book_image_width,
            book_review_link = domainModel.book_review_link,
            book_uri = domainModel.book_uri,
            description = domainModel.description,
            publisher = domainModel.publisher,
            rank = domainModel.rank,
            rank_last_week = domainModel.rank_last_week,
            title = domainModel.title,
            weeks_on_list = domainModel.weeks_on_list,
        )
    }

    fun toDomainList(initial: List<BookDto>): List<Book>{
        return initial.map { mapToDomainModel(it) }
    }
}