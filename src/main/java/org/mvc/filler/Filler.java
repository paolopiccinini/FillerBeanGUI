package org.mvc.filler;

import java.util.concurrent.CountDownLatch;

public class Filler<T> {
	
	private CountDownLatch latch;
	private InnerFiller<T, Object> filler;
	private T oggettoDaFillare;
	
	public Filler(T objectToFill) {
		oggettoDaFillare = objectToFill;
		latch = new CountDownLatch(1);
		InnerFiller.latch = latch;
	}
	
	public void fill() {
		try {
			filler = new InnerFiller<T, Object>(oggettoDaFillare);
			filler.fill();
			latch.await();
			System.out.println("main: " + oggettoDaFillare);
		}
		catch(Exception e) {	
			e.printStackTrace();
			return;
		}
	}
}
