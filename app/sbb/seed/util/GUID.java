package sbb.seed.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GUID {
	private static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	private static int digitsSize = digits.length;
	
	private static int[] seeder = new int[8];
	
	private static char[] key = new char[32];
	
	static{
		
		StringBuilder baseBuilder = new StringBuilder();
		
		long currentTime = System.currentTimeMillis();
		
		int timeInt = (int) currentTime& 0xFFFFFFFF;
		
		baseBuilder.append(Integer.toHexString(timeInt));
				
		InetAddress localInetAddress = null;

		try{
			
			localInetAddress = InetAddress.getLocalHost();
			
		}catch( final UnknownHostException uhe ){
			
			try{
				localInetAddress = InetAddress.getByName( "localhost" );
			}catch ( final UnknownHostException e ){}
			
		}
		
		byte serverIP[] = localInetAddress.getAddress();
		
				
		
		baseBuilder.append(Integer.toHexString(getInt(serverIP)));
		
		String sPid = ManagementFactory.getRuntimeMXBean().getName();
		
		int lPid = Integer.parseInt(sPid.substring(0, sPid.indexOf('@')));
				
		String hexPid = Integer.toHexString(lPid);
		
		for(int i = 0; i< 8 - hexPid.length(); i++){
			baseBuilder.append('0');
		}
		
		baseBuilder.append(hexPid);
		
		for(int i = 0; i< 32; i++){
			key[i] = '0';
		}
		
		for(int i = 0;i < baseBuilder.length(); i++){
			key[i] = baseBuilder.charAt(i);
		}
		
		
	}
	
	private static int getInt(final byte bytes[]){
		int i= 0;
		int j= 24;
		for( int k=0 ; 0<=j ; ++k ){
			int l = bytes[k] & 0xff;
			i += l << j;
			j -= 8;

		}
		return i;
	}

	
	public synchronized static String next(){
		
		int i = 7;
		
		seeder[i] ++;
		
		if(seeder[i] == digitsSize){
			
			do{
				
				seeder[i] = 0;

				key[i+24] = digits[seeder[i]];
				
				seeder[--i]++;
				
			}while(seeder[i] == digitsSize);
			
		}
		
		key[i+24] = digits[seeder[i]];
		
		
		return new String(key);
					
	}
}
