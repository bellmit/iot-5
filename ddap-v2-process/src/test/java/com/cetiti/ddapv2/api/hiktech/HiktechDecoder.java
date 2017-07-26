package com.cetiti.ddapv2.api.hiktech;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年6月9日
 * 
 */
public class HiktechDecoder extends ByteToMessageDecoder{
	
	private static final int ST_CORRUPTED = -1;
    private static final int ST_INIT = 0;
    private static final int ST_DECODING = 1;
    private static final byte ESCAPE = (byte) 0x99;
    
    private static final byte REGISTER_SUCCESS = (byte) 0x00;
    private static final int HOST_PACKET_TYPE_REGISTER_REP_COMMAND = 0x0001;
	private static final int HOST_PACKET_TYPE_ALIVE_REP_COMMAND = 0x0002;
	private static final int HOST_PACKET_TYPE_DATA_COMMAND = 0x1001;
	private static final int HOST_PACKET_TYPE_ALARMDATA_COMMAND = 0x1002;
	private static final int HOST_PACKET_TYPE_DEVSTATE_COMMAND = 0x0103;
	private static final Charset CHARSET = Charset.forName("UTF-8");
    
    private int state;
    private int idx;
    private byte[] frame = new byte[0xffff];
    
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		if(state==ST_INIT){
			while(in.isReadable()){
				if(in.readByte()==Instruction.HEAD[0]&&in.readByte()==Instruction.HEAD[1]){
					state = ST_DECODING;
					frame[0] = Instruction.HEAD[0];
					frame[1] = Instruction.HEAD[1];
					idx = 2;
					break;
				}
			}
		}
		
		if(state==ST_DECODING){
			while(in.isReadable()){
				byte by = in.readByte();
				frame[idx++] = by;
				if(by==Instruction.TAIL[0]){
					by = in.readByte();
					frame[idx++] = by;
					if(by==Instruction.TAIL[1]){
						Object retn = processFrame();
						if(null!=retn){
							if(retn instanceof Collection<?>){
								out.addAll((Collection<?>) retn);
							}else{
								out.add(retn);
							}
							
						}
					}
					state = ST_INIT;
					idx = 0;
				}
				if(by==ESCAPE){
					by = in.readByte();
					frame[idx++] = by;
					by = (byte) ~by;
					if(Instruction.HEAD[0]!=by&&ESCAPE!=by&&Instruction.TAIL[0]!=by){
						state = ST_INIT;
						idx = 0;
						System.err.println("escape code error."+by);
					}
				}
			}
		}
	}
	
	private Object processFrame(){
		int j = 0;
		byte[] escapeFrame = new byte[idx];
		byte checkSum = 0;
		boolean escape = false;
		for(int i=0; i<idx-3; i++){
			checkSum += frame[i];
			if(i>1&&frame[i]==ESCAPE){
				escape = true;
				continue;
			}
			if(escape){
				escapeFrame[j++] = (byte) ~frame[i];
				escape = false;
			}else{
				escapeFrame[j++] = frame[i];
			}
		}
		if(frame[idx-3]!=checkSum){
			System.err.println("checksum error, checksum:"+checkSum);
			return null;
		}
		
		if(j-Instruction.HEAD.length!=(escapeFrame[3]<<8) + escapeFrame[2]-1){
			System.err.println("length error, length:"+j);
			return null;
		}
		
		Object retn = null;
		int type = (escapeFrame[6]<<8) + escapeFrame[5];
		byte[] payload = new byte[j-7];
		System.arraycopy(escapeFrame, 7, payload, 0, payload.length);
		switch (type) {
		case HOST_PACKET_TYPE_REGISTER_REP_COMMAND:
			if(escapeFrame[7]==REGISTER_SUCCESS){
				System.out.println("register success");
			}else{
				System.err.println("register failed");
			}
			break;
		case HOST_PACKET_TYPE_ALIVE_REP_COMMAND:
			System.out.println("keepalive response");
			break;
		case HOST_PACKET_TYPE_DATA_COMMAND:
			System.out.println("HOST_PACKET_TYPE_DATA_COMMAND");
			String data = new String(decompress(payload), CHARSET);
			System.out.println(data);
			List<RfidData> list = new ArrayList<RfidData>();
			for(String s:data.split(Instruction.LINE_SEPERATOR)){
				list.add(new RfidData(s));
			}
			retn = list;
			break;
		case HOST_PACKET_TYPE_ALARMDATA_COMMAND:
			System.out.println("HOST_PACKET_TYPE_ALARMDATA_COMMAND");
			data = new String(decompress(payload), CHARSET);
			List<RfidAlarm> alarmList = new ArrayList<RfidAlarm>();
			for(String s:data.split(Instruction.LINE_SEPERATOR)){
				alarmList.add(new RfidAlarm(s));
			}
			retn = alarmList;
			break;
		case HOST_PACKET_TYPE_DEVSTATE_COMMAND:
			System.out.println("HOST_PACKET_TYPE_DEVSTATE_COMMAND");
			data = new String(decompress(payload), CHARSET);
			List<RfidState> stateList = new ArrayList<RfidState>();
			for(String s:data.split(Instruction.LINE_SEPERATOR)){
				stateList.add(new RfidState(s));
			}
			retn = stateList;
			break;
		default:
			break;
		}
		
		return retn;
	}
	
	public static byte[] decompress(byte[] data) {  
        ByteArrayInputStream is = new ByteArrayInputStream(data);  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
  
        int count = 0;  
	    byte buf[] = new byte[1024]; 
	    try{
	    	GZIPInputStream gis = new GZIPInputStream(is);  
	 	    while ((count = gis.read(buf, 0, 1024)) != -1) {  
	 	        os.write(buf, 0, count);  
	 	    }  
	 	    gis.close(); 
	        data = os.toByteArray();  
	    }catch (IOException e) {
			System.err.println("gzip decompress exception! message:"+e.getMessage());
			data = new byte[0];
		}
    	 
        return data;  
    }  
	
    public static byte[] compress(String str, String encoding) {  
        if (str == null || str.length() == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        GZIPOutputStream gzip;  
        try {  
            gzip = new GZIPOutputStream(out);  
            gzip.write(str.getBytes(encoding));  
            gzip.close();  
        } catch (IOException e) {  
        	System.out.println("gzip compress excption! message:"+e.getMessage());
        }  
        return out.toByteArray();  
    }  
    
    public static void main(String[] args){
    /*	System.out.println("gzip");
    	System.out.println(new String(decompress(compress("gzip", "utf-8"))));*/
    	byte[] bytes = {90, -103};
    	for(byte by:bytes){
    		if(Instruction.HEAD[0]!=by&&ESCAPE!=by&&Instruction.TAIL[0]!=by){
    			System.out.println("escape code error."+by);
    		}
    	}
    	
    	System.out.println(Instruction.HEAD[0]);
    	System.out.println(Instruction.HEAD[0]==0x5a);
    }

}
