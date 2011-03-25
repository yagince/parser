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
import java.io.StringReader;

import jp.undo.vtable.parser.lang.arithmetic.engine.Evaluator;
import jp.undo.vtable.parser.lang.arithmetic.model.Add;
import jp.undo.vtable.parser.lang.arithmetic.model.Expression;
import jp.undo.vtable.parser.lang.arithmetic.model.Value;
import jp.undo.vtable.parser.lang.arithmetic.scanner.Token;
import jp.undo.vtable.parser.lang.arithmetic.scanner.TokenKind;
import jp.undo.vtable.parser.lang.arithmetic.scanner.TokenScanner;
import jp.undo.vtable.parser.util.CharacterSource;
import jp.undo.vtable.parser.util.ParseException;

/**
 * サンプル用の構文解析器。
 * @version $Date: 2009-06-12 18:45:35 +0900 (金, 12 6 2009) $
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class ExampleParser {
    
    private final TokenScanner scanner;

    /**
     * インスタンスを生成する。
     * @param reader 解析対象の文字列を含むストリーム
     * @throws IOException ストリームの読み出しに失敗した場合
     * @throws IllegalArgumentException 引数に{@code null}が含まれる場合
     */
    public ExampleParser(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("reader is null"); //$NON-NLS-1$
        }
        this.scanner = new TokenScanner(new CharacterSource(reader));
    }
    
    /**
     * この構文解析器に渡された入力を解析し、解析結果のモデルオブジェクトを返す。
     * <p>
     * 次の{@code Expression}をゴール記号とする構文を解析する。
     * </p>
     * <pre><code>
     * Expression ::= Value$a '+' Value$b ; new Add(a, b)
     *      Value ::= NUMBER$t            ; new Value(t.image)
     * </code></pre>
     * <p>
     * ただし、それぞれの構文アクションに出現する型名は
     * {@code jp.undo.vtable.parser.lang.arithmetic.model}パッケージ下に
     * 宣言されたクラスを利用するものとする。
     * </p>
     * @return 解析結果の式
     * @throws ParseException 構文解析に失敗した場合
     */
    public Expression parse() {
        // ゴール記号を解釈する
        Expression result = Expression();
        
        // ゴール記号の後は入力の終端
        if (scanner.get().kind != TokenKind.EOF) {
            throw new ParseException("EOF : " + scanner.get());
        }
        
        // 全体としてExpressionを返す
        return result;
    }

    private Expression Expression() {
        // Expression ::= Value$a '+' Value$b ; Add(a, b)
        
        // 最初はValueを処理
        Value a = Value();
        
        // 次に、'+'が来ることを確認
        if (scanner.get().kind == TokenKind.PLUS) {
            // 確認できたらトークンを消費する
            scanner.consume();
        }
        else {
            // 間違っていたら構文エラー
            throw new ParseException("'+' : " + scanner.get());
        }
        
        // 最後にもう一度Valueを処理
        Value b = Value();
        
        // 加算のASTを構築する
        return new Add(a, b);
    }

    private Value Value() {
        // Value ::= NUMBER$t ; Value(t)
        
        // 数値が来ることを確認
        if (scanner.get().kind == TokenKind.NUMBER) {
            // 確認できたらトークンを消費
            Token t = scanner.consume();
            // そのトークンで数値のASTを構築する
            return new Value(t.image);
        }
        else {
            // 間違っていたら構文エラー
            throw new ParseException("NUMBER : " + scanner.get());
        }
    }

    /**
     * このパーサを実行してみる。
     * @param args 無視される
     */
    public static void main(String[] args) {
        eval("1 + 2");
        eval("0 + 1");
        eval("3 + 5");
    }
    
    private static void eval(String input) {
        try {
            ExampleParser parser = new ExampleParser(new StringReader(input));
            Expression expression = parser.parse();
            System.out.printf(
                "%d = %s%n",
                Evaluator.eval(expression),
                input);
        }
        catch (IOException e) {
            // may not occur
            throw new AssertionError(e);
        }
    }
}
