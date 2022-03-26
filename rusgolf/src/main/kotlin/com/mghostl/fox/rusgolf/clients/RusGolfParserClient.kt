package com.mghostl.fox.rusgolf.clients

import com.mghostl.fox.rusgolf.model.RusGolfUserDTO
import com.mghostl.fox.rusgolf.parsers.RusGolfParser
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class RusGolfParserClient(
    private val rusGolfParser: RusGolfParser
): RusGolfClient {

    companion object: KLogging()

    override fun getUserData(): Set<RusGolfUserDTO> {
        var users: Set<RusGolfUserDTO>
        val result = mutableListOf<RusGolfUserDTO>()
        var pageNum = 1
        do {
            users = rusGolfParser.parse(pageNum).also { result.addAll(it) }
            pageNum++
        } while (users.isNotEmpty())
        return result.toSet()
    }
}