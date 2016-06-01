package flowgrammer.tas;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apache.log4j.Logger;

public class PacketDecoder extends ByteToMessageDecoder {
	
	Logger log = Logger.getLogger(PacketDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
        // Wait until the length prefix is available.
        if (in.readableBytes() < 196) {
            return;
        }

        in.markReaderIndex();
        
        byte[] decoded = new byte[6];
        in.readBytes(decoded, 0, 6);
        String headerVersion = new String(decoded, "UTF-8");

        decoded = new byte[4];
        in.readBytes(decoded, 0, 4);
        String applicationId = new String(decoded, "UTF-8");

        decoded = new byte[9];
        in.readBytes(decoded, 0, 9);
        String messageId = new String(decoded, "UTF-8");
        
        long sessionId = in.readLong();
        long transoutActionId = in.readLong();
        int serviceId = in.readInt();
        
        decoded = new byte[20];
        in.readBytes(decoded, 0, 20);
        String imei = new String(decoded, "UTF-8");
        
        decoded = new byte[20];
        in.readBytes(decoded, 0, 20);
        String wifi = new String(decoded, "UTF-8");

        decoded = new byte[20];
        in.readBytes(decoded, 0, 20);
        String msisdn = new String(decoded, "UTF-8");
        
        decoded = new byte[30];
        in.readBytes(decoded, 0, 30);
        String model = new String(decoded, "UTF-8");

        decoded = new byte[10];
        in.readBytes(decoded, 0, 10);
        String ispName = new String(decoded, "UTF-8");

        decoded = new byte[1];
        in.readBytes(decoded, 0, 1);
        String osType = new String(decoded, "UTF-8");

        decoded = new byte[20];
        in.readBytes(decoded, 0, 20);
        String osVersion = new String(decoded, "UTF-8");

        decoded = new byte[24];
        in.readBytes(decoded, 0, 24);
        String uuid = new String(decoded, "UTF-8");

        int bodyType = in.readShort();
        int statusCode = in.readShort();
        
        int headerLength = in.readInt();
        int bodyLength = in.readInt();

        if (in.readableBytes() < headerLength + bodyLength) {
        	in.resetReaderIndex();
        	return;
        }
        
        in.readInt();
        decoded = new byte[headerLength - 4];
        in.readBytes(decoded, 0, headerLength - 4);
        String header = new String(decoded, "UTF-8");
        
        String body = "";
        if (bodyLength > 0) {
        	in.readInt();
        	decoded = new byte[bodyLength - 4];
        	in.readBytes(decoded, 0, bodyLength - 4);
        	body = new String(decoded, "UTF-8");
        }
        
        log.debug("header version : " + headerVersion);
        log.debug("application id : " + applicationId);
        log.debug("message id : " + messageId);
        log.debug("session id : " + sessionId);
        log.debug("transout action id : " + transoutActionId);
        log.debug("service id : " + serviceId);
        log.debug("imei : " + imei);
        log.debug("wifi : " + wifi);
        log.debug("os type : " + osType);
        log.debug("status : " + statusCode);
        log.debug("header length : " + headerLength);
        log.debug("body length : " + bodyLength);
        
//        log.info("header : " + header);
//        log.info("body : " + body);
        
        TMGPacket packet = new TMGPacket();
        
        packet.headerVersion = headerVersion;
        packet.applicationId = applicationId;
        packet.messageId = messageId;
        packet.sessionId = sessionId;
        packet.transoutActionId = transoutActionId;
        packet.serviceId = serviceId;
        packet.imei = imei;
        packet.wifi = wifi;
        packet.msisdn = msisdn;
        packet.model = model;
        packet.ispName = ispName;
        packet.osType = osType;
        packet.osVersion = osVersion;
        packet.uuid = uuid;
        packet.bodyType = bodyType;
        packet.statusCode = statusCode;
        packet.headerLength = headerLength;
        packet.bodyLength = bodyLength;
        packet.setHeader(header);
        packet.setRequestBody(body);
        
        out.add(packet);
	}
}
