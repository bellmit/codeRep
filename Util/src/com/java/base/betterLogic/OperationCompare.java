package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��18������3:04:17
 * @version 1.0
 * ��Ȼλ������ٶ�ԶԶ�����������㣬�����������ж�ʱ��
 * ʹ��λ������������ȷʵ�Ƿǳ������ѡ���������ж�ʱ��
 * Java ��Բ����������൱��ֵ��Ż��������б��ʽ a��b��c ���в������㡰a&&b&&c����
 * ����߼�����ص㣬ֻҪ�����������ʽ����һ��� false��������ʽ�ͷ��� false��
 * ��ˣ������ʽ a Ϊ false ʱ���ñ��ʽ���������� false�������ȥ������ʽ b �� c��
 * ����ʱ�����ʽ a��b��c ��Ҫ��Ĵ�����ϵͳ��Դ�����ִ��?ʽ���Խ�ʡ��Щ������Դ��
 * ͬ�?��������ʽ��a||b||c��ʱ��ֻҪ a��b �� c��3 �����ʽ��������һ��������Ϊ true ʱ��
 * ������ʽ�������� true����ȥ����ʣ����ʽ���򵥵�˵���ڲ�����ʽ�ļ����У�
 * ֻҪ���ʽ��ֵ����ȷ�����ͻ��������أ������ʣ���ӱ��ʽ�ļ��㡣��ʹ��λ���� (��λ�롢��λ��)
 *  �����߼�����߼�����Ȼλ���㱾��û���������⣬����λ��������Ҫ�����е��ӱ��ʽȫ��������ɺ�
 *  �ٸ�����ս����ˣ�������Ƕȿ���ʹ��λ�������������ʹϵͳ���кܶ���Ч����
 */
public class OperationCompare {
	 @SuppressWarnings("unused")
	public static void booleanOperate(){
	 long start = System.currentTimeMillis();
	 boolean a = false;
	 boolean b = true;
	 int c = 0;
	 //����ѭ����ʼ����λ���㣬���ʽ��������м������Ӷ��ᱻ��������
	 for(int i=0;i<1000000;i++){
	 if(a&b&"Test_123".contains("123")){
	 c = 1;
	 }
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 }
	 
	 @SuppressWarnings("unused")
	public static void bitOperate(){
	 long start = System.currentTimeMillis();
	 boolean a = false;
	 boolean b = true;
	 int c = 0;
	 //����ѭ����ʼ���в������㣬ֻ������ʽ a ������������
	 for(int i=0;i<1000000;i++){
	 if(a&&b&&"Test_123".contains("123")){
	 c = 1;
	 }
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 }
	 
	 public static void main(String[] args){
	 OperationCompare.booleanOperate();
	 OperationCompare.bitOperate();
	 }
	}
