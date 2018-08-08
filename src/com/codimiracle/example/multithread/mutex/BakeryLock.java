package com.codimiracle.example.multithread.mutex;

import com.codimiracle.example.multithread.ThreadID;
import com.codimiracle.example.multithread.ThreadUtil;

public class BakeryLock implements Lockable {
	private volatile boolean[] flag;
	private volatile int[] label;
	
	public BakeryLock(int n) {
		flag = new boolean[n];
		label = new int[n];
		for (int i = 0; i < flag.length; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}
	
	private boolean dictCompare(int labelA, int idA, int labelB, int idB) {
		return labelA < labelB || (labelA == labelB && idA < idB);
	}
	
	private boolean isLocking() {
		int id = ThreadID.get();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] && dictCompare(label[i], i, label[id], id)) {
				return true;
			}
		}
		return false;
	}
	
	private int getNextLabel() {
		int max = 0;
		for (int i = 0; i < label.length; i++) {
			max = Math.max(label[i], max);
		}
		return max + 1;
	}
	
	@Override
	public void lock() {
		int id = ThreadID.get();
		flag[id] = true;
		label[id] = getNextLabel();
		while (isLocking()) {
			ThreadUtil.wait(10);
		}
	}

	@Override
	public void unlock() {
		flag[ThreadID.get()] = false;
	}

}
