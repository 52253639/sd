package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WBSAdjustManager implements Serializable{
	//	存放WBS调整项列表
	private List wbsItems=new ArrayList();
	private Map wbsMap = new HashMap();
	
	public WBSAdjustManager(){
		wbsMap.clear();
	}
	
//	获取WBS调整项
	public WBSAdjustManagerItem get(int index){
		return index>=0?(WBSAdjustManagerItem) wbsItems.get(index):null;
	}
//	重新排序
	public void reSort(){
		
	}
	
	public int size(){
		return wbsItems.size();
	}
	
//	因为现在都改为三位的编码，所以不从数据库取数，直接按照从上到下重新生成编码
	public void reSetNumber(){
		Map parentMap = new HashMap();
		for(int i=0;i<wbsItems.size();i++){
			WBSAdjustManagerItem item = get(i);
			WBSAdjustManagerItem preItem = get(i-1);
			if(!item.isLeaf()){
				parentMap.put(item.getWbsInfo().getId().toString(), item);
			}
			int level = item.getLevel();
			int preLevel  = (preItem==null)?0:preItem.getLevel();
			WBSAdjustManagerItem parentItem = item.getWbsInfo().getParent() == null ?null:(WBSAdjustManagerItem) parentMap.get(item.getWbsInfo().getParent().getId().toString());
			if(parentItem == null){
				item.setNumber("001");
				item.setLongNumber("001");
			}else{
				String[] splitNum = preItem.getLongNumber().split("!"); 
				if(preLevel<level){
					String number = "001";
					String longNumber = preItem.getLongNumber() +"!" + number;
					item.setLongNumber(longNumber);
					item.setNumber(number);
				}else if(preLevel == level){
					String number = SCHHelper.getFixLenNum(3, Integer.parseInt(splitNum[splitNum.length-1])+1);
					String longNumber = preItem.getLongNumber().substring(0,preItem.getLongNumber().lastIndexOf("!")+1)+number;
					item.setLongNumber(longNumber);
					item.setNumber(number);
				}else{
					for(int j=i-1;j>0;j--){
						WBSAdjustManagerItem myPreItem = get(j);
						if(myPreItem.level == level){
							splitNum = myPreItem.getLongNumber().split("!");
							String number = SCHHelper.getFixLenNum(3, Integer.parseInt(splitNum[splitNum.length-1])+1);
							String longNumber = myPreItem.getLongNumber().substring(0,myPreItem.getLongNumber().lastIndexOf("!")+1)+number;
							item.setLongNumber(longNumber);
							item.setNumber(number);
							break;
						}
					}
				}
			}
			System.out.println(item.longNumber);
		}
	}
	

	/**
	 * 算法：根据上级及上一个兄弟算出厂前机节点Number及longNumber
	 * 		若上级未重算编码，则先重算上级编码；
	 * 		若上个兄弟未算编码，则先重算上个兄弟编码
	 * 		1.parent==null，sib==null，longNumber=001；
	 * 		2.parent==null，sib!=null，longNumber=sib.longNumber+1；
	 * 		3.parent!=null，sib==null，longNumber=parent.longNumber+"!001"；
	 * 		4.parent!=null，sib!=null，longNumber=parent.longNumber+"!"+sib.number+1；
	 * @param item
	 */
	public void reCalNumber(WBSAdjustManagerItem item){
		FDCWBSInfo wbsInfo = item.getWbsInfo();
		FDCWBSInfo sibInfo = null;
		FDCWBSInfo parentInfo = null;
//		若该节点已经重算过，则不再重算
		if(item.isIsRecaledNumber()) return;
		if(item.getSibInfo() != null){
			sibInfo = (FDCWBSInfo) wbsMap.get(item.getSibInfo().getId().toString());
			if(!((WBSAdjustManagerItem) sibInfo.get("adjustItem")).isIsRecaledNumber()){
				reCalNumber((WBSAdjustManagerItem) sibInfo.get("adjustItem"));
			}
		}
		if(item.getNewParent() != null){
			parentInfo = (FDCWBSInfo) wbsMap.get(item.getNewParent().getId().toString());
			if(!((WBSAdjustManagerItem) parentInfo.get("adjustItem")).isIsRecaledNumber()){
				reCalNumber((WBSAdjustManagerItem) parentInfo.get("adjustItem"));
			}
		}
		if(parentInfo == null){
			if(sibInfo == null){
				item.setNumber("001");
				item.setLongNumber("001");
			}else{
				String number = SCHHelper.getFixLenNum(3, Integer.parseInt(((WBSAdjustManagerItem)sibInfo.get("adjustItem")).getNumber())+1);
				item.setNumber(number);
				item.setLongNumber(number);
			}
		}else {
			if(sibInfo == null){
				item.setNumber("001");
				item.setLongNumber(((WBSAdjustManagerItem)parentInfo.get("adjustItem")).getLongNumber()+"!001");
			}else{
				String number = SCHHelper.getFixLenNum(3, Integer.parseInt(((WBSAdjustManagerItem)sibInfo.get("adjustItem")).getNumber())+ 1);
				item.setNumber(number);
				item.setLongNumber(((WBSAdjustManagerItem)parentInfo.get("adjustItem")).getLongNumber() + "!" +number);
			}
		}
		item.setRecaledNumber(true);
//		System.out.println(":::"+item.getWbsInfo().getLongNumber()+":::newNumber:::"+item.getLongNumber());
	}
	
	private void addItem(WBSAdjustManagerItem item){
		wbsItems.add(item);
	}
	
	public void addItem(FDCWBSInfo info,FDCWBSInfo preSibInfo){
		WBSAdjustManagerItem item = new WBSAdjustManagerItem(info,preSibInfo);
		info.put("adjustItem", item);
		wbsMap.put(info.getId().toString(), info);
		addItem(item);
	}
	
	public class WBSAdjustManagerItem implements Serializable{
		private FDCWBSInfo wbsInfo;
		private FDCWBSInfo newParent;
		private FDCWBSInfo sibInfo;
		private int level;
		private boolean isLeaf;
		private String number;
		private String longNumber;
//		判断是否重算了编码，避免死循环
		private boolean isRecaledNumber;
		public WBSAdjustManagerItem(FDCWBSInfo wbsInfo,FDCWBSInfo preSibInfo){
			this.wbsInfo = wbsInfo;
			this.newParent = wbsInfo.getParent();
			this.level = wbsInfo.getLevel();
			this.isLeaf = wbsInfo.isIsLeaf();
			this.sibInfo = preSibInfo;
			this.isRecaledNumber = false;
//			this.number = wbsInfo.getNumber();
//			this.longNumber = wbsInfo.getLongNumber();
		}
		
		public FDCWBSInfo getSibInfo() {
			return sibInfo;
		}

		public void setSibInfo(FDCWBSInfo sibInfo) {
			this.sibInfo = sibInfo;
		}
		
		public boolean isIsRecaledNumber() {
			return isRecaledNumber;
		}

		public void setRecaledNumber(boolean isRecaledNumber) {
			this.isRecaledNumber = isRecaledNumber;
		}

		public FDCWBSInfo getWbsInfo() {
			return wbsInfo;
		}

		public void setWbsInfo(FDCWBSInfo wbsInfo) {
			this.wbsInfo = wbsInfo;
		}

		public FDCWBSInfo getNewParent() {
			return newParent;
		}

		public void setNewParent(FDCWBSInfo newParent) {
			this.newParent = newParent;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public boolean isLeaf() {
			return isLeaf;
		}

		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		public String getNumber() {
			return number;
		}

		public String getLongNumber() {
			return longNumber;
		}

		public void setNumber(String number){
			this.number = number;
		}
		public void setLongNumber(String longNumber){
			this.longNumber = longNumber;
		}
	}
}
