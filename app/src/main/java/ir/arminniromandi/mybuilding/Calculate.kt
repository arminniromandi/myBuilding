package ir.arminniromandi.mybuilding

import android.content.Context
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminniromandi.mybuilding.ModelForActivity.BUnit
import ir.arminniromandi.mybuilding.ModelForActivity.UserForCalculate
import ir.arminniromandi.mybuilding.dataBase.DataModel.BUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class Calculate(private val context: Context ) {

    fun calculateAll(water : Int,
                     allCost:Int ,
                     data : List<UserForCalculate>
    ): ArrayList<Int> {
        val db = DBHandler.getDataBase(context).buildingDao()
        var vahed = db.getBUnit().get(1)

        var ab = (water.toFloat() / vahed.B_Unit)

        var cost = allCost / vahed.B_Unit




        vahed.B_Unit = 0
        var calculate =  ArrayList<Int>()

        data.forEach {
            vahed.B_Unit++
            var waterOfUnit = it.unit * ab
            var all = waterOfUnit + cost
            calculate.add(all.toInt())
        }

        return calculate
    }




}