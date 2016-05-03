package com.propertymanagerhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamConverter {
	 public String convertStreamToString(InputStream is) {
         /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          * 
          * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
          */
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));
         StringBuilder sb = new StringBuilder();

         String line = null;
         try {
                 while ((line = reader.readLine()) != null) {
                         sb.append(line + "\n");
                 }
         } catch (IOException e) {
                 e.printStackTrace();
         } finally {
                 try {
                         is.close();
                 } catch (IOException e) {
                         e.printStackTrace();
                 }
         }
         return sb.toString();
 }

}
