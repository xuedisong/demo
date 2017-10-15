package com.baidu;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.*;

public class Spyder {

	static String SendGet(String url) {
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try {
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null) {
				// 遍历抓取到的每一行并将其存储到result里面
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	static Vector<String> RegexString(String targetStr, String patternStr) {
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		// 相当于埋好了陷阱匹配的地方就会掉下去
		Pattern pattern = Pattern.compile(patternStr);
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		// 如果找到了
		Vector<String> vec = new Vector<String>();
		while (matcher.find()) {
			vec.add(matcher.group(1));
		}
		return vec;
	}

	static int number(String targetStr, String patternStr) {
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	public static void main(String[] args) {
		final int MAX_NumberOfPages=10;
		int targetCount = 0;
		int PageDropCount = 0;
		String root ="http://www.runoob.com";
		int front = 0;// 队头指针
		int rear = 0;// 队尾指针
		String[] quene = new String[MAX_NumberOfPages+1];// 队列,入队列访问结点
		Arrays.fill(quene, "");// 队列初始化
		quene[front] = root;
		front++;// 根结点入队列
		// visit start
		targetCount += number(SendGet(root), "(梦想)");
		System.out.println(root+'\n'+SendGet(root));
		
		//System.out.println(SendGet(root));
		// end
		while (rear != front) {
			// 循环入队列
			Vector<String> href = RegexString(SendGet(quene[rear]), "<a[^>]*?href=\"(http:[^>]*)\"\\s[^>]*>");
			while (href.listIterator().hasNext()) {
				String temp = href.listIterator().next();
				quene[front] = temp;
				if(front<MAX_NumberOfPages)front++;
				else{
					System.out.println("The number of 'Java' is:"+targetCount);
					return;
				}
				// visit start
				targetCount += number(SendGet(temp), "(梦想)");
				System.out.println(temp);
				System.out.println(SendGet(temp));
				// end
			}
			// 出队列
			// no visit
			rear++;
		}
		System.out.println("The number of 'Java' is:"+targetCount);
		// System.out.println("链接："+href.toString());
	}
}