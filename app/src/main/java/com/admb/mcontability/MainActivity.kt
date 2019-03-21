package com.admb.mcontability

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.admb.mcontability.Model.Despesa
import com.admb.mcontability.Model.Enums.Code
import com.admb.mcontability.Model.Movimentacao
import com.admb.mcontability.Model.Receita
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    val listMovimentacao:MutableList<Movimentacao> = mutableListOf()
    lateinit var listViewMovimentacao: ListView
    var selected = 0
    var id = 1
    lateinit var adapter: ArrayAdapter<*>

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_receitas -> {
                message.setText(R.string.title_receitas)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_despesas -> {
                message.setText(R.string.title_despesas)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        selected = -1
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listMovimentacao)
        listViewMovimentacao = findViewById(R.id.listViewMovimentacoes)
        listViewMovimentacao.adapter = adapter
//        listViewMovimentacao.setSelector(holo_green_light)

        listViewMovimentacao.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position) as Movimentacao
            selected = position
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.operations_main_activity,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.add -> {
            val intent: Intent = Intent(this, AddMovimentacaoActivity::class.java)
            startActivityForResult(intent, Code.REQUEST_ADD.code)
            true
        }
        R.id.edit -> {
            Toast.makeText(this,"TODO",Toast.LENGTH_SHORT).show()
            true
        }
        R.id.del -> {
            apagarMovimentacao()
            true
        }
        R.id.about -> {
            Toast.makeText(this,"TODO",Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun apagarMovimentacao(){
        if (selected >= 0){
            listMovimentacao.removeAt(selected)
            adapter.notifyDataSetChanged()
            Toast.makeText(this,"Item excluído",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Selecione um item",Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickAddMovimentacao(view: View){
       val intent:Intent = Intent(this, AddMovimentacaoActivity::class.java)
        startActivityForResult(intent, Code.REQUEST_ADD.code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var resultado =
            """
            Resultado ${requestCode} - ${resultCode}
            """
                .trimIndent()

        if (requestCode == Code.REQUEST_ADD.code && resultCode == Code.REQUEST_ADD.code){

        }else if (requestCode == Code.REQUEST_EDIT.code && resultCode == Code.REQUEST_ADD.code){
            //TODO
        }

        if (data != null) {
            if(data.extras!=null){
                val nome:String = data.extras.get("nome") as String
                val valor:String = data.extras.get("valor") as String
                val descricao:String = data.extras.get("descricao") as String
                val situacao:String = data.extras.get("situacao") as String

                resultado +=
                    """
                        - ${nome} - ${valor} - ${descricao} - ${situacao}
                    """.trimIndent()

                salvar(nome,valor, descricao, situacao)

                Toast.makeText(this, "Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show()
            }
        }else if (resultCode == Code.CANCEL.code){
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
        }

        Log.d("MainActivity", resultado)

    }

    fun salvar(nome:String, valor:String, descricao:String, situacao:String){
        val value = BigDecimal(valor)

        if(value.toDouble()>0){
            val receita = Receita(id.toLong(), nome,value,descricao,situacao)
            listMovimentacao.add(receita)
            adapter.notifyDataSetChanged()
            id++
        }else{
            val despesa = Despesa(id.toLong(), nome,value,descricao,situacao)
            listMovimentacao.add(despesa)
            adapter.notifyDataSetChanged()
            id++
        }
    }
}
