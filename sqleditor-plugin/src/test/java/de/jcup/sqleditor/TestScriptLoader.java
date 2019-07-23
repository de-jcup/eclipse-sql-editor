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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestScriptLoader {
	private static File testScriptRootFolder = new File("./sqleditor-other/testscripts");
	static{
		if (!testScriptRootFolder.exists()){
			// workaround for difference between eclipse test and gradle execution (being in root folder...)
			testScriptRootFolder = new File("./../sqleditor-other/testscripts");
		}
	}
	public static List<String> fetchAllTestScriptNames() {
	    return fetchAllTestScriptNames(null);
	}
	public static List<String> fetchAllTestScriptNames(String path) {
		File folder = assertTestScriptFolder(path);
		if (!folder.exists()) {
		    throw new IllegalStateException("Testcase corrupt, folder does not exist:"+folder);
		}
		List<String> list = new ArrayList<>();
		for (File file: folder.listFiles()){
			list.add(file.getName());
		}
		return list;
	}
	
    public static File assertTestScriptFolder(String path) {
        assertTestscriptFolderExists();
		File folder = testScriptRootFolder;
		if (path!=null) {
		    folder = new File(testScriptRootFolder,path);
		}
        return folder;
    }
	
	public static String loadScriptFromTestScripts(String testScriptName) throws IOException{
		assertTestscriptFolderExists();
		
		File file = new File(testScriptRootFolder,testScriptName);
		if (!file.exists()){
			throw new IllegalArgumentException("Test case corrupt! Test script file does not exist:"+file);
		}
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
		    boolean firstLine=true;
			String line = null;
			while ((line=br.readLine())!=null){
			    if (!firstLine) {
                    sb.append("\n");
                }
				sb.append(line);
				if (firstLine) {
				    firstLine=false;
				}
			}
		}
		return sb.toString();
	}

	private static void assertTestscriptFolderExists() {
		if (!testScriptRootFolder.exists()){
			throw new IllegalArgumentException("Test setup corrupt! Root folder of test scripts not found:"+testScriptRootFolder);
		}
	}
}
