package ir.arminniromandi.mybuilding.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import ir.arminniromandi.mybuilding.dataBase.dataModel.BUser
import ir.arminniromandi.mybuilding.dataBase.dataModel.Building
import ir.arminniromandi.mybuilding.dataBase.dataModel.CostForUnit
import ir.arminniromandi.mybuilding.dataBase.dataModel.CostInMonth
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingDao {


    @Insert
    fun insertData(building: Building)

    @Insert
    fun addUsers(users: BUser)

    @Update
    fun updateUsers(users: BUser)


    @Query("SELECT * FROM ${DBHandler.CostInMounth_TABLE}")
    fun getMouths():Flow<List<CostInMonth>>





    @Query("SELECT * FROM ${DBHandler.Building_TABLE}")
    fun getBData():List<Building>

    @Query("SELECT B_HostName FROM ${DBHandler.Building_TABLE}")
    fun getBHostName():String


    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    fun getUserUnit():List<BUser>

    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    fun getUserUnitForEdit():Flow<List<BUser>>

    @Insert
    fun insertMonth(month: CostInMonth)

    @Insert
    fun insertCost(cost: CostForUnit)



    @Query("SELECT * FROM ${DBHandler.CostForUnit_TABLE} WHERE CostInMonthAndYear = :month")
    fun getCostWithMonth(month:String):Flow<List<CostForUnit>>



    @Update
    fun updateCost(cost: CostForUnit)

    @Query("SELECT COUNT(isPaid) FROM CostForUnit_TABLE where CostInMonthAndYear =:month AND isPaid = :isPaid")
    fun getPaidCount(month:String , isPaid:Int):Int


    @Query("SELECT * FROM ${DBHandler.CostInMounth_TABLE} ORDER BY CostInMonth_Id DESC LIMIT 1")
    fun getLastMonth():CostInMonth

    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    fun getAllUsers():Flow<List<BUser>>

}