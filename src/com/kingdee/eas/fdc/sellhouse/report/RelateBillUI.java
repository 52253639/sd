/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Dimension;
import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierReviewGatherListUI;
import com.kingdee.eas.fdc.sellhouse.client.BaseTransactionListUI;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.SignManageListUI;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class RelateBillUI extends AbstractRelateBillUI
{
    private static final Logger logger = CoreUIObject.getLogger(RelateBillUI.class);
    
    public RelateBillUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		super.onLoad();
		FilterInfo filter=(FilterInfo) this.getUIContext().get("filter");
		FilterInfo changeFilter=(FilterInfo) this.getUIContext().get("changeFilter");
		String table=(String) this.getUIContext().get("table");
		KDScrollPane panel=new KDScrollPane();
		panel.setMinimumSize(new Dimension(1013,629));		
		panel.setPreferredSize(new Dimension(1013,629));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("filter", filter);
			
		String uiName=null;
		if(table.equals("t_she_prePurchaseManage")){
			this.kDTabbedPane1.add(panel,"预定");
			uiName=PrePurchaseManageListUI.class.getName();
		}else if(table.equals("t_she_purchaseManage")){
			this.kDTabbedPane1.add(panel,"认购");
			uiName=PurchaseManageListUI.class.getName();
		}else{
			this.kDTabbedPane1.add(panel,"签约");
			uiName=SignManageListUI.class.getName();
		}
        
		BaseTransactionListUI ui = (BaseTransactionListUI) UIFactoryHelper.initUIObject(uiName, uiContext, null,OprtState.VIEW);
		panel.setViewportView(ui);
		panel.setKeyBoardControl(true);
		panel.setEnabled(false);
		
		KDScrollPane changePanel=new KDScrollPane();
        changePanel.setMinimumSize(new Dimension(1013,629));		
        changePanel.setPreferredSize(new Dimension(1013,629));
        this.kDTabbedPane1.add(changePanel,"退房");
	        
		UIContext changeUiContext = new UIContext(this);
		changeUiContext.put("filter", changeFilter);
        
		BaseTransactionListUI changeUi = (BaseTransactionListUI) UIFactoryHelper.initUIObject(uiName, changeUiContext, null,OprtState.VIEW);
		changePanel.setViewportView(changeUi);
		changePanel.setKeyBoardControl(true);
		changePanel.setEnabled(false);
	}
}