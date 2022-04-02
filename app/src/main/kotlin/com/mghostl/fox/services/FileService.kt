package com.mghostl.fox.services

import com.mghostl.fox.model.Avatar
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun uploadFile(phone: String, file: MultipartFile): Avatar
    fun downloadFile(userId: Int, fileName: String): Resource
}