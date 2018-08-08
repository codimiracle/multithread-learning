package com.codimiracle.example.multithread.mutex;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.ThreadUtil;

public class LockTwo implements Lockable {
	private volatile int victim;

	@Override
	public void lock() {
		int id = ThreadID.get();
		victim = id;
		while (victim == id) {
			ThreadUtil.wait(10);
		}
	}

	@Override
	public void unlock() {
		victim = ThreadID.get();
	}

}
