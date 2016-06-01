package flowgrammer.tas;

import org.json.JSONObject;

public class TMGHeader {

	public String LegacyId;
	public String AccountId;
	public String Min;
	public String Wifi;
	public String AuthKey;
	public String Version;
	
	public TMGHeader(String json) {
		JSONObject obj = new JSONObject(json);
		this.LegacyId = obj.getString("LegacyId");
		this.AccountId = obj.getString("AccountId");
		this.Min = obj.getString("Min");
		this.Wifi = obj.getString("Wifi");
		this.AuthKey = obj.getString("AuthKey");
		this.Version = obj.getString("Version");
	}
	
	public String getJson() {
		JSONObject obj = new JSONObject();
		obj.put("LegacyId", this.LegacyId);
		obj.put("AccountId", this.AccountId);
		obj.put("AccountId", this.AccountId);
		obj.put("Wifi", this.Wifi);
		obj.put("AuthKey", this.AuthKey);
		obj.put("Version", this.Version);
		return obj.toString();
	}
}
