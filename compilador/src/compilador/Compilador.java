/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;
import lexer.Lexer;
import tag.Tag;
import token.Token;
import token.Word;
import ts.Env;
import ts.Id;

/**
 *
 * @author luiz
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Lexer lexer;

        Token retorno = null;

        try {
            System.out.println("\n---- LISTA DE TOKENS IDENTIFICADOS E TABELAS DE S�MBOLOS----");
            for (int j = 1; j <= 6; j++) {
                Env env = new Env(null);

                lexer = new Lexer("testes/teste" + j + ".txt");
                System.out.println("\n\nTESTE " + j + ":\n");

                System.out.println("Tokens identificados:\n");

                for (int i = 0; i < lexer.getTamanho(); i++) {
                    retorno = lexer.scan();

                    // Trabalha com a TS
                    if (retorno.getTag().equals(Tag.IDENTIFIER)) {// Verifica se � um identificador
                        env.put(retorno, new Id(((Word) retorno).getLexeme(), retorno.getTag(), 0)); // Insere na TS o identificador
                    } else if (retorno.getClass().equals(Word.class)) {// Verifica se � uma palavra reservada
                        if (env.get(retorno) == null) {
                            env.put(retorno, new Id(((Word) retorno).getLexeme(), retorno.getTag(), 0)); // Insere na TS a palavra reservada

                        }
                    }

                    if (retorno.getTag().equals(Lexer.SVAZIO)) { // Arquivo vindo com caracteres "invis�veis"
                        break;
                    }
                    System.out.println("\t\t" + retorno);

                }
                System.out.println("\nTabela de S�mbolos - Teste " + j + ":\n");
                env.imprimir();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
