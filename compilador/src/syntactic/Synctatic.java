/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntactic;

import java.util.LinkedList;
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

    public Synctatic() {
        tokens = new LinkedList<>();
    }

    public void run() {
        printMethod("program");
        token = tokens.get(0);
        switch (token.getTag()) {
            case Tag.APP:
                eat(Tag.APP);
                depth++;
                eat(Tag.IDENTIFIER);
                body();
                depth--;
                break;
            default:
                error("program");
        }
    }

    public void addToken(Token t) {
        this.tokens.add(t);
    }

    private void eat(String tag) {
        if (token.getTag().equals(tag)) {
            System.out.println(token.getTag() + " : " + token.toString() + "\tLinha: " + token.getLine());
            advance();
        } else {
            System.out.println("Erro! Token não encontrado: " + tag);
            System.out.println("Token encontrado: " + token.getTag());
        }
    }

    private void advance() {
        if (tokens.size() > 0) {
            tokens.remove(0);
            token = tokens.get(0);
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
        System.out.println("Erro na análise sintática: " + producao);
    }

    private void printMethod(String local) {
        for (int i = 0; i < depth - 1; i++) {
            System.out.print(" ");
        }
        System.out.println(local);
    }

    private void declList() {
        printMethod("decl-list");
        depth++;
        decl();
        depth--;
        eat(Tag.PONTOVIRGULA);
        while (token.getTag().equals(Tag.INTEGER) || token.getTag().equals(Tag.REAL)) {
            depth++;
            decl();
            depth--;
            eat(Tag.PONTOVIRGULA);
        }
    }

    private void body() {
        printMethod("body");
        switch (token.getTag()) {
            case Tag.INTEGER:
            case Tag.REAL:
                declList();
            case Tag.START:
                eat(Tag.START);
                stmtList();
                eat(Tag.STOP);
                break;
            default:
                error("body");
                break;
        }
    }

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

    private void constant() {
        switch (token.getTag()) {
            case Tag.INTEGER_CONST:
                eat(Tag.INTEGER_CONST);
                break;
            case Tag.FLOAT_CONST:
                eat(Tag.FLOAT_CONST);
                break;
            default:
                error("Constant");
                break;
        }
    }

    private void operator() {
        switch (token.getTag()) {
            case Tag.OP_AND:
                eat(Tag.OP_AND);
                break;
            case Tag.OP_COMPARA:
                eat(Tag.OP_COMPARA);
                break;
            case Tag.OP_DIVISAO:
                eat(Tag.OP_DIVISAO);
                break;
            case Tag.OP_GT:
                eat(Tag.OP_GT);
                break;
            case Tag.OP_GTE:
                eat(Tag.OP_GTE);
                break;
            case Tag.OP_LT:
                eat(Tag.OP_LT);
                break;
            case Tag.OP_LTE:
                eat(Tag.OP_LTE);
                break;
            case Tag.OP_MOD:
                eat(Tag.OP_MOD);
                break;
            case Tag.OP_MULTIPLICACAO:
                eat(Tag.OP_MULTIPLICACAO);
                break;
            case Tag.OP_NOTEQUAL:
                eat(Tag.OP_NOTEQUAL);
                break;
            case Tag.OP_OR:
                eat(Tag.OP_OR);
                break;
            case Tag.OP_SOMA:
                eat(Tag.OP_SOMA);
                break;
            case Tag.OP_SUBTRACAO:
                eat(Tag.OP_SUBTRACAO);
                break;
            default:
                error("Operator");
                break;
        }
    }
}
