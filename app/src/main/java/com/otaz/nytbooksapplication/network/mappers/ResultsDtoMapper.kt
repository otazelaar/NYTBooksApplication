//package com.otaz.nytbooksapplication.network.mappers
//
//import com.otaz.nytbooksapplication.domain.DomainMapper
//import com.otaz.nytbooksapplication.domain.model.Results
//import com.otaz.nytbooksapplication.network.model.ResultsDto
//
//class ResultsDtoMapper : DomainMapper<ResultsDto, Results> {
//
//    override fun mapToDomainModel(model: ResultsDto): Results {
//        return Results(
//            bestsellers_date = model.bestsellers_date,
//            books = model.books,
//            display_name = model.display_name,
//            list_name = model.list_name,
//            list_name_encoded = model.list_name_encoded,
//            next_published_date = model.next_published_date,
//            normal_list_ends_at = model.normal_list_ends_at,
//            previous_published_date = model.previous_published_date,
//            published_date = model.published_date,
//            published_date_description = model.published_date_description,
//            updated = model.updated
//        )
//    }
//
//    override fun mapToNetworkModel(domainModel: Results): ResultsDto {
//        return ResultsDto(
//            bestsellers_date = domainModel.bestsellers_date,
//            books = domainModel.books,
//            display_name = domainModel.display_name,
//            list_name = domainModel.list_name,
//            list_name_encoded = domainModel.list_name_encoded,
//            next_published_date = domainModel.next_published_date,
//            normal_list_ends_at = domainModel.normal_list_ends_at,
//            previous_published_date = domainModel.previous_published_date,
//            published_date = domainModel.published_date,
//            published_date_description = domainModel.published_date_description,
//            updated = domainModel.updated
//        )
//    }
//
//    fun toDomainList(initial: List<ResultsDto>): List<Results>{
//        return initial.map { mapToDomainModel(it) }
//    }
//}