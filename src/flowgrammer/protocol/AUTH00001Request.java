package flowgrammer.protocol;

import org.json.JSONObject;

import flowgrammer.tas.TMGBody;

public class AUTH00001Request extends TMGBody {

	public String Signature;
	public String DisplayName;
	public String Password;
	public String AppVersion;
	
	public AUTH00001Request(String json) {
		super(json);
		JSONObject obj = new JSONObject(json);
		this.Signature = obj.getString("Signature");
		this.DisplayName = obj.getString("DisplayName");
		this.Password = obj.getString("Password");
		this.AppVersion = obj.getString("AppVersion");
	}
	
	@Override
	public String getJson() {
		JSONObject obj = new JSONObject();
		obj.put("Signature", this.Signature);
		obj.put("DisplayName", this.DisplayName);
		obj.put("Password", this.Password);
		obj.put("AppVersion", this.AppVersion);
		return obj.toString();
	}
}
