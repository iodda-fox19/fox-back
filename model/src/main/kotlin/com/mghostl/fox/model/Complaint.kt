package com.mghostl.fox.model

import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "`Complaints`")
data class Complaint(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaintsSequenceGenerator")
    @SequenceGenerator(name = "complaintsSequenceGenerator", sequenceName = "`Complaints_id_seq`", allocationSize = 1)
    @Column(name = "id")
    var id: Int? = null,

    @Column(name = "`userId`")
    var userId: Int? = null,

    @Column(name = "`indictedUserId`")
    var indictedUserId: Int? = null,

    var comment: String? = null,

    @Column(name = "`createdAt`")
    @CreationTimestamp
    var createdAt: ZonedDateTime? = null,

    @Column(name = "`updatedAt`")
    @UpdateTimestamp
    var updatedAt: ZonedDateTime? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Complaint

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , userId = $userId , indictedUserId = $indictedUserId , comment = $comment)"
    }
}