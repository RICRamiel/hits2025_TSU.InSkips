package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.domain.UserInterface

class LogoutUseCase(private var userInterface: UserInterface) {
    suspend operator fun invoke() {
        userInterface.logout()
    }
}