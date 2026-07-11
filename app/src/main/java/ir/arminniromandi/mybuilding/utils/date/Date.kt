package ir.arminniromandi.mybuilding.utils.date

import javax.inject.Inject
import javax.inject.Singleton

class Date @Inject constructor() {
     fun getDate(): String {
        val date = PersianDate()
        val currentDate = "${date.strWeekDay}  ${date.day}   ${date.strMonth}"
        return currentDate

    }

    fun getDateForCalculate():String{
        val date = PersianDate()
        val currentDate = "${date.year} ${date.strMonth}"
        return currentDate

    }
}