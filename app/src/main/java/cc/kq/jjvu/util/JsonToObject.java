package cc.kq.jjvu.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonToObject {

	Gson gson = new Gson();

	public <T> T jsonTojava(String json, Class<T> type) {
		T result = gson.fromJson(json, type);
		return result;
	}
	
	public <T> List<T> jsonTolist(String json,Class<T> type) {
		
		List<T> result = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
		
		return result;
	}

}
