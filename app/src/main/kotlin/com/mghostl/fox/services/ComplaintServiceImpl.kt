package com.mghostl.fox.services

import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.dto.GetComplaintsResponse
import com.mghostl.fox.mappers.ComplaintMapper
import com.mghostl.fox.repository.ComplaintRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ComplaintServiceImpl(
    private val userService: UserService,
    private val complaintRepository: ComplaintRepository,
    private val complaintMapper: ComplaintMapper
): ComplaintService {
    @Transactional
    override fun create(phone: String, complaintDTO: ComplaintDTO): ComplaintDTO {
        val userId = userService.findByPhone(phone).id!!
        checkIndictedUserExists(complaintDTO)
        return complaintMapper.map(complaintDTO)
            .apply { this.userId = userId }
            .also { complaintRepository.save(it) }
            .let { complaintMapper.map(it) }
    }

    @Transactional
    override fun get(limit: Int, offset: Int) = PageRequest.of(offset, limit)
        .let { complaintRepository.findByResolvedFalse(it) }
        .let { it.totalElements to  it.map { complaint -> complaintMapper.map(complaint) }}
        .let { GetComplaintsResponse(it.second.toSet(), it.first) }

    private fun checkIndictedUserExists(complaintDTO: ComplaintDTO) {
        userService.findById(complaintDTO.indictedUserId!!)
    }
}