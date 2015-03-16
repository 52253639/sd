package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;


/**
 * 由服务器端返回，用于封装界面的adapter
 * @author xiaohong_shi
 *
 */
public class DynamicCostMap extends CostMap implements Serializable{
	public DynamicCostMap(){
	}
	public DyCostSplitDataGetter getDyCostSplitDataGetter(){
		return (DyCostSplitDataGetter) get("DyCostSplitDataGetter");
	}
	public void setDyCostSplitDataGetter(DyCostSplitDataGetter dynGetter){
		if(dynGetter==null){
			throw new IllegalArgumentException();
		}
		put("DyCostSplitDataGetter",dynGetter);
	}
	public AimCostSplitDataGetter getAimCostSplitDataGetter(){
		final DyCostSplitDataGetter dyCostSplitDataGetter = getDyCostSplitDataGetter();
		if(dyCostSplitDataGetter!=null){
			return getDyCostSplitDataGetter().getAimCostSplitDataGetter();
		}
		return null;
	}
	
	
	public HappenDataGetter getHappenDataGetter(){
		final DyCostSplitDataGetter dyCostSplitDataGetter = getDyCostSplitDataGetter();
		if(dyCostSplitDataGetter!=null){
			return getDyCostSplitDataGetter().getHappenDataGetter();
		}
		return null;
	}
	
	public DyProductTypeGetter getDyProductTypeGetter(){
		DyProductTypeGetter dyProductTypeGetter=(DyProductTypeGetter)get("dyProductTypeGetter");
		if(dyProductTypeGetter!=null){
			return dyProductTypeGetter;
		}
		final DyCostSplitDataGetter dyCostSplitDataGetter = getDyCostSplitDataGetter();
		if(dyCostSplitDataGetter!=null){
			return getDyCostSplitDataGetter().getDyProductTypeGetter();
		}
		return null;
	}
	public AimProductTypeGetter getAimProductTypeGetter (){
		final AimCostSplitDataGetter aimCostSplitDataGetter = getAimCostSplitDataGetter();
		if(aimCostSplitDataGetter!=null)	{
			return aimCostSplitDataGetter.getAimProductTypeGetter();
		}
		return null;
	}
	
	public void setAimSellApportionMap(Map aimSellApportionMap){
		put("aimSellApportionMap",aimSellApportionMap);
	}
	public Map getAimSellApportionMap(){
		return (Map)get("aimSellApportionMap");
	}
	
	public Map getDyApportionMap() throws BOSException{
		final DyProductTypeGetter dyProductGetter = getDyProductTypeGetter();
		if(dyProductGetter!=null){
			return dyProductGetter.getDyApportionMap();
		}
		return new HashMap();
	}
	
	public void setNoSplitAmt(BigDecimal noSplitAmt){
		put("noSplitAmt",noSplitAmt);
	}
	public BigDecimal getNoSplitAmt(){
		return (BigDecimal) get("noSplitAmt");
	}
	
	public void setAcctMap(Map acctMap){
		put("acctMap",acctMap);
	}
	public Map getAcctMap(){
		return (Map)get("acctMap");
	}
	public void setAdjustMap(Map adjustMap){
		put("adjustMap",adjustMap);
	}
	public Map getAdjustMap(){
		return (Map)get("adjustMap");
	}
}
