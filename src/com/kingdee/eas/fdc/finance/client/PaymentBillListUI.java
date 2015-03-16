/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.AbstractContractWithoutTextListUI;
import com.kingdee.eas.fdc.contract.client.AbstractSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.PaymentSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.SplitInvokeStrategyFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitHelper;
import com.kingdee.eas.fi.cas.BgCtrlPaymentBillHandler;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBill2BankPayBillBuilder;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.RecPayException;
import com.kingdee.eas.fi.cas.RecPayHelper;
import com.kingdee.eas.fi.cas.client.CasRecPayHandler;
import com.kingdee.eas.fi.cas.client.RecPayHandler;
import com.kingdee.eas.fm.be.BEHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.fm.common.client.MutexUtils;
import com.kingdee.eas.fm.nt.BillStateEnum;
import com.kingdee.eas.fm.nt.NTTypeGroupEnum;
import com.kingdee.eas.fm.nt.PayableBillInfo;
import com.kingdee.eas.fm.nt.ReceivableBillCollection;
import com.kingdee.eas.fm.nt.ReceivableBillFactory;
import com.kingdee.eas.fm.nt.ReceivableBillInfo;
import com.kingdee.eas.fm.nt.client.ChequeAssociateUI;
import com.kingdee.eas.fm.nt.client.OutEndorsementBillEditUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.IBgCtrlHandler;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PaymentBillListUI extends AbstractPaymentBillListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentBillListUI.class);
	//付款单提交、审批时，是否检查合同未完全拆分
	private boolean checkAllSplit = true;
	//生成凭证时是否校验拆分
	private boolean isPaymentSplit = true;
	//校验付款是否完全拆分
	private boolean checkPaymentAllSplit = true;
	//简单模式处理发票
	private boolean isSimpleInvoice = false;
	//简单一体化 
	private boolean isSimpleFinancial = false;
	//调整模式
	private boolean isAdjustVourcherModel = false;
	private boolean isFinancial = false;
	private boolean isSimpleFinancialExtend =true;
	private boolean isSeparate = false;
    
	/**
	 * output class constructor
	 */
	public PaymentBillListUI() throws Exception {
		super();
	}

	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		showPayreq();
