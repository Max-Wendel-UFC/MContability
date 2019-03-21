package com.admb.mcontability

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.admb.mcontability.Model.Enums.Code

class AddMovimentacaoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var edtnome : EditText
    lateinit var  edtvalor : EditText
    lateinit var  sdescricao : Spinner
    lateinit var  rbisComplete : RadioButton
    var descricao = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movimentacao)

        edtnome = findViewById(R.id.idEditTextNome)
        edtvalor = findViewById(R.id.idEditTextValor)
        sdescricao = findViewById(R.id.spinner1)
        rbisComplete = findViewById(R.id.idRadioButton)

        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,R.array.descricao,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sdescricao.adapter = adapter
        sdescricao.onItemSelectedListener = this
    }

    fun adicionarMovimentacao(view: View){

        val intent:Intent = Intent()

        val nome = if (!edtnome.text.toString().equals("")) edtnome.text.toString() else "Sem Nome"
        val valor = if (!edtvalor.text.toString().equals(""))edtvalor.text.toString() else "0.0"
        val isComplete = rbisComplete.isChecked.toString()

        intent.putExtra("nome", nome)
        intent.putExtra("valor", valor)
        intent.putExtra("descricao", descricao)
        intent.putExtra("situacao", isComplete)

        setResult(Code.RESULT_ADD.code, intent)
        finish()

    }

    fun cancelarMovimentacao(view:View){
        setResult(Code.CANCEL.code)
        finish()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        descricao = parent!!.getItemAtPosition(position).toString()
    }

}
