package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;


/**
 * 由服务器端返回，用于封装界面的adapter
 * @author xiaohong_shi
 *
 */
public class ProductDynamicCostMap extends CostMap implements Serializable{
	public ProductDynamicCostMap(){
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
}
