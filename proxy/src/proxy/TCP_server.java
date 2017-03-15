package proxy;
import java.io.*;
import java.net.HttpRetryException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JFrame;



public class TCP_server extends JFrame{
	
	
	public static void main(String[] args) throws Exception
	{
		
		TCP_server server = new TCP_server();
		server.run();
		
	}

	private void run() throws Exception{
		// TODO Auto-generated method stub
		
		ServerSocket srvsoc = new ServerSocket(344);
		StringBuffer sb = new StringBuffer("");
		Socket sock = srvsoc.accept();
		Runtime rt = Runtime.getRuntime();
		InputStreamReader input = new InputStreamReader(sock.getInputStream());
		
		BufferedReader br = new BufferedReader(input);
		
		
		String message = br.readLine();
		String split[];
		System.out.println("URL From client is:"+message);
		
		
		
		
		

//=====================================Headers info============================================
		
		try
		{
		URL obj = new URL(message);
		URLConnection conn = obj.openConnection();
		
		
		Map<String, List<String>> map = conn.getHeaderFields();
		
	 
		System.out.println("Printing Response Header...\n");
	 
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() 
	                           + " ,Value : " + entry.getValue());
		}
	 
		//System.out.println("\nGet Response Header By Key ...\n");
		
		String ETag = conn.getHeaderField("ETag");
		String Last = conn.getHeaderField("Last-Modified");
		String Cache = conn.getHeaderField("Cache-Control");		
		String page = null,etag = null,last = null,content;
		
//=============================end of headers===========================
	    

		
//=============================file input===============================
	    
	    String fileName = "cache/temp.txt";
	    

