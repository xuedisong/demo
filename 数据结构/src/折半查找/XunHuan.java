/*
 * 折半查找非递归算法 ，循环结构
 */
package 折半查找;

public class XunHuan {

    //查询成功返回该对象的下标序号，失败时返回-1。 
    int BiSearch(int r[],int n,int k) 
    { 
        int low=0; 
        int high=n-1; 
        while(low<=high) 
        { 
            int mid=(low+high)/2; 
            if(r[mid]==k) 
                return mid; 
            else 
                if(r[mid]<k) 
                    low=mid+1; 
                else 
                    high=mid-1; 
        } 
        return -1; 
    }
}