//		super.actionTraceUp_actionPerformed(e);
	}
	
	private void showPayreq() throws Exception {
		Map uiContext = new HashMap();
		uiContext.put(UIContext.OWNER, this);
		String billID = getSelectedKeyValue(); // 获取ID
		FilterInfo myFilter = new FilterInfo();
		myFilter.getFilterItems().add(new FilterItemInfo("id", "select ffdcpayreqid from t_cas_paymentbill where fid='"+billID+"'", CompareType.INNER));
		uiContext.put("MyFilter", myFilter); // 注意，这里的billID是44位长的BOSUuid，类型是String
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.contract.client.PayRequestFullListUI",
				uiContext, null);
		if (window instanceof UIModelDialog) {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			// ((JPanel)((UIModelDialog)window).getContentPane()).setSize(screenDim);
			int width = 1013;
			int height = 629;
			Dimension maxSize = new Dimension(1013, 629);
			if (screenDim.width > maxSize.getWidth()
					|| screenDim.height > maxSize.getHeight()) {
				screenDim = maxSize;
			}
			JPanel panel = ((JPanel) ((UIModelDialog) window).getContentPane());
			ListUI fullUI = (ListUI) window.getUIObject();
			// fullUI.setSize(new Dimension(690,300));
			fullUI.getMainTable().setBounds(
					new Rectangle(10, 10, width - 25, height - 50));
			fullUI.getMainTable().getColumn("id").getStyleAttributes()
					.setHided(true);
			KDTabbedPane tabPane = new KDTabbedPane();
			tabPane.addTab(PaymentBillClientUtils.getRes("payReqBill"), fullUI);
			panel.add(tabPane);
			panel.setPreferredSize(screenDim);
		} else {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension maxSize = new Dimension(1013, 629);
			screenDim.setSize(maxSize);
			JPanel panel = ((JPanel) ((KDFrame) window).getContentPane());
			ListUI fullUI = (ListUI) window.getUIObject();
			fullUI.getMainTable().getColumn("id").getStyleAttributes()
					.setHided(true);
			KDTabbedPane tabPane = new KDTabbedPane();
			JPanel panel2 = new KDPanel();
			panel2.setLayout(new BorderLayout());
			panel2.add(fullUI, BorderLayout.CENTER);
			tabPane.addTab(PaymentBillClientUtils.getRes("payReqBill"), panel2);
			panel.setLayout(new BorderLayout());
			panel.add(tabPane, BorderLayout.CENTER);
			panel.setPreferredSize(screenDim);
		}
		window.show();
	}
	/**
	 * 快速模糊定位
	 * 
	 * @author ling_peng  
	 */
	protected String[] getLocateNames() {
		String[] locateNames = new String[5];
		locateNames[0] = "number";
		locateNames[1] = "contractName";
		locateNames[2] = "partB.name";
		locateNames[3] = "contractType.name";
		locateNames[4] = "signDate";
		return locateNames;
		
	}

    /**
     * 单据转换时获取选中的单据对象，该方法会中先获取选中的单据的ID，然后调用filterBillListForBTP来获取单据并过滤
     */
    protected CoreBillBaseCollection getNewBillList() throws Exception
    {
    	return super.getBillList();
//        checkSelected();
//
//        ArrayList idList = new ArrayList();
//        List entriesKey = new ArrayList();
//        getBillIdList(idList,entriesKey);
//
//        CoreBillBaseCollection sourceBillCollection = filterBillListForBTP(idList);
//
//         //删除集合中非选中的分录ID
//         if(entriesKey.size()>0)
//         {
//              removeUnSelect(entriesKey, sourceBillCollection);
//         }
//         return sourceBillCollection;
    }
    
    private boolean isDAPTrans = false; 
    private void removeUnSelect(List entriesKey ,
                                CoreBillBaseCollection sourceBillCollection)
    {
        //如果是DAP,则不删除分录。
        if(isDAPTrans)
        {
            return;
        }
        //删除集合中非选中的分录ID
        for (int i =0 ;i <sourceBillCollection.size() ;i ++)
        {
            CoreBillBaseInfo bills = sourceBillCollection.get(i);

            boolean isHasSelect = false;
            IObjectCollection entries = (IObjectCollection) bills.get(this.getEntriesName());

            if(entries == null)
            {
                return;
            }

            Iterator iters = entries.iterator();
            int unSelect = 0;
            int count = 0;
            int ii = entries.size();
            while (iters.hasNext())
            {
                CoreBaseInfo cInfo = (CoreBaseInfo) iters.next();
                String ss = cInfo.getId().toString();
                isHasSelect = false;
                for (int k = 0; k < entriesKey.size(); k++)
                {
                    if (cInfo.get("id").toString().
                        equals(entriesKey.get(k).toString()))
                    {
                        isHasSelect = true;
                        break;
                    }
                }
                if (!isHasSelect)
                {
                    iters.remove();
                    //entries.removeObject();
                }
                //count++;
            }
        }
    }

    
    /**
     * 获取当前表格选取的单据id和分录id
     * @return
     * @throws Exception
     */
    public void getBillIdList(List idList,List entriesList){
    	
    	KDTable table = getBillListTable();
        int mode=0;
        List blockList=table.getSelectManager().getBlocks();
        //判断是整表选取还是分块选取
        if (blockList!=null&&blockList.size()==1)
        {
           mode=((IBlock)table.getSelectManager().getBlocks().get(0)).getMode();
        }
        if (mode==KDTSelectManager.TABLE_SELECT){//表选择
            List selectIdList=this.getQueryPkList();
            if (selectIdList!=null){
                Iterator lt=selectIdList.iterator();
                while (lt.hasNext())
                {
                    Object[] idObj=(Object[])lt.next();
                    if (idObj==null)
                        continue;
                    if (!idList.contains(idObj[0].toString()))
                        idList.add(idObj[0].toString());
                    if (idObj.length==2)
                        entriesList.add(idObj[1]);
                }
            }
        }else{
            ArrayList blocks = table.getSelectManager().getBlocks();
            Iterator iter = blocks.iterator();

            while (iter.hasNext())
            {
                KDTSelectBlock block = (KDTSelectBlock) iter.next();
                int top = block.getTop();
                int bottom = block.getBottom();

                for (int rowIndex = top; rowIndex <= bottom; rowIndex++)
                {
                    ICell cell = table.getRow(rowIndex).getCell(getKeyFieldName());

                    //记录选中的分录ID
                    if(table.getRow(rowIndex).getCell(this.getEntriesPKName()) != null
                            && table.getRow(rowIndex).getCell(this.
                                    getEntriesPKName()).getValue() != null)
                    {
                        entriesList.add(table.getRow(rowIndex).getCell(this.
                            getEntriesPKName()).getValue().toString());
                    }

                    if (!idList.contains(cell.getValue()))
                    {
                        idList.add(cell.getValue());
                    }
                }
            }
        }
    }

	/*
	 * 如果组织为成本中心，则不能打开付款单编辑界面 （非 Javadoc）
	 * 
	 * @see com.kingdee.bos.ui.face.IUIObject#onLoad() @author: zhonghui_luo
	 *      2006-10-13 15:10:35
	 */
	public void onLoad() throws Exception {
		
		
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}
		//付款单提交时，是否检查合同拆分
		checkAllSplit = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		super.onLoad();
		getBillListTable().removeColumn(getBillListTable().getColumnIndex("bookedDate"));
		getBillListTable().setColumnMoveable(true);
		tblMain.setColumnMoveable(true);
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
		actionTraceUp.setVisible(true);
		actionTraceUp.setEnabled(true);
		/*售前临时内容
		int fiVoucher=this.getBillListTable().getColumnIndex("fiVouchered");
		IColumn col = this.getBillListTable().addColumn(fiVoucher+1);
		col.setKey("voucherType");
		col = this.getBillListTable().addColumn(fiVoucher+2);
		col.setKey("voucherNumber");
		IRow headRow=getBillListTable().getHeadRow(0);
		headRow.getCell("voucherType").setValue("凭证字");
		headRow.getCell("voucherNumber").setValue("凭证号");
		// this.getUIContext();
		 
		 */
		
		//获得当前组织
		if(orgUnit==null){
			orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		if(company==null){
			company = SysContext.getSysContext().getCurrentFIUnit();
		}
		Map fiParam = FDCUtils.getDefaultFDCParam(null, company.getId().toString());
		isPaymentSplit = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
		
		isSimpleInvoice = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
		isSimpleFinancial = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		isAdjustVourcherModel = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		isSimpleFinancialExtend = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		isFinancial = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_FINACIAL);
		isSeparate = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		
		Map param = FDCUtils.getDefaultFDCParam(null, orgUnit.getId().toString());
		checkPaymentAllSplit = isPaymentSplit&&FDCUtils.getParamValue(param, FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT);
		if(!checkPaymentAllSplit || !isPaymentSplit){
			actionSplit.setVisible(false);
			actionSplit.setEnabled(false);
			
		}
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
	}

	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnPaymentPlan.setIcon(EASResource.getIcon("imgTbtn_showdata"));
		btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_showdata"));
		actionPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_payment"));
		// btnPay.setIcon(EASResource.getIcon("imgTbtn_showdata"));
		this.toolBar.add(btnPaymentPlan);
		this.btnPaymentPlan.setVisible(false);
		this.btnProjectAttachment.setVisible(false);
		actionPay.setEnabled(true);
	}
	public boolean checkHasPaymentSplit() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		if(idList.size()==0){
			return false;
		}
		Set idSet = ContractClientUtils.listToSet(idList);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("paymentBill.id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = PaymentSplitFactory.getRemoteInstance()
				.getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		CoreBillBaseCollection coll2 = PaymentNoCostSplitFactory.getRemoteInstance()
			.getCoreBillBaseCollection(view);
		Iterator iter2 = coll2.iterator();
		if (iter.hasNext() || iter2.hasNext()) {
			return true;
		}
		else
			return false;
	}

	protected boolean checkBeforeRemove() throws Exception {
		if(!super.checkBeforeRemove()){
			return false;
		}
		
		
		if (checkHasPaymentSplit()) {
			if(checkPaymentAllSplit){
				MsgBox.showWarning(this, "单据已拆分，不能执行此操作!");
			}else{
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
			}
			SysUtil.abort();
		}
		
		return true;
	}
	
	/**
	 * 合同最终结算后，提交、审批时，比较已审批的付款单合计金额加上本次付款单金额大于合同结算价的大小
	 * by Cassiel_peng  2009-08-12		<p>
	 * 逻辑发生更改,如果合同实付款(不含本次) 加 本次付款单合同内工程款本期发生大于合同结算价，则给出相应提示
	 * by Cassiel_peng  2009-12-03 		<p>
	 */
	private void checkAmt() throws Exception{

		checkSelected(this.tblPaymentBill);
		
		// 获取用户选择的块
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set paymentSet=new HashSet();
		for (int i = 0; i < idList.size(); i++) {
			String paymentBillId=idList.get(i).toString();
			PaymentBillInfo paymentBillInfo=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBillId)));
			paymentSet.add(paymentBillInfo);
		}
		
		boolean isCanPass=PaymentBillClientUtils.checkForSaveSubmitAudit(paymentSet);
		if(!isCanPass){
			MsgBox.showError("合同实付款不能大于合同结算价【合同实付款 =已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计】");
			SysUtil.abort();
		}
	}
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// KDTable bak=getMainTable();
		// tblMain=getBillListTable();
		checkContractSplitState();
		super.actionAddNew_actionPerformed(e);
		// tblMain=bak;
	}

