package json;

import org.json.JSONObject;

public class MainClass02 {
	public static void main(String[] args) {
		JSONObject obj=new JSONObject();
		obj.put("num", 1);
		obj.put("name", "김구라");
		obj.put("isMan", true);
		
		String result1=obj.toString();
		System.out.println("result1:"+result1);
	}
}
