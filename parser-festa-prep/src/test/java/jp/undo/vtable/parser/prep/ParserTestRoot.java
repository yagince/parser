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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;

import jp.undo.vtable.parser.util.ParseException;

/**
 * Abstract super class of parser unit testing.
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 * @param <T> パーサが返すオブジェクトの型
 */
public abstract class ParserTestRoot<T> {

    private String testTitle;
    
    /**
     * このテストを失敗させる。
     * @param expected 期待した値
     * @param actual 実際の値
     */
    protected void fail(String expected, String actual) {
        assertEquals(testTitle, expected, actual);
    }
    
    /**
     * テスト対象のを解析して、モデルを返す。
     * @param input テストデータ
     * @return 解析したモデル
     * @throws IOException 入力の読み出しに失敗した場合
     * @throws ParseException 構文解析に失敗した場合
     */
    protected abstract T parse(Reader input) throws IOException;
    
    /**
     * テスト対象の入力を解析して、モデルを返す。
     * @param title テストタイトル
     * @param input 入力文字列
     * @return 構文解析の結果
     * @throws AssertionError 何らかの例外が発生した場合
     */
    protected T test(String title, String input) {
        try {
            Reader reader = prepare(title, input);
            return parse(reader);
        }
        catch (Exception e) {
            throw (AssertionError) new AssertionError(title).initCause(e);
        }
    }

    /**
     * テスト対象の入力を解析して、構文解析を行い構文解析エラーを捕捉する。
     * @param title テストタイトル
     * @param input 入力文字列
     * @throws AssertionError
     *     {@link ParseException}以外の例外が発生した場合、
     *     または構文解析中に例外が一つも発生しなかった場合
     */
    protected void error(String title, String input) {
        try {
            Reader reader = prepare(title, input);
            T result = parse(reader);
            throw new AssertionError(MessageFormat.format(
                "\"{0}\" expected ParseException, but {1}",
                title,
                result));
        }
        catch (ParseException e) {
            return; // ok.
        }
        catch (Exception e) {
            throw (AssertionError) new AssertionError(title).initCause(e);
        }
    }

    private Reader prepare(String title, String input) {
        assertThat(title, is(not(nullValue())));
        assertThat(input, is(not(nullValue())));
        testTitle = title;
        return new StringReader(input);
    }
}
