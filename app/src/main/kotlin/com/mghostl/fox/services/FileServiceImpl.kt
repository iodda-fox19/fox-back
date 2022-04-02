package com.mghostl.fox.services

import com.mghostl.fox.exceptions.FileStorageException
import com.mghostl.fox.model.Avatar
import com.mghostl.fox.utils.saveFile
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Paths
import java.util.UUID
import javax.transaction.Transactional

@Service
class FileServiceImpl(
   private val userService: UserService
): FileService {

    @Value("\${file.uploadDir}")
    private lateinit var uploadDir: String

    @Transactional
    override fun uploadFile(phone: String, file: MultipartFile): Avatar {
        val user = userService.findByPhone(phone)
        val path = generatePath()
        val fileName = file.originalFilename?.let { StringUtils.cleanPath(it) } ?: path
        if(fileName.contains("..")) {
            throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
        }
        user.avatar = Avatar(fileName, "/api/avatars/$path.${FilenameUtils.getExtension(fileName)}?userId=${user.id}")
        saveFile(getUploadDir(user.id!!), path, file)
        userService.updateUser(user, phone)
        return user.avatar!!
    }

    private fun getUploadDir(userId: Int) = "${uploadDir}/$userId"

    override fun downloadFile(userId: Int, fileName: String): Resource {
        val path = Paths.get(getUploadDir(userId)).resolve(fileName).normalize()
        val resource = UrlResource(path.toUri())
        return if(resource.exists())  resource
        else throw FileNotFoundException("File not found $fileName")
    }


    private fun generatePath() = UUID.randomUUID().toString()
}