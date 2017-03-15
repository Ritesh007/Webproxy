package proxy;



import java.net.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class TCP_client extends JFrame {
	
	
	
	
	Socket sock;
	
	
	String split1[];
	
	
	
	public static void main(String args[]) throws Exception
	{
		TCP_client client = new TCP_client();
		
		client.run1();
	}

	private void run1() throws Exception{
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			TCP_client frame = new TCP_client();
			try {
				frame.TCP_client1();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.setVisible(true);
			}
			});
	
		
		/*try { 
			
			JEditorPane ed1=new JEditorPane("text/html",message);
			System.out.println(message);
			add(ed1);
			setVisible(true); 
			setSize(1300,1000); 
			setDefaultCloseOperation(EXIT_ON_CLOSE); 
			}

		catch(Exception e)
		{
			
		}*/
		
	}

	public  void TCP_client1() throws Exception{
		 
		
		JFrame f = new JFrame("First JTextPane Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1100, 500);
		setTitle("ThaiCreate.Com Java GUI Tutorial");
		getContentPane().setLayout(null);

		
		final JLabel lb = new JLabel("Please enter the URL:");
		lb.setBounds(10, 10, 144, 14);
		getContentPane().add(lb);
		
		
		//Button
		JButton btn1 = new JButton("Submit");
		btn1.setBounds(10, 70, 90, 25);
		getContentPane().add(btn1);
		
		JButton btn2 = new JButton("Display Response");
		btn2.setBounds(10, 120, 150, 25);
		getContentPane().add(btn2);
		 
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		final JTextArea textArea1 = new JTextArea();
		
		textArea1.setBounds(276, 160, 791, 215);
		textArea1.setBorder(border);
		
		
		//textArea1.setEditable(false);
		
		getContentPane().add(textArea1);
		
		// TextArea
		final JTextArea textArea = new JTextArea(10,30);
		
		textArea.setBounds(276, 30, 291, 75);
		textArea.setBorder(border);
		//final JScrollPane scroll = new JScrollPane(textArea);
		//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setEditable(false);
	
		getContentPane().add(textArea);
		
		
		
		
		final JTextField textfield = new JTextField();
		textfield.setBounds(10,30,200,30);
		getContentPane().add(textfield);
		 
		// Label
		final JLabel lbl = new JLabel("HTTP Response:");
		lbl.setBounds(10, 180, 144, 14);
		getContentPane().add(lbl);
		
		final JLabel lb2 = new JLabel("Header Information:");
		lb2.setBounds(276, 10, 144, 14);
		getContentPane().add(lb2);
		
		final JLabel lb3 = new JLabel("Content of the site:");
		lb3.setBounds(276, 140, 144, 14);
		getContentPane().add(lb3);
		
		final JLabel lb4 = new JLabel();
		lb4.setBounds(12, 200, 174, 14);
		
		getContentPane().add(lb4);

		btn1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			
		//lbl.setText("Hello : "+ textArea.getText());

		//Read ln
		

		
Socket sock=null;
try {
	sock = new Socket("localhost",344);
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
		
		PrintStream ps=null;
		PrintStream ps1=null;
		PrintStream ps2=null;
		try {
			ps = new PrintStream(sock.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ps.println(textfield.getText());
		
		InputStreamReader input=null;
		try {
			input = new InputStreamReader(sock.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(input);
		
		String message=null;
		try {
			message = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(message);
		split1 = message.split("\t");
		
		textArea.setText(split1[1]+"\n"+split1[2]+"\n"+split1[3]);
		textArea1.setText(split1[4]);
		
	
	
		}
		});
		
		
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lb4.setText(split1[0]); 
				lb4.setVisible(true);
				
			}
		});
		}
	
}
