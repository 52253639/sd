package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

import com.kingdee.bos.dao.AbstractObjectCollection;

public class FDCBudgetAcctInfo extends AbstractFDCBudgetAcctInfo implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7825789485605014993L;
	public FDCBudgetAcctInfo()
    {
        super();
    }
    protected FDCBudgetAcctInfo(String pkField)
    {
        super(pkField);
    }
    public FDCBudgetAcctEntryCollection getFDCBudgetAcctEntrys(){
    	AbstractObjectCollection c=(AbstractObjectCollection)get("entrys");
    	return (FDCBudgetAcctEntryCollection)c.cast(FDCBudgetAcctEntryCollection.class);
    }
}