//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		// 获取用户选择的块
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try {

			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);
			
			//审批时，更新单据的期间和申请期间       Add by zhiyuan_tang 2010-01-12
			FDCPaymentBillFactory.getRemoteInstance().updatePeriodAfterAudit(idList);
			
			//checkPaymentAllSplit启用则已拆分不需要提示弹出
			//isPaymentSplit购买成本才提示
			if(!checkPaymentAllSplit&&isPaymentSplit&&FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SPLITAFTERAUDIT)){
				if(idList.size()>1){
					FDCMsgBox.showInfo(this, "多条单据审批通过，请立即进行拆分！");
				}else if(idList.size()==1){
					AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory.createSplitStrategyByParam((String)idList.get(0), this);
					strategy.invokeSplit();
				}
			}

		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}

	}
	private void checkPaymentAllSplit() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Map param = new HashMap();
		param.put("isSimpleInvoice", Boolean.valueOf(isSimpleInvoice));
		param.put("isSimpleFinancial", Boolean.valueOf(isSimpleFinancial));
		param.put("isSimpleFinancialExtend", Boolean.valueOf(isSimpleFinancialExtend));
		param.put("isFinancial", Boolean.valueOf(isFinancial));
		param.put("isSeparate", Boolean.valueOf(isSeparate));
		for(int i=0;i<idList.size();i++){
			String billId = (String)idList.get(i);
			String msg = PaymentClientUtils.checkPaymentSplitState(billId,param);
			if(msg.length()!=0){
				FDCMsgBox.showWarning(this, msg);
				this.abort();
			}
		}
	}
	/*
	 * 覆盖父类的编辑方法。响应编辑事件，调出付款单编辑界面
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}		
		
	protected String getEditUIName() {
		return PaymentBillEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		// return ContractBillFactory.getRemoteInstance();
		return PaymentBillFactory.getRemoteInstance();
	}

	protected ICoreBase getPayBizInterface() throws Exception {
		return PaymentBillFactory.getRemoteInstance();
	}

	protected String getKeyFieldName() {
		return "id";
	}

	// 获取单据列表
	protected KDTable getBillListTable() {
		return tblPaymentBill;
	}

	// 重载initTable来实现对表格的冻结操作,在基类中表格默认冻结
	protected void initTable() {
		super.initTable();

		FDCHelper.formatTableNumber(getBillListTable(),
				PaymentBillContants.COL_EXCHANGERATE);
		FDCHelper.formatTableNumber(getBillListTable(),
				PaymentBillContants.COL_LOCALAMT);
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return PaymentBillFactory.getRemoteInstance();
	}

	/*
	 * 根据合同显示对应的付款单列表 （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#displayBillByContract(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 *      @author: zhonghui_luo 2006-9-8 13:46:58
	 */
	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
