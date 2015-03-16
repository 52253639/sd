/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.spi.SPInfo;
import com.kingdee.bos.spi.SPManager;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBill;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basecrm.client.NewSHERevBillEditUI;
import com.kingdee.eas.fdc.basecrm.client.SHERevBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BankPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeManageFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeManageInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreBillListUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IDAPBillTrans;
import com.kingdee.eas.framework.client.IDAPTrans;
import com.kingdee.eas.fdc.sellhouse.DealStateEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
/**
 * output class name
 */
public class PaymentManageUI extends AbstractPaymentManageUI {
	private static final Logger logger = CoreUIObject.getLogger(PaymentManageUI.class);
	private static final String BIZTYPE = "变更类型";
	private static final String DEALSTATE = "处理状态";
	private static final String ROOM = "房间";
	private static final String CUSTOMER = "客户";
	private static final String PAYPERSON = "交款人";
	private static final String BIZDATE = "业务日期";
	private static final String NUMBER = "单据编码";
	private static final String PROJECTNUMBER = "项目排号";
	private static final String PERSON = "预约人";
	private static final String BANK = "贷款银行";
	private static final String BANUMBER = "放款单号";
	private static final String MONEYDEFINETYPE = "款项类型";
	private static final String ACTDATE = "实收日期";
	private static final String STATE = "单据状态";
	private static final String INBANK = "入账银行";
	private static final String ISNEW = "最新交易";
	private static final String ISOLD = "历史交易";

	private static final String TB_ID = "id";
	private static final String TB_NUMBER = "number";
	private static final String TB_STATE = "state";
	private static final String TB_DEALSTATE = "dealState";
	private static final String TB_BIZDATE = "bizDate";
	private static final String TB_BIZTYPE = "bizType";
	private static final String TB_SRCROOM = "srcRoom";
	private static final String TB_SRCCUSTOMER = "srcCustomer";
	private static final String TB_ROOM = "room";
	private static final String TB_CUSTOMER = "customer";
	private static final String TB_HANDLER = "handler";
	private static final String TB_BANK = "bank";
	private static final String TB_CREATOR = "creator";
	private static final String TB_CREATEDATE = "createDate";
	private static final String TB_AUDITOR = "auditor";
	private static final String TB_AUDITDATE = "auditDate";
	private static final String TB_PAYPERSON = "payPerson";
	private static final String TB_AMOUNT = "amount";
	private static final String TB_CURRENCY = "currency";
	private static final String TB_INVOICENUMBER = "invoiceNumber";
	private static final String TB_RECEIPTNUMBER = "receiptNumber";

	private static final String TB_MONEYDEFINE = "moneyDefine";
	private static final String TB_MONEYDEFINETYPE = "moneyDefineType";
	private static final String TB_ACTDATE = "actDate";
	private static final String TB_HASREFUNDMENTAMOUNT = "hasRefundmentAmount";
	private static final String TB_HASTRANSFERREDAMOUNT = "hasTransferredAmount";
	private static final String TB_PERSON = "person";
	private static final String TB_TEL = "tel";
	private static final String TB_INVALIDDATE = "invalidDate";
	private static final String TB_PROJECTNUMBER = "projectNumber";

	private static final String TB_APPDATE = "appDate";
	private static final String TB_APPAMOUNT = "appAmount";
	private static final String TB_ACTAMOUNT = "actAmount";
	private static final String TB_MARK = "mark";

	private static final String TB_TOTALAMOUNT = "totalAmount";
	private static final String TB_SETTLEMENT = "settlement";
	private static final String TB_SETTLEMENTNUMBER = "settlementNumber";
	private static final String TB_ACCOUNT = "account";
	private static final String TB_REACCOUNT = "reAccount";
	private static final String TB_HEAD = "head";
	private static final String TB_TRANID = "tranId";
	private static final String TB_FIVOUCHERED = "fiVouchered";
	private static final String TB_REVTYPE = "revType";
	private static final String TB_REVAMOUNT = "revAmount";
	private static final String TB_APPREVAMOUNT = "appRevAmount";
	
	private static final String AUDITED = "已审批";
	private static final String CANREMOVE="删除";
	private static final String CANEDIT="修改";
	private static final String CANAUDIT="审批";
	private static final String CANUNAUDIT="反审批";
	private static final String CANVOUCHER="生成凭证";
	private static final String UNGATHERING = "未收款";
	private static final String HASGATHERING = "已收款";
	private static final String HASREFUNDMENT = "已退款";
	private static final String HASTRANSFER = "已转款";
	
