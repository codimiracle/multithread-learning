package com.codimiracle.example.multithread.mutex;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.ThreadUtil;

public class LockOne implements Lockable {
	private volatile boolean[] flag = new boolean[2];

	@Override
	public void lock() {
		int id = ThreadID.get();
		int anotherId = 1 - id;
		flag[id] = true;
		while (flag[anotherId]) {
			ThreadUtil.wait(10);
		}
	}

	@Override
	public void unlock() {
		int id = ThreadID.get();
		flag[id] = false;
	}

}
