package com.java.base.waitNotify;

import java.util.PriorityQueue;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a> 2015��8��21������3:45:42
 * @version 1.0
 */
public class ConsumerProducer {
	private int queueSize = 10;
	private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

	public static void main(String[] args) {
		ConsumerProducer test = new ConsumerProducer();
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
				synchronized (queue) {
					while (queue.size() == 0) {
						try {
							System.out.println("���пգ��ȴ�����");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.poll(); // ÿ�����߶���Ԫ��
					queue.notify();
					System.out.println("�Ӷ���ȡ��һ��Ԫ�أ�����ʣ��" + queue.size() + "��Ԫ��");
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
				synchronized (queue) {
					while (queue.size() == queueSize) {
						try {
							System.out.println("���������ȴ��п���ռ�");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.offer(1); // ÿ�β���һ��Ԫ��
					queue.notify();
					System.out.println("�����ȡ�в���һ��Ԫ�أ�����ʣ��ռ䣺"
							+ (queueSize - queue.size()));
				}
			}
		}
	}
}
