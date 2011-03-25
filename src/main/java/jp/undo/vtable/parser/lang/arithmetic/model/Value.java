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
package jp.undo.vtable.parser.lang.arithmetic.model;

import java.text.MessageFormat;

/**
 * 値。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Value implements Expression {

    private final String token;

    /**
     * インスタンスを生成する。
     * @param token 値を表現する文字列
     * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
     */
    public Value(String token) {
        super();
        if (token == null) {
            throw new IllegalArgumentException("token is null"); //$NON-NLS-1$
        }
        this.token = token;
    }

    /**
     * 値を表現する文字列を返す。
     * @return 値を表現する文字列
     */
    public String getToken() {
        return token;
    }

    public <R, C, E extends Throwable> R accept(
            ExpressionVisitor<R, C, E> visitor, C context) throws E {
        if (visitor == null) {
            throw new IllegalArgumentException("visitor is null"); //$NON-NLS-1$
        }
        return visitor.visitValue(this, context);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + token.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Value other = (Value) obj;
        if (token.equals(other.token) == false) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("new Value(\"{0}\")", getToken());
    }
}
