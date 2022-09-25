package model

import android.widget.Toast

class Ayam(idHewan: Int, NamaHewan: String, JenisHewan: String = "Ayam", umurHewan: Int, FotoHewan: String) : Hewan(idHewan, NamaHewan, JenisHewan, umurHewan, FotoHewan) {

    override fun Interaction(): String {
        return "Bock bock bock"
    }
}