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
import jp.undo.vtable.parser.lang.arithmetic.model.Subtract;
import jp.undo.vtable.parser.lang.arithmetic.model.Value;
import jp.undo.vtable.parser.lang.arithmetic.scanner.TokenKind;
import jp.undo.vtable.parser.lang.arithmetic.scanner.TokenScanner;
import jp.undo.vtable.parser.util.CharacterSource;
import jp.undo.vtable.parser.util.ParseException;

/**
 * 問題0の構文解析器。
 * 
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Q0Parser {

	private final TokenScanner scanner;

	/**
	 * インスタンスを生成する。
	 * 
	 * @param reader
	 *            解析対象の文字列を含むストリーム
	 * @throws IOException
	 *             ストリームの読み出しに失敗した場合
	 * @throws IllegalArgumentException
	 *             引数に{@code null}が含まれる場合
	 */
	public Q0Parser(Reader reader) throws IOException {
		if (reader == null) {
			throw new IllegalArgumentException("reader is null"); //$NON-NLS-1$
		}
		this.scanner = new TokenScanner(new CharacterSource(reader));
	}

	/**
	 * このパーサを実行してみる。
	 * 
	 * @param args
	 *            無視される
	 */
	public static void main(String[] args) {
		process("+ 10 20");
		process("- 10 20");
	}

	private static void process(String input) {
		try {
			Q0Parser parser = new Q0Parser(new StringReader(input));
			Expression expression = parser.parse();
			System.out.printf("%s (=%d); %s%n", input, Evaluator.eval(expression), expression);
		} catch (IOException e) {
			// may not occur
			throw new AssertionError(e);
		}
	}

	/**
	 * この構文解析器に渡された入力を解析し、解析結果のモデルオブジェクトを返す。
	 * <p>
	 * 次の{@code Expression}をゴール記号とする構文を解析する。
	 * </p>
	 * 
	 * <pre>
	 * &lt;code&gt;
	 * Expression ::= '+' Value$a Value$b ; new Add(a, b)
	 *             |  '-' Value$a Value$b ; new Subtract(a, b)
	 *      Value ::= NUMBER$t            ; new Value(t.image)
	 * &lt;/code&gt;
	 * </pre>
	 * <p>
	 * ただし、それぞれの構文アクションに出現する型名は
	 * {@code jp.undo.vtable.parser.lang.arithmetic.model}パッケージ下に
	 * 宣言されたクラスを利用するものとする。
	 * </p>
	 * 
	 * @return 解析結果の式
	 * @throws ParseException
	 *             構文解析に失敗した場合
	 */
	public Expression parse() {
		if (scanner.get(3).kind != TokenKind.EOF)
			throw new ParseException("");

		switch (scanner.get().kind) {
		case PLUS:
			return new Add(getValue(1), getValue(2));
		case MINUS:
			return new Subtract(getValue(1), getValue(2));
		default:
			throw new ParseException("");
		}
	}

	private Value getValue(int index) {
		switch (scanner.get(index).kind) {
		case NUMBER:
			return new Value(scanner.get(index).image);
		default:
			throw new ParseException("");
		}
	}

}
