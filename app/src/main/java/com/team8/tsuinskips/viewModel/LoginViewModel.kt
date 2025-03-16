package com.team8.tsuinskips.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.domain.UserLogin
import com.team8.tsuinskips.domain.useCase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val loginUseCase: LoginUseCase = LoginUseCase()
    private val _isLogged = MutableStateFlow(false)
    val isLogged = _isLogged.asStateFlow()
    private var _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    fun login(login: UserLogin) {
        viewModelScope.launch {
            val resp = loginUseCase(login)
            RetrofitApi.updateToken(resp)
            _isLogged.update { va ->
                !va
            }
        }
    }
}