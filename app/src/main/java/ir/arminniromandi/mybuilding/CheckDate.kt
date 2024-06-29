package ir.arminniromandi.mybuilding

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ir.arminapp.myapp2.Room.DBHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckDate(var context: Context) {
     @SuppressLint("CoroutineCreationDuringComposition")
     @Composable
     fun getAndCheck(date: Date):Boolean {

          var check by remember {
               mutableStateOf(false)
          }
          CoroutineScope(Dispatchers.IO).launch {
               val lastIndex = DBHandler.getDataBase(context).buildingDao().getLastMonth()
               launch(Dispatchers.Main) {
                    check = if (lastIndex != null) {
                         lastIndex.CostInMounthAndYear != date.getDateForCalculate()
                    }else
                         true
               }
          }

          return check
     }
}