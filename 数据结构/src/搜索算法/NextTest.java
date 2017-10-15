package 搜索算法;

public class NextTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] t1={'a','a','a','b'};
		int[] r1=KMP.next(t1);
		System.out.println("aaab的next[]为：");
		for(int i=0;i<r1.length;i++){
			System.out.println(r1[i]);
		}
		
		char[] t2={'a','b','c','a','b','a','a'};
		int[] r2=KMP.next(t1);
		System.out.println("abcabaa的next[]为：");
		for(int i=0;i<r2.length;i++){
			System.out.println(r2[i]);
		}
		char[] t3={'a','b','c','a','a','b','b','a','b','c','a','b','a','a','c','b','a'};
		int[] r3=KMP.next(t3);
		System.out.println("abcaabbabcabaacba的next[]为：");
		for(int i=0;i<r3.length;i++){
			System.out.println(r3[i]);
		}
	}

}
