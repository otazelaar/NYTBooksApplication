package com.otaz.nytbooksapplication.domain

interface DomainMapper <T, DomainModel>{

    fun mapToDomainModel(model: T): DomainModel

    fun mapToNetworkModel(domainModel: DomainModel): T
}