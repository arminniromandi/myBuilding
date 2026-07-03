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
    suspend fun insertData(building: Building)

    @Insert
    suspend fun addUsers(users: BUser)

    @Update
    suspend fun updateUsers(users: BUser)

    @Query("SELECT * FROM ${DBHandler.CostInMounth_TABLE}")
    fun getMouths(): Flow<List<CostInMonth>>

    @Query("SELECT SUM(U_Unit) FROM ${DBHandler.User_TABLE}")
    suspend fun getBData(): Int?

    @Query("SELECT COUNT(*) FROM ${DBHandler.User_TABLE}")
    suspend fun getBUnitCount(): Int

    @Query("SELECT B_HostName FROM ${DBHandler.Building_TABLE}")
    suspend fun getBHostName(): String?

    @Query("SELECT COUNT(B_Id) FROM ${DBHandler.Building_TABLE}")
    suspend fun countOfBuilding(): Int

    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    suspend fun getUserUnit(): List<BUser>

    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    fun getUserUnitForEdit(): Flow<List<BUser>>

    @Insert
    suspend fun insertMonth(month: CostInMonth)

    @Insert
    suspend fun insertCost(cost: CostForUnit)

    @Query("SELECT * FROM ${DBHandler.CostForUnit_TABLE} WHERE CostInMonthAndYear = :month")
    fun getCostWithMonth(month: String): Flow<List<CostForUnit>>

    @Update
    suspend fun updateCost(cost: CostForUnit)

    @Query("SELECT COUNT(isPaid) FROM ${DBHandler.CostForUnit_TABLE} WHERE CostInMonthAndYear = :month AND isPaid = :isPaid")
    suspend fun getPaidCount(month: String, isPaid: Int): Int

    @Query("SELECT * FROM ${DBHandler.CostInMounth_TABLE} ORDER BY CostInMonth_Id DESC LIMIT 1")
    suspend fun getLastMonth(): CostInMonth?

    @Query("SELECT COUNT(CostInMonth_Id) FROM ${DBHandler.CostInMounth_TABLE} WHERE CostInMonthAndYear = :month")
    suspend fun isLastMonthExist(month: String): Int

    @Query("SELECT * FROM ${DBHandler.User_TABLE}")
    fun getAllUsers(): Flow<List<BUser>>

    @Query("DELETE FROM ${DBHandler.CostForUnit_TABLE}")
    suspend fun deleteCost()

    @Query("DELETE FROM ${DBHandler.CostInMounth_TABLE}")
    suspend fun deleteMounth()
}
