package flowgrammer.protocol;

import org.json.JSONObject;

import flowgrammer.tas.TMGBody;

public class COM000001Response extends TMGBody {

	public String Latest_App_Yn;
	public String Mail_Count;
	public String Schedule_Count;
	
	public String Email;
	
	@Override
	public String getJson() {
		JSONObject obj = new JSONObject();
		obj.put("Latest_App_Yn", this.Latest_App_Yn);
		obj.put("Mail_Count", this.Mail_Count);
		obj.put("Schedule_Count", this.Schedule_Count);
		return obj.toString();
	}
}
