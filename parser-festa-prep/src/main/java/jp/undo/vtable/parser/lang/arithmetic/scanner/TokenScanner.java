/*
 * Copyright 2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.undo.vtable.parser.lang.arithmetic.scanner;

import java.util.ArrayList;
import java.util.List;

import jp.undo.vtable.parser.util.CharacterSource;

/**
 * 文字の列を解析して、四則演算言語のトークンを抽出するスキャナ。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class TokenScanner {
    
    private static final Token EOF = new Token(TokenKind.EOF, "");
    
    private final CharacterSource source;
    
    private Token[] content;
    
    private int cursor;
    
    /**
     * インスタンスを生成する。
     * @param source トークンの元になる文字の列を提供するオブジェクト
     * @throws IllegalArgumentException 引数に{@code null}が指定された場合
     */
    public TokenScanner(CharacterSource source) {
        super();
        if (source == null) {
            throw new IllegalArgumentException("source is null"); //$NON-NLS-1$
        }
        this.source = source;
        this.content = null;
        this.cursor = -1;
    }
    
    /**
     * 現在のトークンを返す。
     * <p>
     * この呼び出しは、{@link #get(int) get(0)}と等しい。
     * </p>
     * @return 現在のトークン、全ての入力を処理し終わっている場合は終端を表現するトークン
     * @see #consume()
     */
    public Token get() {
        return get(0);
    }
    
    /**
     * 現在のトークンから、指定の個数だけ先のトークンを返す。
     * <p>
     * 引数に{@code 0}が指定された場合、この呼び出しは現在のトークンを返す。
     * </p>
     * <p>
     * そのようなトークンが存在しない場合、この呼び出しは終端を表現するトークンを返す。
     * </p>
     * @param k 先読みする個数
     * @return 指定の個数だけ先のトークン、存在しない場合は終端を表現するトークン
     * @see #consume()
     */
    public Token get(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k < 0");
        }
        prepare();
        if (isEof(k)) {
            return EOF;
        }
        return content[cursor + k];
    }

    /**
     * 現在のトークンを捨てる。
     * <p>
     * 次に{@link #get()}を呼び出した場合、現在までのトークンの次のトークンが返される。
     * </p>
     * <p>
     * 全ての入力を処理し終わっている場合、この呼び出しは何も行わない。
     * </p>
     * @return 現在のトークン、全ての入力を処理し終わっている場合は終端を表現するトークン
     * @see #get()
     */
    public Token consume() {
        prepare();
        if (isEof(0)) {
            return EOF;
        }
        return content[cursor++];
    }

    private boolean isEof(int k) {
        return cursor + k >= content.length;
    }

    private void prepare() {
        if (cursor >= 0) {
            return;
        }
        List<Token> list = new ArrayList<Token>();
        while (true) {
            Token token = fetch();
            if (token == null) {
                break;
            }
            list.add(token);
        }
        cursor = 0;
        content = list.toArray(new Token[list.size()]);
    }

    private Token fetch() {
        consumeWhiteSpace();
        int head = source.get();
        
        if (isDigit(head)) {
            return fetchNumber();
        }
        switch (head) {
        // symbols
        case '+': return fetchSymbol(TokenKind.PLUS);
        case '-': return fetchSymbol(TokenKind.MINUS);
        case '*': return fetchSymbol(TokenKind.ASTERISK);
        case '/': return fetchSymbol(TokenKind.SLASH);
        case '^': return fetchSymbol(TokenKind.CARET);
        case '(': return fetchSymbol(TokenKind.OPEN_PAREN);
        case ')': return fetchSymbol(TokenKind.CLOSE_PAREN);
            
        // eof
        case CharacterSource.EOF :
            return null;
        
        // erroneous
        default:
            return fetchSymbol(TokenKind.UNKNOWN);
        }
    }

    private static boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    private void consumeWhiteSpace() {
        while (true) {
            int c = source.get();
            if (Character.isWhitespace(c) == false) {
                break;
            }
            source.consume();
        }
    }

    private Token fetchSymbol(TokenKind kind) {
        assert kind != null;
        int symbol = source.get();
        source.consume();
        return new Token(kind, String.valueOf((char) symbol));
    }

    private Token fetchNumber() {
        StringBuilder buf = new StringBuilder();
        while (true) {
            int digit = source.get();
            if (isDigit(digit) == false) {
                break;
            }
            buf.append((char) digit);
            source.consume();
        }
        assert buf.length() >= 1;
        return new Token(TokenKind.NUMBER, buf.toString());
    }
    
    @Override
    public String toString() {
        prepare();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < cursor; i++) {
            buf.append(content[i]);
            buf.append(" ");
        }
        buf.append("^");
        for (int i = cursor; i < content.length; i++) {
            buf.append(content[i]);
            buf.append(" ");
        }
        buf.append("(EOF)");
        return buf.toString();
    }
}
