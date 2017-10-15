package 小作业;

import java.util.*;

public class Main
{
  public static void main(String args[])
  {
      Scanner cin = new Scanner(System.in);
      String a;
      int f=0,l=0,count=0;
      while(cin.hasNext())
      {
          a = cin.next();
          if(a.substring(0,1).toCharArray()[0]<=90){
        	  count++;
        	  f=1;
          };
          for(int i=1;i<a.length();i++){
        	  char temp=a.substring(i,i+1).toCharArray()[0];
        	  if(temp<=90)l=1;
        	  else l=0;
        	  if(l!=f)count++;
        	  f=l;
          }
          /* z-122
           * a-97
           * Z-90
           * A-65
           */
          System.out.println(a.length()+count);
      }
  }
}
