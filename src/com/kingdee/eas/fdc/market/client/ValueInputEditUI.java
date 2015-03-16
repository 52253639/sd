/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.BanInformationEntrysNewInfo;
import com.kingdee.eas.fdc.basedata.BanInformationFactory;
import com.kingdee.eas.fdc.basedata.BanInformationInfo;
import com.kingdee.eas.fdc.basedata.CalculateTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.HouseTypeFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeDateEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeDateFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeDateInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectCollection;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectFactory;
import com.kingdee.eas.fdc.basedata.ProjectDataCollectInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanEntryInfo;
import com.kingdee.eas.fdc.basedata.SchedulePlanFactory;
import com.kingdee.eas.fdc.basedata.SchedulePlanInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.market.DecorateEnum;
import com.kingdee.eas.fdc.market.ValueInputCollection;
import com.kingdee.eas.fdc.market.ValueInputEntryInfo;
import com.kingdee.eas.fdc.market.ValueInputFactory;
import com.kingdee.eas.fdc.market.ValueInputInfo;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListCollection;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ValueInputEditUI extends AbstractValueInputEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ValueInputEditUI.class);
    private Set shBuildId=null;
    public ValueInputEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ValueInputFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void onLoad() throws Exception {
		this.kdtEntry.checkParsed();
		FDCTableHelper.disableDelete(this.kdtEntry);
		KDComboBox combo = new KDComboBox();
        for(int i = 0; i < DecorateEnum.getEnumList().size(); i++){
        	combo.addItem(DecorateEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("decorate").setEditor(comboEditor);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setNegatived(false);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtEntry.getColumn("price").setEditor(weight);
		this.kdtEntry.getColumn("price").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("amount").setEditor(weight);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtTotalEntry.checkParsed();
		this.kdtTotalEntry.getStyleAttributes().setLocked(true);
		for(int i=0;i<this.kdtTotalEntry.getColumnCount();i++){
			if(this.kdtTotalEntry.getColumnKey(i).indexOf("amount")>=0||this.kdtTotalEntry.getColumnKey(i).indexOf("price")>=0){
				this.kdtTotalEntry.getColumn(i).setEditor(weight);
				this.kdtTotalEntry.getColumn(i).getStyleAttributes().setNumberFormat("#,##0;-#,##0");
				this.kdtTotalEntry.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}else if(this.kdtTotalEntry.getColumnKey(i).indexOf("area")>=0){
				this.kdtTotalEntry.getColumn(i).setEditor(weight);
				this.kdtTotalEntry.getColumn(i).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				this.kdtTotalEntry.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
		this.kdtPriceEntry.checkParsed();
		this.kdtPriceEntry.getStyleAttributes().setLocked(true);
		this.kdtPriceEntry.getColumn("standardTotalAmount").setEditor(weight);
		this.kdtPriceEntry.getColumn("standardTotalAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtPriceEntry.getColumn("standardTotalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtPriceEntry.getColumn("baseStandardPrice").setEditor(weight);
		this.kdtPriceEntry.getColumn("baseStandardPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtPriceEntry.getColumn("baseStandardPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtPriceEntry.getColumn("buildingPrice").setEditor(weight);
		this.kdtPriceEntry.getColumn("buildingPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtPriceEntry.getColumn("buildingPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtPriceEntry.getColumn("buildingArea").setEditor(weight);
		this.kdtPriceEntry.getColumn("buildingArea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtPriceEntry.getColumn("buildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtPriceEntry.getColumn("roomPrice").setEditor(weight);
		this.kdtPriceEntry.getColumn("roomPrice").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtPriceEntry.getColumn("roomPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtPriceEntry.getColumn("roomArea").setEditor(weight);
		this.kdtPriceEntry.getColumn("roomArea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtPriceEntry.getColumn("roomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtEntry.getColumn("decorate").getStyleAttributes().setHided(true);
		this.kdtTotalEntry.getColumn("decorate").getStyleAttributes().setHided(true);
		
		super.onLoad();
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.prmtProject.setEnabled(false);
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		panel.remove(this.contPriceEntry);
//		panel.removeAll();
//		if(this.editData.getSourceBillId()!=null){
//			panel.add(contEntry, "货值填写-未取预证");
//	        panel.add(contPriceEntry, "货值填写-已取预证");
//	        panel.add(contTotalEntry, "货值汇总");
//		}else{
//			panel.add(contEntry, "货值填写");
//	        panel.add(contTotalEntry, "货值汇总");
//		}
		try {
			if(shBuildId==null){
				shBuildId=new HashSet();
				SellProjectCollection sp = SellProjectFactory.getRemoteInstance().getSellProjectCollection("select id from where projectBase.id='"+this.editData.getProject().getId().toString()+"'");
				if(sp.size()>0){
					Set spId=SHEManageHelper.getAllSellProjectCollection(null, sp.get(0));
					if(spId.size()>0){
						SelectorItemCollection sel=new SelectorItemCollection();
						sel.add("banBasedataEntry.parent.project.id");
						EntityViewInfo view=new EntityViewInfo();
						FilterInfo filter=new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id",spId,CompareType.INCLUDE));
//						filter.getFilterItems().add(new FilterItemInfo("head.isGetCertificated",Boolean.TRUE));
						view.setFilter(filter);
						view.setSelector(sel);
						BanBasedataEntryListCollection banEntryCol=BanBasedataEntryListFactory.getRemoteInstance().getBanBasedataEntryListCollection(view);
						for(int i=0;i<banEntryCol.size();i++){
							shBuildId.add(banEntryCol.get(i).getBanBasedataEntry().getParent().getProject().getId().toString());
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(shBuildId!=null&&shBuildId.size()>0){
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				IRow row=this.kdtEntry.getRow(i);
				ValueInputEntryInfo entry=(ValueInputEntryInfo) row.getUserObject();
				if(entry.getBuildId()!=null&&shBuildId.contains(entry.getProjectId().toString())){
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
				}
			}
		}
		if(this.editData.getIndexVersion()!=null){
			IndexVersionInfo info=this.editData.getIndexVersion();
			this.kdtTotalEntry.getHeadRow(0).getCell(5).setValue(info.getName()+"总货值");
		}else{
			this.kdtTotalEntry.getHeadRow(0).getCell(5).setValue("动态版总货值");
		}
		int account=0;
		BigDecimal area=FDCHelper.ZERO;
		BigDecimal amount=FDCHelper.ZERO;
		
		int saccount=0;
		BigDecimal sarea=FDCHelper.ZERO;
		BigDecimal samount=FDCHelper.ZERO;
		
		int daccount=0;
		BigDecimal darea=FDCHelper.ZERO;
		BigDecimal damount=FDCHelper.ZERO;
		
		int paccount=0;
		BigDecimal parea=FDCHelper.ZERO;
		BigDecimal pamount=FDCHelper.ZERO;
		
		int totalAccount=0;
		int subAccount=0;
		BigDecimal totalArea=FDCHelper.ZERO;
		BigDecimal subArea=FDCHelper.ZERO;
		BigDecimal totalAmount=FDCHelper.ZERO;
		BigDecimal subAmount=FDCHelper.ZERO;
		
		int totalSAccount=0;
		int subSAccount=0;
		BigDecimal totalSArea=FDCHelper.ZERO;
		BigDecimal subSArea=FDCHelper.ZERO;
		BigDecimal totalSAmount=FDCHelper.ZERO;
		BigDecimal subSAmount=FDCHelper.ZERO;
		
		int totalDAccount=0;
		int subDAccount=0;
		BigDecimal totalDArea=FDCHelper.ZERO;
		BigDecimal subDArea=FDCHelper.ZERO;
		BigDecimal totalDAmount=FDCHelper.ZERO;
		BigDecimal subDAmount=FDCHelper.ZERO;
		
		int totalPAccount=0;
		int subPAccount=0;
		BigDecimal totalPArea=FDCHelper.ZERO;
		BigDecimal subPArea=FDCHelper.ZERO;
		BigDecimal totalPAmount=FDCHelper.ZERO;
		BigDecimal subPAmount=FDCHelper.ZERO;
		
		for(int i=0;i<this.kdtTotalEntry.getRowCount();i++){
			IRow row=this.kdtTotalEntry.getRow(i);
			subAccount=(Integer) row.getCell("account").getValue();
  			account=account+subAccount;
  			totalAccount=totalAccount+subAccount;
  			
  			subArea=(BigDecimal) row.getCell("area").getValue();
			area=FDCHelper.add(area, subArea);
			totalArea=FDCHelper.add(totalArea, subArea);
			
			subAmount=(BigDecimal) row.getCell("amount").getValue();
			amount=FDCHelper.add(amount, subAmount);
			totalAmount=FDCHelper.add(totalAmount, subAmount);
			
			subSAccount=(Integer) row.getCell("saccount").getValue();
  			saccount=saccount+subSAccount;
  			totalSAccount=totalSAccount+subSAccount;
  			
  			subSArea=(BigDecimal) row.getCell("sarea").getValue();
			sarea=FDCHelper.add(sarea, subSArea);
			totalSArea=FDCHelper.add(totalSArea, subSArea);
			
			subSAmount=(BigDecimal) row.getCell("samount").getValue();
			samount=FDCHelper.add(samount, subSAmount);
			totalSAmount=FDCHelper.add(totalSAmount, subSAmount);
			
			subDAccount=(Integer) row.getCell("daccount").getValue();
  			daccount=daccount+subDAccount;
  			totalDAccount=totalDAccount+subDAccount;
  			
  			subDArea=(BigDecimal) row.getCell("darea").getValue();
			darea=FDCHelper.add(darea, subDArea);
			totalDArea=FDCHelper.add(totalDArea, subDArea);
			
			subDAmount=(BigDecimal) row.getCell("damount").getValue();
			damount=FDCHelper.add(damount, subDAmount);
			totalDAmount=FDCHelper.add(totalDAmount, subDAmount);
			
			subPAccount=(Integer) row.getCell("paccount").getValue();
  			paccount=paccount+subPAccount;
  			totalPAccount=totalPAccount+subPAccount;
  			
  			subPArea=(BigDecimal) row.getCell("parea").getValue();
			parea=FDCHelper.add(parea, subPArea);
			totalPArea=FDCHelper.add(totalPArea, subPArea);
			
			subPAmount=(BigDecimal) row.getCell("pamount").getValue();
			pamount=FDCHelper.add(pamount, subPAmount);
			totalPAmount=FDCHelper.add(totalPAmount, subPAmount);
			
			if(subAccount==0){
  				row.getCell("account").setValue(null);
  			}
			if(subArea==null||subArea.compareTo(FDCHelper.ZERO)==0){
				row.getCell("area").setValue(null);
			}
			if(row.getCell("price").getValue()==null||((BigDecimal)row.getCell("price").getValue()).compareTo(FDCHelper.ZERO)==0){
				row.getCell("price").setValue(null);
			}
			if(subAmount==null||subAmount.compareTo(FDCHelper.ZERO)==0){
				row.getCell("amount").setValue(null);
			}
			
			if(subDAccount==0){
  				row.getCell("daccount").setValue(null);
  			}
			if(subDArea==null||subDArea.compareTo(FDCHelper.ZERO)==0){
				row.getCell("darea").setValue(null);
			}
			if(row.getCell("dprice").getValue()==null||((BigDecimal)row.getCell("dprice").getValue()).compareTo(FDCHelper.ZERO)==0){
				row.getCell("dprice").setValue(null);
			}
			if(subDAmount==null||subDAmount.compareTo(FDCHelper.ZERO)==0){
				row.getCell("damount").setValue(null);
			}
			
			if(subSAccount==0){
  				row.getCell("saccount").setValue(null);
  			}
			if(subSArea==null||subSArea.compareTo(FDCHelper.ZERO)==0){
				row.getCell("sarea").setValue(null);
			}
			if(row.getCell("sprice").getValue()==null||((BigDecimal)row.getCell("sprice").getValue()).compareTo(FDCHelper.ZERO)==0){
				row.getCell("sprice").setValue(null);
			}
			if(subSAmount==null||subSAmount.compareTo(FDCHelper.ZERO)==0){
				row.getCell("samount").setValue(null);
			}
			
			if(subPAccount==0){
  				row.getCell("paccount").setValue(null);
  			}
			if(subPArea==null||subPArea.compareTo(FDCHelper.ZERO)==0){
				row.getCell("parea").setValue(null);
			}
			if(row.getCell("pprice").getValue()==null||((BigDecimal)row.getCell("pprice").getValue()).compareTo(FDCHelper.ZERO)==0){
				row.getCell("pprice").setValue(null);
			}
			if(subPAmount==null||subPAmount.compareTo(FDCHelper.ZERO)==0){
				row.getCell("pamount").setValue(null);
			}
			if(row.getCell("operationPhases").getValue()!=null&&this.kdtTotalEntry.getRow(row.getRowIndex()-1)!=null&&
					 this.kdtTotalEntry.getRow(row.getRowIndex()-1).getCell("operationPhases").getValue()!=null&&!row.getCell("operationPhases").getValue().toString().equals(this.kdtTotalEntry.getRow(row.getRowIndex()-1).getCell("operationPhases").getValue().toString())){
           		 i=i+1;
				 IRow totalRow=kdtTotalEntry.addRow(row.getRowIndex());
           		 totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
           		 totalRow.getCell("account").setValue(totalAccount-subAccount);
           		 totalRow.getCell("area").setValue(FDCHelper.subtract(totalArea, subArea));
           		 totalRow.getCell("amount").setValue(FDCHelper.subtract(totalAmount, subAmount));
           		 totalRow.getCell("price").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("amount").getValue(), new BigDecimal(10000)), totalRow.getCell("area").getValue(), 0, BigDecimal.ROUND_HALF_UP));
           		
           		 totalRow.getCell("saccount").setValue(totalSAccount-subSAccount);
          		 totalRow.getCell("sarea").setValue(FDCHelper.subtract(totalSArea, subSArea));
          		 totalRow.getCell("samount").setValue(FDCHelper.subtract(totalSAmount, subSAmount));
          		 totalRow.getCell("sprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("samount").getValue(), new BigDecimal(10000)), totalRow.getCell("sarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
          		
          		 totalRow.getCell("daccount").setValue(totalDAccount-subDAccount);
          		 totalRow.getCell("darea").setValue(FDCHelper.subtract(totalDArea, subDArea));
          		 totalRow.getCell("damount").setValue(FDCHelper.subtract(totalDAmount, subDAmount));
          		 totalRow.getCell("dprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("damount").getValue(), new BigDecimal(10000)), totalRow.getCell("darea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
          		
          		 totalRow.getCell("paccount").setValue(totalPAccount-subPAccount);
          		 totalRow.getCell("parea").setValue(FDCHelper.subtract(totalPArea, subPArea));
          		 totalRow.getCell("pamount").setValue(FDCHelper.subtract(totalPAmount, subPAmount));
          		 totalRow.getCell("pprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("pamount").getValue(), new BigDecimal(10000)), totalRow.getCell("parea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
          		
           		 totalRow.getCell("project").setValue(this.kdtTotalEntry.getRow(row.getRowIndex()-1).getCell("project").getValue().toString());
           		 totalRow.getCell("operationPhases").setValue(this.kdtTotalEntry.getRow(row.getRowIndex()-1).getCell("operationPhases").getValue().toString());
           		 totalRow.getCell("batch").setValue("小计");
           		 
           		 totalAccount=subAccount;
           		 totalArea=subArea;
           		 totalAmount=subAmount;
           		 
           		 totalSAccount=subSAccount;
          		 totalSArea=subSArea;
          		 totalSAmount=subSAmount;

          		 totalDAccount=subDAccount;
          		 totalDArea=subDArea;
          		 totalDAmount=subDAmount;
          		 
          		 totalPAccount=subPAccount;
          		 totalPArea=subPArea;
          		 totalPAmount=subPAmount;
           	 }
		}
		if(this.kdtTotalEntry.getRowCount()>0){
		   	 IRow totalRow=this.kdtTotalEntry.addRow();
			 totalRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
       		 totalRow.getCell("account").setValue(totalAccount);
       		 totalRow.getCell("area").setValue(totalArea);
       		 totalRow.getCell("amount").setValue(totalAmount);
       		 totalRow.getCell("price").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("amount").getValue(), new BigDecimal(10000)), totalRow.getCell("area").getValue(), 0, BigDecimal.ROUND_HALF_UP));
       		
       		 totalRow.getCell("saccount").setValue(totalSAccount);
      		 totalRow.getCell("sarea").setValue(totalSArea);
      		 totalRow.getCell("samount").setValue(totalSAmount);
      		 totalRow.getCell("sprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("samount").getValue(), new BigDecimal(10000)), totalRow.getCell("sarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
      		
      		 totalRow.getCell("daccount").setValue(totalDAccount);
      		 totalRow.getCell("darea").setValue(totalDArea);
      		 totalRow.getCell("damount").setValue(totalDAmount);
      		 totalRow.getCell("dprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("damount").getValue(), new BigDecimal(10000)), totalRow.getCell("darea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
      		
      		 
      		 totalRow.getCell("paccount").setValue(totalPAccount);
      		 totalRow.getCell("parea").setValue(totalPArea);
      		 totalRow.getCell("pamount").setValue(totalPAmount);
      		 totalRow.getCell("pprice").setValue(FDCHelper.divide(FDCHelper.multiply(totalRow.getCell("pamount").getValue(), new BigDecimal(10000)), totalRow.getCell("parea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
      		
       		 totalRow.getCell("project").setValue(this.kdtTotalEntry.getRow(totalRow.getRowIndex()-1).getCell("project").getValue().toString());
       		 totalRow.getCell("operationPhases").setValue(this.kdtTotalEntry.getRow(totalRow.getRowIndex()-1).getCell("operationPhases").getValue().toString());
       		 totalRow.getCell("batch").setValue("小计");
        }
		IRow footRow = null;
        KDTFootManager footRowManager = this.kdtTotalEntry.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(this.kdtTotalEntry);
            footRowManager.addFootView();
            this.kdtTotalEntry.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            this.kdtTotalEntry.getIndexColumn().setWidthAdjustMode((short)1);
            this.kdtTotalEntry.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        footRow.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
        footRow.getCell("account").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
        footRow.getCell("account").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("account").setValue(account);
        
        footRow.getCell("area").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("area").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
        footRow.getCell("area").setValue(area);
        
        footRow.getCell("amount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("amount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("amount").setValue(amount);
        
        footRow.getCell("price").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("price").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("price").setValue(FDCHelper.divide(FDCHelper.multiply(amount, new BigDecimal(10000)), area, 0, BigDecimal.ROUND_HALF_UP));
        
        footRow.getCell("saccount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
        footRow.getCell("saccount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("saccount").setValue(saccount);
        
        footRow.getCell("sarea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("sarea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
        footRow.getCell("sarea").setValue(sarea);
        
        footRow.getCell("samount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("samount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("samount").setValue(samount);
        
        footRow.getCell("sprice").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("sprice").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("sprice").setValue(FDCHelper.divide(FDCHelper.multiply(samount, new BigDecimal(10000)), sarea, 0, BigDecimal.ROUND_HALF_UP));
        
        footRow.getCell("daccount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
        footRow.getCell("daccount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("daccount").setValue(daccount);
        
        footRow.getCell("darea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("darea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
        footRow.getCell("darea").setValue(darea);
        
        footRow.getCell("damount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("damount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("damount").setValue(damount);
        
        footRow.getCell("dprice").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("dprice").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("dprice").setValue(FDCHelper.divide(FDCHelper.multiply(damount, new BigDecimal(10000)), darea, 0, BigDecimal.ROUND_HALF_UP));
        
        footRow.getCell("paccount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("left"));
        footRow.getCell("paccount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("paccount").setValue(paccount);
        
        footRow.getCell("parea").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("parea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
        footRow.getCell("parea").setValue(parea);
        
        footRow.getCell("pamount").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("pamount").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("pamount").setValue(pamount);
        
        footRow.getCell("pprice").getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.getAlignment("right"));
        footRow.getCell("pprice").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
        footRow.getCell("pprice").setValue(FDCHelper.divide(FDCHelper.multiply(pamount, new BigDecimal(10000)), parea, 0, BigDecimal.ROUND_HALF_UP));
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
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
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	public void storeFields() {
		super.storeFields();
	}
	protected IObjectValue createNewData() {
		ValueInputInfo info=(ValueInputInfo)this.getUIContext().get("info");
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		ProjectBaseInfo pb=null;
		Map entrySet=new HashMap();
		Map ptEntrySet=new HashMap();
		if(info==null){
			info=new ValueInputInfo();
			info.setVersion(1);
			info.setBizDate(now);
			
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("name");
			sel.add("engineeProject.*");
			try {
				pb = ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(((ProjectBaseInfo)this.getUIContext().get("project")).getId()),sel);
				info.setProject(pb);
				info.setOrgUnit(pb.getEngineeProject());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}else{
			info.setName(info.getIndexVersion().getName()+"_"+FDCDateHelper.DateToString(now));
			info.setVersion(info.getVersion()+1);
			info.setBizDate(now);
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				ValueInputEntryInfo entry=info.getEntry().get(i);
				if(entry.getBuildId()!=null){
					entrySet.put(entry.getBuildId(), entry);
				}else{
					ptEntrySet.put(entry.getProjectId()+entry.getBuild()+entry.getProductTypeId(), entry);
				}
			}
			info.getEntry().clear();
			info.getDyEntry().clear();
			pb=info.getProject();
		}
		try{
			IndexVersionInfo indexVersion=null;
			int maxIndexVersion=0;
			Map map=new HashMap();
			ProjectDataCollectCollection col=ProjectDataCollectFactory.getRemoteInstance().getProjectDataCollectCollection("select project.name,project.longNumber,indexVersion.*,planId,areaId from where isLatest=1 and project.projectBase='"+pb.getId().toString()+"'");
			for(int i=0;i<col.size();i++){
				if(map.get(col.get(i).getProject().getLongNumber())==null){
					map.put(col.get(i).getProject().getLongNumber(), col.get(i));
					int nowNumber=Integer.parseInt(col.get(i).getIndexVersion().getNumber().replaceFirst("^0*", ""));
					if(nowNumber>maxIndexVersion){
						maxIndexVersion=nowNumber;
						indexVersion=col.get(i).getIndexVersion();
					}
				}else{
					ProjectDataCollectInfo pd=(ProjectDataCollectInfo) map.get(col.get(i).getProject().getLongNumber());
					int number=Integer.parseInt(pd.getIndexVersion().getNumber().replaceFirst("^0*", ""));
					int nowNumber=Integer.parseInt(col.get(i).getIndexVersion().getNumber().replaceFirst("^0*", ""));
					if(nowNumber>number){
						map.put(col.get(i).getProject().getLongNumber(), col.get(i));
					}
					if(nowNumber>maxIndexVersion){
						maxIndexVersion=nowNumber;
						indexVersion=col.get(i).getIndexVersion();
					}
				}
			}

			IndexVersionInfo mindexVersion=IndexVersionFactory.getRemoteInstance().getIndexVersionInfo("select * from where number='005'");
			if(ValueInputFactory.getRemoteInstance().exists("select * from where indexVersion.id='"+mindexVersion.getId()+"' and project.id='"+pb.getId().toString()+"'")){
				info.setIndexVersion(mindexVersion);
			}else{
				info.setIndexVersion(indexVersion);
			}
			info.setName(info.getIndexVersion().getName()+"_"+FDCDateHelper.DateToString(now));
			Object[] key = map.keySet().toArray();
			Arrays.sort(key); 
			
			for (int k = 0; k < key.length; k++) {
				ProjectDataCollectInfo pd = (ProjectDataCollectInfo) map.get(key[k]);
				BOSUuid areaId=pd.getAreaId();
				BOSUuid planId=pd.getPlanId();
				if(areaId==null||planId==null) continue;
				if(planId.getType().equals(new ProductTypeDateInfo().getBOSType())){
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("entrys.*");
					sel.add("entrys.protucType.*");
					ProductTypeDateInfo ptInfo=ProductTypeDateFactory.getRemoteInstance().getProductTypeDateInfo(new ObjectUuidPK(planId),sel);
					for(int i=0;i<ptInfo.getEntrys().size();i++){
						ProductTypeDateEntryInfo ptEntry=ptInfo.getEntrys().get(i);
						ValueInputEntryInfo entry=new ValueInputEntryInfo();
						entry.setProject(pd.getProject().getName());
						entry.setProjectId(pd.getProject().getId());
						entry.setBatch(String.valueOf(ptEntry.getLaunchNumber()));
						entry.setBuild("P"+ptEntry.getLaunchNumber());
						if(ptEntry.getProtucType()!=null){
							entry.setProductType(ptEntry.getProtucType().getName());
							entry.setProductTypeId(ptEntry.getProtucType().getId());
						}else{
							entry.setProductType("——");
						}
						entry.setModelName("——");
						entry.setModelType("——");
						entry.setModelArea("——");
						
						entry.setArea(ptEntry.getSupperArea());
						entry.setAccount(ptEntry.getSupperAccount());
						
						entry.setDate(ptEntry.getSupperTime());
						entry.setCalculateType(ptEntry.getCalculation());
						
						if(ptEntrySet.containsKey(entry.getProjectId()+entry.getBuild()+entry.getProductTypeId())){
							entry.setPrice(((ValueInputEntryInfo)ptEntrySet.get(entry.getProjectId()+entry.getBuild()+entry.getProductTypeId())).getPrice());
							if(CalculateTypeEnum.NOW.equals(entry.getCalculateType())){
								entry.setAmount(FDCHelper.multiply(entry.getPrice(), entry.getArea()));
							}else{
								entry.setAmount(FDCHelper.multiply(entry.getPrice(), entry.getAccount()));
							}
						}
						info.getEntry().add(entry);
					}
				}else{
					Map dateMap=new HashMap();
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("entrys.*");
					SchedulePlanInfo spInfo=SchedulePlanFactory.getRemoteInstance().getSchedulePlanInfo(new ObjectUuidPK(planId),sel);
					for(int i=0;i<spInfo.getEntrys().size();i++){
						SchedulePlanEntryInfo entry=spInfo.getEntrys().get(i);
						if(entry.getBanNumber()!=null&&entry.getProductType()!=null){
							dateMap.put(entry.getBanNumber().getId().toString()+entry.getProductType().getId().toString(), entry);
						}
					}
					sel=new SelectorItemCollection();
					sel.add("EntrysNew.*");
					sel.add("EntrysNew.banNumber.*");
					sel.add("EntrysNew.banNumber.productType.*");
					sel.add("EntrysNew.roomType.*");
					BanInformationInfo banInfo=BanInformationFactory.getRemoteInstance().getBanInformationInfo(new ObjectUuidPK(areaId),sel);
					for(int i=0;i<banInfo.getEntrysNew().size();i++){
						BanInformationEntrysNewInfo spEntry=banInfo.getEntrysNew().get(i);
						
						ValueInputEntryInfo entry=new ValueInputEntryInfo();
						entry.setProject(pd.getProject().getName());
						entry.setProjectId(pd.getProject().getId());
						entry.setBuild(spEntry.getBanNumber().getBanNumber());
						entry.setBuildId(spEntry.getBanNumber().getId());
						
						if(spEntry.getBanNumber().getProductType()!=null){
							entry.setProductType(spEntry.getBanNumber().getProductType().getName());
							entry.setProductTypeId(spEntry.getBanNumber().getProductType().getId());
						}else{
							entry.setProductType("——");
						}
						if(spEntry.getHouseTypeID()!=null){
							entry.setModelName(HouseTypeFactory.getRemoteInstance().getHouseTypeInfo(new ObjectUuidPK(spEntry.getHouseTypeID())).getName());
		        		}else{
		        			entry.setModelName("——");
		        		}
						if(spEntry.getRoomType()!=null){
							entry.setModelType(spEntry.getRoomType().getName());
						}else{
							entry.setModelType("——");
						}
						if(spEntry.getRoomArea()!=null){
							entry.setModelArea(spEntry.getRoomArea().toString());
						}else{
							entry.setModelArea("——");
						}
						entry.setAccount(spEntry.getSellNumber());
						entry.setArea(spEntry.getArea());
						if(spEntry.getProductType()!=null&&dateMap.get(spEntry.getBanNumber().getId().toString()+spEntry.getProductType().getId().toString())!=null){
							SchedulePlanEntryInfo planEntry=(SchedulePlanEntryInfo) dateMap.get(spEntry.getBanNumber().getId().toString()+spEntry.getProductType().getId().toString());
							entry.setBatch(String.valueOf(planEntry.getLaunchNumber()));
							entry.setDate(planEntry.getTakeDate());
						}
						entry.setCalculateType(spEntry.getCalculation());
						
						if(spEntry.getBanNumber()!=null&&spEntry.getBanNumber().getProductType()!=null){
							getSellHouseAmount(spEntry.getBanNumber().getId().toString(),spEntry.getBanNumber().getProductType().getId().toString(),entry);
							if(entry.getCalculateType().equals(CalculateTypeEnum.NOW)){
								entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getArea(), 2, BigDecimal.ROUND_HALF_UP));
							}else{
								entry.setPrice(FDCHelper.divide(entry.getAmount(), entry.getAccount(), 2, BigDecimal.ROUND_HALF_UP));
							}
						}
						if(entry.getAmount()==null){
							if(entrySet.containsKey(entry.getBuildId())){
								entry.setPrice(((ValueInputEntryInfo)entrySet.get(entry.getBuildId())).getPrice());
								if(CalculateTypeEnum.NOW.equals(entry.getCalculateType())){
									entry.setAmount(FDCHelper.multiply(entry.getPrice(), entry.getArea()));
								}else{
									entry.setAmount(FDCHelper.multiply(entry.getPrice(), entry.getAccount()));
								}
							}
						}
						info.getEntry().add(entry);
					}
				}
			}
		}catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		info.setIsLatest(false);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel=super.getSelectors();
		sel.add("isLatest");
		sel.add("sourceBillId");
		sel.add("state");
		sel.add("orgUnit.*");
		sel.add("CU.*");
		sel.add("indexVersion.*");
		sel.add("bizDate");
		return sel;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
//		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("project.id",this.editData.getProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("indexVersion.id",this.editData.getIndexVersion().getId().toString()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	ValueInputCollection col=ValueInputFactory.getRemoteInstance().getValueInputCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,"最新版本还未审批！");
    		SysUtil.abort();
    	}else{
    		if(col.size()==0){
    			this.txtVersion.setValue(1);
    		}else if(this.txtVersion.getIntegerValue()!=col.size()+1){
    			FDCMsgBox.showWarning(this,"请重新进行修订操作！");
        		SysUtil.abort();
    		}
    	}
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
			if(row.getStyleAttributes().getBackground().equals(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR)) continue;
				
//			if (row.getCell("decorate").getValue() == null) {
//				FDCMsgBox.showWarning(this,"装修标准不能为空！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("decorate"));
//				SysUtil.abort();
//			} 
			if (row.getCell("price").getValue() == null) {
				FDCMsgBox.showWarning(this,"销售单价(元/M2或元/套)不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("decorate"));
				SysUtil.abort();
			} 
			if (row.getCell("amount").getValue() == null) {
				FDCMsgBox.showWarning(this,"销售金额(元)不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("decorate"));
				SysUtil.abort();
			} 
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("price")){
			if(CalculateTypeEnum.NOW.equals(this.kdtEntry.getRow(e.getRowIndex()).getCell("calculateType").getValue())){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").setValue(FDCHelper.multiply(this.kdtEntry.getRow(e.getRowIndex()).getCell("price").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("area").getValue()));
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").setValue(FDCHelper.multiply(this.kdtEntry.getRow(e.getRowIndex()).getCell("price").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("account").getValue()));
			}
		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("amount")){
			if(CalculateTypeEnum.NOW.equals(this.kdtEntry.getRow(e.getRowIndex()).getCell("calculateType").getValue())){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("price").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("area").getValue(),2, BigDecimal.ROUND_HALF_UP));
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("price").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").getValue(), this.kdtEntry.getRow(e.getRowIndex()).getCell("account").getValue(),2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}
	protected void getSellHouseAmount(String id,String productTypeId,ValueInputEntryInfo entry) throws SQLException, BOSException, EASBizException{
    	BanBasedataEntryListCollection col=BanBasedataEntryListFactory.getRemoteInstance().getBanBasedataEntryListCollection("select head.id from where banBasedataEntry.id='"+id+"'");
    	if(col.size()>0){
//    		FilterInfo filter=new FilterInfo();
//        	filter.getFilterItems().add(new FilterItemInfo("room.building.id",col.get(0).getHead().getId().toString()));
//        	filter.getFilterItems().add(new FilterItemInfo("head.isExecuted",Boolean.TRUE));
//        	if(RoomPriceAdjustEntryFactory.getRemoteInstance().exists(filter)){
        		FDCSQLBuilder builder = new FDCSQLBuilder();
        		IRowSet rowSet = null;
        		builder.appendSql(" select sum(t.account) account,sum(t.area) area,sum(t.amount) amount from (select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) account,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) area,sum(sign.fcontractTotalAmount) amount");
        		builder.appendSql(" from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadId=room.fbuildingId where sign.fbizState in('SignApple','SignAudit') and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId");
        		builder.appendSql(" union all select banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) account,sum(case room.fsellType when 'PlanningSell' then room.fPlanBuildingArea when 'PreSell' then room.FBuildingArea else room.fActualBuildingArea end) area,sum(room.fbaseStandardPrice) amount");
        		builder.appendSql(" from t_she_room room left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadid=room.fbuildingid where room.FSellState!='Sign' and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId) t where t.buildId='"+id+"' and t.productTypeId='"+productTypeId+"'");
        		
        		rowSet = builder.executeQuery();
        		while (rowSet.next()) {
        			entry.setAmount(rowSet.getBigDecimal("amount"));
        			entry.setArea(rowSet.getBigDecimal("area"));
        			entry.setAccount(rowSet.getInt("account"));
        		}
//        	}
    	}
    }
}