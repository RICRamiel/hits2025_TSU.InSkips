package com.team8.tsuinskips.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.tsuinskips.domain.User
import com.team8.tsuinskips.domain.useCase.GetProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestsViewModel(private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase()) :
    ViewModel() {
    private var _profile = MutableStateFlow(User("", "", "", "", "", emptyList()))
    var profile = _profile.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val resp = getProfileUseCase()
            Log.i("USER", "${resp.id} ${resp.name} ${resp.email} ${resp.surname} ${resp.patronymic} ${resp.name} ")
            _profile.value = resp
        }
    }

    fun getSNP(): String {
        val name = profile.value.name
        val surname = profile.value.surname
        val patr = profile.value.patronymic
        Log.i("SNP", "$surname $name $patr")
        return "$surname $name $patr"
    }

    fun getEmail(): String {
        return profile.value.email
    }
}