package flowgrammer.protocol;

import org.json.JSONObject;

import flowgrammer.tas.TMGBody;

public class AUTH00001Response extends TMGBody {

	public String Email;
	
	@Override
	public String getJson() {
		JSONObject obj = new JSONObject();
		obj.put("Email", this.Email);
		return obj.toString();
	}
}
