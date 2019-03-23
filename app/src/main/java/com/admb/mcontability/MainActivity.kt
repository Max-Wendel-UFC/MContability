package com.admb.mcontability

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
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

    lateinit var fab_plus : FloatingActionButton
    lateinit var fab_pay : FloatingActionButton
    lateinit var fab_cash : FloatingActionButton

    lateinit var fabOpen: Animation
    lateinit var fabClose: Animation
    lateinit var fabRClockWise: Animation
    lateinit var fabRAntiClockWise: Animation
    var isOpen:Boolean=false

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(getHomeMessage())
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

        fab_plus = findViewById(R.id.plusActionButton)
        fab_pay = findViewById(R.id.payActionButton)
        fab_cash = findViewById(R.id.cashActionButton)
        fabOpen = AnimationUtils.loadAnimation(applicationContext,R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(applicationContext,R.anim.fab_close)
        fabRClockWise = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate_clockwise)
        fabRAntiClockWise = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate_anticlockwise)

        listViewMovimentacao.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position) as Movimentacao
            selected = position
        }

        listViewMovimentacao.setOnItemLongClickListener { parent, view, position, id ->
            Toast.makeText(this, "Long click detectado!", Toast.LENGTH_SHORT).show()
            return@setOnItemLongClickListener true
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
            Toast.makeText(this,"TODO",Toast.LENGTH_SHORT).show()
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
        R.id.test -> {
            val intent: Intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun onClickPlus(view: View){
        if (isOpen){
            fab_cash.startAnimation(fabClose)
            fab_pay.startAnimation(fabClose)
            fab_plus.startAnimation(fabRAntiClockWise)
            fab_pay.isClickable = false
            fab_cash.isClickable = false
            isOpen = false
        }else{
            fab_cash.startAnimation(fabOpen)
            fab_pay.startAnimation(fabOpen)
            fab_plus.startAnimation(fabRClockWise)
            fab_pay.isClickable = true
            fab_cash.isClickable = true
            isOpen = true
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

    fun onClickAddReceita(view: View){
       val intent:Intent = Intent(this, AddMovimentacaoActivity::class.java)
        startActivityForResult(intent, Code.REQUEST_ADD_RECEITA.code)
    }

    fun onClickAddDespesa(view: View){
        val intent = Intent(this,AddMovimentacaoActivity::class.java)
        startActivityForResult(intent,Code.REQUEST_ADD_DESPESA.code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var resultado =
            """
            Resultado ${requestCode} - ${resultCode}
            """
                .trimIndent()

        if (requestCode == Code.REQUEST_ADD_RECEITA.code && resultCode == Code.REQUEST_ADD_RECEITA.code){

        }else if (requestCode == Code.REQUEST_EDIT.code && resultCode == Code.REQUEST_ADD_RECEITA.code){
            //TODO
        }

        if (data != null) {
            if(data.extras!=null){

                val nome:String = data.extras.get("nome") as String
                val descricao:String = data.extras.get("descricao") as String
                val situacao:String = data.extras.get("situacao") as String
                var valor:String

                if (requestCode == Code.REQUEST_ADD_RECEITA.code){
                    valor = "-"
                    valor += data.extras.get("valor") as String
                }else{
                    valor = data.extras.get("valor") as String
                }
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

    fun getHomeMessage():Int{
        if(listMovimentacao.isEmpty()){
            return R.string.title_home_2
        }else{
            return R.string.title_home_1
        }
    }
}
