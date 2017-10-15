/*
 * 苦逼的测试用例
 */
package 排序;
import java.util.*;
public class SortTest {
	
	public int[] getRandomNumber(int len) {
        if (len < 1) {
            len = 1;
        }
        int[] arr = new int[len];
        for (int index = 0; index < len; index++) {
            arr[index] = (int) (Math.random() * 1000);
        }
  
        return arr;
    }
	public static void main(String[] args) {
		
		Sort S1=new Sort();
		//测试用例1
		int[] a={43,5,47,1,19,11,59,15,48,14};
		int n=10;
		System.out.println("测试用例一\n待排序数组为：");
		for(int i=0;i<n;i++){
			System.out.print(a[i]+"\t");
		}
		//插入排序
		int[] a1={43,5,47,1,19,11,59,15,48,14};
		System.out.println("\n\n顺序表上数据元素的直接插入排序");
		S1.insertSort(a1,n);
		for(int i=0;i<n;i++){
			System.out.print(a1[i]+"\t");
		}
		System.out.println("\n\n单链表上数据元素的直接插入排序");
		int[] temp={43,5,47,1,19,11,59,15,48,14};
		LinkedList<Integer>a1_1=new LinkedList<Integer>();
		for(int i=0;i<n;i++){
			a1_1.add(temp[i]);
		}
		S1.insertSort_Linked(a1_1,n);
		for(int i=0;i<n;i++){
			System.out.print(a1_1.get(i)+"\t");
		}
		System.out.println("\n\n希尔排序");
		int[] a2={43,5,47,1,19,11,59,15,48,14};
		int[] d={4,2,1};
		S1.shellSort(a2,n,d,3);
		for(int i=0;i<n;i++){
			System.out.print(a2[i]+"\t");
		}
		//选择排序
		System.out.println("\n\n不稳定的直接选择排序");
		int[] a3={43,5,47,1,19,11,59,15,48,14};
		S1.selectSort(a3,n);
		for(int i=0;i<n;i++){
			System.out.print(a3[i]+"\t");
		}
		System.out.println("\n\n稳定的直接选择排序");
		int[] a3_1={43,5,47,1,19,11,59,15,48,14};
		S1.selectSortSteady(a3_1,n);
		for(int i=0;i<n;i++){
			System.out.print(a3_1[i]+"\t");
		}
		System.out.println("\n\n堆排序");
		int[] a4={43,5,47,1,19,11,59,15,48,14};
		S1.heapSort(a4, n);
		for(int i=0;i<n;i++){
			System.out.print(a4[i]+"\t");
		}
		//交换排序
		System.out.println("\n\n冒泡排序");
		int[] a5={43,5,47,1,19,11,59,15,48,14};
		S1.bubbleSort(a5,n);
		for(int i=0;i<n;i++){
			System.out.print(a5[i]+"\t");
		}
		System.out.println("\n\n快速排序");
		int[] a6={43,5,47,1,19,11,59,15,48,14};
		S1.quickSort(a6,0,n-1);
		for(int i=0;i<n;i++){
			System.out.print(a6[i]+"\t");
		}
		//归并排序
		System.out.println("\n\n二路归并排序");
		int[] a7={43,5,47,1,19,11,59,15,48,14};
		S1.mergeSort(a7,n);
		for(int i=0;i<n;i++){
			System.out.print(a7[i]+"\t");
		}
		//基数排序
		System.out.println("\n\n基于链式队列的基数排序");
		int[] a8={43,5,47,1,19,11,59,15,48,14};
		S1.radixSort_Linked(a8, n, 2, 10);
		for(int i=0;i<n;i++){
			System.out.print(a8[i]+"\t");
		}
		System.out.println("\n\n基于顺序队列的基数排序");
		int[] a8_1={43,5,47,1,19,11,59,15,48,14};
		S1.radixSort_Array(a8_1,n,2,10);
		for(int i=0;i<n;i++){
			System.out.print(a8_1[i]+"\t");
		}
		
		//测试用例二
		
		SortTest ST=new SortTest();
		n=100000;
		System.out.println("\n\n产生"+n+"个0:999的随机整数，排序中……");
		int[] A; 
		//插入排序
		A = ST.getRandomNumber(n);
		System.out.println("\n顺序表上数据元素的直接插入排序");
		long start1=System.nanoTime();
		S1.insertSort(A,n);
		long end1=System.nanoTime();
		System.out.println("执行耗时 : "+(end1-start1)/(1000*1000)+"s");
		
		//System.out.println("\n单链表上数据元素的直接插入排序");
		A = ST.getRandomNumber(n);
		LinkedList<Integer>A1_1=new LinkedList<Integer>();
		for(int i=0;i<n;i++){
			A1_1.add(A[i]);
		}
		long start2=System.nanoTime();
		//S1.insertSort_Linked(A1_1,n);
		long end2=System.nanoTime();
		System.out.println("执行耗时 : "+(end2-start2)/(1000*1000)+"s");
		
		System.out.println("\n希尔排序");
		A = ST.getRandomNumber(n);
		int[] D={4,2,1};
		long start3=System.nanoTime();
		S1.shellSort(A,n,D,3);
		long end3=System.nanoTime();
		System.out.println("执行耗时 : "+(end3-start3)/(1000*1000)+"s");
		//选择排序
		System.out.println("\n不稳定的直接选择排序");
		A = ST.getRandomNumber(n);
		long start4=System.nanoTime();
		S1.selectSort(A,n);
		long end4=System.nanoTime();
		System.out.println("执行耗时 : "+(end4-start4)/(1000*1000)+"s");
		
		System.out.println("\n稳定的直接选择排序");
		A = ST.getRandomNumber(n);
		long start5=System.nanoTime();
		S1.selectSortSteady(A,n);
		long end5=System.nanoTime();
		System.out.println("执行耗时 : "+(end5-start5)/(1000*1000)+"s");
		
		System.out.println("\n堆排序");
		A = ST.getRandomNumber(n);
		long start6=System.nanoTime();
		S1.heapSort(A, n);
		long end6=System.nanoTime();
		System.out.println("执行耗时 : "+(end6-start6)/(1000*1000)+"s");
		
		//交换排序
		System.out.println("\n冒泡排序");
		A = ST.getRandomNumber(n);
		long start7=System.nanoTime();
		S1.bubbleSort(A,n);
		long end7=System.nanoTime();
		System.out.println("执行耗时 : "+(end7-start7)/(1000*1000)+"s");
		
		System.out.println("\n快速排序");
		A = ST.getRandomNumber(n);
		long start8=System.nanoTime();
		S1.quickSort(A,0,n-1);
		long end8=System.nanoTime();
		System.out.println("执行耗时 : "+(end8-start8)/(1000*1000)+"s");
		
		//归并排序
		System.out.println("\n二路归并排序");
		A = ST.getRandomNumber(n);
		long start9=System.nanoTime();
		S1.mergeSort(A,n);
		long end9=System.nanoTime();
		System.out.println("执行耗时 : "+(end9-start9)/(1000*1000)+"s");
		
		//基数排序
		System.out.println("\n基于链式队列的基数排序");
		A = ST.getRandomNumber(n);
		long start10=System.nanoTime();
		S1.radixSort_Linked(A, n, 3, 10);
		long end10=System.nanoTime();
		System.out.println("执行耗时 : "+(end10-start10)/(1000*1000)+"s");
		
		System.out.println("\n基于顺序队列的基数排序");
		A = ST.getRandomNumber(n);
		long start11=System.nanoTime();
		S1.radixSort_Array(A,n,3,10);
		long end11=System.nanoTime();
		System.out.println("执行耗时 : "+(end11-start11)/(1000*1000)+"s");
	
	}

}