	protected boolean isSaleHouseOrg = FDCSysContext.getInstance().getOrgMap().containsKey(
			SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	protected SellProjectInfo sellProject = null;
	protected RelatBizType relatBizType = null;
	protected Object[] custObjs = null;
	protected IObjectValue objectValue = null;

	private IDAPTrans dapTrans = null;
	
	public PaymentManageUI() throws Exception {
		super();
	}

	protected void initTree() throws Exception {
//		this.treeMain.setModel(SHEHelper.getSellProjectTree(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	public void onLoad() throws Exception {
		actionQuery.setEnabled(false);
		FDCSysContext.getInstance().checkIsSHEOrg(this);
		initTree();
		initFilterEnum();
		initTable();
		this.labCustomer1.setForeground(Color.BLUE);
		this.labCustomer2.setForeground(Color.BLUE);
		this.labCustomer3.setForeground(Color.BLUE);
		this.labCustomer4.setForeground(Color.BLUE);
		this.labCustomer5.setForeground(Color.BLUE);
		this.f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE, null, null, MoneySysTypeEnum.SalehouseSys, null,
				null));
		super.onLoad();
		initControl();
		
//		if (SPManager.isSPInstalled("DAPTransImpl"))
//        {
//        	SPInfo spInfo=SPManager.getInstance().getSeviceProvider("DAPTransImpl");
//        	Constructor constructor=spInfo.getProviderClass().getConstructor(new Class[]{CoreBillListUI.class});
//        	
//        	UIContext uiContext = new UIContext(this);
//			uiContext.put(getMainBizOrgType(), getMainOrgInfo());
//			ReceiveGatherListUI ui=(ReceiveGatherListUI) UIFactoryHelper.initUIObject(ReceiveGatherListUI.class.getName(), uiContext, null,OprtState.VIEW);
//    		Object dapObject=constructor.newInstance(new Object[]{ui});
//    		dapTrans =(IDAPTrans)dapObject; 
//    		dapTrans.init();
//        }else
//        {
//        	this.actionVoucher.setEnabled(false);
//        	this.actionVoucher.setVisible(false);
//        	this.actionDelVoucher.setEnabled(false);
//        	this.actionDelVoucher.setVisible(false);
//        }
		actionQuery.setEnabled(true);
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton btnReCal=new KDWorkButton();
			btnReCal.setText("重新计算");
			btnReCal.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnReCal_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
			btnReCal.setBounds(new Rectangle(658, 36, 120, 19));
	        this.panelRoomAmount.add(btnReCal, new KDLayout.Constraints(658, 19, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		
	        KDWorkButton btnReAllCal=new KDWorkButton();
	        btnReAllCal.setText("项目重新计算");
	        btnReAllCal.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnReAllCal_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
	        btnReAllCal.setBounds(new Rectangle(658, 36, 120, 19));
	        this.panelRoomAmount.add(btnReAllCal, new KDLayout.Constraints(658, 0, 120, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		}
	}
	protected void btnReCal_actionPerformed(ActionEvent e) throws Exception {
		if(objectValue==null){
			return;
		}
		ObjectUuidPK pk=new ObjectUuidPK(((BaseTransactionInfo)objectValue).getTransactionID());
		TransactionInfo info=TransactionFactory.getRemoteInstance().getTransactionInfo(pk);
		TransactionInfo newInfo=(TransactionInfo) info.clone();
		for(int i=0;i<newInfo.getTranBusinessOverView().size();i++){
			newInfo.getTranBusinessOverView().get(i).setActRevAmount(FDCHelper.ZERO);
		}
		SHERevBillFactory.getRemoteInstance().whenTransEntryChange(info, newInfo);
		fillTable();
	}
	protected void btnReAllCal_actionPerformed(ActionEvent e) throws Exception {
		if(sellProject==null) return;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		
		sqlBuilder.appendSql("select * from ( ");
		sqlBuilder.appendSql("select bill.fsellProjectId SELLPROJECT,bill.ftransactionID TRANID,bill.fbizState STATE from t_she_prePurchaseManage bill ");
		sqlBuilder.appendSql("left join t_she_room room on room.fid=bill.froomid ");
		sqlBuilder.appendSql("union ");
		sqlBuilder.appendSql("select bill.fsellProjectId SELLPROJECT,bill.ftransactionID TRANID,bill.fbizState STATE from t_she_purchaseManage bill ");
		sqlBuilder.appendSql("left join t_she_room room on room.fid=bill.froomid ");
		sqlBuilder.appendSql("union ");
		sqlBuilder.appendSql("select bill.fsellProjectId SELLPROJECT,bill.ftransactionID TRANID,bill.fbizState STATE from t_she_signManage bill ");
		sqlBuilder.appendSql("left join t_she_room room on room.fid=bill.froomid ");
		sqlBuilder.appendSql(") t where 1=1 ");
		
		if(sellProject!=null){
			sqlBuilder.appendSql("and SELLPROJECT =? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		}else{
			sqlBuilder.appendSql("and SELLPROJECT =? ");
			sqlBuilder.addParam("null");
		}
		sqlBuilder.appendSql("and STATE in('PreApple','PreAudit','PurApple','PurAudit','SignApple','SignAudit') ");
		IRowSet rs = sqlBuilder.executeQuery();
		while(rs.next()){
			ObjectUuidPK pk=new ObjectUuidPK(rs.getString("TRANID"));
			TransactionInfo info=TransactionFactory.getRemoteInstance().getTransactionInfo(pk);
			TransactionInfo newInfo=(TransactionInfo) info.clone();
			for(int i=0;i<newInfo.getTranBusinessOverView().size();i++){
				newInfo.getTranBusinessOverView().get(i).setActRevAmount(FDCHelper.ZERO);
			}
			SHERevBillFactory.getRemoteInstance().whenTransEntryChange(info, newInfo);
		}
	}
	protected void initControl() {
//		this.toolBar.setVisible(false);
		this.menuFile.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.treeMain.setSelectionRow(0);

		this.f7Room.setEnabled(false);
		
		for(int i=0;i<this.toolBar.getComponentCount();i++){
			if(!this.toolBar.getComponent(i).equals(this.btnRefresh)){
				this.toolBar.getComponent(i).setVisible(false);
			}
		}
		this.panelPPActAmount.setVisible(false);
		this.btnPPTranAmount.setVisible(false);
		this.btnPPInvoice.setVisible(false);
		this.btnPPRecycleInvoice.setVisible(false);
		
		this.panelTabEMActBill.remove(this.panelEMActAmount);
		this.btnEMTranAmount.setVisible(false);
		this.btnEMInvoice.setVisible(false);
		this.btnEMRecycleInvoice.setVisible(false);
		
		this.panelTabRAActBill.remove(this.panelRaActAmount);
		this.btnRATranAmount.setVisible(false);
		this.btnRAInvoice.setVisible(false);
		this.btnRARecycleInvoice.setVisible(false);
		this.contRelateBaseTran.setVisible(false);
		
		this.btnAddBill.setVisible(false);
		this.btnEditBill.setVisible(false);
		this.btnRemoveBill.setVisible(false);
		this.btnAuditBill.setVisible(false);
		this.btnUnAuditBill.setVisible(false);
		
		this.btnABReceiveAmount.setVisible(false);
		this.btnABQuitAmount.setVisible(false);
		this.btnABTranAmount.setVisible(false);
		this.btnABInvoice.setVisible(false);
		this.btnABRecycleInvoice.setVisible(false);
		this.btnGenTotalFin.setVisible(false);
		
		this.btnAAQuitAmount.setVisible(false);
		this.btnAATranAmount.setVisible(false);
		this.btnAAInvoice.setVisible(false);
		this.btnAARecycleInvoice.setVisible(false);
		
		this.panelTabPayment.remove(this.panelTotalPayment);
		
		this.kDUnGathering.setSelected(true);
		
		tblPPActBill.getColumn("appRevAmount").getStyleAttributes().setHided(true);
		tblEMActBill.getColumn("appRevAmount").getStyleAttributes().setHided(true);
		tblRAActBill.getColumn("appRevAmount").getStyleAttributes().setHided(true);
		tblABActBill.getColumn("appRevAmount").getStyleAttributes().setHided(true);
		tblAAActAmount.getColumn("appRevAmount").getStyleAttributes().setHided(true);
	}

	private void setTblSelectMode(KDTable table, int mode) {
		table.checkParsed();
		table.getStyleAttributes().setLocked(true);
		table.getSelectManager().setSelectMode(mode);
		
		if(!table.equals(this.tblAAActAmount)){
			String[] fields=new String[table.getColumnCount()];
			for(int i=0;i<table.getColumnCount();i++){
				fields[i]=table.getColumnKey(i);
			}
			KDTableHelper.setSortedColumn(table,fields);
		}
	}

	private void setTblNumberFormat(KDTable table, String coloum[]) {
		for (int i = 0; i < coloum.length; i++) {
			table.getColumn(coloum[i]).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			table.getColumn(coloum[i]).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
	}

	protected void initTable() {
		setTblSelectMode(this.tblCMBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblBankAmountBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblPPActBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblPPActAmount, KDTSelectManager.MULTIPLE_ROW_SELECT);
		setTblSelectMode(this.tblEMBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblEMActBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblEMActAmount, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblABActBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblRABill, KDTSelectManager.MULTIPLE_ROW_SELECT);
		setTblSelectMode(this.tblRAActBill, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblRAActAmount, KDTSelectManager.MULTIPLE_ROW_SELECT);
		setTblSelectMode(this.tblRevGather, KDTSelectManager.ROW_SELECT);
		setTblSelectMode(this.tblAAActAmount, KDTSelectManager.MULTIPLE_ROW_SELECT);
		
		setTblNumberFormat(this.tblPPActBill, new String[] { TB_AMOUNT ,TB_REVAMOUNT, TB_APPREVAMOUNT });
//		setTblNumberFormat(this.tblPPActAmount, new String[] { TB_AMOUNT,TB_HASREFUNDMENTAMOUNT, TB_HASTRANSFERREDAMOUNT });
		
		setTblNumberFormat(this.tblEMActBill, new String[] { TB_AMOUNT ,TB_REVAMOUNT, TB_APPREVAMOUNT });
//		setTblNumberFormat(this.tblEMActAmount, new String[] {TB_AMOUNT, TB_HASREFUNDMENTAMOUNT, TB_HASTRANSFERREDAMOUNT });
		
		setTblNumberFormat(this.tblRABill, new String[] { TB_APPAMOUNT, TB_ACTAMOUNT });
		setTblNumberFormat(this.tblRAActBill, new String[] { TB_AMOUNT ,TB_REVAMOUNT, TB_APPREVAMOUNT });
//		setTblNumberFormat(this.tblRAActAmount, new String[] { TB_AMOUNT,TB_HASREFUNDMENTAMOUNT, TB_HASTRANSFERREDAMOUNT });
		
		setTblNumberFormat(this.tblABActBill, new String[] { TB_AMOUNT ,TB_REVAMOUNT, TB_APPREVAMOUNT });
		setTblNumberFormat(this.tblAAActAmount, new String[] { TB_AMOUNT,TB_REVAMOUNT, TB_APPREVAMOUNT });
		
		setTblNumberFormat(this.tblBankAmountBill, new String[] { TB_AMOUNT });
		setTblNumberFormat(this.tblRevGather, new String[] { TB_TOTALAMOUNT });
	}

	protected void initFilterEnum() {
		this.comboCMFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { BIZTYPE }));
		this.comboCMFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { DEALSTATE }));

//		this.comboPPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboPPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
		this.comboPPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { PAYPERSON }));
		this.comboPPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { BIZDATE }));
		this.comboPPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { NUMBER }));

		this.pkPPFilterValue.setVisible(false);
		this.pkPPFilterValue.setValue(null);

		this.comboEMFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
		this.comboEMFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboEMFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { PROJECTNUMBER }));
		this.comboEMFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { PERSON }));

		this.comboRAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboRAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));

		this.comboBAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboBAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
		this.comboBAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { BANK }));
		this.comboBAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { BANUMBER }));

		this.comboABFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboABFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
		this.comboABFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { BIZDATE }));
		this.comboABFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { NUMBER }));

		this.pkABFilterValue.setVisible(false);
		this.pkABFilterValue.setValue(null);

		this.comboAAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboAAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
		this.comboAAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { MONEYDEFINETYPE }));
		this.comboAAFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ACTDATE }));

		this.comboAAFilterValue.setVisible(false);
		this.pkAAFilterValue.setVisible(false);
		this.pkAAFilterValue.setValue(null);
		this.comboAAFilterValue.addItems(MoneyTypeEnum.getEnumList().toArray());

		this.comboTPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { STATE }));
		this.comboTPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { NUMBER }));
		this.comboTPFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { INBANK }));

		this.txtTPFilterValue.setVisible(false);
		this.comboTPFilterValue.addItem(FDCBillStateEnum.getEnum(FDCBillStateEnum.SUBMITTED_VALUE));
		this.comboTPFilterValue.addItem(FDCBillStateEnum.getEnum(FDCBillStateEnum.AUDITTING_VALUE));
		this.comboTPFilterValue.addItem(FDCBillStateEnum.getEnum(FDCBillStateEnum.AUDITTED_VALUE));

		this.comboRelateBaseTran.addItem(new KDComboBoxMultiColumnItem(new String[] { ISNEW }));
		this.comboRelateBaseTran.addItem(new KDComboBoxMultiColumnItem(new String[] { ISOLD }));
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject = null;
		if (node.getUserObject() instanceof SellProjectInfo) {
			// 项目
			if (node.getUserObject() != null) {
				sellProject = (SellProjectInfo) node.getUserObject();
			}
		} 
		if (node.getUserObject() instanceof OrgStructureInfo){
//			this.actionAddBill.setEnabled(false);
			this.actionReceiveAmount.setEnabled(false);
			this.actionQuitAmount.setEnabled(false);
//			this.actionTranAmount.setEnabled(false);
			this.actionSelect.setEnabled(false);
//			this.actionGenTotalFin.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
//				this.actionAddBill.setEnabled(false);
				this.actionReceiveAmount.setEnabled(false);
				this.actionQuitAmount.setEnabled(false);
//				this.actionTranAmount.setEnabled(false);
				this.actionSelect.setEnabled(false);
//				this.actionGenTotalFin.setEnabled(false);
			}else if(isSaleHouseOrg){
//				this.actionAddBill.setEnabled(true);
				this.actionReceiveAmount.setEnabled(true);
				this.actionQuitAmount.setEnabled(true);
//				this.actionTranAmount.setEnabled(true);
				this.actionSelect.setEnabled(true);
//				this.actionGenTotalFin.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
//				this.actionAddBill.setEnabled(true);
				this.actionReceiveAmount.setEnabled(true);
				this.actionQuitAmount.setEnabled(true);
//				this.actionTranAmount.setEnabled(true);
				this.actionSelect.setEnabled(true);
//				this.actionGenTotalFin.setEnabled(true);
			}
		}
		this.tblCMBill.removeRows();
		this.tblBankAmountBill.removeRows();
		this.tblPPActBill.removeRows();
		this.tblPPActAmount.removeRows();
		this.tblEMBill.removeRows();
		this.tblEMActBill.removeRows();
		this.tblEMActAmount.removeRows();
		this.tblABActBill.removeRows();
		this.tblRABill.removeRows();
		this.tblRAActBill.removeRows();
		this.tblRAActAmount.removeRows();
		this.tblRevGather.removeRows();
		this.tblAAActAmount.removeRows();
		
		this.f7Room.setValue(null);
		this.btnRelateSaleBill.setUserObject(null);
		clearCustomer();
		custObjs = null;
		relatBizType = null;
		objectValue = null;
	}

	protected String getTextValue(KDTextField text) {
		if (text.getText().trim().equals("")) {
			return null;
		} else {
			return text.getText();
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fillTable();
	}

	protected void fillTable() throws SQLException, BOSException {
		IRowSet rowSet = null;
		String room = null;
		String customer = null;
		String payPerson = null;
		Date bizDate = null;
		String number = null;
		String bank = null;
		String person = null;
		String state = null;
		String moneyType = null;
		Date actDate = null;
		
		if (this.panelChangeManage.isShowing()) {
			this.tblCMBill.removeRows();
			if (comboCMFilter.getSelectedItem().toString().equals(BIZTYPE)) {
				rowSet = getChangeMangaeSqlResult(
						((ChangeBizTypeEnum) comboCMFilterValue.getSelectedItem()).getValue(), DealStateEnum.NOTDEAL_VALUE);
			} else {
				rowSet = getChangeMangaeSqlResult(null, ((DealStateEnum) comboCMFilterValue.getSelectedItem())
						.getValue());
			}
			if (rowSet != null) {
				while (rowSet.next()) {
					IRow row = this.tblCMBill.addRow();
					row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
					row.getCell(TB_NUMBER).setValue(rowSet.getString(TB_NUMBER));
					row.getCell(TB_STATE).setValue(AUDITED);
					if(rowSet.getString(TB_DEALSTATE)!=null){
						row.getCell(TB_DEALSTATE).setValue(DealStateEnum.getEnum(rowSet.getString(TB_DEALSTATE)).getAlias());
					}
					row.getCell(TB_BIZDATE).setValue(rowSet.getDate(TB_BIZDATE));
					if(rowSet.getString(TB_BIZTYPE)!=null){
						row.getCell(TB_BIZTYPE).setValue(ChangeBizTypeEnum.getEnum(rowSet.getString(TB_BIZTYPE)).getAlias());
					}
					row.getCell(TB_SRCROOM).setValue(rowSet.getString(TB_SRCROOM));
					row.getCell(TB_SRCCUSTOMER).setValue(rowSet.getString(TB_SRCCUSTOMER));
					row.getCell(TB_ROOM).setValue(rowSet.getString(TB_ROOM));
					row.getCell(TB_CUSTOMER).setValue(rowSet.getString(TB_CUSTOMER));
					row.getCell(TB_HANDLER).setValue(rowSet.getString(TB_HANDLER));
				}
			}
		}
		if (this.panelPrePayment.isShowing()) {
			this.tblPPActBill.removeRows();
			this.tblPPActAmount.removeRows();
			if (comboPPFilter.getSelectedItem().toString().equals(ROOM)) {
				room = getTextValue(txtPPFilterValue);
			}
			if (comboPPFilter.getSelectedItem().toString().equals(CUSTOMER)) {
				customer = getTextValue(txtPPFilterValue);
			}
			if (comboPPFilter.getSelectedItem().toString().equals(PAYPERSON)) {
				payPerson = getTextValue(txtPPFilterValue);
			}
			if (comboPPFilter.getSelectedItem().toString().equals(BIZDATE)) {
				bizDate = (Date) pkPPFilterValue.getValue();
			}
			if (comboPPFilter.getSelectedItem().toString().equals(BANUMBER)) {
				number = getTextValue(txtPPFilterValue);
			}
			rowSet = getSHERevPaymentSqlResult(room, customer, payPerson, bizDate, number, null, null, true);
			fillActBill(this.tblPPActBill, rowSet, false);
		}

		if (this.panelBankAmount.isShowing()) {
			this.tblBankAmountBill.removeRows();
			if (comboBAFilter.getSelectedItem().toString().equals(ROOM)) {
				room = getTextValue(txtBAFilterValue);
			}
			if (comboBAFilter.getSelectedItem().toString().equals(CUSTOMER)) {
				customer = getTextValue(txtBAFilterValue);
			}
			if (comboBAFilter.getSelectedItem().toString().equals(BANK)) {
				bank = getTextValue(txtBAFilterValue);
			}
			if (comboBAFilter.getSelectedItem().toString().equals(BANUMBER)) {
				number = getTextValue(txtBAFilterValue);
			}
			rowSet = getBankPaymentSqlResult(room, customer, bank, number);
			if (rowSet != null) {
				while (rowSet.next()) {
					IRow row = this.tblBankAmountBill.addRow();
					row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
					row.getCell(TB_NUMBER).setValue(rowSet.getString(TB_NUMBER));
					if(rowSet.getString(TB_STATE)!=null){
						row.getCell(TB_STATE).setValue(FDCBillStateEnum.getEnum(rowSet.getString(TB_STATE)).getAlias());
					}
					row.getCell(TB_BIZDATE).setValue(rowSet.getDate(TB_BIZDATE));
					row.getCell(TB_AMOUNT).setValue(rowSet.getBigDecimal(TB_AMOUNT));
					row.getCell(TB_BANK).setValue(rowSet.getString(TB_BANK));
					row.getCell(TB_CREATOR).setValue(rowSet.getString(TB_CREATOR));
					row.getCell(TB_CREATEDATE).setValue(rowSet.getDate(TB_CREATEDATE));
					row.getCell(TB_AUDITOR).setValue(rowSet.getString(TB_AUDITOR));
					row.getCell(TB_AUDITDATE).setValue(rowSet.getDate(TB_AUDITDATE));
				}
				CRMClientHelper.getFootRow(this.tblBankAmountBill, new String[]{TB_AMOUNT});
			}
		}
		if (this.panelEarnestMoney.isShowing()) {
			this.tblEMBill.removeRows();
			this.tblEMActBill.removeRows();
			this.tblEMActAmount.removeRows();
			if (comboEMFilter.getSelectedItem().toString().equals(ROOM)) {
				room = getTextValue(txtEMFilterValue);
			}
			if (comboEMFilter.getSelectedItem().toString().equals(CUSTOMER)) {
				customer = getTextValue(txtEMFilterValue);
			}
			if (comboEMFilter.getSelectedItem().toString().equals(PERSON)) {
				person = getTextValue(txtEMFilterValue);
			}
			if (comboEMFilter.getSelectedItem().toString().equals(PROJECTNUMBER)) {
				number = getTextValue(txtEMFilterValue);
			}
			rowSet = this.getSincerityPurchaseSqlResult(number, room, customer, person,this.kDUnGathering.isSelected(),this.kDHasGathering.isSelected(),this.kDHasRefundment.isSelected(),this.kDHasTransfer.isSelected());
			if (rowSet != null) {
				while (rowSet.next()) {
					IRow row = this.tblEMBill.addRow();
					row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
					row.getCell(TB_TRANID).setValue(rowSet.getString(TB_TRANID));
					row.getCell(TB_REVTYPE).setValue(rowSet.getString(TB_REVTYPE));
					row.getCell(TB_NUMBER).setValue(rowSet.getString(TB_NUMBER));
					row.getCell(TB_ROOM).setValue(rowSet.getString(TB_ROOM));
					row.getCell(TB_CUSTOMER).setValue(rowSet.getString(TB_CUSTOMER));
					row.getCell(TB_PERSON).setValue(rowSet.getString(TB_PERSON));
					row.getCell(TB_TEL).setValue(rowSet.getString(TB_TEL));
					row.getCell(TB_BIZDATE).setValue(rowSet.getDate(TB_BIZDATE));
					row.getCell(TB_INVALIDDATE).setValue(rowSet.getDate(TB_INVALIDDATE));
					row.getCell(TB_PROJECTNUMBER).setValue(rowSet.getString(TB_PROJECTNUMBER));
					if(rowSet.getString(TB_STATE)!=null){
						row.getCell(TB_STATE).setValue(TransactionStateEnum.getEnum(rowSet.getString(TB_STATE)).getAlias());
					}
				}
			}
		}
		if (this.panelActBill.isShowing()) {
			this.tblABActBill.removeRows();
			if (comboABFilter.getSelectedItem().toString().equals(ROOM)) {
				room = getTextValue(txtABFilterValue);
			}
			if (comboABFilter.getSelectedItem().toString().equals(CUSTOMER)) {
				customer = getTextValue(txtABFilterValue);
			}
			if (comboABFilter.getSelectedItem().toString().equals(BIZDATE)) {
				bizDate = (Date) pkABFilterValue.getValue();
			}
			if (comboABFilter.getSelectedItem().toString().equals(NUMBER)) {
				number = getTextValue(txtABFilterValue);
			}
			rowSet = getSHERevPaymentSqlResult(room, customer, null, bizDate, number, null, null, false);
			fillActBill(this.tblABActBill, rowSet, true);
		}
		if (this.panelRoomAmount.isShowing()) {
			
			this.tblRABill.removeRows();
			this.tblRAActBill.removeRows();
			this.tblRAActAmount.removeRows();
			
			if(this.btnRelateSaleBill.getUserObject()!=null)
				rowSet = getTranBusinessOverViewSqlResult(this.btnRelateSaleBill.getUserObject().toString(),relatBizType);
			if (rowSet != null) {
				while (rowSet.next()) {
					IRow row = this.tblRABill.addRow();
					row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
					row.getCell(TB_MONEYDEFINE).setValue(rowSet.getString(TB_MONEYDEFINE));
					row.getCell(TB_APPDATE).setValue(rowSet.getDate(TB_APPDATE));
					row.getCell(TB_APPAMOUNT).setValue(rowSet.getBigDecimal(TB_APPAMOUNT));
					row.getCell(TB_ACTAMOUNT).setValue(rowSet.getBigDecimal(TB_ACTAMOUNT));
					row.getCell(TB_CURRENCY).setValue(rowSet.getString(TB_CURRENCY));
					if (rowSet.getBigDecimal(TB_ACTAMOUNT)!=null){
						if(rowSet.getBigDecimal(TB_APPAMOUNT).compareTo(FDCHelper.ZERO)<0){
							if(rowSet.getBigDecimal(TB_ACTAMOUNT).compareTo(rowSet.getBigDecimal(TB_APPAMOUNT))==0) {
								row.getCell(TB_MARK).setValue(new Boolean(true));
							}else{
								row.getCell(TB_MARK).setValue(new Boolean(false));
							}
						}else{
							if(rowSet.getBigDecimal(TB_ACTAMOUNT).compareTo(rowSet.getBigDecimal(TB_APPAMOUNT))>=0) {
								row.getCell(TB_MARK).setValue(new Boolean(true));
							}else{
								row.getCell(TB_MARK).setValue(new Boolean(false));
							}
						}
					} else {
						row.getCell(TB_MARK).setValue(new Boolean(false));
					}
				}
				CRMClientHelper.getFootRow(this.tblRABill, new String[]{TB_APPAMOUNT,TB_ACTAMOUNT});
			}
			
			if(objectValue!=null) {
				rowSet = this.getSHERevPaymentSqlResult(null, null, null, null, null, null, ((BaseTransactionInfo)objectValue).getTransactionID().toString(),false);
				fillActBill(this.tblRAActBill, rowSet, false);
			}
		}
		if (this.panelTotalPayment.isShowing()) {
			this.tblRevGather.removeRows();

			if (comboTPFilter.getSelectedItem().toString().equals(STATE)) {
				state = ((FDCBillStateEnum) comboTPFilterValue.getSelectedItem()).getValue();
			}
			if (comboTPFilter.getSelectedItem().toString().equals(NUMBER)) {
				number = getTextValue(txtTPFilterValue);
			}
			if (comboTPFilter.getSelectedItem().toString().equals(INBANK)) {
				bank = getTextValue(txtTPFilterValue);
			}

			rowSet = this.getReceiveGatherSqlResult(state, number, bank);
			if (rowSet != null) {
				while (rowSet.next()) {
					IRow row = this.tblRevGather.addRow();
					row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
					row.getCell(TB_NUMBER).setValue(rowSet.getString(TB_NUMBER));
					if(rowSet.getString(TB_STATE)!=null){
						row.getCell(TB_STATE).setValue(FDCBillStateEnum.getEnum(rowSet.getString(TB_STATE)).getAlias());
					}
					row.getCell(TB_FIVOUCHERED).setValue(new Boolean(rowSet.getBoolean(TB_FIVOUCHERED)));
					row.getCell(TB_TOTALAMOUNT).setValue(rowSet.getBigDecimal(TB_TOTALAMOUNT));
					row.getCell(TB_SETTLEMENT).setValue(rowSet.getString(TB_SETTLEMENT));
					row.getCell(TB_SETTLEMENTNUMBER).setValue(rowSet.getString(TB_SETTLEMENTNUMBER));
					row.getCell(TB_BANK).setValue(rowSet.getString(TB_BANK));
					row.getCell(TB_ACCOUNT).setValue(rowSet.getString(TB_ACCOUNT));
					row.getCell(TB_REACCOUNT).setValue(rowSet.getString(TB_REACCOUNT));
				}
				CRMClientHelper.getFootRow(this.tblRABill, new String[]{TB_TOTALAMOUNT});
			}
		}
		if (this.panelActAmount.isShowing()) {
			this.tblAAActAmount.removeRows();

			if (comboAAFilter.getSelectedItem().toString().equals(MONEYDEFINETYPE)) {
				moneyType = ((MoneyTypeEnum) comboAAFilterValue.getSelectedItem()).getValue();
			}
			if (comboAAFilter.getSelectedItem().toString().equals(ACTDATE)) {
				actDate = (Date) pkAAFilterValue.getValue();
			}
			if (comboAAFilter.getSelectedItem().toString().equals(ROOM)) {
				room = getTextValue(txtAAFilterValue);
			}
			if (comboAAFilter.getSelectedItem().toString().equals(CUSTOMER)) {
				customer =  getTextValue(txtAAFilterValue);
			}
			IRowSet sheRevEntryRowSet = getSHERevPaymenteEntrySqlResult(null, moneyType, actDate,customer,room);
			fillActAmount(this.tblAAActAmount, sheRevEntryRowSet, false);
		}
	}
	private void loadCustomer(){
		if (custObjs != null && custObjs.length > 0) {
			if (custObjs.length == 1) {
				labCustomer1.setText(((SHECustomerInfo) custObjs[0]).getName());
				labCustomer1.setUserObject((SHECustomerInfo) custObjs[0]);
				labCustomer2.setText(null);
				labCustomer2.setUserObject(null);
				labCustomer3.setText(null);
				labCustomer3.setUserObject(null);
				labCustomer4.setText(null);
				labCustomer4.setUserObject(null);
				labCustomer5.setText(null);
				labCustomer5.setUserObject(null);
			}
			if (custObjs.length == 2) {
				labCustomer1.setText(((SHECustomerInfo) custObjs[0]).getName());
				labCustomer1.setUserObject((SHECustomerInfo) custObjs[0]);
				labCustomer2.setText(((SHECustomerInfo) custObjs[1]).getName());
				labCustomer2.setUserObject((SHECustomerInfo) custObjs[1]);
				labCustomer3.setText(null);
				labCustomer3.setUserObject(null);
				labCustomer4.setText(null);
				labCustomer4.setUserObject(null);
				labCustomer5.setText(null);
				labCustomer5.setUserObject(null);
			}
			if (custObjs.length == 3) {
				labCustomer1.setText(((SHECustomerInfo) custObjs[0]).getName());
				labCustomer1.setUserObject((SHECustomerInfo) custObjs[0]);
				labCustomer2.setText(((SHECustomerInfo) custObjs[1]).getName());
				labCustomer2.setUserObject((SHECustomerInfo) custObjs[1]);
				labCustomer3.setText(((SHECustomerInfo) custObjs[2]).getName());
				labCustomer3.setUserObject((SHECustomerInfo) custObjs[2]);
				labCustomer4.setText(null);
				labCustomer4.setUserObject(null);
				labCustomer5.setText(null);
				labCustomer5.setUserObject(null);
			}
			if (custObjs.length == 4) {
				labCustomer1.setText(((SHECustomerInfo) custObjs[0]).getName());
				labCustomer1.setUserObject((SHECustomerInfo) custObjs[0]);
				labCustomer2.setText(((SHECustomerInfo) custObjs[1]).getName());
				labCustomer2.setUserObject((SHECustomerInfo) custObjs[1]);
				labCustomer3.setText(((SHECustomerInfo) custObjs[2]).getName());
				labCustomer3.setUserObject((SHECustomerInfo) custObjs[2]);
				labCustomer4.setText(((SHECustomerInfo) custObjs[3]).getName());
				labCustomer4.setUserObject((SHECustomerInfo) custObjs[3]);
				labCustomer5.setText(null);
				labCustomer5.setUserObject(null);
			}
			if (custObjs.length == 5) {
				labCustomer1.setText(((SHECustomerInfo) custObjs[0]).getName());
				labCustomer1.setUserObject((SHECustomerInfo) custObjs[0]);
				labCustomer2.setText(((SHECustomerInfo) custObjs[1]).getName());
				labCustomer2.setUserObject((SHECustomerInfo) custObjs[1]);
				labCustomer3.setText(((SHECustomerInfo) custObjs[2]).getName());
				labCustomer3.setUserObject((SHECustomerInfo) custObjs[2]);
				labCustomer4.setText(((SHECustomerInfo) custObjs[3]).getName());
				labCustomer4.setUserObject((SHECustomerInfo) custObjs[3]);
				labCustomer5.setText(((SHECustomerInfo) custObjs[4]).getName());
				labCustomer5.setUserObject((SHECustomerInfo) custObjs[4]);
			}
		} else {
			labCustomer1.setText(null);
			labCustomer2.setText(null);
			labCustomer3.setText(null);
			labCustomer1.setUserObject(null);
			labCustomer2.setUserObject(null);
			labCustomer3.setUserObject(null);
			labCustomer4.setText(null);
			labCustomer4.setUserObject(null);
			labCustomer5.setText(null);
			labCustomer5.setUserObject(null);
		}
	}
	private String fillActBill(KDTable table, IRowSet rowSet, boolean hasCr) throws SQLException {
		String id=null;
		if (rowSet != null) {
			while (rowSet.next()) {
				IRow row = table.addRow();
				row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
				row.getCell(TB_NUMBER).setValue(rowSet.getString(TB_NUMBER));
				if(rowSet.getString(TB_BIZTYPE)!=null){
					row.getCell(TB_BIZTYPE).setValue(RevBillTypeEnum.getEnum(rowSet.getString(TB_BIZTYPE)).getAlias());
				}
				if(rowSet.getString(TB_STATE)!=null){
					row.getCell(TB_STATE).setValue(FDCBillStateEnum.getEnum(rowSet.getString(TB_STATE)).getAlias());
				}
				row.getCell(TB_PAYPERSON).setValue(rowSet.getString(TB_PAYPERSON));
				row.getCell(TB_ROOM).setValue(rowSet.getString(TB_ROOM));
				row.getCell(TB_CUSTOMER).setValue(rowSet.getString(TB_CUSTOMER));
				row.getCell(TB_BIZDATE).setValue(rowSet.getDate(TB_BIZDATE));
				row.getCell(TB_REVAMOUNT).setValue(rowSet.getBigDecimal(TB_REVAMOUNT));
				row.getCell(TB_APPREVAMOUNT).setValue(rowSet.getBigDecimal(TB_APPREVAMOUNT));
				row.getCell(TB_AMOUNT).setValue(rowSet.getBigDecimal(TB_AMOUNT));
				row.getCell(TB_CURRENCY).setValue(rowSet.getString(TB_CURRENCY));
				if (hasCr) {
					row.getCell(TB_CREATOR).setValue(rowSet.getString(TB_CREATOR));
					row.getCell(TB_CREATEDATE).setValue(rowSet.getDate(TB_CREATEDATE));
					row.getCell(TB_AUDITOR).setValue(rowSet.getString(TB_AUDITOR));
					row.getCell(TB_AUDITDATE).setValue(rowSet.getDate(TB_AUDITDATE));
				}
			}
			CRMClientHelper.getFootRow(table, new String[]{TB_REVAMOUNT,TB_APPREVAMOUNT,TB_AMOUNT});
			if(rowSet.size()>0){
				table.getSelectManager().select(0,0);
				return id=table.getRow(0).getCell(TB_ID).getValue().toString();
			}
		}
		return id;
	}
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
	private void fillActAmount(KDTable table, IRowSet rowSet, boolean isHasActBill) throws SQLException {
		if (rowSet != null) {
			while (rowSet.next()) {
				IRow row = table.addRow();
				if (!isHasActBill) {
					if(rowSet.getString(TB_MONEYDEFINETYPE)!=null){
						row.getCell(TB_MONEYDEFINETYPE).setValue(
								MoneyTypeEnum.getEnum(rowSet.getString(TB_MONEYDEFINETYPE)).getAlias());
					}
					row.getCell(TB_CUSTOMER).setValue(rowSet.getString(TB_CUSTOMER));
					row.getCell(TB_ROOM).setValue(rowSet.getString(TB_ROOM));
				}
				row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
				row.getCell(TB_HEAD).setValue(rowSet.getString(TB_HEAD));
				row.getCell(TB_MONEYDEFINE).setValue(rowSet.getString(TB_MONEYDEFINE));
				row.getCell(TB_ACTDATE).setValue(rowSet.getDate(TB_BIZDATE));
				row.getCell(TB_REVAMOUNT).setValue(rowSet.getBigDecimal(TB_REVAMOUNT));
				row.getCell(TB_APPREVAMOUNT).setValue(rowSet.getBigDecimal(TB_APPREVAMOUNT));
				row.getCell(TB_AMOUNT).setValue(rowSet.getBigDecimal(TB_AMOUNT));
				row.getCell(TB_CURRENCY).setValue(rowSet.getString(TB_CURRENCY));
				row.getCell(TB_INVOICENUMBER).setValue(rowSet.getString(TB_INVOICENUMBER));
				row.getCell(TB_RECEIPTNUMBER).setValue(rowSet.getString(TB_RECEIPTNUMBER));
			}
			CRMClientHelper.getFootRow(table, new String[]{TB_REVAMOUNT,TB_APPREVAMOUNT,TB_AMOUNT});
			if(!isHasActBill){
				mergerTable(table,new String[]{TB_ROOM,TB_CUSTOMER},new String[]{TB_ROOM,TB_CUSTOMER});
			}
		}
	}

	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		if(this.panelRoomAmount.isShowing()){
			String room=null;
			String customer=null;
			boolean isNew = true;
			if (comboRAFilter.getSelectedItem().toString().equals(ROOM)) {
				room = getTextValue(txtRAFilterValue);
			}
			if (comboRAFilter.getSelectedItem().toString().equals(CUSTOMER)) {
				customer = getTextValue(txtRAFilterValue);
			}
			if (comboRelateBaseTran.getSelectedItem().toString().equals(ISOLD)) {
				isNew = false;
			}
			UIContext uiContext = new UIContext(this);
			Map info = new HashMap();
			uiContext.put("room", room);
			uiContext.put("customer", customer);
			uiContext.put("isNew", new Boolean(isNew));
			uiContext.put("sellProject", sellProject);
			uiContext.put("info", info);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					PaymentManageRelateBillUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
			
			if (info.get("id") != null && info.get("bizType") != null&&info.get("tranId")!= null  ) {
				this.f7Room.setValue(info.get("room"));

				custObjs = ((Object[]) info.get("customer"));
				loadCustomer();
				
				if(info.get("comboFilter").toString().equals(ROOM)){
					this.comboRAFilter.setSelectedIndex(0);
				}
				if(info.get("comboFilter").toString().equals(CUSTOMER)){
					this.comboRAFilter.setSelectedIndex(1);
				}
				txtRAFilterValue.setText((String)info.get("txtFilterValue"));
				
				this.btnRelateSaleBill.setUserObject(info.get("id"));
				relatBizType = (RelatBizType) info.get("bizType");
				ObjectUuidPK pk = new ObjectUuidPK(info.get("id").toString());
				objectValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(), pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
			}
		}
		fillTable();
	}

	public void actionAddBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelBankAmount.isShowing()) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", sellProject);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(BankPaymentEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
		if (this.panelTotalPayment.isShowing()) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", sellProject);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ReceiveGatherFilterListUI.class.getName(), uiContext, null,
					OprtState.ADDNEW);
			uiWindow.show();
		}

		fillTable();
	}
	public void checkBillState(String can,FDCBillInfo info){
		if(info==null) return;
		if(can.equals(CANEDIT)||can.equals(CANREMOVE)){
			if(FDCBillStateEnum.AUDITTED.equals(info.getState())){
				FDCMsgBox.showWarning(this,"已审批的单据不能"+can+"！");
				SysUtil.abort();
			}
			if(FDCBillStateEnum.AUDITTING.equals(info.getState())){
				FDCMsgBox.showWarning(this,"审批中的单据不能"+can+"！");
				SysUtil.abort();
			}
		}
		if(can.equals(CANAUDIT)){
			if(!FDCBillStateEnum.SUBMITTED.equals(info.getState())){
				FDCMsgBox.showWarning(this,"只有提交的单据才能"+can+"！");
				SysUtil.abort();
			}
		}
		if(can.equals(CANUNAUDIT)){
			if(!FDCBillStateEnum.AUDITTED.equals(info.getState())){
				FDCMsgBox.showWarning(this,"只有已审批的单据才能"+can+"！");
				SysUtil.abort();
			}
		}
		if(can.equals(CANVOUCHER)){
			if(!FDCBillStateEnum.AUDITTED.equals(info.getState())){
				FDCMsgBox.showWarning(this,"只有已审批的单据才能"+can+"！");
				SysUtil.abort();
			}
		}
	}
	public void actionEditBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelBankAmount.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblBankAmountBill);
			String id=getTableCellValue(this.tblBankAmountBill, TB_ID).get(0).toString();
			checkBillState(CANEDIT,BankPaymentFactory.getRemoteInstance().getBankPaymentInfo(new ObjectUuidPK(id)));
			
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(BankPaymentEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}
		if (this.panelTotalPayment.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblRevGather);
			String id=getTableCellValue(this.tblRevGather, TB_ID).get(0).toString();
			checkBillState(CANEDIT,ReceiveGatherFactory.getRemoteInstance().getReceiveGatherInfo(new ObjectUuidPK(id)));
			
			
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ReceiveGatherEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}
		fillTable();
	}

	public void actionRemoveBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelBankAmount.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblBankAmountBill);
			String id=getTableCellValue(this.tblBankAmountBill, TB_ID).get(0).toString();
			
			checkBillState(CANREMOVE,BankPaymentFactory.getRemoteInstance().getBankPaymentInfo(new ObjectUuidPK(id)));
			
			if (confirmRemove()) {
				BankPaymentFactory.getRemoteInstance().delete(new ObjectUuidPK(id));
			}
		}
		if (this.panelTotalPayment.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblRevGather);
			String id=getTableCellValue(this.tblRevGather, TB_ID).get(0).toString();
			
			checkBillState(CANREMOVE,ReceiveGatherFactory.getRemoteInstance().getReceiveGatherInfo(new ObjectUuidPK(id)));
			
			if (confirmRemove()) {
				ReceiveGatherFactory.getRemoteInstance().delete(new ObjectUuidPK(id));
			}
		}
		fillTable();
	}

	public void actionAuditBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelBankAmount.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblBankAmountBill);
			List id=getTableCellValue(this.tblBankAmountBill, TB_ID);
			
			checkBillState(CANAUDIT,BankPaymentFactory.getRemoteInstance().getBankPaymentInfo(new ObjectUuidPK(id.get(0).toString())));
			
			BankPaymentFactory.getRemoteInstance().audit(id);
			FDCClientUtils.showOprtOK(this);
		}
		if (this.panelTotalPayment.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblRevGather);
			List id=getTableCellValue(this.tblRevGather, TB_ID);
			
			checkBillState(CANAUDIT,ReceiveGatherFactory.getRemoteInstance().getReceiveGatherInfo(new ObjectUuidPK(id.get(0).toString())));
			
			ReceiveGatherFactory.getRemoteInstance().audit(id);
			FDCClientUtils.showOprtOK(this);
		}
		fillTable();
	}

	public void actionUnAuditBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelBankAmount.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblBankAmountBill);
			List id=getTableCellValue(this.tblBankAmountBill, TB_ID);
			
			checkBillState(CANUNAUDIT,BankPaymentFactory.getRemoteInstance().getBankPaymentInfo(new ObjectUuidPK(id.get(0).toString())));
			
			BankPaymentFactory.getRemoteInstance().unAudit(id);
			FDCClientUtils.showOprtOK(this);
		}
		if (this.panelTotalPayment.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblRevGather);
			List id=getTableCellValue(this.tblRevGather, TB_ID);
			
			checkBillState(CANUNAUDIT,ReceiveGatherFactory.getRemoteInstance().getReceiveGatherInfo(new ObjectUuidPK(id.get(0).toString())));
			
			ReceiveGatherFactory.getRemoteInstance().unAudit(id);
			FDCClientUtils.showOprtOK(this);
		}
		fillTable();
	}
	
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
//		if (dapTrans instanceof IDAPBillTrans){
//			IDAPBillTrans dapTransform =(IDAPBillTrans)dapTrans; 
//    		dapTransform.init();
//    		return;
//    	}
        SHEManageHelper.checkSelected(this,this.tblRevGather);
        ArrayList list=this.getTableCellValue(this.tblRevGather, TB_ID);
        ReceiveGatherInfo info= ReceiveGatherFactory.getRemoteInstance().getReceiveGatherInfo(new ObjectUuidPK(list.get(0).toString()));
        
        checkBillState(CANVOUCHER,info);
        if(info.isFiVouchered()){
        	String msg = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_VoucherError_1");
        	FDCMsgBox.showWarning(this,msg);
        	return;
        }
        CoreBillBaseCollection col=new CoreBillBaseCollection();
        col.add(info);
        dapTrans.trans(col);
        fillTable();
	}

	public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception {
		SHEManageHelper.checkSelected(this,this.tblRevGather);
		ArrayList list=this.getTableCellValue(this.tblRevGather, TB_ID);
		ReceiveGatherInfo info= ReceiveGatherFactory.getRemoteInstance().getReceiveGatherInfo(new ObjectUuidPK(list.get(0).toString()));
	       
		if(!info.isFiVouchered()){
			String msg = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_DelVoucherError");
			FDCMsgBox.showWarning(this,msg);
			return;
		}
        String message =EASResource.getString(FrameWorkClientUtils.strResource+ "Confirm_Delete");
        if(MsgBox.isYes(MsgBox.showConfirm2(this,message ))){
            dapTrans.delTrans(list);
            fillTable();
        }
	}
	public  SelectorItemCollection getBizSelectors(BOSObjectType bosType) {
		SelectorItemCollection sic = new SelectorItemCollection();
    	if(bosType.equals(new PrePurchaseManageInfo().getBOSType())){
    		sic.add("prePurchaseCustomerEntry.*");
    		sic.add("prePurchaseCustomerEntry.customer.*");
    		sic.add("prePurchasePayListEntry.*");
	    }else if(bosType.equals(new PurchaseManageInfo().getBOSType())){
	    	sic.add("purCustomerEntry.*");
    		sic.add("purCustomerEntry.customer.*");
    		sic.add("purPayListEntry.*");
    	}else if(bosType.equals(new SignManageInfo().getBOSType())){
    		sic.add("signCustomerEntry.*");
    		sic.add("signCustomerEntry.customer.*");
    		sic.add("signPayListEntry.*");
    	}
    	sic.add("room.*");
    	sic.add("transactionID");
    	sic.add("number");
    	sic.add("room.*");
    	return sic;
     }
	public void actionFinDeal_actionPerformed(ActionEvent e) throws Exception {
		SHEManageHelper.checkSelected(this,this.tblCMBill);
		
		int activeRowIndex = this.tblCMBill.getSelectManager().getActiveRowIndex();
		IRow selectRow = this.tblCMBill.getRow(activeRowIndex);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("srcRoom.*");
		sic.add("srcId");
		sic.add("newId");
		sic.add("transactionID");
		ChangeManageInfo info=ChangeManageFactory.getRemoteInstance().getChangeManageInfo(new ObjectUuidPK(selectRow.getCell(TB_ID).getValue().toString()),sic);
		
		ObjectUuidPK pk = new ObjectUuidPK(info.getSrcId());
		ObjectUuidPK newpk = new ObjectUuidPK(info.getNewId());
		
		IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(), pk,
				getBizSelectors(pk.getObjectType()));
		
		if(info.getNewId()==null){
			Object[] quitCustomer =null;
			if(objectValue instanceof PrePurchaseManageInfo){
				quitCustomer=new Object[((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size()];
				for(int i=0;i<((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size();i++){
					quitCustomer[i]=(((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(i).getCustomer());
				}
	    	}else if(objectValue instanceof PurchaseManageInfo){
	    		quitCustomer=new Object[((PurchaseManageInfo)objectValue).getPurCustomerEntry().size()];
				for(int i=0;i<((PurchaseManageInfo)objectValue).getPurCustomerEntry().size();i++){
					quitCustomer[i]=(((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(i).getCustomer());
				}
	    	}else if(objectValue instanceof SignManageInfo){
	    		quitCustomer=new Object[((SignManageInfo)objectValue).getSignCustomerEntry().size()];
				for(int i=0;i<((SignManageInfo)objectValue).getSignCustomerEntry().size();i++){
					quitCustomer[i]=(((SignManageInfo)objectValue).getSignCustomerEntry().get(i).getCustomer());
				}
	    	}
			CRMClientHelper.openTheRevBillWindow(this, null, sellProject, RevBillTypeEnum.refundment, (BaseTransactionInfo)objectValue, quitCustomer, new SHERevBillEntryCollection());
			return;
		}
		
		IObjectValue newobjectValue = DynamicObjectFactory.getRemoteInstance().getValue(newpk.getObjectType(), newpk,
				getBizSelectors(newpk.getObjectType()));
		
		Object[] newCustomer =null;
		Object[] oldCustomer =null;
		Set newtranEntryIdSet = new HashSet();
		RoomInfo room=null;
		RoomInfo newRoom=null;
		if(objectValue instanceof PrePurchaseManageInfo){
			newCustomer=new Object[((PrePurchaseManageInfo)newobjectValue).getPrePurchaseCustomerEntry().size()];
			for(int i=0;i<((PrePurchaseManageInfo)newobjectValue).getPrePurchaseCustomerEntry().size();i++){
				newCustomer[i]=(((PrePurchaseManageInfo)newobjectValue).getPrePurchaseCustomerEntry().get(i).getCustomer());
			}
			oldCustomer=new Object[((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size()];
			for(int i=0;i<((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size();i++){
				oldCustomer[i]=(((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(i).getCustomer());
			}
			
			PrePurchasePayListEntryCollection newsignPayListColl = ((PrePurchaseManageInfo)newobjectValue).getPrePurchasePayListEntry();
			for (int i = 0; i < newsignPayListColl.size(); i++) {
				PrePurchasePayListEntryInfo signPayEntryInfo = newsignPayListColl.get(i);
				if(signPayEntryInfo.getTanPayListEntryId()!=null)
					newtranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
			}
			
			objectValue.put("RelateBizType", RelatBizType.PreOrder);
			newobjectValue.put("RelateBizType", RelatBizType.PreOrder);
			room=((PrePurchaseManageInfo)objectValue).getRoom();
			newRoom=((PrePurchaseManageInfo)newobjectValue).getRoom();
    	}else if(objectValue instanceof PurchaseManageInfo){
    		newCustomer=new Object[((PurchaseManageInfo)newobjectValue).getPurCustomerEntry().size()];
			for(int i=0;i<((PurchaseManageInfo)newobjectValue).getPurCustomerEntry().size();i++){
				newCustomer[i]=(((PurchaseManageInfo)newobjectValue).getPurCustomerEntry().get(i).getCustomer());
			}
			oldCustomer=new Object[((PurchaseManageInfo)objectValue).getPurCustomerEntry().size()];
			for(int i=0;i<((PurchaseManageInfo)objectValue).getPurCustomerEntry().size();i++){
				oldCustomer[i]=(((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(i).getCustomer());
			}
			
			PurPayListEntryCollection newsignPayListColl = ((PurchaseManageInfo)newobjectValue).getPurPayListEntry();
			for (int i = 0; i < newsignPayListColl.size(); i++) {
				PurPayListEntryInfo signPayEntryInfo = newsignPayListColl.get(i);
				if(signPayEntryInfo.getTanPayListEntryId()!=null)
					newtranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
			}
			
			objectValue.put("RelateBizType", RelatBizType.Purchase);
			newobjectValue.put("RelateBizType", RelatBizType.Purchase);
			room=((PurchaseManageInfo)objectValue).getRoom();
			newRoom=((PurchaseManageInfo)newobjectValue).getRoom();
    	}else if(objectValue instanceof SignManageInfo){
    		newCustomer=new Object[((SignManageInfo)newobjectValue).getSignCustomerEntry().size()];
			for(int i=0;i<((SignManageInfo)newobjectValue).getSignCustomerEntry().size();i++){
				newCustomer[i]=(((SignManageInfo)newobjectValue).getSignCustomerEntry().get(i).getCustomer());
			}
			oldCustomer=new Object[((SignManageInfo)objectValue).getSignCustomerEntry().size()];
			for(int i=0;i<((SignManageInfo)objectValue).getSignCustomerEntry().size();i++){
				oldCustomer[i]=(((SignManageInfo)objectValue).getSignCustomerEntry().get(i).getCustomer());
			}
			
			SignPayListEntryCollection newsignPayListColl = ((SignManageInfo)newobjectValue).getSignPayListEntry();
			for (int i = 0; i < newsignPayListColl.size(); i++) {
				SignPayListEntryInfo signPayEntryInfo = newsignPayListColl.get(i);
				if(signPayEntryInfo.getTanPayListEntryId()!=null)
					newtranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
			}
			
			objectValue.put("RelateBizType", RelatBizType.Sign);
			newobjectValue.put("RelateBizType", RelatBizType.Sign);
			room=((SignManageInfo)objectValue).getRoom();
			newRoom=((SignManageInfo)newobjectValue).getRoom();
    	}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("SellProjectInfo", sellProject);
		uiContext.put("RevBillTypeEnum", RevBillTypeEnum.refundment);
		uiContext.put("FDCBillInfo", (BaseTransactionInfo)objectValue);
		uiContext.put("CustomerEntrys", oldCustomer);
		uiContext.put("RoomInfo", room);
		
		BigDecimal PMAmount=FDCHelper.ZERO;
		Map PM=new HashMap();
		
		SHERevBillEntryCollection quitrevEntryColl=SHERevBillEntryFactory.getRemoteInstance().getSHERevBillEntryCollection("select *,moneyDefine.* from where parent.relateTransId='"+info.getTransactionID()+"'");
		if(quitrevEntryColl.size()>0) {
			SHERevBillEntryCollection revEntryColl = new SHERevBillEntryCollection();
			for (int i = 0; i < quitrevEntryColl.size(); i++) {
				SHERevBillEntryInfo revEntryInfo = new SHERevBillEntryInfo();
				revEntryInfo.setSeq(i);
				revEntryInfo.setMoneyDefine(quitrevEntryColl.get(i).getMoneyDefine());
				revEntryInfo.setRevAmount(quitrevEntryColl.get(i).getRemainAmount().negate());
				if(quitrevEntryColl.get(i).getRemainAmount().compareTo(new BigDecimal("0"))>0){
					PMAmount=PMAmount.add(quitrevEntryColl.get(i).getRemainAmount());
					revEntryColl.add(revEntryInfo);
				}
			}
			if(PMAmount.compareTo(FDCHelper.ZERO)>0){
				PM.put("PMAmount", PMAmount);
			}else{
				PM=null;
			}
			uiContext.put("PM", PM);
			uiContext.put("RevEntryColl", revEntryColl);
		} 
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
						.create(NewSHERevBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		if(PM==null||PM.get("PMAmount")==null) return;
		UIContext newuiContext = new UIContext(this);
		newuiContext.put("RevBillTypeEnum", RevBillTypeEnum.gathering);
		newuiContext.put("FDCBillInfo", (BaseTransactionInfo)newobjectValue);
		newuiContext.put("CustomerEntrys", newCustomer);
		newuiContext.put("RoomInfo", newRoom);
		newuiContext.put("SellProjectInfo", sellProject);
		newuiContext.put("PM", PM);
		
		if(newtranEntryIdSet.size()>0) {
			String transIdsStr = CRMHelper.getStringFromSet(newtranEntryIdSet);	
			TranBusinessOverViewCollection transfEntryColl = null;
			if(transIdsStr!=null && !transIdsStr.equals("")) {
				transfEntryColl = TranBusinessOverViewFactory.getRemoteInstance()
							.getTranBusinessOverViewCollection("select *,moneyDefine.*,head.room.name,head.room.number where id in ("+transIdsStr+") order by seq ");
				SHERevBillEntryCollection revEntryColl = new SHERevBillEntryCollection();
				for (int i = 0; i < transfEntryColl.size(); i++) {
					SHERevBillEntryInfo revEntryInfo = new SHERevBillEntryInfo();
					revEntryInfo.setSeq(i);
					revEntryInfo.setMoneyDefine(transfEntryColl.get(i).getMoneyDefine());
					revEntryInfo.setRevAmount(transfEntryColl.get(i).getUnPayAmount());
					if(transfEntryColl.get(i).getUnPayAmount().compareTo(new BigDecimal("0"))>0)
						revEntryColl.add(revEntryInfo);
				}
				newuiContext.put("RevEntryColl", revEntryColl);
			}
		} 
		
		IUIWindow newuiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(NewSHERevBillEditUI.class.getName(), newuiContext, null, OprtState.ADDNEW);
		newuiWindow.show();
		ArrayList list=new ArrayList();
		list.add(selectRow.getCell(TB_ID).getValue().toString());
		ChangeManageFactory.getRemoteInstance().mark(list);
		fillTable();
	}

	public void actionMark_actionPerformed(ActionEvent e) throws Exception {
		SHEManageHelper.checkSelected(this,this.tblCMBill);

		if (FDCMsgBox.showConfirm2New(this, "标识完成后不可恢复，请确认是否标识完成?") == MsgBox.YES) {
			ArrayList list = getTableCellValue(this.tblCMBill, TB_ID);
			if(DealStateEnum.DEAL.equals(ChangeManageFactory.getRemoteInstance().getChangeManageInfo(new ObjectUuidPK(list.get(0).toString())).getDealState())){
				FDCMsgBox.showWarning(this,"财务处理已标识完成，不需要再次标识完成！");
				return;
			}
			ChangeManageFactory.getRemoteInstance().mark(list);
			FDCClientUtils.showOprtOK(this);
			fillTable();
		}
	}
	/**
	 * 收款
	 */
	public void actionReceiveAmount_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelPrePayment.isShowing()) {
			CRMClientHelper.openTheGatherRevBillWindow(this, null, sellProject, null, null, null);
		}
		if (this.panelRoomAmount.isShowing()) {
			if(comboRelateBaseTran.getSelectedItem().toString().equals(ISOLD)){
				FDCMsgBox.showWarning(this,"历史交易不允许收款！");
				SysUtil.abort();
			}
			SHEManageHelper.checkSelected(this,this.tblRABill);
			if (this.btnRelateSaleBill.getUserObject() != null && relatBizType != null) {
				int[] selectRows = KDTableUtil.getSelectedRows(this.tblRABill);
				if(selectRows!=null){
					for (int i = 0; i < selectRows.length; i++) {
						IRow temRow = this.tblRABill.getRow(selectRows[i]);
						BigDecimal amount = (BigDecimal)temRow.getCell(TB_APPAMOUNT).getValue();
						if(amount.compareTo(FDCHelper.ZERO)<0){
							Set transEntryIdSet = new HashSet();
							transEntryIdSet.add(temRow.getCell(TB_ID).getValue().toString());
							CRMClientHelper.openTheGatherRevBillWindow(this, null, sellProject, (BaseTransactionInfo)objectValue, this.custObjs,transEntryIdSet);
							return;
						}
					}
				}
				Set transEntryIdSet = new HashSet();
				ArrayList aArray = this.getTableCellValue(tblRABill, TB_ID);
				for (int i = 0; i < aArray.size(); i++) {
					transEntryIdSet.add(aArray.get(i));
				}
				CRMClientHelper.openTheGatherRevBillWindow(this, null, sellProject, (BaseTransactionInfo)objectValue, this.custObjs,transEntryIdSet);
			}
		}
		if (this.panelEarnestMoney.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblEMBill);

			int activeRowIndex = this.tblEMBill.getSelectManager().getActiveRowIndex();
			IRow selectRow = this.tblEMBill.getRow(activeRowIndex);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("customer.customer.*");
			sels.add("sincerPriceEntrys.*");
			sels.add("sincerPriceEntrys.moneyDefine.*");
			SincerityPurchaseInfo info = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(
					new ObjectUuidPK(selectRow.getCell(TB_ID).getValue().toString()), sels);
			Object[] customer = new Object[info.getCustomer().size()];
			for (int i = 0; i < info.getCustomer().size(); i++) {
				customer[i] = info.getCustomer().get(i).getCustomer();
			}
			Set transEntryIdSet = new HashSet();
			for (int i = 0; i < info.getSincerPriceEntrys().size(); i++) {
				SincerReceiveEntryInfo tempPayListInfo = info.getSincerPriceEntrys().get(i);
				transEntryIdSet.add(tempPayListInfo.getTanPayListEntryId().toString());
			}
			CRMClientHelper.openTheGatherRevBillWindow(this, null, sellProject, info, customer,transEntryIdSet);
		}
		if (this.panelActBill.isShowing()) {
			CRMClientHelper.openTheGatherRevBillWindow(this, null, sellProject, null, null, null);
		}
		
		this.fillTable();
//		this.tblRABill.removeRows();
//		this.tblRAActBill.removeRows();
//		
//		IRowSet rowSet = null;
//		if(this.btnRelateSaleBill.getUserObject()!=null)
//			rowSet = getTranBusinessOverViewSqlResult(this.btnRelateSaleBill.getUserObject().toString(),relatBizType);
//		if (rowSet != null) {
//			while (rowSet.next()) {
//				IRow row = this.tblRABill.addRow();
//				row.getCell(TB_ID).setValue(rowSet.getString(TB_ID));
//				row.getCell(TB_MONEYDEFINE).setValue(rowSet.getString(TB_MONEYDEFINE));
//				row.getCell(TB_APPDATE).setValue(rowSet.getDate(TB_APPDATE));
//				row.getCell(TB_APPAMOUNT).setValue(rowSet.getBigDecimal(TB_APPAMOUNT));
//				row.getCell(TB_ACTAMOUNT).setValue(rowSet.getBigDecimal(TB_ACTAMOUNT));
//				row.getCell(TB_CURRENCY).setValue(rowSet.getString(TB_CURRENCY));
//				if (rowSet.getBigDecimal(TB_APPAMOUNT).equals(rowSet.getBigDecimal(TB_ACTAMOUNT))) {
//					row.getCell(TB_MARK).setValue(new Boolean(true));
//				} else {
//					row.getCell(TB_MARK).setValue(new Boolean(false));
//				}
//			}
//		}
//		
//		if(objectValue!=null) {
//			rowSet = this.getSHERevPaymentSqlResult(null, null, null, null, null, null, ((BaseTransactionInfo)objectValue).getTransactionID().toString(),false);
//			fillActBill(this.tblRAActBill, rowSet, false);
//		}
	}
	private Object[] getCustomerAndSHERevBillCol(KDTable actBill, KDTable actAmount) throws EASBizException, BOSException{
		Object[] value=new Object[2];
		String parentid = null;
		int activeRowIndex = -1;
		SHERevBillEntryCollection col = null;
		Object[] customer = null;
		if (actBill != null && actAmount != null) {
			if ((actBill.getRowCount() == 0 || actBill.getSelectManager().size() == 0)
					&&(actAmount.getRowCount() == 0 || actAmount.getSelectManager().size() == 0)) {
				FDCMsgBox.showWarning(this, EASResource
						.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
				SysUtil.abort();
			}
			activeRowIndex = actAmount.getSelectManager().getActiveRowIndex();

			int parent = actBill.getSelectManager().getActiveRowIndex();
			IRow selectRow = actBill.getRow(parent);
			parentid = selectRow.getCell(TB_ID).getValue().toString();
		}
		if (actBill == null) {
			SHEManageHelper.checkSelected(this,actAmount);
			activeRowIndex = actAmount.getSelectManager().getActiveRowIndex();

		}
		if (actAmount == null) {
			SHEManageHelper.checkSelected(this,actBill);

			int parent = actBill.getSelectManager().getActiveRowIndex();
			IRow selectRow = actBill.getRow(parent);
			parentid = selectRow.getCell(TB_ID).getValue().toString();
		}
		if (parentid != null) {
			col = new SHERevBillEntryCollection();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("entrys.*");
			sels.add("entrys.moneyDefine.*");
			sels.add("customerEntry.sheCustomer.*");

			SHERevBillInfo info = SHERevBillFactory.getRemoteInstance().getSHERevBillInfo(new ObjectUuidPK(parentid),sels);
			customer = new Object[info.getCustomerEntry().size()];
			for (int i = 0; i < info.getCustomerEntry().size(); i++) {
				customer[i] = info.getCustomerEntry().get(i).getSheCustomer();
			}
			if (activeRowIndex == -1) {
				col = info.getEntrys();
			}else{
				SelectorItemCollection entrySels = new SelectorItemCollection();
				entrySels.add("*");
				entrySels.add("moneyDefine.*");
				for (int i = 0; i < getTableCellValue(actAmount, TB_ID).size(); i++) {
					SHERevBillEntryInfo entry = SHERevBillEntryFactory.getRemoteInstance().getSHERevBillEntryInfo(
							new ObjectUuidPK(getTableCellValue(actAmount, TB_ID).get(i).toString()), entrySels);
					col.add(entry);
				}
			}
		}else{
			col = new SHERevBillEntryCollection();
			SelectorItemCollection entrySels = new SelectorItemCollection();
			entrySels.add("*");
			entrySels.add("moneyDefine.*");
			for (int i = 0; i < getTableCellValue(actAmount, TB_ID).size(); i++) {
				SHERevBillEntryInfo entry = SHERevBillEntryFactory.getRemoteInstance().getSHERevBillEntryInfo(
						new ObjectUuidPK(getTableCellValue(actAmount, TB_ID).get(i).toString()), entrySels);
				col.add(entry);
			}
		}
		
		value[0]=customer;
		value[1]=col;
		return value;
	}
	private void recycleInvoice(KDTable actBill, KDTable actAmount) throws EASBizException, BOSException{
		Object[] value=getCustomerAndSHERevBillCol(actBill,actAmount);
		SHERevBillEntryCollection revCol=((SHERevBillEntryCollection)value[1]);
		Object[] col=new Object[revCol.size()];
		for(int i=0;i<revCol.size();i++){
			col[i]=revCol.get(i).getId().toString();
		}
		ChequeDetailEntryFactory.getRemoteInstance().clearInvoice(col, false);
		FDCClientUtils.showOprtOK(this);
	}
	private void toInvoice(KDTable actBill, KDTable actAmount) throws EASBizException, BOSException{
//		Object[] value=getCustomerAndSHERevBillCol(actBill,actAmount);
//		NewCommerceHelper.openMakeInvoiceEdit(this, (Object[])value[0], (SHERevBillEntryCollection)value[1]);
	}
	private void quitAmount(KDTable actBill, KDTable actAmount) throws EASBizException, BOSException {
		Object[] value=getCustomerAndSHERevBillCol(actBill,actAmount);
		SHERevBillEntryCollection quitEntryColl = (SHERevBillEntryCollection)value[1];
		//退款明细的实收金额为负值,退款对应的实收明细id要填，id要为空
		Set revEnrtyIdSet = new HashSet();
		for (int i = 0; i < quitEntryColl.size(); i++) {
			SHERevBillEntryInfo quitEntryInfo = quitEntryColl.get(i);
			revEnrtyIdSet.add(quitEntryInfo.getId().toString());
		}
		CRMClientHelper.openTheQuitOrTransfRevBillWindow(this, RevBillTypeEnum.refundment, sellProject, revEnrtyIdSet);
	}

	/**
	 * 退款
	 */
	public void actionQuitAmount_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelPrePayment.isShowing()) {
			quitAmount(this.tblPPActBill, this.tblPPActAmount);
		}
		if (this.panelRoomAmount.isShowing()) {
			if (this.btnRelateSaleBill.getUserObject() != null && relatBizType != null) {
				int[] selectRows = KDTableUtil.getSelectedRows(this.tblRABill);
				if(selectRows!=null){
					for (int i = 0; i < selectRows.length; i++) {
						IRow temRow = this.tblRABill.getRow(selectRows[i]);
						BigDecimal amount = (BigDecimal)temRow.getCell(TB_APPAMOUNT).getValue();
						if(amount.compareTo(FDCHelper.ZERO)<0){
							Set transEntryIdSet = new HashSet();
							transEntryIdSet.add(temRow.getCell(TB_ID).getValue().toString());
							CRMClientHelper.openTheGatherRevBillWindow(this, null, sellProject, (BaseTransactionInfo)objectValue, this.custObjs,transEntryIdSet);
							return;
						}
					}
				}
			}
			quitAmount(this.tblRAActBill, this.tblRAActAmount);
		}
		if (this.panelEarnestMoney.isShowing()) {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblEMBill);
			if(selectRows==null || selectRows.length==0){
				FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
				return;
			}
			for (int i = 0; i < selectRows.length; i++) {
				IRow temRow = this.tblEMBill.getRow(selectRows[i]);
				String idStr = (String)temRow.getCell(TB_STATE).getValue();
				if(!idStr.equals(TransactionStateEnum.QUITNUMBER.getAlias())){
					FDCMsgBox.showWarning(this, "只有业务状态为退号的预约单才能进行退款操作！");
					return;
				}
			}
			quitAmount(this.tblEMActBill, this.tblEMActAmount);
		}
		if (this.panelActBill.isShowing()) {
			quitAmount(this.tblABActBill, null);
		}
		if (this.tblAAActAmount.isShowing()) {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblAAActAmount);
			if(selectRows==null || selectRows.length==0){
				FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
				return;
			}
			
			//必须说选明细对应的收款单的客户明细存在相同的客户，否则不允许退款和转款
			Set idsSet = new HashSet(); 
			for (int i = 0; i < selectRows.length; i++) {
				IRow temRow = this.tblAAActAmount.getRow(selectRows[i]);
				String idStr = (String)temRow.getCell(TB_ID).getValue();
				idsSet.add(idStr);
			}
			
			CRMClientHelper.openTheQuitOrTransfRevBillWindow(this,RevBillTypeEnum.refundment, sellProject, idsSet);
		}
		this.fillTable();
	}

	/**
	 * 转款
	 */
	public void actionTranAmount_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelPrePayment.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblPPActBill);
			IRow selectRow = KDTableUtil.getSelectedRow(this.tblPPActBill);
			String idStr = (String)selectRow.getCell(TB_ID).getValue();
			
			CRMClientHelper.openTheQuitORTransfRevBillWindow(this,RevBillTypeEnum.transfer, sellProject, idStr);
			return;
		}
		if (this.panelRoomAmount.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblRAActBill);
			IRow selectRow = KDTableUtil.getSelectedRow(this.tblRAActBill);
			String idStr = (String)selectRow.getCell(TB_ID).getValue();
			
			CRMClientHelper.openTheQuitORTransfRevBillWindow(this,RevBillTypeEnum.transfer, sellProject, idStr);
			return;	
		}
		if (this.panelEarnestMoney.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblEMActBill);
			IRow selectRow = KDTableUtil.getSelectedRow(this.tblEMActBill);
			String idStr = (String)selectRow.getCell(TB_ID).getValue();
			
			CRMClientHelper.openTheQuitORTransfRevBillWindow(this,RevBillTypeEnum.transfer, sellProject, idStr);
			return;	
		}
		if (this.panelActBill.isShowing()) {
			SHEManageHelper.checkSelected(this,this.tblABActBill);
			IRow selectRow = KDTableUtil.getSelectedRow(this.tblABActBill);
			String idStr = (String)selectRow.getCell(TB_ID).getValue();
			
			CRMClientHelper.openTheQuitORTransfRevBillWindow(this,RevBillTypeEnum.transfer, sellProject, idStr);
			return;	
		}
		if (this.tblAAActAmount.isShowing()) {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblAAActAmount);
			if(selectRows==null || selectRows.length==0){
				FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
				return;
			}
			
			//必须说选明细对应的收款单的客户明细存在相同的客户，否则不允许退款和转款
			Set idsSet = new HashSet(); 
			for (int i = 0; i < selectRows.length; i++) {
				IRow temRow = this.tblAAActAmount.getRow(i);
				String idStr = (String)temRow.getCell(TB_ID).getValue();
				idsSet.add(idStr);
			}
			
			CRMClientHelper.openTheQuitOrTransfRevBillWindow(this,RevBillTypeEnum.transfer, sellProject, idsSet);
			return;	
			//quitAmount(null, this.tblAAActAmount);
		}
		CRMClientHelper.openTheQuitOrTransfRevBillWindow(this, RevBillTypeEnum.transfer, sellProject, null);
		this.fillTable();
	}

	/**
	 * 开票
	 */
	public void actionInvoice_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelPrePayment.isShowing()) {
			toInvoice(tblPPActBill,tblPPActAmount);
		}
		if (this.panelRoomAmount.isShowing()) {
			toInvoice(this.tblRAActBill, this.tblRAActAmount);
		}
		if (this.panelEarnestMoney.isShowing()) {
			toInvoice(this.tblEMActBill, this.tblEMActAmount);
		}
		if (this.panelActBill.isShowing()) {
			toInvoice(this.tblABActBill, null);
		}
		if (this.tblAAActAmount.isShowing()) {
			toInvoice(null, this.tblAAActAmount);
		}
		this.fillTable();
	}

	/**
	 * 回收票据
	 */
	public void actionRecycleInvoice_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelPrePayment.isShowing()) {
			recycleInvoice(tblPPActBill,tblPPActAmount);
		}
		if (this.panelRoomAmount.isShowing()) {
			recycleInvoice(this.tblRAActBill, this.tblRAActAmount);
		}
		if (this.panelEarnestMoney.isShowing()) {
			recycleInvoice(this.tblEMActBill, this.tblEMActAmount);
		}
		if (this.panelActBill.isShowing()) {
			recycleInvoice(this.tblABActBill, null);
		}
		if (this.tblAAActAmount.isShowing()) {
			recycleInvoice(null, this.tblAAActAmount);
		}
		this.fillTable();
	}

	/**
	 * 生成出纳汇总单
	 */
	public void actionGenTotalFin_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("sellProject", sellProject);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(ReceiveGatherFilterListUI.class.getName(), uiContext, null,
				OprtState.ADDNEW);
		uiWindow.show();
		this.fillTable();
	}

	/**
	 * 关联销售单据
	 */
	public void actionRelateSaleBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.btnRelateSaleBill.getUserObject() != null && relatBizType != null) {
			String ui = null;
			if (relatBizType.equals(RelatBizType.PrePur)) {
				ui = PrePurchaseManageEditUI.class.getName();
			}
			if (relatBizType.equals(RelatBizType.Purchase)) {
				ui = PurchaseManageEditUI.class.getName();
			}
			if (relatBizType.equals(RelatBizType.Sign)) {
				ui = SignManageEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", this.btnRelateSaleBill.getUserObject().toString());
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ui, uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"无关联销售单据！");
		}
	}
	protected ArrayList getTableCellValue(KDTable table, String columnName) {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(table);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = table.getRow(selectRows[i]);
			list.add(row.getCell(columnName).getValue());
		}
		return list;
	}
	private void clearCustomer(){
		labCustomer1.setText(null);
		labCustomer2.setText(null);
		labCustomer3.setText(null);
		labCustomer1.setUserObject(null);
		labCustomer2.setUserObject(null);
		labCustomer3.setUserObject(null);
		
		labCustomer4.setText(null);
		labCustomer4.setUserObject(null);
		labCustomer5.setText(null);
		labCustomer5.setUserObject(null);
	}
	protected void comboRelateBaseTran_itemStateChanged(ItemEvent e) throws Exception {
		this.tblRABill.removeRows();
		this.tblRAActBill.removeRows();
		this.tblRAActAmount.removeRows();
		clearCustomer();
		this.f7Room.setValue(null);
		this.btnRelateSaleBill.setUserObject(null);
		custObjs = null;
		relatBizType = null;
		objectValue = null;
	}

	protected void comboAAFilter_itemStateChanged(ItemEvent e) throws Exception {
		if (this.comboAAFilter.getSelectedItem().toString().equals(MONEYDEFINETYPE)) {
			this.pkAAFilterValue.setVisible(false);
			this.comboAAFilterValue.setVisible(true);
			this.txtAAFilterValue.setVisible(false);
		} else if(this.comboAAFilter.getSelectedItem().toString().equals(ACTDATE)){
			this.comboAAFilterValue.setVisible(false);
			this.pkAAFilterValue.setVisible(true);
			this.txtAAFilterValue.setVisible(false);
		}else{
			this.comboAAFilterValue.setVisible(false);
			this.pkAAFilterValue.setVisible(false);
			this.txtAAFilterValue.setVisible(true);
		}
	}

	protected void comboABFilter_itemStateChanged(ItemEvent e) throws Exception {
		if (this.comboABFilter.getSelectedItem().toString().equals(BIZDATE)) {
			this.pkABFilterValue.setVisible(true);
			this.txtABFilterValue.setVisible(false);
		} else {
			this.pkABFilterValue.setVisible(false);
			this.txtABFilterValue.setVisible(true);
		}
	}

	protected void comboCMFilter_itemStateChanged(ItemEvent e) throws Exception {
		this.comboCMFilterValue.removeAllItems();
		if (this.comboCMFilter.getSelectedItem().toString().equals(BIZTYPE)) {
			this.comboCMFilterValue.addItem(ChangeBizTypeEnum.QUITROOM);
			this.comboCMFilterValue.addItem(ChangeBizTypeEnum.CHANGENAME);
			this.comboCMFilterValue.addItem(ChangeBizTypeEnum.CHANGEROOM);
		}
		if (this.comboCMFilter.getSelectedItem().toString().equals(DEALSTATE)) {
			this.comboCMFilterValue.addItems(DealStateEnum.getEnumList().toArray());
		}
	}

	protected void comboPPFilter_itemStateChanged(ItemEvent e) throws Exception {
		if (this.comboPPFilter.getSelectedItem().toString().equals(BIZDATE)) {
			this.pkPPFilterValue.setVisible(true);
			this.txtPPFilterValue.setVisible(false);
		} else {
			this.pkPPFilterValue.setVisible(false);
			this.txtPPFilterValue.setVisible(true);
		}
	}

	protected void comboTPFilter_itemStateChanged(ItemEvent e) throws Exception {
		if (this.comboTPFilter.getSelectedItem().toString().equals(STATE)) {
			this.comboTPFilterValue.setVisible(true);
			this.txtTPFilterValue.setVisible(false);
		} else {
			this.comboTPFilterValue.setVisible(false);
			this.txtTPFilterValue.setVisible(true);
		}
	}

	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Room_dataChanged(e);
	}

	protected void tblPPActBill_tableSelectChanged(KDTSelectEvent e) throws Exception {
//		int activeRowIndex = this.tblPPActBill.getSelectManager().getActiveRowIndex();
//		if (activeRowIndex == -1) {
//			return;
//		}
//		IRow selectRow = this.tblPPActBill.getRow(activeRowIndex);
//		this.tblPPActAmount.removeRows();
//		IRowSet rowSet = getSHERevPaymenteEntrySqlResult(selectRow.getCell(TB_ID).getValue().toString(), null, null,null,null);
//		fillActAmount(this.tblPPActAmount, rowSet, true);
	}
	
	protected void tblEMActBill_tableSelectChanged(KDTSelectEvent e) throws Exception {
//		int activeRowIndex = this.tblEMActBill.getSelectManager().getActiveRowIndex();
//		if (activeRowIndex == -1) {
//			return;
//		}
//		IRow selectRow = this.tblEMActBill.getRow(activeRowIndex);
//		this.tblEMActAmount.removeRows();
//		IRowSet rowSet = getSHERevPaymenteEntrySqlResult(selectRow.getCell(TB_ID).getValue().toString(), null, null,null,null);
//		fillActAmount(this.tblEMActAmount, rowSet, true);
	}

	protected void tblRAActBill_tableSelectChanged(KDTSelectEvent e) throws Exception {
//		int activeRowIndex = this.tblRAActBill.getSelectManager().getActiveRowIndex();
//		if (activeRowIndex == -1) {
//			return;
//		}
//		IRow selectRow = this.tblRAActBill.getRow(activeRowIndex);
//		this.tblRAActAmount.removeRows();
//		IRowSet rowSet = getSHERevPaymenteEntrySqlResult(selectRow.getCell(TB_ID).getValue().toString(), null, null,null,null);
//		fillActAmount(this.tblRAActAmount, rowSet, true);
	}

	protected void tblEMBill_tableSelectChanged(KDTSelectEvent e) throws Exception {
		int activeRowIndex = this.tblEMBill.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			return;
		}
		IRow selectRow = this.tblEMBill.getRow(activeRowIndex);
		this.tblEMActBill.removeRows();
		this.tblEMActAmount.removeRows();
		
		IRowSet rowSet = getSHERevPaymentSqlResult(null, null, null, null, null, null, selectRow.getCell(TB_TRANID).getValue().toString(), false);
