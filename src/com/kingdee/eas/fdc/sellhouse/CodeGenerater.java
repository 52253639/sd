package com.kingdee.eas.fdc.sellhouse;

public class CodeGenerater {

	private String codeRule;//编码规则

	private int count;//张数

	private int beginIndex;//起始流水号
	
	private int currentIndex = 0;//当前流水号
	
	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public String getCodeRule() {
		return codeRule;
	}

	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
    /**
     * 获取下一个编码
     */
    public String nextNumber() {
    	//初始化当前索引
    	if (currentIndex <= 0) {
    		currentIndex = beginIndex;
    	}
    	
    	int maskBegin = codeRule.indexOf("*");//*号起始索引
    	int maskEnd = codeRule.lastIndexOf("*");//*号终止索引
    	
    	StringBuffer seqNumber = new StringBuffer("");
    	
    	int seqLen = 0;//*号的长度
    	
    	seqLen = maskEnd - maskBegin + 1;
    	
    	String prefix =""; //前缀
    	prefix = codeRule.substring(0, maskBegin);
    	
    	String suffix =""; //后缀
    	suffix = codeRule.substring(maskEnd + 1);
    	
    	String sMaxIndex = String.valueOf(beginIndex + count -1);//最大流水号,用于计算位数
    	
    	String sCurrentIx =  String.valueOf(currentIndex);//当前索引，用于计算位数
    	
    	int zeroNumber = 0;//需要补的0的个数
    	
    	if ( seqLen >= sMaxIndex.length()) {
    		//*号的长度大于或等于最大流水号的长度,用*号的长度-当前流水号长度，得到需要补0的个数
    		zeroNumber = seqLen - sCurrentIx.length();
    	} else {
    		//*号的长度小于最大流水号的长度,用最大流水号长度-当前流水号长度，得到需要补0的个数
    		zeroNumber = sMaxIndex.length() - sCurrentIx.length();	
    	}
    	
    	//补加0,使编码一致
    	for (int i = 0; i < zeroNumber; i++) {
    		seqNumber.append("0");	
    	}
    	
    	seqNumber.append(String.valueOf(currentIndex));
    	
    	currentIndex++;
    	
    	//前缀+系列号+后缀，得到最终支票编号
		return prefix + seqNumber.toString() + suffix;
	}
    
    /**
     * 获取下一个编码，给领用，核销找编码规则使用
     */
    public String nextNumber(int countNumber,int begin) {
    	//初始化当前索引
    	if (currentIndex <= 0) {
    		currentIndex = beginIndex;
    	}
    	
    	int maskBegin = codeRule.indexOf("*");//*号起始索引
    	int maskEnd = codeRule.lastIndexOf("*");//*号终止索引
    	
    	StringBuffer seqNumber = new StringBuffer("");
    	
    	int seqLen = 0;//*号的长度
    	
    	seqLen = maskEnd - maskBegin + 1;
    	
    	String prefix =""; //前缀
    	prefix = codeRule.substring(0, maskBegin);
    	
    	String suffix =""; //后缀
    	suffix = codeRule.substring(maskEnd + 1);
    	
    	String sMaxIndex = String.valueOf(begin + countNumber -1);//最大流水号,用于计算位数
    	
    	String sCurrentIx =  String.valueOf(currentIndex);//当前索引，用于计算位数
    	
    	int zeroNumber = 0;//需要补的0的个数
    	
    	if ( seqLen >= sMaxIndex.length()) {
    		//*号的长度大于或等于最大流水号的长度,用*号的长度-当前流水号长度，得到需要补0的个数
    		zeroNumber = seqLen - sCurrentIx.length();
    	} else {
    		//*号的长度小于最大流水号的长度,用最大流水号长度-当前流水号长度，得到需要补0的个数
    		zeroNumber = sMaxIndex.length() - sCurrentIx.length();	
    	}
    	
    	//补加0,使编码一致
    	for (int i = 0; i < zeroNumber; i++) {
    		seqNumber.append("0");	
    	}
    	
    	seqNumber.append(String.valueOf(currentIndex));
    	
    	currentIndex++;
    	
    	//前缀+系列号+后缀，得到最终支票编号
		return prefix + seqNumber.toString() + suffix;
	}
    
}
