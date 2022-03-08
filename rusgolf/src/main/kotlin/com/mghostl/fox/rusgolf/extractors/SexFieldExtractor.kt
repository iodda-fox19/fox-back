package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.model.Sex
import com.mghostl.fox.rusgolf.model.FieldType
import org.springframework.stereotype.Component

@Component
class SexFieldExtractor : FieldExtractor<Sex>() {
    override fun getFieldType() = FieldType.SEX

    override fun List<String>.getValue(index: Int) = get(index)
        .let {
            when (it.trim()) {
                "Муж." -> Sex.MALE
                "Жен." -> Sex.FEMALE
                else -> throw IllegalArgumentException("There is no such sex $it")
            }
        }
}