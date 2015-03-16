/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.DefaultPromptBoxFactory;
import com.kingdee.eas.base.commonquery.client.IPromptBoxFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class WorkLoadFullListUI extends AbstractWorkLoadFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(WorkLoadFullListUI.class);
    private CommonQueryDialog commonQueryDialog = null;
    private CustomerQueryPanel filterUI = null;
    /**
     * output class constructor
     */
    public WorkLoadFullListUI() throws Exception
    {
        super();
    }
    
    protected String getEditUIName() {
    	return WorkLoadConfirmBillEditUI.class.getName();
    }
    
    
    protected ICoreBase getBizInterface() throws Exception {
    	return WorkLoadConfirmBillFactory.getRemoteInstance();
    }

    protected void initWorkButton() {
    	super.initWorkButton();
    	actionAddNew.setVisible(false);
    	actionAddNew.setEnabled(false);
    	actionRemove.setVisible(false);
    	actionRemove.setEnabled(false);
    	actionEdit.setVisible(false);
    	actionEdit.setEnabled(false);
    	actionCreateTo.setVisible(false);
    	actionCreateTo.setEnabled(false);
    	actionWorkFlowG.setVisible(false);
    	actionWorkFlowG.setEnabled(false);
    	actionAuditResult.setVisible(false);
    	actionAuditResult.setEnabled(false);
		actionTraceUp.setVisible(false);
		actionTraceUp.setEnabled(false);
		menuEdit.setVisible(false);
		menuEdit.setEnabled(false);
    }
    public void onLoad() throws Exception {
    	String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
    
    	super.onLoad();
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()&&
    			SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
    		MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
    		SysUtil.abort();
    	}
    	
		if(!FDCUtils.getDefaultFDCParamByKey(null, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT)){
			FDCMsgBox.showWarning(this, "未启用“工程量确认流程与付款流程分离”参数，不能使用本功能！");
			SysUtil.abort();
		}
    	actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
		actionTraceUp.setVisible(false);
		actionTraceUp.setEnabled(false);
		menuEdit.setVisible(false);
		menuEdit.setEnabled(false);
		
		tblMain.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {
				if(e.getRowIndex() <0){
					actionVoucher.setEnabled(false);
					actionDelVoucher.setEnabled(false);
					return;
				}
				if("true".equals(tblMain.getCell(e.getRowIndex(), "fiVouchered").getValue().toString())){
					actionVoucher.setEnabled(false);
					actionDelVoucher.setEnabled(true);
				}else{
					actionVoucher.setEnabled(true);
					actionDelVoucher.setEnabled(false);
				}
			}
		});
    }
    
    protected CommonQueryDialog initCommonQueryDialog() {
    	CommonQueryDialog commonDialog = super.initCommonQueryDialog();
		IPromptBoxFactory factory = new DefaultPromptBoxFactory() {
			public KDPromptBox create(String queryName,	EntityObjectInfo entity, String propertyName) {
				return super.create(queryName, entity, propertyName);
			}

			public KDPromptBox create(String queryName, QueryInfo mainQuery,String queryFieldName) {
				final KDBizPromptBox f7 = (KDBizPromptBox) super.create(queryName, mainQuery, queryFieldName);
				if (queryName.equalsIgnoreCase("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery")) {
					f7.addSelectorListener(new SelectorListener() {
						public void willShow(SelectorEvent e) {
							f7.getQueryAgent().resetRuntimeEntityView();
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							String curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
							filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",curOrgId));
							view.setFilter(filter);
							// f7.setEntityViewInfo(null);
							f7.setEntityViewInfo(view);
						}
					});

				}
				return f7;
			}
		};
		commonDialog.setPromptBoxFactory(factory);
		return commonDialog;
    }
    
    protected String[] getLocateNames() {
    	String[] colNames = {"curProject.name","contractBill.number","contractBill.name"};
    	return colNames;
    }
    public void loadFields() {
//    	super.loadFields();//??
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
    
    public void storeFields() {
    	super.storeFields();
    }

    protected void updateButtonStatus() {
		super.updateButtonStatus();
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
   }
    protected boolean initDefaultFilter() {
    	return false;
    }
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
    	uiContext.put("WorkLoadFullListUI", "WorkLoadFullListUI");
    	
    	super.prepareUIContext(uiContext, e);
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo){
    	EntityViewInfo view=(EntityViewInfo)viewInfo.clone();
    	FilterInfo filter=new FilterInfo();
    	String companyId=SysContext.getSysContext().getCurrentFIUnit().getId().toString();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id","select fid from T_FDC_CurProject where ffullorgunit='"+companyId+"'",CompareType.INNER));
    	try {
			view.getFilter().mergeFilter(filter, "and");
		} catch (BOSException e) {
			this.handUIException(e);
		}
    	return super.getQueryExecutor(queryPK, view);
    }
    protected void tblMain_tableSelectChanged(KDTSelectEvent e)
    		throws Exception {
    	super.tblMain_tableSelectChanged(e);
    	int activeRowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	if(activeRowIndex < 0){
			actionVoucher.setEnabled(false);
			actionDelVoucher.setEnabled(false);
			return;
		}
		if("true".equals(tblMain.getCell(activeRowIndex, "fiVouchered").getValue().toString())){
			actionVoucher.setEnabled(false);
			actionDelVoucher.setEnabled(true);
		}else{
			actionVoucher.setEnabled(true);
			actionDelVoucher.setEnabled(false);
		}
    }
}