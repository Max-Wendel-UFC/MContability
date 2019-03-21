package com.admb.mcontability.Model.Enums

enum class Code(val code:Int) {
    REQUEST_ADD_RECEITA(11),
    REQUEST_ADD_DESPESA(10),
    REQUEST_EDIT(12),
    REQUEST_DELETE(13),
    RESULT_ADD (21),
    RESULT_EDIT(22),
    RESULT_DELETE(23),
    CANCEL(40)
}