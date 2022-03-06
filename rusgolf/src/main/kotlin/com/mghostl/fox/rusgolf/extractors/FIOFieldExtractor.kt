package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.rusgolf.model.FieldType
import org.springframework.stereotype.Component

@Component
class FIOFieldExtractor: FieldExtractor<String>() {

    override fun getFieldType() = FieldType.FIO

    override fun List<String>.getValue(index: Int) = get(index)
}