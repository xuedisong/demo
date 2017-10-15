//测试程序

package 搜索算法;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KMP K1=new KMP();
		BF B1=new BF();
		String S="rfdasfsdf";
		String T="da";
		int v2 = B1.BFIndex(S,0,T);
		System.out.println("BF匹配位置为：");
		System.out.println(v2);
		
		int v1 = K1.KMPIndex(S.toCharArray(),T.toCharArray());
		System.out.println("KMP匹配位置为：");
		System.out.println(v1);
	}

}
