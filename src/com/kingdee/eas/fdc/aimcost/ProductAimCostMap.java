package com.kingdee.eas.fdc.aimcost;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;


/**
 * 由服务器端返回，用于封装界面的adapter
 * 
 * @author xiaohong_shi
 * 
 */
public class ProductAimCostMap extends CostMap implements Serializable{
	public ProductAimCostMap() {
		
	}
	
	public void buildAdapter(DefaultMutableTreeNode node,AimProductTypeGetter productTypeGetter,AimCostSplitDataGetter aimGetter) throws BOSException{
		if(node==null||!(node.getUserObject() instanceof CostAccountInfo)) return;
		CostAccountInfo costAcct=(CostAccountInfo) node.getUserObject();
		ProductAimCostUIAdapter adapter=new ProductAimCostUIAdapter();
		adapter.setAcctNumber(costAcct.getLongNumber().replaceAll("!", "\\."));
		adapter.setAcctName(costAcct.getName());
		addAdapter(costAcct.getLongNumber(), adapter);
		if (node.isLeaf()) {
			CostEntryCollection coll = aimGetter.getCostEntrys(costAcct.getId().toString());
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					CostEntryInfo info = coll.get(i);
					adapter=new ProductAimCostUIAdapter();
					adapter.setId(info.getId());
					adapter.setTreeLevel(node.getLevel());
					adapter.setAcctName(info.getEntryName());
					adapter.setAmount(info.getCostAmount());
					setProductSplitData(adapter, info, productTypeGetter,aimGetter);
					this.addAdapter(costAcct.getLongNumber(), adapter);
				}
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				buildAdapter((DefaultMutableTreeNode)node.getChildAt(i), productTypeGetter,aimGetter);
			}
		}
		
		
	}
	
	private void setProductSplitData(ProductAimCostUIAdapter adapter,CostEntryInfo info,AimProductTypeGetter productTypeGetter,AimCostSplitDataGetter aimGetter) throws BOSException{
		AimCostProductSplitEntryCollection splits = aimGetter.createSetting(info);
		Map calculateData = aimGetter.getAimProductData(info);
		Set keySet = calculateData.keySet();
		if (splits.get(0).getApportionType() == null) {
			adapter.put("chooseBG",Color.cyan);
		}else{
			adapter.setApportionType(splits.get(0).getApportionType());
		}
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal splitAmount = (BigDecimal) calculateData.get(prodId);
			adapter.setProductAmount(prodId, splitAmount);
			if(adapter.getApportionType()!=null){
				adapter.setIndexValue(prodId,productTypeGetter.getProductApprotion(prodId, adapter.getApportionType().getId().toString()));
			}
		}
	
	}
	
	public ProductAimCostUIAdapter getProductAimCostUIAdapter(String key) {
		return (ProductAimCostUIAdapter)super.getAdapter(key);
	}
	
	
	public static class ProductAimCostUIAdapter extends CostMap.CostUIAdapter {
		public void setSplitType(String splitType){
			put("splitType",splitType);
		}
		public String getSplitType(){
			return (String)get("splitType");
		}
		public void setIndexValue(String prodId,BigDecimal indexValue){
			put(prodId+"indexValue",indexValue);
		}
		public BigDecimal getIndexValue(String prodId){
			return (BigDecimal)get("indexValue");
		}
		public void setProductAmount(String prodId,BigDecimal prodAmt){
			put(prodId+"productAmount",prodAmt);
		}
		public BigDecimal getProductAmount(String prodId){
			return (BigDecimal)get(prodId+"productAmount");
		}
		public BigDecimal getProductSellAmount(String prodId){
			if(FDCHelper.toBigDecimal(getIndexValue(prodId)).signum()==0){
				return FDCHelper.ZERO;
			}else{
				BigDecimal prodAmt=FDCHelper.toBigDecimal(getProductAmount(prodId));
				prodAmt=prodAmt.divide(getIndexValue(prodId), 2, BigDecimal.ROUND_HALF_UP);
				return prodAmt;
			}
		}
		public void setApportionType(ApportionTypeInfo apportionInfo){
			put("apportionType",apportionInfo);
		}
		public ApportionTypeInfo getApportionType(){
			return (ApportionTypeInfo)get("apportionType");
		}
		
		public boolean isCostEntry(){
			Boolean isCostEntry=(Boolean)get("isCostEntry");
			return isCostEntry!=null&&isCostEntry.booleanValue();
		}
		public void setCostEntry(boolean isCostEntry){
			put("isCostEntry",Boolean.valueOf(isCostEntry));
		}
	}
	
}
