package com.codimiracle.example.multithread.sync;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.mutex.Lockable;
import com.codimiracle.example.multithread.mutex.PetersonLock;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Lockable mutex = new PetersonLock();
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				mutex.lock();
				try {
					System.out.println("--- Thread " + ThreadID.get() + " ---");
					for (int i = 0; i < 10; i++) {
						System.out.println(i + 1);
					}
					System.out.println("----------------");
				} finally {
					mutex.unlock();
				}
			}
		};
		Thread threadA = new Thread(runnable);
		Thread threadB = new Thread(runnable);
		threadA.start();
		threadB.start();
		threadA.join();
		threadB.join();
	}

}
