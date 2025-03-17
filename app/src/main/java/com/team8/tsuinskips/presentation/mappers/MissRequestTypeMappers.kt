package com.team8.tsuinskips.presentation.mappers

import com.team8.tsuinskips.data.datasource.MissRequestType

fun MissRequestType.toRuString(): String {
    return when(this){
        MissRequestType.SICK -> "болезнь"
        MissRequestType.FAMILY -> "семейная"
        MissRequestType.EVENT_TRIP -> "поездка"
    }
}