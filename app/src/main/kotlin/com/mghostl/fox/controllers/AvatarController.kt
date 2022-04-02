package com.mghostl.fox.controllers

import com.mghostl.fox.dto.UploadFileResponse
import com.mghostl.fox.resolvers.UserPhone
import com.mghostl.fox.services.FileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import javax.annotation.security.RolesAllowed
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/avatars")
@RolesAllowed(value = ["USER"])
class AvatarController(
    private val fileService: FileService
) {

    companion object: KLogging()

    @PostMapping
    @Operation(summary = "upload user's avatar", description = "png, jpeg")
    @ApiResponses(
        ApiResponse(description = "File was uploaded", responseCode = "200")
    )
    fun uploadPhoto(@UserPhone phone: String, @RequestPart file: MultipartFile): ResponseEntity<UploadFileResponse> {
        logger.info { "Upload file for user $phone" }
        return fileService.uploadFile(phone, file)
            .also { logger.info { "File was uploaded to $it" }}
            .let { UploadFileResponse(it) }
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("{fileName}")
    @Operation(summary = "getting user's avatar", description = "png, jpeg")
    @ApiResponses(
        ApiResponse(description = "File was downloaded", responseCode = "200"),
        ApiResponse(description = "User or File wasn't found", responseCode = "404")
    )
    fun downloadAvatar(@RequestParam userId: Int, @PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        logger.info { "Downloading file with filePath: $fileName" }
        val resource = fileService.downloadFile(userId, fileName)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(request.getContentType(resource)))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename +"\"")
            .body(resource)
    }

    private fun HttpServletRequest.getContentType(resource: Resource) = try{
        servletContext.getMimeType(resource.file.absolutePath)
    } catch (ex: IOException) {
        logger.info { "Couldn't determine classPath" }
        "application/octet-stream"
    } ?: "application/octet-stream"
}