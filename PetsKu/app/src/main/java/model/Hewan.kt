package model

open class Hewan(
    idHewan:Int,
    NamaHewan: String,
    JenisHewan:String,
    umurHewan: Int,
    FotoHewan: String){

    var idHewan = idHewan
    val NamaHewan = NamaHewan
    val JenisHewan = JenisHewan
    val umurHewan = umurHewan
    val FotoHewan = FotoHewan

    open fun Interaction():String{
        return ""
    }

    fun Feed(biji: Biji):String{
        return "Kamu memberi makan ayam biji-bijian"
    }
    fun <Int> Feed(rumput: Rumput):String{
        return "Kamu memberi makan hewanmu rumput"
    }
}