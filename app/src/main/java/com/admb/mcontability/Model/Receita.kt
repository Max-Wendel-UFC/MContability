package com.admb.mcontability.Model

import java.math.BigDecimal

class Receita(override val id: Long,
              override val nome: String,
              override val valor: BigDecimal,
              override val descricao: String,
              override val situacao: String
) :Movimentacao{

    override fun getFormatedValue():String {
        return "+R$ "+valor.setScale(2).toPlainString()
    }


}