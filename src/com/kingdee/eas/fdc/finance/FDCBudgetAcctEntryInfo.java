package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

import com.kingdee.bos.dao.AbstractObjectCollection;

public class FDCBudgetAcctEntryInfo extends AbstractFDCBudgetAcctEntryInfo implements Serializable 
{
    public FDCBudgetAcctEntryInfo()
    {
        super();
    }
    protected FDCBudgetAcctEntryInfo(String pkField)
    {
        super(pkField);
    }
    
    public boolean isEmptyRow(){
    	return getBoolean("emptyRow");
    }
    public void setEmptyRow(boolean isEmptyRow){
    	setBoolean("emptyRow", isEmptyRow);
    }
    public AbstractObjectCollection getFromItems()
    {
        return (AbstractObjectCollection)get("items");
    }
}