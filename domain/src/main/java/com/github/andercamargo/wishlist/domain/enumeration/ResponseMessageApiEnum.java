package com.github.andercamargo.wishlist.domain.enumeration;

public enum ResponseMessageApiEnum {
    OPERACAO_SUCESSO("Operação realizada com sucesso"),
    OPERACAO_ERRO("Ocorreu um erro na operação"),
    PRODUTO_NAO_ENCONTRADO("Produto não encontrado na wishlist do cliente informado"),
    WISHLIST_NAO_ENCONTRADO("Wishlist não encontrada do cliente informado");

    private final String valor;

    ResponseMessageApiEnum(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
}
