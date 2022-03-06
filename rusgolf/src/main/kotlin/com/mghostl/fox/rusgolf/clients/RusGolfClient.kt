package com.mghostl.fox.rusgolf.clients

import com.mghostl.fox.rusgolf.model.UserDTO

interface RusGolfClient {
    fun getUserData(): Set<UserDTO>
}