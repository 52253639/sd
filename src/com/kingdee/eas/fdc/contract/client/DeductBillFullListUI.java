/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.client.DeductBillEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DeductBillFullListUI extends AbstractDeductBillFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DeductBillFullListUI.class);
    
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
//    private static final String ENTRYS_CONTRACT_NAME = "entrys.contractName";
//	private static final String ENTRYS_CONTRACT_NUMBER = "entrys.contractNumber";
	private static final String CONTRACT_ID = "entrys.contractId";
	
    /**
     * output class constructor
     */
    public DeductBillFullListUI() throws Exception
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
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();
		
		super.onLoad();
		
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false); 
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false); 
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuBar.remove(menuBiz);
		this.menuEdit.setVisible(false);
		
		FDCClientHelper.initTable(tblMain);
	}
	
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new DeductBillFullFilterUI(this,this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		
		return filterUI;
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}
	
	protected boolean isShowAttachmentAction() {
		
		return false;
	}
	
	protected boolean initDefaultFilter() {
		return true;
	}

    protected boolean isIgnoreCUFilter() {
        return true;
    }
    
    protected OrgType getMainBizOrgType() {
		return OrgType.CostCenter;
	}
        
	/**
     * 设置是否显示合计行
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
    
	protected String getEditUIModal() {
		// TODO 自动生成方法存根
		return UIFactoryName.NEWTAB;
	}
	
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

  
    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	
    }
   
    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

   

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return DeductBillEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return DeductBillFactory.getRemoteInstance();
	}

	protected void execQuery() {
		super.execQuery();
		
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"entrys.deductAmt"});
		FDCClientHelper.initTable(tblMain);
	}
	
	// 返回需要融合的列
	public String[] getMergeColumnKeys() {
		String[] columnKeys = { "id", "state", "number",};
		return columnKeys;
	}

	private void setMergeColumn(boolean merge) {
		// 增加对于单据头的表格融合设置
		String mergeColumnKeys[] = getMergeColumnKeys();
		if (mergeColumnKeys != null && mergeColumnKeys.length > 0) {
			tblMain.checkParsed();
			tblMain.getGroupManager().setGroup(merge);
			for (int i = 0; i < mergeColumnKeys.length; i++) {
				tblMain.getColumn(mergeColumnKeys[i]).setGroup(merge);
				tblMain.getColumn(mergeColumnKeys[i]).setMergeable(merge);
			}
		}
	}
	
	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {

		//设置融合
		setMergeColumn(true);
		
		//超类获取数据
		super.tblMain_doRequestRowSet(e);
	}
	
//	public void onGetRowSet(IRowSet rowSet) {
//		super.onGetRowSet(rowSet);
//		//Set idSet = new HashSet();
//		Map numberMap  = new HashMap();
//		try {
//			rowSet.beforeFirst();//不然会露掉一条
//			while (rowSet.next()) {
//				String id = rowSet.getString(CONTRACT_ID);
//				if(id!=null){
//					numberMap.put(id,id);
//				}
//			}
//			
////			Map contractMap = ContractClientUtils.getContractMap(idSet);
//			//编码名称
//			Map contractMap = ContractFacadeFactory.getRemoteInstance().getContractNumberAndNameMap(numberMap);
//			
//			rowSet.beforeFirst();
//			while(rowSet.next()) {
//				String id = rowSet.getString(CONTRACT_ID);
//				if(id==null) continue; 
//				FDCBillInfo billInfo = (FDCBillInfo) contractMap.get(id);
//				if(billInfo==null) continue; 
//				rowSet.updateString(ENTRYS_CONTRACT_NUMBER, billInfo.getNumber());
//				rowSet.updateString(ENTRYS_CONTRACT_NAME, billInfo.getName());
//			}
//			rowSet.beforeFirst();
//			
//		} catch (Exception e) {
//			handUIException(e);
//		}
//		
//	}
}