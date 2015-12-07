/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import static compilador.Compilador.env;
import java.util.HashMap;
import token.Token;
import token.Word;
import ts.Env;
import ts.Id;

/**
 *
 * @author Mateus
 */
public class Semantic {

    public Semantic() {;
    }

    public Token addIdentifier(Token token, String tipo) {

        if (isUnique(token)) {
            env.put(token, new Id(((Word) token).getLexeme(), tipo, 0));
            return token;
        }
        return null;
    }

    public boolean isUnique(Token token) {
        //If getToken == null, return true; else return false
        return env.get(token) == null;
    }

}
