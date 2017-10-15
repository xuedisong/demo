/*
 * 通过公有继承，需要添加成员数据么？不需要
 * 需要添加成员函数么？排序函数？
 * 需要改写那些函数？构造函数。
 * 那些函数可以不动？
 * 
 */
package 升序顺序表;
import 顺序表.List;
public class SeqList extends List<Double>{

	  public SeqList(){
	    super();
	  }//构造函数
	  public SeqList(int s){
	    super(s);
	  }//构造函数
	  
	  public void paixv(){
		  int k=0;
		  int h=0;
		  do{
			  for(k=0,h=0;h<size-1;h++){
				  if(((Double)listArray[h]).compareTo((Double)listArray[h+1])>0){//而且类之间不能用大于小于，大写字母开头的是类的标识。
				 /*
				  * 真TM日了狗了
				  *错误写法：当时在List.java中声明listArray时语法为： protected T [] listArray;
				  *List.java中构造函数有一句是：listArray=(T[]) new Object [sz];//这样的转化本身就很危险，即时编译没有报错。
				  *
				  *还TM想的是Object简直是多此一举，刚开始就将它转化成T泛型，现在在终于被打脸了
				  *
				  *if((listArray[h]).compareTo(listArray[h+1])>0){//这样写，当时候这边的报错就是无法将Object转化成Double类型
				  *
				  *
				  */
					  Object temp;//此处可改
					  temp=listArray[h];
					  listArray[h]=listArray[h+1];
					  listArray[h+1]=temp;
					  k++;
				  }
			  }
		  }while(k!=0);
	  }
	
	  public void addd(int i,double obj) throws Exception{
	      add(i,obj);
	      if(size>=2){
	    	  paixv();
	      }; 										//此处调用升序排序函数
	  }//在i处加入object对象
	  
	  public void insertt(int i,double obj) throws Exception{
		  insert(i, obj);
		  if(size>=2)paixv();
	  }//在i处插入object对象
	  
}
