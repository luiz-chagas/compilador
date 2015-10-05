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
            System.out.println("---- LISTA DE TOKENS IDENTIFICADOS E TABELAS DE SIMBOLOS----");
          //  for (int j = 1; j <= 8; j++) {
                Env env = new Env(null);

                
                lexer = new Lexer(args[0]);
                System.out.println("\n\n"+args[0] + ":\n");

                System.out.println("Tokens identificados:");

                for (int i = 0; i < lexer.getTamanho(); i++) {
                    retorno = lexer.scan();

                    // Trabalha com a TS
                    if (retorno.getTag().equals(Tag.IDENTIFIER)) {// Verifica se eh um identificador
                        env.put(retorno, new Id(((Word) retorno).getLexeme(), retorno.getTag(), 0)); // Insere na TS o identificador
                    } else if (retorno.getClass().equals(Word.class)) {// Verifica se eh uma palavra reservada
                        if (env.get(retorno) == null) {
                            env.put(retorno, new Id(((Word) retorno).getLexeme(), retorno.getTag(), 0)); // Insere na TS a palavra reservada

                        }
                    }

                    if (retorno.getTag().equals(Lexer.SVAZIO)) { // Arquivo vindo com caracteres "invisiveis"
                        break;
                    }
                    System.out.println("\t\t" + retorno);

                }
                System.out.println("\nTabela de Simbolos - Teste " +args[0]+ ":\n");
                env.imprimir();
          //  }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
