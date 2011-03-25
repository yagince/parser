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
 * {@link Expression}を実際に数式として評価し、結果の数値を返す。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public enum Evaluator implements ExpressionVisitor<Integer, Void, NoThrow> {

    /**
     * 唯一のインスタンス。
     */
    INSTANCE,
    ;
    
    /**
     * 指定の式を数式として評価した結果を返す。
     * @param expression 対象の数式
     * @return 評価結果
     */
    public static int eval(Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("expression is null");
        }
        return expression.accept(INSTANCE, null);
    }
    
    public Integer visitValue(Value model, Void context) {
        return Integer.parseInt(model.getToken());
    }

    public Integer visitAdd(Add model, Void context) {
        Integer first = model.getFirst().accept(this, context);
        Integer second = model.getSecond().accept(this, context);
        return first + second;
    }

    public Integer visitSubtract(Subtract model, Void context) {
        Integer first = model.getFirst().accept(this, context);
        Integer second = model.getSecond().accept(this, context);
        return first - second;
    }

    public Integer visitMultiply(Multiply model, Void context) {
        Integer first = model.getFirst().accept(this, context);
        Integer second = model.getSecond().accept(this, context);
        return first * second;
    }

    public Integer visitDivide(Divide model, Void context) {
        Integer first = model.getFirst().accept(this, context);
        Integer second = model.getSecond().accept(this, context);
        return first / second;
    }

    public Integer visitParenthesized(Parenthesized model, Void context) {
        Integer expression = model.getExpression().accept(this, context);
        return expression;
    }
    
    public Integer visitPlus(Plus model, Void context) {
        Integer operand = model.getOperand().accept(this, context);
        return +operand;
    }
    
    public Integer visitMinus(Minus model, Void context) throws NoThrow {
        Integer operand = model.getOperand().accept(this, context);
        return -operand;
    }
}
