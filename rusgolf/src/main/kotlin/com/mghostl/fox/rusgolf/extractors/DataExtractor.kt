package com.mghostl.fox.rusgolf.extractors

interface DataExtractor<T> {
    fun extract(data: List<String>, headers: List<String>): T
}