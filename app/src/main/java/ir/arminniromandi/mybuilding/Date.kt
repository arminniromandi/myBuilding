package ir.arminniromandi.mybuilding

import ir.amozeshgam.persiandate.PersianDate


class Date {
     fun getDate(): String {

        val date = PersianDate()

        val currentDate = "${date.strWeekDay} ${date.day}  ${date.strMonth}"

        return currentDate

    }
}