//		合计行本币
		BigDecimal localAmtAll = FDCHelper.ZERO; 
		
		getBillListTable().getColumn("period").getStyleAttributes().setHided(true);
		 getBillListTable().getColumn(PaymentBillContants.COL_EXCHANGERATE).getStyleAttributes().setHided(true);
		 
		PaymentBillCollection paymentBillCollection = PaymentBillFactory
				.getRemoteInstance().getPaymentBillCollection(view);
		
		//通过ID获取房地产付款单中间表的isRespite字段
		//TODO
		HashSet idSet = new HashSet();
		HashMap isRespiteMap = null;
		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			if (element.getContractBillId() != null) {
				idSet.add(element.getId().toString());
			}
		}
		
		try {
			isRespiteMap = FDCPaymentBillFactory.getRemoteInstance().getIsRespiteByPaymentBillIds(idSet);
		} catch (EASBizException e) {
			logger.error("根据付款单ID集合获取isRespite字段失败：" + e.getMessage(), e);
		}
		
		if (isRespiteMap == null) {
			isRespiteMap = new HashMap();
		}
		
		
		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			if (element.getContractBillId() != null) {
				IRow row = getBillListTable().addRow();
				//id
				row.getCell(PaymentBillContants.COL_ID).setValue(element.getId().toString());
				//billStatus
				row.getCell(PaymentBillContants.COL_STATE).setValue(element.getBillStatus());
				row.getCell("settlementStatus").setValue(element.getSettlementStatus());
				row.getCell("fiVouchered").setValue(new Boolean(element.isFiVouchered()));
				/*
				row.getCell("voucherNumber").setValue(element.getVoucherNumber());
				row.getCell("voucherType").setValue(element.getVoucherType().getName());
				*/
				if(element.getFdcPayType()!=null){
//					row.getCell("paymentType").setValue(new Boolean(element.getFdcPayType().getName()));
					//改为显示名字  by hpw 2009-07-17
					row.getCell("paymentType").setValue(element.getFdcPayType().getName());
				}
				
				row.getCell(PaymentBillContants.COL_NUMBER).setValue(element.getNumber());
				if (element.getFdcPayReqNumber() != null)
					row.getCell(PaymentBillContants.COL_REQNUM).setValue(
							element.getFdcPayReqNumber().toString());
				row.getCell(PaymentBillContants.COL_CURRENCY).setValue(
						element.getCurrency());
				
				int pre = element.getCurrency().getPrecision();		
				String curFormat = FDCClientHelper.getNumberFormat(pre,true);
				row.getCell(PaymentBillContants.COL_AMOUNT).getStyleAttributes().setNumberFormat(curFormat);
				
				row.getCell(PaymentBillContants.COL_AMOUNT).setValue(
						element.getAmount());
				row.getCell(PaymentBillContants.COL_EXCHANGERATE).setValue(
						element.getExchangeRate());
				row.getCell(PaymentBillContants.COL_LOCALAMT).setValue(
						element.getLocalAmt());
				row.getCell(PaymentBillContants.COL_PAYDATE).setValue(
						element.getPayDate());
				row.getCell(PaymentBillContants.COL_PAYEENAME).setValue(
						element.getFdcPayeeName());// .getName());
				row.getCell(PaymentBillContants.COL_ACTPAYEENAME).setValue(
						element.getActFdcPayeeName());
				if (element.getCreator() != null) {
					row.getCell(PaymentBillContants.COL_CREATER).setValue(
							element.getCreator().getName());// .getCreator());
				}
				row.getCell(PaymentBillContants.COL_CREATETIME).setValue(
						element.getCreateTime());
				row.getCell(PaymentBillContants.COL_SUMMARY).setValue(
						element.getUsage());
				row.getCell("descritpion").setValue(
						element.getDescription());
				
				row.getCell(PaymentBillContants.COL_PAYEEBANK).setValue(
						element.getPayeeBank());
				row.getCell(PaymentBillContants.COL_PAYEEACCOUNTBANK).setValue(
						element.getPayeeAccountBank());
				if (element.getAuditor() != null)
					row.getCell(PaymentBillContants.COL_AUDITOR).setValue(
							element.getAuditor().getName());
				row.getCell(PaymentBillContants.COL_AUDITDATE).setValue(
						element.getAuditDate());
				row.getCell("contractId").setValue(element.getContractBillId());
				
				row.getCell(PaymentBillContants.COL_ISRESPITE).setValue((Boolean)isRespiteMap.get(element.getId().toString()));
//				求合计行本币值
				localAmtAll = FDCHelper.add(localAmtAll, element.getLocalAmt());
			}
		}
		KDTFootManager footRowManager = tblPaymentBill.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			footRowManager = new KDTFootManager(tblPaymentBill);
			footRowManager.addFootView();
			tblPaymentBill.setFootManager(footRowManager);
			footRow = tblPaymentBill.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			tblPaymentBill.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			tblPaymentBill.getIndexColumn().setWidth(60);
			footRow.getCell(PaymentBillContants.COL_LOCALAMT).setValue(localAmtAll);
			footRow.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			footRowManager.addIndexText(0, "合计");
		}else{
			footRow=tblPaymentBill.getFootRow(0);
			footRow.getCell(PaymentBillContants.COL_LOCALAMT).setValue(localAmtAll);
		}
	}

	// 描述：重载采用KDLayout布局的容器的"OriginalBounds"客户属性。KDLayout设计思想采用了绝对布局方式，没有考虑Java中存在相对布局的情况。
	public void initUIContentLayout() {
		super.initUIContentLayout();
		if (getBillListTable().getColumn(PaymentBillContants.COL_AMOUNT) != null) {
			// 设置金额的格式
			getBillListTable().getColumn(PaymentBillContants.COL_AMOUNT)
					.getStyleAttributes().setNumberFormat("###,##0.00");
		}
	}

	/*
	 * 描述：生成获取单据的Selector
	 */
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		
		//contractBillId
		selectors.add("contractBillId");
		selectors.add("billStatus");
		selectors.add("number");
		selectors.add("fdcPayReqNumber");
		selectors.add("settlementStatus");
		selectors.add("fiVouchered");
		selectors.add("amount");
		selectors.add("exchangeRate");
		selectors.add("payDate");
		selectors.add("localAmt");
		selectors.add("createTime");
		selectors.add("summary");
		selectors.add("usage");
		selectors.add("description");
		selectors.add("payeeBank");
		selectors.add("payeeAccountBank");
		selectors.add("auditDate");
		//fdcPayType
		selectors.add("fdcPayType.name");
		selectors.add("creator.name");
		selectors.add("auditor.number");
		selectors.add("auditor.name");
		selectors.add("fdcPayeeName.name");
		selectors.add("actFdcPayeeName.name");
		//怎么会有个.name? 屏蔽掉
