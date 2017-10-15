package com.leetcode;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayPartition1 {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		while(in.hasNext()){
			int[] array=new int[4];
			for(int i=0;i<4;i++){
				array[i]=in.nextInt();
			}
			System.out.println("result"+sum(array));
		}
		in.close();
	}
	public static int sum(int[] array){
		Arrays.sort(array);
		int l=array.length;
		int sum=0;
		for(int i=0;i<l;i+=2){
			sum+=array[i];
		}
		return sum;
	}
}
