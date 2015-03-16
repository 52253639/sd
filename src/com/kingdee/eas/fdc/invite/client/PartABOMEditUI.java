/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.invite.PartABOMEntryInfo;
import com.kingdee.eas.fdc.invite.PartABOMFactory;
import com.kingdee.eas.fdc.invite.PartABOMInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.enums.EnumUtils;

/**
 * 甲供物资清单 编辑界面
 */
public class PartABOMEditUI extends AbstractPartABOMEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PartABOMEditUI.class);
    
    /**
     * output class constructor
     */
    public PartABOMEditUI() throws Exception
    {
        super();
    }
    
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}
    }   
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        if(editData!=null&&FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
        	this.actionSave.setEnabled(false);
        }
    }
	
	protected KDTable getDetailTable() {		
		return this.kdtEntrys;
	}
	
	protected IObjectValue createNewData() {		
		PartABOMInfo object = new PartABOMInfo();
		object.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		object.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		return object;
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		PartABOMEntryInfo info = new PartABOMEntryInfo();
		info.setParent(editData);
		editData.getEntrys().add(info);
		return info;
	}
	
	protected ICoreBase getBizInterface() throws Exception {		
		return PartABOMFactory.getRemoteInstance();
	}
		
	/**
	 * 获取物料F7
	 * @return 物料F7的KDBizPromptBox
	 */
	private KDBizPromptBox getF7Material()
	{
		KDBizPromptBox f7MatNum = new KDBizPromptBox();
		f7MatNum.setDisplayFormat("$number$");
		f7MatNum.setEditFormat("$number$");
		f7MatNum.setCommitFormat("$number$");
		f7MatNum.setSelector(new MaterialPromptSelector(this));  // 使用自定义左树右表的F7 Added by owen_wen 2010-8-27
//		f7MatNum.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialQuery");
		f7MatNum.setEnabledMultiSelection(true);
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add( new SelectorItemInfo("id"));
		sic.add( new SelectorItemInfo("number"));
		sic.add( new SelectorItemInfo("name"));
		sic.add( new SelectorItemInfo("model"));
		sic.add( new SelectorItemInfo("baseUnit.id"));
		sic.add( new SelectorItemInfo("baseUnit.number"));
		sic.add( new SelectorItemInfo("baseUnit.name"));
		
		f7MatNum.setSelectorCollection(sic);

		return f7MatNum;
	}
	
	/**
	 * 编辑分录中的物料结束后进行处理，若有重复的，直接删除，不再做添加重复物料提示判断。
	 */
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		if(e.getColIndex()==getDetailTable().getColumnIndex("material.number")){
			KDTable table = getDetailTable();
			if(e.getValue()!=null&&e.getValue() instanceof Object[]){
				Object[] materials = (Object[]) e.getValue();
				
				//过滤掉已经存在的物料
				for(int i = 0; i < materials.length; ++i)
				{
					MaterialInfo material = null;
					if(materials[i] instanceof MaterialInfo)
						material = (MaterialInfo)materials[i];
					
					for(int j = 0; j < table.getRowCount(); ++j)
					{
						if(j != table.getSelectManager().getActiveRowIndex())
						{
							if(table.getRow(j).getCell("material.number").getValue() == null)
								continue ;
							MaterialInfo supMateral = (MaterialInfo)table.getRow(j).getCell("material.number").getValue();
							if(material.getId().equals(supMateral.getId()))
								materials[i] = null;

						}
					}
				}
				
				IRow row = table.getRow(e.getRowIndex());
				for(int i=0;i<materials.length;i++){
					MaterialInfo material = null;
					if(materials[i] instanceof MaterialInfo)
						material = (MaterialInfo)materials[i];
					
					if(i == 0 && material == null)
					{
						table.removeRow(e.getRowIndex());
						continue;
					}
					
					if(i>0){
						if(material != null)
						{
							addLine(table);
							row = table.getRow(table.getRowCount()-1);
							IObjectValue detailData = createNewDetailData(table);
							row.setUserObject(detailData);
						}
						else
						{
							continue;
						}
					}
					
					row.getCell("material.number").setValue(material);
					row.getCell("material.name").setValue(material.getName());
					row.getCell("model").setValue(material.getModel());
					row.getCell("unit").setValue(material.getBaseUnit());
					if(row.getUserObject()!=null&&row.getUserObject() instanceof PartABOMEntryInfo)
					{
						PartABOMEntryInfo entry = (PartABOMEntryInfo)row.getUserObject();
						entry.setMaterial(material);
					}
				}
				
				for(int k=table.getRowCount()-1;k>=0;k--)
				{
					if(table.getRow(k).getCell("material.number").getValue() == null ||"".equals(table.getRow(k).getCell("material.number").getValue()) )
					{
						table.removeRow(table.getRow(k).getRowIndex());
					}
				}
			}
		}
	}
	
	// 在onload()中被调用
	protected void initWorkButton() {		
		super.initWorkButton();
		initUI();
	}
	
	/**
	 * 初始化一些图标和按钮状态
	 */
	private void initUI() {
    	JButton btnAddLine = this.kDContainer1.add(actionAddLine);
    	JButton btnInsertLine = this.kDContainer1.add(actionInsertLine);
		JButton btnDelLine = this.kDContainer1.add(actionRemoveLine);
		btnAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));		
		btnAddLine.setSize(22, 19);
		btnInsertLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
		btnInsertLine.setSize(22, 19);
		btnDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelLine.setSize(22, 19);
		
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
				
		this.actionViewDoProccess.setVisible(false);
		this.actionWorkflowList.setVisible(false);		
		
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		
		//屏蔽 “表格->复制分录” 按钮，Added By Owen_wen 2010-10-21
		this.actionCopyLine.setVisible(false); 
		this.actionCopyLine.setEnabled(false);
    }
	
	public void onLoad() throws Exception {
		
		super.onLoad();
		
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}else if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
		
		if(editData!=null&&FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
		this.kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.txtNumber.setMaxLength(80);
		
		this.prmtCurProject.setDisplayFormat("$number$ $name$");
		this.prmtCurProject.setEditFormat("$name$");
		this.prmtCurProject.setCommitFormat("$name$");
		this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		
		prmtCurProject.addSelectorListener(new SelectorListener()
		{
			public void willShow(SelectorEvent e) {
				try {
					setProjectFilter(e);
				} catch (Exception e1) {
					logger.error(e1);
				}
			}
		});
		
		
		//设置物料F7
		this.kdtEntrys.getColumn("material.number").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		this.kdtEntrys.getColumn("material.number").setRequired(true);
		ObjectValueRender segNum = new ObjectValueRender();
		segNum.setFormat(new BizDataFormat("$number$"));
		this.kdtEntrys.getColumn("material.number").setRenderer(segNum);
		this.getDetailTable().getColumn("material.number").setEditor(new KDTDefaultCellEditor(this.getF7Material()));
			 
		
		/**
		 * 设置计量单位为F7控件
		 */
		IColumn colUnit = this.kdtEntrys.getColumn("unit");
		colUnit.setRequired(true);
		
		colUnit.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		KDBizPromptBox f7MeasureUnit = new KDBizPromptBox();
		
		f7MeasureUnit.setDisplayFormat("$number$");
		f7MeasureUnit.setEditFormat("$number$");
		f7MeasureUnit.setCommitFormat("$number$");
		f7MeasureUnit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		
		KDTDefaultCellEditor f7EditorUnit = new KDTDefaultCellEditor(f7MeasureUnit);
		colUnit.setEditor(f7EditorUnit);
		
		KDFormattedTextField amountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		
		amountField.setPrecision(2);
		amountField.setNegatived(false);
		amountField.setSupportedEmpty(true);
		
		KDTDefaultCellEditor qtyDecimalEditor = new KDTDefaultCellEditor(amountField);
		
		this.kdtEntrys.getColumn("amount").setEditor(qtyDecimalEditor);
		this.kdtEntrys.getColumn("amount").setRequired(true);
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00");
		/**
		 * 设置进场日期
		 */
		IColumn inputDateCol = this.kdtEntrys.getColumn("enterTime");
		
		KDDatePicker inputDateField = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(inputDateField);
		inputDateCol.setEditor(dateEditor);
		
		FDCHelper.formatTableDate(kdtEntrys, "enterTime");
		
		/**
		 * 设置规格型号
		 */
		KDTextField txtSize = new KDTextField();
		txtSize.setMaxLength(80);
		KDTDefaultCellEditor sizeCellEditor = new KDTDefaultCellEditor(txtSize);
		this.kdtEntrys.getColumn("model").setEditor(sizeCellEditor);
		this.kdtEntrys.getColumn("brand").setEditor(sizeCellEditor);
		this.kdtEntrys.getColumn("factory").setEditor(sizeCellEditor);
		this.kdtEntrys.getColumn("use").setEditor(sizeCellEditor);
		
		KDComboBox box = new KDComboBox();
		box.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.InstallTypeEnum").toArray());
		KDTDefaultCellEditor boxCellEditor = new KDTDefaultCellEditor(box);
		this.kdtEntrys.getColumn("install").setEditor(boxCellEditor);
		
		KDComboBox sectionBox = new KDComboBox();
		sectionBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.SectionEnum").toArray());
		KDTDefaultCellEditor sectionBoxCellEditor = new KDTDefaultCellEditor(sectionBox);
		this.kdtEntrys.getColumn("section").setEditor(sectionBoxCellEditor);
		
		/**
		 * 设置备注cell长度
		 */
		KDTextField txtDescription = new KDTextField();
		txtDescription.setMaxLength(100);
		KDTDefaultCellEditor descriptionCellEditor = new KDTDefaultCellEditor(txtDescription);
		
		this.kdtEntrys.getColumn("remark").setEditor(descriptionCellEditor);
	}

	private SelectorItemCollection getCurProjectSelectorCols()
	{
		SelectorItemCollection sic = new SelectorItemCollection();
		
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("name"));
		
		sic.add(new SelectorItemInfo("landDeveloper.name"));
		sic.add(new SelectorItemInfo("startDate"));
		sic.add(new SelectorItemInfo("sortNo"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		
		sic.add(new SelectorItemInfo("parent.id"));
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("projectStatus.name"));
		sic.add(new SelectorItemInfo("projectPeriod"));
		sic.add(new SelectorItemInfo("projectType.name"));
		
		sic.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sic.add(new SelectorItemInfo("projectStatus.name"));
		sic.add(new SelectorItemInfo("projectType.name"));
		
		sic.add(new SelectorItemInfo("isLeaf"));
	
		return sic ;
	}
	
	private void setProjectFilter(SelectorEvent e) throws Exception
	{		
		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		if(orgs==null){
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
		            OrgType.CostCenter,  null,  null, null);
		}
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();
		
		view.setSelector(getCurProjectSelectorCols());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
		
		
		/**
		 * 组织架构树:财务组织，带需求确认好再修改
		 */
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber",orgUnit.getLongNumber()+"%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",authorizedOrgs,CompareType.INCLUDE));

		//明细工程项目
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.valueOf(true)));
		
		view.setFilter(filter);
		prmtCurProject.setEntityViewInfo(view);
		if (prmtCurProject.getSelector() != null && prmtCurProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI)prmtCurProject.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI)prmtCurProject.getSelector()).onLoad();
			

		}else {
			prmtCurProject.getEntityViewInfo().setFilter(filter);
			prmtCurProject.getQueryAgent().resetRuntimeEntityView();
			prmtCurProject.setRefresh(true);
		}
	}

	
	protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		editData.setName(editData.getNumber());
		if(StringUtils.isEmpty(editData.getNumber()))
		{
			FDCMsgBox.showWarning(this,"单据编号不能为空");
			abort();
		}
		
		if(editData.getCurProject() == null)
		{
			FDCMsgBox.showWarning(this,"工程项目不能为空");
			abort();
		}
		
		if(editData.getPartABOMType() == null)
		{
			FDCMsgBox.showWarning(this,"请选择单据类别");
			abort();
		}
		if(editData.getEntrys().size() == 0)
		{
			FDCMsgBox.showWarning(this,"请至少录入一行分录");
			abort();
		}
		
		
		//检测分录
		for(int i = 0; i < kdtEntrys.getRowCount(); ++i)
		{
			IRow row = kdtEntrys.getRow(i);
			Integer index = new Integer(i+1);
			String warning = "第" + index.toString() + "行、第" ;
			if(row.getCell("material.number").getValue() == null)
			{
				warning = warning + "1列物料不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell("unit").getValue() == null)
			{
				warning = warning + "5列计量单位不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell("amount").getValue() == null)
			{
				warning = warning + "6列数量不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
		}
	}
	
	public SelectorItemCollection getSelectors(){
		SelectorItemCollection sic = super.getSelectors();
		sic.add("state");
		sic.add("orgUnit.id");
		sic.add("CU.id");
		return sic;
	}
}