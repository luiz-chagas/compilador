/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import java.util.HashMap;
import token.Token;
import ts.Env;

/**
 *
 * @author Mateus
 */
public class Semantic {
    Env env = new Env(null);

    public Semantic() {
    }
    public void addIdentifier (Token token) {
        if (!isUnique(token));
        
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
