package 顺序表;
/*
 * Object  与泛型<T>的结合使用，Object是一个类，或者说是个数据类型，可以有各种定义操作（比如数组），
 */
public class List<T> {//此顺序表的泛型限制在Number及以下，可修改此处条件
	
	protected final int defaultSize=100;
	  
	  protected int maxSize;//数组长度
	  protected  int size;//元素个数
	  protected Object [] listArray;//数组成员只有本质上数组是泛型<T>,才能实例T进行比较，不然Object类是没有比较的，*****真是日了狗了对应Seqlist.java中日狗
	  
	  public List(){
	    initiate(defaultSize);
	  }//构造函数
	  public List(int size){
	    initiate(size);
	  }//构造函数
	  private void initiate(int sz){
	    maxSize=sz;
	    size=0;
	    listArray=new Object [sz];//不用T,因为无法构造出T的数组。然后强制转换成T的类型。******真是日了狗了
	  }//私有成员
	  
	  public void add(int i,T obj) throws Exception{
	    if(size==maxSize){
	      throw new Exception("顺序表已满无法插入");
	    }
	    
	    listArray[i]=obj;
	    size++;
	  }//在i处加入object对象
	  
	  public void insert(int i,T obj) throws Exception{
		    if(size==maxSize){
		      throw new Exception("顺序表已满无法插入");
		    }
		    for(int j=i;j<size-1;j++){
		    	listArray[j+1]=listArray[j];
		    }
		    listArray[i]=obj;
		    size++;
		  }//在i处插入object对象
	  
	  public T delete(int i) throws Exception{
	    if(size==0){
	      throw new Exception("顺序表已空无法删除！");
	    }
	    Object it=listArray [i];
	    for(int j=i;j<size-1;j++)
	      listArray[j]=listArray[j+1];//往前移
	    
	    size--;
	    return (T)it;//转换格式
	  }//返回被删对象
	  
	  public T getData(int i) throws Exception{
	    if(i<0||i>size){
	      throw new Exception("参数错误");
	    }
	    return (T)listArray[i];
	  }//返回指定位置的对象
	  
	  public void sizevalue(int size){
		  this.size=size;
	  }
	  public int size(){
	    return size;
	  }//返回元素个数
	  
	  public boolean isEmpty(){
	    return size==0;
	  }//判断元素个数是否为空
	  
	  public int MoreDadaDelete(List<T> L,T x)throws Exception{
	    int i;
	    int tag=0;
	    
	    for(i=0;i<L.size;i++){
	      if(x.equals(L.getData(i))){//采用Object类中的equals进行比较
	        L.delete(i);
	        i--;
	        tag=1;
	      }
	    }
	    return tag;
	  }//删除多个元素，删除成功返回1，检索不到返回0
	  
	  public List<T> ListMerge(List<T> L2,List<T> L3){
		  L3.size=size+L2.size;
		  for(int i=0;i<size;i++){
			  L3.listArray[i]=listArray[i];
		  }
		  for(int i=size;i<L3.size;i++){
			  L3.listArray[i]=L2.listArray[i];
		  }
		  return L3;
	  }
	  
	  public void printSeqList(){
		  for(int i=0;i<size;i++)
		  System.out.println(listArray[i]);
	  }
}
