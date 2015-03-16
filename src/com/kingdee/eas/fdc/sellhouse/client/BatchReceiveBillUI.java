/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.ma.crbg.ReceiveBillInfo;

/**
 * output class name
 */
public class BatchReceiveBillUI extends AbstractBatchReceiveBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(BatchReceiveBillUI.class);
    
    /**
     * output class constructor
     */
    public BatchReceiveBillUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception
    {
    	// TODO 自动生成方法存根
    	super.onLoad();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected KDTable getDetailTable()
	{
		return this.kDTable1;
	}

	
	protected void attachListeners()
	{
		// TODO 自动生成方法存根
		
	}

	protected void detachListeners()
	{
		// TODO 自动生成方法存根
		
	}

	protected KDTextField getNumberCtrl()
	{
		return null;
	}
	protected ICoreBase getBizInterface() throws Exception
	{
		return ReceivingBillFactory.getRemoteInstance();
	}
	protected IObjectValue createNewData()
	{
		return new ReceivingBillInfo();
	}
	protected void fetchInitData() throws Exception
	{
		// TODO 自动生成方法存根
//		super.fetchInitData();
	}

}