package com.admb.mcontability

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.GridView
import android.widget.ToggleButton

class TestActivity : AppCompatActivity() {

    var robos = listOf<String>("XLRM8","ECVS1","AGDU3","BPIT0","HYTC4","ZONG7")

    lateinit var playSong:ToggleButton
    lateinit var autoComplete:AutoCompleteTextView
    lateinit var gridItens : GridView
    lateinit var adapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //        playSong = findViewById(R.id.togglePalySong)

        autoComplete = findViewById(R.id.autoCompleteTextView)
        gridItens = findViewById(R.id.idGView) as GridView

        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, robos)
        gridItens.adapter = adapter

        autoComplete.setAdapter(adapter)

        gridItens.adapter = adapter

    }
}
