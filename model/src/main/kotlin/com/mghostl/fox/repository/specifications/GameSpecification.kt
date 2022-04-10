package com.mghostl.fox.repository.specifications

import com.mghostl.fox.model.Game
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class GameSpecification(
    private val searchCriteria: SearchCriteria
): Specification<Game> {

    override fun toPredicate(root: Root<Game>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        return if(searchCriteria.operation?.equals("<", ignoreCase = true) == true) {
            criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.key), searchCriteria.value.toString())
         } else if(searchCriteria.operation?.equals("<", ignoreCase = true) == true) {
            criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.key), searchCriteria.value.toString())
        } else if (searchCriteria.operation?.equals(":", ignoreCase = true) == true) {
            criteriaBuilder.like(root.get(searchCriteria.key), searchCriteria.value.toString())
        } else {
            if(searchCriteria.value is Boolean) {
                criteriaBuilder.equal(root.get<String>(searchCriteria.key), searchCriteria.value)
            } else {
                criteriaBuilder.equal(root.get<String>(searchCriteria.key), searchCriteria.value.toString())
            }
        }
    }
}

class EmptyGameSpecification: Specification<Game> {
    override fun toPredicate(root: Root<Game>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        return null
    }
}

data class SearchCriteria (
    val key: String? = null,
    val operation: String? = null,
    val value: Any? = null
)