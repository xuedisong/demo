/*
 * 二叉树方法类
 */
package 二叉树;
import java.util.*;
public class BiTree {
	
	BiTreeNode insertLeftNode(BiTreeNode curr,Object x) //在左边插入节点
	{
		BiTreeNode s,t;
		if(curr==null){
			return null;
		}
		t=curr.leftChild;
		s=new BiTreeNode(x);
		s.leftChild=t;
		s.rightChild=null;
		curr.leftChild=s;
		return curr.leftChild;
	}
	BiTreeNode insertRightNode(BiTreeNode curr,Object x) //在右边插入节点
	{
		BiTreeNode s,t;
		if(curr==null){
			return null;
		}
		t=curr.rightChild;
		s=new BiTreeNode(x);
		s.rightChild=t;
		s.leftChild=null;
		curr.rightChild=s;
		return curr.rightChild;
	}
	/*
	 * 后序遍历
	 */
	int leafNumber(BiTreeNode bt){//返回叶子结点数目
		
		if(bt==null)return 0;
		
 		if(bt.leftChild==null&&bt.rightChild==null){
			return 1;
		}
 		int l=leafNumber(bt.leftChild);
 		int r=leafNumber(bt.rightChild);
 		return l+r;
	}
	/*
	 * 后序遍历
	 */
	int treeHight(BiTreeNode bt){//返回树的高度(一个节点高度为1)
		if(bt==null)return 0;
		
		int l=treeHight(bt.leftChild);
		int r=treeHight(bt.rightChild);
		return (l>r?l:r)+1;
	}
	/*
	 * 当且仅当不满足以下条件之一，就不为完全二叉树：
	 * （1）没有左孩子，却有右孩子
	 * （2）进行层序遍历，如果之前节点缺孩子，那么后续节点也不能要孩子。
	 */
	int isWanQuanTree(BiTreeNode b)//判断是否为完全二叉树
	{
		BiTreeNode[] Qu=new BiTreeNode[100];//定义一个队列，用于层次遍历
		BiTreeNode p;
		int front=0,rear=0;//队头、队尾指针
		int cm=1;//返回的结果，1为是，0为否
		int bj=1;//判断之前节点是否缺孩子，0为缺
		if(b!=null)
		{
			rear++;
			Qu[rear]=b;
			//进队
			while(front!=rear)
			{
				front++;
				p=Qu[front];
				if(p.leftChild==null)//p节点没有左孩子
				{
					bj=0;
					if(p.rightChild!=null)//没有左孩子但有右孩子
						return cm=0;//则不是完全二叉树，违反（1）
				}
				else//p节点有左孩子
				{
					if(bj==1)//迄今为止，所有节点均有左右孩子
					{
						rear++;//左孩子进队
						Qu[rear]=b.leftChild;
						if(p.rightChild==null)//p有左孩子但没有右孩子
							bj=0;
						else
						{
							rear++;//右孩子进队
							Qu[rear]=p.rightChild;
						}
					}
					else	//bj=0:迄今为止，已有节点缺孩子
						return cm=0;//而此时p节点有左孩子，违反（2）
				}
			}
			return cm;
		}
		return 1;//把空树当成特殊的完全二叉树
	}
	/*
	 * 打印二叉树
	 */
	void printTree(BiTreeNode bt,int n){//n为缩进层数，初始为0.
		if(bt==null)return;
		printTree(bt.rightChild,n+1);
		for(int i=0;i<n;i++){
			System.out.print('\t');
		}
		System.out.println(bt.data);
		printTree(bt.leftChild,n+1);
	}
	/*
	 * 给定权重数组，构建哈夫曼树。
	 * 思路：
	 * （1）数组w[]都new出节点W[]。
	 * （2）W[]排序，找出最小的两个W对应的节点，添加一个父节点(W1+W2)，并将原来的W1,W2删除
	 * （3） 将该父节点的权值入数组W
	 * （4）重复步骤（2）（3）直到W数组剩下一个元素。
	 * （5）令该父节点为根节点，将其返回。
	 * 技巧：
	 * Vector类
	 * sort函数
	 */
	BiTreeNode searchNode(Vector<BiTreeNode> v,int a){//给定data值，查找对应节点。
			
		for(int i=0;i<v.size();i++){
			BiTreeNode temp=(BiTreeNode) v.get(i);
			if((int)temp.data==a)return temp;
		}
		return null;
	}
	BiTreeNode HuffmanTree(int[] w){
		Vector<BiTreeNode> W=new Vector<BiTreeNode>();
		BiTreeNode parent = null,W0,W1;
		for(int i=0;i<w.length;i++){
			W.addElement (new BiTreeNode(w[i]));
		}//节点向量W
		while(w.length!=1){
			Arrays.sort(w);//排序
			W0=searchNode(W,w[0]);//找见最小节点
			W.remove(W0);//移除
			W1=searchNode(W,w[1]);//找见次小节点
			W.remove(W1);//移除
			parent=new BiTreeNode(w[0]+w[1]);//新建父节点
			parent.leftChild=W0;
			parent.rightChild=W1;//链接
			W.add(parent);//父节点入向量W
			w[1]=w[0]+w[1];
			w=Arrays.copyOfRange(w, 1, w.length);//数组也相应变动
		}
		return parent;
	}
	/*
	 * 给定权重数组，返回霍夫曼编码数组（自上而下）
	 * 思路：
	 * （1）从根节点遍历
	 * （2）若访问至叶子结点，则记录叶子数值，及路径。
	 */
	
	void HuffmanCode(BiTreeNode bt,Vector<Integer> v,Map<?, ?> m){
		if(bt==null)return;
		if(bt.leftChild==null&&bt.rightChild==null){
			Set<?> s=m.keySet();
			Object[] key=s.toArray();
			for(int i=0;i<key.length;i++){
				if(m.get(key[i])==bt.data){
					System.out.println("权值："+bt.data+"字符："+key[i]+"\t编码："+v);
					m.remove(key[i]);
					break;
				}
			}
		}
		if(bt.leftChild!=null){
			v.add(0);//压栈
			HuffmanCode(bt.leftChild,v,m);
			v.remove(v.size()-1);//出栈
		}
		if(bt.rightChild!=null){
			v.add(1);//压栈
			HuffmanCode(bt.rightChild,v,m);
			v.remove(v.size()-1);//出栈
		}			
	}
}
