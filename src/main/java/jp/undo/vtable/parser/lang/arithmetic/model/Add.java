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
 * 加算式。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Add implements Expression {

    private final Expression first;

    private final Expression second;

    /**
     * インスタンスを生成する。
     * @param first 第1演算数
     * @param second 第2演算数
     * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
     */
    public Add(Expression first, Expression second) {
        super();
        if (first == null) {
            throw new IllegalArgumentException("first is null"); //$NON-NLS-1$
        }
        if (second == null) {
            throw new IllegalArgumentException("second is null"); //$NON-NLS-1$
        }
        this.first = first;
        this.second = second;
    }

    /**
     * 第1演算数を返す。
     * @return 第1演算数
     */
    public Expression getFirst() {
        return first;
    }

    /**
     * 第2演算数を返す。
     * @return 第2演算数
     */
    public Expression getSecond() {
        return second;
    }

    public <R, C, E extends Throwable> R accept(
            ExpressionVisitor<R, C, E> visitor, C context) throws E {
        if (visitor == null) {
            throw new IllegalArgumentException("visitor is null"); //$NON-NLS-1$
        }
        return visitor.visitAdd(this, context);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + first.hashCode();
        result = prime * result + second.hashCode();
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
        Add other = (Add) obj;
        if (first.equals(other.first) == false) {
            return false;
        }
        if (second.equals(other.second) == false) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("new Add({0}, {1})", getFirst(), getSecond());
    }
}
