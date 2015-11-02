/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillCollection;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ContractPCSplitBillEditUI extends AbstractContractPCSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPCSplitBillEditUI.class);
    private IUIWindow selectUI=null;
    private boolean hasRemove=false;
    private BigDecimal totalSupplyAmount=null;
    private String mainContractId=null;
    public ContractPCSplitBillEditUI() throws Exception{
        super();
    }
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractPCSplitBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}
	protected void getMainContractId(String billId) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select parent.fid id from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and con.fid='"+billId+"'");
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			mainContractId=rowSet.getString("id");
		}
	}
	
	protected void getSupplyAmount(String billId) throws BOSException, SQLException{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select sum(entry.fcontent) as amount from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and parent.fid='"+billId+"'");
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			totalSupplyAmount=rowSet.getBigDecimal("amount");
		}
	}

	protected IObjectValue createNewData() {
		ContractPCSplitBillInfo info=new ContractPCSplitBillInfo();
        
    	String contractBillId = (String)getUIContext().get("contractBillId");
    	if(contractBillId!=null){
    		ContractBillInfo contractBillInfo=null;
            SelectorItemCollection sel = new SelectorItemCollection();
            sel.add("id");
            sel.add("number");
            sel.add("name");
            sel.add("amount");
            sel.add("curProject.*");
            sel.add("curProject.fullOrgUnit.*");
            sel.add("contractPropert");
            sel.add("contractType.id");
            try {
            	contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),sel);
    		} catch (Exception e) {
    			handUIExceptionAndAbort(e);
    		}
        	info.setContractBill(contractBillInfo);
        	info.setCurProject(contractBillInfo.getCurProject());
    		try {
    			if(ContractPropertyEnum.SUPPLY.equals(contractBillInfo.getContractPropert())){
    				this.getMainContractId(contractBillId);
    				if(mainContractId==null){
    					FDCMsgBox.showWarning(this,"主合同不存在！");
    	    			SysUtil.abort();
    				}
	        		EntityViewInfo view = new EntityViewInfo();
	        		FilterInfo filter = new FilterInfo();
	        		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",mainContractId));
	        		view.setFilter(filter);
	        		view.setSelector(this.getSelectors());
    			
    				ContractPCSplitBillCollection spcol = ContractPCSplitBillFactory.getRemoteInstance().getContractPCSplitBillCollection(view);
    				if(spcol.size()==0){
    	    			FDCMsgBox.showWarning(this,"请先先对主合同进行跨期合同合约规划拆分操作！");
    	    			SysUtil.abort();
    	    		}
    				BigDecimal amount=contractBillInfo.getAmount();
    	    		for(int i=0;i<spcol.get(0).getEntry().size();i++){
    	    			ContractPCSplitBillEntryInfo entry=spcol.get(0).getEntry().get(i);
    	    			entry.setId(null);
    	    			if(entry.getScale()!=null&&amount!=null){
    	    				entry.setAmount(amount.multiply(entry.getScale()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
    	    			}
    	    			info.getEntry().add(entry);
    	    		}
    	    		info.setAmount(contractBillInfo.getAmount());
    			}else{
//    				this.getSupplyAmount(contractBillId);
            		info.setAmount(FDCHelper.subtract(contractBillInfo.getAmount(), totalSupplyAmount));
            	}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	String contractChangeBillId = (String)getUIContext().get("contractChangeBillId");
    	if(contractChangeBillId!=null){
    		ContractChangeBillInfo contractChangeBillInfo=null;
            SelectorItemCollection sel = new SelectorItemCollection();
            sel.add("id");
            sel.add("contractBill.number");
            sel.add("contractBill.name");
            sel.add("amount");
            sel.add("curProject.*");
            sel.add("curProject.fullOrgUnit.*");
            try {
            	contractChangeBillInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChangeBillId)),sel);
    		} catch (Exception e) {
    			handUIExceptionAndAbort(e);
    		}
        	info.setContractChangeBill(contractChangeBillInfo);
        	info.setCurProject(contractChangeBillInfo.getCurProject());
        	info.setAmount(contractChangeBillInfo.getAmount());
        	
        	EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractChangeBillInfo.getContractBill().getId().toString()));
    		view.setFilter(filter);
    		view.setSelector(this.getSelectors());
			try {
				ContractPCSplitBillCollection spcol = ContractPCSplitBillFactory.getRemoteInstance().getContractPCSplitBillCollection(view);
				if(spcol.size()==0){
	    			FDCMsgBox.showWarning(this,"请先进行跨期合同合约规划拆分操作！");
	    			SysUtil.abort();
	    		}
				BigDecimal amount=contractChangeBillInfo.getAmount();
	    		for(int i=0;i<spcol.get(0).getEntry().size();i++){
	    			ContractPCSplitBillEntryInfo entry=spcol.get(0).getEntry().get(i);
	    			entry.setId(null);
	    			if(entry.getScale()!=null&&amount!=null){
	    				entry.setAmount(amount.multiply(entry.getScale()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
	    			}
	    			info.getEntry().add(entry);
	    		}
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	String contractChangeSettleBillId = (String)getUIContext().get("contractChangeSettleBillId");
    	if(contractChangeSettleBillId!=null){
    		ContractChangeSettleBillInfo contractChangeSettleBillInfo=null;
            SelectorItemCollection sel = new SelectorItemCollection();
            sel.add("id");
            sel.add("contractBill.number");
            sel.add("contractBill.name");
            sel.add("allowAmount");
            sel.add("curProject.*");
            sel.add("curProject.fullOrgUnit.*");
            try {
            	contractChangeSettleBillInfo = ContractChangeSettleBillFactory.getRemoteInstance().getContractChangeSettleBillInfo(new ObjectUuidPK(BOSUuid.read(contractChangeSettleBillId)),sel);
    		} catch (Exception e) {
    			handUIExceptionAndAbort(e);
    		}
        	info.setContractChangeSettleBill(contractChangeSettleBillInfo);
        	info.setCurProject(contractChangeSettleBillInfo.getCurProject());
        	info.setAmount(contractChangeSettleBillInfo.getAllowAmount());
        	
        	EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractChangeSettleBillInfo.getContractBill().getId().toString()));
    		view.setFilter(filter);
    		view.setSelector(this.getSelectors());
			try {
				ContractPCSplitBillCollection spcol = ContractPCSplitBillFactory.getRemoteInstance().getContractPCSplitBillCollection(view);
				if(spcol.size()==0){
	    			FDCMsgBox.showWarning(this,"请先进行跨期合同合约规划拆分操作！");
	    			SysUtil.abort();
	    		}
				BigDecimal amount=contractChangeSettleBillInfo.getAllowAmount();
	    		for(int i=0;i<spcol.get(0).getEntry().size();i++){
	    			ContractPCSplitBillEntryInfo entry=spcol.get(0).getEntry().get(i);
	    			entry.setId(null);
	    			if(entry.getScale()!=null&&amount!=null){
	    				entry.setAmount(amount.multiply(entry.getScale()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
	    			}
	    			info.getEntry().add(entry);
	    		}
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
    	
    	String contractSettleBillId = (String)getUIContext().get("contractSettleBillId");
    	if(contractSettleBillId!=null){
    		ContractSettlementBillInfo contractSettleBillInfo=null;
            SelectorItemCollection sel = new SelectorItemCollection();
            sel.add("id");
            sel.add("contractBill.number");
            sel.add("contractBill.name");
            sel.add("curSettlePrice");
            sel.add("curProject.*");
            sel.add("curProject.fullOrgUnit.*");
            try {
            	contractSettleBillInfo = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(contractSettleBillId)),sel);
    		} catch (Exception e) {
    			handUIExceptionAndAbort(e);
    		}
        	info.setContractSettleBill(contractSettleBillInfo);
        	info.setCurProject(contractSettleBillInfo.getCurProject());
        	info.setAmount(contractSettleBillInfo.getCurSettlePrice());
        	
        	EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractSettleBillInfo.getContractBill().getId().toString()));
    		view.setFilter(filter);
    		view.setSelector(this.getSelectors());
			try {
				ContractPCSplitBillCollection spcol = ContractPCSplitBillFactory.getRemoteInstance().getContractPCSplitBillCollection(view);
				if(spcol.size()==0){
	    			FDCMsgBox.showWarning(this,"请先进行跨期合同合约规划拆分操作！");
	    			SysUtil.abort();
	    		}
				BigDecimal amount=contractSettleBillInfo.getCurSettlePrice();
	    		for(int i=0;i<spcol.get(0).getEntry().size();i++){
	    			ContractPCSplitBillEntryInfo entry=spcol.get(0).getEntry().get(i);
	    			entry.setId(null);
	    			if(entry.getScale()!=null&&amount!=null){
	    				entry.setAmount(amount.multiply(entry.getScale()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
	    			}
	    			info.getEntry().add(entry);
	    		}
			} catch (BOSException e) {
				e.printStackTrace();
			}
    	}
		return info;
	}
	public void loadFields() {
		super.loadFields();
		getTotal();
		
		if(this.editData.getContractBill()!=null){
			this.txtContractNumber.setText(this.editData.getContractBill().getNumber());
			this.txtContractName.setText(this.editData.getContractBill().getName());
			if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractBill().getContractPropert())){
				this.txtAmount.setValue(this.editData.getContractBill().getAmount());
			}else{
				if(this.editData.getId()!=null){
//					try {
//						this.getSupplyAmount(this.editData.getContractBill().getId().toString());
//					} catch (BOSException e) {
//						e.printStackTrace();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
					this.txtAmount.setValue(FDCHelper.subtract(this.editData.getContractBill().getAmount(), totalSupplyAmount));
				}
			}
		}else if(this.editData.getContractChangeBill()!=null){
			this.txtContractNumber.setText(this.editData.getContractChangeBill().getContractBill().getNumber());
			this.txtContractName.setText(this.editData.getContractChangeBill().getContractBill().getName());
			this.txtAmount.setValue(this.editData.getContractChangeBill().getAmount());
		}else if(this.editData.getContractChangeSettleBill()!=null){
			this.txtContractNumber.setText(this.editData.getContractChangeSettleBill().getContractBill().getNumber());
			this.txtContractName.setText(this.editData.getContractChangeSettleBill().getContractBill().getName());
			this.txtAmount.setValue(this.editData.getContractChangeSettleBill().getAllowAmount());
		}else if(this.editData.getContractSettleBill()!=null){
			this.txtContractNumber.setText(this.editData.getContractSettleBill().getContractBill().getNumber());
			this.txtContractName.setText(this.editData.getContractSettleBill().getContractBill().getName());
			this.txtAmount.setValue(this.editData.getContractSettleBill().getCurSettlePrice());
		}
	}
	protected void getTotal(){
		BigDecimal splitedAmount=FDCHelper.ZERO;
		for(int i=0;i<this.getDetailTable().getRowCount();i++){
			BigDecimal amount=(BigDecimal) this.getDetailTable().getRow(i).getCell("amount").getValue();
			splitedAmount=FDCHelper.add(splitedAmount, amount);
		}
		this.txtSplitedAmount.setValue(splitedAmount);
		this.txtUnSplitAmount.setValue(FDCHelper.subtract(this.txtAmount.getBigDecimalValue(), splitedAmount));
	}
	public void storeFields(){
		BigDecimal unSplitAmount=this.txtUnSplitAmount.getBigDecimalValue();
		BigDecimal splitAmount=this.txtSplitedAmount.getBigDecimalValue();
    	if(unSplitAmount.compareTo(FDCHelper.ZERO)>0){
    		if(splitAmount.compareTo(FDCHelper.ZERO)==0){
    			this.editData.setSplitState(CostSplitStateEnum.NOSPLIT);
    		}else{
    			this.editData.setSplitState(CostSplitStateEnum.PARTSPLIT);
    		}
    	}else if(unSplitAmount.compareTo(FDCHelper.ZERO)==0){
    		this.editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
    	}
		super.storeFields();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkflowList.setVisible(false);
		this.actionCopyLine.setVisible(false);
		this.menuBiz.setVisible(false);
		
		this.actionPCSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		this.getDetailTable().checkParsed();
		this.getDetailTable().getColumn("scale").setRenderer(render_scale);
		this.getDetailTable().getColumn("scale").getStyleAttributes().setNumberFormat("#0.00");
		this.getDetailTable().getColumn("scale").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDFormattedTextField scale = new KDFormattedTextField();
		scale.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		scale.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		scale.setPrecision(2);
		scale.setMaximumValue(new BigDecimal(100));
		scale.setNegatived(false);
		KDTDefaultCellEditor scaleEditor = new KDTDefaultCellEditor(scale);
		this.getDetailTable().getColumn("scale").setEditor(scaleEditor);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.getDetailTable().getColumn("amount").setEditor(amountEditor);
		this.getDetailTable().getColumn("amount").getStyleAttributes().setNumberFormat("#0.00");
		this.getDetailTable().getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.getDetailTable().getColumn("pcAmount").setEditor(amountEditor);
		this.getDetailTable().getColumn("pcAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.getDetailTable().getColumn("pcAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
//		if(this.editData.getContractBill()!=null&&!this.oprtState.equals(OprtState.VIEW)){
//			FilterInfo filter=new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("contractChangeSettleBill.contractBill.id",this.editData.getContractBill().getId().toString()));
//			
//			FilterInfo settleFilter=new FilterInfo();
//			settleFilter.getFilterItems().add(new FilterItemInfo("contractSettleBill.contractBill.id",this.editData.getContractBill().getId().toString()));
//			if(this.getBillInterface().exists(filter)){
//				this.actionRemoveLine.setEnabled(false);
//				this.actionPCSelect.setEnabled(false);
//			}else if(this.getBillInterface().exists(settleFilter)){
//				this.actionRemoveLine.setEnabled(false);
//				this.actionPCSelect.setEnabled(false);
//			}
//		}
	}
	protected boolean isShowAttachmentAction(){
        return false;
    }
	protected void setAuditButtonStatus(String oprtType) {
		super.setAuditButtonStatus(oprtType);
		if(this.oprtState.equals(OprtState.VIEW)){
			this.actionRemoveLine.setEnabled(false);
			this.actionPCSelect.setEnabled(false);
		}
		if(this.editData!=null){
			if(this.editData.getContractBill()!=null&&ContractPropertyEnum.SUPPLY.equals(this.editData.getContractBill().getContractPropert())){
				this.actionRemoveLine.setVisible(false);
				this.actionPCSelect.setVisible(false);
			}else if(this.editData.getContractChangeBill()!=null){
				this.contAmount.setBoundLabelText("预算金额");
				this.setUITitle("跨期合同变更指令合约规划拆分");
				this.actionRemoveLine.setVisible(false);
				this.actionPCSelect.setVisible(false);
			}else if(this.editData.getContractChangeSettleBill()!=null){
				this.contAmount.setBoundLabelText("最终审定金额");
				this.setUITitle("跨期合同变更确认合约规划拆分");
				this.actionRemoveLine.setVisible(false);
				this.actionPCSelect.setVisible(false);
			}else if(this.editData.getContractSettleBill()!=null){
				this.contAmount.setBoundLabelText("当前结算价");
				this.setUITitle("跨期合同结算合约规划拆分");
				this.actionRemoveLine.setVisible(false);
				this.actionPCSelect.setVisible(false);
			}
		}
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
	}
	public void afterActionPerformed(ActionEvent e) {
    	super.afterActionPerformed(e);
    	if(e.getSource()==btnRemove||e.getSource()==menuItemRemove){
    		if(hasRemove){
        		try {
        			setOprtState(OprtState.VIEW);
    				actionExitCurrent_actionPerformed(null);
    			} catch (Exception e1) {
    				handUIException(e1);
    			}
    		}
    	}
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		hasRemove=false;
        if(!confirmRemove()){
      	  return;
        }
        String tempState = this.getOprtState();
        this.setOprtState("REMOVE");
        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;
        getUIContext().put("CURRENT.VO",null) ;
        setDataObject(val) ;
        try
        {
        	IObjectPK pk = new ObjectUuidPK(this.editData.getId());
            this.getBizInterface().delete(pk);
            hasRemove=true;
        }
        finally
        {
            this.setOprtState(tempState);
        }
        setSave(true);
        setSaved(true);
	}
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				ContractPCSplitBillEntryInfo entry=(ContractPCSplitBillEntryInfo) table.getRow(top).getUserObject();
        		String pcId=entry.getProgrammingContract().getId().toString();
        		FilterInfo filter=new FilterInfo();
        		if(this.editData.getId()!=null){
    				filter.getFilterItems().add(new FilterItemInfo("head.id",editData.getId().toString(),CompareType.NOTEQUALS));
    			}
        		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcId));
    			try {
					if(ContractPCSplitBillEntryFactory.getRemoteInstance().exists(filter)){
						FDCMsgBox.showWarning(this,"第"+(top+1)+"行合约规划已经被关联，禁止删除！");
						SysUtil.abort();
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel=super.getSelectors();
		sel.add("curProject.*");
		sel.add("curProject.fullOrgUnit.*");
		sel.add("splitState");
		sel.add("entry.programmingContract.*");
		
		sel.add("contractBill.number");
		sel.add("contractBill.name");
		sel.add("contractBill.amount");
		sel.add("contractBill.contractPropert");
		sel.add("contractBill.contractType.id");
		
		sel.add("contractChangeBill.contractBill.number");
		sel.add("contractChangeBill.contractBill.name");
	 	sel.add("contractChangeBill.amount");
	 	
		sel.add("contractChangeSettleBill.contractBill.number");
		sel.add("contractChangeSettleBill.contractBill.name");
	 	sel.add("contractChangeSettleBill.allowAmount");
		
		sel.add("contractSettleBill.contractBill.number");
		sel.add("contractSettleBill.contractBill.name");
		sel.add("contractSettleBill.curSettlePrice");
		return sel;
	}
	public void actionPCSelect_actionPerformed(ActionEvent e) throws Exception {
		ProgrammingContractCollection pcc=null;
		ProgrammingContractCollection addPcc=new ProgrammingContractCollection();
		if(this.editData.getContractBill()==null&&this.editData.getContractChangeSettleBill()==null&&this.editData.getContractSettleBill()==null){
			FDCMsgBox.showWarning(this,"关联单据不能为空！");
			SysUtil.abort();
		}
		if(selectUI==null){
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",this.editData.getCurProject());
			uiContext.put("contractType",this.editData.getContractBill().getContractType());
			selectUI=UIFactory.createUIFactory(UIFactoryName.MODEL).create(PCSelectUI.class.getName(),uiContext, null , null);       
		}else{
			((PCSelectUI) selectUI.getUIObject()).actionNoneSelect_actionPerformed(null);
		}
		selectUI.show();
		IUIWindow uiWin=selectUI;
		if (((PCSelectUI) uiWin.getUIObject()).isOk()) {	
			pcc=((PCSelectUI) uiWin.getUIObject()).getData();
		}else{
			return;
		}
		if(pcc!=null){
			for(int i=0;i<pcc.size();i++){
				boolean isAdd=true;
				ProgrammingContractInfo pc=pcc.get(i);
				for(int j=0;j<this.getDetailTable().getRowCount();j++){
					ContractPCSplitBillEntryInfo entry=(ContractPCSplitBillEntryInfo) this.getDetailTable().getRow(j).getUserObject();
					if(entry.getProgrammingContract().getId().toString().equals(pc.getId().toString())){
						isAdd=false;
					}
				}
				if(isAdd){
					addPcc.add(pc);
				}
			}
			for(int i=0;i<addPcc.size();i++){
				ProgrammingContractInfo pc=addPcc.get(i);
				ContractPCSplitBillEntryInfo entry=new ContractPCSplitBillEntryInfo();
				entry.setProgrammingContract(pc);
				IRow addRow=this.getDetailTable().addRow();
				addRow.setUserObject(entry);
				addRow.getCell("curProject").setValue(pc.getProgramming().getProject().getName());
				addRow.getCell("pcNumber").setValue(pc.getLongNumber());
				addRow.getCell("pcName").setValue(pc.getName());
				addRow.getCell("pcAmount").setValue(pc.getAmount());
			}
		}
	}
	protected void verifyInputForSave() throws Exception {
		if(this.editData.getContractBill()==null&&this.editData.getContractChangeBill()==null&&this.editData.getContractChangeSettleBill()==null&&this.editData.getContractSettleBill()==null){
			FDCMsgBox.showWarning(this,"关联单据不能为空！");
			SysUtil.abort();
		}
		if(this.kdtEntrys.getRowCount()==0){
			FDCMsgBox.showWarning(this,"分录不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
			IRow row = this.kdtEntrys.getRow(i);
			BigDecimal scale = (BigDecimal)row.getCell("scale").getValue();
			if (scale == null){
				FDCMsgBox.showWarning(this,"分录拆分比例不能为空！");
				this.kdtEntrys.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntrys.getColumnIndex("scale"));
				SysUtil.abort();
			}
			if (scale.compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"分录拆分比例不能为0！");
				this.kdtEntrys.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntrys.getColumnIndex("scale"));
				SysUtil.abort();
			}
			BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
			if (amount == null){
				FDCMsgBox.showWarning(this,"分录拆分金额不能为空！");
				this.kdtEntrys.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntrys.getColumnIndex("amount"));
				SysUtil.abort();
			}
