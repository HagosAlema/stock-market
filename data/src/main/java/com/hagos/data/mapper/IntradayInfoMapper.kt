package com.hagos.data.mapper

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

