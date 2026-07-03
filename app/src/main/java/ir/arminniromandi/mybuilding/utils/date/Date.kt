package ir.arminniromandi.mybuilding.utils.date

import javax.inject.Singleton

@Singleton
class Date {
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