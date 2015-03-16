/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Component;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.finance.client.DeductBillInfoUI;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractFullInfoUI extends AbstractContractFullInfoUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";
	private boolean isFirstLoad=true;
	/**
	 * output class constructor
	 */
	public ContractFullInfoUI() throws Exception {
		super();
	}

	private String getNoSplitId(String contractId) throws BOSException {
		ConNoCostSplitCollection contractCostSplitCollection = ConNoCostSplitFactory
				.getRemoteInstance().getConNoCostSplitCollection(
						"select id where contractBill='" + contractId + "'");
		if (contractCostSplitCollection.size() > 0) {
			ConNoCostSplitInfo info = contractCostSplitCollection.get(0);
			return info.getId().toString();
		}
		return null;
	}

	private String getSplitId(String contractId) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		ContractCostSplitCollection contractCostSplitCollection = ContractCostSplitFactory
				.getRemoteInstance().getContractCostSplitCollection(view);
		if (contractCostSplitCollection.size() > 0) {
			ContractCostSplitInfo info = contractCostSplitCollection.get(0);
			return info.getId().toString();
		}
		return null;
	}

	private String getSettleId(String contractId) throws BOSException {
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		//支持多次结算，取最后一个
		SorterItemInfo sorter = new SorterItemInfo("createTime");
		sorter.setSortType(SortType.DESCEND);
		view.getSorter().add(sorter);
		ContractSettlementBillCollection settles = ContractSettlementBillFactory
				.getRemoteInstance().getContractSettlementBillCollection(view);
		if (settles.size() > 0) {
			ContractSettlementBillInfo info = settles.get(0);
			return info.getId().toString();
		}
		return null;
	}

	public void loadFields() {
		super.loadFields();
	}
	
	private void addPanel(String id, String uiClass, String title)
			throws UIException {
		if (id == null) {
			return;
		}
		if(isFirstLoad){
			KDScrollPane scrollPane=new KDScrollPane();
			this.plContrastFullInfo.add(scrollPane, title);
			int index = plContrastFullInfo.indexOfTab(title);
			if(index==0){
				//打开界面时加载第一个页签
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, id);
				CoreUIObject plUI = (CoreUIObject) UIFactoryHelper.initUIObject(uiClass, uiContext,
						null, "VIEW");
				scrollPane.setViewportView(plUI);
				scrollPane.setKeyBoardControl(true);
			}
			scrollPane.setUserObject(new String[]{id,uiClass});
		}
	}
	
	private void lazyLoadPanel()throws UIException {
		if(isFirstLoad) return;
		int index = plContrastFullInfo.getSelectedIndex();
		if(index==0){
			return;
		}
		KDScrollPane scrollPane=(KDScrollPane)plContrastFullInfo.getComponentAt(index);
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		Object obj = scrollPane.getUserObject();
		if(obj!=null&&obj.getClass().isArray()){
			String s[]=(String[])obj;
			UIContext uiContext = new UIContext(ui);
			uiContext.put(UIContext.ID, s[0]);
			uiContext.put("contractBillId", contractId);
			uiContext.put("fromContractQuery", Boolean.TRUE);
			//12.15添加 在结算单 页签显示时 把汇总 页签隐藏
			if(s[1].equals(ContractSettlementBillEditUI.class.getName()))
				uiContext.put("isShowPanelCollection", Boolean.FALSE);
			
			Component plUI = (CoreUIObject) UIFactoryHelper.initUIObject(s[1], uiContext,
					null, OprtState.VIEW);
			if(plUI instanceof PaymentBillEditUI){
				//付款单只显示工程付款情况表
				PaymentBillEditUI ui=(PaymentBillEditUI)plUI;
				plUI=ui.getPayBillEntryPanel();
			}
			scrollPane.setViewportView(plUI);
			scrollPane.setKeyBoardControl(true);
			scrollPane.setUserObject("hasLoad");
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		isFirstLoad=true;
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionPre.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionNext.setVisible(false);
		super.onLoad();
		plContrastFullInfo.removeAll();
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		ContractBillInfo contract = ContractBillFactory
				.getRemoteInstance()
				.getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
		addPanel(contractId, ContractDetailFullInfoUI.class.getName(), this.getResouce("billInfo"));
		if (contract.isIsCoseSplit()) {
			addPanel(this.getSplitId(contractId), ContractCostSplitEditUI.class
					.getName(), this.getResouce("splitInfo"));
		} else {
			addPanel(this.getNoSplitId(contractId), ConNoCostSplitEditUI.class
					.getName(), this.getResouce("splitInfo"));
		}
		Object obj = getUIContext().get("addSettlePanel");
		//结算单加载汇总信息时不加载自身
		if(obj==null||obj.equals(Boolean.TRUE)){
			addPanel(this.getSettleId(contractId),
				ContractSettlementBillEditUI.class.getName(), this.getResouce("settleInfo"));
		}
		addPanel(contractId, ContractChangeFullUI.class.getName(), this.getResouce("changeInfo"));
		addPanel(contractId, ContractCostFullInfoUI.class.getName(), this.getResouce("costInfo"));
		addPanel(contractId, PaymentFullInfoUI.class.getName(), this.getResouce("payInfo"));
//		addPanel(contractId, ContractPayPlanEditUI.class.getName(), this.getResouce("payPlan"));
		addPanel(contractId, PayRequestFullInfoUI.class.getName(), this.getResouce("payRequest"));
		addPanel(contractId, ContractMoveHistoryListUI.class.getName(), this.getResouce("bakInfo"));
		//关联合同页签
		addPanel(contractId, ContractBillFullUI.class.getName(), "关联合同页签");
		
		//违约单、扣款单、奖励单
		addPanel(contractId, CompensationBillInfoUI.class.getName(), this.getResouce("Compensation"));
		addPanel(contractId, DeductBillInfoUI.class.getName(), this.getResouce("Guerdon"));
		addPanel(contractId, GuerdonBilInfoUI.class.getName(), this.getResouce("Deduct"));
		
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contractBillId", contractId);
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select top 1 fid from T_CAS_PaymentBill where fcontractbillid=? order by fcreateTime desc");
		builder.addParam(contractId);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet!=null&&rowSet.size()==1){
			rowSet.next();
			String fid=rowSet.getString("fid");
			addPanel(fid, PaymentBillEditUI.class.getName(), "工程付款情况表");
		}
		
		plContrastFullInfo.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				try {
					lazyLoadPanel();
				} catch (UIException e1) {
					handUIException(e1);
				}
			}
		});
		isFirstLoad=false;
		
		menuItemCopy.setVisible(false);
		btnCopy.setVisible(false);
		
	}

	private String getResouce(String resName) {
		return EASResource.getString(resourcePath, resName);
	}

	/**
	 * 根据id显示窗体
	 */
	public static boolean showDialogWindows(IUIObject ui, String id)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, id);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(ContractFullInfoUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
		return true;
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}
	
//	public SelectorItemCollection getSelectors() {
//		// TODO Auto-generated method stub
//		SelectorItemCollection sic = super.getSelectors();
//		sic.add(new SelectorItemInfo("mainContract.*"));
//		sic.add(new SelectorItemInfo("effectiveStartDate"));
//		sic.add(new SelectorItemInfo("effectiveEndDate"));
//		sic.add(new SelectorItemInfo("isSubContract"));
//		sic.add(new SelectorItemInfo("information"));
//		return sic;
//	}
}