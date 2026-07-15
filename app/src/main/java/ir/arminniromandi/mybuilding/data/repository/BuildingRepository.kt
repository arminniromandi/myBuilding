package ir.arminniromandi.mybuilding.data.repository


import ir.arminniromandi.mybuilding.data.local.dataBase.dao.BuildingDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Building
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship.CostForUnitDetails
import ir.arminniromandi.mybuilding.domain.model.MonthlyCostCommand
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuildingRepository @Inject constructor(
    private val dao: BuildingDao
) {


    suspend fun getAllB()=
        dao.getAllBuilding()

    suspend fun insertData(building: Building) =
        dao.insertData(building)

    suspend fun addUsers(users: BUser) =
        dao.addUsers(users)

    suspend fun updateUsers(users: BUser) =
        dao.updateUsers(users)

    fun getMouths(): Flow<List<CostInMonth>> =
        dao.getMouths()

    suspend fun getMouthDetail(mouthId: Int): CostInMonth? =
        dao.getMonthDetails(mouthId)

    suspend fun getBData(): Int? =
        dao.getBData()

    suspend fun getBUnitCount(): Int =
        dao.getBUnitCount()

    suspend fun getBHostName(): String? =
        dao.getBHostName()

    suspend fun countOfBuilding(): Int =
        dao.countOfBuilding()

    suspend fun getUserUnit(): List<BUser> =
        dao.getUserUnit()

    fun getUserUnitForEdit(): Flow<List<BUser>> =
        dao.getUserUnitForEdit()

    suspend fun insertMonth(month: CostInMonth) =
        dao.insertMonth(month)

    suspend fun insertCost(cost: CostForUnit) =
        dao.insertCost(cost)


    suspend fun insertMonthWithCosts(
        command: MonthlyCostCommand
    ): Int {
        return dao.insertMonthWithCosts(command)
    }


    fun getCostWithMonth(monthId: Int): Flow<List<CostForUnitDetails>> =
        dao.getCostWithMonth(monthId)

    suspend fun updateCost(cost: CostForUnit) =
        dao.updateCost(cost)

    suspend fun getPaidCount(month: String, isPaid: Boolean): Int =
        dao.getPaidCount(month, isPaid)

    suspend fun getLastMonth(): CostInMonth? =
        dao.getLastMonth()

    suspend fun isLastMonthExist(month: String): Int =
        dao.isLastMonthExist(month)

    fun getAllUsers(): Flow<List<BUser>> =
        dao.getAllUsers()

    suspend fun deleteCost() =
        dao.deleteCost()

    suspend fun deleteMounth() =
        dao.deleteMounth()


}