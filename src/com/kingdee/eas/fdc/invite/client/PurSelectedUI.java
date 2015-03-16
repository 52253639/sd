/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntryCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntryInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.PurchaseBillEntryCollection;
import com.kingdee.eas.fdc.invite.PurchaseBillEntryFactory;
import com.kingdee.eas.fdc.invite.PurchaseBillEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PurSelectedUI extends AbstractPurSelectedUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurSelectedUI.class);
    
    /**
     * output class constructor
     */
    public PurSelectedUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
	public void onLoad() throws Exception {
		super.onLoad();
		//获得所属项目
		Object objCur=this.getUIContext().get("optState");
		this.kdtQueryPuraseQuery.removeRows();
		this.kdtQueryPuraseQuery.getStyleAttributes().setLocked(true);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("material.*");
		view.getSelector().add("unit.*");
		view.getSelector().add("parent.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("parent.curProject.name", objCur,CompareType.EQUALS));
		PurchaseBillEntryCollection purchaseCollection=PurchaseBillEntryFactory.getRemoteInstance().getPurchaseBillEntryCollection(view);
		kdtQueryPuraseQuery.checkParsed();
		for (Iterator iter = purchaseCollection.iterator(); iter.hasNext();) 
		{
			PurchaseBillEntryInfo purchase=(PurchaseBillEntryInfo)iter.next();
			IRow row = this.kdtQueryPuraseQuery.addRow();
			row.setUserObject(purchase);
			row.getCell("id").setValue(purchase.getId().toString());
			row.getCell("curProject.name").setValue(purchase.getParent().getCurProject().getName());
			if(purchase.getMaterial() !=null)
			{
				row.getCell("material.number").setValue(purchase.getMaterial().getNumber());
				row.getCell("material.name").setValue(purchase.getMaterial().getName());
				row.getCell("material.model").setValue(purchase.getMaterial().getModel());
			}
			if(purchase.getUnit() !=null)
			{
				row.getCell("unit.name").setValue(purchase.getUnit().getName());
			}
			if(purchase.getEnterTime()!=null)
			{
				row.getCell("enterTime").setValue(purchase.getEnterTime());
			}
			if(purchase.getRemark()!=null)
			{
				row.getCell("remark").setValue(purchase.getRemark());
			}
			if(purchase.getAmount()!=null)
			{
			ICell amountCell = row.getCell("amount");
			amountCell.setValue(purchase.getAmount() + "");
			amountCell.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			amountCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
		//设置多选按钮 
		final KDCheckBox checkBox = new KDCheckBox();
		this.kdtQueryPuraseQuery.getColumn("hasSelected").setEditor(new KDTDefaultCellEditor(checkBox));
		for (int i = 0, size = this.kdtQueryPuraseQuery.getRowCount(); i < size; i++) 
		{
			this.kdtQueryPuraseQuery.getCell(i, "hasSelected").setValue(Boolean.FALSE);
		}
		this.kdtQueryPuraseQuery.getColumn("hasSelected").getStyleAttributes().setLocked(false);
		this.kdtQueryPuraseQuery.getColumn("hasSelected").getStyleAttributes().setHided(false);
		this.kdtQueryPuraseQuery.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		String formatString = "yyyy-MM-dd";
		kdtQueryPuraseQuery.getColumn("enterTime").getStyleAttributes().setNumberFormat(formatString);

	}


    /**
     * 确定
     */
    protected void btnYes_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnYes_actionPerformed(e);
      
        List idList = new ArrayList();
        
    	for (int i = 0, size = this.kdtQueryPuraseQuery.getRowCount(); i < size; i++) 
		{
			if (kdtQueryPuraseQuery.getCell(i, "hasSelected").getValue().equals(Boolean.TRUE))
			{
				idList.add(kdtQueryPuraseQuery.getCell(i, "id").getValue().toString());
			}
		}
    	if (idList.size() == 0) 
		{
			MsgBox.showError(this, "未选择任何记录！");
			SysUtil.abort();
		}
    	else 
    	{
			InviteProjectEditUI EditUI = (InviteProjectEditUI) getUIContext().get(UIContext.OWNER);
//			EditUI.setMyUiContext(idList.toString());
			this.destroyWindow();
		}
		
    	
    }
    
    

    /**
     * 取消
     */
    protected void btnNo_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnNo_actionPerformed(e);
        this.destroyWindow();
    }

    

}