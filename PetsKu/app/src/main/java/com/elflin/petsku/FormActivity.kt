package com.elflin.petsku

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.elflin.petsku.databinding.ActivityFormBinding
import model.*
import java.lang.NullPointerException
import java.lang.NumberFormatException

class FormActivity : AppCompatActivity() {

    private lateinit var hewan: Hewan
    private lateinit var viewBinding: ActivityFormBinding
    private lateinit var imageArray: ByteArray
    private var position: Int = 0
    private var jenis: String = ""
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        GetIntent()
        SetupListener()
    }

    private fun GetIntent(){

        if (intent.getIntExtra("status", 0) == GlobalVar.StatusAdd){
            viewBinding.FormTittleEdit.visibility = View.INVISIBLE

            if(GlobalVar.ListDataHewan.size != 0){
                id = GlobalVar.ListDataHewan[GlobalVar.ListDataHewan.size-1].idHewan +1
            }
        }else if (intent.getIntExtra("status", 0) == GlobalVar.StatusEdit){

            hewan = GlobalVar.ListDataHewan[0]
            position = intent.getIntExtra("position", -1)
            viewBinding.FormTittleTambah.visibility = View.INVISIBLE
            for(i in 0..GlobalVar.ListDataHewan.size-1){
                if(GlobalVar.ListDataHewan[i].idHewan == position){
                    hewan = GlobalVar.ListDataHewan[i]
                }
            }
            viewBinding.FormInputNama.editText?.setText(hewan.NamaHewan)
            if(hewan is Ayam){
                viewBinding.buttonayam.isChecked = true
            }
            if(hewan is Sapi){
                viewBinding.buttonsapi.isChecked = true
            }
            if(hewan is Kambing){
                viewBinding.buttonkambing.isChecked = true
            }
            id = position
            viewBinding.FormInputUmurHewan.editText?.setText(hewan.umurHewan.toString())
            if(hewan.FotoHewan != "null") {
                val bArray = GlobalVar.StringToByteArr(hewan.FotoHewan)
                val options = BitmapFactory.Options()
                options.inSampleSize = 2
                options.inScaled = true
                val bitMap = BitmapFactory.decodeByteArray(
                    bArray,
                    0,
                    bArray.size,
                    options
                )
                viewBinding.FormPicture.setImageBitmap(bitMap)
            }
        }
    }

    private fun SetupListener(){
        viewBinding.FormPicture.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_PICK)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBinding.buttonayam.setOnClickListener{
            jenis = "Ayam"
            viewBinding.buttonkambing.isChecked = false
            viewBinding.buttonsapi.isChecked = false
        }
        viewBinding.buttonsapi.setOnClickListener{
            jenis = "Sapi"
            viewBinding.buttonkambing.isChecked = false
            viewBinding.buttonayam.isChecked = false
        }
        viewBinding.buttonkambing.setOnClickListener{
            jenis = "Kambing"
            viewBinding.buttonayam.isChecked = false
            viewBinding.buttonsapi.isChecked = false
        }

        viewBinding.FormBackButton.setOnClickListener {
            finish()
        }
        viewBinding.FormInputButton.setOnClickListener{
            try{
                var hewan = Hewan(
                    id,
                    viewBinding.FormInputNama.editText?.text.toString().trim(),
                    jenis,
                    viewBinding.FormInputUmurHewan.editText?.text.toString().trim().toInt(),
                    GlobalVar.ByteArrToString(imageArray!!)
                )

                if (FormChecker(hewan)){
                    if(jenis == "Ayam"){
                        var ayam:Ayam = Ayam(id, hewan.NamaHewan, hewan.JenisHewan, hewan.umurHewan, hewan.FotoHewan)
                        hewan = ayam
                    }
                    else if(jenis == "Sapi"){
                        var sapi:Sapi = Sapi(id, hewan.NamaHewan, hewan.JenisHewan, hewan.umurHewan, hewan.FotoHewan)
                        hewan = sapi
                    }
                    else if(jenis == "Kambing") {
                        var kambing:Kambing = Kambing(id, hewan.NamaHewan, hewan.JenisHewan, hewan.umurHewan, hewan.FotoHewan)
                        hewan = kambing
                    }

                    if (intent.getIntExtra("status", 0) == GlobalVar.StatusAdd){
                        GlobalVar.ListDataHewan.add(hewan)
                    } else if (intent.getIntExtra("status", 0) == GlobalVar.StatusEdit){
                        for(i in 0..GlobalVar.ListDataHewan.size-1){
                            if(GlobalVar.ListDataHewan[i].idHewan == position){
                                GlobalVar.ListDataHewan[i] = hewan
                            }
                        }
                    }
                    Toast.makeText(baseContext, "Data berhasil di simpan", Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(baseContext, "Data gagal di simpan", Toast.LENGTH_LONG).show()
                }
            }catch (e: NumberFormatException){
                viewBinding.FormInputUmurHewan.error = "Umur hewan belum terisi"
            }catch (e: UninitializedPropertyAccessException){
                if (intent.getIntExtra("status", 0) == GlobalVar.StatusEdit){
                    var hewan = Hewan(
                        id,
                        viewBinding.FormInputNama.editText?.text.toString().trim(),
                        jenis,
                        viewBinding.FormInputUmurHewan.editText?.text.toString().trim().toInt(),
                        hewan.FotoHewan
                    )
                    if (FormChecker(hewan)){
                        if(jenis == "Ayam"){
                            var ayam:Ayam = Ayam(id, hewan.NamaHewan, hewan.JenisHewan, hewan.umurHewan, hewan.FotoHewan)
                            hewan = ayam
                        }
                        else if(jenis == "Sapi"){
                            var sapi:Sapi = Sapi(id, hewan.NamaHewan, hewan.JenisHewan, hewan.umurHewan, hewan.FotoHewan)
                            hewan = sapi
                        }
                        else if(jenis == "Kambing") {
                            var kambing:Kambing = Kambing(id, hewan.NamaHewan, hewan.JenisHewan, hewan.umurHewan, hewan.FotoHewan)
                            hewan = kambing
                        }
                        for(i in 0..GlobalVar.ListDataHewan.size-1){
                            if(GlobalVar.ListDataHewan[i].idHewan == position){
                                GlobalVar.ListDataHewan[i] = hewan
                                break
                            }
                        }
                        Toast.makeText(baseContext, "Data berhasil di simpan", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(baseContext, "Data gagal di simpan", Toast.LENGTH_LONG).show()
                    }
                }
                Toast.makeText(baseContext, "Foto Hewan belum di pilih", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun FormChecker(hewan:Hewan): Boolean {

        var isCompleted = true

        if(hewan.NamaHewan.isEmpty()){
            viewBinding.FormInputNama.error = "Nama hewan belum terisi"
            isCompleted = false
        }else{
            viewBinding.FormInputNama.error = ""
        }

        if(jenis == ""){
            Toast.makeText(baseContext, "Jenis hewan belum di pilih", Toast.LENGTH_LONG).show()
            isCompleted = false
        }

        if(hewan.umurHewan == 0){
            viewBinding.FormInputUmurHewan.error = "Umur hewan belum terisi"
            isCompleted = false
        }else{
            viewBinding.FormInputUmurHewan.error = ""
        }
        return isCompleted
    }

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data                 // GET PATH TO IMAGE FROM GALLEY
            viewBinding.FormPicture.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
            if (uri != null) {
                val inputStream = contentResolver.openInputStream(uri)
                inputStream?.buffered()?.use {
                    imageArray = it.readBytes()
                }
            }
        }
    }
}