package json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class MainClass04 {
	public static void main(String[] args) {
		List<String> names=new ArrayList<String>();
		names.add("김구라");
		names.add("해골");
		names.add("원숭이");
		/*
		 * 위의 List에 저장된 모든 문자열을 JSON
		 * ["김구라", "해골", "원숭이", ...]
		 * 형식으로 만든다고 가정하자
		 * 만일 라이브러리를 사용하지 않는다면 아래와 같은 코드가 된다.
		 */
		String result1="[";
		for(int i=0; i<names.size(); i++) {
			String tmp=names.get(i);
			//만일 인덱스가 마지막 인덱스라면
			if(i==names.size()-1) {
				//, 대신에 대괄호를 닫아준다.
				result1=result1+"\""+tmp+"\"]";
			}else {//그 이외 경우에는, 를 뒤에 붙여준다.
				result1=result1+"\""+tmp+"\",";
			}
		}
		System.out.println("result1:"+result1);
		
		//라이브러리를 사용한다면 아래와 같다.
		JSONArray arr=new JSONArray(names);
		String result2=arr.toString();
		
		System.out.println("result2:"+result2);
	}
}
/* 
 * ArrayList 는 Collection<E> 를 상속받기도 한다.
 */
