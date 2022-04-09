package com.mghostl.fox.mappers

import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.model.Complaint
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface ComplaintMapper {
    fun map(complaint: Complaint): ComplaintDTO
    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "userId", ignore = true),
        Mapping(target = "updatedAt", ignore = true),
        Mapping(target = "createdAt", ignore = true),
    )
    fun map(complaintDTO: ComplaintDTO): Complaint
}