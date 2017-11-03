package com.cetiti.ddapv2.process.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年6月13日
 * 
 */
public class GzipUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GzipUtil.class);
	
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
	    	LOGGER.error("gzip decompress [{}] exception! [{}]", data, e.getMessage());
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
        	LOGGER.error("gzip compress [{}] encoding [{}] excption! [{}]", str, encoding, e.getMessage());
        }  
        return out.toByteArray();  
    }  
    
}
