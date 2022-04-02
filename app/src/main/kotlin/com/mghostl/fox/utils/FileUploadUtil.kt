package com.mghostl.fox.utils

import org.apache.commons.io.FilenameUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun saveFile(uploadDir: String, fileName: String, multipartFile: MultipartFile) {
    val uploadPath = Paths.get(uploadDir)
    if(!Files.exists(uploadPath)) Files.createDirectories(uploadPath)
    val name = "$fileName.${FilenameUtils.getExtension(multipartFile.originalFilename)}"

    multipartFile.inputStream.use {
        val filePath = uploadPath.resolve(name)
        Files.copy(it, filePath, StandardCopyOption.REPLACE_EXISTING)
    }

}