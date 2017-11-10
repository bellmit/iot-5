package com.cetiti.ddapv2.process.handler.json;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.service.DataService;
import com.cetiti.ddapv2.process.util.JsonUtil;
import com.cetiti.ddapv2.process.util.LocalCache;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.util.RestResult.CODE;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.DecoderException;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;

/**
 * @接收json格式的 {@code Data}, {@code Data} 必须包含deviceId，或者serialNumber和productId
 * @author Wuwuhao
 * @date 2017年11月10日
 * 
 */
@Service("defaultTcpServerHandler")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Sharable
public class DefaultTcpServerHandler extends SimpleChannelInboundHandler<Object> {

	@Resource
	private JsonUtil jsonUtil;
	@Resource
	private DataService dataService;
	@Resource
	private LocalCache localCache;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		Map<String, Object> mapData = jsonUtil.mapFromJson(msg.toString(), String.class, Object.class);
		if(null==mapData){
			throw new CorruptedFrameException("invalid json [{"+msg+"}]");
		}
		//System.out.println(mapData);
		dataService.process(buildData(mapData));
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(Unpooled.copiedBuffer(jsonUtil.toJson(RestResult.defaultSuccessResult()), CharsetUtil.UTF_8));
        ctx.flush();
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		RestResult result = null;
        if(cause instanceof DecoderException){
        	result = new RestResult(CODE.RET_BAD_RQUEST, cause.getMessage(), null);
        }else{
        	result = new RestResult(CODE.RET_NO_EXCEPTION, cause.getMessage(), null);
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer(jsonUtil.toJson(result), CharsetUtil.UTF_8))
        	.addListener(ChannelFutureListener.CLOSE);
        ctx.close();
    }
	
	private Data buildData(Map<String, Object> map) {
		Data data = new Data();
		Object id = map.get("deviceId");
		Object sn = map.get("serialNumber");
		Object pd = map.get("productId");
		if (null!=id) {
			data.setDeviceId(id.toString());
		} else if (null!=sn&&null!=pd) {
			String deviceId = localCache.getDeviceIdBySerialNumberAndProductId(sn.toString(), pd.toString());
			if (null!=deviceId) {
				data.setDeviceId(deviceId);
			} else {
				data.setDeviceId(sn.toString()+"/"+pd.toString());
			}
		} else {
			throw new CorruptedFrameException("invalid json [{"+map.toString()+"}], deviceId null");
		}
		map.remove("deviceId");
		map.remove("serialNumber");
		map.remove("productId");
		data.setMapData(map);
		return data;
	}

}
