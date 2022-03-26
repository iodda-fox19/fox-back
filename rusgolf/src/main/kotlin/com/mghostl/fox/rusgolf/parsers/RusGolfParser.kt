package com.mghostl.fox.rusgolf.parsers

import com.mghostl.fox.rusgolf.model.RusGolfUserDTO

interface RusGolfParser {
    fun parse(pageNum: Int): Set<RusGolfUserDTO>
}