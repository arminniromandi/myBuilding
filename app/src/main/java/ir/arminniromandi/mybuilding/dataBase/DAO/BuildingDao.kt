package ir.arminapp.myapp2.Room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminapp.myapp2.Room.DataModel.Building
import ir.arminniromandi.mybuilding.ModelForActivity.BUnit
import ir.arminniromandi.mybuilding.dataBase.DataModel.BUser
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostForUnit
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostInMonth

@Dao
interface BuildingDao {


    @Insert
    fun insertData(building: Building)

    @Insert
    fun addUsers(users: BUser)





    @Query("SELECT ${DBHandler.B_Unit} FROM ${DBHandler.Building_TABLE}")
    fun getBUnit():List<BUnit>


//    @Query("SELECT ${DBHandler.} FROM User_TABLE")
//    fun getUserUnit():List<UserForCalculate>

    @Insert
    fun insertMonth(month: CostInMonth)

    @Insert
    fun insertCost(cost: CostForUnit)


    @Transaction
    @Query("SELECT * FROM ${DBHandler.CostForUnit_TABLE} WHERE CostInMonthAndYear = :month")
    fun getCostWithMonth(month:String):List<CostForUnit>


    @Query("SELECT * FROM ${DBHandler.CostInMounth_TABLE} ORDER BY CostInMonth_Id DESC LIMIT 1")
    fun getLastMonth():CostInMonth



//
//    @Query("DELETE FROM ${DBHandler.NOTES_TABLE} WHERE ${DBHandler.NOTES_ID} = :id")
//    fun deleteNotes(id: Int)
//
//    @Query("SELECT * FROM ${DBHandler.NOTES_TABLE} WHERE ${DBHandler.NOTES_ID} = :id")
//    fun getNotesById(id: Int):Flow<List<Building>>
//
//    @Query("SELECT ${DBHandler.NOTES_ID} ,${DBHandler.NOTES_TITLE} FROM ${DBHandler.NOTES_TABLE} WHERE ${DBHandler.NOTES_DELETE_STATE} = :deleteState ")
//    fun getNotesForRecyclerView(deleteState:String):Flow<List<RecyclerNotesData>>
//
//    @Query("UPDATE ${DBHandler.NOTES_TABLE} SET ${DBHandler.NOTES_DELETE_STATE} = :state WHERE ${DBHandler.NOTES_ID} = :id ")
//    fun updateNotes(id :Int , state :String):Int
}