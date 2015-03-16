/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SellSupplyPlanEditUI extends AbstractSellSupplyPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellSupplyPlanEditUI.class);
    private SellProjectInfo SellInfo = null;
    private String sellProjectID = "";
    private KDBizPromptBox f7productType = null; 
	private KDBizPromptBox f7AreaRange = null; 
    
    public SellSupplyPlanEditUI() throws Exception {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		this.setUITitle("预证获取计划");
		this.chkunlocked.setVisible(false);  //隐藏是否启用
		this.pkBizDate.setEnabled(false);
		this.prmtSellProject.setEnabled(false);
		this.btnAddNew.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnCreateFrom.setVisible(false);
		this.btnCreateTo.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnNextPerson.setVisible(false);
		initTable();
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		//this.kdtEntrys.addRow();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		
		int rowCount = this.kdtEntrys.getRowCount();
		for(int i=0;i<rowCount;i++){
			IRow row = this.kdtEntrys.getRow(i);
			if(row.getCell("productCompiste").getValue() == null){
				MsgBox.showWarning("第"+(i+1)+"行分录产品构成不能为空");
				SysUtil.abort();
			}
			if(row.getCell("productType").getValue() == null){
				MsgBox.showWarning("第"+(i+1)+"行分录产品类型不能为空");
				SysUtil.abort();
			}
			if(row.getCell("areaArange").getValue() == null){
				MsgBox.showWarning("第"+(i+1)+"行分录面积段不能为空");
				SysUtil.abort();
			}
			if(row.getCell("yzrq").getValue() == null){
				MsgBox.showWarning("第"+(i+1)+"行分录预计获取预证日期不能为空");
				SysUtil.abort();
			}
			if(row.getCell("ploidy").getValue() == null || new BigDecimal(row.getCell("ploidy").getValue().toString()).compareTo(BigDecimal.ZERO)==0){
				MsgBox.showWarning("第"+(i+1)+"行分录套数不能为空并且不能为零");
				SysUtil.abort();
			}
			if(row.getCell("area").getValue() == null || new BigDecimal(row.getCell("area").getValue().toString()).compareTo(BigDecimal.ZERO)==0){
				MsgBox.showWarning("第"+(i+1)+"行分录面积不能为空并且不能为零");
				SysUtil.abort();
			}
			if(row.getCell("price").getValue() == null || new BigDecimal(row.getCell("price").getValue().toString()).compareTo(BigDecimal.ZERO)==0){
				MsgBox.showWarning("第"+(i+1)+"行分录单价不能为空并且不能为零");
				SysUtil.abort();
			}
			
		}
		
	}

	private void initTable(){
		ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
		kdtEntrys.getColumn("area").setEditor(bigDecimalEditor);
		kdtEntrys.getColumn("area").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		kdtEntrys.getColumn("ploidy").setEditor(bigDecimalEditor);
		kdtEntrys.getColumn("ploidy").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		kdtEntrys.getColumn("price").setEditor(bigDecimalEditor);
		kdtEntrys.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		kdtEntrys.getColumn("totalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		kdtEntrys.getColumn("totalAmount").setEditor(bigDecimalEditor);
		kdtEntrys.getColumn("totalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		kdtEntrys.getColumn("totalAmount").getStyleAttributes().setLocked(true);
		
		kdtEntrys.getColumn("productType").setEditor(new KDTDefaultCellEditor(getF7productType(1)));
		kdtEntrys.getColumn("areaArange").setEditor(new KDTDefaultCellEditor(getF7Area(1)));
		
		if(this.oprtState == OprtState.ADDNEW){
			this.kdtEntrys.addRow();
		}
    }
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);
		this.pkBizDate.setEnabled(false);
		this.prmtSellProject.setEnabled(false);
	}

	protected void kdtEntrys_editStopped(KDTEditEvent e) {
		int columnIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		KDTable table = (KDTable) e.getSource();
		if(table.getColumnKey(columnIndex).equals("area") || table.getColumnKey(columnIndex).equals("price")){
			IRow row = table.getRow(rowIndex);
			BigDecimal area = row.getCell("area").getValue() == null? BigDecimal.ZERO : ((BigDecimal)row.getCell("area").getValue()).setScale(2);
			BigDecimal price = row.getCell("price").getValue() == null? BigDecimal.ZERO : ((BigDecimal)row.getCell("price").getValue()).setScale(2);
			row.getCell("totalAmount").setValue(area.multiply(price));
		}
	}
    
    public KDBizPromptBox getF7Area(int columnIndex){
		if(f7AreaRange==null){
			f7AreaRange = new KDBizPromptBox();
			f7AreaRange.setQueryInfo("com.kingdee.eas.fdc.market.app.AreaSetQuery");
	    	EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			view.setFilter(filter);
			f7AreaRange.setEntityViewInfo(view);
			f7AreaRange.setEditable(true);
			f7AreaRange.setDisplayFormat("$number$");
			f7AreaRange.setEditFormat("$number$");
			f7AreaRange.setCommitFormat("$id$");
		}
		if(columnIndex == 1){
			return f7AreaRange;
		}else{
			return null;
		}
	}
    
    public KDBizPromptBox  getF7productType(int columnIndex){
		if(f7productType==null){
			f7productType = new KDBizPromptBox();
			f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
			view.setFilter(filter);
			f7productType.setEntityViewInfo(view);
			f7productType.setEditable(true);
			f7productType.setDisplayFormat("$name$");
			f7productType.setEditFormat("$number$");
			f7productType.setCommitFormat("$number$");	
		}
		if(columnIndex == 1){
			return f7productType;
		}else{
			return null;
		}
	}
    
    
    

	public void storeFields() {
		super.storeFields();
	}

	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
        return com.kingdee.eas.fdc.market.SellSupplyPlanFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table) {
        return null;
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData() {
        com.kingdee.eas.fdc.market.SellSupplyPlanInfo objectValue = new com.kingdee.eas.fdc.market.SellSupplyPlanInfo();
        SellInfo = (SellProjectInfo)getUIContext().get("sellProject");
		if(SellInfo!=null) {
			sellProjectID = SellInfo.getId().toString();
		}
		try {
			if (sellProjectID != null) {
				SellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectID));
				objectValue.setSellProject(SellInfo);
			}
			objectValue.setBizDate(SysUtil.getAppServerTime(null));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        return objectValue;
    }

}