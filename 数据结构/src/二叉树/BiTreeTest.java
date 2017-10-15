/*
 * 测试程序
 */
package 二叉树;

public class BiTreeTest {

	public static void main(String[] args) {
		//输入二叉树
		BiTree B1=new BiTree();
		BiTreeNode p,pp;
		BiTreeNode root=new BiTreeNode('L');
		p=B1.insertLeftNode(root, 'Z');
		p=B1.insertLeftNode(p, 'k');
		B1.insertRightNode(p, 'y');
		pp=B1.insertRightNode(root, 'O');
		B1.insertLeftNode(pp, 'E');
		B1.insertRightNode(pp, 'V');
		B1.printTree(root,0);
		//test20
		System.out.println("叶子数目："+B1.leafNumber(root));
		//test21
		System.out.println("是否为完全二叉树（1为是，0为否）："+B1.isWanQuanTree(root));
		//test22
		System.out.println("树的高度："+B1.treeHight(root));
	}
	
}
