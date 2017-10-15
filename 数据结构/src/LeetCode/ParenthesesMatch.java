package LeetCode;

import java.util.ArrayList;

/* 序号：20
 * 题目：判断括号是否匹配
 * 我的：把字符串每个字符依次放置在数组中，从左往右找，找到第一个右括号，它之前的一个字符按理说应该和它匹配。
 * 判断是否合格，若合格，去掉它俩，寻找后面出现的第一个右括号，重复操作。
 * 细节：使用可变字符串
 * 正确：使用ArrayList(推荐)或者char[]数组(基本没有方法)，因为Stack继承的Vector实现了线程安全，所以是低效的。
 */
public class ParenthesesMatch {
	public static boolean isValid(String s) {
		ArrayList<Character> stack=new ArrayList<Character>();
		int len = s.length();
		if(s.charAt(0)=='}'||s.charAt(0)==']'||s.charAt(0)==')')return false; 
		for (int i = 0; i < len; i++) {
			stack.add(s.charAt(i));
			if(s.charAt(i+1)=='}'||s.charAt(i+1)==']'||s.charAt(i+1))==')'){
				
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
