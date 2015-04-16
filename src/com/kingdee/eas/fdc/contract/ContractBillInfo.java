package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;

public class ContractBillInfo extends AbstractContractBillInfo implements Serializable 
{
    public ContractBillInfo()
    {
        super();
    }
    protected ContractBillInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setCoopLevel(com.kingdee.eas.fdc.contract.CoopLevelEnum item)
    {
    	if(item==null){
    		setString("coopLevel", null);
    	}else{
    		super.setCoopLevel(item);
    	}
    }
    
    public void setPriceType(com.kingdee.eas.fdc.contract.PriceTypeEnum item)
    {
    	if(item==null){
    		setString("priceType", null);
    	}else{
    		super.setPriceType(item);
    	}
        
    }
    public void setContractSource(com.kingdee.eas.fdc.contract.ContractSourceEnum item)
    {
    	if(item==null){
    		setString("contractSource", null);
    	}else{
    		super.setContractSource(item);
    		
    	}
    }
    
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
    	if(item==null){
    		 setString("splitState", null);
    	}else{
    		super.setSplitState(item);
    	}
    }
    
    public void setExecState(com.kingdee.eas.fdc.contract.ContractExecStateEnum item)
    {
    	if(item==null){
    		setString("execState", null);
    	}else{
    		super.setExecState(item);
    	}
        
    }
    
    public void setCostProperty(com.kingdee.eas.fdc.contract.CostPropertyEnum item)
    {
    	if(item==null){
    		setString("costProperty", null);
    	}else{
    		super.setCostProperty(item);
    	}
        
    }
    
    public void setContractPropert(com.kingdee.eas.fdc.contract.ContractPropertyEnum item)
    {
        if(item==null){
        	setString("contractPropert", null);
        }else{
        	super.setContractPropert(item);
        }
    	
    }
    
    static public SelectorItemCollection getDefaultSelector() {
        SelectorItemCollection sic = new SelectorItemCollection();
        
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("codeType.number"));
        
        return sic;
    }
    
}