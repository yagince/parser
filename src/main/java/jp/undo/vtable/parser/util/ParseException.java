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

/**
 * 構文解析に失敗したことを表現する例外。
 * @version 0
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class ParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * インスタンスを生成する。
     * @param message メッセージ(省略可)
     */
    public ParseException(String message) {
        super(message);
    }
}
