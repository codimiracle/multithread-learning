package com.codimiracle.example.multithread.mutex;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.ThreadUtil;

public class FilterLock implements Lockable {
	private volatile int[] level;
	private volatile int[] victim;

	public FilterLock(int n) {
		level = new int[n];
		victim = new int[n];
		for (int i = 0; i < level.length; i++) {
			level[i] = 0;
		}
	}
	private boolean isLockingInField(int field) {
		int id = ThreadID.get();
		for (int i = 0; i < level.length; i++) {
			if (i != id && level[i] >= field && victim[field] == id) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void lock() {
		int id = ThreadID.get();
		for (int i = 1; i < level.length; i++) {
			level[id] = i; //ID 为 id 的线程进入临界区。
			victim[i] = id;
			while(isLockingInField(i)) {
				ThreadUtil.wait(10);
			}
		}
	}

	@Override
	public void unlock() {
		int id = ThreadID.get();
		level[id] = 0;
	}

}
