package json;

import org.json.JSONArray;
/* 
 * {"오늘 할일":["은행가기","게임하기","친구 만나기"]}
 * {"약속1":{"날짜":"2020년 12월 24일", "친구":["김","이","박"]}}
 */
public class MainClass01 {
	public static void main(String[] args) {
		JSONArray arr=new JSONArray();
		arr.put(10);//숫자 입력 put(int)
		arr.put(20);
		arr.put(30);
		//JSONarray 객체 안에 있는 정보를 JSON 표기법에 맞는 문자열로 얻어내기
		String result1=arr.toString();
		System.out.println("result1:"+result1);
		
		JSONArray arr2=new JSONArray();
		arr2.put("kim"); //문자 입력 put(long)
		arr2.put("lee");
		arr2.put("park");
		String result2=arr2.toString();
		System.out.println("result2:"+result2);
	}
}
/*
 * jar 파일은 사용할 때 다운받아서 경로를 설정해주면 된다.
 * 20190722 JSON 파일 사용
 * JSON 문법 (JSON 문자열의 작성 규칙)
 * 숫자는 그냥 나열, 문자는 ""따옴표로 감싼다.
 * {"age":28, "name":"김홍주", "addr":"남구"}
 * [3, 30, 33] 
 * {"age":28, "name":"김홍주", "addr":"남구", "numbers": [3, 30, 33] }
 * 
 * 형식에 맞춰 사용하는 이유 - 프로그래밍 언어에서 쉽게 다룰 수 있도록 정해놓은 것. 
 * JSON 에는 이 7가지 문법만 들어갈 수 있다. 
 * 숫자, 문자, 불리언(true, false), {}, [], null 만 넣을 수 있다. 
 * 
 * {"num":10}
 * {"name":"kim"}
 * {"isMan":true}
 * {"info":{}}
 * {"info":{"age":10,"loc":"seoul"}}
 * [10, 20, 30] ["kim", "lee", "park"] [{},{}]
 * [{"age":10,"loc":"seoul"}],{"age":20,"loc":"busan"}}
 * [[],[],[]]
 * {"num":1, "name":"kim", "addr":null}
 * 
 * JSON String 
 * |(convert) [org.json.JSONObject / JSONArray] JSON String을 쉽게 사용할 수 있게 도와준다.
 * JAVA Object 
 * | 
 * DB
 */