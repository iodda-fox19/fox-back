package com.mghostl.fox.rusgolf.parsers

import com.mghostl.fox.rusgolf.model.UserDTO

interface RusGolfParser {
    fun parse(pageNum: Int): Set<UserDTO>
}