package com.team8.tsuinskips.data.mapper

import androidx.compose.runtime.rememberUpdatedState
import com.team8.tsuinskips.data.datasource.TokenResponseDTO
import com.team8.tsuinskips.domain.Token

object TokenMapper {
    fun map(dto: TokenResponseDTO): Token {
        return with(dto) {
            Token(token)
        }
    }

    fun map(dto: Token): TokenResponseDTO {
        return with(dto) {
            TokenResponseDTO(key)
        }
    }
}