//		selectors.add(".name");
		selectors.add("currency.name");
		selectors.add("currency.precision");
		/*售前临时内容
		selectors.add("voucherNumber");
		selectors.add("voucherType.name");
		*/
		return selectors;
	}

	/*
	 * 冻结付款单的需冻结列
	 */
	protected void freezeBillTableColumn() {
		super.freezeBillTableColumn();
		KDTable billListTable = getBillListTable();
		// 币别
		int payreqbillnum_col_index = billListTable.getColumn("currency")
				.getColumnIndex();
		KDTViewManager viewManager = billListTable.getViewManager();
		viewManager.setFreezeView(-1, payreqbillnum_col_index);
		billListTable = null;
		viewManager = null;
	}

	protected void audit(List ids) throws Exception {
		
		checkAmt();
		
		//付款单提交、审批时，是否检查合同未完全拆分
		if(checkAllSplit){
			checkContractSplitState();			
		}
		
		if(checkPaymentAllSplit){
			checkPaymentAllSplit();
		}
		PaymentBillFactory.getRemoteInstance().audit4FDC(ids);
	}
	
	private void checkContractSplitState(){
		KDTable kdtContract = getMainTable();
		int selectRows[] = KDTableUtil.getSelectedRows(kdtContract);
		if (selectRows.length == 1) {
			String contractId = kdtContract.getCell(selectRows[0],"id").getValue().toString();
			if (contractId != null) {
				if(!ContractClientUtils.getContractSplitState(contractId)){
					MsgBox.showWarning(this, FDCSplitClientHelper
							.getRes("conNotSplited"));
					SysUtil.abort();
				}
			}
		}
	}
	
	protected void unAudit(List ids) throws Exception {
		Set set4FDC = new HashSet();
		Set set4STD = new HashSet();		
		
		for (Iterator it = ids.iterator(); it.hasNext();) {
			String paymentBillId = it.next().toString();
			PaymentBillInfo paymentBill = PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(paymentBillId));
			
			// 如果不需要付款，且为付款状态， 调用FDC的反审批：要实现已付款状态的单据也能反审批 By Owen_wen 2011-02-21
			if (BillStatusEnum.PAYED.equals(paymentBill.getBillStatus()) && !paymentBill.isIsNeedPay()) {
				set4FDC.add(paymentBillId);
			} else { // 调用标准财务接口，才能与标准财务一致
				set4STD.add(paymentBillId);
			}
		}		
		
		if (set4FDC.size() > 0)
			PaymentBillFactory.getRemoteInstance().antiAudit4FDC(FMHelper.set2List(set4FDC));
		
		// 调用标准财务接口,才能与标准财务一致，里面有房地产接口 by hpw 2011.1.20
		if (set4STD.size() > 0)
			PaymentBillFactory.getRemoteInstance().antiAudit(set4STD);
	}

	protected String getStateForAudit() {
		return BillStatusEnum.SUBMIT_VALUE + "";
	}

	protected String getStateForUnAudit() {
		return BillStatusEnum.AUDITED_VALUE + "";
	}

	/*
	 * 显示已审批的合同 （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getTreeSelectChangeFilter()
	 *      @author: zhonghui_luo 2006-9-8 13:50:21
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		/*
		 * FilterInfo filter = new FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("state", ContractStateEnum.AUDITTED_VALUE)); return
		 * filter;
		 */
		return super.getTreeSelectChangeFilter();
	}

	/**
	 * 
	 * 描述：生成查询单据的EntityViewInfo
	 * 
	 * @param e
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected EntityViewInfo genBillQueryView(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
		int top = selectBlock.getTop();
		String contractId = (String) getMainTable().getCell(top,
				getKeyFieldName()).getValue();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractId));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
		view.getSorter().add(new SorterItemInfo("number"));
		
		SelectorItemCollection selectors = genBillQuerySelector();
		if (selectors != null && selectors.size() > 0) {
			for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);

			}
		}
		return view;
	}

	/*
	 * 单据状态属性名称 （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.ContractListBaseUI#getBillStatePropertyName()
	 *      @author: zhonghui_luo 2006-9-8 13:51:50
	 */
	protected String getBillStatePropertyName() {
		return "billStatus";
	}

	protected void updateButtonStatus() {
		// super.updateButtonStatus();
		// 如果是虚体财务组织，则不能增、删、改
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				|| (!SysContext.getSysContext().getCurrentFIUnit()
						.isIsBizUnit())) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionAttachment.setEnabled(false);
			menuEdit.setVisible(false);
			// actionAudit.setEnabled(false);
			// actionUnAudit.setEnabled(false);
		}
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
	}

	/**
	 * 需要重写父类的方法，因为父类的方法用的是FDCBillStateEnum，在这里不适用。
	 * @Modified By Owen_wen 2010-11-22
	 */
	protected String[] getBillStateForEditOrRemove() {
		return new String[] { BillStatusEnum.SAVE_VALUE + "",
				BillStatusEnum.SUBMIT_VALUE + "" };
	}

	/*
	 * protected void checkBeforeRemove() throws Exception{ checkSelected(); }
	 */
	protected void selectFirstRow() {
		if (!isSelectForTable()) {
			// 选中第一行
			if (tblPaymentBill.getRow(0) != null && tblMain.getRow(0) != null) {
				tblPaymentBill.getSelectManager().select(0, 0);
				tblMain.getSelectManager().select(0, 0);
			}
		}
	}

	/**
	 * 批量删除
	 * 
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void removeBill() throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];
		Set arrayPKSet = new HashSet();
		
		boolean canRemove = true ;
		//网络控制
        try
        {
			for (int i = 0; i < selectRows.length; i++) {
				String id = (String) getBillListTable().getCell(selectRows[i],
						getKeyFieldName()).getValue();
				checkRef(id);
				arrayPK[i] = new ObjectUuidPK(id);
				arrayPKSet.add(id);
	
                this.setOprtState("REMOVE");
                this.pubFireVOChangeListener(arrayPK[i].toString());
			}
        }catch (Throwable ex)
        {
        	this.handUIException(ex);
        	canRemove = false;
            SysUtil.abort();
        }
		
		if(canRemove){
			PaymentBillFactory.getRemoteInstance().batchRemove(arrayPKSet);
			showOprtOKMsgAndRefresh();
		}
	}

	protected void buildContractTypeTree() throws Exception {
		super.buildContractTypeTree();
		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeContractType
				.getModel();
		DefaultKingdeeTreeNode rootNode = (DefaultKingdeeTreeNode) treeModel
				.getRoot();
		KDTreeNode node = new KDTreeNode("ContractWithoutText");
		node.setText(PaymentBillClientUtils.getRes("contractNoText"));
		treeContractType.addNodeInto(node, rootNode);// 用无合同类型内的数据来模拟

	}
    private boolean flag = false;
    private static ResourceBundleHelper resHelperWithout=new ResourceBundleHelper(AbstractContractWithoutTextListUI.class.getName());
	protected void treeContractType_valueChanged(TreeSelectionEvent e) throws Exception
	{	
		if (getTypeSelectedTreeNode()==null) return;
		KDTreeNode oldNode=null;
		if(e.getOldLeadSelectionPath()!=null){
			 oldNode = (KDTreeNode)e.getOldLeadSelectionPath().getLastPathComponent();
		}
		if (getTypeSelectedTreeNode().getUserObject().equals("ContractWithoutText")) {
			flag = true;
//			if(oldNode!=null&&!oldNode.getUserObject().equals("ContractWithoutText")){
			if(oldNode==null||!oldNode.getUserObject().equals("ContractWithoutText")){
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
		        this.tblMain.setFormatXml(resHelperWithout.getString("tblMain.formatXml"));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","number","billName",
                		"contractType.name","currency.name","originalAmount","amount","receiveUnit.name","signDate","currency.id","currency.precision"});
		        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractWithoutTextQuery");
		        tblMain.checkParsed(true);

			}
	        contractWithoutTextSelect();
	        
	        if (getMainTable().getRowCount() > 0) {
				getMainTable().getSelectManager().select(0, 0);
//				btnAddNew.setEnabled(true);
//				actionAddNew.setEnabled(true);
//				无文本合同不能新增生成付款申请单
				btnAddNew.setEnabled(false);
				actionAddNew.setEnabled(false);
				menuItemAddNew.setEnabled(false);
			}

			tHelper.init() ;
			tHelper.setQuerySolutionInfo(null);
		}else {
			flag = false;
			//测试
			if(oldNode==null||oldNode.getUserObject().equals("ContractWithoutText")){
				mainQuery.getSelector().clear();
				mainQuery.getFilter().getFilterItems().clear();
				mainQuery.getSorter().clear();
				this.tblMain.setFormatXml(resHelper.getString("tblMain.formatXml"));
				this.tblMain.putBindContents("mainQuery",new String[] {"id","bookedDate","period.number","state","hasSettled","contractType.name","number","name",
						"currency.name","originalAmount","amount","partB.name","contractSource","signDate","landDeveloper.name","partC.name","costProperty","contractPropert","currency.id","currency.precision"});
				mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillQuery");
				tblMain.checkParsed(true);
			}
			super.treeContractType_valueChanged(e);
		}
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//		getMainTable().getColumn("amount").getStyleAttributes().setNumberFormat("###,##0.00");
		FDCHelper.formatTableNumber(getMainTable(), "amount");
		FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
	}

	private void contractWithoutTextSelect() throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("state",
				FDCBillStateEnum.AUDITTED_VALUE));
		// filterItems.add(new FilterItemInfo("contractType.isEnabled",
		// Boolean.TRUE));
		/*
		 * 工程项目树
		 */
		if (getProjSelectedTreeNode() != null
				&& getProjSelectedTreeNode().getUserObject() instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) getProjSelectedTreeNode()
					.getUserObject();

			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo) {
				BOSUuid id = ((OrgStructureInfo) projTreeNodeInfo).getUnit()
						.getId();

				Set idSet = FDCClientUtils.genOrgUnitIdSet(id);
				filterItems.add(new FilterItemInfo("orgUnit.id", idSet,
						CompareType.INCLUDE));
			}
			// 选择的是项目，取该项目及下级项目（如果有）下的所有合同
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				BOSUuid id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}
		mainQuery.setFilter(filter);
		execQuery();
		if (isHasBillTable()) {
			getBillListTable().removeRows(false);
		}
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
		}
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {
		if (getTypeSelectedTreeNode() != null
				&& getTypeSelectedTreeNode().getUserObject().equals(
						"ContractWithoutText")) {
			contractWithoutTextSelect();
		} else {
			super.treeProject_valueChanged(e);
		}
	}

	public void actionPay_actionPerformed(ActionEvent e) throws Exception {
		checkBeforePay();
		int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
				.getRes("pay"));
		if (MsgBox.isYes(isYes)) {
			List idList = ContractClientUtils.getSelectedIdValues(
					getBillListTable(), getKeyFieldName());
			for (int i = 0; i < idList.size(); i++) {
				PaymentBillInfo info = ((IPaymentBill) getPayBizInterface())
						.getPaymentBillInfo(new ObjectUuidPK(idList.get(i)
								.toString()));
				// 522收款时结算方式不能为空
				if (info.getSettlementType() == null)
					throw new RecPayException(
							RecPayException.SETTLEMENTTYPE_CANNOT_BE_NULL);
				// 522付款时付款科目不能为空
				if (info.getPayerAccount() == null)
					throw new RecPayException(
							RecPayException.PAYERACCOUNT_CANNOT_BE_NULL);
			}
			Set idSet = ContractClientUtils.listToSet(idList);
			PaymentBillFactory.getRemoteInstance().pay(idSet);
			showOprtOKMsgAndRefresh();
		}
		super.actionPay_actionPerformed(e);
	}

	protected void checkBeforePay() throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = getRemoteInterface()
				.getCoreBillBaseCollection(view);

		String states = getBillStateForPay();

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			if (billState.equals(states)) {
				pass = true;
			}
			if (!pass) {
				MsgBox.showWarning(this, PaymentBillClientUtils
						.getRes("notGood"));
				SysUtil.abort();
			}
		}
	}

	protected String getBillStateForPay() {
		return BillStatusEnum.AUDITED_VALUE + "";
	}

	// 工程项目
	public void actionProjectAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		super.actionProjectAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		// 不能直接用合同进行过滤，因为存在无文本合同的情况
		String payBillId = this.getSelectedKeyValue();
		PaymentBillInfo info = PaymentBillFactory.getRemoteInstance()
				.getPaymentBillInfo(new ObjectUuidPK(BOSUuid.read(payBillId)));
		CurProjectInfo curProject = info.getCurProject();
		if (curProject == null) {
			return;
		}
		acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
	}

	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("isNeedPay");
		view.getSelector().add(getBillStatePropertyName());

		CoreBaseCollection coll = PaymentBillFactory.getRemoteInstance().getCollection(view);
		if (coll.size() == 0) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(res));
			abort();
		}
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();

			// 检查单据是否在工作流中
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());

			// 如果不需要付款，且为付款状态
			if (!element.isIsNeedPay() && BillStatusEnum.PAYED.equals(element.getBillStatus())) {
				continue;
			}

			if (!stateSet.contains(element.getString(getBillStatePropertyName()))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionCommitSettle.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_submit"));
		actionCommitToBE.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_submit"));
		actionPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_payment"));
		actionCancelPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_unpayment"));
		actionExportData.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_output"));
		actionImportData.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_input"));
		actionWriteOff.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_attributeset"));
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_check"));
		btnPayGroup.setIcon(EASResource.getIcon("imgTbtn_particular"));
		actionEnrolNote.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_credenceprint"));
		actionEndorseOut.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_writeatbackout"));

		// this.btnPayGroup.addAssistMenuItem(actionPay);
		// this.btnPayGroup.addAssistMenuItem(actionWriteOff);
		// this.btnPayGroup.addAssistMenuItem(actionCommitToBE);
		// this.btnPayGroup.addAssistMenuItem(actionEnrolNote);
		// this.btnPayGroup.addAssistMenuItem(actionEndorseOut);
		// this.btnPayGroup.addAssistMenuItem(actionCancelPay);

		actionWriteOff.setEnabled(true);
		actionCommitSettle.setEnabled(true);
		actionCommitToBE.setEnabled(true);
		actionEnrolNote.setEnabled(true);
		actionEndorseOut.setEnabled(true);
		actionCancelPay.setEnabled(true);

		actionWriteOff.setVisible(false);
		actionEnrolNote.setVisible(false);
		actionEndorseOut.setVisible(false);
		actionViewBgBalance.setVisible(false);
		btnPayGroup.setVisible(false);
		actionCommitSettle.setVisible(false);
		menuPay.setVisible(false);
		menuItemCancelPay.setVisible(false);
		
		actionTraceUp.setVisible(true);
		actionTraceUp.setEnabled(true);
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		menuItemSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		menuItemSplit.setAccelerator(KeyStroke.getKeyStroke("alt shift S"));
		
		//增加启用暂缓，取消暂缓按钮
		actionRespite.setVisible(true);
		actionCancelRespite.setVisible(true);
	}

	public void actionCancelPay_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		RecPayHelper helper = new RecPayHelper();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		Set validSet = helper.getPayValidIdSetByAction(null, idSet,
				RecPayHelper.CANCEL_PAY);
		((IPaymentBill) getPayBizInterface()).cancelPay(validSet);
		showMsg(e);
		refresh(e);
	}

	private void showMsg(ActionEvent e) {
		FMClientHelper.showSuccessMessage(this, e);
	}

	/**
	 * 描述：提交结算中心 提交结算中心的前提条件：来源系统非资金结算、单据状态＝已审批、且集中结算类型不为空的付款单
	 */
	public void actionCommitSettle_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		((IPaymentBill) getPayBizInterface()).commitSettle(idSet);
		refresh(e);
		showMsg(e);
	}

	/**
	 * 描述：提交银企互联
	 */
	public void actionCommitToBE_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		RecPayHelper helper = new RecPayHelper();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		Set validSet = helper.getPayValidIdSetByAction(null, idSet,
				RecPayHelper.COMMIT_BE);
		BEHelper.commitToBE(null, validSet,
				PaymentBill2BankPayBillBuilder.class.getName());
		// 提交银企互联成功后自动调原“付款”功能登记日记账（单据登账下）和打出纳标志。
		((IPaymentBill) getPayBizInterface()).pay(idSet, true);
		showMsg(e);
		refresh(e);
	}

	private static String resourcePath = RecPayHandler.srcPath;

	public void actionEndorseOut_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		int rowNumber = FMClientHelper.getSelectedRow(getBillListTable());
		IRow row = getBillListTable().getRow(rowNumber);
		if (row == null) {
			MsgBox.showError(EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
		String id = row.getCell("id").getValue().toString();
		PaymentBillInfo info = ((IPaymentBill) getPayBizInterface())
				.getPaymentBillInfo(new ObjectUuidPK(id), getSelectors());
		// 522收款时结算方式不能为空
		if (info.getSettlementType() == null)
			throw new RecPayException(
					RecPayException.SETTLEMENTTYPE_CANNOT_BE_NULL);
		// 522付款时付款科目不能为空
		if (info.getPayerAccount() == null)
			throw new RecPayException(
					RecPayException.PAYERACCOUNT_CANNOT_BE_NULL);
		ReceivableBillInfo sourceBill = null;
		// 付款单结算方式的票据类型等于应收票据分组
		if (info.getSettlementType() != null
				&& info.getSettlementType().getNtType() != null
				&& info.getSettlementType().getNtType().getGroup() != null
				&& NTTypeGroupEnum.RECEIVABLE.equals(info.getSettlementType()
						.getNtType().getGroup())
				&& info.getSettlementNumber() != null) {
			EntityViewInfo query = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			query.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("number", info.getSettlementNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("billAmt", info.getActPayAmt()));
			if (info.getCurrency() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("currency.id", info.getCurrency()
								.getId().toString()));
			}
			filter.setMaskString("#0 and #1 and #2 ");
			ReceivableBillCollection receivableBillCollection = ReceivableBillFactory
					.getRemoteInstance().getReceivableBillCollection(query);
			// 且结算号与某张应收票据相同。
			if (receivableBillCollection != null
					&& receivableBillCollection.size() >= 1) {
				sourceBill = receivableBillCollection.get(0);
				if (sourceBill.getBillState().equals(BillStateEnum.SAVED)) {
					OutEndorsementBillEditUI.createNewOutEndorseBill(this,
							sourceBill.getId().toString(), sourceBill
									.getBillAmt(), sourceBill.getCurrency()
									.getId().toString(), id);
					refresh(e);
				} else {
					MsgBox.showInfo(this, EASResource.getString(resourcePath,
							"voucher_not_saved"));
					SysUtil.abort();
				}
			} else {
				MsgBox.showInfo(this, EASResource.getString(resourcePath,
						"paybill_cannot_endorse"));
				SysUtil.abort();
			}
		} else {
			MsgBox.showInfo(this, EASResource.getString(resourcePath,
					"paybill_cannot_endorse"));
			SysUtil.abort();
		}
		MutexUtils.releaseMutex(getSelectedKeyValue());
	}

	// 5222 付款单指定的结算方式的票据类型等于应付票据分组时，允许[登记应付票据]，
	// 关联生成应付票据，编辑应付票据提交后，自动完成付款单的付款：
	public void actionEnrolNote_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		if (id == null) {
			MsgBox.showError(EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
		PaymentBillInfo info = ((IPaymentBill) getPayBizInterface())
				.getPaymentBillInfo(new ObjectUuidPK(id), getSelectors());
		// 522收款时结算方式不能为空
		if (info.getSettlementType() == null)
			throw new RecPayException(
					RecPayException.SETTLEMENTTYPE_CANNOT_BE_NULL);
		// 522付款时付款科目不能为空
		if (info.getPayerAccount() == null)
			throw new RecPayException(
					RecPayException.PAYERACCOUNT_CANNOT_BE_NULL);
		if (BillStatusEnum.AUDITED != info.getBillStatus()) {
			refresh(e);
			throw new RecPayException(RecPayException.CANNOT_MULTI_PAY);
		}
		MutexUtils.setMutex(this, getSelectedKeyValue());
		FMClientHelper.generateDestBill(this, new PaymentBillInfo()
				.getBOSType().toString(), new PayableBillInfo().getBOSType()
				.toString(), id);
		refresh(e);
	}

	public void actionViewBgBalance_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		PaymentBillInfo info = ((IPaymentBill) getPayBizInterface())
				.getPaymentBillInfo(new ObjectUuidPK(id), getSelectors());
		IBgCtrlHandler handler = new BgCtrlPaymentBillHandler();
		BgCtrlParamCollection paramColl = handler.getParameters(null, id);
		String boName = RecPayHelper.BO_NAME_PAY;
		FMClientHelper.viewBgBalance(this, boName, paramColl, info);
	}

	/**
	 * 描述：开票 1、付款方式为银行、单据状态＝已审批，且未关联票据的付款单，才允许填开支票。
	 * 2、付款单的结算方式关联的票据类型为空，或关联票据类型的分组不等于支票，不允许开票；提示用户结算方式不能用于支票。
	 * 3、如果批量指定多张付款单，除同时满足以上两个条件外，还必须是付款账户、币别、结算方式一致，且均未关联支票；
	 * 如果付款单的结算号不完全一致，提示用户，指定单据的结算号不相同，是否继续，用户确认也可继续
	 */
	// 收付款单处理类
	private CasRecPayHandler handler = new CasRecPayHandler();

	public void actionWriteOff_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		Set idSet = ContractClientUtils.listToSet(idList);
		Set validSet = handler.getValidWriteOffIdSet(idSet);
		if (validSet == null || validSet.isEmpty()) {
			throw new RecPayException(RecPayException.HASNOT_VALID_LIST,
					new Object[] { e.getActionCommand() });
		}
		ChequeAssociateUI.showDialogWindows(this, (String[]) validSet
				.toArray(new String[] {}));
		;
		refresh(e);

		Iterator iter = validSet.iterator();
		while (iter.hasNext()) {
			String billId = (String) iter.next();
			((IPaymentBill) getPayBizInterface()).pay4WF(new ObjectUuidPK(
					billId));
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("entries.*"));
		sic.add(new SelectorItemInfo("contractBill.*"));
		sic.add(new SelectorItemInfo("curProject.*"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.*"));
		sic.add(new SelectorItemInfo("currency.*"));
		// sic.add(new SelectorItemInfo("exchangeRate.*"));
		sic.add(new SelectorItemInfo("fdcPayeeName.*"));
		sic.add(new SelectorItemInfo("settlementType.*"));
		sic.add(new SelectorItemInfo("fdcPayType.*"));
		sic.add(new SelectorItemInfo("payerBank.*"));
		sic.add(new SelectorItemInfo("payerAccountBank.*"));
		sic.add(new SelectorItemInfo("payerAccount.*"));
		sic.add(new SelectorItemInfo("oppAccount.*"));
		sic.add(new SelectorItemInfo("fpItem.*"));
		sic.add(new SelectorItemInfo("cashier.*"));
		sic.add(new SelectorItemInfo("accountant.*"));
		sic.add(new SelectorItemInfo("company.*"));
		sic.add(new SelectorItemInfo("useDepartment.*"));
		sic.add(new SelectorItemInfo("actFdcPayeeName.*"));
		sic.add(new SelectorItemInfo("addProjectAmt"));
		sic.add(new SelectorItemInfo("payPartAMatlAmt"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.*"));
		return sic;
	}

	protected boolean isFootVisible() {
		return true;
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnRefresh || e.getSource() == menuItemRefresh) {
			actionVoucher.setVisible(false);
			actionDelVoucher.setVisible(false);
		}
	}

	public void refreshList() throws Exception {
		super.refreshList();
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
	}

	protected boolean isVoucherVisible() {
		return false;
	}
	
	protected boolean isOrderPre() {
		return false;
	}
	
	/**
	 * 合同类型树是否有无文本合同
	 * @return
	 */
	protected boolean containConWithoutTxt(){
		return true;
	}

	protected void fetchInitData() throws Exception {
		// TODO 自动生成方法存根
		
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!checkPaymentAllSplit&&checkHasPaymentSplit()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantUnAudit"));
			SysUtil.abort();
		}		
		super.actionUnAudit_actionPerformed(e);
	}
	//功能与付款拆分序时薄一致
	//TODO 后续优化至工具类
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		String contractId = (String)getBillListTable().getCell(0, "contractId").getValue();
		if (id == null || contractId==null)
			return;
		Map param = new HashMap();
		param.put("isSimpleFinancial", Boolean.valueOf(isSimpleFinancial));
		param.put("isAdjustVourcherModel", Boolean.valueOf(isAdjustVourcherModel));
		PaymentSplitHelper.checkPaymentVouchered(id, contractId, param);
		
		
		AbstractSplitInvokeStrategy invokeStra = new PaymentSplitInvokeStrategy(
				id, this);
		invokeStra.invokeSplit();
	}
	

	protected boolean isOnlyQueryAudited() {
		return true;
	}
	
	protected void tblPaymentBill_tableSelectChanged(KDTSelectEvent e) throws Exception {
		super.tblPaymentBill_tableSelectChanged(e);
		IRow row;
		if (this.tblPaymentBill.getSelectManager().getActiveRowIndex() != -1) {
			if ((tblPaymentBill.getSelectManager().getBlocks().size() > 1)
					|| (e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() > 0)) {
				actionRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			} else {
				row = this.tblPaymentBill.getRow(this.tblPaymentBill.getSelectManager().getActiveRowIndex());
				String state = row.getCell(PaymentBillContants.COL_STATE).getValue().toString();
				if (FDCBillStateEnum.SAVED.toString().equals(state)
						|| FDCBillStateEnum.SUBMITTED.toString().equals(state)
						|| FDCBillStateEnum.AUDITTING.toString().equals(state)) {
					if (Boolean.TRUE.equals(row.getCell("isRespite").getValue())) {
						actionRespite.setEnabled(false);
						actionCancelRespite.setEnabled(true);
					} else {
						actionRespite.setEnabled(true);
						actionCancelRespite.setEnabled(false);
					}
					if (tblMain.getSelectManager().getBlocks().size() == 1
							&& FDCBillStateEnum.AUDITTED.equals(row.getCell("billStatus").getValue())) {
						actionRespite.setEnabled(false);
						actionCancelRespite.setEnabled(true);
					}
				} else {
					actionRespite.setEnabled(false);
					actionCancelRespite.setEnabled(false);
				}
			}
		}
	}
	
	public void actionRespite_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),false);
			if(idList.size()>0 && idList.get(0) != null){
				FDCPaymentBillFactory.getRemoteInstance().setRespite(idList, true);
				MsgBox.showWarning("操作成功！已审批状态的单据不会启用暂缓");
				refreshList();
			}
	}
	
	public void actionCancelRespite_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(), true);
		if (idList.size() > 0 && idList.get(0) != null) {
			FDCPaymentBillFactory.getRemoteInstance().setRespite(idList, false);
			showOprtOKMsgAndRefresh();
		}
	}
}
