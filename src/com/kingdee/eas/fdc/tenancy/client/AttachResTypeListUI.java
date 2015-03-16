/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.client.RoomFormListUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AttachResTypeFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AttachResTypeListUI extends AbstractAttachResTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AttachResTypeListUI.class);

    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public AttachResTypeListUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }

    protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		AttachResTypeListUI.checkHasRelatedByAttachResource("attachResType.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
    
    private static boolean hasRelatedByAttachResource(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		return AttachResourceFactory.getRemoteInstance().exists(filter);
	}
    
    public static void checkHasRelatedByAttachResource(String key, List selectedIds) throws EASBizException, BOSException{
		if(hasRelatedByAttachResource(key, selectedIds)){
			MsgBox.showInfo("已经被配套资源引用不可删除");
			SysUtil.abort();
		}
	}
    
	protected ICoreBase getBizInterface() throws Exception {
		return AttachResTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return AttachResTypeEditUI.class.getName();
	}
}