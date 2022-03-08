package com.mghostl.fox.model

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
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
    @NotNull
    var handicapUpdateAt: LocalDate? = null,

    @NotNull
    var fio: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserRusGolf

        return golfRegistryIdRU != null && golfRegistryIdRU == other.golfRegistryIdRU
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "UserRusGolf(golfRegistryIdRU=$golfRegistryIdRU, handicap=$handicap, handicapUpdateAt=$handicapUpdateAt, fio=$fio)"
    }
}