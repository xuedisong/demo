package 升序顺序表;



public class SeqListTest {

	

	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
				SeqList list1=new SeqList();//泛型要大写
				SeqList list3=new SeqList();
				System.out.println("数组为");
		       
				list1.addd(0,30.1);
		        list1.addd(1,30.1);
		        list1.addd(2,20.1);
		        list1.addd(3,10.1);
		        list1.addd(4,15.1);
		        list1.addd(5,33.1);
		        list1.addd(6,33.1);
		        list1.addd(7,33.1);
		        list1.addd(8,33.1);
		        list1.addd(9,33.1);

		  
				list1.printSeqList();
				
				int j=3;
				System.out.println("删除下标为"+j+"的值是:"+list1.delete(j));
				System.out.println("删除后数组为");
				list1.printSeqList();
				
				double jj=10.5;
				System.out.println("添加下标为"+j+"的值是:"+jj);
				list1.insertt(j,10.5);
				System.out.println("添加后数组为");
				list1.printSeqList();
				
				int i=2;
				System.out.println("数组下标为"+i+"的值是"+list1.getData(i));
				
				System.out.println("数组长度为："+list1.size());
				System.out.println("数组是空的吗？"+list1.isEmpty());
				
				double k=15.5;
				System.out.println("删除值为"+k+"后,成功了吗？"+list1.MoreDadaDelete(list1,k));
				list1.printSeqList();
				
				double p=15.1;
				System.out.println("删除值为"+p+"后,成功了吗？"+list1.MoreDadaDelete(list1,p));
				list1.printSeqList();
				
				//ListMerge(L1,L2,L3)
				
				System.out.println("合并list1和list1至list3");
				  ListMerge(list1,list1,list3);//合并list1和list1至list3
				  list3.printSeqList();
	}

	private static void ListMerge(SeqList L1, SeqList L2, SeqList L3)throws Exception {//注意size的长度//实在找不见，就根据警告信息，让它纠错，再调试
		// TODO Auto-generated method stub
		  for(int f=0;f<L1.size();f++){
			  L3.addd(f,L1.getData(f));
		  }
		  for(int f=L1.size();f<L1.size()+L2.size();f++){
			  L3.addd(f,L2.getData(f-L1.size()));
		  }
	}

}
