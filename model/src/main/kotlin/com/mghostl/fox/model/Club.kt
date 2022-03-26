package com.mghostl.fox.model

import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "`Clubs`")
data class Club(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clubSequenceGenerator")
    @SequenceGenerator(name = "clubSequenceGenerator", sequenceName = "`Clubs_id_seq`", allocationSize = 1, initialValue = 1000)
    @Column(name = "id")
    var id: Int? = null,

    @Column(name = "name")
    @NotNull
    var name: String? = null,

    @Column(name = "`gpsLat`")
    var gpsLat: Float? = null,

    @Column(name = "`gpsLon`")
    var gpsLon: Float? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "holes")
    @NotNull
    var holes: Int? = null,

    @Column(name = "`createdAt`")
    @CreationTimestamp
    var createdAt: ZonedDateTime? = null,

    @Column(name = "`updatedAt`")
    @UpdateTimestamp
    var updatedAt: ZonedDateTime? = null,

    var address: String? = null,

    var phone1: String? = null,

    var phone2: String? = null,

    var site: String? = null,

    var ordering: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "`countryId`", nullable = false, unique = false)
    var country: Country? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "`cityId`", nullable = false, unique = false)
    var city: City,

    @Column(name = "`fieldRate`")
    var fieldRate: Float? = null,

    @Column(name = "`infraRate`")
    var infraRate: Float? = null,

    @Column(name = "`serviceRate`")
    var serviceRate: Float? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Club

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , gpsLat = $gpsLat , gpsLon = $gpsLon , description = $description , holes = $holes , createdAt = $createdAt , updatedAt = $updatedAt , address = $address , phone1 = $phone1 , phone2 = $phone2 , site = $site , ordering = $ordering , fieldRate = $fieldRate , infraRate = $infraRate , serviceRate = $serviceRate )"
    }
}
