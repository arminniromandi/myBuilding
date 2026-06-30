package ir.arminniromandi.mybuilding.utils.date

import android.content.Context
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//کلاسی که بررسی میکند ایا در این ماه شارژی موجود است یا نه
class CheckDate(private var context: Context) {
    suspend fun getAndCheck(date: Date): Boolean {
        return withContext(Dispatchers.IO) {
            val lastIndex = DBHandler.getDataBase(context).buildingDao().getLastMonth()

            if (lastIndex != null) {
                lastIndex.CostInMounthAndYear != date.getDateForCalculate()
            } else {
                true
            }
        }
    }
}