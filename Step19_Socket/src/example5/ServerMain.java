package example5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
	//접속한 클라이언트를 응대하는 스레드 객체의 '목록'을 담을 필드 목록-스레드 객체가 여러개 생긴다는 말.
	static List<ServerThread> threadList; //static 을 사용하는 이유? 메인메소드에서만 사용하려면 static이 있어야 한다. 
	
	public static void main(String[] args) {//run 하게 되면 들어오는 메인 스레드(작업단위) static main 이기 때문에 
		/* 
		 * static main 에서 참조하기 위해서는 필드, 클래스 전부 static이 붙어있어야 한다. 
		 * static은 구분된 공간이라는 것 기억하기.
		 */
		threadList=new ArrayList<ServerMain.ServerThread>();
		ServerSocket serverSocket=null;
		
		try {
			//5000 번 통신 port 를 열고 클라이언트의 접속을 기다린다.
			serverSocket=new ServerSocket(5000);
			/*
			 *  .accept() 메소드는 클라이언트가 접속을 해야지 리턴하는 메소드이다.
			 *  클라이언트가 접속을 해오면 해당 클라이언트와 연결된 Socket 객체의
			 *  참조값을 리턴한다. 
			 */
			while(true) {//메인스레드가 하는 일. 접속을 기다리다가 접속을 하면 스레드 시작. 반복.
				System.out.println("클라이언트의 Socket 접속을 기다립니다...");
				//1.클라이언트가 접속을 해오면
				Socket socket=serverSocket.accept();
				//2.새로운 스레드를 시작 시킨다.
				ServerThread t=new ServerThread(socket);
				t.start();
				//하는 일은 ServerThread에 코딩을 하겠다. 
				
				//3.시작된 스레드의 참조값을 목록에 누적 시킨다.
				threadList.add(t);//소캣객체의 참조값이 다 다른 스레드가 누적된다. 10명이 들어오면 10개의 참조값이 다 다르다.
				/* 
				 * 스레드들은 전부 독립적으로 움직인다.
				 * 각각 접속한 클라이언트의 숫자만큼 생겨서 일을 진행.(다들 하는 일이 다르기 때문)
				 * 
				 */
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("ServerMain main 메소드가 종료 됩니다.");
	}
	
	//스레드 클래스 설계 (main 클래스가 시작되었을 때 스레드가 하는 일 코딩) static이 붙은 이유. 메인 메소드에 사용하려면 내부 클래스에서도 static을 붙여야 한다. 아니면 참조할 수 없음. 
	static class ServerThread extends Thread{//스레드에서 상속받기
		
		//필드(클라이언트와 연결된 Socket 객체의 참조값을 저장할 필드)
		private Socket socket;
		//클라이언트에게 문자열을 출력할 객체의 참조값을 저장할 필드
		private BufferedWriter bw;
		
		//생성자
		public ServerThread(Socket socket) {
			//생성자의 인자로 전달받은 Socket 객체의 참조값을 필드에 저장하기
			this.socket=socket;
			
		}
		
		//인자로 전달되는 문자열을 Socket 객체를 통해서 출력하는 메소드
		public void broadcast(String msg) throws IOException { //서버스레드 메소드는 브로드캐스트라는 메소드를 갖게 되었다. 
			//인자로 전달된 문자열을 필드에 저장된 bufferedWriter 객체의 참조값을 이용해서 클라이언트에게 출력하기
			bw.write(msg);
			bw.newLine();//개행기호
			bw.flush();//방출
			/*
			 * bw는 이미 트라이캐치로 묶인 곳에서 호출하는 것이기 때문에
			 * throws해도 밑에서 묶여있으므로 상관없다. 
			 * *나의 질문*
			 * bw가 이미 트라이캐치 안에 묶여있는 상태에서 호출을 하는 것이기 때문에 throws로 넘겨버려도 상관없다는 건가요?
			 * close는 모든 것이 끝나고 나서 닫는다. 
			 */
		}
		
		@Override
		public void run() {
			try {
				
//				InputStream is=socket.getInputStream();
//				InputStreamReader isr=new InputStreamReader(is);
//				BufferedReader br=new BufferedReader(isr);
				
				//위의 코드 한줄로 바꾸기
				//클라이언트가 전송하는 문자열을 읽어들일 객체
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				//클라이언트에게 문자열을 출력할 객체
				bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				//반복문 돌면서 클라이언트가 전송하는 문자열이 있는지 읽어와 본다.
				while(true) {
					//문자열 한줄이 전송될 때 까지 블록킹 되는 메소드
					String line=br.readLine();//접속한 클라이언트의 갯수만큼 생성될 예정.(갯수만큼 있다.) 클라이언트가 전송한 문자열을 읽어와서. 누군가 나가면 이곳에서 익셉션이 발생!
					//반복문 돌면서 모든 클라이언트에게 전송하기. 어떤 클라이언트가 문자를 보내면 모든 클라이언트에게 중계를 하겠다.
					for(ServerThread tmp:threadList) { // *확장 for문 확실히 알기 분석!*
						tmp.broadcast(line);
					}
					
				}
			}catch (Exception e) {
				e.printStackTrace(); //익셉션 발생 뛰어넘고.
			}finally {
				try {
					socket.close(); //소켓 클로즈
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//현재 여기에 실행순서가 넘어온 스레드의 참조값은? this 로 참조가 가능하다
			
			//오류가 나거나 접속 종료된 스레드는 목록에서 제거 해야한다.
			threadList.remove(this);//오류난 지점이 현재 스레드이기 때문에 this
		}//run()
	}//class ServerThread
}
/* 
 * heap 영역에 스레드가 여러개 생김. 클라이언트가 들어온 만큼...
 */




