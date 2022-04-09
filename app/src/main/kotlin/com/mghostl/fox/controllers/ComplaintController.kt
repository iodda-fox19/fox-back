package com.mghostl.fox.controllers

import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.resolvers.UserPhone
import com.mghostl.fox.services.ComplaintService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("api/complaints")
@RolesAllowed(value = ["USER"])
class ComplaintController(
    private val complaintService: ComplaintService
) {

    companion object: KLogging()

    @PostMapping
    @Operation(summary = "Create complaint to user")
    @ApiResponses(
        ApiResponse(description = "Complaint was created", responseCode = "200"),
        ApiResponse(description = "User doesn't exists", responseCode = "404")
    )
    fun createComplaint(@UserPhone phone: String, @Valid @RequestBody complaintDTO: ComplaintDTO): ResponseEntity<ComplaintDTO> {
        logger.info { "User $phone create complaint $complaintDTO" }
        return complaintService.create(phone, complaintDTO)
            .let { ResponseEntity.ok(it) }
    }
}