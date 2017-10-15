package 小作业;
import java.util.*;
public class N_max {

	 int max(Stack a){//采用栈结构存储int
		int b=0;
		int c=(int)a.pop();
		if(a.isEmpty())
			return c;
		else{
			int d=max(a);//递归调用
			if(c<d)c=d;
			return c;
		}	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		N_max N1=new N_max();
		int a[]={3,8,3,1,4,5,2,6};
		int l=a.length;
		System.out.println("数组为：");
		for(int i=0;i<l;i++){
			System.out.print(a[i]+"  ");
		}
		
		Stack S1=new Stack();
		
		for(int i=0;i<l;i++){
			S1.push(a[i]);
		}
		System.out.println("\n最大值为："+N1.max(S1));
	}
}
