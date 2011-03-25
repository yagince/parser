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
package jp.undo.vtable.parser.prep;

import java.io.IOException;
import java.io.Reader;

import jp.undo.vtable.parser.lang.arithmetic.engine.PrefixEmitter;
import jp.undo.vtable.parser.lang.arithmetic.model.Add;
import jp.undo.vtable.parser.lang.arithmetic.model.Expression;
import jp.undo.vtable.parser.lang.arithmetic.model.Subtract;
import jp.undo.vtable.parser.lang.arithmetic.model.Value;

import org.junit.Test;

/**
 * Test for {@link Q1Parser}.
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Q1ParserTest extends ParserTestRoot<Expression> {

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Number() throws Exception {
        verify(
            test("数値のみ", "1"),
            new Value("1"));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Add() throws Exception {
        verify(
            test("加算テスト", "+ 1 2"),
            new Add(new Value("1"), new Value("2")));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Sub() throws Exception {
        verify(
            test("減算テスト", "- 1 2"),
            new Subtract(new Value("1"), new Value("2")));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_DeepFirst() throws Exception {
        verify(
            test("第1演算数を数式に", "+ - 1 2 3"),
            new Add(
                new Subtract(new Value("1"), new Value("2")),
                new Value("3")));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_DeepSecond() throws Exception {
        verify(
            test("第2演算数を数式に", "- 1 + 2 3"),
            new Subtract(
                new Value("1"),
                new Add(new Value("2"), new Value("3"))));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_DeepBoth() throws Exception {
        verify(
            test("両演算数を数式に", "+ + 1 2 - 3 4"),
            new Add(
                new Add(new Value("1"), new Value("2")),
                new Subtract(new Value("3"), new Value("4"))));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_VeryDeep() throws Exception {
        verify(
            test("深さ4の演算子", "+ + + + 1 2 3 4 - 5 - 6 - 7 8"),
            new Add(
                new Add(
                    new Add(
                        new Add(new Value("1"), new Value("2")),
                        new Value("3")),
                    new Value("4")),
                new Subtract(
                    new Value("5"),
                    new Subtract(
                        new Value("6"),
                        new Subtract(new Value("7"), new Value("8"))))));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Less() throws Exception {
        error("演算数少なすぎ", "+ 1");
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Extra() throws Exception {
        error("演算数多すぎ", "+ 1 2 3");
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q0Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_InvalidOperator() throws Exception {
        error("演算子が演算数になってる", "1 2 3");
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q0Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_InvalidFirst() throws Exception {
        error("第1演算数が演算子になってる", "+ + 2");
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q0Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_InvalidSecond() throws Exception {
        error("第2演算数が演算子になってる", "+ 1 +");
    }
    
    private void verify(Expression actual, Expression expect) {
        if (expect.equals(actual) == false) {
            fail(PrefixEmitter.emit(expect), PrefixEmitter.emit(actual));
        }
    }
    
    @Override
    protected Expression parse(Reader input) throws IOException {
        Q1Parser parser = new Q1Parser(input);
        return parser.parse();
    }
}
