package com.hagos.data.mapper

import com.hagos.data.local.CompanyInfoEntity
import com.hagos.data.local.CompanyListingEntity
import com.hagos.data.remote.dto.CompanyInfoDto
import com.hagos.domain.model.CompanyInfo
import com.hagos.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing = CompanyListing(name, symbol, exchange)

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity = CompanyListingEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
)

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}

fun CompanyInfoDto.toCompanyInfoEntity(): CompanyInfoEntity {
    return CompanyInfoEntity(
        symbol = symbol ?: "",
        description=description,
        name=name,
        country=country,
        industry=industry
    )
}

fun CompanyInfoEntity.toCompanyInfo(): CompanyInfo{
    return CompanyInfo(
        symbol,
        description ?: "",
        name ?: "",
        country?: "",
        industry ?: ""
    )
}