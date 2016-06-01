package flowgrammer.tas;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<TMGPacket> {

	Logger log = Logger.getLogger(PacketEncoder.class);
	
	@Override
	protected void encode(ChannelHandlerContext ctx, TMGPacket response, ByteBuf out)
			throws Exception {
		
		out.writeBytes(response.headerVersion.getBytes("UTF-8"), 0, 6);
		out.writeBytes(response.applicationId.getBytes("UTF-8"), 0, 4);
		out.writeBytes(response.messageId.getBytes("UTF-8"), 0, 9);
		out.writeLong(response.sessionId);
		out.writeLong(response.transoutActionId);
		out.writeInt(response.serviceId);
		out.writeBytes(response.imei.getBytes("UTF-8"), 0, 20);
		out.writeBytes(response.wifi.getBytes("UTF-8"), 0, 20);
		out.writeBytes(response.msisdn.getBytes("UTF-8"), 0, 20);
		out.writeBytes(response.model.getBytes("UTF-8"), 0, 30);
		out.writeBytes(response.ispName.getBytes("UTF-8"), 0, 10);
		out.writeBytes(response.osType.getBytes("UTF-8"), 0, 1);
		out.writeBytes(response.osVersion.getBytes("UTF-8"), 0, 20);
		out.writeBytes(response.uuid.getBytes("UTF-8"), 0, 24);
		out.writeShort(response.bodyType);
		out.writeShort(response.statusCode);
		
		byte[] header = response.getHeaderInJson().getBytes("UTF-8");
		int headerLength = header.length;
		
		
		byte[] body = response.getBodyInJson().getBytes("UTF-8");
		int bodyLength = body.length;
		
		log.info("response header : " + response.getHeaderInJson());
		log.info("response body : " + response.getBodyInJson());
		
		out.writeInt(headerLength + 4);
		out.writeInt(bodyLength + 4);
		
		out.writeInt(headerLength);
		out.writeBytes(header);
		
		out.writeInt(bodyLength);
		out.writeBytes(body);
	}

}
