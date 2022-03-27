package com.mghostl.fox.checkers

import com.mghostl.fox.model.User
import org.springframework.stereotype.Component

@Component
class PhoneUpdateChecker: UpdateChecker<User>() {
    override fun notValid(oldValue: User, newValue: User) = !oldValue.isSubmittedTrainer && newValue.phone != oldValue.phone

    override fun getExceptionMessage() = "Only trainer can update phone"
}