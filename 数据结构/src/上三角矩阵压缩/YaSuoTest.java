/*
 * 遇到的问题：控制台输入，当程序出问题后，之后的输入会出bug，
 * 这时先运行个正确的程序，再试原程序
 */
package 上三角矩阵压缩;

public class YaSuoTest {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		YaSuo Y1=new YaSuo();
		YaSuo Y2=new YaSuo();
		int[] a=Y1.yaSuo();
		int[] b=Y2.yaSuo();
	/*	测试输入矩阵
	    int l=a.length;
		for(int i=0;i<l;i++){
			System.out.println(a[i]);
		}
		测试指标函数
		int[] a1=Y1.biao(2,2);
		for(int i=0;i<a1.length;i++){
			System.out.println(a1[i]);
		}
*/	
		//测试乘法
		int C[]=Y1.chengFa(a,b);
		System.out.println("相乘后的上三角阵压缩存储为数组：");
		for(int i=0;i<C.length;i++){
			System.out.println(C[i]);
		}
		//测试加法
		int D[]=Y1.jaFa(a,b);	
		System.out.println("相加后的上三角阵压缩存储为数组：");
		for(int i=0;i<D.length;i++){
			System.out.println(D[i]);
		}
	
	}
	
}
