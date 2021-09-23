package cn.kuwo.common.util;

// by haiping ，简单处理，工期紧急，以后有时间再加策略
public final class KwThreadPool {

	private static final int POOL_CAPACITY_MAX = 5;

	public static void runThread(final Runnable r) {
		KwThread thread = getIdleThread();
		thread.runThread(r);
	}

	private static KwThread getIdleThread() {
		if (nextBlankPos == 0) {
			return new KwThread();
		}

		synchronized (threads) {
			if (nextBlankPos == 0) {
				return new KwThread();
			} else {
				--nextBlankPos;
				KwThread thread = threads[nextBlankPos];
				threads[nextBlankPos] = null;
				return thread;
			}
		}
	}

	private static volatile int nextBlankPos = 0;
	private static KwThread[] threads = new KwThread[POOL_CAPACITY_MAX];

	private static final class KwThread extends Thread {
		public void runThread(final Runnable r) {
			this.r = r;
			if (!running) {
				this.start();
				running = true;
			} else {
				synchronized (this) {
					this.notify();
				}
			}
		}

		public void run() {
			while (true) {
				r.run();
				r = null;
				if (nextBlankPos >= POOL_CAPACITY_MAX) {
					break;
				} else {
					synchronized (this) {
						synchronized (threads) {
							if (nextBlankPos >= POOL_CAPACITY_MAX) {
								break;
							}
							threads[nextBlankPos] = this;
							++nextBlankPos;
						}
						try {
							this.wait();
						} catch (InterruptedException e) {
							break;
						}
					}
				}
			}
			running = false;
		}

		private volatile Runnable r;
		private volatile boolean running;
	}

	private KwThreadPool() {
	}
}
