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

/**
 * 四則演算言語のトークンを表現する{@link Token}の種類。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public enum TokenKind {
    
    /**
     * 数値。
     */
    NUMBER,
    
    /**
     * 加算({@code "+"})。
     */
    PLUS,
    
    /**
     * 減算({@code "-"})。
     */
    MINUS,
    
    /**
     * 乗算({@code "*"})。
     */
    ASTERISK,
    
    /**
     * 除算({@code "/"})。
     */
    SLASH,
    
    /**
     * 累乗({@code "^"})。
     */
    CARET,
    
    /**
     * 開きカッコ({@code "("})。
     */
    OPEN_PAREN,
    
    /**
     * 閉じカッコ({@code ")"})。
     */
    CLOSE_PAREN,
    
    /**
     * 入力の終端。
     */
    EOF,
    
    /**
     * 正しく解析できなかったトークン。
     */
    UNKNOWN,
    ;
}
