/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.CostIndexFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.client.ContractFullInfoUI;
import com.kingdee.eas.fdc.contract.client.PayRequestFullFilterUI;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class CostIndexFullListUI extends AbstractCostIndexFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexFullListUI.class);
    private CustomerQueryPanel filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;   
    public CostIndexFullListUI() throws Exception
    {
        super();
    }
    protected boolean initDefaultFilter() {
		return true;
	}
    public void onLoad() throws Exception {
    	super.onLoad();
    	FDCHelper.formatTableNumber(getMainTable(), "contract.amount");
    	FDCHelper.formatTableNumber(getMainTable(), "contract.settleAmt");
    	FDCHelper.formatTableNumber(getMainTable(), "amount");
    	FDCHelper.formatTableNumber(getMainTable(), "buildPrice");
    	FDCHelper.formatTableNumber(getMainTable(), "saleArea");
    	FDCHelper.formatTableNumber(getMainTable(), "salePrice");
    	FDCHelper.formatTableDate(getMainTable(),"contract.auditTime");
    	FDCHelper.formatTableDate(getMainTable(),"createTime");
    	FDCHelper.formatTableDate(getMainTable(),"bizDate");
    	
    	this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		this.actionWorkFlowG.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
    }
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
    protected boolean isIgnoreCUFilter() {
        return true;
    }
    protected String getEditUIName() {
		return CostIndexEditUI.class.getName();
	}
    protected void refresh(ActionEvent e) throws Exception {		
		if (e!=null && e.getSource() != null && !e.getSource().equals(this.btnRefresh) ) {			
			return;
		}
		super.refresh(e);
	}
    protected ICoreBase getBizInterface() throws Exception {
		return CostIndexFactory.getRemoteInstance();
	}
    protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(600);
		commonQueryDialog.setHeight(200);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		try {
			commonQueryDialog.init();
			commonQueryDialog.getCommonQueryParam().setDirty(false);
		} catch (UIException e) {
			e.printStackTrace();
		}
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new CostIndexFullFilterUI(this,this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		if(viewInfo.getSorter()!=null&&viewInfo.getSorter().size()<2){
			viewInfo.getSorter().clear();
			viewInfo.getSorter().add(new SorterItemInfo("orgUnit.longNumber"));
			viewInfo.getSorter().add(new SorterItemInfo("inviteType.longNumber"));
			viewInfo.getSorter().add(new SorterItemInfo("curProject.longNumber"));
			viewInfo.getSorter().add(new SorterItemInfo("contract.number"));
			viewInfo.getSorter().add(new SorterItemInfo("number"));
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
}