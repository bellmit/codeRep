package com.java.base.waitNotify;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a> 2015��8��21������3:49:02
 * @version 1.0
 */
public class ConditionConsumerProducer {
	private int queueSize = 10;
	private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();

	public static void main(String[] args) {
		ConditionConsumerProducer test = new ConditionConsumerProducer();
		Producer producer = test.new Producer();
		Consumer consumer = test.new Consumer();

		producer.start();
		consumer.start();
	}

	class Consumer extends Thread {

		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while (true) {
				lock.lock();
				try {
					while (queue.size() == 0) {
						try {
							System.out.println("���пգ��ȴ�����");
							notEmpty.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					queue.poll(); // ÿ�����߶���Ԫ��
					notFull.signal();
					System.out.println("�Ӷ���ȡ��һ��Ԫ�أ�����ʣ��" + queue.size() + "��Ԫ��");
				} finally {
					lock.unlock();
				}
			}
		}
	}

	class Producer extends Thread {

		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while (true) {
				lock.lock();
				try {
					while (queue.size() == queueSize) {
						try {
							System.out.println("���������ȴ��п���ռ�");
							notFull.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					queue.offer(1); // ÿ�β���һ��Ԫ��
					notEmpty.signal();
					System.out.println("�����ȡ�в���һ��Ԫ�أ�����ʣ��ռ䣺"
							+ (queueSize - queue.size()));
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
