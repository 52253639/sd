/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

import java.awt.Component;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationContractEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationContractEntryFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationContractEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationIndexValueInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Collection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Factory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Info;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.client.SelectContractUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;

/**
 * output class name
 */
public class MarketSupplierEvaluationEditUI extends AbstractMarketSupplierEvaluationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierEvaluationEditUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierEvaluationEditUI() throws Exception
    {
        super();
    }
    
    
    public void storeFields()
    {
    	this.editData.getContractEntry().clear();
		for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
			MarketSupplierEvaluationContractEntryInfo  entry=(MarketSupplierEvaluationContractEntryInfo) this.kdtContractEntry.getRow(i).getUserObject();
			boolean isHasContract=((Boolean) this.kdtContractEntry.getRow(i).getCell("isHasContract").getValue()).booleanValue();
			entry.setIsHasContract(isHasContract);
			if(isHasContract){
				entry.setContractBill((ContractBillInfo)this.kdtContractEntry.getRow(i).getCell("contract").getValue());
			}else{
				String contract=(String)this.kdtContractEntry.getRow(i).getCell("contract").getValue();
				if(contract!=null){
					contract=contract.trim();
				}
				entry.setContract(contract);
			}
			entry.setContractAmount((BigDecimal)this.kdtContractEntry.getRow(i).getCell("contractAmount").getValue());
			this.editData.getContractEntry().add(entry);
		}
    	setSupplierInfo((MarketSupplierStockInfo) this.prmtMarketsupplier.getValue());
        super.storeFields();
    }
    protected void loadContractEntry(){
    	MarketSupplierEvaluationContractEntryCollection col=this.editData.getContractEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtContractEntry.removeRows();
		for(int i=0;i<col.size();i++){
			MarketSupplierEvaluationContractEntryInfo entry =col.get(i);
			IRow row=this.kdtContractEntry.addRow();
			row.setUserObject(entry);
			row.getCell("isHasContract").setValue(Boolean.valueOf(entry.isIsHasContract()));
			setEntryContractEditor(i);
			if(entry.isIsHasContract()){
				row.getCell("contract").setValue(entry.getContractBill());
			}else{
				row.getCell("contract").setValue(entry.getContract());
			}
			row.getCell("contractAmount").setValue(entry.getContractAmount());
		}
    }
    public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getMarketsupplier()!=null){
			this.txtSplArea.setText(getSupplierSplArea(this.editData.getMarketsupplier()));
			if(this.editData.getMarketsupplier().getInviteType()!=null){
				try {
					this.txtInviteType.setText(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(this.editData.getMarketsupplier().getInviteType().getId())).getName());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
       	 	}
			if(this.editData.getMarketsupplier().getSupplierBusinessMode()!=null){
     			try {
					this.txtBusinessMode.setText(MarketSupplierBusinessModeFactory.getRemoteInstance().getMarketSupplierBusinessModeInfo(new ObjectUuidPK(this.editData.getMarketsupplier().getSupplierBusinessMode().getId())).getName());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		TableUtils.getFootRow(this.kdtEntry,new String[]{"Mweight"});
		BigDecimal score =getTotalScore(this.kdtEntry,"score","Mweight");
        this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
        
		this.loadContractEntry();
		setEntryScorePass();
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
    
    public void setEntryScorePass(){
    	Map map = new HashMap();
        if(this.prmtsupplierTemple.getValue()!=null){
			if(this.prmtsupplierTemple.getValue()!=null){
				String tempID = ((MarketSupplierAppraiseTemplateInfo)this.prmtsupplierTemple.getValue()).getId().toString();
				try {
					String oql = "select id where id='"+tempID+"' ";
					MarketSupplierAppraiseTemplateInfo  tempinfo =MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(oql);
					MarketSupplierAppraiseTemplateE1Collection e1col = tempinfo.getE1();
					for (int i = 0; i < e1col.size(); i++) {
						MarketSupplierAppraiseTemplateE1Info e1info = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK(((MarketSupplierAppraiseTemplateE1Info)e1col.get(i)).getId()));
						map.put(e1info.getIndexName().getId().toString(), e1info.getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			MarketSplAuditIndexInfo entry=(MarketSplAuditIndexInfo) this.kdtEntry.getRow(i).getCell("MguideName").getValue();
			MarketSupplierAppraiseTemplateE1Info  E1entry = null;
			String evalType = entry.getId().toString();
			try {
				if(map.get(evalType)!=null){
					E1entry = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
					.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK((String)map.get(evalType)));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(E1entry!=null&&AppraiseTypeEnum.PASS.equals(E1entry.getScoreType())){
				this.kdtEntry.getRow(i).getCell("score").getStyleAttributes().setLocked(true);
				this.kdtEntry.getRow(i).getCell("isPass").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}else{
				this.kdtEntry.getRow(i).getCell("isPass").getStyleAttributes().setLocked(true);
				this.kdtEntry.getRow(i).getCell("score").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}
		}
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("orgUnit.*");
    	sel.add("indexValue.templateEntry.appraiseType");
    	sel.add("contractEntry.*");
    	sel.add("contractEntry.contractBill.id");
    	sel.add("contractEntry.contractBill.number");
    	sel.add("contractEntry.contractBill.name");
    	sel.add("contractEntry.contractBill.amount");
		return sel;
	}
	protected String getSupplierSplArea(MarketSupplierStockInfo info){
		if(info==null) return null;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplierSplAreaEntry.fdcSplArea.*");
		try {
			info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(info.getId()), sel);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		String text="";
		MarketSupplierStockSupplierSplAreaEntryCollection col=info.getSupplierSplAreaEntry();
		for(int i=0;i<col.size();i++) {
			MarketSupplierStockSupplierSplAreaEntryInfo entry = col.get(i);
			if(i==col.size()-1){
				text=text+entry.getFdcSplArea().getName();
			}else{
				text=text+entry.getFdcSplArea().getName()+";";
			}
		}
    	return text;
    }
	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		this.prmtEvaluationType.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketAccreditationTypeQuery");
		this.prmtsupplierTemple.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.app.F7MarketSupplierAppraiseTemplateQuery");
		this.prmtMarketsupplier.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                	NewprmtSupplier_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		
		this.prmtsupplierTemple.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    NewprmtTemplate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		
		
		
		
		initControl();
		setEntryScorePass();
	}
	protected void initTable(){
		this.kdtEntry.checkParsed();
		prmtsupplierTemple.setRequired(true);
		
		this.kdtEntry.getColumn("MguideType").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("MguideName").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("threeBz").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("Mweight").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("Mweight").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("Mweight").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.person.app.F7PersonQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("PositionMember.isPrimary",Boolean.TRUE));
//		filter.getFilterItems().add(new FilterItemInfo("deletedStatus",DeletedStatusEnum.NORMAL_VALUE));
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntry.getColumn("auditPerson").setEditor(f7Editor);
		this.prmtMarketsupplier.setRequired(true);
		f7Box = new KDBizPromptBox();
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery");
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		view.setFilter(filter);
		f7Box.setEntityViewInfo(view);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntry.getColumn("auditDep").setEditor(f7Editor);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(new BigDecimal(5));
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtEntry.getColumn("score").setEditor(weight);
		this.kdtEntry.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < IsGradeEnum.getEnumList().size(); i++){
        	combo.addItem(IsGradeEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("isPass").setEditor(comboEditor);
		
		this.kdtEntry.getColumn("remark").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtEntry.getColumn("auditPerson").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtEntry.getColumn("auditDep").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.kdtContractEntry.checkParsed();
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtContractEntry.getColumn("isHasContract").setEditor(editor);
 		
 		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
 		this.kdtContractEntry.getColumn("contractAmount").setEditor(amountEditor);
 		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtContractEntry.getColumn("isHasContract").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtContractEntry.getColumn("contract").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
	}
	protected void initControl() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set org=new HashSet();
		if(editData.getOrgUnit()!=null){
			org.add(editData.getOrgUnit().getId().toString());
		}
		org.add(OrgConstants.DEF_CU_ID);
		filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
		view.setFilter(filter);
		this.prmtMarketsupplier.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtEvaluationType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		if(this.prmtEvaluationType.getValue()!=null){
			filter.getFilterItems().add(new FilterItemInfo("AccreditationType.id",((MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue()).getId()));
		}
		view.setFilter(filter);
		this.prmtsupplierTemple.setEntityViewInfo(view);
		
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		setComEnable();
	}
	protected void setComEnable(){
//		MarketAccreditationTypeInfo type=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
//		if(type!=null){
//			if(type.getNumber().equals("003")||type.getNumber().equals("004")||
//					type.getNumber().equals("005")){
//				if(this.oprtState.equals(OprtState.VIEW)){
//					this.btnALine.setEnabled(false);
//					this.btnILine.setEnabled(false);
//					this.btnRLine.setEnabled(false);
//				}else{
//					this.btnALine.setEnabled(true);
//					this.btnILine.setEnabled(true);
//					this.btnRLine.setEnabled(true);
//				}
//			}else{
//				this.btnALine.setEnabled(false);
//				this.btnILine.setEnabled(false);
//				this.btnRLine.setEnabled(false);
//			}
//		}else{
//			this.btnALine.setEnabled(false);
//			this.btnILine.setEnabled(false);
//			this.btnRLine.setEnabled(false);
//		}
		
		if(this.oprtState.equals(OprtState.VIEW)){
			this.btnALine.setEnabled(false);
			this.btnILine.setEnabled(false);
			this.btnRLine.setEnabled(false);
		}else{
			this.btnALine.setEnabled(true);
			this.btnILine.setEnabled(true);
			this.btnRLine.setEnabled(true);
		}
		if(this.prmtMarketsupplier.getValue()!=null&&this.prmtEvaluationType.getValue()!=null){
			this.prmtsupplierTemple.setEnabled(true);
		}else{
			this.prmtsupplierTemple.setEnabled(false);
		}
	}
	protected void NewprmtSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception{
		 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         MarketSupplierStockInfo info=(MarketSupplierStockInfo) this.prmtMarketsupplier.getValue();
         if(info==null){
        	 this.txtSplArea.setText(null);
     		 this.txtInviteType.setText(null);
     		 this.txtSupplierName.setText(null);
    		 this.txtBusinessMode.setText(null);
    		 
    		 this.txtPartProject.setText(null);
     		 this.txtLinkPerson.setText(null);
     		 this.txtLinkPhone.setText(null);
     		 this.txtLinkJob.setText(null);
     		 this.txtContractor.setText(null);
     		 this.txtContractorPhone.setText(null);
     		 this.txtManager.setText(null);
     		 this.txtManagerPhone.setText(null);
         }else{
        	 this.txtSplArea.setText(getSupplierSplArea(info));
        	 if(info.getInviteType()!=null){
        		 this.txtInviteType.setText(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(info.getInviteType().getId())).getName());
        	 }
     		 this.txtSupplierName.setText(info.getName());
     		 if(info.getSupplierBusinessMode()!=null){
     			this.txtBusinessMode.setText(MarketSupplierBusinessModeFactory.getRemoteInstance().getMarketSupplierBusinessModeInfo(new ObjectUuidPK(info.getSupplierBusinessMode().getId())).getName());
     		 }
     		 
     		 this.txtPartProject.setText(info.getPartProject());
     		 this.txtLinkPerson.setText(info.getAuthorizePerson());
     		 this.txtLinkPhone.setText(info.getAuthorizePhone());
     		 this.txtLinkJob.setText(info.getAuthorizeJob());
     		 this.txtContractor.setText(info.getContractor());
     		 this.txtContractorPhone.setText(info.getContractorPhone());
     		 this.txtManager.setText(info.getManager());
     		 this.txtManagerPhone.setText(info.getManagerPhone());
         }
         setComEnable();
	}
	protected void prmtEvaluationType_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        MarketAccreditationTypeInfo info=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
        
        EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		if(info!=null){
			filter.getFilterItems().add(new FilterItemInfo("AccreditationType.id",info.getId()));
		}
		view.setFilter(filter);
		this.prmtsupplierTemple.setEntityViewInfo(view);
		
		if(this.prmtMarketsupplier.getValue()!=null&&this.prmtEvaluationType.getValue()!=null){
			if(this.prmtsupplierTemple.getValue()!=null){
				if(!((MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue()).getId().toString().equals(((MarketSupplierAppraiseTemplateInfo)this.prmtsupplierTemple.getValue()).getAccreditationType().getId().toString())){
					this.prmtsupplierTemple.setValue(null);
				}
			}
		}else{
			this.prmtsupplierTemple.setValue(null);
		}
		if(info!=null){
			if(!(info.getNumber().equals("003")||info.getNumber().equals("004")||
					info.getNumber().equals("005"))){
				this.kdtContractEntry.removeRows();
			}
		}
		setComEnable();
	}
	
	protected AdminOrgUnitInfo getDepByPerson(PersonInfo person) throws BOSException{
		SelectorItemCollection personsel=new SelectorItemCollection();
		personsel.add("position.adminOrgUnit.*");
		if(person!=null){
			EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("person.id",person.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isPrimary",Boolean.TRUE));
    		view.setFilter(filter);
    		view.setSelector(personsel);
			
			PositionMemberCollection pmcol=PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
			if(pmcol.size()>0){
				return pmcol.get(0).getPosition().getAdminOrgUnit();
			}
		}
		return null;
	}
	protected void NewprmtTemplate_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        MarketSupplierAppraiseTemplateInfo info=(MarketSupplierAppraiseTemplateInfo)this.prmtsupplierTemple.getValue();
        boolean isShowWarn=false;
        boolean isUpdate=false;
        if(this.kdtEntry.getRowCount()>0||this.kdtEntry.getRowCount()>0){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(FDCMsgBox.showConfirm2(this, "评审模板改变会覆盖供应商评审数据，是否继续？")== FDCMsgBox.YES){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        if(isUpdate){
        	this.kdtEntry.removeRows();
        	if(info!=null){
        		this.storeFields();
        		SelectorItemCollection sel=super.getSelectors();
        		sel.add("evaluationType.*");
        		sel.add("guideEntry.*");
            	sel.add("guideEntry.splAuditIndex.*");
            	sel.add("guideEntry.splAuditIndex.indexGroup.*");
            	
            	info=MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(info.getId()), sel);
        		this.editData.setEvaluationType(info.getAccreditationType());
        		MarketSupplierAppraiseTemplateE1Collection guideEntry=info.getE1();
        		for(int i=0;i<guideEntry.size();i++){
        			MarketSupplierAppraiseTemplateE1Info e1info = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
        			.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK(((MarketSupplierAppraiseTemplateE1Info)guideEntry.get(i)).getId()));
        			IRow  irow = kdtEntry.addRow();
        			irow.getCell("MguideType").setValue(e1info.getAccreditationwd());
        			if(e1info.getIndexName()!=null){
        				irow.getCell("MguideName").setValue(MarketSplAuditIndexFactory.getRemoteInstance().getMarketSplAuditIndexInfo(new ObjectUuidPK(e1info.getIndexName().getId())));
        			}
        			irow.getCell("threeBz").setValue(e1info.getThreeStandard());
        			irow.getCell("Mweight").setValue(e1info.getQz());
        		}
        	}else{
        		TableUtils.getFootRow(this.kdtEntry,new String[]{"Mweight"});
        		BigDecimal score =getTotalScore(this.kdtEntry,"score","Mweight");
                this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
        	}
        }
        setEntryScorePass();
        TableUtils.getFootRow(this.kdtEntry,new String[]{"Mweight"});
		BigDecimal score =getTotalScore(this.kdtEntry,"score","Mweight");
        this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
	}
	public static BigDecimal getTotalScore(KDTable kdtEntry,String scoreName,String weightName){
		BigDecimal sum = new BigDecimal(0);
		for(int i=0;i<kdtEntry.getRowCount();i++){
			IRow row=kdtEntry.getRow(i);
			if(row.getCell(scoreName).getValue() instanceof BigDecimal){
				BigDecimal score=row.getCell(scoreName).getValue()!=null?(BigDecimal)row.getCell(scoreName).getValue():BigDecimal.ZERO;
				BigDecimal weight=row.getCell(weightName).getValue()!=null?(BigDecimal)row.getCell(weightName).getValue():BigDecimal.ZERO;
				if(score!=null&&weight!=null){
					sum=sum.add(score.multiply(weight).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		sum=sum.multiply(new BigDecimal(100).divide(new BigDecimal(5), 2, BigDecimal.ROUND_HALF_UP));
		return sum;
	}
	protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception{
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("score")){
			BigDecimal score =getTotalScore(this.kdtEntry,"score","Mweight");
	    	if(score.compareTo(new BigDecimal("100")) >0){
	    		FDCMsgBox.showWarning(this, "评分之和不能大于100！");
	    		this.kdtEntry.getRow(e.getRowIndex()).getCell("score").setValue(null);
	    	}
	        this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("auditPerson")){
			PersonInfo person=(PersonInfo) this.kdtEntry.getRow(e.getRowIndex()).getCell("auditPerson").getValue();
			if(person!=null){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("auditDep").setValue(getDepByPerson(person));
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("auditDep").setValue(null);
			}
		}
		TableUtils.getFootRow(this.kdtEntry,new String[]{"Mweight"});
		BigDecimal score =getTotalScore(this.kdtEntry,"score","Mweight");
        this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
    }
	protected boolean getContractEntry() throws BOSException, EASBizException{
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtMarketsupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtsupplierTemple);
		
//		MarketAccreditationTypeInfo type=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
//		if(!type.getNumber().equals("005")&&this.kdtContractEntry.getRowCount()==1){
//			FDCMsgBox.showWarning(this,"只有履约综合评估才能新增多条合同分录！");
//			SysUtil.abort();
//		}
//		if(this.kdtContractEntry.getRowCount()>0){
//			return false;
//		}
//		
//		FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("head.supplier.id",((MarketSupplierStockInfo)this.prmtMarketsupplier.getValue()).getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("head.evaluationType.id",((MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue()).getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("head.template.id",((MarketSupplierAppraiseTemplateInfo)this.prmtsupplierTemple.getValue()).getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("head.orgUnit.id",this.editData.getOrgUnit().getId().toString()));
//		if(this.editData.getId()!=null){
//			filter.getFilterItems().add(new FilterItemInfo("head.id",this.editData.getOrgUnit().getId().toString(),CompareType.NOTEQUALS));
//		}
//		if(MarketSupplierEvaluationContractEntryFactory.getRemoteInstance().exists(filter)){
//			if (FDCMsgBox.showConfirm2(this,"已有关联合同分录，是否引用？") == MsgBox.OK) {
//				UIContext uiContext = new UIContext(this);
//				
//				uiContext.put("supplier", this.prmtMarketsupplier.getValue());
//				uiContext.put("type", this.prmtEvaluationType.getValue());
//				uiContext.put("template", this.prmtsupplierTemple.getValue());
//				uiContext.put("editData", this.editData);
//				uiContext.put("table", this.kdtContractEntry);
//				uiContext.put("orgUnit", this.editData.getOrgUnit());
//				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectContractUI.class.getName(), uiContext, null, OprtState.VIEW);
//				uiWindow.show();
//			}
//		}
		return false;
	}
	protected void btnALine_actionPerformed(ActionEvent e) throws Exception {
		if(getContractEntry()){
			return;
		}
		IRow row=this.kdtContractEntry.addRow();
		MarketSupplierEvaluationContractEntryInfo entry=new MarketSupplierEvaluationContractEntryInfo();
		row.setUserObject(entry);
		row.getCell("isHasContract").setValue(Boolean.FALSE);
	}
	protected void btnILine_actionPerformed(ActionEvent e) throws Exception {
		if(getContractEntry()){
			return;
		}
		IRow row = null;
		if (this.kdtContractEntry.getSelectManager().size() > 0) {
			int top = this.kdtContractEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtContractEntry)){
				row = this.kdtContractEntry.addRow();
			}else{
				row = this.kdtContractEntry.addRow(top);
			}
		} else {
			row = this.kdtContractEntry.addRow();
		}
		MarketSupplierEvaluationContractEntryInfo entry=new MarketSupplierEvaluationContractEntryInfo();
		row.setUserObject(entry);
		row.getCell("isHasContract").setValue(Boolean.FALSE);
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	protected void btnRLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtContractEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtContractEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtContractEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtContractEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtContractEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtContractEntry.removeRow(top);
			}
		}
	}
	protected void setEntryContractEditor(int row){
		boolean isHasContract=((Boolean) this.kdtContractEntry.getRow(row).getCell("isHasContract").getValue()).booleanValue();
		this.kdtContractEntry.getRow(row).getCell("contract").setValue(null);
		this.kdtContractEntry.getRow(row).getCell("contractAmount").setValue(null);
		if(isHasContract){
			KDBizPromptBox prmtcontract = new KDBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7Query");

			prmtcontract.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
					f7.getQueryAgent().resetRuntimeEntityView();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
					if(editData.getOrgUnit()!=null){
						try {
							FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(editData.getOrgUnit().getId()));
							filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitInfo.getLongNumber()+"%",CompareType.LIKE));
						} catch (EASBizException e1) {
							e1.printStackTrace();
						} catch (BOSException e1) {
							e1.printStackTrace();
						}
					}
					view.setFilter(filter);
					f7.setEntityViewInfo(view);
				}
			});
			KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(prmtcontract);
			this.kdtContractEntry.getRow(row).getCell("contract").setEditor(contractEditor);
			ObjectValueRender ovrName = new ObjectValueRender();
			ovrName.setFormat(new BizDataFormat("$name$"));
			this.kdtContractEntry.getColumn("contract").setRenderer(ovrName);
			
			this.kdtContractEntry.getRow(row).getCell("contractAmount").getStyleAttributes().setLocked(true);
		}else{
			KDTextField contract = new KDTextField();
			contract.setMaxLength(200);
			KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(contract);
			this.kdtContractEntry.getRow(row).getCell("contract").setEditor(contractEditor);
			this.kdtContractEntry.getRow(row).getCell("contractAmount").getStyleAttributes().setLocked(false);
		}
	}
	protected void kdtContractEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("isHasContract")){
			setEntryContractEditor(e.getRowIndex());
		}else{
			if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("contract")){
				Object contract=this.kdtContractEntry.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
				if(contract!=null){
					if(contract instanceof ContractBillInfo){
						this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").setValue(((ContractBillInfo) contract).getAmount());
					}
				}else{
					if(contract instanceof ContractBillInfo){
						this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").setValue(null);
					}
				}
			}
		}
	}
	protected void attachListeners() {
		addDataChangeListener(this.prmtMarketsupplier);
		addDataChangeListener(this.prmtEvaluationType);
		addDataChangeListener(this.prmtsupplierTemple);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtMarketsupplier);
		removeDataChangeListener(this.prmtEvaluationType);
		removeDataChangeListener(this.prmtsupplierTemple);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MarketSupplierEvaluationFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		MarketSupplierEvaluationInfo info= new MarketSupplierEvaluationInfo();
		if(getUIContext().get("org")!=null){
			info.setOrgUnit(((OrgStructureInfo) getUIContext().get("org")).getUnit());
		}
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		return info;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void setSupplierInfo(MarketSupplierStockInfo info){
		if(info!=null){
			info.setPartProject(this.txtPartProject.getText());
			info.setAuthorizePerson(this.txtLinkPerson.getText());
			info.setAuthorizePhone(this.txtLinkPhone.getText());
			info.setAuthorizeJob(this.txtLinkJob.getText());
			info.setContractor(this.txtContractor.getText());
			info.setContractorPhone(this.txtContractorPhone.getText());
			info.setManager(this.txtManager.getText());
			info.setManagerPhone(this.txtManagerPhone.getText());
		}
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtMarketsupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkPerson);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtContractor);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtContractorPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtManager);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtManagerPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtsupplierTemple);
		
		Map map = new HashMap();
        if(this.prmtsupplierTemple.getValue()!=null){
			if(this.prmtsupplierTemple.getValue()!=null){
				String tempID = ((MarketSupplierAppraiseTemplateInfo)this.prmtsupplierTemple.getValue()).getId().toString();
				try {
					String oql = "select id where id='"+tempID+"' ";
					MarketSupplierAppraiseTemplateInfo  tempinfo =MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(oql);
					MarketSupplierAppraiseTemplateE1Collection e1col = tempinfo.getE1();
					for (int i = 0; i < e1col.size(); i++) {
						MarketSupplierAppraiseTemplateE1Info e1info = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK(((MarketSupplierAppraiseTemplateE1Info)e1col.get(i)).getId()));
						map.put(e1info.getIndexName().getId().toString(), e1info.getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		
		MarketAccreditationTypeInfo type=(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue();
		if(type.getNumber().equals("002")){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("Marketsupplier.id",((MarketSupplierStockInfo)this.prmtMarketsupplier.getValue()).getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("evaluationType.number","001"));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",this.editData.getOrgUnit().getId().toString()));
			if(!MarketAccreditationTypeFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"供应商考察评审之前必须进行供应商初审！");
				SysUtil.abort();
			}
		}
		if(type.getNumber().equals("003")||type.getNumber().equals("004")||
				type.getNumber().equals("005")){
			if(this.kdtContractEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"合同分录不能为空！");
				SysUtil.abort();
			}
			for(int i = 0; i < this.kdtContractEntry.getRowCount(); i++){
				IRow row=this.kdtContractEntry.getRow(i);
				boolean isHasContract=((Boolean) this.kdtContractEntry.getRow(i).getCell("isHasContract").getValue()).booleanValue();
				if(isHasContract){
					if(row.getCell("contract").getValue()==null){
						FDCMsgBox.showWarning(this,"合同名称不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
						SysUtil.abort();
					}
				}else{
					if(row.getCell("contract").getValue()==null||"".equals(row.getCell("contract").getValue().toString().trim())){
						FDCMsgBox.showWarning(this,"合同名称不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
						SysUtil.abort();
					}
					if(row.getCell("contractAmount").getValue()==null){
						FDCMsgBox.showWarning(this,"合同签订金额不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contractAmount"));
						SysUtil.abort();
					}
					if(((BigDecimal)row.getCell("contractAmount").getValue()).compareTo(FDCHelper.ZERO)==0){
						FDCMsgBox.showWarning(this,"合同签订金额不能为0！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contractAmount"));
						SysUtil.abort();
					}
				}
				for(int j=0;j<this.kdtContractEntry.getRowCount();j++){
					if(j==i) continue;
					IRow nRow=this.kdtContractEntry.getRow(j);
					boolean nIsHasContract=((Boolean) this.kdtContractEntry.getRow(j).getCell("isHasContract").getValue()).booleanValue();
					if(nIsHasContract==isHasContract){
						if(isHasContract){
							if(nRow.getCell("contract").getValue()==null){
								continue;
							}
							ContractBillInfo contract=(ContractBillInfo) row.getCell("contract").getValue();
							ContractBillInfo nContract=(ContractBillInfo) nRow.getCell("contract").getValue();
							if(contract.getId().toString().equals(nContract.getId().toString())){
								FDCMsgBox.showWarning(this,"合同名称不能重复！");
								this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
								SysUtil.abort();
							}
						}else{
							if(nRow.getCell("contract").getValue()==null||"".equals(nRow.getCell("contract").getValue().toString().trim())){
								continue;
							}
							String contract=(String) row.getCell("contract").getValue();
							String nContract=(String) nRow.getCell("contract").getValue();
							if(contract.toString().equals(nContract.toString())){
								FDCMsgBox.showWarning(this,"合同名称不能重复！");
								this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
								SysUtil.abort();
							}
						}
					}
				}
			}
		}
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"供应商评审不能为空！");
			SysUtil.abort();
		}
		
		for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
			IRow row=this.kdtEntry.getRow(i);
			MarketSplAuditIndexInfo E1entry=(MarketSplAuditIndexInfo) row.getCell("MguideName").getValue();
			MarketSupplierAppraiseTemplateE1Info  entry = null;
			String evalType = E1entry.getId().toString();
			try {
				if(map.get(evalType)!=null){
					entry = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
					.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK((String)map.get(evalType)));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(AppraiseTypeEnum.WEIGHT.equals(entry.getScoreType())){
				if(row.getCell("score").getValue()==null){
					FDCMsgBox.showWarning(this,"供应商评审评分不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("score"));
					SysUtil.abort();
				}
			}else{
				if(row.getCell("isPass").getValue()==null){
					FDCMsgBox.showWarning(this,"供应商评审合格与否不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("isPass"));
					SysUtil.abort();
				}
			}
		}
		boolean isWarning=true;
		if(type.getNumber().equals("001")){
			for(int i = 0; i < this.kdtEntry.getRowCount(); i++){
				IRow row=this.kdtEntry.getRow(i);
				MarketSplAuditIndexInfo E1entry=(MarketSplAuditIndexInfo) row.getCell("MguideName").getValue();
				MarketSupplierAppraiseTemplateE1Info  entry = null;
				String evalType = E1entry.getId().toString();
				try {
					if(map.get(evalType)!=null){
						entry = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK((String)map.get(evalType)));
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				if(AppraiseTypeEnum.PASS.equals(entry.getScoreType())){
					if(row.getCell("isPass").getValue()!=null&&IsGradeEnum.ENGRADE.equals(row.getCell("isPass").getValue())){
						isWarning=false;
						break;
					}
				}
			}
		}
		if(isWarning){
			for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
				IRow row=this.kdtEntry.getRow(i);
				if(row.getCell("score").getValue()!=null||row.getCell("isPass").getValue()!=null){
					if(row.getCell("remark").getValue()==null||"".equals(row.getCell("remark").getValue().toString().trim())){
						FDCMsgBox.showWarning(this,"情况描述不能为空！");
						this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("remark"));
						SysUtil.abort();
					}
					if(row.getCell("auditPerson").getValue()==null){
						FDCMsgBox.showWarning(this,"供应商评审评审人不能为空！");
						this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("auditPerson"));
						SysUtil.abort();
					}
					if(row.getCell("auditDep").getValue()==null){
						FDCMsgBox.showWarning(this,"供应商评审评审部门不能为空！");
						this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("auditDep"));
						SysUtil.abort();
					}
				}
			}
		}
	}
	protected void setAuditButtonStatus(String oprtType){
    	super.setAuditButtonStatus(oprtType);
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		}
    }
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
		setComEnable();
	}


}