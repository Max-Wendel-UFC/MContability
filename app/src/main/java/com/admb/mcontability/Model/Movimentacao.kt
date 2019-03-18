package com.admb.mcontability.Model

import java.math.BigDecimal

interface Movimentacao{
    val id:Long
    val nome:String
    val valor:BigDecimal
    val descricao:String
    val situacao:String

    fun getFormatedValue():String
}