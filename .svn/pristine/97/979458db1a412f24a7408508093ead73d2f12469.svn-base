/**
 * 作者    艾建
 * 日期    2011/12/08
 * 功能    密码加密解密函数
 * 修改历史
 * 修改日期:     	修改人:      修改目的:
 */
package com.ant.util;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *功能描述：
 */

public class PwdCrypt {
	
	private static Log log = LogFactory.getLog(PwdCrypt.class);
	final String str = "sunmao@123456";
	
	public static PwdCrypt getInstance(){
		return new PwdCrypt();
	}
    /**
     * 加密操作
     * 入参 data 明文
     * @return 密文
     */
    public String encrypt(String data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(simplecrypt(data).getBytes());
    }
    
    /**
     * 解密操作
     * 入参 data密文
     * @return 明文
     * @throws IOException
     */
    public String decrypt(String data) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] result = null;
		try {
			result = decoder.decodeBuffer(data);
		} catch (IOException e) {
			log.error(e.getMessage() , e);
		}
        return simplecrypt(new String(result));
    }
    
    /**
     * 
     *功能描述：进行常量异或
     *@author lianglp
     *@param
     *@return String
     *@throws原文：
     *@version 1.0
     *@date Mar 9, 200911:44:02 AM
     */
    public String simplecrypt(String data){
    	char[] a = data.toCharArray();
		for (int i = 0; i < a.length; i++) {
			for(int j=0;j<str.length();j++){
				char c = str.charAt(j);
				a[i] = (char) (a[i] ^ c);
			}
		}
		String s = new String(a);
		return s;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String miwen = PwdCrypt.getInstance().encrypt("aj");
		System.out.println("原文：aj");
		System.out.println("加密后：" + miwen);
		System.out.println("解密后：" + PwdCrypt.getInstance().decrypt(miwen));
	}
}