package com.elflin.petsku

import adapter.HewanRVAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.elflin.petsku.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import listener.CardAnimalListener
import model.*

class MainActivity : AppCompatActivity(), CardAnimalListener {

    private lateinit var viewBind: ActivityMainBinding
    private lateinit var hewan: Hewan
    private val RVAdapter = HewanRVAdapter(GlobalVar.ListDataHewan, this)
    private val Adapter = HewanRVAdapter(GlobalVar.FilterDataHewan, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        SetupRecyclerView()
        SetupListener()
    }

    override fun onResume() {
        super.onResume()
        if (GlobalVar.ListDataHewan.size > 0){
            viewBind.MainRecyclerView.visibility = View.VISIBLE
            viewBind.MainNoData.visibility = View.GONE
        }else{
            viewBind.MainRecyclerView.visibility = View.GONE
            viewBind.MainNoData.visibility = View.VISIBLE
        }
        RVAdapter.notifyDataSetChanged()
    }

    private fun SetupRecyclerView(){
        val layoutManager = LinearLayoutManager(baseContext)
        viewBind.MainRecyclerView.layoutManager = layoutManager
        viewBind.MainRecyclerView.adapter = RVAdapter
    }

    private fun SetupListener(){
        viewBind.MainFAB.setOnClickListener{
            val intent = Intent(baseContext, FormActivity::class.java).apply {
                putExtra("status", GlobalVar.StatusAdd)
            }
            startActivity(intent)
        }
        viewBind.filterayam.setOnClickListener{
            GlobalVar.FilterDataHewan.clear()
            for(i in 0..GlobalVar.ListDataHewan.size-1){
                if(GlobalVar.ListDataHewan[i] is Ayam){
                    GlobalVar.FilterDataHewan.add(GlobalVar.ListDataHewan[i])
                }
            }
            viewBind.MainRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }
        viewBind.filtersapi.setOnClickListener{
            GlobalVar.FilterDataHewan.clear()
            for(i in 0..GlobalVar.ListDataHewan.size-1){
                if(GlobalVar.ListDataHewan[i] is Sapi){
                    GlobalVar.FilterDataHewan.add(GlobalVar.ListDataHewan[i])
                }
            }
            viewBind.MainRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }
        viewBind.filterkambing.setOnClickListener{
            GlobalVar.FilterDataHewan.clear()
            for(i in 0..GlobalVar.ListDataHewan.size-1){
                if(GlobalVar.ListDataHewan[i] is Kambing){
                    GlobalVar.FilterDataHewan.add(GlobalVar.ListDataHewan[i])
                }
            }
            viewBind.MainRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }
        viewBind.filterall.setOnClickListener{
            viewBind.MainRecyclerView.adapter = RVAdapter
            RVAdapter.notifyDataSetChanged()
        }
    }

    override fun OnEditClicked(position: Int) {
        val intent = Intent(baseContext, FormActivity::class.java).apply {
            putExtra("status", GlobalVar.StatusEdit)
            putExtra("position", position)
        }
        startActivity(intent)
    }

    override fun OnDeleteClicked(position: Int) {
        for(i in 0..GlobalVar.ListDataHewan.size-1){
            if(GlobalVar.ListDataHewan[i].idHewan == position){
                GlobalVar.ListDataHewan.removeAt(i)
                break
            }
        }
        MaterialAlertDialogBuilder(this)
            .setTitle("Hapus Hewan")
            .setMessage("Apakah anda ingin menghapus hewan ini?")
            .setNegativeButton("Batal") { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton("Setuju") { dialog, which ->
                for(i in 0..GlobalVar.ListDataHewan.size-1){
                    if(GlobalVar.ListDataHewan[i].idHewan == position){
                        GlobalVar.ListDataHewan.removeAt(i)
                        break
                    }
                }
                Toast.makeText(baseContext, "Data berhasil di hapus", Toast.LENGTH_LONG).show()
                onResume()
            }
            .show()
    }

    override fun OnInteractionClicked(position: Int) {
        for(i in 0..GlobalVar.ListDataHewan.size-1){
            if(GlobalVar.ListDataHewan[i].idHewan == position){
                Toast.makeText(this, GlobalVar.ListDataHewan[i].Interaction(), Toast.LENGTH_SHORT).show()
                break
            }
        }
    }

    override fun OnFeedClicked(position: Int) {
        for(i in 0..GlobalVar.ListDataHewan.size-1){
            if(GlobalVar.ListDataHewan[i].idHewan == position){
                if(GlobalVar.ListDataHewan[i] is Ayam){
                    Toast.makeText(this, GlobalVar.ListDataHewan[i].Feed(Biji()), Toast.LENGTH_SHORT).show()
                }
                else if(GlobalVar.ListDataHewan[i] is Sapi || GlobalVar.ListDataHewan[i] is Kambing){
                        Toast.makeText(this, GlobalVar.ListDataHewan[i].Feed<Int>(Rumput()), Toast.LENGTH_SHORT).show()
                }
                break
            }
        }
    }
}