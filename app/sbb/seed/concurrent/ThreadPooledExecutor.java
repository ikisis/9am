package sbb.seed.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sbb.seed.consts.SimpleConstants;

public class ThreadPooledExecutor {

	final private static ExecutorService exec = Executors.newFixedThreadPool(SimpleConstants.DEFAULT_WORKERS);
	
	public static void run(Runnable r){
		
		exec.execute(r);
		
	}
	
}
