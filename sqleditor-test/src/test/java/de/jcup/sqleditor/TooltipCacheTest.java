package de.jcup.sqleditor;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import de.jcup.sqleditor.document.keywords.SQLKeyword;

public class TooltipCacheTest {

    private TooltipCache toTest;

    @Before
    public void before() {
        toTest = new TooltipCache();
        
        toTest.generator=mock(SQLTooltipGenerator.class);
    }

    @Test
    public void when_sql_generator_generates_a_description_the_result_is_returned() {
        
        /* prepare */
        SQLKeyword keyword = mock(SQLKeyword.class);
        when(toTest.generator.createDescription(keyword)).thenReturn("important things");
        
        /* execute */
        String result = toTest.getTooltip(keyword);

        /* test */
        assertNotNull(result);
        assertEquals("important things",result);
    }
    
    @Test
    public void generator_is_only_called_ones_when_geno_result_is_not_empty_but_called_twice() {
        
        /* prepare */
        SQLKeyword keyword = mock(SQLKeyword.class);
        when(toTest.generator.createDescription(keyword)).thenReturn("a");
        
        /* execute */
        String result = toTest.getTooltip(keyword);
        assertEquals("a",result);
        result = toTest.getTooltip(keyword);
        assertEquals("a",result);

        /* test */
        verify(toTest.generator,times(1)).createDescription(any());
    }
    
    @Test
    public void generator_is_only_called_ones_when_geno_result_is_empty_but_called_twice() {
        
        /* prepare */
        SQLKeyword keyword = mock(SQLKeyword.class);
        when(toTest.generator.createDescription(any())).thenReturn(null);
        
        /* execute */
        String result = toTest.getTooltip(keyword);
        assertEquals("",result);
        result = toTest.getTooltip(keyword);
        assertEquals("",result);

        /* test */
        verify(toTest.generator,times(1)).createDescription(any());
    }

    @Test
    public void an_unknown_sql_result_is_not_null_but_an_empty_string() {
        
        /* prepare */
        SQLKeyword keyword = mock(SQLKeyword.class);
        
        /* execute */
        String selectResult = toTest.getTooltip(keyword);

        /* test */
        assertNotNull(selectResult);
        assertTrue(selectResult.trim().equals(""));
    }

}
