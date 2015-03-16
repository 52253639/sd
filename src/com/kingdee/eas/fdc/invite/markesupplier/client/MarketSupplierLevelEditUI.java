/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class MarketSupplierLevelEditUI extends AbstractMarketSupplierLevelEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierLevelEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierLevelEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
        prmtLevel.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketLevelSetUpQuery");
    }
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(!this.cbIsOver.isSelected()&&this.prmtLevel.getValue()==null){
    		FDCMsgBox.showWarning(this,"供应商级别不能为空！");
    		return;
    	}
    	ArrayList id=(ArrayList)this.getUIContext().get("id");
    	if(id!=null){
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("level");
    		for(int i=0;i<id.size();i++){
        		MarketSupplierStockInfo info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(id.get(i).toString()));
        		if(this.cbIsOver.isSelected()){
        			info.setLevel(null);
        		}else{
        			info.setLevel((MarketLevelSetUpInfo) this.prmtLevel.getValue());
        		}
        		MarketSupplierStockFactory.getRemoteInstance().updatePartial(info, sel);
    		}
    		FDCMsgBox.showInfo(this,"操作成功！");
    		this.getUIWindow().close();
    	}
    }
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }
    protected void cbIsOver_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	if(this.cbIsOver.isSelected()){
			this.prmtLevel.setEnabled(false);
			this.prmtLevel.setValue(null);
		}else{
			this.prmtLevel.setEnabled(true);
		}
    }

}