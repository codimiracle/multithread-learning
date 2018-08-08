package com.codimiracle.example.multithread.mutex;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.ThreadUtil;

public class PetersonLock implements Lockable {
	private volatile boolean[] flag = new boolean[2];
	private volatile int victim;

	@Override
	public void lock() {
		int id = ThreadID.get();
		int anotherId = 1 - id;
		flag[id] = true;
		victim = id;
		while (flag[anotherId] && victim == id) {
			ThreadUtil.wait(10);
		}
	}

	@Override
	public void unlock() {
		int id = ThreadID.get();
		flag[id] = false; // 当前线程退出临界区。
		victim = id; // 让出资源。
	}

}
