package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.data.repository.RequestRepository
import com.team8.tsuinskips.domain.RequestInterface
import com.team8.tsuinskips.domain.RequestList

class GetRequestsUseCase(private val requestInterface: RequestInterface = RequestRepository) {
    suspend operator fun invoke(): RequestList {
        return requestInterface.getRequests()
    }
}