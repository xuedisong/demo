/* 9-12
 *设待排序数据元素的关键字为整数类型，编写函数实现在O(n)的时间复杂度内和O(1)的
 *空间复杂度内重排数组a,使得将所有取负值的关键字排在所有取非负值的关键字之前。
 */
package 排序;

public class SignSort {
	//一次快速排序的思想
	static void Sort(int[] a,int n){
		int temp,i=0,j=n-1;
		while(i<=j){
			if(a[i]>=0&&a[j]<0){//交换位置
				temp=a[i];
				a[i]=a[j];
				a[j]=temp;
			}
			if(a[i]<0)i++;
			if(a[j]>=0)j--;
		}
	}
	/*数据元素比较n次，for循环内最坏数据元素赋值3次，最好0次
	 * 时间复杂度：n+3n=4n=O(n)
	 */
	public static void main(String[] args) {
		int n=8;
		int[] a={2,-6,0,45,-3,0,89,-9};
		System.out.println("原数组为：");
		for(int i=0;i<n;i++){
			System.out.print(a[i]+"\t");
		}
		System.out.println("\n\n排序后结果为：");
		Sort(a,n);
		for(int i=0;i<n;i++){
			System.out.print(a[i]+"\t");
		}
	}

}
