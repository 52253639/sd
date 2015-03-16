/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.invite.IPageHead;
import com.kingdee.eas.fdc.invite.PageHeadCollection;
import com.kingdee.eas.fdc.invite.PageHeadFactory;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class PageChooseUI extends AbstractPageChooseUI
{
    private static final Logger logger = CoreUIObject.getLogger(PageChooseUI.class);
    private boolean isOk = false;
    /**
     * output class constructor
     */
    public PageChooseUI() throws Exception
    {
        super();
    }

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	private KDTable getMainTable(){
		return tblMain;
	}
	public void onLoad() throws Exception {
		super.onLoad();
		EntityViewInfo evInfo = new EntityViewInfo();
		IPageHead iPageHead = PageHeadFactory.getRemoteInstance();
		SelectorItemCollection selectors=evInfo.getSelector();
        selectors.add("id");
		selectors.add("name");
		selectors.add("number");
		selectors.add("description");
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("number");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		PageHeadCollection coll = iPageHead.getPageHeadCollection(evInfo);
		getMainTable().checkParsed();
		int count = coll.size();
		IRow row;
		PageHeadInfo info;
		for(int i=0; i<count; i++){
			row = getMainTable().addRow();
			info = coll.get(i);
			row.setUserObject(info);
			row.getCell("selected").setValue(Boolean.valueOf(false));
			row.getCell("id").setValue(info.getId());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
			row.getCell("description").setValue(info.getDescription());
		}
		getMainTable().getColumn("number").getStyleAttributes().setLocked(true);
		getMainTable().getColumn("name").getStyleAttributes().setLocked(true);
		getMainTable().getColumn("description").getStyleAttributes().setLocked(true);
	}

	public void initLayout() {
		this.initUIContentLayout();
	}

	protected PageHeadCollection getSelected() throws Exception{
		PageHeadCollection c=new PageHeadCollection();
		for (int i = 0, count = getMainTable().getRowCount(); i < count; i++) {
			IRow row = getMainTable().getRow(i);
			if (((Boolean) row.getCell("selected").getValue()).booleanValue()) {
				PageHeadInfo cai = (PageHeadInfo) row.getUserObject();
				c.add(cai);
			}
		}
		return c;
	}

	protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
		setConfirm(false);
    	disposeUIWindow();
	}

	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		setConfirm(true);
		getUIContext().put("pages", getSelected());
//		PageHeadCollection coll = (PageHeadCollection) getUIContext().get("pages");
	}

	private void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}

	public boolean isOk() {
    	return isOk;
    }
}