package flowgrammer.protocol;

import org.json.JSONObject;

import flowgrammer.tas.TMGBody;

public class COM000001Request extends TMGBody {

	public String AppVeriosn;
	
	public COM000001Request(String json) {
		super(json);
		JSONObject obj = new JSONObject(json);
		this.AppVeriosn = obj.getString("AppVeriosn");
	}
	
	@Override
	public String getJson() {
		JSONObject obj = new JSONObject();
		obj.put("AppVeriosn", this.AppVeriosn);
		return obj.toString();
	}
}