        try {
            // Use this for reading the data.
           // byte[] buffer = new byte[1000];
        	String sCurrentLine;
        	
        	br = new BufferedReader(new FileReader(fileName));
        
            // read fills buffer with data and returns
            // the number of bytes read (which of course
            // may be less than the buffer size, but
            // it will never be more).
            if ((sCurrentLine = br.readLine())== null)
            {
            	
            	System.out.println("Cache Entry is added");
            	BufferedReader in = new BufferedReader(new InputStreamReader(obj.openStream()));

        	    String inputLine;
        	    while ((inputLine = in.readLine()) != null)
        	    
        	    	sb.append(inputLine);
        	    in.close();
        	    //System.out.println(sb);
        	       

                try {
                    // Assume default encoding.
                    FileWriter fileWriter =
                        new FileWriter(fileName,true);

                    // Always wrap FileWriter in BufferedWriter.
                    BufferedWriter bufferedWriter =
                        new BufferedWriter(fileWriter);

                    // Note that write() does not automatically
                    // append a newline character.
                    
                    fileWriter.write(message+"\t"+ETag+"\t"+Last+"\t"+Cache+"\t"+sb);
                    fileWriter.write("\n");
                    

                    // Always close files.
                    fileWriter.close();
                    System.out.println("\nHTTP/1.1 200 OK");
                    System.out.println("ETAG Value: "+ETag);
                    System.out.println("Last-modified: "+Last);
                    System.out.println("Cache-content: "+Cache);
                    System.out.println("\n Content From Server:");
                    
                    
                    String s = "HTTP/1.1 200 OK"+"\t"+"ETAG VALUE= "+ETag+"\t"+"LAST MODIFIED VALUE= "+Last+"\t"+"CACHE-CONTENT= "+Cache
                			+"\t"+"CONTENT = "+sb;
        		PrintStream ps = new PrintStream(sock.getOutputStream());
    			ps.println(s);
        			
        			System.out.println(sb);
        			try { 
        				rt.exec( "rundll32 url.dll,FileProtocolHandler " + message);
        				String sb1 = sb.toString();
        				JEditorPane ed1=new JEditorPane("text/html",sb1);
        				add(ed1);
        				setVisible(true); 
        				setSize(1300,1000); 
        				setDefaultCloseOperation(EXIT_ON_CLOSE); 
        				
        				}
        			
        			catch(Exception e)
        			{
        				
        			}
        			
              /*  BufferedReader in1 = new BufferedReader(new InputStreamReader(obj.openStream()));

        	    String inputLine1;
        	    while ((inputLine1 = in1.readLine()) != null)
        	    
        	    	sb.append(inputLine1);
        	    in1.close();
        	    System.out.println(sb);*/
                }
                catch(IOException ex) {
                    System.out.println(
                        "Error writing to file '"
                        + fileName + "'");
                    // Or we could just do this:
                    // ex.printStackTrace();
                }
                System.exit(0);
            }
            
            else{
           BufferedReader br1 = new BufferedReader(new FileReader(fileName));
           
            while((sCurrentLine = br1.readLine())!= null) {
                // Convert to String so we can display it.
                // Of course you wouldn't want to do this with
                // a 'real' binary file.
               // System.out.println(sCurrentLine);
            	split = sCurrentLine.split("\t");
            	 page = split[0];
            	 etag = split[1];
            	 last = split[2];
            	content = split[4];
            	//System.out.println(page);
            	
            	if ( (page.equals( message) && etag.equals(ETag) && last.equals(Last)) || (page.equals( message) && etag.equals("null") && last.equals("null")))
            	{
            		//System.out.println("IF-ELSE");
            		System.out.println("\nHTTP/1.1 304 Not Modified");
                    System.out.println("ETAG Value: "+etag);
                    System.out.println("Last-modified: "+last);
                    System.out.println("Cache-content: "+split[3]);
            		System.out.println("Content From Cache: ");
            		String s = "HTTP/1.1 304 Not Modified"+"\t"+"ETAG VALUE= "+etag+"\t"+"LAST MODIFIED VALUE= "+last+"\t"+"CACHE-CONTENT= "+split[3]+"\t"
            				+"CONTENT= "+content;
            		PrintStream ps = new PrintStream(sock.getOutputStream());
        			ps.println(s);
        			
        			
        			
        			System.out.println(content);
        			
        			try { 
        				rt.exec( "rundll32 url.dll,FileProtocolHandler " + page);
        				
        				JEditorPane ed1=new JEditorPane("text/html",content);
        				add(ed1);
        				setVisible(true); 
        				setSize(1300,1000); 
        				setDefaultCloseOperation(EXIT_ON_CLOSE); 
        				
        				
        				}

        			catch(Exception e)
        			{
        				
        			}
        			 br1.close();
        			 
        			 break;
        			 
        				 
            	}
            	
            	 
            	
            
            } 
            
           
            	
           // System.out.println(page+etag);
            if(!(etag.equals(ETag) && last.equals(Last) ) && !(page.equals( message) && etag.equals("null") && last.equals("null")) || !page.equals(message))
            {
            		//=================================file output=====================================
                   // System.out.println("ELSE");
                    
             	   
            	    BufferedReader in = new BufferedReader(new InputStreamReader(obj.openStream()));

            	    String inputLine;
            	    while ((inputLine = in.readLine()) != null)
            	    
            	    	sb.append(inputLine);
            	    in.close();
            	  //  System.out.println(sb);
            	       

                    try {
                        // Assume default encoding.
                        FileWriter fileWriter =
                            new FileWriter(fileName,true);

                        // Always wrap FileWriter in BufferedWriter.
                        BufferedWriter bufferedWriter =
                            new BufferedWriter(fileWriter);

                        // Note that write() does not automatically
                        // append a newline character.
                        
                        fileWriter.write(message+"\t"+ETag+"\t"+Last+"\t"+Cache+"\t"+sb);
                        fileWriter.write("\n");
                        

                        // Always close files.
                        fileWriter.close();
                        System.out.println("\nHTTP/1.1 200 OK");
                        System.out.println("ETAG Value: "+ETag);
                        System.out.println("Last-modified: "+Last);
                        System.out.println("Cache-content: "+Cache);
                        System.out.println("Content from Server: ");
                        String s = "HTTP/1.1 200 OK"+"\t"+"ETAG VALUE= "+ETag+"\t"+"LAST MODIFIED VALUE= "+Last+"\t"+"CACHE-CONTENT= "+Cache
                        			+"\t"+"CONTENT = "+sb;
                		PrintStream ps = new PrintStream(sock.getOutputStream());
            			ps.println(s);
            			
            			
            			System.out.println(sb);
            			try { 
            				rt.exec( "rundll32 url.dll,FileProtocolHandler " + message);
            				String sb1 = sb.toString();
            				JEditorPane ed1=new JEditorPane("text/html",sb1);
            				add(ed1);
            				setVisible(true); 
            				setSize(1300,1000); 
            				setDefaultCloseOperation(EXIT_ON_CLOSE); 
            				}

            			catch(Exception e)
            			{
            				
            			}
            			 
                       
                  /*  BufferedReader in1 = new BufferedReader(new InputStreamReader(obj.openStream()));

            	    String inputLine1;
            	    while ((inputLine1 = in1.readLine()) != null)
            	    
            	    	sb.append(inputLine1);
            	    in1.close();
            	    System.out.println(sb);*/
                    }
                    catch(IOException ex) {
                        System.out.println(
                            "Error writing to file '"
                            + fileName + "'");
                        // Or we could just do this:
                        // ex.printStackTrace();
                    
            	    
                     
            	
           
            // Always close files.
            }
            
        }
          
            		
            }
        
        
        }
            catch(FileNotFoundException ex) {
            }
        
               
            	
           		

            
        
        
	    
	    
//=================================================================================

	 
		if (ETag == null) {
			//System.out.println("Key 'ETag' is not found!");
		} else {
			//System.out.println("ETag - " + ETag);
			
			//String ifn = conn.getHeaderField("If-None-Match");
		                     
			//System.out.println("If-None-Match - " + ifn);
				
				
		
		}
		}
		catch(FileNotFoundException ex) {
        }
	 
		System.out.println("\n Done");
	 
	    
		
		if(message != null)
		{
			//PrintStream ps = new PrintStream(sock.getOutputStream());
			//ps.println("Message Received");
		}
		
		
        }
	

		}
