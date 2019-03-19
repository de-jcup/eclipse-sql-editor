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

public class ParseToken {

	private static final String EQUAL_OPERAND = "=";
	private String text;
	int start;
	int end;
    private String uppercasedText;

	ParseToken() {
	    this(null);
	}

	ParseToken(String text) {
		this(text, 0, 0);
	}

	ParseToken(String text, int start, int end) {
	    changeText(text);
		this.start = start;
		this.end = end;
	}


	/**
	 * @return always a upper cased text
	 */
	public String getUppercasedText() {
		return uppercasedText;
	}
	
	public String getText() {
        return text;
    }

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(createTypeDescription());
		sb.append(":'");
		sb.append(text);
		sb.append('\'');
		
		return sb.toString();
	}

	public String createTypeDescription() {
		StringBuilder sb = new StringBuilder();
		if (isComment()){
			sb.append("COMMENT");
		}
		if (isString()){
			sb.append("STRING");
		}
		if (isCase()){
			sb.append("CASE");
		}
		if (sb.length()==0){
			sb.append("EXPRESSION");
		}
		return sb.toString();
	}

	public boolean isComment() {
		return getSafeText().startsWith("--");
	}

	public boolean isSingleString() {
		return getSafeText().startsWith("'");
	}

	public boolean isDoubleString() {
		return getSafeText().startsWith("\"");
	}

	public boolean isDoubleTickedString() {
		return getSafeText().startsWith("`");
	}

	private String getSafeText() {
		return text==null ? "":text;
	}
	
	private String getSafeUppercasedText() {
        return uppercasedText==null ? "":uppercasedText;
    }

	public boolean isString() {
		boolean isString = isSingleString() || isDoubleString() || isDoubleTickedString();
		return isString;
	}

	public boolean isFunctionKeyword() {
		return "function".equals(text);
	}

	/**
	 * @return <code>true</code> when token ends with function brackets and contains no illegal states (e.g. string, comment)
	 * and also no illegal characters in name
	 */ 
	public boolean isFunction() {
		boolean isFunctionName = endsWithFunctionBrackets();
		isFunctionName = isFunctionName && isLegalFunctionName();
		isFunctionName = isFunctionName && !isComment();
		isFunctionName = isFunctionName && text.length() > 2;
		isFunctionName = isFunctionName && !isString();
		
		return  isFunctionName;
	}

	public boolean isLegalFunctionName() {
		return ! getSafeText().contains(EQUAL_OPERAND);
	}

	public boolean endsWithFunctionBrackets() {
		return getSafeText().endsWith("()");
	}
	
	public boolean isFunctionStartBracket() {
        return getSafeText().equals("(");
    }
	public boolean isFunctionEndBracket() {
        return getSafeText().equals(")");
    }

	public boolean hasLength(int length) {
		return getSafeText().length() == length;
	}

	public String getTextAsFunctionName() {
		// String name = token.text;
		if (getSafeText().endsWith("()")) {
			return text.substring(0, text.length() - 2);
		}
		return text;
	}

	public boolean isAdd() {
	    return getSafeUppercasedText().equals("ADD");
	}
	
	public boolean isUnion() {
        return getSafeUppercasedText().equals("UNION");
    }
	
	public boolean isJoin() {
        return getSafeUppercasedText().equals("JOIN");
    }
	
	public boolean isCase() {
		return getSafeUppercasedText().equals("CASE");
	}

    public boolean isEndOfStatement() {
        return getSafeUppercasedText().equals(";");
    }

    public boolean isCandidateForSubStatement() {
        return isAdd() || isJoin() || isUnion();
    }

    public boolean isBracketStart() {
        return getSafeText().startsWith("(");
    }

    public boolean isFrom() {
        return getSafeUppercasedText().equals("FROM");
    }

    public boolean isWhere() {
        return getSafeUppercasedText().equals("WHERE");
    }

    public boolean isSelect() {
        return getSafeUppercasedText().equals("SELECT");
    }

    void changeText(String text) {
        if (text == null) {
            this.text = "";
        }else {
            this.text=text;
        }
        this.uppercasedText=this.text.toUpperCase();
    }

    public boolean isBracketEnd() {
        return getSafeText().endsWith(")");
    }

    public boolean isAs() {
        return getSafeUppercasedText().equals("AS");
    }
    
    public boolean isNot() {
        return getSafeUppercasedText().equals("NOT");
    }
	

	
}
