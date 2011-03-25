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
package jp.undo.vtable.parser.lang.arithmetic.engine;

import jp.undo.vtable.parser.lang.arithmetic.model.Add;
import jp.undo.vtable.parser.lang.arithmetic.model.Divide;
import jp.undo.vtable.parser.lang.arithmetic.model.Expression;
import jp.undo.vtable.parser.lang.arithmetic.model.ExpressionVisitor;
import jp.undo.vtable.parser.lang.arithmetic.model.Minus;
import jp.undo.vtable.parser.lang.arithmetic.model.Multiply;
import jp.undo.vtable.parser.lang.arithmetic.model.Parenthesized;
import jp.undo.vtable.parser.lang.arithmetic.model.Plus;
import jp.undo.vtable.parser.lang.arithmetic.model.Subtract;
import jp.undo.vtable.parser.lang.arithmetic.model.Value;
import jp.undo.vtable.parser.util.NoThrow;

/**
 * {@link Expression}を中置表現で文字列にする。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public enum InfixEmitter implements
        ExpressionVisitor<Void, StringBuilder, NoThrow> {

    /**
     * 唯一のインスタンス。
     */
    INSTANCE,
    ;
    
    /**
     * 指定の式をこのクラスのインスタンスで文字列化した結果を返す。
     * @param expression 対象の式
     * @return 対応する文字列
     * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
     */
    public static String emit(Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("expression is null");
        }
        StringBuilder buf = new StringBuilder();
        expression.accept(INSTANCE, buf);
        return buf.toString();
    }

    public Void visitValue(Value model, StringBuilder context) {
        context.append(model.getToken());
        return null;
    }

    public Void visitAdd(Add model, StringBuilder context) {
        model.getFirst().accept(this, context);
        context.append(" + ");
        model.getSecond().accept(this, context);
        return null;
    }

    public Void visitSubtract(Subtract model, StringBuilder context) {
        model.getFirst().accept(this, context);
        context.append(" - ");
        model.getSecond().accept(this, context);
        return null;
    }

    public Void visitMultiply(Multiply model, StringBuilder context) {
        model.getFirst().accept(this, context);
        context.append(" * ");
        model.getSecond().accept(this, context);
        return null;
    }

    public Void visitDivide(Divide model, StringBuilder context) {
        model.getFirst().accept(this, context);
        context.append(" / ");
        model.getSecond().accept(this, context);
        return null;
    }

    public Void visitParenthesized(Parenthesized model, StringBuilder context) {
        context.append("(");
        model.getExpression().accept(this, context);
        context.append(")");
        return null;
    }
    
    public Void visitPlus(Plus model, StringBuilder context) {
        context.append("+");
        model.getOperand().accept(this, context);
        return null;
    }
    
    public Void visitMinus(Minus model, StringBuilder context) {
        context.append("-");
        model.getOperand().accept(this, context);
        return null;
    }
}
