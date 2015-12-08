/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntactic;

import java.util.LinkedList;
import java.util.List;
import commons.Tag;
import static compilador.Compilador.env;
import java.util.ArrayList;
import semantic.Semantic;
import token.Token;
import token.Word;
import ts.Env;

/**
 *
 * @author Mateus
 */
public class Synctatic {

    private Semantic semantic;
    private List<Token> tokens;
    private Token token;
    private int depth;
    private String tipo;
    private List<String> synctacticErrors;
    private List<String> semanticErrors;

    public Synctatic() {
        tokens = new LinkedList<>();
        synctacticErrors = new ArrayList<>();
        semanticErrors = new ArrayList<>();
        semantic = new Semantic();
    }

    public void run() {
        token = tokens.get(0);
        switch (token.getTag()) {
            case Tag.APP:
                eat(Tag.APP);
                eat(Tag.IDENTIFIER);
                body();
                break;
            case Tag.COMENTARIO:
                eat(Tag.COMENTARIO);
                run();
                break;
            default:
                synctacticError("program", token.getLine());
        }
    }

    public void addToken(Token t) {
        this.tokens.add(t);
    }

    private void eat(String tag) {
        if (token.getTag().equals(tag)) {
            //System.out.println(token.getTag() + " : " + token.toString() + "\tLinha: " + token.getLine());
            advance();

        } else {
            System.out.println("Erro! Token não encontrado: " + tag);
            System.out.println("Token encontrado: " + token.getTag());
        }
    }

    private void advance() {
        if (tokens.size() > 1) {
            tokens.remove(0);
            token = tokens.get(0);
        } else if (tokens.size() == 1) {
            tokens.remove(0);
        } else {
            synctacticError("no tokens left", token.getLine());
            //System.exit(0);
        }
    }

    private void type() {
        switch (token.getTag()) {
            case Tag.INTEGER:
                tipo = Tag.INTEGER;
                eat(Tag.INTEGER);
                break;
            case Tag.REAL:
                tipo = Tag.REAL;
                eat(Tag.REAL);
                break;
            default:
                synctacticError("type", token.getLine());
                break;
        }
    }

    private void synctacticError(String producao, int linha) {
        synctacticErrors.add("Erro na análise sintática, linha" + linha + ": " + producao);
    }

    private void semanticError(String producao, int linha) {
        semanticErrors.add("Erro na análise semântica, linha" + linha + ": " + producao);
    }

    private void printMethod(String local) {
        for (int i = 0; i < depth - 1; i++) {
            System.out.print(" ");
        }
        System.out.println(local);
    }

    private void declList() {
        decl();
        switch (token.getTag()) {
            case Tag.PONTOVIRGULA:
                eat(Tag.PONTOVIRGULA);
                declList();
                break;
        }
    }

    private void stmtList() {
        stmt();
        switch (token.getTag()) {
            case Tag.PONTOVIRGULA:
                eat(Tag.PONTOVIRGULA);
                stmtList();

                break;
        }
    }

