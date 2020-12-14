package example4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener{
	JTextField tf;
	//생성자
	public ClientFrame(String title) {
		super(title);
		setLayout(new BorderLayout());
		
		//페널을 만들어서 프레임의 위쪽에 배치하기 
		JPanel topPanel=new JPanel();
		topPanel.setBackground(Color.YELLOW);
		add(topPanel, BorderLayout.NORTH);
		
		//프레임에 UI 배치하기
		tf=new JTextField(10);
		JButton sendBtn=new JButton("Send");
		topPanel.add(tf);
		topPanel.add(sendBtn);
		//버튼 리스너 등록하기
		sendBtn.addActionListener(this);
	}
	
	public static void main(String[] args) {
		//프레임객체 생성하면서 프레임의 제목을 chatting 으로 지정하기 
		ClientFrame f=new ClientFrame("chatting");
		//프레임의 x좌표, y좌표, width, height 설정하기
		f.setBounds(100, 100, 300, 200);
		//프레임을 닫을때 프로세스도 종료 되도록 한다. 
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//프레임을 화면상에 보이게 하기 
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//1. JTextField 에 입력한 문자열을 읽어와서
		String msg=tf.getText();
		//2. Socket 접속을 해서 전송한다.
		Socket socket=null;
		try {
			// new Socket("접속할 ip 주소", 포트번호)
			socket=new Socket("127.0.0.1", 5000);
			System.out.println("서버에 Socket 접속 성공!");
			//2. Socket 을 통해서 출력하기 
			OutputStream os=socket.getOutputStream();
			OutputStreamWriter osw=new OutputStreamWriter(os);
			osw.write(msg); //입력한 문자열 출력 
			osw.write("\r\n"); //개행기호 출력
			osw.flush(); //방출
			
			//3. Socket 을 통해 입력 받기
			InputStream is=socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			
			//서버가 전송한 문자열 읽어들이기
			String line=br.readLine();
			System.out.println(line);
			
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}		
		//3. JTextField 에 입력한 문자열 삭제
		tf.setText("");
	}
}




