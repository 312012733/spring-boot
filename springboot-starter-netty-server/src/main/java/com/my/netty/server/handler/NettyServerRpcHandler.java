package com.my.netty.server.handler;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.netty.model.NettyResponse;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
public class NettyServerRpcHandler extends ChannelInboundHandlerAdapter
{
    private static final String HEART_BEAT = "heart-beat";
    
    private static final Logger LOG = LoggerFactory.getLogger(NettyServerRpcHandler.class);
    
    public static final String NETTY_MSG_END = "\r\n";
    
    private ApplicationContext context;
    
    public NettyServerRpcHandler(ApplicationContext context)
    {
        this.context = context;
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        
        if (HEART_BEAT.equals(msg.toString()))
        {
            return;
        }
        
        LOG.info("【--------------netty-server receive({}):\n{}\n】 ", ctx.channel().remoteAddress(), msg);
        
        JSONObject jsonObject = (JSONObject) JSONObject.parse(msg.toString());
        JSONObject classMsgBean = jsonObject.getJSONObject("classMsgBean");
        JSONArray argsType = classMsgBean.getJSONArray("argsType");
        JSONArray argsValue = classMsgBean.getJSONArray("args");
        
        int argsSize = argsType.size();
        
        Class<?>[] targetArgTypes = new Class[argsSize];
        Object[] targetAgs = new Object[argsSize];
        
        for (int i = 0; i < argsSize; i++)
        {
            String argTypeName = argsType.getString(i);
            Class<?> argType = Class.forName(argTypeName);
            
            targetArgTypes[i] = argType;
            targetAgs[i] = argsValue.getObject(i, argType);
        }
        
        String className = classMsgBean.getString("className");
        String methodName = classMsgBean.getString("method");
        
        Class<?> targetClass = Class.forName(className);
        Method targetMethod = targetClass.getMethod(methodName, targetArgTypes);
        
        long id = jsonObject.getLongValue("id");
        Object target = context.getBean(targetClass);
        Object result = targetMethod.invoke(target, targetAgs);
        
        NettyResponse response = new NettyResponse();
        
        response.setId(id);
        response.setContent(result);
        
        String msgJson = JSONObject.toJSONString(response);
        
        ctx.channel().writeAndFlush(msgJson + NETTY_MSG_END);
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE)
            {
                throw new Exception("reader_idle exception ");
            }
            else if (state == IdleState.WRITER_IDLE)
            {
                throw new Exception("writer_idle exception ");
            }
            else if (state == IdleState.ALL_IDLE)
            {
                ctx.channel().writeAndFlush(HEART_BEAT + NETTY_MSG_END);
            }
        }
        
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        LOG.info("【--------------channelActive.{}】", ctx.channel().remoteAddress());
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        LOG.info("【--------------channelInactive.{}】", ctx.channel().remoteAddress());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        LOG.error("【--------------exceptionCaught." + ctx.channel().remoteAddress() + "】", cause);
        ctx.channel().close();
    }
    
}
