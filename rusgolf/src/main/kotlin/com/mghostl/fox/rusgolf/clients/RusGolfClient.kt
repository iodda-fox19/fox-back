package com.mghostl.fox.rusgolf.clients

import com.mghostl.fox.rusgolf.model.RusGolfUserDTO

interface RusGolfClient {
    fun getUserData(): Set<RusGolfUserDTO>
}