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
import jp.undo.vtable.parser.lang.arithmetic.scanner.Token;
import jp.undo.vtable.parser.lang.arithmetic.scanner.TokenKind;
import jp.undo.vtable.parser.lang.arithmetic.scanner.TokenScanner;
import jp.undo.vtable.parser.util.CharacterSource;
import jp.undo.vtable.parser.util.ParseException;

/**
 * 問題2の構文解析器。
 * 
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Q1Parser {

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
	public Q1Parser(Reader reader) throws IOException {
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
		process("10");
		process("+ 10 - 20 30");
		process("+ + + 10 - 20 30 40 50");
	}

	private static void process(String input) {
		try {
			Q1Parser parser = new Q1Parser(new StringReader(input));
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
	 * Expression ::= '+' Expression$a Expression$b ; new Add(a, b)
	 *             |  '-' Expression$a Expression$b ; new Subtract(a, b)
	 *             |  Value$v                       ; v
	 *      Value ::= NUMBER$t                      ; new Value(t.image)
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
		Expression expression = parseInternal();
		if (scanner.consume().kind != TokenKind.EOF)
			throw new ParseException("");
		return expression;
	}

	private Expression parseInternal() {
		Token token = scanner.consume();
		switch (token.kind) {
		case PLUS:
			return new Add(parseInternal(), parseInternal());
		case MINUS:
			return new Subtract(parseInternal(), parseInternal());
		case NUMBER:
			return new Value(token.image);
		default:
			throw new ParseException("");
		}
	}

}
