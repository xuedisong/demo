/*
 * 排序方法类
 */
package 排序;

import java.util.*;

public class Sort {
	
	void insertSort(int[] a,int n){//直接插入排序
		int j,temp;
		for(int i=0;i<n-1;i++){
			temp=a[i+1];
			j=i;
			while(j>-1&&temp<a[j]){
				a[j+1]=a[j];
				j--;
			}
			a[j+1]=temp;
		}
	}
	void shellSort(int[] a,int n,int[] d,int numOfD){//希尔排序
		int j,span;
		int temp;
		for(int m=0;m<numOfD;m++){
			span=d[m];
			for(int k=0;k<span;k++){
				for(int i=k;i<n-span;i=i+span){
					temp=a[i+span];
					j=i;
					while(j>-1&&temp<=a[j]){
						a[j+span]=a[j];
						j=j-span;
					}
					a[j+span]=temp;
				}
			}
		}
	}
	
	void selectSort(int[] a,int n){//直接选择排序
		int temp,small;
		for(int i=0;i<n-1;i++){
			small=i;
			for(int j=i+1;j<n;j++)
				if(a[j]<a[small])small=j;
			if(small!=i){
				temp=a[i];
				a[i]=a[small];
				a[small]=temp;
			}
		}	
	}
	//堆排序
	void creatHeap(int[] a,int n,int h){
		int i,j,flag;
		int temp;
		i=h;
		j=2*i+1;
		temp=a[i];
		flag=0;
		while(j<n&&flag!=1){
			if(j<n-1&&a[j]<a[j+1])j++;
			if(temp>a[j])
				flag=1;
			else{
				a[i]=a[j];
				i=j;
				j=2*i+1;
			}
		}
		a[i]=temp;
	}
	void initCreatHeap(int[] a,int n){//堆排序
		int i;
		for(i=(n-1)/2;i>=0;i--){
			creatHeap(a,n,i);
		}
	}
	void heapSort(int[] a,int n){
		int i;
		int temp;
		initCreatHeap(a,n);
		for(i=n-1;i>0;i--){
			temp=a[0];
			a[0]=a[i];
			a[i]=temp;
			creatHeap(a,i,0);
		}
	}
	void bubbleSort(int[] a,int n){//冒泡排序
		int temp,flag=1;
		for(int i=1;i<n&&flag==1;i++){
			flag=0;
			for(int j=0;j<n-i;j++){
				if(a[j]>a[j+1]){
					flag=1;
					temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
	}
	void quickSort(int[] a,int low,int high){//快速排序
		int i=low,j=high;
		int temp=a[low];
		while(i<j){
			while(i<j&&temp<=a[j])j--;
			if(i<j){
				a[i]=a[j];
				i++;
			}
			while(i<j&&a[i]<temp)i++;
			if(i<j){
				a[j]=a[i];
				j--;
			}
		}
		a[i]=temp;
		if(low<i)quickSort(a,low,i-1);
		if(i<high)quickSort(a,j+1,high);
	}
	void merge(int[] a,int n,int[] swap,int k){//一次二路归并排序
		int m=0,u1,l2,i,j,u2;
		int l1=0;
		while(l1+k<=n-1){
			l2=l1+k;
			u1=l2-1;
			u2=(l2+k-1<=n-1)?l2+k-1:n-1;
			for(i=l1,j=l2;i<=u1&&j<=u2;m++){
				if(a[i]<=a[j]){
					swap[m]=a[i];
					i++;
				}
				else{
					swap[m]=a[j];
					j++;
				}
			}
			while(i<=u1){
				swap[m]=a[i];
				m++;
				i++;
			}
			while(j<=u2){
				swap[m]=a[j];
				m++;
				j++;
			}
			l1=u2+1;
		}
		for(i=l1;i<n;i++,m++)swap[m]=a[i];
	}
	void mergeSort(int[] a,int n){//二路归并排序
		int i,k=1;
		int[] swap=new int[n];
		while(k<n){
			merge(a,n,swap,k);
			for(i=0;i<n;i++)
				a[i]=swap[i];
			k=2*k;
		}
	}
	//链式基数排序
	void radixSort_Linked(int a[],int n,int m,int d){
		int i,j,k,power=1;
		LinkedList[] tub=new LinkedList[d]; 
		for(i=0;i<d;i++)
			tub[i]=new LinkedList<Integer>();
		for(i=0;i<m;i++){
			if(i==0)power=1;
			else power=power*d;
			for(j=0;j<n;j++){
				k=a[j]/power-(a[j]/(power*d))*d;
				tub[k].add(a[j]);
			}
			k=0;
			for(j=0;j<d;j++)
				while(tub[j].isEmpty()==false){
					a[k]=(int)tub[j].removeFirst();
					k++;
				}
		}		
	}
	//顺序基数排序
	void radixSort_Array(int a[],int n,int m,int d){
		int i,j,k,power=1;
		ArrayList[] tub=new ArrayList[d]; 
		for(i=0;i<d;i++)
			tub[i]=new ArrayList<Integer>();
		for(i=0;i<m;i++){
			if(i==0)power=1;
			else power=power*d;
			for(j=0;j<n;j++){
				k=a[j]/power-(a[j]/(power*d))*d;
				tub[k].add(a[j]);
			}
			k=0;
			for(j=0;j<d;j++)
				while(tub[j].isEmpty()==false){
					a[k]=(int)tub[j].remove(0);
					k++;
				}
		}		
	}
	//稳定直接选择排序
	void selectSortSteady(int[] a,int n){
		int temp,small;
		for(int i=0;i<n-1;i++){
			small=i;
			for(int j=i+1;j<n;j++)
				if(a[j]<a[small])small=j;
			if(small!=i){
				//temp=a[i];
				//a[i]=a[small];
				//a[small]=temp;
				temp=a[small];
				for(int k=small;k>i;k--){
					a[k]=a[k-1];
				}
				a[i]=temp;
			}
		}	
	}
	//链式直接插入排序
	void insertSort_Linked(LinkedList<Integer> a,int n){//直接插入排序
		int j,temp;
		for(int i=0;i<n-1;i++){
			//temp=a[i+1];
			temp=a.remove(i+1);
			j=i;
			while(j>-1&&temp<a.get(j)){
				//a[j+1]=a[j];
				j--;
			}
			//a[j+1]=temp;
			a.add(j+1, temp);
		}
	} 
	
}
