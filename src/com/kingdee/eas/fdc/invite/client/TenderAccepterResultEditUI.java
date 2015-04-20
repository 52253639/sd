/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.BaseInviteCollection;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteBidEvaluationCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormInfo;
import com.kingdee.eas.fdc.invite.InviteClarifyCollection;
import com.kingdee.eas.fdc.invite.InviteClarifyInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsInfo;
import com.kingdee.eas.fdc.invite.InviteFixCollection;
import com.kingdee.eas.fdc.invite.InviteFixFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadCollection;
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadInfo;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningCollection;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo;
import com.kingdee.eas.fdc.invite.MaterialSampleCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierStockEditUI;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


public class TenderAccepterResultEditUI extends AbstractTenderAccepterResultEditUI
{
	private Map tableMaps=new HashMap();
	public TenderAccepterResultEditUI() throws Exception {
		super();
	}
	public void storeFields()
	{
		storeEntry();
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		loadEntry();
		try {
			InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection("select programmingContract.amount from where parent.id='"+this.editData.getInviteProject().getId().toString()+"'");
			if(col.size()>0&&col.get(0).getProgrammingContract()!=null){
				this.txtAmount.setValue(col.get(0).getProgrammingContract().getAmount());
			}
			loadRecordTable();
		} catch (BOSException e) {
			this.handleException(e);
		}
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	private void loadEntry(){
		tableMaps=new HashMap();
		this.entryPanel.removeAll();
		for(int i=0;i<this.editData.getEntrys().size();i++){
			TenderAccepterResultEntryInfo entry=this.editData.getEntrys().get(i);
			KDTable table=null;
			if(tableMaps.get(entry.getRecordSeq())==null){
				table=new KDTable();
				if(entry.getRecordSeq()==0){
					this.createTable(table, "中标结果报审");
				}else{
					this.createTable(table, "第"+entry.getRecordSeq()+"标段-中标结果报审");
				}
				tableMaps.put(entry.getRecordSeq(), table);
			}else{
				table=(KDTable) tableMaps.get(entry.getRecordSeq());
			}
			
			IRow row=table.addRow();
			row.setUserObject(entry);
			row.getCell("hit").setValue(entry.isHit());
			row.getCell("supplier").setValue(entry.getSupplier());
			row.getCell("bidAmount").setValue(entry.getBidAmount());
			row.getCell("totalSeq").setValue(entry.getTotalSeq());
			row.getCell("lastAmount").setValue(entry.getLastAmount());
			row.getCell("fix").setValue(entry.getInviteFix());
			row.getCell("afterFixAmount").setValue(entry.getAfterFixAmount());
			row.getCell("isLowest").setValue(entry.isIsLowest());
			row.getCell("cost").setValue(entry.getCostOfContruction());
		}
	}
	private void storeEntry(){
		this.editData.getEntrys().clear();
		if(tableMaps!=null&&tableMaps.size()>0){
			Object[] key = tableMaps.keySet().toArray(); 
			Arrays.sort(key); 
			for (int k = 0; k < key.length; k++) { 
				KDTable table=(KDTable) tableMaps.get(key[k]);
				
				BigDecimal minAmount=null;
				for(int i=0;i<table.getRowCount();i++){
					Boolean hit=(Boolean) table.getRow(i).getCell("hit").getValue();
					BigDecimal lastAmount = (BigDecimal) table.getRow(i).getCell("lastAmount").getValue();
					
					if(!hit.booleanValue()&&(minAmount==null||lastAmount.compareTo(minAmount)<0)){
						minAmount=lastAmount;
					}
				}
				for(int i=0;i<table.getRowCount();i++){
					IRow row=table.getRow(i);
					TenderAccepterResultEntryInfo entry=(TenderAccepterResultEntryInfo) row.getUserObject();
					Boolean hit=(Boolean) table.getRow(i).getCell("hit").getValue();
					BigDecimal lastAmount = (BigDecimal) row.getCell("lastAmount").getValue();
					
					entry.setHit(hit);
					entry.setTotalSeq((Integer) row.getCell("totalSeq").getValue());
					
					entry.setLastAmount(lastAmount);
					if(hit&&(minAmount==null||lastAmount.compareTo(minAmount)<=0)){
						entry.setIsLowest(true);
					}else{
						entry.setIsLowest(false);
					}
					entry.setCostOfContruction((BigDecimal)row.getCell("cost").getValue());
					
					this.editData.getEntrys().add(entry);
				}
			}
		}
	}
	private void createTable(KDTable table,String title){
		table.checkParsed();
		FDCTableHelper.disableAutoAddLine(table);
		IRow headRow=table.addHeadRow();
		
		IColumn coloum=table.addColumn();
		coloum.setKey("hit");
		headRow.getCell("hit").setValue("中标");
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		coloum.setEditor(editor);
 		coloum.setRequired(true);
 		
 		coloum=table.addColumn();
		coloum.setKey("supplier");
		coloum.setWidth(250);
		headRow.getCell("supplier").setValue("供应商");
 		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		coloum.setEditor(f7Editor);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("bidAmount");
		coloum.setWidth(150);
		headRow.getCell("bidAmount").setValue("最后一轮回标价格(元)");
		KDFormattedTextField price = new KDFormattedTextField();
		price.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		price.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		price.setPrecision(2);
		KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("totalSeq");
		headRow.getCell("totalSeq").setValue("中标顺序");
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.INTEGER_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setValue(null);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		coloum.setEditor(amountEditor);
		coloum.setRequired(true);
		
		coloum=table.addColumn();
		coloum.setKey("lastAmount");
		headRow.getCell("lastAmount").setValue("中标价格(元)");
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.setRequired(true);
		
		coloum=table.addColumn();
		coloum.setKey("fix");
		headRow.getCell("fix").setValue("修正系数");
		coloum.setRenderer(new ObjectValueRender(){
			public String getText(Object obj)
			{
				if(obj instanceof InviteFixInfo){
					if(obj==null||((InviteFixInfo)obj).getFix()==null) return null;
					return ((InviteFixInfo)obj).getFix().toString();
				}
				return null;
			}
		});
		coloum.getStyleAttributes().setLocked(true);
		coloum.getStyleAttributes().setBackground(new Color(245,245,245));
		
		coloum=table.addColumn();
		coloum.setKey("afterFixAmount");
		headRow.getCell("afterFixAmount").setValue("修正后报价(元)");
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		coloum.getStyleAttributes().setLocked(true);
		
		coloum=table.addColumn();
		coloum.setKey("isLowest");
		headRow.getCell("isLowest").setValue("是否最低价中标");
		coloum.setEditor(editor);
		coloum.getStyleAttributes().setLocked(true); 
		coloum.getStyleAttributes().setBackground(new Color(245, 245, 245));
		
		coloum=table.addColumn();
		coloum.setKey("cost");
		headRow.getCell("cost").setValue("已完成采购的单方造价(元)");
		coloum.setWidth(200);
		coloum.setEditor(priceEditor);
		coloum.getStyleAttributes().setNumberFormat("#0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		coloum.setRequired(true);
		
		KDContainer contEntry = new KDContainer();
		contEntry.setName(table.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		this.entryPanel.add(contEntry, title);
		
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	private void table_editStopped(KDTEditEvent e) {
		KDTable table=(KDTable) e.getSource();
		if ("lastAmount".equals(table.getColumn(e.getColIndex()).getKey())){
			InviteFixInfo fix = (InviteFixInfo) table.getRow(e.getRowIndex()).getCell("fix").getValue();
			BigDecimal lastAmount = (BigDecimal) table.getRow(e.getRowIndex()).getCell("lastAmount").getValue();
			if (fix != null && fix.getFix() != null && lastAmount != null){
				table.getRow(e.getRowIndex()).getCell("afterFixAmount").setValue(lastAmount.divide(fix.getFix(), 4,RoundingMode.HALF_UP));
			}else{
				table.getRow(e.getRowIndex()).getCell("afterFixAmount").setValue(null);
			}
		}
	}
	private void loadRecordTable() throws BOSException {
		this.kdtRecord.checkParsed();
		this.kdtRecord.getStyleAttributes().setLocked(true);
		FDCTableHelper.disableAutoAddLine(kdtRecord);
		this.kdtRecord.removeColumns();
		IRow headRow=this.kdtRecord.addHeadRow();
		
		IColumn column=this.kdtRecord.addColumn();
		column.setKey("times");
		headRow.getCell("times").setValue("回标次数");
		
		column=this.kdtRecord.addColumn();
		column.setKey("seq");
		headRow.getCell("seq").setValue("标段");
		
		if(tableMaps!=null&&tableMaps.size()>0) {
			KDTable table=(KDTable) tableMaps.get(0);
			if(table==null){
				table=(KDTable) tableMaps.get(1);
			}
			if(table!=null){
				for(int i=0;i<table.getRowCount();i++){
					SupplierStockInfo supplier=(SupplierStockInfo)table.getCell(i, "supplier").getValue();
					if(supplier!=null&&this.editData.getInviteProject()!=null){
						
						column=this.kdtRecord.addColumn();
						column.setKey(supplier.getId().toString());
						column.getStyleAttributes().setNumberFormat("#0.00");
						column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						column.setWidth(200);
						headRow.getCell(supplier.getId().toString()).setValue(supplier.getName());
						
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						view.getSelector().add(new SelectorItemInfo("isMultiple"));
						view.getSelector().add(new SelectorItemInfo("times"));
						view.getSelector().add(new SelectorItemInfo("price"));
						view.getSelector().add(new SelectorItemInfo("entry.*"));
						
						SorterItemCollection sort=new SorterItemCollection();
						sort.add(new SorterItemInfo("times"));
						sort.add(new SorterItemInfo("supplier.number"));
						sort.add(new SorterItemInfo("entry.seq"));
						view.setSorter(sort);
						
						view.setFilter(filter);
						filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",this.editData.getInviteProject().getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
						SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
						for(int j=0;j<col.size();j++){
							if(col.get(j).isIsMultiple()){
								for(int k=0;k<col.get(j).getEntry().size();k++){
									IRow addRow=this.kdtRecord.getRow(col.get(j).getEntry().get(k).getSeq()-1);
									if(addRow==null){
										addRow=this.kdtRecord.addRow();
										addRow.getCell("times").setValue(Integer.valueOf(col.get(j).getTimes()));
									}
									addRow.getCell(supplier.getId().toString()).setUserObject(col.get(j).getId().toString());
									addRow.getCell(supplier.getId().toString()).setValue(col.get(j).getEntry().get(k).getPrice());
									addRow.getCell("seq").setValue("第"+col.get(j).getEntry().get(k).getSeq()+"标段");
								}
							}else{
								IRow addRow=this.kdtRecord.getRow(col.get(j).getTimes()-1);
								if(addRow==null){
									addRow=this.kdtRecord.addRow();
									addRow.getCell("times").setValue(Integer.valueOf(col.get(j).getTimes()));
								}
								addRow.getCell(supplier.getId().toString()).setUserObject(col.get(j).getId().toString());
								addRow.getCell(supplier.getId().toString()).setValue(col.get(j).getPrice());
							}
						}
					}
				}
			}
		}
		if(this.kdtRecord.getRowCount()>0&&this.kdtRecord.getRow(0).getCell("seq").getValue()!=null){
			for(int i=0;i<this.kdtRecord.getColumnCount();i++){
				if(!this.kdtRecord.getColumnKey(i).equals("times")&&!this.kdtRecord.getColumnKey(i).equals("seq")){
					CRMClientHelper.getFootRow(this.kdtRecord, new String[]{this.kdtRecord.getColumnKey(i)});
				}
			}
		}
		mergerTable(this.kdtRecord,new String[]{"times"},new String[]{"times"});
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
	private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		parentPanel_stateChanged(null);
		this.txtSpecial.setToolTipText("包括与目标成本、数据库、市场价的差异分析，超目标成本原因，拟中标条件与招标条件的差异（包括付款方式等），未按招标策划中的定标规则定标的原因说明，其他说明等");
	}
	protected void initTable() {
		
	}
	protected ICoreBase getBizInterface() throws Exception {
		return TenderAccepterResultFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		
		sic.add(new SelectorItemInfo("drawingDepth"));
		sic.add(new SelectorItemInfo("deployType"));
		sic.add(new SelectorItemInfo("entrys.supplier.grade.*"));
		sic.add(new SelectorItemInfo("entrys.supplier.*"));
		sic.add(new SelectorItemInfo("entrys.inviteFix.*"));
		sic.add(new SelectorItemInfo("entrys.*"));
		return sic;
	}
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/TenderAccepterResult";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.TenderAccepterResultPrintQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		TenderAccepterResultInfo info=new TenderAccepterResultInfo();
		createBaseInvite(info);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("supplier.*"));
		view.getSelector().add(new SelectorItemInfo("supplier.grade.*"));
		view.getSelector().add(new SelectorItemInfo("entry.*"));
		
		SorterItemCollection sort=new SorterItemCollection();
		SorterItemInfo times=new SorterItemInfo("times");
		times.setSortType(SortType.DESCEND);
		sort.add(times);
		sort.add(new SorterItemInfo("supplier.number"));
		sort.add(new SorterItemInfo("entry.seq"));
		view.setSorter(sort);
		
		if(info.getInviteProject()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",info.getInviteProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","'null'"));
		}
		try {
			SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
			if(col!=null && col.size()>0) {
				HashMap map =TenderAccepterResultFactory.getRemoteInstance().getFixMap(info.getInviteProject().getId());
				HashMap supplier=new HashMap();
				
				int time=col.get(0).getTimes();
				for(int i=0;i<col.size();i++){
					if(col.get(i).getSupplier()!=null){
						if(time!=col.get(i).getTimes()){
							continue;
						}
						if(supplier.get(col.get(i).getSupplier().getId().toString())==null){
							Object[] key = supplier.keySet().toArray(); 
							for (int k = 0; k < key.length; k++) { 
								SupplierInviteRecordInfo recordEntry=(SupplierInviteRecordInfo) supplier.get(key[k]);
								if(!Boolean.valueOf(recordEntry.isIsMultiple()).equals(Boolean.valueOf(col.get(i).isIsMultiple()))){
									FDCMsgBox.showWarning(this,"供应商"+col.get(i).getSupplier().getName()+"投标记录是否分标段不一致！");
									SysUtil.abort();
								}
								if(recordEntry.isIsMultiple()&&col.get(i).isIsMultiple()&&recordEntry.getEntry().size()!=col.get(i).getEntry().size()){
									FDCMsgBox.showWarning(this,"供应商"+col.get(i).getSupplier().getName()+"投标记录分标段数量不一致！");
									SysUtil.abort();
								}
							}
							supplier.put(col.get(i).getSupplier().getId().toString(),col.get(i));
							
							if(col.get(i).isIsMultiple()){
								for(int j=0;j<col.get(i).getEntry().size();j++){
									TenderAccepterResultEntryInfo entry=new TenderAccepterResultEntryInfo();
									entry.setSupplier(col.get(i).getSupplier());
									if(col.get(i).getSupplier().getGrade()!=null){
										String sid = col.get(i).getSupplier().getId().toString();
										if(map.get(sid)!=null) {
											entry.setInviteFix((InviteFixInfo) map.get(sid));
										}
									}
									entry.setRecordSeq(col.get(i).getEntry().get(j).getSeq());
									entry.setBidAmount(col.get(i).getEntry().get(j).getPrice());
									entry.setLastAmount(col.get(i).getEntry().get(j).getPrice());
									info.getEntrys().add(entry);
								}
							}else{
								TenderAccepterResultEntryInfo entry=new TenderAccepterResultEntryInfo();
								entry.setSupplier(col.get(i).getSupplier());
								if(col.get(i).getSupplier().getGrade()!=null){
									String sid = col.get(i).getSupplier().getId().toString();
									if(map.get(sid)!=null) {
										entry.setInviteFix((InviteFixInfo) map.get(sid));
									}
								}
								entry.setRecordSeq(0);
								entry.setBidAmount(col.get(i).getPrice());
								entry.setLastAmount(col.get(i).getPrice());
								info.getEntrys().add(entry);
							}
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new TenderAccepterResultEntryInfo();
	}
	protected void closeDeleteAttachment(){
	}
	protected void kdtRecord_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.kdtRecord.getRow(e.getRowIndex());
			if(row.getCell(e.getColIndex()).getUserObject()==null) return;
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell(e.getColIndex()).getUserObject().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SupplierInviteRecordEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.combRange);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSpecial);
		if(tableMaps!=null&&tableMaps.size()>0){
			Object[] key = tableMaps.keySet().toArray(); 
			Arrays.sort(key); 
			for (int k = 0; k < key.length; k++) { 
				boolean isHit=false;
				KDTable table=(KDTable) tableMaps.get(key[k]);
				for(int i=0;i<table.getRowCount();i++){
					IRow row=table.getRow(i);
					if( ((Boolean) table.getCell(i, "hit").getValue()).booleanValue() ) {
						isHit=true;
					}
					if(table.getCell(i, "totalSeq").getValue()==null){
						if(key[k].equals(0)){
							FDCMsgBox.showWarning(this,"中标顺序不能为空！");
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("totalSeq"));
							SysUtil.abort();
						}else{
							FDCMsgBox.showWarning(this,"第"+key[k]+"标段中标顺序不能为空！");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("totalSeq"));
							SysUtil.abort();
						}
					}
					if(table.getCell(i, "lastAmount").getValue()==null){
						if(key[k].equals(0)){
							FDCMsgBox.showWarning(this,"中标价不能为空！");
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("lastAmount"));
							SysUtil.abort();
						}else{
							FDCMsgBox.showWarning(this,"第"+key[k]+"标段中标价不能为空！");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("lastAmount"));
							SysUtil.abort();
						}
					}
					if(table.getCell(i, "cost").getValue()==null){
						if(key[k].equals(0)){
							FDCMsgBox.showWarning(this,"已完成采购的单方造价不能为空！");
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("cost"));
							SysUtil.abort();
						}else{
							FDCMsgBox.showWarning(this,"第"+key[k]+"标段已完成采购的单方造价不能为空！");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(row.getRowIndex(), table.getColumnIndex("cost"));
							SysUtil.abort();
						}
					}
				}
				if(!isHit){
					if(key[k].equals(0)){
						MsgBox.showWarning(this,"中标结果报审至少有一个供应商中标！");
						SysUtil.abort();
					}else{
						MsgBox.showWarning(this,"第"+key[k]+"标段至少有一个供应商中标！");
						this.entryPanel.setSelectedIndex(k);
						SysUtil.abort();
					}
				}
			}
		}
	}
	HashMap<Integer, KDTable> tableMap = new HashMap<Integer, KDTable>();
	String[] className = new String[] {
			SupplierQualifyEditUI.class.getName(),     InviteTenderPlanningEditUI.class.getName(),
			InviteDocumentsEditUI.class.getName(),     SupplierInviteRecordEditUI.class.getName(),
			InviteClarifyEditUI.class.getName(),       MaterialSampleEditUI.class.getName(),
			InviteBidEvaluationEditUI.class.getName(), InviteChangeFormEditUI.class.getName()//新增改变采购方式请示
	};
	KDScrollPane[] panes = new KDScrollPane[] {
			this.supplierPanel, this.planningPanel, this.documentPanel, this.recordPanel,
			this.clarifyPanel, this.samplePanel, this.reportPanel,this.changInviteTypeApplication
	};
	
	protected void parentPanel_stateChanged(javax.swing.event.ChangeEvent e) throws Exception {
		if(this.editData == null ) {
			return;
		}
		int index = this.parentPanel.getSelectedIndex();
		if( tableMap.get(index)==null ) {
			String tblBaseInviteStrXML= null;
			String prjID = this.editData.getInviteProject().getId().toString();
			if( index==3) {
				tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"> <Styles> <c:Style id=\"sCol0\"> <c:Protection hidden=\"true\"/> </c:Style> </Styles> <Table id=\"KDTable\"> <t:Sheet name=\"sheet1\"> <t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"> <t:ColumnGroup> <t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\"/> <t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\"/> <t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\"/> <t:Column t:key=\"openDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\"/> <t:Column t:key=\"place\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\"/> <t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\"/> <t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\"/> <t:Column t:key=\"returnDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\"/> <t:Column t:key=\"times\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\"/> <t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\"/> <t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\"/> <t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\"/> <t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\"/> <t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\"/> <t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\"/> </t:ColumnGroup> <t:Head> <t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"> <t:Cell>$Resource{id}</t:Cell> <t:Cell>状态</t:Cell> <t:Cell>单据编码</t:Cell> <t:Cell>开标日期</t:Cell> <t:Cell>开标地点</t:Cell> <t:Cell>供应商</t:Cell> <t:Cell>供应商报价</t:Cell> <t:Cell>回标日期</t:Cell> <t:Cell>回标次数</t:Cell> <t:Cell>备注</t:Cell> <t:Cell>编制部门</t:Cell> <t:Cell>编制人</t:Cell> <t:Cell>编制日期</t:Cell> <t:Cell>审批人</t:Cell> <t:Cell>审批日期</t:Cell> </t:Row> </t:Head> </t:Table> <t:SheetOptions> <t:MergeBlocks> <t:Head/> </t:MergeBlocks> </t:SheetOptions> </t:Sheet> </Table> </DocRoot>  ";
//				tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"openDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"place\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"supplier\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"price\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"returnDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"times\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{openDate}</t:Cell><t:Cell>$Resource{place}</t:Cell><t:Cell>$Resource{supplier}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{returnDate}</t:Cell><t:Cell>$Resource{times}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{respDept}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createDate}</t:Cell><t:Cell>$Resource{auditor}</t:Cell><t:Cell>$Resource{auditDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";

			}else {
				tblBaseInviteStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"> <Styles> <c:Style id=\"sCol0\"> <c:Protection hidden=\"true\"/> </c:Style> <c:Style id=\"sCol5\"><c:Protection locked=\"true\"/></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\"/></c:Style></Styles> <Table id=\"KDTable\"> <t:Sheet name=\"sheet1\"> <t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"> <t:ColumnGroup> <t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\"/> <t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\"/> <t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\"/> <t:Column t:key=\"respDept\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\"/> <t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\"/> <t:Column t:key=\"createDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\"/> <t:Column t:key=\"auditor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\"/> <t:Column t:key=\"auditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\"/> </t:ColumnGroup> <t:Head> <t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"> <t:Cell>$Resource{id}</t:Cell> <t:Cell>状态</t:Cell> <t:Cell>单据编码</t:Cell> <t:Cell>编制部门</t:Cell> <t:Cell>编制人</t:Cell> <t:Cell>编制日期</t:Cell> <t:Cell>审批人</t:Cell> <t:Cell>审批日期</t:Cell> </t:Row> </t:Head> </t:Table> <t:SheetOptions> <t:MergeBlocks> <t:Head/> </t:MergeBlocks> </t:SheetOptions> </t:Sheet> </Table> </DocRoot>";
			}
			
			KDTable table = new KDTable();
			table.setFormatXml(tblBaseInviteStrXML);
			table.checkParsed();
			table.getStyleAttributes().setLocked(true);
			if(table.getColumn("respDept") != null) {
				table.getColumn("respDept").getStyleAttributes().setHided(true);
			}
			
			String fmt = "yyyy-MM-dd";
			table.getColumn(COL_CREATEDATE).getStyleAttributes().setLocked(true);
			table.getColumn(COL_AUDITDATE).getStyleAttributes().setLocked(true);
			table.getColumn(COL_CREATEDATE).getStyleAttributes().setNumberFormat(fmt);
			table.getColumn(COL_AUDITDATE).getStyleAttributes().setNumberFormat(fmt);
			
			if( index==3) {
				table.getColumn("openDate").getStyleAttributes().setLocked(true);
				table.getColumn("returnDate").getStyleAttributes().setLocked(true);
				table.getColumn("openDate").getStyleAttributes().setNumberFormat(fmt);
				table.getColumn("returnDate").getStyleAttributes().setNumberFormat(fmt);
			}
//			table.parseFormatXml(tblBaseInviteStrXML);
			
			Iterator<BaseInviteInfo> iter=null;
			if(index==0) {//入围供应商页签
				SupplierQualifyCollection recordCols = SupplierQualifyListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==1) {
				InviteTenderPlanningCollection recordCols = InviteTenderPlanningListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==2) {
				InviteDocumentsCollection recordCols = InviteDocumentsListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==3) {
				SupplierInviteRecordCollection recordCols = SupplierInviteRecordListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==4) {
				InviteClarifyCollection recordCols = InviteClarifyListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==5) {
				MaterialSampleCollection recordCols = MaterialSampleListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}else if(index==6) {
				InviteBidEvaluationCollection recordCols = InviteBidEvaluationListUI.getBillList(prjID);
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}//增加改变采购方式请示
			else if(index==7)
			{
				com.kingdee.eas.fdc.invite.InviteChangeFormCollection recordCols =com.kingdee.eas.fdc.invite.client.InviteChangeFormListUICTEx.getBillList(prjID);
				
				
				if (recordCols != null) {
					iter = recordCols.iterator();
				}
			}
			
			if(iter != null) {
				while (iter.hasNext()) {
					Object obj = iter.next();
					if(obj instanceof BaseInviteInfo)
					{
						fillTable(table,(BaseInviteInfo ) obj);
					}else if(obj instanceof InviteChangeFormInfo)
					{
						fillTable(table, (InviteChangeFormInfo)obj );
					}
						
				}
			}
//			
			
			tableMap.put(index, table);
			panes[index].setViewportView(table);
			
			table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
		            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
		                try {
		                	 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
		         	        	setCursorOfWair();
		         	        	int index = parentPanel.getSelectedIndex();
		         				IRow row=KDTableUtil.getSelectedRow( tableMap.get(index) );
		         				String id = row.getCell("id").getValue().toString();
		         				UIContext uiContext = new UIContext(this);
	         					uiContext.put(UIContext.ID, id);
		         				
	         					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
         								className[index], uiContext, null,"VIEW");
		         				uiWindow.show();
		         				setCursorOfDefault();
		         	      }
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                }
		            }
		        });
		}
		
	}
	
	/**
	 * 增加方法填充立项变更
	 * @param table
	 * @param info
	 * @throws BOSException
	 */
	protected void fillTable(KDTable table, com.kingdee.eas.fdc.invite.InviteChangeFormInfo info) throws BOSException {
		IRow row = table.addRow();

		if (info != null) {
			row.getCell(COL_ID).setValue(info.getId());
			row.getCell(COL_NUMBER).setValue(info.getNumber());
			row.getCell(COL_STATE).setValue(info.getState());
//
//			if (info.get != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
//			}
			if (info.getCreator() != null) {
				row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
			}
			row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
			if (info.getAuditor() != null) {
				row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
			}
			row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
			
			
			row.setUserObject(info);
		}
	}
	
	
	
	protected void fillTable(KDTable table, BaseInviteInfo info) throws BOSException {
		IRow row = table.addRow();

		if (info != null) {
			row.getCell(COL_ID).setValue(info.getId());
			row.getCell(COL_NUMBER).setValue(info.getNumber());
			row.getCell(COL_STATE).setValue(info.getState());

			if (info.getRespDept() != null) {row.getCell(COL_RESPDEPT).setValue(info.getRespDept().getName());
			}
			if (info.getCreator() != null) {
				row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
			}
			row.getCell(COL_CREATEDATE).setValue(info.getCreateTime());
			if (info.getAuditor() != null) {
				row.getCell(COL_AUDITOR).setValue(info.getAuditor().getName());
			}
			row.getCell(COL_AUDITDATE).setValue(info.getAuditTime());
			
			if(info instanceof SupplierInviteRecordInfo ) {
				SupplierInviteRecordInfo sInfo = (SupplierInviteRecordInfo) info;
				row.getCell("openDate").setValue(sInfo.getOpenDate());
				row.getCell("place").setValue(sInfo.getPlace());
				if(sInfo.getSupplier()!=null){
					row.getCell("supplier").setValue(sInfo.getSupplier().getName());
				}
				row.getCell("price").setValue(sInfo.getPrice());
				row.getCell("returnDate").setValue(sInfo.getReturnDate());
				row.getCell("times").setValue(Integer.valueOf(sInfo.getTimes()));
				row.getCell("remark").setValue(sInfo.getRemark());
			}
			row.setUserObject(info);
		}
	}
	
	protected final String COL_ID = "id";
    protected final String COL_STATE = "state";
    protected final String COL_RESPDEPT = "respDept";
    protected final String COL_CREATOR = "creator";
    protected final String COL_CREATEDATE = "createDate";
    protected final String COL_AUDITOR = "auditor";
    protected final String COL_AUDITDATE = "auditDate";
    protected final String PROJECTDATE_COL = "prjDate";
    protected final String AUDITTIME_COL = "auditTime";
    protected final String COL_NUMBER = "number";
    
    public final static String INVITEPROJECTID = "inviteProjectId";
    public final static String SUPPLIERIDSET = "supplierIDSet";
    
    private HashMap getProviderDataMap() {
    	HashMap map = new HashMap();
    	HashSet set = new HashSet();
    	String inviteProjectId = this.editData.getInviteProject().getId().toString();
    	
    	if(tableMaps!=null&&tableMaps.size()>0){
			Object[] key = tableMaps.keySet().toArray(); 
			Arrays.sort(key); 
			for (int k = 0; k < key.length; k++) { 
				KDTable table=(KDTable) tableMaps.get(key[k]);
				
				for(int i=0;i<table.getRowCount();i++){
					SupplierStockInfo supplier=(SupplierStockInfo)table.getCell(i, "supplier").getValue();
					if(supplier !=null ) {
		    			set.add(supplier.getId().toString());
		    		}
				}
			}
		}
    	map.put(TenderAccepterResultEditUI.INVITEPROJECTID, inviteProjectId);
    	map.put(TenderAccepterResultEditUI.SUPPLIERIDSET, set);
    	return map;
    }
    
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		TenderAccepterResultPrintDataProvider dataPvd = new TenderAccepterResultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		TenderAccepterResultPrintDataProvider dataPvd = new TenderAccepterResultPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
}