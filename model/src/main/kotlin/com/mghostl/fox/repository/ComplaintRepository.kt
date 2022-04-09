package com.mghostl.fox.repository

import com.mghostl.fox.model.Complaint
import org.springframework.data.jpa.repository.JpaRepository

interface ComplaintRepository: JpaRepository<Complaint, Int>