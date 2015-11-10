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
    
    private void eat (String tag) {
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
            System.out.println ("Erro! Token não encontrado: "+tag);
        }
    }

    private void advance() {
        if (tokens.size() > 0) {
            token = tokens.get(0);
            tokens.remove(0);
        }
    }
    private void declList() {
            //printMethod("decl-list");
            depth++;
            decl();
            depth--;
            eat(Tag.ABRECHAVE);
            while (token.getTag().equals(Tag.INTEGER) || token.getTag().equals(Tag.REAL)) {
                    depth++;
                    decl();
                    depth--;
                    eat(Tag.PONTOVIRGULA);
            }
    }
    
    
    private void type() {
        
        switch (token.getTag()) {
        case Tag.INTEGER:
                eat(Tag.INTEGER);
                break;
        case Tag.REAL:
                eat(Tag.REAL);
                break;
        default:
                error("type");
                break;
        }
    }
   
    private void error(String producao) {
        System.out.println ("Erro na análise sintática: "+producao);
    }    
    
//    private void declList() {
//        //printMethod("decl-list");
//        depth++;
//        decl();
//        depth--;
//        eat(Tag.PONTOVIRGULA);
//        while (token.getTag().equals(Tag.INTEGER) || token.getTag().equals(Tag.REAL)) {
//                depth++;
//                decl();
//                depth--;
//                eat(Tag.PONTOVIRGULA);
//        }
//    }
//    
//	private void body() {
//	//	printMethod("body");
//		switch (token.getTag()) {
//                    case Tag.INTEGER:
//                    case Tag.REAL:
//                            depth++;
//                            declList();
//                            depth--;
//                    case Tag.APP:
//                            eat(Tag.APP);
//                            depth++;
//                            stmtList();
//                            depth--;
//                            eat(Tag.END);
//                            break;
//                    default:
//                            error("body");
//                            break;
//		}
//	}

    private void decl() {
		depth++;
		type();
		identList();
		depth--;

        
    }
    
	private void identList() {
		switch (token.getTag()) {
		case Tag.IDENTIFIER:
			eat(Tag.IDENTIFIER);
			while (token.getTag().equals(Tag.VIRGULA)) {
				eat(Tag.VIRGULA);
				eat(Tag.IDENTIFIER);
			}
			break;
		default:
			error("ident-list");
			break;
		}
	}

}
