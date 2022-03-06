package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.rusgolf.model.FieldType
import org.springframework.stereotype.Component

@Component
class IdFieldExtractor: FieldExtractor<String>() {

    override fun getFieldType() = FieldType.ID

    override fun List<String>.getValue(index: Int) = get(index)
}