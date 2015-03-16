/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HeadTypeF7UI extends AbstractHeadTypeF7UI
{
    private static final Logger logger = CoreUIObject.getLogger(HeadTypeF7UI.class);
    private boolean isCancel;
    /**
     * output class constructor
     */
    public HeadTypeF7UI() throws Exception
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

    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }

    private void confirm() throws Exception {
		checkSelected();
		getData();
		setCancel(false);
	}
    
    protected void btnCan_actionPerformed(ActionEvent e) throws Exception {
    	disposeUIWindow();
		setCancel(true);
    }

    public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public HeadTypeInfo getData() throws Exception {
		String id = getSelectedKeyValue();
		HeadTypeInfo headTypeInfo = HeadTypeFactory.getRemoteInstance()

		.getHeadTypeInfo(new ObjectUuidPK(id));
		if (!headTypeInfo.isIsLeaf()) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
			SysUtil.abort();
		}
		disposeUIWindow();
		return headTypeInfo;
	}

	public boolean destroyWindow() {
		setCancel(true);
		return super.destroyWindow();
	}
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getClickCount() == 2) {
			// modify to view when doubleClick row by Jacky 2005-1-7
			if (e.getType() == 0) {
				return;
			}
			confirm();
		}
	}

	// 业务系统可重载，执行Query前对EntityViewInfo进行处理。
	protected void beforeExcutQuery(EntityViewInfo ev) {
		FilterInfo filter = getEnableFilter();
		try {
			ev.getFilter().mergeFilter(filter, "and");
		} catch (BOSException e) {
			handUIException(e);
		}

	}

	private FilterInfo getEnableFilter() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}

	protected FilterInfo getDefaultFilterForQuery() {
		FilterInfo filter = getEnableFilter();
		return filter;
	}

	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = super.getDefaultFilterForTree();
		FilterInfo enableFilter = getEnableFilter();
		if (filter == null || filter.getFilterItems().isEmpty())
			return enableFilter;
		try {
			filter.mergeFilter(enableFilter, "and");
		} catch (BOSException e) {
			handUIException(e);
		}
		return filter;
	}

}