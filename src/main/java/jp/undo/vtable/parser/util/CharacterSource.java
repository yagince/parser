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
package jp.undo.vtable.parser.util;

import java.io.IOException;
import java.io.Reader;

/**
 * 文字を取り出せるソース。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class CharacterSource {

    /**
     * 終端文字({@value})。
     */
    public static final int EOF = -1;

    private final char[] content;

    private int cursor;

    /**
     * インスタンスを生成する。
     * @param reader 読みだし元のストリーム
     * @throws IOException 読み出しに失敗した場合
     * @throws IllegalArgumentException 引数に{@code null}が指定された場合
     */
    public CharacterSource(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("reader == null");
        }
        try {
            this.content = drain(reader);
        }
        finally {
            reader.close();
        }
    }

    private char[] drain(Reader reader) throws IOException {
        char[] buf = new char[256];
        StringBuilder out = new StringBuilder();
        while (true) {
            int read = reader.read(buf);
            if (read == -1) {
                break;
            }
            out.append(buf, 0, read);
        }
        char[] output = new char[out.length()];
        out.getChars(0, out.length(), output, 0);
        return output;
    }
    
    /**
     * 現在の文字を返す。
     * <p>
     * この呼び出しは、{@link #get(int) get(0)}と等しい。
     * </p>
     * @return 現在の文字、全ての入力を処理し終わっている場合は終端を表現する文字
     * @see #consume()
     */
    public int get() {
        return get(0);
    }
    
    /**
     * 現在の文字から、指定の個数だけ先の文字を返す。
     * <p>
     * 引数に{@code 0}が指定された場合、この呼び出しは現在の文字を返す。
     * </p>
     * <p>
     * そのような文字が存在しない場合、この呼び出しは終端を表現する文字を返す。
     * </p>
     * @param k 先読みする個数
     * @return 指定の個数だけ先の文字、存在しない場合は終端を表現する文字
     * @see #consume()
     */
    public int get(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k < 0");
        }
        if (isEof(k)) {
            return EOF;
        }
        return content[cursor + k];
    }

    /**
     * 現在の文字を捨てる。
     * <p>
     * 次に{@link #get()}を呼び出した場合、現在までの文字の次の文字が返される。
     * </p>
     * <p>
     * 全ての入力を処理し終わっている場合、この呼び出しは何も行わない。
     * </p>
     * @return 現在の文字、全ての入力を処理し終わっている場合は終端を表現する文字
     * @see #get()
     */
    public int consume() {
        if (isEof(0)) {
            return EOF;
        }
        return content[cursor++];
    }

    private boolean isEof(int k) {
        return cursor + k >= content.length;
    }
}
