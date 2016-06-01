package flowgrammer.tas;

import flowgrammer.protocol.AUTH00001Request;
import flowgrammer.protocol.AUTH00001Response;
import flowgrammer.protocol.COM000001Request;



public class TMGPacket {
    public TMGPacket(TMGPacket packet) {
		
    	this.headerVersion = packet.headerVersion;
    	this.applicationId = packet.applicationId;
    	this.messageId = packet.messageId;
    	this.sessionId = packet.sessionId;
    	this.transoutActionId = packet.transoutActionId;
    	this.serviceId = packet.serviceId;
    	this.imei = packet.imei;
    	this.wifi = packet.wifi;
    	this.msisdn = packet.msisdn;
    	this.model = packet.model;
    	this.ispName = packet.ispName;
    	this.osType = packet.osType;
    	this.osVersion = packet.osVersion;
    	this.uuid = packet.uuid;
    	this.bodyType = packet.bodyType;
    	this.statusCode = packet.statusCode;
    	this.header = packet.header;
    	this.body = new TMGBody("{}");
//    	this.body = "";
	}
    
	public TMGPacket() {
		// TODO Auto-generated constructor stub
	}
	
//	public Object getHeaderField(String key){
//		JSONObject obj = new JSONObject(this.header);
//		return obj.get(key);
//	}
	
	public String headerVersion;
    public String applicationId;
    public String messageId;
    public long sessionId;
    public long transoutActionId;
    public int serviceId;
    public String imei;
    public String wifi;
    public String msisdn;
    public String model;
    public String ispName;
    public String osType;
    public String osVersion;
    public String uuid;
    public int bodyType;
    public int statusCode;
    public int headerLength;
    public int bodyLength;
    private TMGHeader header;
    private TMGBody body;
    
    public void setHeader(String json) {
    	this.header = new TMGHeader(json);
    }
    
    public TMGHeader getHeader() {
    	return this.header;
    }
    
    public String getHeaderInJson() {
    	return this.header.getJson();
    }
    
    public void setRequestBody(String json) {
        if (this.messageId.equalsIgnoreCase("AUTH00001")) {
        	this.body = new AUTH00001Request(json);
        }
        else if (this.messageId.equalsIgnoreCase("COM000001")) {
        	this.body = new COM000001Request(json);
        }
        else {
        	this.body = new TMGBody(json);
        }
    }
    
    public void setResponseBody(TMGBody body) {
    	this.body = body;
    }
    
    public TMGBody getBody() {
    	return this.body;
    }
    
    public String getBodyInJson() {
    	return this.body.getJson();
    }
}
