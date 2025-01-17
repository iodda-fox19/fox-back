package com.mghostl.fox.model

import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "`Sms`")
data class Sms (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "smsSequenceGenerator")
    @SequenceGenerator(name = "smsSequenceGenerator", sequenceName = "`Sms_id_seq`", allocationSize = 1, initialValue = 1000)
    @Column(name = "id")
    val id: Int? = null,

    var phone: String? = null,

    var code: String? = null,

    @Column(name = "`userId`")
    var userId: Int? = null,

    @Column(name = "`sendedCode`")
    var sendedCode: String? = null,

    @CreationTimestamp
    @Column(name = "`createdAt`")
    var createdAt: LocalDateTime? = null,

    var attempts: Int = 0,

    @UpdateTimestamp
    @Column(name = "`updatedAt`")
    var updatedAt: LocalDateTime? = null

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Sms

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , phone = $phone , code = $code , userId = $userId , sendedCode = $sendedCode , createdAt = $createdAt , updatedAt = $updatedAt )"
    }
}