/*
 * 仅适用：上三角矩阵，上三角部分并不稀疏，矩阵元素为整形。
 * 由于并不稀疏，所以没必要采用链表
 * n阶上三角，用数组int[(1+n)*n/2]存储。
 */

package 上三角矩阵压缩;

import java.util.Scanner;;

public class YaSuo {
	
	int n=0;//矩阵的维数
	int shun;//数组维数
	int temp[];//用于存储矩阵上三角元素的数组
	
	public int[] yaSuo(){//矩阵输入到数组的函数
		System.out.println("请输入矩阵维数：");
		Scanner in = new Scanner(System.in);
		n=in.nextInt();
		shun=(1+n)*n/2;
		temp=new int[shun];
		for(int i=0;i<n;i++){
			System.out.println("输入矩阵第"+i+"行：");
			for(int j = 0; j< (n-i); j++){
				System.out.println("输入矩阵A["+i+"] ["+(j+i)+"]");
				temp[(n+n-i+1)*i/2+j] = in.nextInt();
			}
		}
		//in.close关闭后，JAVA不能重新建立扫描对象，不理解。
		return temp;
	}
	
	int[] jaFa(int[]A,int[]B){//加法函数,返回数组
		int[]C=new int[shun];
		for(int i=0;i<shun;i++){
			C[i]=A[i]+B[i];
		}
		return C;
	}
	
	int[] chengFa(int[]A,int[]B){//乘法函数，返回数组
		/*
		 * 两个上三角矩阵相乘，A*B=C,C矩阵下三角部分为0，
		 * 上三角部分按照矩阵乘法定义。
		 */
		int[]C=new int[shun];
		for(int i=0;i<shun;i++){
			int temp=0;//存储一个求和
			int[] bi=biao(n,i);
			int a[]=new int[n];//行标（从0开始）
			int b[]=new int[n];//列标
			for(int p=0;p<(n-bi[0]);p++){
				a[n-p-1]=A[(2*n+1-bi[0])*bi[0]/2+n-1-bi[0]-p];
			}
			for(int p=0,k=bi[1],k1=n-1;p<bi[1]+1;p++,k1--){
				b[p]=B[k];
				k=k+k1;
			}
		/*	for(int p=0;p<n;p++){
				System.out.print(a[p]+"\t");
			}
			for(int p=0;p<n;p++){
				System.out.print(b[p]+"\t");
			}
			System.out.println("");
		*/	
			for(int p=0;p<n;p++){//a[],b[]对应元素相乘
				temp+=a[p]*b[p];
			}
			C[i]=temp;
		}
		return C;
	}
	
	int [] biao(int n,int m){//指标函数
		/*
		 * 已知元素在数组中的位置，返回元素在矩阵的位置i,j
		 * 用一个数组存储i，j
		 */
		int nn=(1+n)*n/2-m;
		int count1=0,count=0;
		for(int i=0,j=1;nn>i;i=i+j,j++){
			count++;
			count1=nn-i;
		}
		int[] a={n-count,n-count1};
		return a;
	}
}
