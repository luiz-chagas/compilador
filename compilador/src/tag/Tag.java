/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tag;

/**
 *
 * @author luiz
 */
public class Tag {

    public static final String // PONTUACAO
            PONTOVIRGULA = "PONTOVIRGULA",
            VIRGULA = "VIRGULA", DOISPONTOS = "DOISPONTOS", ABREP = "ABREP", FECHAP = "FECHAP",
            ABREASPAS = "ABREASPAS", FECHAASPAS = "FECHAASPAS",
            //PALAVRAS RESERVADAS
            PROGRAM = "PROGRAM", DECLARE = "DECLARE", START = "START", END = "END", INT = "INT", STRING = "STRING", IF = "IF",
            THEN = "THEN",
            ELSE = "ELSE", DO = "DO", WHILE = "WHILE", READ = "READ", WRITE = "WRITE",
            //OPERADORES L�GICOS
            OR = "OR", AND = "AND", RECEBE = "RECEBE",
            //OPERADORES RELACIONAIS
            IGUAL = "IGUAL", MENOR = "MENOR", MENORIGUAL = "MENORIGUAL", MAIOR = "MAIOR", MAIORIGUAL = "MAIORIGUAL", DIFERENTE = "DIFERENTE",
            //OPERADORES MATEM�TICOS
            MAIS = "MAIS", MENOS = "MENOS", BARRA = "BARRA", VEZES = "VEZES",
            //OUTROS
            INTEGER_CONST = "INTEGER_CONST", LITERAL = "LITERAL", IDENTIFIER = "IDENTIFIER", COMENTARIO = "COMENTARIO",
            //DESCONHECIDO

            DESCONHECIDO = "DESCONHECIDO";

}
