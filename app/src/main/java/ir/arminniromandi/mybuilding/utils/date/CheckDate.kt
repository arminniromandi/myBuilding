package ir.arminniromandi.mybuilding.utils.date

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private var check = mutableStateOf(false)
class CheckDate(var context: Context) {

     fun getAndCheck(date: Date):Boolean {

          CoroutineScope(Dispatchers.IO).launch {
               val lastIndex = DBHandler.getDataBase(context).buildingDao().getLastMonth()
               launch(Dispatchers.Main) {
                    check.value = if (lastIndex != null) {
                         lastIndex.CostInMounthAndYear != date.getDateForCalculate()
                    }else
                         true
               }
          }

          return check.value
     }
}