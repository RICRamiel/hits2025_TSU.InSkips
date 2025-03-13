package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.data.repository.UserRepository
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.domain.UserInterface
import com.team8.tsuinskips.domain.UserRegister

class RegisterUseCase(private var userInterface: UserInterface = UserRepository) {
    suspend operator fun invoke(userRegister: UserRegister): Token {
        return userInterface.register(userRegister)
    }
}