/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.BanBasedataCollection;
import com.kingdee.eas.fdc.basedata.BanBasedataFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SellProjectEditEndDateUI extends AbstractSellProjectEditEndDateUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellProjectEditEndDateUI.class);
    public SellProjectEditEndDateUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	List id=(List) this.getUIContext().get("list");
    	if(id!=null){
    		SelectorItemCollection selCol=new SelectorItemCollection();
    		selCol.add("endDate");
    		for(int i=0;i<id.size();i++){
    			SellProjectInfo info=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(id.get(i).toString()),selCol);
    			this.pkEndDate.setValue(info.getEndDate());
    		}
    	}
    }
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	List id=(List) this.getUIContext().get("list");
    	if(id!=null){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("endDate");
    		for(int i=0;i<id.size();i++){
    			SellProjectInfo info=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(id.get(i).toString()));
    			info.setEndDate((Date) this.pkEndDate.getValue());
    			SellProjectFactory.getRemoteInstance().updatePartial(info, sel);
    		}
    		
    		FDCMsgBox.showInfo(this,"²Ù×÷³É¹¦£¡");
    		this.getUIWindow().close();
    	}
    }
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }

}