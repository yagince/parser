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
/**
 * 整数の四則演算に関する字句を解析する。
 * <p>
 * 次のようなプログラムを書くことができる。
 * </p>
 * <pre><code>
 * Reader reader = new StringReader(&quot+ 10;&quot);
 * TokenScanner scanner = new TokenScanner(reader);
 * 
 * // '+' 演算子を読みだす
 * Token t1 = scanner.get();
 * assert t1.kind == TokenKind.PLUS;
 * 
 * // 次の文字へ
 * scanner.consume();
 * 
 * // 数値 '10' を読みだす
 * Token t2 = scanner.get();
 * assert t2.kind == TokenKind.NUMBER;
 * assert t2.image.equals("10");
 * 
 * // 次の文字へ
 * scanner.consume();
 * 
 * // 入力の終端
 * assert scanner.get().kind == TokenKind.EOF;
 * </code></pre>
 */
package jp.undo.vtable.parser.lang.arithmetic.scanner;

