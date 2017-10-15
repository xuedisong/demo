//KMP算法

package 搜索算法;

public class KMP {

    /** 
     * 获得字符串的next函数值 
     *  
     *  t 
     *            字符串 
     *  next函数值 
     */  
	static int count=0;
    public static int[] next(char[] t) {  
        int[] next = new int[t.length];  
        next[0] = -1;  
        int i = 0;  
        int j = -1; 
        
        while (i < t.length - 1) {  
            if (j == -1 || t[i] == t[j]) {  
                i++;  
                j++;  
                if (t[i] != t[j]) {  
                    next[i] = j;  
                } else {  
                    next[i] = next[j];  
                }  
            } else {  
                j = next[j];  
            }
            count++;
        }  
        return next;  
    }  
  
    /** 
     * KMP匹配字符串 
     *  
     * s 
     *            主串 
     * t 
     *            模式串 
     *  若匹配成功，返回下标，否则返回-1 
     */  
    public static int KMPIndex(char[] s, char[] t) {  
        int[] next = next(t);  
        int i = 0;  
        int j = 0;  
        
        while (i <= s.length - 1 && j <= t.length - 1) {  
            if (j == -1 || s[i] == t[j]) {  
                i++;  
                j++;  
            } else {  
                j = next[j];  
            }
            count++;
        }
        System.out.println("KMP循环次数为：");
        System.out.println(count);
        if (j < t.length) {  
            return -1;  
        } else  
            return i - t.length; // 返回模式串在主串中的头下标  
    } 
}



