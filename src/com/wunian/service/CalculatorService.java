package com.wunian.service;


public class CalculatorService {
	/**
	 * 处理运算
	 * 可以处理复杂的运算
	 * 使用ApI str.charAt(index)
	 * @param str 运算字符串
	 * @return
	 * @throws Exception
	 */
	public static double getResult3(String str)throws Exception{
		double sum=0;
		String[] sign=new String[50];//存储运算符
		String[] numArr=new String[10000];//存储数字
		numArr[0]="";//注意必须先初始化数组，否则默认值为null
		int signIndex=0;
		int numIndex=0;
		//将数字和运算符分开存放
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='+'||str.charAt(i)=='-'||str.charAt(i)=='*'||str.charAt(i)=='/'){
				sign[signIndex]=str.charAt(i)+"";
				signIndex++;
				numIndex++;
				numArr[numIndex]="";//注意必须先初始化数组，否则默认值为null
			}else{
				numArr[numIndex]+=str.charAt(i);
			}
		}
		int index=0;
		//根据运算符的存储顺序进行数学运算
		for(int i=0;i<=numIndex;i++){
			if(i==0){
				sum=Double.parseDouble(numArr[0]);
			}else{
				if(sign[index].equals("+")){
					sum+=Double.parseDouble(numArr[i]);
				}else if(sign[index].equals("-")){
					sum-=Double.parseDouble(numArr[i]);
				}else if(sign[index].equals("*")){
					sum*=Double.parseDouble(numArr[i]);
				}else if(sign[index].equals("/")){
					sum/=Double.parseDouble(numArr[i]);
				}
				if(index<signIndex)
					index++;
			}
		}
		return sum;
	}
	/**
	 * 处理运算
	 * 可以处理复杂的运算
	 * 使用ApI str.split("")
	 * @param str 运算字符串
	 * @return
	 * @throws Exception
	 */
	public static double getResult4(String str)throws Exception{
		double sum=0;
		String[] sign=new String[50];//存储运算符
		String[] splitStr=str.split("");
		String[] numArr=new String[10000];//存储数字
		for(int i=0;i<numArr.length;i++){
			numArr[i]="";//注意必须先初始化数组，否则默认值为null
		}
		int signIndex=0;
		int numIndex=0;
		for(int i=0;i<splitStr.length;i++){
			if(splitStr[i].equals("+")||splitStr[i].equals("-")||splitStr[i].equals("*")||splitStr[i].equals("/")){
				sign[signIndex]=splitStr[i];
				signIndex++;
				numIndex++;
			}else{
				numArr[numIndex]+=splitStr[i];
			}
		}
		int index=0;
		for(int i=0;i<=numIndex;i++){
			if(i==0){
				sum=Double.parseDouble(numArr[0]);
			}else{
				if(sign[index].equals("+")){
					sum+=Double.parseDouble(numArr[i]);
				}else if(sign[index].equals("-")){
					sum-=Double.parseDouble(numArr[i]);
				}else if(sign[index].equals("*")){
					sum*=Double.parseDouble(numArr[i]);
				}else if(sign[index].equals("/")){
					sum/=Double.parseDouble(numArr[i]);
				}
				if(index<signIndex)
					index++;
			}
		}
		return sum;
	}
}
