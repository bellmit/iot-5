package com.cetiti.ddapv2.api.hiktech;

import com.cetiti.ddapv2.process.util.IpPortUtil;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年6月8日
 * 
 */
public class Instruction {
	
	static final byte[] HEAD = {0x5A, 0x55};
	static final byte[] TAIL = {0x6A, 0x69};
	static final byte PORT = 0x0A;
	
	private static final byte[] HOST_PACKET_TYPE_ALIVE_REP_COMMAND = {0x00, 0x02};

	private static final byte[] CLINET_PACKET_TYPE_REGISTER_COMMAND = {0x00, 0x01};
	private static final byte[] CLINET_PACKET_TYPE_ALIVE_COMMAND = {0x00, 0x02};
	
	static final String ELEMENT_SEPERATOR = "\t";
	static final String LINE_SEPERATOR = "\n";
	
	public enum SystemType{
		
		ElectroMobile(new byte[]{'0', '1'}),
		Hotel(new byte[]{'0', '2'}),
		OldPeople(new byte[]{'0', '3'}),
		Student(new byte[]{'0', '4'}),
		Citizen(new byte[]{'0', '5'});
		
		private byte[] value;
		
		private SystemType(byte[] bytes){
			this.value = bytes;
		}
		
		public byte[] value(){
			return this.value;
		}
	}
	
	static class Builder{
		private byte[] headers = new byte[7];
		private byte[] payload = new byte[0];
		private byte[] tails = new byte[3];
		
		private Builder() {
			System.arraycopy(HEAD, 0, headers, 0, 2);
			System.arraycopy(TAIL, 0, tails, 1, 2);
			headers[4] = PORT;
		}
		
		public static Builder create(){
			return new Builder();
		}
		
		public Builder setPort(byte port){
			headers[4] = port;
			return this;
		}
		
		public Builder setInstructionType(byte[] type){
			if(type.length==2){
				headers[5] = type[1];
				headers[6] = type[0];
			}
			return this;
		}
		
		public Builder setPayload(byte[] pl){
			this.payload = pl;
			return this;
		}
		
		public byte[] build(){
			int totalLength = headers.length+payload.length+tails.length;
			int length = totalLength - HEAD.length - TAIL.length;
			headers[2] = (byte) (length&0xff);
			length >>= 8;
			headers[3] = (byte) (length&0xff);
	
			byte[] instruct = new byte[totalLength]; 
			System.arraycopy(headers, 0, instruct, 0, headers.length);
			System.arraycopy(payload, 0, instruct, headers.length, payload.length);
			System.arraycopy(tails, 0, instruct, headers.length+payload.length, tails.length);
			
			byte checkSum = 0;
			for(int i=0; i<totalLength-3; i++){
				checkSum += instruct[i];
			}
			instruct[totalLength-3] = checkSum;
			
			return instruct;
		}
	}
	
	private static byte[] createIpString(){
		String ip = IpPortUtil.SERVER_IP;
		byte[] ipbyte = new byte[12];
		int i = 0;
		for(String s:ip.split("\\.")){
			int d = 0;
			try{
				d = Integer.parseInt(s);
			}catch (NumberFormatException e) {
				throw new RuntimeException("createIpString");
			}
			ipbyte[i] = (byte)(d/100+'0');
			i++;
			d = d%100;
			ipbyte[i] = (byte)(d/10+'0');
			i++;
			ipbyte[i] = (byte)(d%10+'0');
			i++;
		}
		return ipbyte;
	}
	
	private static byte[] createClientInstruction(SystemType systemType, byte dataType, byte[] instrctionType){
		byte[] payload = new byte[17];
		byte[] ipbyte = createIpString();
		System.arraycopy(ipbyte, 0, payload, 0, 12);
		payload[12] = systemType.value()[0];
		payload[13] = systemType.value()[1];
		/*payload[14] = 0x30;
		payload[15] = 0x30;*/
		payload[16] = dataType;
		
		return Builder.create().setInstructionType(instrctionType)
				.setPayload(payload).build();
		
	}
	
	public static byte[] createClientRegisterInstruction(SystemType systemType, byte dataType){
		return createClientInstruction(systemType, dataType, CLINET_PACKET_TYPE_REGISTER_COMMAND);
	}
	
	public static byte[] createClientKeepaliveInstruction(SystemType systemType, byte dataType){
		return createClientInstruction(systemType, dataType, CLINET_PACKET_TYPE_ALIVE_COMMAND);
	}
	
	public static byte[] createTestInstruction(){
		return Builder.create().setInstructionType(HOST_PACKET_TYPE_ALIVE_REP_COMMAND)
			.setPayload(new byte[0]).build();
	}
	
	public static void main(String[] args){
		
		for(byte b:createClientRegisterInstruction(SystemType.ElectroMobile, (byte)0x37)){
			System.out.print("0x"+b);
		}
		System.out.println();
		for(byte b:createClientKeepaliveInstruction(SystemType.ElectroMobile, (byte)0x37)){
			System.out.print("0x"+b);
		}
		System.out.println();
		System.out.println((byte)(1+'0'));
		
	}
	
}
