package com.mghostl.fox.rusgolf.clients

import com.mghostl.fox.rusgolf.model.UserDTO
import com.mghostl.fox.rusgolf.parsers.RusGolfParser
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class RusGolfParserClient(
    private val rusGolfParser: RusGolfParser
): RusGolfClient {

    companion object: KLogging()

    override fun getUserData(): Set<UserDTO> {
        var users: Set<UserDTO>
        val result = mutableListOf<UserDTO>()
        var pageNum = 1
        do {
            users = rusGolfParser.parse(pageNum).also { result.addAll(it) }
            pageNum++
        } while (users.isNotEmpty())
        return result.toSet()
    }
}