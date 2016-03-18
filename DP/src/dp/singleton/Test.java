package dp.singleton;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��1������3:24:38
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
		//����ģʽ
		Wife1 w1=Wife1.getInstance();
		Wife1 w2=Wife1.getInstance();
		if(w1==w2){
			System.out.println("s1��s2��ͬһ������");
		}else{
			System.out.println("s1��s2����ͬһ������");
		}
		
		//����ģʽ
		Wife2 w3=Wife2.getInstance();
		Wife2 w4=Wife2.getInstance();
		if(w3==w4){
			System.out.println("s3��s4��ͬһ������");
		}else{
			System.out.println("S3��s4����ͬһ������");
		}
		
	}
}
