package ir.arminniromandi.mybuilding

import android.annotation.SuppressLint
import android.content.Context
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminniromandi.mybuilding.dataBase.DataModel.BUser
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostForUnit
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostInMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class Calculate(val context: Context) {

    @SuppressLint("CoroutineCreationDuringComposition")

    fun calculateAll(
        water: Int,
        allCost: Int
    ) {


        CoroutineScope(Dispatchers.IO).launch {
            val db = DBHandler.getDataBase(context).buildingDao()
            var bUnit = db.getBUnit().get(0).B_Unit

            var AllUnit = db.getUserUnit()

            val ab = (water.toFloat() / bUnit)
            val cost = allCost / bUnit
            val sum = water + cost

            db.insertMonth(
                CostInMonth(
                    C_MonthId = 0,
                    Date().getDateForCalculate(),
                    water,
                    sum
                )
            )

            launch(Dispatchers.Main) {

                var unit = 0
                AllUnit.forEach {
                    unit++
                    val waterOfUnit = it.U_Unit * ab
                    val all = waterOfUnit + cost
                    launch(Dispatchers.IO) {
                        db.insertCost(
                            CostForUnit(
                                B_id = 0,
                                C_Cost = all.toInt().toString(),
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