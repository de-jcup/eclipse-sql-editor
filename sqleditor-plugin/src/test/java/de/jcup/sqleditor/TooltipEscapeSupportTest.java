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
package de.jcup.sqleditor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TooltipEscapeSupportTest {

    private TooltipEscapeSupport supportToTest;

    @Before
    public void before() {
        supportToTest = new TooltipEscapeSupport();
    }

    @Test
    public void no_commas_not_escaped() {
        assertEquals("abcd", supportToTest.escapeCommasInTooltip("abcd"));
        assertEquals("", supportToTest.escapeCommasInTooltip(""));
        assertEquals("", supportToTest.escapeCommasInTooltip(null));
    }

    @Test
    public void commas_are_escaped() {
        assertEquals("abcd_§_", supportToTest.escapeCommasInTooltip("abcd,"));
        assertEquals("_§_a", supportToTest.escapeCommasInTooltip(",a"));
        assertEquals("x_§_a", supportToTest.escapeCommasInTooltip("x,a"));
        assertEquals("_§_", supportToTest.escapeCommasInTooltip(","));
    }

    @Test
    public void no_commas_not_unescaped_when_fetchOriginTooltipFromEscapedString() {
        assertEquals("abcd", supportToTest.fetchOriginTooltipFromEscapedString("abcd"));
        assertEquals("", supportToTest.fetchOriginTooltipFromEscapedString(""));
        assertEquals("", supportToTest.fetchOriginTooltipFromEscapedString(null));
    }

    @Test
    public void commas_are_unescaped_when_fetchOriginTooltipFromEscapedString() {
        assertEquals("abcd,", supportToTest.fetchOriginTooltipFromEscapedString("abcd_§_"));
        assertEquals(",a", supportToTest.fetchOriginTooltipFromEscapedString("_§_a"));
        assertEquals("x,a", supportToTest.fetchOriginTooltipFromEscapedString("x_§_a"));
        assertEquals(",", supportToTest.fetchOriginTooltipFromEscapedString("_§_"));
    }

}
