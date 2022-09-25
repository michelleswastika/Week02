package model

class Sapi(idHewan: Int, NamaHewan: String, JenisHewan: String, umurHewan: Int, FotoHewan: String) : Hewan(idHewan, NamaHewan, JenisHewan, umurHewan, FotoHewan) {
    override fun Interaction(): String {
        return "Mooooo"
    }
}