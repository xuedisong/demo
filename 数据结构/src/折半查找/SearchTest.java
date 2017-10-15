/*
 * 测试段
 */

package 折半查找;

public class SearchTest {

    public static void main(String[] args) { 
        XunHuan X1=new XunHuan();
        DiGui D1=new DiGui();
//(2)
       
        int r[]={1,2,3,4,5,6,7,8,9};//只能适用顺序排列的奇数个数的序列 
        System.out.println("从{1,2,3,4,5,6,7,8,9}中查找5");
        System.out.println("循环结构查找成功，结果为："); 
        System.out.println(X1.BiSearch(r,9,5));//返回下标从0开始
        System.out.println("从{1,2,3,4,5,6,7,8,9}中查找10");
        System.out.println("循环结构查找不成功，结果为："); 
        System.out.println(X1.BiSearch(r,9,10));
//(3)
        int n=10000000;
        int k=999;
        System.out.println("\n从1："+n+"中查找"+k);
        int Data[]=new int[n];
        for(int i=0;i<n;i++){
        	Data[i]=i+1;
        }
        System.out.println("递归结构查找结果：");
        long start=System.nanoTime();
        int result=D1.BiSearch2(Data,1,n,k);
        long end=System.nanoTime();
        System.out.println(result);
        System.out.println("执行耗时 : "+(end-start)+"ns");
        
        System.out.println("\n循环结构查找结果：");
        long start1=System.nanoTime();
        int result1=X1.BiSearch(Data,n,k);
        long end1=System.nanoTime();
        System.out.println(result1);
        System.out.println("执行耗时 : "+(end1-start1)+"ns");
    }
}
