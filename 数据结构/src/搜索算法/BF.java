//BF算法

package 搜索算法;

public class BF {

	static int count=0;
	int BFIndex(String S,int start,String T)
	{
		int i=start,j=0,v;
		
		while(i<S.length()&&j<T.length())
		{
			if(S.toCharArray()[i]==T.toCharArray()[j])//S.substring(i,i+1)不对，此处存疑
			{
				i++;
				j++;
			}
			else
			{
				i=i-j+1;
				j=0;
			}
			count++;
		}
		System.out.println("BF循环次数为：");
        System.out.println(count);
		if(j==T.length())v=i-T.length();
		else v=-1;
		return v;
	}
	
}
