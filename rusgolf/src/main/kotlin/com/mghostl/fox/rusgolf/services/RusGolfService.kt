package com.mghostl.fox.rusgolf.services

import com.mghostl.fox.rusgolf.model.RusGolfUserDTO

interface RusGolfService {
    fun getUsersData(): Set<RusGolfUserDTO>
}