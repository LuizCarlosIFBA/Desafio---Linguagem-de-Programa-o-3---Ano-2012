package sevidor;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import cliente.Cliente;

import java.io.InputStreamReader;

import java.io.InputStream;
import java.io.OutputStream;

public class Servidor extends Cliente{
	 
	
	public static void main(String[] args) throws Exception {
	  
	  System.out.println("Servidor... OK");
	    
	  Socket socket = new Socket("localhost", 2525);
	 	  
 	  /*Cliente cli = new Cliente("localhost", 2525);
	  for(int i = 0;i>cli.mensagem.size();i++){   
          System.out.println(cli.mensagem[i]);  
	   }*/
	   
	   socket.close();
	  
	 }

}