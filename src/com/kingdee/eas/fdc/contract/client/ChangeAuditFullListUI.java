/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 变更审批单查询 列表界面
 */
public class ChangeAuditFullListUI extends AbstractChangeAuditFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeAuditFullListUI.class);
    
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Map proLongNameMap=new HashMap();

    /**
     * output class constructor
     */
    public ChangeAuditFullListUI() throws Exception
    {
        super();
    }

	protected boolean isShowAttachmentAction() {
		
		return false;
	}
	
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionAddNew_actionPerformed(e);
    	//ChangeAuditEditUI;
    	/*UIContext uiContext = new UIContext(this);
    	uiContext.put(UIContext.ID, null);
    	IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		//IUIWindow dialog = uiFactory.create(ChangeAuditEditUI.class.getName(), uiContext);
		IUIWindow curDialog = uiFactory.create(ChangeAuditEditUI.class.getName(), getUIContext(), null, OprtState.ADDNEW);
		curDialog.show();*/
		
		//ChangeAuditEditUI 的onload 方法
		//String cuId = editData.getCU()==null?SysContext.getSysContext().getCurrentCtrlUnit().getId().toString():editData.getCU().getId().toString();
    	
    	
    }

     /**
	 * output actionEdit_actionPerformed
	 */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeEdit();
        super.actionEdit_actionPerformed(e);
    }

	protected String getEditUIName() {
		return ChangeAuditEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChangeAuditBillFactory.getRemoteInstance();
	}
	

	protected void execQuery() {
		super.execQuery();
		
		//costNouse
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"costNouse"});
		FDCClientHelper.initTable(tblMain);
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

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ChangeAuditFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	public void onLoad() throws Exception {
		FDCClientUtils.checkCurrentCostCenterOrg(this);
		tblMain.checkParsed();		

		FDCClientHelper.addSqlMenu(this,this.menuEdit);
		this.proLongNameMap=FDCHelper.createTreeDataMap(CurProjectFactory.getRemoteInstance(),"name","\\");
		super.onLoad();
		
		//new update by renliang 2010-5-26
		this.actionAddNew.setEnabled(false);
		///this.actionEdit.setEnabled(false);
		this.actionEdit.setEnabled(true);
		
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		//new update by renliang 2010-5-26
		this.actionAddNew.setVisible(false);
		//this.actionEdit.setVisible(false);
		this.actionEdit.setVisible(true);
		
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		
		//new add by renliang 2010-5-26
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		
		FDCClientHelper.initTable(tblMain);
	}
	
	public void onGetRowSet(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("orgUnit.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.onGetRowSet(rowSet);
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
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
    
    /**
     * 新增加审批方法 by renlaing 2010-5-26
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    
    	//检查单据状态
		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");
    	
    	// 获取用户选择的块
		List idList = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		
		if(idList!=null && idList.size()>0){
			IChangeAuditBill changeAuditBill = (IChangeAuditBill)getBizInterface();
			changeAuditBill.audit(idList);
			//显示提示并刷新页面
			showOprtOKMsgAndRefresh();
		}else{
			MsgBox.showWarning(this,"请先选中行！");
			SysUtil.abort();
		}
	
    }
   
    /**
	 * 
	 * 描述：提示操作成功
	 * @author:renliang
	 * @date 2010-5-19
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
	/**
	 * 检查单据状态
	 * @param states
	 * @param res
	 * @throws Exception
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
	    	
			List idList = ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
	
			if(idList==null){
				MsgBox.showWarning(this, "请选中行");
				abort();
				return ;
			}
			
			Set idSet = ContractClientUtils.listToSet(idList);
			Set stateSet = FDCHelper.getSetByArray(states);
	
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add(getBillStatePropertyName());
			CoreBaseCollection coll = getBizInterface().getCollection(view);
	
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
				
				//检查单据是否在工作流中
				FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

				if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
					MsgBox.showWarning(this, ContractClientUtils.getRes(res));
					abort();
				}
	
			}
	}
	 
	 /**
	  * 
	  * 描述：单据状态属性名称，基类提供缺省实现
	  * @return
	  * @author:liupd
	  * 创建时间：2006-8-26 <p>
	  */
	 protected String getBillStatePropertyName() {
	 	return "state";
	 }
	 
	 protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	 }
	 
	 /**
	     * 修改前状态的检查
	     * @throws renliang
	     * @date 2010-5-26
	     */
    private void checkBeforeEdit() throws Exception{
    	this.checkSelected();
    	
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),getKeyFieldName());
		if (idList.size() > 1) {
			MsgBox.showWarning(this, "您选择了多行记录，请重新选择！");
			abort();
			return;
		}

		String id = idList.get(0).toString();
		ChangeAuditBillInfo info = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(BOSUuid.read(id)));

		if (!info.getState().equals(FDCBillStateEnum.SAVED) && !info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			MsgBox.showWarning(this, "您当前选择的单据的状态不适合修改操作！");
			abort();
		}
	}
}