package com.kingdee.eas.fdc.finance.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryInfo;
import com.kingdee.eas.fdc.finance.PartAMainContractCollection;
import com.kingdee.eas.fdc.finance.PartAMainContractFactory;
import com.kingdee.eas.fdc.finance.PartAMainContractInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.material.client.PartAUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * PaymentBill辅助类
 * @author pengwei_hou 
 * Date 2008-9-4
 */
public class PaymentBillTableHelper {
	
	/**
	 * 甲供材主合同列表页签表格
	 */
	private static KDTable table = null;
	
	private PaymentBillEditUI editUI = null;
	
	private PaymentBillInfo editData = null;
	
	private JButton btnAddPartANew = null;
	
	private JButton btnDelPartANew = null;
	
	private boolean isModified = false;
	public PaymentBillTableHelper(){}
	
	public PaymentBillTableHelper(PaymentBillEditUI ui) {
		table = ui.kdtPartA;
		editUI = ui;
	}
	
	/**
	 * 初始化合同F7
	 */
	public void setPartAMainContractF7(PaymentBillInfo editData) {
		
		this.editData = editData;
		
		KDBizPromptBox prmt = new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQuery");
		prmt.setVisible(true);
		prmt.setEditable(true);
		prmt.setDisplayFormat("$number$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");

		EntityViewInfo view =  new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isPartAMaterialCon", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", editData.getCurProject().getId().toString()));
		view.setFilter(filter);
		SelectorItemCollection sic=new  SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("state");
		sic.add("partB.name");
		prmt.setSelectorCollection(sic);
		prmt.setEntityViewInfo(view);
		
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(prmt);
		table.getColumn("conNumber").setEditor(cellEditor);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new BizDataFormat("$number$"));
		table.getColumn("conNumber").setRenderer(render);
	}
	
	/**
	 * 设置表格属性
	 */
	public void setPartATable(){
		
		KDFormattedTextField formate = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formate.setPrecision(2);
		formate.setSupportedEmpty(false);
		formate.setNegatived(false);
		formate.setMinimumValue(FDCHelper.ZERO);
		formate.setMaximumValue(new BigDecimal("99999999999"));
		formate.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		ICellEditor editor = new KDTDefaultCellEditor(formate);
		table.getColumn("amount").setEditor(editor);
		table.getHeadRow(0).getCell("amount").setValue("金额（本币）");
		table.getColumn("conName").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		FDCHelper.formatTableNumber(table, "amount");
	}
	
	/**
	 * 加载甲供主合同列表数据
	 * @param paymentBillId
	 * @throws BOSException
	 */
	public void loadPartAData() throws BOSException{
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("mainContractBill.number");
		view.getSelector().add("mainContractBill.name");
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", editData.getId().toString()));
		view.setFilter(filter);
		
		PartAMainContractCollection partColl = PartAMainContractFactory.getRemoteInstance().getPartAMainContractCollection(view);
		for(Iterator iter = partColl.iterator(); iter.hasNext();){
			
			IRow row = table.addRow();
			PartAMainContractInfo info = (PartAMainContractInfo) iter.next();
			row.getCell("conNumber").setValue(info.getMainContractBill().getNumber());
			row.getCell("conName").setValue(info.getMainContractBill().getName());
			row.getCell("amount").setValue(info.getAmount());
			row.getCell("desc").setValue(info.getDesc());
			row.getCell("id").setValue(info.getId());
			row.getCell("contractId").setValue(info.getMainContractBill().getId());
			row.getCell("paymentId").setValue(info.getPaymentBill().getId());
		}
	}
	/**
	 * 加载材料明细数据
	 * @param paymentBillId
	 * @throws BOSException
	 */
	public void loadMaterialData(PayRequestBillConfirmEntryCollection mcBills) throws BOSException{
		editData = (PaymentBillInfo)editUI.getEditData();
		if(editData==null||editData.getFdcPayReqID()==null)
			return;
		KDTable tableMaterial = editUI.tblMaterial;
		tableMaterial.checkParsed();
		tableMaterial.removeRows();
		tableMaterial.getStyleAttributes().setLocked(true);
		tableMaterial.getColumn("confirmAmt").getStyleAttributes().setNumberFormat("#,###.00");
		tableMaterial.getColumn("confirmAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tableMaterial.getColumn("allConfirmAmt").getStyleAttributes().setNumberFormat("#,###.00");
		tableMaterial.getColumn("allConfirmAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tableMaterial.getColumn("paidAmount").getStyleAttributes().setNumberFormat("#,###.00");
		tableMaterial.getColumn("paidAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tableMaterial.getColumn("reqAmount").getStyleAttributes().setNumberFormat("#,###.00");
		tableMaterial.getColumn("reqAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tableMaterial.getColumn("confirmPaidAmt").getStyleAttributes().setNumberFormat("#,###.00");
		tableMaterial.getColumn("confirmPaidAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		for(Iterator iter = mcBills.iterator(); iter.hasNext();){
			
			IRow row = tableMaterial.addRow();
			PayRequestBillConfirmEntryInfo info = (PayRequestBillConfirmEntryInfo) iter.next();
			row.getCell("id").setValue(info.getId());
			row.getCell("number").setValue(info.getConfirmBill().getNumber());
			row.getCell("reqAmount").setValue(info.getReqAmount());
			row.getCell("paidAmount").setValue(info.getPaidAmount());
			row.getCell("confirmPaidAmt").setValue(info.getConfirmBill().getPaidAmt());
			if(!info.containsKey("notIncludeThisPaidAmt")){
				BigDecimal notIncludeThisPaidAmt = FDCHelper.toBigDecimal(info.getConfirmBill().getPaidAmt()).subtract(FDCHelper.toBigDecimal(info.getPaidAmount()));
				info.put("notIncludeThisPaidAmt", notIncludeThisPaidAmt);
			}
			row.getCell("confirmAmt").setValue(info.getConfirmBill().getConfirmAmt());
			row.getCell("allConfirmAmt").setValue(info.getConfirmBill().getToDateConfirmAmt());
			if(info.getConfirmBill().getMaterialContractBill()!=null){
				row.getCell("materialContractId").setValue(info.getConfirmBill().getMaterialContractBill().getId().toString());
				row.getCell("materialContractNumber").setValue(info.getConfirmBill().getMaterialContractBill().getNumber());
				row.getCell("materialContractName").setValue(info.getConfirmBill().getMaterialContractBill().getName());
			}
			if(info.getConfirmBill().getMainContractBill()!=null){
				row.getCell("mainContractId").setValue(info.getConfirmBill().getMainContractBill().getId());
				row.getCell("mainContractNumber").setValue(info.getConfirmBill().getMainContractBill().getNumber());
				row.getCell("mainContractName").setValue(info.getConfirmBill().getMainContractBill().getName());
			}
			
			//row.getCell("paymentId").setValue(info.getPaymentBill().getId());
		}
	}
	
	/**
	 * 增加合计行，并设置属性
	 */
	public void addTotalRow() {
		IRow totalRow = table.addRow();
		totalRow.getCell("conName").setValue(FDCClientUtils.getRes("total"));
		totalRow.getCell("amount").setValue(FDCClientUtils.getRes("total"));
		totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		totalRow.getStyleAttributes().setLocked(true);
	}
	
	/**
	 * 计算金额列总额
	 */
	public void sumTable() {
		int count = table.getRowCount();
		BigDecimal total = FDCHelper.ZERO;
		for (int i = 0; i < count - 1; i++) {
			Object value = table.getCell(i, "amount").getValue();
			if(value != null) {
				BigDecimal amt = new BigDecimal(value.toString());
				total = total.add(amt);
			}
		}
		table.getCell(count - 1, "amount").setValue(total);
	}
	
	/**
	 * 增加 主合同列表分录的添加与删除按钮,初始化按钮状态
	 * @param ctn
	 * @param enum 
	 */
	public void initCtn(KDContainer ctn){
		btnAddPartANew = ctn.add(new ItemAction(){

			public void actionPerformed(ActionEvent arg0) {
				addLine();
				sumTable();
			}

		});
		btnDelPartANew = ctn.add(new ItemAction(){

			public void actionPerformed(ActionEvent arg0) {
				removeLine();
				sumTable();
			}
		});
		btnAddPartANew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddPartANew.setSize(20,50);
		btnAddPartANew.setText(PartAUtils.getRes("addEntry"));
		btnAddPartANew.setPreferredSize(new Dimension(120, 20));
		btnDelPartANew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelPartANew.setText(PartAUtils.getRes("delEntry"));
		btnDelPartANew.setPreferredSize(new Dimension(120, 20));
		btnAddPartANew.setVisible(true);
		btnDelPartANew.setVisible(true);
		
	}
	
	public void addLine(){
		
		if(table == null){
            return;
        }
        IRow row = null;
        if (table.getSelectManager().size() > 0)
        {
            int top = table.getSelectManager().get().getTop();
            if (isTableColumnSelected(table)){
                row = table.addRow();
            }else{
                row = table.addRow(top);
            }
        }else{
            row = table.addRow(0);
        }

	}
	
	public void removeLine(){
		int index = table.getSelectManager().getActiveRowIndex();
		if(index == -1){
			index = index + 1;
		}
		
		Object value = table.getRow(index).getCell("conName").getValue();
		if(value == null){
			table.removeRow(index);
		}
		if(value != null && !value.toString().equals(FDCClientUtils.getRes("total"))){
			table.removeRow(index);
		}
	}
	
	public void setPartAButtonStatus(String oprtState){
		
		if(oprtState == editUI.STATUS_VIEW || oprtState == FDCBillStateEnum.AUDITTED_VALUE){
			btnAddPartANew.setEnabled(false);
			btnDelPartANew.setEnabled(false);
		}else{
			btnAddPartANew.setEnabled(true);
			btnDelPartANew.setEnabled(true);
		}
		
	}
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if (table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();

            if ((block.getMode() == KDTSelectManager.COLUMN_SELECT)
                    || (block.getMode() == KDTSelectManager.TABLE_SELECT))
            {
                return true;
            }
        }

        return false;
    }
	
	/**
	 * 保存甲供材主合同列表
	 * @param editData
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void storePartAConList(PaymentBillInfo editData) throws EASBizException, BOSException{

		CoreBaseCollection collection = new CoreBaseCollection();
		for(int i=0; i<table.getRowCount(); i++){
			IRow row = table.getRow(i);
			if(row.getCell("conNumber").getValue() == null){
				continue;
			}
			PartAMainContractInfo item = new PartAMainContractInfo();
			ContractBillInfo contract = new ContractBillInfo();
			PaymentBillInfo payment =  new PaymentBillInfo();
			String contractId = row.getCell("contractId").getValue().toString();
			String paymentId = row.getCell("paymentId").getValue().toString();
			BigDecimal amount = new BigDecimal(row.getCell("amount").getValue().toString());
			Object desc = row.getCell("desc").getValue();
			contract.setId(BOSUuid.read(contractId));
			payment.setId(BOSUuid.read(paymentId));
			item.setPaymentBill(payment);
			item.setMainContractBill(contract);
			item.setAmount(amount);
			if(desc != null){
				item.setDesc(desc.toString());
			}
			collection.add(item);
		}
//		editData.put("PartAMainContractEntrys",collection);
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", editData.getId().toString()));
		PartAMainContractFactory.getRemoteInstance().delete(filter);
		if(collection != null && collection.size() > 0){
			PartAMainContractFactory.getRemoteInstance().addnew(collection);
		}
		isModified = false;
	}
	
	/**
	 * 描述：录入的本位币金额，合计必须等于付款单对应的本位币金额<p>
	 * 提交时校验
	 */
	public void verifySubmit() throws Exception{
		
		//必然录入
		if(table.getRowCount() < 2){
			MsgBox.showWarning(editUI, PartAUtils.getRes("partAMainConList")+FDCClientUtils.getRes("atLeastOneEntry"));
			SysUtil.abort();
		}
		
		Object value = table.getCell(table.getRowCount()-1,"amount").getValue();
		if(value != null){
			BigDecimal cellTotal = FDCHelper.toBigDecimal(value);
			if(cellTotal.doubleValue() < 0 
					|| (cellTotal.doubleValue() > 0 
					&& cellTotal.compareTo(editUI.txtLocalAmt.getBigDecimalValue()) != 0)){
				MsgBox.showWarning(PartAUtils.getRes("checkPartAMainConAmount"));
				SysUtil.abort();
			}
		}
		
	}
	
	/**
	 * 保存校验
	 * @throws Exception
	 */
	public void verifySave() throws Exception{
		
		int rowCount = table.getRowCount();
		Set set = new HashSet();
		for(int i=0; i<rowCount-1; i++){
			
			IRow row = table.getRow(i);
			int index = i+1;
			Object obj = row.getCell("conNumber").getValue();
			if(obj != null){
				if(set.contains(obj)){
					String msg = PartAUtils.getRes("partAMainConList")+PartAUtils.getRes("repeatCon");
					String[] args = new String[]{Integer.toString(index)};
					MsgBox.showWarning(FDCClientHelper.formatMessage(msg, args));
					SysUtil.abort();
				}
				set.add(obj);
			}
			for(int j=0; j<3; j++ ){
				if (FDCHelper.isEmpty(row.getCell(j).getValue())) 
				{
					table.getEditManager().editCellAt(i, j);
					String value = (String)table.getHeadRow(0).getCell(j).getValue();
					String msg = PartAUtils.getRes("partAMainConList")+PartAUtils.getRes("argsCanNotBeNull");
					String[] args = new String[]{Integer.toString(index),value};
					MsgBox.showWarning(editUI, FDCClientHelper.formatMessage(msg, args));
					SysUtil.abort();
				}
			}
			
			Object value = row.getCell("desc").getValue();
			if(value != null && value.toString().length() > 80){
	        	String info = PartAUtils.getRes("moreRemark");
	        	String[] args = new String[]{Integer.toString(index)};
	        	MsgBox.showWarning(FDCClientHelper.formatMessage(info, args));
	        	SysUtil.abort();
	        }
		}
	}

	public boolean isModify(){
//		int rowCount = table.getRowCount();
////   		if(rowCount == 1){
////   			return false;
////   		}
//   		PartAMainContractCollection partAMainContractCollection = null;
//     	try {
//	   		EntityViewInfo view = new EntityViewInfo();
//	   		view.getSelector().add("mainContractbill.id");
//	   		view.getSelector().add("mainContractBill.number");
//	   		view.getSelector().add("mainContractBill.name");
//	   		view.getSelector().add("amount");
//	 		view.getSelector().add("desc");
//	  		FilterInfo filter = new FilterInfo();
//	  		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", this.editData.getId()));
//			view.setFilter(filter);
//			partAMainContractCollection = PartAMainContractFactory.getRemoteInstance().getPartAMainContractCollection(view);
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		if(partAMainContractCollection == null 
//				|| (rowCount-1)!=partAMainContractCollection.size() 
//				|| partAMainContractCollection.size()==0){
//			return true;
//		}
//		String symbol = "1";
//	    for(int i=0;i<rowCount-1;i++){
//	    	Object conId = table.getRow(i).getCell("contractId").getValue();
//		   	Object amount = table.getRow(i).getCell("amount").getValue();
//		 	Object desc = table.getRow(i).getCell("desc").getValue();
//		    for(int j=0;j<partAMainContractCollection.size();j++){
//		    	PartAMainContractInfo partAMainContractInfo = partAMainContractCollection.get(i);
//		    	if(conId.equals(partAMainContractInfo.getMainContractBill().getId())
//		    			&& partAMainContractInfo.getAmount().compareTo(FDCHelper.toBigDecimal(amount))==0
//		    			&& (desc+symbol).equals(partAMainContractInfo.getDesc()+symbol)){
//		    		continue;
//		    	}else{
//		    		return true;
//		    	}
//		    }
//	    }
		return isModified;
	}
	
	protected void kdtPartA_editStopped(KDTEditEvent e) throws Exception {
		if(e.getColIndex() == table.getColumn("conNumber").getColumnIndex() && e.getValue()!= null){
    		if(e.getValue() != null && e.getValue() instanceof ContractBillInfo){
    			ContractBillInfo info = (ContractBillInfo)e.getValue();
    			table.getCell(e.getRowIndex(), "conNumber").setValue(info.getNumber());
    			table.getCell(e.getRowIndex(), "conName").setValue(info.getName());
    			table.getCell(e.getRowIndex(), "contractId").setValue(info.getId());
    			table.getCell(e.getRowIndex(), "paymentId").setValue(editData.getId());
    		}
    	}
    	sumTable();
    	isModified = true;
	}
	
	void checkAdvance(PaymentBillInfo editData,Map bindCellMap) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", editData.getContractBillId()));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", new Integer(BillStatusEnum.AUDITED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", new Integer(BillStatusEnum.PAYED_VALUE)));
		if(editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		}
		filter.setMaskString("#0 and (#1 or #2) and #3");
		view.getSelector().add("prjPayEntry.advance");
		PaymentBillCollection c = PaymentBillFactory.getRemoteInstance()
			.getPaymentBillCollection(view);
		BigDecimal advance = FDCHelper.ZERO;
		if(c!=null){
			for(int i=0;i<c.size();i++){
				PaymentBillInfo info = c.get(i);
				if(info.getPrjPayEntry()!=null){
					advance = FDCHelper.add(advance, info.getPrjPayEntry().getAdvance());
				}
			}
		}
		Object cellValue = ((ICell)bindCellMap.get(PaymentBillContants.ADVANCE)).getValue();
		advance = FDCHelper.add(advance, cellValue);
		if (FDCHelper.ZERO.compareTo(advance) == 1) {
			MsgBox.showError("合同下已审批状态的付款单的 预付款本次申请原币 + 本张单据的预付款本次申请原币 必须大于0");
			SysUtil.abort();
		}
		
	}
}

