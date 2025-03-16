package com.team8.tsuinskips.data.mapper

import com.team8.tsuinskips.data.datasource.ConfirmationFileDTO
import com.team8.tsuinskips.domain.ConfirmationFile

object ConfirmationFileMapper {
    fun map(dto: ConfirmationFileDTO): ConfirmationFile {
        return with(dto) {
            ConfirmationFile(id, name, attachDate)
        }
    }

    fun map(dto: ConfirmationFile): ConfirmationFileDTO {
        return with(dto) {
            ConfirmationFileDTO(id, name, attachDate)
        }
    }
}