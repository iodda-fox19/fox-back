package com.mghostl.fox.services

import com.mghostl.fox.dto.ComplaintDTO

interface ComplaintService {
    fun create(phone: String, complaintDTO: ComplaintDTO): ComplaintDTO
}