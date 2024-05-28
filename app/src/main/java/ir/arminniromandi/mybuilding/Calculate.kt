package ir.arminniromandi.mybuilding

class Calculate() {

    fun calculateAll(water : Int, allCost:Int ): ArrayList<Int> {

        var vahed = 1


        var ab = (water.toFloat() / 35)

        var cost = allCost / vahed

        var afrad = arrayOf(
            4,
            3,
            3,
            4,
            3,
            1,
            4,
            5,
            3,
            4
        )

        vahed = 0
        var calculate =  ArrayList<Int>()
        afrad.forEach {
            vahed++
            var waterOfUnit = it * ab
            var all = waterOfUnit + cost
            calculate.add(all.toInt())
            println("vahed $vahed : ${all.toInt()}")
        }

        return calculate
    }



}