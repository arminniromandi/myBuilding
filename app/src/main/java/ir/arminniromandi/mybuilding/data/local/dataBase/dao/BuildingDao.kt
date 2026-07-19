package ir.arminniromandi.mybuilding.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Building
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInYear
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship.BuildingWithUnits
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship.CostForUnitDetails
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship.CostInMonthWithCosts
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship.CostInYearWithMonths
import ir.arminniromandi.mybuilding.domain.model.MonthlyCostCommand
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingDao {

    // -----------------------------
    // Building
    // -----------------------------


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertData(building: Building): Long

    @Query(
        """
        SELECT B_HostName
        FROM ${DBHandler.Building_TABLE}
        """
    )
    suspend fun getBHostName(): String






    // -----------------------------
    // Unit / BUser
    // -----------------------------

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUsers(users: BUser): Long

    @Update
    suspend fun updateUsers(users: BUser)

    @Query(
        """
        SELECT COALESCE(SUM(U_Unit), 0)
        FROM ${DBHandler.User_TABLE}
        """
    )
    suspend fun getBData(): Int

    @Query(
        """
        SELECT COUNT(*)
        FROM ${DBHandler.User_TABLE}
        """
    )
    suspend fun getBUnitCount(): Int

    @Query(
        """
        SELECT *
        FROM ${DBHandler.User_TABLE}
        ORDER BY U_Number ASC
        """
    )
    suspend fun getUserUnit(): List<BUser>


    @Query(
        """
        SELECT *
        FROM ${DBHandler.User_TABLE}
        ORDER BY U_Number ASC
        """
    )
    fun getAllUsers(): Flow<List<BUser>>


    // -----------------------------
    // Year
    // -----------------------------

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertYear(year: CostInYear): Long






    /*
    * برای حذف سال های ثبت شده
    * */
    @Query(
        """
        DELETE FROM ${DBHandler.CostInYear_TABLE}
        WHERE C_YearId = :yearId
        """
    )
    suspend fun deleteYear(yearId: Int)


    // -----------------------------
    // Month
    // -----------------------------

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMonth(month: CostInMonth): Long


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMonths(months: List<CostInMonth>): List<Long>

    @Update
    suspend fun updateMonth(month: CostInMonth)


    @Query(
        """
    SELECT CostInMonth_Id
    FROM ${DBHandler.CostInMounth_TABLE}
    WHERE M_YearId = :yearId
      AND M_MonthNumber = :monthNumber
    LIMIT 1
    """
    )
    suspend fun getMonthId(
        yearId: Int,
        monthNumber: Int
    ): Int?



    @Query(
        """
        SELECT month.*
        FROM ${DBHandler.CostInMounth_TABLE} AS month
        INNER JOIN ${DBHandler.CostInYear_TABLE} AS yearTable
            ON yearTable.C_YearId = month.M_YearId
        ORDER BY yearTable.Y_Year DESC,
                 month.M_MonthNumber DESC
        """
    )
    fun getMouths(): Flow<List<CostInMonth>>



    @Query(
        """
    SELECT *
    FROM ${DBHandler.CostInMounth_TABLE}
    WHERE CostInMonth_Id = :monthId
    LIMIT 1
    """
    )
    suspend fun getMonthDetails(
        monthId: Int
    ): CostInMonth?




    @Transaction
    @Query(
        """
        SELECT *
        FROM ${DBHandler.CostInMounth_TABLE}
        WHERE CostInMonth_Id = :monthId
        """
    )
    fun getMonthWithCosts(
        monthId: Int
    ): Flow<List<CostInMonthWithCosts>>

    @Query(
        """
        SELECT month.*
        FROM ${DBHandler.CostInMounth_TABLE} AS month
        INNER JOIN ${DBHandler.CostInYear_TABLE} AS yearTable
            ON yearTable.C_YearId = month.M_YearId
        ORDER BY yearTable.Y_Year DESC,
                 month.M_MonthNumber DESC
        LIMIT 1
        """
    )
    suspend fun getLastMonth(): CostInMonth?

    @Query(
        """
        SELECT COUNT(*)
        FROM ${DBHandler.CostInMounth_TABLE}
        WHERE CostInMonthAndYear = :month
        """
    )
    suspend fun isLastMonthExist(month: String): Int


    // -----------------------------
    // Cost for Unit
    // -----------------------------

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCost(
        costs: List<CostForUnit>
    ): List<Long>



    //اضافه کردن به صورت تکی
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCost(
        cost: CostForUnit
    ): Long



    @Update
    suspend fun updateCost(cost: CostForUnit)


    //todo : delete this if don needeed
    /*@Transaction
    @Query(
        """
        SELECT unitCost.*
        FROM ${DBHandler.CostForUnit_TABLE} AS unitCost
        INNER JOIN ${DBHandler.CostInMounth_TABLE} AS month
            ON month.CostInMonth_Id = unitCost.C_MonthId
        WHERE month.CostInMonthAndYear = :month
        ORDER BY unitCost.C_UnitId ASC
        """
    )
    fun getCostWithMonth(
        month: String
    ): Flow<List<CostForUnitDetails>>*/


    @Transaction
    @Query(
        """
    SELECT *
    FROM ${DBHandler.CostForUnit_TABLE}
    WHERE C_MonthId = :monthId
    ORDER BY C_UnitId ASC
    """
    )
    fun getCostWithMonth(
        monthId: Int
    ): Flow<List<CostForUnitDetails>>


    @Query(
        """
        SELECT COUNT(*)
        FROM ${DBHandler.CostForUnit_TABLE} AS unitCost
        INNER JOIN ${DBHandler.CostInMounth_TABLE} AS month
            ON month.CostInMonth_Id = unitCost.C_MonthId
        WHERE month.CostInMonthAndYear = :month
          AND unitCost.isPaid = :isPaid
        """
    )
    suspend fun getPaidCount(
        month: String,
        isPaid: Boolean
    ): Int

    @Query(
        """
        DELETE FROM ${DBHandler.CostForUnit_TABLE}
        """
    )
    suspend fun deleteCost()

    @Query(
        """
        DELETE FROM ${DBHandler.CostInMounth_TABLE}
        """
    )
    suspend fun deleteMounth()




    @Query(
        """
    SELECT C_YearId
    FROM ${DBHandler.CostInYear_TABLE}
    WHERE Y_BuildingId = :buildingId
      AND Y_Year = :year
    LIMIT 1
    """
    )
    suspend fun getYearId(
        buildingId: Int,
        year: Int
    ): Int?

    @Transaction
    suspend fun insertMonthWithCosts(
        command: MonthlyCostCommand
    ): Int {


        //امکان خطا بسیار کم است
        val duplicateUnitCount = command.unitCosts
            .groupBy { it.unitId }
            .any { it.value.size > 1 }

        require(!duplicateUnitCount) {
            "شناسه واحد تکراری در لیست شارژ وجود دارد"
        }

        /*
         * اگر سال وجود نداشت، ساخته می‌شود.
         */
        val yearId = getYearId(
            buildingId = command.buildingId,
            year = command.year
        ) ?: insertYear(
            CostInYear(
                Y_BuildingId = command.buildingId,
                Y_Year = command.year
            )
        ).toInt()


        val monthId = insertMonth(
            CostInMonth(
                M_YearId = yearId,
                M_MonthNumber = command.monthNumber,
                CostInMounthAndYear = "${command.year} ${command.monthDisplayName}",
                costWater = command.waterCost,
                cost = command.otherCost,
                sumOfAll = command.totalCost
            )
        ).toInt()

        val costs = command.unitCosts.map { unitCost ->
            CostForUnit(
                C_MonthId = monthId,
                C_UnitId = unitCost.unitId,
                C_Cost = unitCost.amount,
                isPaid = false
            )
        }

        insertCost(costs)

        return monthId
    }




}