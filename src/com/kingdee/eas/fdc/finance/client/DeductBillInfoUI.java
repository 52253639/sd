/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class DeductBillInfoUI extends AbstractDeductBillInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(DeductBillInfoUI.class);
    
    /**
     * output class constructor
     */
    public DeductBillInfoUI() throws Exception
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
		this.tblMain.checkParsed();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getStyleAttributes().setLocked(true);
		
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add("Parent.number");
		view.getSelector().add("Parent.state");
		view.getSelector().add("deductAmt");
		view.getSelector().add("remark");
		view.getSelector().add("deductDate");
		view.getSelector().add("deductType.name");
		view.getSelector().add("deductUnit.name");
		
		view.getSelector().add("Parent.auditor.name");
		view.getSelector().add("Parent.createTime");
		view.getSelector().add("Parent.auditTime");
		view.getSelector().add("Parent.creator.name");
		
		//设置对齐方式
		this.tblMain.getColumn("entrys.deductAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//设置格式
		this.tblMain.getColumn("entrys.deductAmt").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//设置时间格式
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("auditTime").getStyleAttributes().setNumberFormat(formatString);
		this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(formatString);
		this.tblMain.getColumn("entrys.remark").getStyleAttributes().setHided(true);
		
		
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		
		DeductBillEntryCollection col = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view);
		for (int i = 0; i < col.size(); i++) {
			DeductBillEntryInfo info = col.get(i);
			IRow row = this.tblMain.addRow();
			row.setUserObject(info.getParent().getId().toString());
			//entrys.deductUnit.name
			
			
			row.getCell("number").setValue(info.getParent().getNumber());

			row.getCell("state").setValue(info.getParent().getState());
			row.getCell("entrys.deductUnit.name").setValue(info.getDeductUnit().getName());
			row.getCell("entrys.deductType.name").setValue(info.getDeductType().getName());
			
			row.getCell("entrys.deductAmt").setValue(info.getDeductAmt());			
			row.getCell("entrys.remark").setValue(info.getRemark());
			if(info.getParent().getAuditor()!=null){
				row.getCell("auditor.name").setValue(info.getParent().getAuditor().getName());
				row.getCell("auditTime").setValue(info.getParent().getAuditTime());
			}
			row.getCell("creator.name").setValue(info.getParent().getCreator().getName());
			row.getCell("createTime").setValue(info.getParent().getCreateTime());
			//entrys.deductDate
			row.getCell("entrys.deductDate").setValue(info.getDeductDate());
		}
		
	}
    
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) return ;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, row.getUserObject());
			uiContext.put("disableSplit", Boolean.TRUE);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(DeductBillEditUI.class.getName(),
							uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
    
}