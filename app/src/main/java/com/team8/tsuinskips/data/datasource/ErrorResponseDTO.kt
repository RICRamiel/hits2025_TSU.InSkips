package com.team8.tsuinskips.data.datasource

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDTO(val errors: List<String>)
