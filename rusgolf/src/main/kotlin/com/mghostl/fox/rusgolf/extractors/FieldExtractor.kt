package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.rusgolf.model.FieldType
import mu.KLogging

abstract class FieldExtractor<T> {

    companion object: KLogging()

    fun extract(data: List<String>, headers: List<String>) = try {
        data.getValue(headers.getIndex())
    } catch (e: Exception) {
        val error = "Couldn't extract value from $data with headers $headers"
        logger.error(e) { error }
        throw IllegalArgumentException(error, e)
    }

    private fun List<String>.getIndex() = indexOf(getHeader())

    private fun getHeader() = getFieldType().header

    abstract fun getFieldType(): FieldType

    protected abstract fun List<String>.getValue(index: Int): T
}