package com.mghostl.fox.services

import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.mappers.ComplaintMapper
import com.mghostl.fox.repository.ComplaintRepository
import org.springframework.stereotype.Service

@Service
class ComplaintServiceImpl(
    private val userService: UserService,
    private val complaintRepository: ComplaintRepository,
    private val complaintMapper: ComplaintMapper
): ComplaintService {
    override fun create(phone: String, complaintDTO: ComplaintDTO): ComplaintDTO {
        val userId = userService.findByPhone(phone).id!!
        checkIndictedUserExists(complaintDTO)
        return complaintMapper.map(complaintDTO)
            .apply { this.userId = userId }
            .also { complaintRepository.save(it) }
            .let { complaintMapper.map(it) }
    }

    private fun checkIndictedUserExists(complaintDTO: ComplaintDTO) {
        userService.findById(complaintDTO.indictedUserId!!)
    }
}