//		String id = fillActBill(this.tblEMActBill, rowSet, false);
	    fillActBill(this.tblEMActBill, rowSet, false);
//		if (rowSet != null && rowSet.size() > 0) {
//			IRowSet sheRevEntryRowSet = getSHERevPaymenteEntrySqlResult(id, null, null,null,null);
//			fillActAmount(this.tblEMActAmount, sheRevEntryRowSet, true);
//		}
	}
	
	private IRowSet getTranBusinessOverViewSqlResult(String id,RelatBizType bizType) {
		if(bizType==null) return null;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		if(RelatBizType.Sign.equals(bizType)){
			sqlBuilder.appendSql("select bo.ftanPayListEntryId ID,moneyDefine.fname_l2 MONEYDEFINE,currency.fname_l2 CURRENCY,bo.fappAmount APPAMOUNT,bo.fappDate APPDATE, ");
			sqlBuilder.appendSql("act.factRevAmount ACTAMOUNT from T_SHE_SignPayListEntry bo ");
			sqlBuilder.appendSql("left join t_she_moneyDefine moneyDefine on bo.fmoneyDefineid=moneyDefine.fid ");
			sqlBuilder.appendSql("left join t_she_TranBusinessOverView act on bo.ftanPayListEntryId=act.fid ");
			sqlBuilder.appendSql("left join t_bd_currency currency on bo.fcurrencyid=currency.fid where 1=1 ");
//			sqlBuilder.appendSql("and moneyDefine.fmoneyType in('PurchaseAmount','FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') ");
		}
		if(RelatBizType.PrePur.equals(bizType)){
			sqlBuilder.appendSql("select bo.ftanPayListEntryId ID,moneyDefine.fname_l2 MONEYDEFINE,currency.fname_l2 CURRENCY,bo.fappAmount APPAMOUNT,bo.fappDate APPDATE, ");
			sqlBuilder.appendSql("act.factRevAmount ACTAMOUNT from T_SHE_prePurchasePayListEntry bo ");
			sqlBuilder.appendSql("left join t_she_moneyDefine moneyDefine on bo.fmoneyDefineid=moneyDefine.fid ");
			sqlBuilder.appendSql("left join t_she_TranBusinessOverView act on bo.ftanPayListEntryId=act.fid ");
			sqlBuilder.appendSql("left join t_bd_currency currency on bo.fcurrencyid=currency.fid where 1=1 ");
//			sqlBuilder.appendSql("and moneyDefine.fmoneyType in('PurchaseAmount','FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') ");
		}
		if(RelatBizType.Purchase.equals(bizType)){
			sqlBuilder.appendSql("select bo.ftanPayListEntryId ID,moneyDefine.fname_l2 MONEYDEFINE,currency.fname_l2 CURRENCY,bo.fappAmount APPAMOUNT,bo.fappDate APPDATE, ");
			sqlBuilder.appendSql("act.factRevAmount ACTAMOUNT from T_SHE_purPayListEntry bo ");
			sqlBuilder.appendSql("left join t_she_moneyDefine moneyDefine on bo.fmoneyDefineid=moneyDefine.fid ");
			sqlBuilder.appendSql("left join t_she_TranBusinessOverView act on bo.ftanPayListEntryId=act.fid ");
			sqlBuilder.appendSql("left join t_bd_currency currency on bo.fcurrencyid=currency.fid where 1=1 ");
//			sqlBuilder.appendSql("and moneyDefine.fmoneyType in('PurchaseAmount','FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') ");
		}
		if (id != null) {
			sqlBuilder.appendSql("and bo.fheadid = ? ");
			sqlBuilder.addParam(id);
		} else {
			sqlBuilder.appendSql("and bo.fheadid =? ");
			sqlBuilder.addParam("null");
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return null;
	}

	private IRowSet getReceiveGatherSqlResult(String state, String number, String bank) {
//		if(state==null && number==null && bank==null) return null;
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select rg.ffivouchered FIVOUCHERED,rg.fid ID,rg.fnumber NUMBER,rg.fstate STATE,rg.fsumAmount TOTALAMOUNT, ");
		sqlBuilder
				.appendSql("settlement.fname_l2 SETTLEMENT,rg.fsettlementNumber SETTLEMENTNUMBER,bank.fname_l2 BANK,account.fname_l2 ACCOUNT,reAccount.fname_l2 REACCOUNT from T_SHE_ReceiveGather rg ");
		sqlBuilder.appendSql("left join t_bd_bank bank on rg.fbankid=bank.fid ");
		sqlBuilder.appendSql("left join T_BD_SettlementType settlement on rg.FSettlementType=settlement.fid ");
		sqlBuilder.appendSql("left join T_BD_AccountView account on rg.frevAccountid=account.fid ");
		sqlBuilder.appendSql("left join T_BD_AccountView reAccount on rg.foppAccountid=reAccount.fid where 1=1");

		if (sellProject != null) {
			sqlBuilder.appendSql("and rg.FProjectID =? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		} else {
			sqlBuilder.appendSql("and rg.FProjectID =? ");
			sqlBuilder.addParam("null");
		}
		if (state != null) {
			sqlBuilder.appendSql("and rg.fstate = ? ");
			sqlBuilder.addParam(state);
		}
		if (number != null) {
			sqlBuilder.appendSql("and rg.fnumber like ? ");
			sqlBuilder.addParam("%" + number + "%");
		}
		if (bank != null) {
			sqlBuilder.appendSql("and bank.fname_l2 like ? ");
			sqlBuilder.addParam("%" + bank + "%");
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

	private IRowSet getSHERevPaymenteEntrySqlResult(String parentid, String moneyDefineType, Date actDate,String customer,String room) {
//		if(parentid==null && moneyDefineType==null && actDate==null&&customer==null&&room==null) return null;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select room.fname_l2 ROOM,parent.fcustomerNames CUSTOMER,parent.fid HEAD,rp.fid ID,moneyDefine.fname_l2 MONEYDEFINE,moneyDefine.fmoneyType MONEYDEFINETYPE,rp.famount AMOUNT,(rp.famount+rp.frevAmount) as APPREVAMOUNT, ");
		sqlBuilder.appendSql("parent.fbizDate BIZDATE,currency.fname_l2 CURRENCY,rp.finvoiceNumber INVOICENUMBER,rp.freceiptNumber RECEIPTNUMBER,rp.frevAmount REVAMOUNT from T_BDC_SHERevBillEntry rp ");
		sqlBuilder.appendSql("left join t_she_moneyDefine moneyDefine on rp.fmoneyDefineid=moneyDefine.fid ");
		sqlBuilder.appendSql("left join t_bdc_sheRevBill parent on rp.fparentid=parent.fid ");
		sqlBuilder.appendSql("left join t_she_room room on parent.froomid=room.fid ");
		sqlBuilder.appendSql("left join t_bd_currency currency on parent.fcurrencyid=currency.fid where 1=1 ");

		if (parentid != null) {
			sqlBuilder.appendSql("and rp.fparentid = ? ");
			sqlBuilder.addParam(parentid);
		}
		if (sellProject != null) {
			sqlBuilder.appendSql("and parent.fsellProjectId =? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		} else {
			sqlBuilder.appendSql("and parent.fsellProjectId =? ");
			sqlBuilder.addParam("null");
		}
		if (moneyDefineType != null) {
			sqlBuilder.appendSql("and moneyDefine.fmoneyType=? ");
			sqlBuilder.addParam(moneyDefineType);
		}
		if (actDate != null) {
			Calendar cal=Calendar.getInstance();
	    	cal.setTime(actDate);
			sqlBuilder.appendSql(" and YEAR(parent.fbizDate) = "+cal.get(Calendar.YEAR)+" and  MONTH(parent.fbizDate) = "+(cal.get(Calendar.MONTH) + 1)+
					" and DAYOFMONTH(parent.fbizDate) = "+cal.get(Calendar.DAY_OF_MONTH));
		}
		if (room != null) {
			sqlBuilder.appendSql("and room.fname_l2 like ? ");
			sqlBuilder.addParam("%" + room + "%");
		}
		if (customer != null) {
			sqlBuilder.appendSql("and parent.fcustomerNames like ? ");
			sqlBuilder.addParam("%" + customer + "%");
		}
		sqlBuilder.appendSql("order by room.fname_l2,parent.fcustomerNames,parent.fbizDate ");
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	private IRowSet getSincerityPurchaseSqlResult(String projectNumber, String room, String customer, String person,boolean unGathering,boolean hasGathering,boolean hasRefundment,boolean hasTransfer) {
//		if(projectNumber==null && room==null && customer==null && person==null) return null;
		
		if(!unGathering&&! hasGathering&& !hasRefundment&& !hasTransfer) return null;
		
		String select="select sp.FTransactionID TRANID ,sp.fid ID,sp.fnumber NUMBER,room.fname_l2 ROOM,sp.fappointmentPeople PERSON,sp.fappointmentPhone TEL,sp.fbizDate BIZDATE, "+
			"sp.finvalidationDate invalidDate,sp.fprojectNum PROJECTNUMBER,sp.fbizstate STATE,sp.fcustomerNames CUSTOMER ,";
		String from="from T_SHE_NewSincerityPurchase sp "+
			"left join t_she_room room on sp.froomid=room.fid where ";
		String where="";
		if (sellProject != null) {
			where=where+" and sp.fsellProjectId ='"+sellProject.getId().toString()+"' ";
		} else {
			where=where+" and sp.fsellProjectId ='null' ";
		}
		if (person != null) {
			where=where+" and sp.fappointmentPeople like '%" + person + "%' ";
		}
		if (projectNumber != null) {
			where=where+" and sp.fprojectNum like '%" + projectNumber + "%' ";
		}
		if (customer != null) {
			where=where+" and sp.fcustomerNames like '%" + customer + "%' ";
		}
		if (room != null) {
			where=where+" and room.fname_l2 like '%" + room + "%' ";
		}
		
		String sellProjectWhere="";
		if (sellProject != null) {
			sellProjectWhere="and t_bdc_sheRevBill.fsellProjectId ='"+sellProject.getId().toString()+"'";
		} else {
			sellProjectWhere="and t_bdc_sheRevBill.fsellProjectId ='null'";
		}
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		if(unGathering){
			sqlBuilder.appendSql(select+"'"+UNGATHERING+"' as REVTYPE "+from);
			sqlBuilder.appendSql(" sp.fid not in(select distinct frelateBizBillId from t_bdc_sheRevBill where frelateTransId is not null ");
			sqlBuilder.appendSql(sellProjectWhere);
			sqlBuilder.appendSql(")");
			sqlBuilder.appendSql(where);
			
		}
		if(unGathering&&hasGathering){
			sqlBuilder.appendSql(" union ");
		}
		if(hasGathering){
			sqlBuilder.appendSql(select+"'"+HASGATHERING+"' as REVTYPE "+from);
			sqlBuilder.appendSql(" sp.fid in(select distinct frelateBizBillId from t_bdc_sheRevBill left join t_bdc_sheRevBillentry on t_bdc_sheRevBill.fid=t_bdc_sheRevBillentry.fparentid where t_bdc_sheRevBill.frelateTransId is not null ");
			sqlBuilder.appendSql(" and t_bdc_sheRevBillentry.frevAmount>0 and (fhasMapedAmount is null or fhasMapedAmount=0) and (fhasRefundmentAmount is null or fhasRefundmentAmount=0)");
			sqlBuilder.appendSql(sellProjectWhere);
			sqlBuilder.appendSql(")");
			sqlBuilder.appendSql(where);
		}
		if((unGathering||hasGathering)&&hasRefundment){
			sqlBuilder.appendSql(" union ");
		}
		if(hasRefundment){
			sqlBuilder.appendSql(select+"'"+HASREFUNDMENT+"' as REVTYPE "+from);
			sqlBuilder.appendSql(" sp.fid in(select distinct frelateBizBillId from t_bdc_sheRevBill left join t_bdc_sheRevBillentry on t_bdc_sheRevBill.fid=t_bdc_sheRevBillentry.fparentid where t_bdc_sheRevBill.frelateTransId is not null ");
			sqlBuilder.appendSql(" and t_bdc_sheRevBillentry.fhasRefundmentAmount>0 and (fhasMapedAmount is null or fhasMapedAmount=0)");
			sqlBuilder.appendSql(sellProjectWhere);
			sqlBuilder.appendSql(")");
			sqlBuilder.appendSql(where);
		}
		if((unGathering||hasGathering||hasRefundment)&&hasTransfer){
			sqlBuilder.appendSql(" union ");
		}
		if(hasTransfer){
			sqlBuilder.appendSql(select+"'"+HASTRANSFER+"' as REVTYPE "+from);
			sqlBuilder.appendSql(" sp.fid in(select distinct frelateBizBillId from t_bdc_sheRevBill left join t_bdc_sheRevBillentry on t_bdc_sheRevBill.fid=t_bdc_sheRevBillentry.fparentid where t_bdc_sheRevBill.frelateTransId is not null ");
			sqlBuilder.appendSql(" and t_bdc_sheRevBillentry.fhasMapedAmount>0 and (fhasRefundmentAmount is null or fhasRefundmentAmount=0)");
			sqlBuilder.appendSql(sellProjectWhere);
			sqlBuilder.appendSql(")");
			sqlBuilder.appendSql(where);
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

	private IRowSet getSHERevPaymentSqlResult(String room, String customer, String payPerson, Date bizDate,
			String number, RevBillTypeEnum revBillType, String relateTransId, boolean isPre) {
//		if(room==null && customer==null && payPerson==null && bizDate==null && number==null&&relateTransId==null) return null;
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select rp.fid ID,rp.fnumber NUMBER,rp.frevBillType BIZTYPE,rp.fstate STATE,rp.fpayCustomerName PAYPERSON, ");
		sqlBuilder.appendSql("room.fname_l2 ROOM,rp.fcustomerNames CUSTOMER,rp.fbizDate BIZDATE,rp.frevAmount REVAMOUNT,currency.fname_l2 CURRENCY, ");
		sqlBuilder.appendSql("rp.fcreateTime CREATEDATE,creator.fname_l2 CREATOR,rp.fauditTime AUDITDATE,auditor.fname_l2 AUDITOR,rp.fOriginalAmount as APPREVAMOUNT,(rp.fOriginalAmount-rp.frevAmount) as AMOUNT from t_bdc_sheRevBill rp ");
		sqlBuilder.appendSql("left join t_pm_user creator on rp.fcreatorid=creator.fid ");
		sqlBuilder.appendSql("left join t_pm_user auditor on rp.fauditorid=auditor.fid ");
		sqlBuilder.appendSql("left join t_bd_currency currency on rp.fcurrencyid=currency.fid ");
		sqlBuilder.appendSql("left join t_she_room room on rp.froomid=room.fid where 1=1 ");
		if (sellProject != null) {
			sqlBuilder.appendSql("and rp.fsellProjectId =? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		} else {
			sqlBuilder.appendSql("and rp.fsellProjectId =? ");
			sqlBuilder.addParam("null");
		}
		if (room != null) {
			sqlBuilder.appendSql("and room.fname_l2 like ? ");
			sqlBuilder.addParam("%" + room + "%");
		}
		if (customer != null) {
			sqlBuilder.appendSql("and rp.fcustomerNames like ? ");
			sqlBuilder.addParam("%" + customer + "%");
		}
		if (payPerson != null) {
			sqlBuilder.appendSql("and rp.fpayCustomerName like ? ");
			sqlBuilder.addParam("%" + payPerson + "%");
		}
		if (bizDate != null) {
			Calendar cal=Calendar.getInstance();
	    	cal.setTime(bizDate);
			sqlBuilder.appendSql(" and YEAR(rp.fbizDate) = "+cal.get(Calendar.YEAR)+" and  MONTH(rp.fbizDate) = "+(cal.get(Calendar.MONTH) + 1)+
					" and DAYOFMONTH(rp.fbizDate) = "+cal.get(Calendar.DAY_OF_MONTH));
		}
		if (number != null) {
			sqlBuilder.appendSql("and rp.fnumber like ? ");
			sqlBuilder.addParam("%" + number + "%");
		}
		if (revBillType != null) {
			sqlBuilder.appendSql("and rp.frevBillType = ? ");
			sqlBuilder.addParam(revBillType.getValue());
		}
		if (isPre) {
			sqlBuilder.appendSql("and rp.frelateTransId is null ");
		} else {
			if (relateTransId != null) {
				sqlBuilder.appendSql("and rp.frelateTransId = ? ");
				sqlBuilder.addParam(relateTransId);
			}
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

	private IRowSet getBankPaymentSqlResult(String room, String customer, String bank, String number) {
//		if(room==null&&customer==null&&bank==null&&number==null) return null;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select distinct bp.fid ID,bp.fnumber NUMBER,bp.fstate STATE,bp.fpaymentDate BIZDATE,bp.fpaymentAmout AMOUNT,bank.fname_l2 BANK, ");
		sqlBuilder.appendSql("bp.fcreateTime CREATEDATE,creator.fname_l2 CREATOR,bp.fauditTime AUDITDATE,auditor.fname_l2 AUDITOR from t_she_bankpayment bp ");
		sqlBuilder.appendSql("left join t_pm_user creator on bp.fcreatorid=creator.fid ");
		sqlBuilder.appendSql("left join t_pm_user auditor on bp.fauditorid=auditor.fid ");
		sqlBuilder.appendSql("left join t_bd_bank bank on bp.fpaymentBankid=bank.fid ");
		sqlBuilder.appendSql("left join t_she_bankpaymententry entry on bp.fid=entry.fpaymentid ");
		sqlBuilder.appendSql("left join t_she_room room on entry.froomid=room.fid where 1=1 ");

		if (sellProject != null) {
			sqlBuilder.appendSql("and bp.fprojectid =? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		} else {
			sqlBuilder.appendSql("and bp.fprojectid=? ");
			sqlBuilder.addParam("null");
		}
		if (room != null) {
			sqlBuilder.appendSql("and room.fname_l2 like ? ");
			sqlBuilder.addParam("%" + room + "%");
		}
		if (customer != null) {
			sqlBuilder.appendSql("and entry.fcustomerDisName like ? ");
			sqlBuilder.addParam("%" + customer + "%");
		}
		if (bank != null) {
			sqlBuilder.appendSql("and bank.fname_l2 like ? ");
			sqlBuilder.addParam("%" + bank + "%");
		}
		if (number != null) {
			sqlBuilder.appendSql("and bp.fnumber like ? ");
			sqlBuilder.addParam("%" + number + "%");
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

	private IRowSet getChangeMangaeSqlResult(String bizType, String dealState) {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select cm.fid ID,cm.fnumber NUMBER,cm.fdealState DEALSTATE,cm.fchangeDate BIZDATE,cm.fbizType BIZTYPE, ");
		sqlBuilder.appendSql("srcroom.fname_l2 SRCROOM,cm.fsrcCustomerNames SRCCUSTOMER,room.fname_l2 ROOM,fcustomerNames CUSTOMER,handler.fname_l2 HANDLER from t_she_changeManage cm ");
		sqlBuilder.appendSql("left join t_pm_user handler on cm.fhandlerid=handler.fid ");
		sqlBuilder.appendSql("left join t_she_room room on cm.froomid=room.fid ");
		sqlBuilder.appendSql("left join t_she_room srcRoom on cm.fsrcRoomid=srcRoom.fid where fstate='4AUDITTED' ");

		if (bizType != null) {
			sqlBuilder.appendSql("and cm.fbizType=? ");
			sqlBuilder.addParam(bizType);
		}
		if (dealState != null) {
			sqlBuilder.appendSql("and cm.fdealState=? ");
			sqlBuilder.addParam(dealState);
		}
		if (sellProject != null) {
			sqlBuilder.appendSql("and cm.fsellProjectid=? ");
			sqlBuilder.addParam(sellProject.getId().toString());
		} else {
			sqlBuilder.appendSql("and cm.fsellProjectid=? ");
			sqlBuilder.addParam("null");
		}
		try {
			IRowSet rs = sqlBuilder.executeQuery();
			return rs;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void labCustomer1_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer1.getUserObject());
		}
	}

	protected void labCustomer2_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer2.getUserObject());
		}
	}

	protected void labCustomer3_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer3.getUserObject());
		}
	}
	
	protected void labCustomer4_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer4.getUserObject());
		}
	}

	protected void labCustomer5_mouseClicked(MouseEvent e) throws Exception {
		if(e.getClickCount()==2){
			SHEManageHelper.openCustomerInformation(this,(SHECustomerInfo)labCustomer5.getUserObject());
		}
	}

	private void openUI(KDTable table,String key,String UI,KDTMouseEvent e) throws UIException{
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = table.getRow(e.getRowIndex());
			if(row.getCell(key).getValue()==null) return;
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell(key).getValue().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(UI, uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	protected void tblAAActAmount_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblAAActAmount,TB_HEAD,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblABActBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblABActBill,TB_ID,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblBankAmountBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblBankAmountBill,TB_ID,BankPaymentEditUI.class.getName(),e);
	}
	protected void tblEMActAmount_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblEMActAmount,TB_HEAD,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblEMActBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblEMActBill,TB_ID,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblEMBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblEMBill,TB_ID,SincerityPurchaseChangeNameUI.class.getName(),e);
	}
	protected void tblCMBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblCMBill,TB_ID,ChangeManageEditUI.class.getName(),e);
	}
	protected void tblPPActAmount_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblPPActAmount,TB_HEAD,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblPPActBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblPPActBill,TB_ID,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblRAActAmount_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblRAActAmount,TB_HEAD,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblRAActBill_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblRAActBill,TB_ID,NewSHERevBillEditUI.class.getName(),e);
	}
	protected void tblRevGather_tableClicked(KDTMouseEvent e) throws Exception {
		openUI(this.tblRevGather,TB_ID,ReceiveGatherEditUI.class.getName(),e);
	}
	
}