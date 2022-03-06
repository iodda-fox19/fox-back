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
import javax.validation.constraints.NotNull

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null,

    @NotNull
    var name: String? = null,

    var phone: String? = null,

    var email: String? = null,

    var password: String? = null,

    @NotNull
    var handicap: Float? = null,

    @CreationTimestamp
    var createdAt: ZonedDateTime? = null,

    @UpdateTimestamp
    var updatedAt: ZonedDateTime? = null,

    var isAdmin: Boolean = false,

    var isReferee: Boolean = false,

    var isGamer: Boolean = false,

    var isTrainer: Boolean = false,
    
    var about: String? = null,

    @Column(name = "golfRegistryIdRU")
    var golfRegistryIdRU: String? = null,

    var handicapUpdateAt: ZonedDateTime = ZonedDateTime.now(),

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