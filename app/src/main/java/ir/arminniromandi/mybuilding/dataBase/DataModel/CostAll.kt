package ir.arminniromandi.mybuilding.dataBase.DataModel

import androidx.room.Embedded
import androidx.room.Relation

data class CostAll(
    @Embedded val cost: CostInMonth,
    @Relation(
        parentColumn = "CostInMonthAndYear",
        entityColumn = "CostInMonthAndYear"
    )
    val costDetails: List<CostForUnit>

    )
