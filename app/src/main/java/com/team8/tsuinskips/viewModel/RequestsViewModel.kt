package com.team8.tsuinskips.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.team8.tsuinskips.data.datasource.RequestProlongDTO
import com.team8.tsuinskips.domain.RequestList
import com.team8.tsuinskips.domain.RequestProlong
import com.team8.tsuinskips.domain.User
import com.team8.tsuinskips.domain.useCase.GetProfileUseCase
import com.team8.tsuinskips.domain.useCase.GetRequestsFilteredUseCase
import com.team8.tsuinskips.domain.useCase.GetRequestsUseCase
import com.team8.tsuinskips.domain.useCase.LogoutUseCase
import com.team8.tsuinskips.domain.useCase.ProlongUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class RequestsViewModel(
    private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase(),
    private val getRequestsUseCase: GetRequestsUseCase = GetRequestsUseCase(),
    private val getRequestsFilteredUseCase: GetRequestsFilteredUseCase = GetRequestsFilteredUseCase(),
    private val logoutUseCase: LogoutUseCase = LogoutUseCase(),
    private val prolongUseCase: ProlongUseCase = ProlongUseCase()
) : ViewModel() {
    private var _profile = MutableStateFlow(User("", "", "", "", "", emptyList(), ""))
    var profile = _profile.asStateFlow()
    private var _requests = MutableStateFlow(RequestList(emptyList()))
    var requests = _requests.asStateFlow()
    private var _filteredRequests = MutableStateFlow(RequestList(emptyList()))
    var filteredRequests = _filteredRequests.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val resp = getProfileUseCase()
            Log.i(
                "USER",
                "${resp.id} ${resp.name} ${resp.email} ${resp.surname} ${resp.patronymic} ${resp.name} "
            )
            _profile.value = resp
        }
    }

    fun getSNP(): String {
        val name = profile.value.name
        val surname = profile.value.surname
        val patr = if (profile.value.patronymic != null) {
            profile.value.patronymic
        } else {
            ""
        }
        Log.i("SNP", "$surname $name $patr")
        return "$surname $name $patr".trimEnd()
    }

    fun getEmail(): String {
        return profile.value.email
    }

    fun getRequestList() {
        viewModelScope.launch {
            val resp = getRequestsUseCase()
            _requests.value = resp
        }
    }

    fun logout() {
        viewModelScope.launch {
            val resp = logoutUseCase()
        }
    }

    fun getFilteredRequestList(
        group: String?,
        subgroups: List<String>?,
        surname: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ) {
        viewModelScope.launch {
            val resp = getRequestsFilteredUseCase(
                group, subgroups, surname, startDate, endDate
            )
            _filteredRequests.value = resp
        }
    }

    fun prolong(
        id: String,
        date: String
    ) {
        viewModelScope.launch {
            val resp = prolongUseCase(id, RequestProlong(date))
        }
    }
}