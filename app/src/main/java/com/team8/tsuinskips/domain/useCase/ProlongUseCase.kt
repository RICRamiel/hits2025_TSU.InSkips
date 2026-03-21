package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.data.datasource.RequestProlongDTO
import com.team8.tsuinskips.data.repository.RequestRepository
import com.team8.tsuinskips.data.repository.UserRepository
import com.team8.tsuinskips.domain.RequestInterface
import com.team8.tsuinskips.domain.RequestProlong
import com.team8.tsuinskips.domain.UserInterface

class ProlongUseCase (private var requestInterface: RequestInterface = RequestRepository) {
    suspend operator fun invoke(id:String, date: RequestProlong) {
        requestInterface.prolongRequest(id,date)
    }
}