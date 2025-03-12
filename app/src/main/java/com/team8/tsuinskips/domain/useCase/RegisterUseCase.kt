package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.domain.UserInterface
import com.team8.tsuinskips.domain.UserRegister

class RegisterUseCase(private var userInterface: UserInterface) {
    suspend operator fun invoke(userRegister: UserRegister){
        userInterface.register(userRegister)
    }
}