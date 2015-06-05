/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * 描述:无文本合同列表界面
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextListUI extends
AbstractContractWithoutTextListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractWithoutTextListUI.class);

	/**
	 * output class constructor
	 */
	public ContractWithoutTextListUI() throws Exception {
		super();
	}

	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractWithoutTextFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractWithoutTextInfo objectValue = new com.kingdee.eas.fdc.contract.ContractWithoutTextInfo();
		return objectValue;
	}
	/**
	 * 
	 * 描述：冻结合同表列
	 * 
	 * @author:liupd 创建时间：2006-7-25
	 *               <p>
	 */
	protected void freezeTableColumn() {
		//FDCHelper.formatTableNumber(getMainTable(), "amount");
	
		// 合同名称
		int name_col_index = getMainTable().getColumn("billName")
				.getColumnIndex();
		getMainTable().getViewManager().setFreezeView(-1, name_col_index+1);
	}
	
	
	
	 /**
	 * 
	 * 描述：返回远程接口（子类必须实现）
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractWithoutTextFactory.getRemoteInstance();
	}
	
	/**
	 * 
	 * 描述：审核通过（子类必须实现，调用服务器端打审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void audit(List ids) throws Exception {
		ContractWithoutTextFactory.getRemoteInstance().audit(ids);
	}

	/**
	 * 
	 * 描述：反审核（子类必须实现，调用服务器端打反审核标志的方法即可）
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * 创建时间：2006-8-1 <p>
	 */
	protected void unAudit(List ids) throws Exception {
		ContractWithoutTextFactory.getRemoteInstance().unAudit(ids);
	}
	
	/**
     * 
     * 描述：检查是否有关联对象
     * @author:liupd
     * 创建时间：2006-8-26 <p>
     */
    protected void checkRef(String id) {
    	ContractWithoutTextClientUtils.checkRef(this, id);
    }
    
    protected void updateButtonStatus() {
    
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
    	FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
    	super.actionAddNew_actionPerformed(e);
    }
    
