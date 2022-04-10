package com.mghostl.fox.controllers

import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.dto.GetComplaintsResponse
import com.mghostl.fox.resolvers.UserPhone
import com.mghostl.fox.services.ComplaintService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("api/complaints")
class ComplaintController(
    private val complaintService: ComplaintService
) {

    companion object: KLogging()

    @RolesAllowed(value = ["USER"])
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

    @GetMapping
    @RolesAllowed(value = ["ADMIN"])
    @Operation(summary = "Get complaints")
    @ApiResponses(
        ApiResponse(description = "successfully getting complaints", responseCode = "200")
    )
    fun getComplaints(@RequestParam(required = false, defaultValue = "10") limit: Int, @RequestParam(required = false, defaultValue = "0") offset: Int): ResponseEntity<GetComplaintsResponse> {
        logger.info { "Getting complaints with limit $limit and offset $offset" }
        return complaintService.get(limit, offset)
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping("{complaintId}")
    @RolesAllowed(value = ["ADMIN"])
    @Operation(summary = "Resolve complaint")
    @ApiResponses(
        ApiResponse(description = "Complaint was resolved", responseCode = "200"),
        ApiResponse(description = "Complaint was not found", responseCode = "404")
    )
    fun resolveComplaint(@PathVariable complaintId: Int): ResponseEntity<ComplaintDTO> {
        logger.info { "Resolve complaint with id $complaintId" }
        return complaintService.resolve(complaintId)
            .let { ResponseEntity.ok(it) }
    }
}