    private void body() {
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
                synctacticError("body", token.getLine());
                break;
        }
    }

    private void decl() {
        type();
        identList();
    }

    private void stmt() {
        switch (token.getTag()) {
            case Tag.IDENTIFIER: //assign-stmt

                eat(Tag.IDENTIFIER);
                eat(Tag.ATRIBUICAO);
                simpleExpr();
                break;
            case Tag.IF: //if-stmt
                eat(Tag.IF);
                expression();
                eat(Tag.THEN);
                stmtList();
                ifStmtB();
                eat(Tag.END);
                break;
            case Tag.WHILE:
                eat(Tag.WHILE);
                expression();
                eat(Tag.DO);
                stmtList();
                eat(Tag.END);
                break;
            case Tag.READ: //read-stmt
                eat(Tag.READ);
                eat(Tag.ABREPARENTESE);
                eat(Tag.IDENTIFIER);
                eat(Tag.FECHAPARENTESE);
                break;
            case Tag.WRITE: //write-stmt
                eat(Tag.WRITE);
                eat(Tag.ABREPARENTESE);
                writable();
                eat(Tag.FECHAPARENTESE);
                break;
            case Tag.REPEAT:
                eat(Tag.REPEAT);
                stmtList();
                stmtSuffix();
                break;
        }
    }

    private void identList() {

        switch (token.getTag()) {

            case Tag.IDENTIFIER:
                if (semantic.addIdentifier(token, tipo) == null) {
                    semanticError(token.getTag(), token.getLine());
                }
                eat(Tag.IDENTIFIER);
                while (token.getTag().equals(Tag.VIRGULA)) {
                    eat(Tag.VIRGULA);
                    if (semantic.addIdentifier(token, tipo) == null) {
                        semanticError(token.getTag(), token.getLine());
                    }
                    eat(Tag.IDENTIFIER);
                }
                break;
            default:
                synctacticError("ident-list", token.getLine());
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
                synctacticError("Constant", token.getLine());
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
                synctacticError("Operator", token.getLine());
                break;
        }
    }

    private void simpleExpr() {
        switch (token.getTag()) {
            case Tag.IDENTIFIER:
            case Tag.INTEGER_CONST:
            case Tag.FLOAT_CONST:
            case Tag.ABREPARENTESE:
            case Tag.OP_MULTIPLICACAO:
            case Tag.OP_DIVISAO:
            case Tag.OP_AND:
                term();
                simpleExpr();
                break;
            case Tag.EXCLAMACAO:
                eat(Tag.EXCLAMACAO);
                term();
                break;
            case Tag.TRACO:
                eat(Tag.TRACO);
                term();
                break;
            case Tag.OP_SOMA:
                eat(Tag.OP_SOMA);
                term();
                simpleExpr();
                break;
            case Tag.OP_SUBTRACAO:
                eat(Tag.OP_SUBTRACAO);
                term();
                simpleExpr();
                break;

        }
    }

    private void term() {
        switch (token.getTag()) {
            case Tag.IDENTIFIER:
                eat(Tag.IDENTIFIER);
                break;
            case Tag.FLOAT_CONST:
            case Tag.INTEGER_CONST:
                constant();
                break;
            case Tag.ABREPARENTESE:
                eat(Tag.ABREPARENTESE);
                expression();
                eat(Tag.FECHAPARENTESE);
                break;
            case Tag.OP_MULTIPLICACAO:
                eat(Tag.OP_MULTIPLICACAO);
                term();
                term();
                break;
            case Tag.OP_DIVISAO:
                eat(Tag.OP_DIVISAO);
                term();
                term();
                break;
            case Tag.OP_AND:
                eat(Tag.OP_AND);
                term();
                term();
                break;
        }
    }

    private void expression() {
        simpleExpr();
        switch (token.getTag()) {
            case Tag.OP_COMPARA:
            case Tag.OP_GT:
            case Tag.OP_GTE:
            case Tag.OP_LT:
            case Tag.OP_LTE:
            case Tag.OP_NOTEQUAL:
                operator();
                simpleExpr();
                break;

        }
    }

    private void writable() {
        switch (token.getTag()) {
            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;
            case Tag.IDENTIFIER:
            case Tag.INTEGER_CONST:
            case Tag.FLOAT_CONST:
            case Tag.ABREPARENTESE:
            case Tag.EXCLAMACAO:
            case Tag.TRACO:
            case Tag.OP_SOMA:
                simpleExpr();
                break;
        }
    }

    private void stmtSuffix() {
        eat(Tag.UNTIL);
        expression(); //condition ::= expression
    }

    private void ifStmtB() {
        switch (token.getTag()) {
            case Tag.ELSE:
                eat(Tag.ELSE);
                stmtList();
                break;
            case Tag.END:
                break;
        }
    }

    public List<String> getSyntacticErrors() {
        return synctacticErrors;
    }

    public void setSyntacticErrors(List<String> syntacticErrors) {
        this.synctacticErrors = syntacticErrors;
    }

    public List<String> getSemanticErrors() {
        return semanticErrors;
    }

    public void setSemanticErrors(List<String> semanticErrors) {
        this.semanticErrors = semanticErrors;
    }

}
