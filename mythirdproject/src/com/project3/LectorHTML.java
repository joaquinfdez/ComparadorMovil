package com.project3;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LectorHTML {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	public String leeHtml(String url){
		URL urlObjet;
		String codigo = "";
		String linea;
		try{
			URL urlObject=new URL(url);
			InputStreamReader isr=new InputStreamReader(urlObject.openStream());
			BufferedReader br = new BufferedReader(isr);
			while((linea=br.readLine())!=null){
				codigo+=linea;
			}
			
		}catch (MalformedURLException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return codigo;
	}

}
