package com.team8.tsuinskips.domain.useCase

import com.team8.tsuinskips.data.repository.UserRepository
import com.team8.tsuinskips.domain.User
import com.team8.tsuinskips.domain.UserInterface

class GetProfileUseCase(private var userInterface: UserInterface = UserRepository) {
    suspend operator fun invoke(): User {
        return userInterface.getProfile()
    }
}