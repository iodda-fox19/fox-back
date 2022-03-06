package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.rusgolf.model.FieldType
import org.springframework.stereotype.Component

@Component
class HandicapFieldExtractor : FieldExtractor<Float>() {

    override fun getFieldType() = FieldType.HANDICAP

    override fun List<String>.getValue(index: Int) =
        get(index).let {
            if (it == "-") 0f
            else it.replace(",", ".").toFloat()
        }
}
