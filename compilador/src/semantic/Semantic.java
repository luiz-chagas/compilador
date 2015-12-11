/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import commons.Tag;
import static compilador.Compilador.env;
import java.util.ArrayList;
import java.util.HashMap;
import token.Float_const;
import token.Integer_const;
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
    public boolean identifierExists (Token t) {
        return env.get(t) != null;
    }
    public String getIdentifierType (Token t){
        Id w = env.get(t);       
        return w.getTipo();
    }

    
    public boolean checkIntegerType (Token t){       
        return t instanceof Integer_const;    
    }
    public boolean checkFloatType (Token t){ 
        return t instanceof Float_const;    
    }
    

}
