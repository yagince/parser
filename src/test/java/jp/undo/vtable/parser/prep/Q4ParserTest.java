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

import jp.undo.vtable.parser.lang.arithmetic.engine.InfixEmitter;
import jp.undo.vtable.parser.lang.arithmetic.model.Add;
import jp.undo.vtable.parser.lang.arithmetic.model.Expression;
import jp.undo.vtable.parser.lang.arithmetic.model.Value;

import org.junit.Test;

/**
 * Test for {@link Q0Parser}.
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Q4ParserTest extends ParserTestRoot<Expression> {

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q1Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Add0() throws Exception {
        verify(
            test("加算なし", "1"),
            new Value("1"));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q4Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Add1() throws Exception {
        verify(
            test("加算1つ", "1 + 2"),
            new Add(new Value("1"), new Value("2")));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q4Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Add2() throws Exception {
        verify(
            test("加算2つ", "1 + 2 + 3"),
            new Add(
                new Add(new Value("1"), new Value("2")),
                new Value("3")));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q4Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Add4() throws Exception {
        verify(
            test("加算4つ", "1 + 2 + 3 + 4 + 5"),
            new Add(
                new Add(
                    new Add(
                        new Add(new Value("1"), new Value("2")),
                        new Value("3")),
                    new Value("4")),
                new Value("5")));
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q4Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Less() throws Exception {
        error("演算数少なすぎ", "1 +");
    }

    /**
     * Test method for {@link jp.undo.vtable.parser.prep.Q4Parser#parse()}.
     * @throws Exception 任意の例外が発生した場合
     */
    @Test
    public void testParse_Extra() throws Exception {
        error("演算数多すぎ", "1 + 2 3");
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
        error("第2演算数が演算子になってる", "1 + +");
    }
    
    private void verify(Expression actual, Expression expect) {
        if (expect.equals(actual) == false) {
            fail(InfixEmitter.emit(expect), InfixEmitter.emit(actual));
        }
    }
    
    @Override
    protected Expression parse(Reader input) throws IOException {
        Q4Parser parser = new Q4Parser(input);
        return parser.parse();
    }
}
