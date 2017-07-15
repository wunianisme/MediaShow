package com.wunian.service;


public class CalculatorService {
	/**
	 * ��������
	 * ���Դ����ӵ�����
	 * ʹ��ApI str.charAt(index)
	 * @param str �����ַ���
	 * @return
	 * @throws Exception
	 */
	public static double getResult3(String str)throws Exception{
		double sum=0;
		String[] sign=new String[50];//�洢�����
		String[] numArr=new String[10000];//�洢����
		numArr[0]="";//ע������ȳ�ʼ�����飬����Ĭ��ֵΪnull
		int signIndex=0;
		int numIndex=0;
		//�����ֺ�������ֿ����
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='+'||str.charAt(i)=='-'||str.charAt(i)=='*'||str.charAt(i)=='/'){
				sign[signIndex]=str.charAt(i)+"";
				signIndex++;
				numIndex++;
				numArr[numIndex]="";//ע������ȳ�ʼ�����飬����Ĭ��ֵΪnull
			}else{
				numArr[numIndex]+=str.charAt(i);
			}
		}
		int index=0;
		//����������Ĵ洢˳�������ѧ����
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
	 * ��������
	 * ���Դ����ӵ�����
	 * ʹ��ApI str.split("")
	 * @param str �����ַ���
	 * @return
	 * @throws Exception
	 */
	public static double getResult4(String str)throws Exception{
		double sum=0;
		String[] sign=new String[50];//�洢�����
		String[] splitStr=str.split("");
		String[] numArr=new String[10000];//�洢����
		for(int i=0;i<numArr.length;i++){
			numArr[i]="";//ע������ȳ�ʼ�����飬����Ĭ��ֵΪnull
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
