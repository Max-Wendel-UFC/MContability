package com.admb.mcontability

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import com.admb.mcontability.Model.Enums.Code

class AddMovimentacaoActivity : AppCompatActivity() {

    lateinit var edtnome : EditText
    lateinit var  edtvalor : EditText
    lateinit var  edtdescricao : EditText
    lateinit var  rbisComplete : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movimentacao)

        edtnome = findViewById(R.id.idEditTextNome)
        edtvalor = findViewById(R.id.idEditTextValor)
        edtdescricao = findViewById(R.id.idEditTextDescricao)
        rbisComplete = findViewById(R.id.idRadioButton)

    }

    fun adicionarMovimentacao(view: View){

        val intent:Intent = Intent()

        val nome = if (!edtnome.text.toString().equals("")) edtnome.text.toString() else "Sem Nome"
        val valor = if (!edtvalor.text.toString().equals(""))edtvalor.text.toString() else "0.0"
        val descricao = if (!edtdescricao.text.toString().equals("")) edtdescricao.text.toString() else "(sem descrição)"
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

}
