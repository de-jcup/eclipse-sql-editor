package de.jcup.sqleditor.script;

import java.util.ArrayList;

public class EchoParserSupport {
	
	private static final String ECHO_LOWERCASED = "echo";
	boolean traceEnabled=true;

	public boolean isAfterEchoHandled(CodePosSupport codePosSupport) {
		int startPos = codePosSupport.getInitialStartPos();
		if (! checkStartsWithEcho(codePosSupport,startPos)){
			return false;
		}
		moveToNextLineOrCommandTermination(codePosSupport,startPos);
		return true;
	}

	private void moveToNextLineOrCommandTermination(CodePosSupport codePosSupport, int startPos) {
		int pos = startPos;
		StringBuilder sb = null;
		if (traceEnabled){
			sb = new StringBuilder();
		}
		EchoParserSupportContext context = new EchoParserSupportContext();
		while(true){
			Character c = codePosSupport.getCharacterAtPosOrNull(pos);
			if (c==null){
				break;
			}
			if (context.isTerminating(c)){
				break;
			}
			
			if (sb!=null){
				sb.append(c);
			}
			context.nextChar(c);
			pos++;
		}
		if (pos>=1){
			codePosSupport.moveToPos(pos-1); // do not add the terminating char, so e.g. | is not syntax highlighted 
		}
	}
	
	static class EchoParserSupportContext{
		
		static final Character ESCAPED = Character.valueOf('^');
		private boolean inEscapeMode;
		private Character lastCharacter;
		
		void nextChar(Character nextChar){
			inEscapeMode = ESCAPED.equals(nextChar);
			if (inEscapeMode){
				if (lastCharacter!=null){
					if (ESCAPED.equals(lastCharacter)){
						/* ^^ found... not escaped!*/
						inEscapeMode=false;
						lastCharacter=null; // reset
						return;
					}
				}
			}
			lastCharacter=nextChar;
		}
		
		boolean isTerminating(Character c) {
			if (c=='\n'){
				return true;
			}
			if (c=='\r'){
				return true;
			}
			if (! isInEscapeMode()){
				if (c=='|'){
					return true;
				}
				if (c=='<'){
					return true;
				}
				if (c=='>'){
					return true;
				}
				if (c=='&'){
					return true;
				}
			}
			return false;
		}

		boolean isInEscapeMode(){
			return inEscapeMode;
		}
	}
	

	protected boolean checkStartsWithEcho(CodePosSupport codePosSupport, int startPos) {
		ArrayList<Character> list = new ArrayList<>();
		for (int i=startPos-4;i<startPos;i++){
			list.add(codePosSupport.getCharacterAtPosOrNull(i));
		}
		if (list.contains(null)){
			codePosSupport.moveToPos(startPos);
			return false;
		}
		StringBuilder sb = new StringBuilder();
		for (Character c: list){
			sb.append(c);
		}
		String lowerCase = sb.toString().toLowerCase();
		if (! (ECHO_LOWERCASED.equals(lowerCase))){
			codePosSupport.moveToPos(startPos);
			return false;
		}
		return true;
	}

}
