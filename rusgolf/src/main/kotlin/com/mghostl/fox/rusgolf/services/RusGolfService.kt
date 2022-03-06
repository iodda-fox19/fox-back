package com.mghostl.fox.rusgolf.services

import com.mghostl.fox.rusgolf.model.UserDTO

interface RusGolfService {
    fun getUsersData(): Set<UserDTO>
}