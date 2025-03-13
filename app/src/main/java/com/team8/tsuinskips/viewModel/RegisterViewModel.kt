package com.team8.tsuinskips.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.domain.UserRegister
import com.team8.tsuinskips.domain.useCase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val registerUseCase: RegisterUseCase = RegisterUseCase()
    private val _isRegistered = MutableStateFlow(false)
    val isRegistered = _isRegistered.asStateFlow()
    private var _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    fun register(register: UserRegister) {
        viewModelScope.launch {
            val resp = registerUseCase(register)
            RetrofitApi.updateToken(resp)
        }
    }
}