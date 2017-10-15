
package 小作业;

public class Hu_Tu {
	//递归结构
	void huaTu(int n){
		
		if(n==0)return;
		huaTu(n-1);
		System.out.println();
		for(int i=0;i<n;i++){
			System.out.print(n+"  ");
		}
		n--;
	}
	
	//循环结构
	void xunHuan(int n){
		for(int i=0;i<n;i++){
			System.out.println();
			for(int j=0;j<i+1;j++){
				System.out.print(i+1+"  ");
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Hu_Tu H1=new Hu_Tu();
		int n=5;
		//递归测试
		System.out.println("递归测试");
		H1.huaTu(n);
		System.out.println("\n循环测试");
		//循环测试
		H1.xunHuan(n);
	}

}
