/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class CommerceChanceSelectUI extends AbstractCommerceChanceSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommerceChanceSelectUI.class);
    
    public CommerceChanceSelectUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		this.toolBar.setVisible(false);
		this.menuFile.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		BaseTransactionInfo info=(BaseTransactionInfo)this.getUIContext().get("info");
		if(info!=null){
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
	    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
			info.setNewCommerceChance(CommerceChanceFactory.getRemoteInstance().getCommerceChanceInfo(new ObjectUuidPK(id)));
			
			this.destroyWindow();
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try	{
			FilterInfo filter = (FilterInfo)this.getUIContext().get("filter");
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
}