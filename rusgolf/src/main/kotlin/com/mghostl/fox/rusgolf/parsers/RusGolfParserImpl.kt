package com.mghostl.fox.rusgolf.parsers

import com.mghostl.fox.rusgolf.extractors.DataExtractor
import com.mghostl.fox.rusgolf.model.RusGolfUserDTO
import com.mghostl.fox.rusgolf.properties.RusGolfProperties
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

@Component
class RusGolfParserImpl(
    private val rusGolfProperties: RusGolfProperties,
    private val dataExtractor: DataExtractor<RusGolfUserDTO>
): RusGolfParser {

    // TODO make it working faster (parallel calculation) now approximate time = 3M 54S
    override fun parse(pageNum: Int): Set<RusGolfUserDTO> {
        val table = getTable(pageNum)
        val headers = getHeaders(table)
        return getData(table)
            .map { dataExtractor.extract(it, headers) }
            .toSet()
    }
    private fun getData(table: Elements) = table.select("tbody")
        .select("tr")
        .map { it.select("td").map { cell -> cell.text() } }

    private fun getTable(pageNum: Int): Elements {
        val document = Jsoup.connect(rusGolfProperties.host + pageNum)
            .userAgent("Mozilla")
            .timeout(15000)
            .referrer("http://google.com")
            .get()
        return document.select("table")
    }

    private fun getHeaders(table: Elements) = table.select("thead")
        .select("tr")
        .select("th")
        .map { it.text() }
}