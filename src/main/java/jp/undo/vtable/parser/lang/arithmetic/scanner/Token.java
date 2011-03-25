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

import java.text.MessageFormat;

/**
 * 字句解析によって識別された四則演算言語のトークン。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Token {
    
    /**
     * このトークンの種類。
     */
    public final TokenKind kind;
    
    /**
     * このトークンを構成する文字列。
     */
    public final String image;

    /**
     * インスタンスを生成する。
     * @param kind このトークンの種類
     * @param image このトークンを構成する文字列
     * @throws IllegalArgumentException 引数に{@code null}が指定された場合
     */
    public Token(TokenKind kind, String image) {
        super();
        if (kind == null) {
            throw new IllegalArgumentException("kind is null"); //$NON-NLS-1$
        }
        if (image == null) {
            throw new IllegalArgumentException("image is null"); //$NON-NLS-1$
        }
        this.kind = kind;
        this.image = image;
    }
    
    @Override
    public String toString() {
        return MessageFormat.format("{0}[\"{1}\"]", kind, image);
    }
}
