package com.mghostl.fox.rusgolf.model

enum class FieldType(val header: String) {
    ID("№"),
    FIO("Фамилия, Имя, Отчество"),
    HANDICAP("HI"),
    UPDATE_DATE_HANDICAP("Дата обновления HCP"),
    SEX("Пол")
}