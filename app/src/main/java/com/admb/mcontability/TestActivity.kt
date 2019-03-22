package com.admb.mcontability

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class TestActivity : AppCompatActivity() {

    var robos = listOf("XLRM8","ECVS1","AGDU3","BPIT0","HYTC4","ZONG7")

    lateinit var playSong:MediaPlayer

    lateinit var toggleButton: ToggleButton
    var isCheck:Boolean = false
    lateinit var autoComplete:AutoCompleteTextView
    lateinit var gridItens : GridView
    lateinit var adapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        playSong = MediaPlayer.create(applicationContext, R.raw.hangouts_incoming_call)

        autoComplete = findViewById(R.id.autoCompleteTextView)
        gridItens = findViewById(R.id.idGView) as GridView

        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, robos)
        gridItens.adapter = adapter

        autoComplete.setAdapter(adapter)

        gridItens.adapter = adapter
    }

    fun onClickPlaySong(view: View){
        playSong.start()
    }

    fun onClickStopSong(view: View){
        playSong.stop()
    }

    fun onToggle(view: View){
        Toast.makeText(this,"Você deu um Toggle!", Toast.LENGTH_SHORT).show()
    }

}
