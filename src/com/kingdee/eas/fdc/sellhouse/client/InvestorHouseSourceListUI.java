/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;
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
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseSourceFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvestorHouseSourceListUI extends AbstractInvestorHouseSourceListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvestorHouseSourceListUI.class);
    public InvestorHouseSourceListUI() throws Exception
    {
        super();
    }
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	if (!OrgConstants.DEF_CU_ID.equals(saleOrg.getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }
	protected String getEditUIName() {
		return InvestorHouseSourceEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InvestorHouseSourceFactory.getRemoteInstance();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		InvestorHouseSourceListUI.checkHasRelatedByFDCCustomer("informationSource.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
    
    private static boolean hasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		return InvestorHouseFactory.getRemoteInstance().exists(filter);
	}
    
    public static void checkHasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		if(hasRelatedByFDCCustomer(key, selectedIds)){
			MsgBox.showInfo("已经被投资房源引用不可删除");
			SysUtil.abort();
		}
	}

}