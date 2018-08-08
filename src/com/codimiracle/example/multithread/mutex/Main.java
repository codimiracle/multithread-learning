package com.codimiracle.example.multithread.mutex;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.ThreadUtil;
import com.codimiracle.example.multithread.mutex.Lockable;
import com.codimiracle.example.multithread.mutex.PetersonLock;

public class Main {
	static Lockable mutex;
	static Runnable runnable = new Runnable() {

		@Override
		public void run() {
			mutex.lock();
			try {
				System.out.println("--- Thread " + ThreadID.get() + " ---");
				for (int i = 0; i < 10; i++) {
					System.out.println(i + 1);
					ThreadUtil.wait(10);
				}
				System.out.println("----------------");
			} finally {
				mutex.unlock();
			}
		}
	};

	public static void main(String[] args) throws InterruptedException {
		int count = 10;
		mutex = new FilterLock(count);
//		int count = 2;
//		mutex = new PetersonLock();
		multithread(count);
	}
	
	public static void multithread(int count) throws InterruptedException {
		Thread[] threads = new Thread[count];
		for (int i = 0; i < count; i++) {
			threads[i] = new Thread(runnable);
			threads[i].start();
		}
		for (int i = 0; i < count; i++) {
			threads[i].join();
		}
	}

}