//			if (amount.compareTo(FDCHelper.ZERO)==0){
//				FDCMsgBox.showWarning(this,"分录拆分金额不能为0！");
//				this.kdtEntrys.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntrys.getColumnIndex("amount"));
//				SysUtil.abort();
//			}
		}
		
    	BigDecimal unSplitAmount=this.txtUnSplitAmount.getBigDecimalValue();
    	if(unSplitAmount.compareTo(FDCHelper.ZERO)<0){
			FDCMsgBox.showWarning(this, FDCSplitClientHelper.getRes("moreThan"));
			SysUtil.abort();
    	}
    	if(this.editData.getContractBill()!=null&&!ContractPropertyEnum.SUPPLY.equals(this.editData.getContractBill().getContractPropert())){
    		for(int i=0;i<this.getDetailTable().getRowCount();i++){
        		ContractPCSplitBillEntryInfo entry=(ContractPCSplitBillEntryInfo) this.getDetailTable().getRow(i).getUserObject();
        		String pcId=entry.getProgrammingContract().getId().toString();
        		FilterInfo filter=new FilterInfo();
    			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcId));
    			if(ContractBillFactory.getRemoteInstance().exists(filter)){
    				FDCMsgBox.showWarning(this,"第"+(i+1)+"行合约规划已经被其他分期合同关联，请重新选择！");
    				SysUtil.abort();
    			}
    			if(this.editData.getId()!=null){
    				filter.getFilterItems().add(new FilterItemInfo("head.id",editData.getId().toString(),CompareType.NOTEQUALS));
    			}
    			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.id",null,CompareType.NOTEQUALS));
    			filter.getFilterItems().add(new FilterItemInfo("head.contractBill.contractPropert",ContractPropertyEnum.SUPPLY_VALUE,CompareType.NOTEQUALS));
    			if(ContractPCSplitBillEntryFactory.getRemoteInstance().exists(filter)){
    				FDCMsgBox.showWarning(this,"第"+(i+1)+"行合约规划已经被其他跨期合同关联，请重新选择！");
    				SysUtil.abort();
    			}
    		}
    	}
	}
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		IRow row=this.getDetailTable().getRow(e.getRowIndex());
		BigDecimal contractAmount=this.txtAmount.getBigDecimalValue();
		if (e.getColIndex()==this.getDetailTable().getColumnIndex("amount")){
			BigDecimal amount=(BigDecimal) row.getCell("amount").getValue();
			if(amount!=null){
				row.getCell("scale").setValue(amount.multiply(new BigDecimal(100)).divide(contractAmount,2,BigDecimal.ROUND_HALF_UP));
			}else{
				row.getCell("scale").setValue(null);
			}
		}else if(e.getColIndex()==this.getDetailTable().getColumnIndex("scale")){
			BigDecimal scale=(BigDecimal) row.getCell("scale").getValue();
			if(scale!=null){
				row.getCell("amount").setValue(contractAmount.multiply(scale).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP));
			}else{
				row.getCell("amount").setValue(null);
			}
		}
		getTotal();
	}
}