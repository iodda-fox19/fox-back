package com.mghostl.fox.checkers

import com.mghostl.fox.model.User
import org.springframework.stereotype.Component

@Component
class GamerStatusUpdateChecker: UpdateChecker<User>() {

    override fun notValid(oldValue: User, newValue: User) = newValue.isGamer != oldValue.isGamer && !(oldValue.isSubmittedAdministrator || oldValue.isSubmittedTrainer)
    override fun getExceptionMessage() = "Couldn't change gamer status without submitted other statuses"
}