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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssertParseTokens {

	private List<ParseToken> parseTokens;

	/**
	 * Ensures given list is not null and returns dedicated assert object
	 * @param parseTokens
	 * @return assert object
	 */
	public static AssertParseTokens assertThat(List<ParseToken> parseTokens){
		return new AssertParseTokens(parseTokens);
	}
	
	public AssertParseTokens(List<ParseToken> parseTokens) {
		assertNotNull("Parse tokens may not be null!", parseTokens);
		this.parseTokens=parseTokens;
	}
	
	public AssertParseTokens containsOneToken(String text){
		return containsToken(text,1);
	}
	
	public AssertParseTokens containsNotToken(String text){
		return containsToken(text,0);
	}
	
	public AssertParseTokens containsToken(String text, int expectedAmount){
		int count =0;
		for (ParseToken token: parseTokens){
			if (text.equals(token.getUppercasedText())){
				count++;
			}
		}
		if (expectedAmount!=count){
			assertEquals("The token amount for '"+text+"' is not as expected", expectedAmount,count);
		}
		return this;
	}
	
	/**
	 * Ensures there exists at least one token with given tokenText. The first token will be returned
	 * @param token
	 * @return token, never <code>null</code>
	 */
	public AssertParseToken token(String token){
		return token(token,1);
	}
		
	public AssertParseToken token(String token, int tokenNumber){
		int nr=0;
		for (ParseToken found: parseTokens){
			if (token.equals(found.getUppercasedText())){
				nr++;
				if (tokenNumber==nr){
					return AssertParseToken.assertThat(found);
				}
			}
		}
		fail("Tried to get token '"+token+"' number:"+tokenNumber+" but did found only "+nr+" in:"+parseTokens);
		return null;
	}

	public AssertParseTokens containsTokens(String ...tokens){
		List<String> found = new ArrayList<String>();
		for (ParseToken token: parseTokens){
			found.add(token.getText());
		} 
		if (tokens.length != found.size()){
			fail("Tokens length differ("+tokens.length+"/"+found.size()+")!\nexpected tokens:\n"+Arrays.asList(tokens)+"\nfound tokens:\n"+found);
		}
		String[] foundArray = found.toArray(new String[found.size()]);
        if (! Arrays.equals(tokens, foundArray)){
		    String firstDiff = "";
		    int i=0;
		    for (String token: tokens) {
		        if (! token.contentEquals(foundArray[i])) {
		            firstDiff = "Token:"+i+" not found. Expected '"+token+"' but found '"+foundArray[i]+"'";
		            break;
		        }
		        i++;
		    }
			fail("Tokens content differ!\n"+firstDiff+"\n\nExpected tokens:\n"+Arrays.asList(tokens)+"\nfound tokens:\n"+found);
		}
		return this;
	}

	public ParseToken resolveToken(String string) {
		for (ParseToken token: parseTokens){
			if (token.getUppercasedText().equals(string)){
				return token;
			}
		} 
		fail("Tried to resolve token '"+string+"' but did not found in:"+parseTokens);
		return null;
	}
	
	
}
