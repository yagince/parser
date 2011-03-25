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
 * {@link Expression}の構造を渡り歩くビジタ。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 * @param <R> それぞれのメソッドの戻り値型
 * @param <C> それぞれのメソッドの引数型
 * @param <E> それぞれのメソッドの例外型
 */
public interface ExpressionVisitor<R, C, E extends Throwable> {
    
    /**
     * {@code Value#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitValue(Value model, C context) throws E;

    /**
     * {@code Add#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitAdd(Add model, C context) throws E;
    
    /**
     * {@code Subtract#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitSubtract(Subtract model, C context) throws E;
    
    /**
     * {@code Multiply#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitMultiply(Multiply model, C context) throws E;
    
    /**
     * {@code Divide#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitDivide(Divide model, C context) throws E;
    
    /**
     * {@code Parenthesized#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitParenthesized(Parenthesized model, C context) throws E;
    
    /**
     * {@code Parenthesized#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitPlus(Plus model, C context) throws E;
    
    /**
     * {@code Parenthesized#accept(ExpressionVisitor, Object)}が呼び出された際に実行される。
     * @param model
     *     {@code Expression#accept(ExpressionVisitor, Object)}が
     *     呼び出されたモデルオブジェクト
     * @param context
     *     {@code Expression#accept(ExpressionVisitor, Object)}の
     *     第2引数に渡された値
     * @return
     *     このメソッドの実行結果
     * @throws E
     *     このメソッドで指定の例外がスローされた場合
     */
    R visitMinus(Minus model, C context) throws E;
}