//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);
	
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }
    
    /**
     * 描述：按钮"查看付款单"的Action方法。
     * @author zhiyuan_tang 2010/08/04
     */
    public void actionViewPayment_actionPerformed(ActionEvent e)
    		throws Exception {
    	
    	//检查是否选中行
    	super.checkSelected();
    	
    	//判断是否有关联的付款单
    	boolean hasPaymentBill = false;
    	String paymentID = null;
    	//取得选中的合同ID
    	String contractBillID = getSelectedKeyValue();
    	
    	//判断有没有该合同对应的付款单
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("ContractBillId", contractBillID));
		view.setFilter(filter);
		view.getSelector().add("id");
		
		PaymentBillCollection col = com.kingdee.eas.fi.cas.PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
		if (col != null && col.size() > 0) {
			hasPaymentBill = true;
			
			//获取付款单ID
			for (int i = 0; i < col.size(); i++) {
				paymentID = col.get(i).getId().toString();
			}
		} else {
			hasPaymentBill = false;
		}
    	
    	
    	if (hasPaymentBill) {
    		//当无文本合同下有对应生成的付款单时则以查看状态打开付款单
    		showPayment(paymentID);
    		
    	} else {
    		//当无文本合同下没有对应生成的付款单时则提示用户：该无文本合同没有对应的付款单
    		FDCMsgBox.showWarning(EASResource.getString(
    						"com.kingdee.eas.fdc.contract.client.ContractResource",
    						"contractWithoutTextHasNOPayment"));
    		abort();
    	}
    }
    
    /**
     * 描述：打开指定付款单的编辑界面
     * @param billID:付款单ID
     * @throws Exception
     * @author zhiyuan_tang 2010/08/03
     */
    private void showPayment(String billID) throws Exception {
		Map uiContext = new HashMap();
		uiContext.put(UIContext.OWNER, this);
		uiContext.put(UIContext.ID, billID);
		FilterInfo myFilter = new FilterInfo();
		myFilter.getFilterItems().add(new FilterItemInfo("id", billID));
		uiContext.put("MyFilter", myFilter); // 注意，这里的billID是44位长的BOSUuid，类型是String
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.finance.client.PaymentBillEditUI",
				uiContext, null, OprtState.VIEW);
		window.show();
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		//Add by zhiyuan_tang 2010/08/03 
		//添加查看付款单按钮,ToolBar和MenuBar上都要添加,图标为下查图标
		btnViewPayment.setVisible(true);
		btnViewPayment.setEnabled(true);
		btnViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		menuItemViewPayment.setVisible(true);
		menuItemViewPayment.setEnabled(true);
		menuItemViewPayment.setIcon(EASResource.getIcon("imgTbtn_downview"));
		
		this.btnProgram.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		this.btnUnProgram.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		
		tblMain.getColumn("useDeptment").getStyleAttributes().setHided(true);
		
		FDCClientUtils.fmtFootNumber(tblMain, new String[]{"originalAmount","amount"});
		
		if(this.getUIContext().get("filter")!=null){
			this.toolBar.setVisible(false);
			this.pnlLeftTree.setVisible(false);
			kDSplitPane1.add(this.pnlLeftTree, "left");
			mainQuery=new EntityViewInfo();
			mainQuery.setFilter((FilterInfo) this.getUIContext().get("filter"));
		}
		this.actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
	}
	protected void treeSelectChange() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.treeSelectChange();
	}
	public void buildProjectTree() throws Exception {
		if(this.getUIContext().get("filter")!=null){
			return;
		}
		super.buildProjectTree();
	}
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return true;
    }
	
	public boolean isPrepareActionSave() {
    	return true;
    }
		
	/** 添加快速定位功能，定位字段有：单据编号，单据名称、合同类型、收款单位、签约日期；
	 * @author owen_wen 2010-09-06
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "billName","contractType", "receiveUnit.name", "signDate", };
	}
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showInfo("请选择一条记录进行操作！");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
			FDCMsgBox.showInfo("已在工作流处理中，请稍后再试!");
			SysUtil.abort();
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		sic.add("curProject.isWholeAgeStage");
		ContractWithoutTextInfo contractBillInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(selectedKeyValue), sic);
		boolean isRealToProgramming =FDCUtils.getDefaultFDCParamByKey(null,contractBillInfo.getOrgUnit().getId().toString(), "FDC900_CON_OLDCONTRACTRTOPROGRAMMING");
		if(isRealToProgramming){
			if(!contractBillInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
				FDCMsgBox.showInfo("合同未审批，不能进行合约规划关联！");
				SysUtil.abort();
			}
		}else{
			FDCMsgBox.showInfo("审批后合同关联合约规划参数未启用，不能进行合约规划关联！");
			SysUtil.abort();
		}
		ProgrammingContractInfo pc = (ProgrammingContractInfo) contractBillInfo.getProgrammingContract();
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("contractWithoutTextInfo", contractBillInfo);
		uiContext.put("isWholeAgeStage", new Boolean(contractBillInfo.getCurProject().isIsWholeAgeStage()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			Object object = uiWindow.getUIObject().getUIContext().get("cancel");
			if (object != null) {
				return;
			} else {
				String param = null;
				SelectorItemCollection sict = new SelectorItemCollection();
				sict.add("*");
				sict.add("fullOrgUnit.*");
				ObjectUuidPK pk = new ObjectUuidPK(contractBillInfo.getOrgUnit().getId());
				param = ContextHelperFactory.getRemoteInstance().getStringParam("FDC228_ISSTRICTCONTROL", pk);
				if (!com.kingdee.util.StringUtils.isEmpty(param)) {
					if(pc!=null&&pc.getId().toString().equals(contractBillInfo.getProgrammingContract().getId().toString())){
						return;
					}
//					int i = Integer.parseInt(param);
//					switch (i) {
//					case 0:
//						// 严格控制时
//						if (contractBillInfo.getProgrammingContract() != null) {
//							if (contractBillInfo.getAmount().compareTo(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance()))) > 0) {
//								FDCMsgBox.showWarning(this, "合同签约金额超过关联的合约控制余额！");
//								SysUtil.abort();
//							}
//						} else {
//							FDCMsgBox.showWarning(this, "未关联框架合约！");
//							SysUtil.abort();
//						}
//						break;
//					case 1:
//						// 提示控制时
//						if (contractBillInfo.getProgrammingContract() != null) {
//							if (contractBillInfo.getAmount().compareTo(
//									FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance())) > 0) {
//								if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否关联？") == FDCMsgBox.CANCEL) {
//									SysUtil.abort();
//								}
//							}
//						} else {
//							FDCMsgBox.showWarning(this, "未关联框架合约！");
//							SysUtil.abort();
//						}
//						break;
//					case 2:
//						// 不控制时
//						if (contractBillInfo.getProgrammingContract() != null) {
//							if (contractBillInfo.getAmount().compareTo(
//									FDCHelper.toBigDecimal(contractBillInfo.getProgrammingContract().getBalance())) > 0) {
//								if (FDCMsgBox.showConfirm2(this, "合同签约金额超过关联的合约控制余额，请确认是否关联？") == FDCMsgBox.CANCEL) {
//									SysUtil.abort();
//								}
//							}
//						}
//						break;
//					}
					ContractWithoutTextFactory.getRemoteInstance().synReUpdateProgramming(contractBillInfo.getId().toString(), pc);
					SelectorItemCollection sicz = new SelectorItemCollection();
					sicz.add("programmingContract");
					ContractWithoutTextFactory.getRemoteInstance().updatePartial(contractBillInfo, sicz);
					ContractWithoutTextFactory.getRemoteInstance().synUpdateProgramming(contractBillInfo.getId().toString(), contractBillInfo.getProgrammingContract());
					FDCMsgBox.showInfo("关联框架合约成功！");
					this.refresh(null);
				} 
			}
		}
	}
	public void actionUnProgram_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showInfo("请选择一条记录进行操作！");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
			FDCMsgBox.showWarning(this,"已在工作流处理中，请稍后再试！");
			SysUtil.abort();
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("programmingContract.*");
		ContractWithoutTextInfo contractBillInfo = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(selectedKeyValue), sic);
		if(contractBillInfo.getProgrammingContract()==null){
			FDCMsgBox.showWarning(this,"请选择关联过合约规划记录进行取消操作！");
			SysUtil.abort();
		}
		ContractWithoutTextFactory.getRemoteInstance().synReUpdateProgramming(contractBillInfo.getId().toString(), contractBillInfo.getProgrammingContract());
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("programmingContract");
		contractBillInfo.setProgrammingContract(null);
		ContractWithoutTextFactory.getRemoteInstance().updatePartial(contractBillInfo, sel);
		FDCMsgBox.showInfo(this,"操作成功！");
		this.refresh(null);
	}
	protected boolean isFootVisible(){
		return true;
	}
	protected void checkBeforeRemove(String str) throws Exception {
		checkSelected();

		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		String[] states = getBillStateForEditOrRemove();

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if (billState.equals(states[i])) {
					pass = true;
				}
			}
			if (!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(str));
				SysUtil.abort();
			}
		}
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove("cantRemove");
		super.actionRemove_actionPerformed(e);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove("cantEdit");
		super.actionEdit_actionPerformed(e);
	}
}