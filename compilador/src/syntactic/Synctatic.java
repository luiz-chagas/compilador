/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntactic;

import java.util.List;
import tag.Tag;
import token.Token;

/**
 *
 * @author Mateus
 */
public class Synctatic {
    private List<Token> tokens;
    private Token token;
    private int depth;
    
    private void eat (Tag tag) {
        if (token.getTag().equals(tag)){
            for (int i = 0; i < depth; i++){
                System.out.println (token.getTag()+ " : "+token.toString()+"\tLinha: "+token.getLine());
                advance();
            }
        }
        else if (tokens.size() > 0) { 
            System.out.println ("Descartando: " + token.getTag() + " : " + token.toString() + "\tLinha " + token.getLine());
            advance ();
            eat (tag);
        }
        else { 
            System.out.println ("Erro! Token não encontrado: "+tag.toString());
        }
    }

    private void advance() {
            if (tokens.size() > 0) {
                token = tokens.get(0);
                tokens.remove(0);
            }
    }

}
