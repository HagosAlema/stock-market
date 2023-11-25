package com.hagos.data.mapper

import com.hagos.data.local.IntradayInfoEntity
import com.hagos.data.remote.dto.IntradayInfoDto
import com.hagos.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(localDateTime, close)
}

fun IntradayInfoDto.toIntradayInfoEntity(symbol: String): IntradayInfoEntity{
    return IntradayInfoEntity(symbol = symbol, timestamp = timestamp, close = close)
}

fun IntradayInfoEntity.toIntradayInfo(): IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(localDateTime, close)
}

fun IntradayInfo.toIntradayInfoEntity(symbol: String): IntradayInfoEntity{
    val localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val localDateTime = localDateTimeFormatter.format(date)
    return IntradayInfoEntity(symbol = symbol, timestamp = localDateTime, close = close)
}

fun LocalDateTime.toDate(): String {
    val localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return localDateTimeFormatter.format(this)
}


