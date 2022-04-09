package com.mghostl.fox.services

import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.dto.GetComplaintsResponse

interface ComplaintService {
    fun create(phone: String, complaintDTO: ComplaintDTO): ComplaintDTO
    fun get(limit: Int, offset: Int): GetComplaintsResponse
}