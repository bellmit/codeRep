package com.java.base.betterLogic;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��8��18������3:00:07
 * @version 1.0
 * JDK �ܶ�����ǲ������鷽ʽʵ�ֵ���ݴ洢������ ArrayList��Vector �ȣ�
 * ������ŵ������������ܷǳ��á�һά����Ͷ�ά����ķ����ٶȲ�һ��
 * һά����ķ����ٶ�Ҫ���ڶ�ά���顣���������е�ϵͳ��Ҫʹ�ö�ά���飬
 * ��������ά����ת��Ϊһά�����ٽ��д��?�����ϵͳ����Ӧ�ٶȡ�
 */
public class arrayTest {
	 @SuppressWarnings("unused")
	public static void main(String[] args){
	 long start = System.currentTimeMillis();
	 int[] arraySingle = new int[1000000];
	 int chk = 0;
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arraySingle.length;j++){
	 arraySingle[j] = j;
	 }
	 }
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arraySingle.length;j++){
	 chk = arraySingle[j];
	 }
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 
	 start = System.currentTimeMillis();
	 int[][] arrayDouble = new int[1000][1000];
	 chk = 0;
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arrayDouble.length;j++){
	 for(int k=0;k<arrayDouble[0].length;k++){
	 arrayDouble[i][j]=j;
	 }
	 }
	 }
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arrayDouble.length;j++){
	 for(int k=0;k<arrayDouble[0].length;k++){
	 chk = arrayDouble[i][j];
	 }
	 }
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 
	 start = System.currentTimeMillis();
	 arraySingle = new int[1000000];
	 int arraySingleSize = arraySingle.length;
	 chk = 0;
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arraySingleSize;j++){
	 arraySingle[j] = j;
	 }
	 }
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arraySingleSize;j++){
	 chk = arraySingle[j];
	 }
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 
	 start = System.currentTimeMillis();
	 arrayDouble = new int[1000][1000];
	 int arrayDoubleSize = arrayDouble.length;
	 int firstSize = arrayDouble[0].length;
	 chk = 0;
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arrayDoubleSize;j++){
	 for(int k=0;k<firstSize;k++){
	 arrayDouble[i][j]=j;
	 }
	 }
	 }
	 for(int i=0;i<100;i++){
	 for(int j=0;j<arrayDoubleSize;j++){
	 for(int k=0;k<firstSize;k++){
	 chk = arrayDouble[i][j];
	 }
	 }
	 }
	 System.out.println(System.currentTimeMillis() - start);
	 }
	}
