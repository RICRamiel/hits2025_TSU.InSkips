package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.data.repository.RequestRepository
import com.team8.tsuinskips.domain.RequestInterface
import com.team8.tsuinskips.domain.RequestList
import java.time.LocalDate

class GetRequestsFilteredUseCase(
    private val requestInterface: RequestInterface = RequestRepository
) {
    suspend operator fun invoke(
        group: String?,
        subgroups: List<String>?,
        surname: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): RequestList {
        return requestInterface.getRequestsFiltered(group, subgroups, surname, startDate, endDate)
    }
}