package com.mghostl.fox.model

import org.hibernate.Hibernate
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Table(name = "user_rus_golf")
@Entity
data class UserRusGolf (
    @Id
    @Column(name = "golf_registry_id_ru")
    var golfRegistryIdRU: String? = null,

    @NotNull
    var handicap: Float? = null,
    
    @Column(name = "handicap_update_at")
    var handicapUpdateAt: LocalDate? = null,

    @UpdateTimestamp
    @Column(name = "update_date_time")
    var updateDateTime: LocalDateTime = LocalDateTime.now(),

    @Enumerated(value = EnumType.STRING)
    var sex: Sex? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "middle_name")
    var middleName: String? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserRusGolf

        return golfRegistryIdRU != null && golfRegistryIdRU == other.golfRegistryIdRU
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "UserRusGolf(golfRegistryIdRU=$golfRegistryIdRU, handicap=$handicap, handicapUpdateAt=$handicapUpdateAt, updateDateTime=$updateDateTime, sex=$sex, firstName=$firstName, lastName=$lastName, middleName=$middleName)"
    }
}