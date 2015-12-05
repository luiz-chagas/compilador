/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

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
    Env env;

    public Semantic() {
         env = new Env(null);
    }
    public Token addIdentifier (Token token) {
        if (!isUnique(token)) {           
            env.put(token, new Id (((Word)token).getLexeme(),token.getTag(), 0 ));
            return token;
        }    
        return null;
    }
    public boolean isUnique (Token token) {
        if (env.get(token) != null) return false;
        return true;
    }
    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        
        this.env = env;
    }

}
