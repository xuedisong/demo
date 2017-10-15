package 顺序表;

public class ListTest {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		List<Double> list1=new List<Double>();//泛型要大写
		
		System.out.println("数组为");
        list1.add(0,30.0);
        list1.add(1,30.1);
        list1.add(2,20.1);
        list1.add(3,10.1);
        list1.add(4,15.1);
        list1.add(5,33.1);
        list1.add(6,33.1);
        list1.add(7,33.1);
        list1.add(8,33.1);
        list1.add(9,33.1);

  
		list1.printSeqList();
		
		int j=3;
		System.out.println("删除下标为"+j+"的值是:"+list1.delete(j));
		System.out.println("删除后数组为");
		list1.printSeqList();
		
		double jj=10.5;
		System.out.println("添加下标为"+j+"的值是:"+jj);
		list1.insert(j,10.5);
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
	}
}
