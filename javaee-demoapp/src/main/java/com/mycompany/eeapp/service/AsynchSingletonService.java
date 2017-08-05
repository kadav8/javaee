package com.mycompany.eeapp.service;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class AsynchSingletonService {

	@Inject
	private Logger logger;

	@Resource
	private ManagedExecutorService managedExecutorService;

	private AtomicInteger counter = new AtomicInteger(0);

	public String startService() {
		logger.log(Level.INFO, "start service [" + this.hashCode() + "]");
		CompletableFuture.runAsync(this::calculation, managedExecutorService);
		return "Started in Singleton";
	}

	private void calculation() {
		for(int i = 0; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) { }
			logger.log(Level.INFO, "singleton calculation... [" + this.hashCode() + "] -> " + counter.getAndIncrement());
		}
	}
}
