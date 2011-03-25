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

/**
 * 式。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public interface Expression {

    /**
     * 指定のビジタを受け入れ、対応するビジタ内のメソッドを呼び出す。
     * @param <R> ビジタの実行結果
     * @param <C> ビジタに渡す引数の型
     * @param <E> ビジタの例外型
     * @param visitor 対象のビジタ
     * @param context ビジタに渡す引数(省略可)
     * @return ビジタの実行結果
     * @throws E ビジタ内で例外が発生した場合
     * @throws IllegalArgumentException {@code visitor}に{@code null}が指定された場合
     */
    <R, C, E extends Throwable> R accept(
            ExpressionVisitor<R, C, E> visitor, C context) throws E;
}
