package com.mghostl.fox.model

import com.mghostl.fox.converters.AvatarConverter
import com.mghostl.fox.converters.TimeConverter
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "`Games`")
data class Game(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameSequenceGenerator")
    @SequenceGenerator(name = "gameSequenceGenerator", sequenceName = "`Games_id_seq`", allocationSize = 1)
    @Column(name = "id")
    var id: Int? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "game_user",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "game_id")]
    )
    var users: MutableSet<User> = mutableSetOf(),

    @Column(name = "`clubId`")
    var clubId: Int? = null,

    @Column(name = "`countryId`")
    var countryId: Int? = null,

    var date: LocalDate? = null,

    var holes: Int? = null,

    @Column(name = "`gamersCount`")
    var gamersCount: Int? = null,

    var reserved: Boolean? = null,

    @Column(name = "`memberPrice`")
    var memberPrice: BigDecimal? = null,

    @Column(name = "`guestPrice`")
    var guestPrice: BigDecimal? = null,

    @Column(name = "`createdAt`")
    @CreationTimestamp
    var createdAt: ZonedDateTime? = null,

    @Column(name = "`updatedAt`")
    @UpdateTimestamp
    var updatedAt: ZonedDateTime? = null,

    @Convert(converter = TimeConverter::class)
    var time: LocalTime? = null,

    var description: String? = null,

    var name: String? = null,

    @Column(name = "`onlyMembers`")
    var onlyMembers: Boolean? = null,

    @Column(name = "`handicap_min`")
    var handicapMin: Float? = null,

    @Column(name = "`handicap_max`")
    var handicapMax: Float? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Game

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , clubId = $clubId , date = $date , holes = $holes , gamersCount = $gamersCount , reserved = $reserved , memberPrice = $memberPrice , guestPrice = $guestPrice , createdAt = $createdAt , updatedAt = $updatedAt , time = $time , description = $description , name = $name , onlyMembers = $onlyMembers )"
    }
}