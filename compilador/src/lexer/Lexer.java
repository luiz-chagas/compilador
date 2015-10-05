/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import tag.Tag;
import token.Comentario;
import token.Float_const;
import token.Integer_const;
import token.Token;
import token.Word;

/**
 *
 * @author luiz
 */
public class Lexer {

    public static int line = 1; // contador de linhas

    private static final int VAZIO = 65535, TKDESC = 1, IDLONGA = 2, COMABERTO = 3;
    public static final String SVAZIO = "65535";

    private char caracterAtual = ' '; // caractere lido do arquivo
    private String fileName;
    private FileReader file;
    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    /*
     * Metodo para inserir palavras reservadas na HashTable
     */
    private void reserve(Word w) {
        words.put(w.getLexeme(), w); // lexema eh a chave para entrada na HashTable
    }

    /*
     * Metodo construtor
     */
    public Lexer(String fileName) throws FileNotFoundException {
        try {
            this.fileName = fileName;
            this.file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado");
            throw e;
        }

        // Insere palavras reservadas na HashTable
        reserve(new Word("app", Tag.APP));
        reserve(new Word("start", Tag.START));
        reserve(new Word("end", Tag.END));
        reserve(new Word("int", Tag.INTEGER));
        reserve(new Word("real", Tag.REAL));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("then", Tag.THEN));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("repeat", Tag.REPEAT));
        reserve(new Word("until", Tag.REPEAT));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));
        reserve(new Word("or", Tag.OP_OR));
        reserve(new Word("and", Tag.OP_AND));
        reserve(new Word("stop", Tag.STOP));
    }

    /*
     * Le o proximo caractere do arquivo
     */
    private void readch() throws IOException {
        caracterAtual = (char) file.read();

    }

    /*
     * Le o proximo caractere do arquivo e verifica se e igual a c
     * @return boolean
     * @param char c
     */
    private boolean readch(char c) throws IOException {
        readch();
        if (caracterAtual != c) {
            return false;
        }
        caracterAtual = ' ';
        return true;
    }

    /*
     * Trata erro
     */
    private void erro(int tipo) {
        if (tipo == TKDESC) {
            System.out.println("\t !!!!!ERRO (TOKEN DESCONHECIDO)!!!!!");
        } else if (tipo == IDLONGA) {
            System.out.println("\t !!!!!ERRO (ID MAIOR QUE 20 CARACTERES)!!!!!");
        } else if (tipo == COMABERTO) {
            System.out.println("\t !!!!!ERRO (COMENTARIO SEM FECHAMENTO)!!!!!");
        }
    }

    /*
     * Desconsidera delimitadores na entrada
     */
    private void desconsideraDelimitadores() throws IOException {

        for (;; readch()) {
            if (caracterAtual == ' ' || caracterAtual == '\t' || caracterAtual == '\r' || caracterAtual == '\b') {
                continue;
            } else if (caracterAtual == '\n') {
                line++;
            } else {
                break;
            }
        }
    }

    /*
     * Escaneia o codigo
     */
    public Token scan() throws IOException {

        desconsideraDelimitadores();
        
        // Operadores e pontuacao
        switch (caracterAtual) {
            case ';': {
                readch();
                return new Token(Tag.PONTOVIRGULA);
            }
            case ',': {
                readch();
                return new Token(Tag.VIRGULA);
            }
            case '(': {
                readch();
                return new Token(Tag.ABREPARENTESE);
            }
            case ')': {
                readch();
                return new Token(Tag.FECHAPARENTESE);
            }
            case '{': {
                readch();
                return new Token(Tag.ABRECHAVE);
            }
            case '}': {
                readch();
                return new Token(Tag.FECHACHAVE);
            }
            case '"': {
                readch();
                return new Token(Tag.ABREASPAS);
            }
            //case '"': {
            //    readch();
            //    return new Token(Tag.FECHAASPAS);
            //}
            case ':': {
                if (readch('=')) {
                    readch();
                    return new Token(Tag.ATRIBUICAO);
                }
                readch();
                return new Token(Tag.DOISPONTOS);
            }
            case '+': {
                readch();
                return new Token(Tag.OP_SOMA);
            }
            case '-': {
                readch();
                return new Token(Tag.OP_SUBTRACAO);
            }
            case '/': {
                if (readch('/')) {
                    StringBuilder sb = new StringBuilder();
                    sb.append('/');
                    sb.append('/');
                    while (caracterAtual != '\n') {
                        readch();
                        sb.append(caracterAtual);
                    }
                    return new Comentario(sb.toString(), Tag.COMENTARIO);
                } else if (caracterAtual == '*') {
                    StringBuilder sb = new StringBuilder();
                    sb.append('/');
                    sb.append('*');
                    readch();
                    while (caracterAtual != '/') {
                        while (caracterAtual != '*') {

                            sb.append(caracterAtual);
                            readch();
                            if (!Character.isDefined(caracterAtual)) {
                                break;
                            }
                        }

                        if (caracterAtual == '*') {
                            sb.append(caracterAtual);
                        }

                        readch();

                        if (!Character.isDefined(caracterAtual)) {
                            erro(COMABERTO);
                            return new Comentario(sb.toString(), Tag.COMENTARIO);
                        }
                    }
                    sb.append('/');
                    readch();
                    return new Comentario(sb.toString(), Tag.COMENTARIO);

                } else {
                    return new Token(Tag.OP_DIVISAO);
                }
            }
            case '*': {
                readch();
                return new Token(Tag.OP_MULTIPLICACAO);
            }
            case '=':
                if (readch('=')) {
                    return new Token(Tag.OP_COMPARA);
                }
            case '<':
                if (readch('=')) {
                    return new Token(Tag.OP_LTE);
                } else if (caracterAtual == '>') {
                    readch();
                    return new Token(Tag.OP_NOTEQUAL);
                } else {
                    readch();
                    return new Token(Tag.OP_LT);
                }
            case '>':
                if (readch('=')) {
                    return new Token(Tag.OP_GTE);
                } else {
                    return new Token(Tag.OP_GT);
                }
        }

        // Numeros
        if (Character.isDigit(caracterAtual)) {
            float value = 0;
            boolean isFloat = false;
            int casaDecimal = 1;
            do {
                if (caracterAtual == '.') {
                    isFloat = true;
                } else {
                    if (isFloat) {
                        value += Character.digit(caracterAtual, 10)
                                / (float) (10 * casaDecimal);
                        casaDecimal *= 10;
                    } else {
                        value = 10 * value + Character.digit(caracterAtual, 10);
                    }
                }
                readch();
            } while (Character.isDigit(caracterAtual) || caracterAtual == '.');
            if (isFloat) {
                return new Float_const(value);
            }
            return new Integer_const((int) value);
        }
        // Identificadores
        if (Character.isLetter(caracterAtual) || caracterAtual == '_') {
            StringBuilder sb = new StringBuilder();
            int contador = 0;
            do {
                sb.append(caracterAtual);
                contador++;
                readch();
            } while (Character.isLetterOrDigit(caracterAtual));

            String s = sb.toString();
            Word w = words.get(s);

            if (w != null) {

                return w; // palavra ja existe na w; //palavra ja existe na HashTable HashTable
            }
            if (contador > 20) {
                erro(IDLONGA);
            }
            w = new Word(s, Tag.IDENTIFIER);

            words.put(s, w);
            return w;
        }
        // Caracteres nao especificados

        if (caracterAtual == VAZIO) {
            Token t = new Token(String.valueOf((int) caracterAtual));
            caracterAtual = ' ';
            return t;
        } else {
            Token t = new Token(String.valueOf(caracterAtual));
            erro(TKDESC);
            caracterAtual = ' ';
            return t;
        }
        // System.out.println((int) caracterAtual);

    }

    /**
     * @return the file
     */
    public FileReader getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(FileReader file) {
        this.file = file;
    }

    /**
     * @return the words
     */
    public Hashtable<String, Word> getWords() {
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(Hashtable<String, Word> words) {
        this.words = words;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {

        File arquivo = new File(fileName);

        int tamanho = 0;

        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(arquivo));

            while (reader.read() != -1) {
                tamanho++;

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tamanho;
    }

}
