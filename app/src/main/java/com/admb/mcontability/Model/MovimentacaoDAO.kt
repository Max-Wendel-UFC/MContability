package com.admb.mcontability.Model

import android.util.Log

class MovimentacaoDAO{

    val movimentacaoDAO:MutableList<Movimentacao> = mutableListOf<Movimentacao>()

    fun createMovimentacao(movimentacao: Movimentacao){
        movimentacaoDAO.add(movimentacao)
    }

    fun findMovimentacaoById(id:Long): Int {
        for ((i, movimentacao:Movimentacao) in movimentacaoDAO.withIndex()){
            if (movimentacao.id == id){
                return i
            }
        }

        return -1
    }

    fun updateMovimentacao(id: Long, movimentacao: Movimentacao){
        val pos = findMovimentacaoById(id)

        if (pos != -1){
            movimentacaoDAO[pos] = movimentacao
        }else{
            Log.d("MovimentacaooDAO","Movimentação inexistente")
        }
    }

    fun deleteMovimentacao(id: Long){
        val pos = findMovimentacaoById(id)
        if (pos != -1){
            movimentacaoDAO.removeAt(pos)
        }else{
            Log.d("MovimentacaooDAO","Movimentação Inexistente")
        }
    }

}