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
import javax.validation.constraints.NotNull

@Entity
@Table(name = "`Users`")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "`Users_id_seq`", allocationSize = 1, initialValue = 1000)
    @Column(name = "id")
    var id: Int? = null,

    @Column(name = "name")
    @NotNull
    var name: String? = null,

    @Column(name = "phone")
    var phone: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "handicap")
    @NotNull
    var handicap: Float? = null,

    @Column(name = "`createdAt`")
    @CreationTimestamp
    var createdAt: ZonedDateTime? = null,

    @Column(name = "`updatedAt`")
    @UpdateTimestamp
    var updatedAt: ZonedDateTime? = null,

    @Column(name = "`isAdmin`")
    var isAdmin: Boolean = false,

    @Column(name = "`isReferee`")
    var isReferee: Boolean = false,

    @Column(name = "`isGamer`")
    var isGamer: Boolean = false,

    @Column(name = "`isTrainer`")
    var isTrainer: Boolean = false,

    @Column(name = "about")
    var about: String? = null,

    @Column(name = "`golfRegistryIdRU`")
    var golfRegistryIdRU: String? = null,

    @Column(name = "`handicapUpdateAt`")
    var handicapUpdateAt: ZonedDateTime? = null,

    @Column(name = "`lastName`")
    var lastName: String? = null
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , phone = $phone , email = $email , password = $password , handicap = $handicap , createdAt = $createdAt , updatedAt = $updatedAt , isAdmin = $isAdmin , isReferee = $isReferee , isGamer = $isGamer , isTrainer = $isTrainer , about = $about , golfRegistryIdRU = $golfRegistryIdRU , handicapUpdateAt = $handicapUpdateAt , lastName = $lastName )"
    }
}