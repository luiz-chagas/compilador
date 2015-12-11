/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;
import lexer.Lexer;
import syntactic.Synctatic;
import commons.Tag;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import semantic.Semantic;
import token.Token;
import token.Word;
import ts.Env;
import ts.Id;

/**
 *
 * @author luiz
 */
public class Compilador {

    public static Env env;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Lexer lexer;
        Synctatic synctatic;
        Token retorno = null;

        try {
            System.out.println("---- LISTA DE TOKENS IDENTIFICADOS E TABELAS DE SIMBOLOS----");

            env = new Env(null);

            lexer = new Lexer(args[0]);
            synctatic = new Synctatic();

            System.out.println(args[0] + ":");
            System.out.println("\nTokens identificados:");
            for (int i = 0; i < lexer.getTamanho(); i++) {
                retorno = lexer.scan();
                if (!retorno.getTag().equals(Lexer.SVAZIO)) {
                    synctatic.addToken(retorno);
                }
                if (retorno.getTag().equals(Lexer.SVAZIO)) { // Arquivo vindo com caracteres "invisiveis"
                    break;
                }
            }
            synctatic.run();
            for (String erro : synctatic.getSyntacticErrors()) {
                System.out.println(erro);
            }
            for (String erro : synctatic.getSemanticErrors()) {
                System.out.println(erro);
            }
            System.out.println("\nTabela de Simbolos:");
            env.imprimir();
            //     }    
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
