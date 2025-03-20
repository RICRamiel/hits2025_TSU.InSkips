package com.team8.tsuinskips.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.tsuinskips.common.application.InSkipsApplication
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.domain.useCase.GetProfileUseCase
import com.team8.tsuinskips.domain.useCase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartViewModel(
    private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase(),
) : ViewModel() {
    private val _isLogged = MutableStateFlow(false)
    val isLogged = _isLogged.asStateFlow()
    fun checkMemory() {
        viewModelScope.launch {
            val token = InSkipsApplication.getApp().appSharedPref.getString("token", "")
            if (token != "") {
                RetrofitApi.updateToken(Token(token!!))
                viewModelScope.launch {
                    if (getProfileUseCase().id != "") {
                        _isLogged.value = true
                    } else {

                    }
                }.join()
            }
        }
    }
}