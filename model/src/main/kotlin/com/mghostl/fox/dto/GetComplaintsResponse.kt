package com.mghostl.fox.dto

class GetComplaintsResponse(data: Set<ComplaintDTO>, count: Long) : GetPageableResponse<ComplaintDTO>(data, count)