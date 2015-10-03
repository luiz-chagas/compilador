/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import java.util.Enumeration;
import java.util.Hashtable;
import token.Token;

/**
 *
 * @author luiz
 */
public class Env {

    private Hashtable<Token, Id> table; // tabela de s�mbolos do ambiente
    protected Env prev; // ambiente imediatamente superior

    public Env(Env n) {
        table = new Hashtable<Token, Id>(); // cria a TS para o ambiente
        prev = n; // associa o ambiente atual ao anterior
    }

    /* Este m�todo insere uma entrada na TS do ambiente */
    /* A chave da entrada � o Token devolvido pelo analisador l�xico */
    /* Id � uma classe que representa os dados a serem armazenados na TS para */
    /* identificadores */
    public void put(Token w, Id i) {
        table.put(w, i);
    }

    /* Este m�todo retorna as informa��es (Id) referentes a determinado Token */
    /* O Token � pesquisado do ambiente atual para os anteriores */
    public Id get(Token w) {
        for (Env e = this; e != null; e = e.prev) {
            Id found = e.table.get(w);
            if (found != null) {
                return found;
            }
        }
        return null; // caso Token n�o exista em uma das TS
    }

    public void imprimir() {
        for (Env e = this; e != null; e = e.prev) {
            for (Enumeration<Id> a = e.table.elements(); a.hasMoreElements();) {
                Id proximo = a.nextElement();
                System.out.println("Nome: " + proximo.getNome() + " / Tipo: " + proximo.getTipo());
            }
        }
    }
}
