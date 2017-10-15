/*
 * 折半查找递归算法，递归结构
 */
package 折半查找;

public class DiGui {
     
    //查询成功返回该对象的下标序号，失败时返回-1。
	
    int BiSearch2(int r[],int low,int high,int k) 
    { 
        if(low>high) 
            return -1; 
        else 
        { 
            int mid=(low+high)/2; 
            if(r[mid]==k) 
                return mid; 
            else 
                if(r[mid]<k) 
                    return BiSearch2(r,mid+1,high,k); 
                else 
                    return BiSearch2(r,low,mid-1,k); 
 
        } 
    }
}
