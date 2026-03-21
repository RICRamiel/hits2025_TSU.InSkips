package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.data.repository.UserRepository
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.domain.UserInterface
import com.team8.tsuinskips.domain.UserLogin

class LoginUseCase(private var userInterface: UserInterface = UserRepository) {
    suspend operator fun invoke(userLogin: UserLogin): Token {
        return userInterface.login(userLogin)
    }
}