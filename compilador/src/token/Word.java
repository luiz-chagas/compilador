/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import tag.Tag;

/**
 *
 * @author luiz
 */
public class Word extends Token {

    private String lexeme = "";

    public static final Word w_pontovirgula = new Word(";", Tag.PONTOVIRGULA);
    public static final Word w_virgula = new Word(",", Tag.VIRGULA);

    public static final Word w_and = new Word("and", Tag.OP_AND);
    public static final Word w_or = new Word("or", Tag.OP_OR);

    public static final Word w_program = new Word("app", Tag.APP);
    //public static final Word w_declare = new Word("declare", Tag.DECLARE);
    public static final Word w_start = new Word("start", Tag.START);
    public static final Word w_end = new Word("end", Tag.END);
    public static final Word w_int = new Word("int", Tag.INTEGER);
    //public static final Word w_string = new Word("string", Tag.STRING);
    public static final Word w_if = new Word("if", Tag.IF);
    public static final Word w_then = new Word("then", Tag.THEN);
    public static final Word w_else = new Word("else", Tag.ELSE);
    //public static final Word w_do = new Word("do", Tag.DO);
    public static final Word w_while = new Word("while", Tag.WHILE);
    public static final Word w_read = new Word("read", Tag.READ);
    public static final Word w_write = new Word("write", Tag.WRITE);

    public static final Word w_sum = new Word("+", Tag.OP_SOMA);
    public static final Word w_sub = new Word("-", Tag.OP_SUBTRACAO);
    public static final Word w_mult = new Word("*", Tag.OP_MULTIPLICACAO);
    public static final Word w_div = new Word("/", Tag.OP_DIVISAO);

    public Word(String s, String tag) {
        super(tag);
        lexeme = s;
    }

    @Override
    public String toString() {
        return "<(" + super.tag + "),(" + lexeme + ")>";
    }

    /**
     * @return the lexeme
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * @param lexeme the lexeme to set
     */
    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

}
