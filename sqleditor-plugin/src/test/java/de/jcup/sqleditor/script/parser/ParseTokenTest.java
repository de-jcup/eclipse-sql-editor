/*
 * Copyright 2019 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package de.jcup.sqleditor.script.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParseTokenTest {
    @Test
    public void XYZ_is_text_XYZ() {
        assertEquals("XYZ", new ParseToken("XYZ").getUppercasedText());
    }
    @Test
    public void xyz_is_text_XYZ() {
        assertEquals("XYZ", new ParseToken("xyz").getUppercasedText());
    }
	@Test
	public void semicolon_is_recognized_as_end_of_statement() {
		assertEquals(true, new ParseToken(";").isEndOfStatement());
	}
	@Test
    public void xyz_open_close_bracketgetTextAsFunctionName_returns_xyz() {
        assertEquals("xyz", new ParseToken("xyz()").getTextAsFunctionName());
    }
}