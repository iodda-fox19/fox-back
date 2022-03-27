package com.mghostl.fox.checkers

import com.mghostl.fox.exceptions.IllegalUpdateException

abstract class UpdateChecker<T> {
    fun check(oldValue: T, newValue: T) {
        if(notValid(oldValue, newValue)) {
            throw IllegalUpdateException(getExceptionMessage())
        }
    }

    protected abstract fun notValid(oldValue: T, newValue: T): Boolean

    protected abstract fun getExceptionMessage(): String
}