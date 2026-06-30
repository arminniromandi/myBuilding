package ir.arminniromandi.mybuilding.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Building
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
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





    @Query("SELECT SUM(U_Unit) FROM ${DBHandler.User_TABLE}")
    fun getBData():Int


    @Query("SELECT COUNT(*) FROM ${DBHandler.User_TABLE}")
    fun getBUnitCount():Int



    @Query("SELECT B_HostName FROM ${DBHandler.Building_TABLE}")
    fun getBHostName():String

    @Query("SELECT COUNT(B_Id) FROM ${DBHandler.Building_TABLE}")
    fun countOfBuilding():Int


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

    @Query("SELECT COUNT(CostInMonth_Id) FROM ${DBHandler.CostInMounth_TABLE} where CostInMonthAndYear =:month ")
    fun isLastMonthExist(month:String):Int


    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    fun getAllUsers():Flow<List<BUser>>


    @Query("DELETE FROM ${DBHandler.CostForUnit_TABLE}")
    fun deleteCost()


    @Query("DELETE FROM ${DBHandler.CostInMounth_TABLE}")
    fun deleteMounth()


}