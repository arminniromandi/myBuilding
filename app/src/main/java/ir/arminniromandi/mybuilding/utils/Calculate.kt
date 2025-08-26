package ir.arminniromandi.mybuilding.utils

import android.content.Context
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.CostInMonth
import ir.arminniromandi.mybuilding.utils.date.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class Calculate(val context: Context) {


    fun calculateAll(
        water: Int,
        allCost: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DBHandler.getDataBase(context).buildingDao()

            // get all data
            val allUnit = db.getUserUnit()

            //get  B unit count
            val unitCount = db.getBData()

            //get all Count BUnit
            val bCount = db.getBUnitCount()

            // get all of unit and sum it
            val ab = (water / unitCount)
            val cost = allCost / bCount

                    //some more data
            val sum = water + allCost
            db.insertMonth(
                CostInMonth(
                    C_MonthId = 0,
                    Date().getDateForCalculate(),
                    water,
                    allCost,
                    sum
                )
            )

            launch(Dispatchers.Main) {

                var unit = 0
                allUnit.forEach {
                    unit++
                    val waterOfUnit = it.U_Unit * ab.toInt()
                    val all = waterOfUnit + cost
                    launch(Dispatchers.IO) {
                        db.insertCost(
                            CostForUnit(
                                B_id = 0,
                                C_Cost = Math.round(all.toDouble()).toString(),
                                C_Unit = unit,
                                isPaid = false,
                                C_MonthAndYear = Date().getDateForCalculate()
                            )
                        )
                    }

                }
            }


        }
    }
}