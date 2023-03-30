package com.otaz.nytbooksapplication.network.model

import com.otaz.nytbooksapplication.domain.DomainMapper
import com.otaz.nytbooksapplication.domain.model.Book

class BookDtoMapper : DomainMapper<BookDto, Book> {

    override fun mapToDomainModel(model: BookDto): Book {
        return Book(
            id = model.id,
        )
    }

    override fun mapFromDomainModel(domainModel: Book): BookDto {
        return BookDto(
            id = domainModel.id,
        )
    }

    fun toDomainList(initial: List<BookDto>): List<Book>{
        return initial.map { mapToDomainModel(it) }
    }
}