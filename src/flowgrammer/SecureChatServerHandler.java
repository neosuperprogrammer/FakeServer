/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package flowgrammer;

import org.apache.log4j.Logger;

import flowgrammer.protocol.AUTH00001Response;
import flowgrammer.protocol.COM000001Response;
import flowgrammer.tas.TMGHeader;
import flowgrammer.tas.TMGPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<TMGPacket> {

    private static final Logger log = Logger.getLogger(SecureChatServerHandler.class);

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
//                ctx.writeAndFlush(
//                        "Welcome to " + InetAddress.getLocalHost().getHostName() +
//                        " secure chat service!\n");
//                ctx.writeAndFlush(
//                        "Your session is protected by " +
//                        ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
//                        " cipher suite.\n");

                channels.add(ctx.channel());
            }
        });
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TMGPacket request) throws Exception {
    	
    	log.info("request header : " + request.getHeaderInJson());
    	log.info("request body : " + request.getBodyInJson());
    	
    	TMGPacket response = new TMGPacket(request);
    	
    	if (request.messageId.equals("AUTH00001")) {
    		TMGHeader header = response.getHeader();
    		header.AuthKey = "test";
    		AUTH00001Response body = new AUTH00001Response();
    		body.Email = "test@test.com";
    		response.setResponseBody(body);
    	}
    	else if (request.messageId.equals("COM000001")) {
    		COM000001Response body = new COM000001Response();
    		body.Latest_App_Yn = "Y";
    		body.Mail_Count = "1";
    		body.Schedule_Count = "1";
    		response.setResponseBody(body);
    	}
    	else {
//    		response.body = "{}";
    	}

    	ctx.writeAndFlush(response);
//        // Send the received message to all channels but the current one.
//        for (Channel c: channels) {
//            if (c != ctx.channel()) {
//                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " +
//                        msg + '\n');
//            } else {
//                c.writeAndFlush("[you] " + msg + '\n');
//            }
//        }
//
//        // Close the connection if the client has sent 'bye'.
//        if ("bye".equals(msg.toLowerCase())) {
//            ctx.close();
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
