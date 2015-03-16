/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.AppraiseTemplateEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * 专家评标 编辑界面
 */
public class ExpertAppraiseEditUI extends AbstractExpertAppraiseEditUI
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(ExpertAppraiseEditUI.class);
    private Map initData = null;
    private Map BindCellScoreMap = new HashMap();
    private Map BindCellDesctMap = new HashMap();
    
    private boolean isUseWeight = true;
    
    private FullOrgUnitInfo curOrgUnit = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
    
    /**
     * output class constructor
     */
    public ExpertAppraiseEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        storeLineFields(null,null,null);
    }

    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
        this.actionRemove.setEnabled(false);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        this.actionSave.setEnabled(false);
        this.actionRemove.setEnabled(false);
    }

    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        if(STATUS_EDIT.equals(this.getOprtState())){
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState())){
				this.actionSave.setEnabled(false);
			}
		}
        this.prmtInviteProject.setEnabled(false);
    }

    /**
     * 招标执行信息
     */
    public void actionInviteAllInformation_actionPerformed(ActionEvent e)
    		throws Exception {
    	//2010-04-27 yell 新增判断条件
    	if((editData==null||editData.getInviteProject()==null||editData.getInviteProject().getId()==null)&&
    			this.prmtInviteProject.getValue()==null){
    		MsgBox.showInfo("招标立项为空!");
    		return;
    	}
    	String inviteProjectId = null;
		if (editData.getInviteProject().getId() != null) {
			inviteProjectId = editData.getInviteProject().getId().toString();
		}
		if (prmtInviteProject.getValue() != null) {
			inviteProjectId = ((InviteProjectInfo) prmtInviteProject.getValue()).getId().toString();
		}
		UIContext uiContext = new UIContext(this);

		uiContext.put(UIContext.ID, null);
		uiContext.put("INVITE_PROJECT", inviteProjectId);
		uiContext.put("LIST_UI", "com.kingdee.eas.fdc.invite.client.InviteProjectListUI");
		uiContext.put("INVITEPROJECT_NAME", null);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteAllInformationUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    /**
     * @param pk
     * @throws BOSException
     * @throws EASBizException
     * @throws Exception
     */
    protected void removeByPK(IObjectPK pk) throws Exception
    {
        // 异常处理：要删除的项目已经不存在了，则提示用户。
        // 2004-9-10 by Jerry

        // 判断是否有网络互斥，保存以前状态，然后设置删除状态。
        String tempState = this.getOprtState();
        this.setOprtState("REMOVE");
        try{
           this.pubFireVOChangeListener(pk.toString());
        }catch(Throwable e)
        {
        	this.setOprtState(tempState);
        	handUIException(e);
			abort();
        }
        //IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;2007-06-05
        //getUIContext().put("CURRENT.VO",null) ;
        try
        {
            //setDataObject(val) ;删除之后不再进行对象比较
        	if (!UtilRequest.isPrepare("ActionRemove",this))
        	{
                this.getBizInterface().delete(pk);
                }
        }
        finally
        {
            //恢复状态。
//            this.setOprtState(tempState);
            //getUIContext().put("CURRENT.VO",val) ;            
//            this.initOldData(null);
            
//            MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+"confirm_close"));
        	this.destroyWindow();
        }
    }
	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO 自动生成方法存根
		ExpertAppraiseEntryInfo entry = new ExpertAppraiseEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		return entry;
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return this.kdtEntry;
	}

	public SelectorItemCollection getSelectors() {
		// TODO 自动生成方法存根
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("inviteProject.name"));
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        sic.add(new SelectorItemInfo("inviteProject.appraiseTemplate.templateType.name"));
        sic.add(new SelectorItemInfo("inviteProject.appraiseTemplate.number"));
        sic.add(new SelectorItemInfo("inviteProject.appraiseTemplate.name"));
        sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
        sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));
        
        sic.add(new SelectorItemInfo("createTime"));
        
        
		return sic;
	}

	protected void addLine(KDTable table) {
		if (table == null) {
			return;
		}
		IObjectValue detailData = createNewDetailData(table);
		if (detailData!=null)
        {
			IRow row = table.addRow();
			getUILifeCycleHandler().fireOnAddNewLine(table,detailData);
			loadLineFields(table, row, detailData);
			afterAddLine(table, detailData);
        }
	}

	protected IObjectValue createNewData() {
		ExpertAppraiseInfo object = new ExpertAppraiseInfo();
	
		object.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		object.setCreateTime(new Timestamp(FDCHelper.getCurrentDate().getTime()));
		object.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo());
		
		if(initData==null){
			try {
				this.fetchInitData();
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(initData!=null){
			AppraiseTemplateInfo template = (AppraiseTemplateInfo)initData.get("appraiseTemplate");
			if(template!=null && template.getTemplateEntry() != null){
				for(Iterator it = template.getTemplateEntry().iterator();it.hasNext();){
					AppraiseTemplateEntryInfo tInfo = (AppraiseTemplateEntryInfo)it.next();
					ExpertAppraiseEntryInfo entryInfo = new ExpertAppraiseEntryInfo();
					entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
					entryInfo.setGuideLine(tInfo.getGuideLine());
					entryInfo.setWeight(tInfo.getWeight()==null?0:tInfo.getWeight().doubleValue());
					entryInfo.setDescription(tInfo.getDescription());
					SupplierQualifyInfo supplierQualify = (SupplierQualifyInfo)initData.get("supplierQualify");
					Set hasRecordSupplierSet = (HashSet)initData.get("hasRecordSupplierSet");					
					if(supplierQualify != null)
					{
						for(int i=0;i<supplierQualify.getEntry().size();i++){
							SupplierQualifyEntryInfo supp = supplierQualify.getEntry().get(i);
							if(!hasRecordSupplierSet.contains(supp.getSupplier().getId().toString()))
								continue;
							ExpertAppraiseEntryScoreInfo scoreInfo = new ExpertAppraiseEntryScoreInfo();
							scoreInfo.setId(BOSUuid.create(scoreInfo.getBOSType()));
//							scoreInfo.setSupplier(supp.getSupplier());
							scoreInfo.setParent(entryInfo);
							entryInfo.getScores().add(scoreInfo);
						}
						entryInfo.setParent(object);
						object.getEntry().add(entryInfo);
					}
				}
			}
		}
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ExpertAppraiseFactory.getRemoteInstance();
	}

	protected void initWorkButton() {
		// TODO 自动生成方法存根
		super.initWorkButton();
		this.actionInsertLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
	}

	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		// TODO 自动生成方法存根
		//super.loadLineFields(table, row, obj);
	}
	private void fetchInitData() throws EASBizException, BOSException{
		if(initData!=null)
			return;
		Map param = new HashMap();
		String inviteProjectId = (String)this.getUIContext().get("inviteProjectId");
		param.put("inviteProjectId",inviteProjectId);
		String objectId = getUIContext().get(UIContext.ID) == null ? null : getUIContext().get(UIContext.ID).toString();
		param.put("objectId",objectId);
		initData = ExpertAppraiseFactory.getRemoteInstance().fetchInitData(param);
	}

	public void onLoad() throws Exception {
		fetchInitData();
		if(initData != null){
			InviteProjectInfo inviteProject = (InviteProjectInfo) initData.get("inviteProject");
			isUseWeight = inviteProject.getAppraiseTemplate().isIsUserWidth();
		}
		super.onLoad();
		if(editData!=null&&editData.getCreator()!=null&&!editData.getCreator().getId().toString().equals(SysContext.getSysContext().getCurrentUserInfo().getId().toString())){
			FDCMsgBox.showInfo(this,"只有专家本人才能执行此操作");
			abort();
		}
		
		prmtInviteProject.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("inviteMode.*"));
				sic.add(new SelectorItemInfo("orgUnit.*"));
				prmtInviteProject.getQueryAgent().setSelectorCollection(sic);
			}});
		
		prmtInviteProject.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				InviteProjectInfo newValue = (InviteProjectInfo) eventObj.getNewValue();
				if(newValue != null){
//					prmtInviteMode.setData(newValue.getInviteMode());
					txtInviteProjectOrg.setText(newValue.getOrgUnit().getName());
				}
				
			}});
		
		if(this.prmtInviteProject.getValue()==null&&editData!=null){
			InviteProjectInfo inviteProject = (InviteProjectInfo)initData.get("inviteProject");
			this.editData.setInviteProject(inviteProject);
			this.editData.setNumber(inviteProject.getNumber()+SysContext.getSysContext().getCurrentUserInfo().getNumber());
			this.editData.setName(inviteProject.getName()+SysContext.getSysContext().getCurrentUserInfo().getNumber());
			prmtInviteProject.setData(inviteProject);			
			this.loadFields();
		}
		
//		prmtInviteProject.addDataChangeListener(new DataChangeListener(){
//
//			public void dataChanged(DataChangeEvent eventObj) {
//                 InviteProjectInfo newValue = (InviteProjectInfo) eventObj.getNewValue();
//                 InviteProjectInfo oldValue = (InviteProjectInfo) eventObj.getOldValue();
//                 if(newValue != null && oldValue != null){
//                	 
//                 }   
//				
//			}});
		initDetailTable();
		loadDetailTable();

		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuView.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuBiz.setVisible(false);
		
		this.actionCopyLine.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.txtDesc.setMaxLength(1000);
		
		this.prmtInviteProject.setEnabled(false);
		
		
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		Component[] components = this.menuWorkflow.getPopupMenu().getComponents();

		for(int i=components.length-1; i>=0; --i){
			this.menuWorkflow.remove(i);
		}
		this.actionAddNew.setVisible(true);
		initButtonState();
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionInviteAllInformation.setEnabled(true);
		
//		if(this.oprtState.equals(OprtState.ADDNEW) && editData == null){
//			actionInviteAllInformation.setEnabled(false);
//    	}
		
		actionPrintPreview.setVisible(true);
		actionPrint.setVisible(true);
		actionPrintPreview.setEnabled(true);
		actionPrint.setEnabled(true);
		
		//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.txtInviteProjectName.setMaxLength(255);
	}
	
	public void initDetailTable(){
		SupplierQualifyInfo supplierQualify = (SupplierQualifyInfo)initData.get("supplierQualify");
		if(supplierQualify==null){
			FDCMsgBox.showError(this,"此立项还没有进行供应商资格预审");
			abort();
		}
		Set hasRecordSupplierSet = (HashSet)initData.get("hasRecordSupplierSet");
		if(hasRecordSupplierSet==null||hasRecordSupplierSet.size()==0){
			FDCMsgBox.showError(this,"此立项还没有任何供应商投标记录");
			abort();
		}
			
		KDTable table = this.getDetailTable();
		int baseIndex = table.getColumnIndex("score");
		
		String baseCollName = (String)table.getHeadRow(0).getCell(baseIndex).getValue();
		for(Iterator it = supplierQualify.getEntry().iterator();it.hasNext();){
			SupplierQualifyEntryInfo entryInfo = (SupplierQualifyEntryInfo)it.next();
			if(!hasRecordSupplierSet.contains(entryInfo.getSupplier().getId().toString())){
				continue;
			}
			baseIndex++;
			IColumn column = table.addColumn(baseIndex);
			column.setKey(entryInfo.getSupplier().getName());
			column.setUserObject(entryInfo.getSupplier());
			KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
			indexValue_TextField.setDataType(KDFormattedTextField.DOUBLE_TYPE);
			indexValue_TextField.setMaximumValue(FDCHelper.ONE_HUNDRED);
			indexValue_TextField.setMinimumValue(FDCHelper._ONE_HUNDRED);
			indexValue_TextField.setSupportedEmpty(true);
			indexValue_TextField.setPrecision(2);
			
			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			column.setEditor(indexValue_CellEditor);
			table.getHeadRow(0).getCell(column.getKey()).setValue(baseCollName);
			table.getHeadRow(1).getCell(column.getKey()).setValue(entryInfo.getSupplier().getName());
		}
		table.getHeadMergeManager().mergeBlock(0,table.getColumnIndex("score"),0,baseIndex);
		table.getColumn("score").getStyleAttributes().setHided(true);
		table.getColumn("glTypeName").getStyleAttributes().setLocked(true);
		table.getColumn("glName").getStyleAttributes().setLocked(true);
		table.getColumn("weight").getStyleAttributes().setLocked(true);
		table.getColumn("weight").getStyleAttributes().setNumberFormat("#0.00");
		table.getColumn("weight").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("score").getStyleAttributes().setLocked(true);
		
		final KDTextField textField = new KDTextField();
		
		textField.setMaxLength(1000);
		textField.setAutoscrolls(true);
		textField.setEditable(true);
		textField.setToolTipText("Alt+Enter换行");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(editor);
		table.getColumn("description").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		table.getColumn("description").getStyleAttributes().setWrapText(true);
		
		initData.put("hasInitTable",Boolean.TRUE);
		
		if(!isUseWeight){
			table.getColumn("weight").getStyleAttributes().setHided(true);
		}
		
	}
	public void loadDetailTable(){
		if(initData.containsKey("hasInitTable")){
			KDTable table = this.getDetailTable();
			if(initData.get("editDataEntry")!=null){
				editData.getEntry().clear();
				editData.getEntry().addCollection((ExpertAppraiseEntryCollection)initData.get("editDataEntry"));
			}
			String expStr = "";
			if(editData!=null&&editData.getEntry()!=null){
				for(Iterator it=editData.getEntry().iterator();it.hasNext();){
					ExpertAppraiseEntryInfo info = (ExpertAppraiseEntryInfo)it.next();
					
					if(info.getGuideLine()!=null&&info.getGuideLine().getGuideLineType()!=null){
						IRow row = table.addRow();
						row.setUserObject(info);
						row.getCell("glTypeName").setValue(info.getGuideLine().getGuideLineType().getName());
						row.getCell("glName").setValue(info.getGuideLine().getName());
						row.getCell("weight").setValue(new Double(info.getWeight()));
						row.getCell("description").setValue(info.getDescription());
						row.getCell("description").setUserObject(info);
						BindCellDesctMap.put(info.getId().toString(),row.getCell("description"));
						for(int i=0;i<info.getScores().size();i++){
							String colKey = info.getScores().get(i).getSupplier().getName();
							row.getCell(colKey).setValue(new Double(info.getScores().get(i).getScore()));
							row.getCell(colKey).getStyleAttributes().setNumberFormat("#0.00");
							row.getCell(colKey).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
							row.getCell(colKey).setUserObject(info.getScores().get(i));
							BindCellScoreMap.put(info.getScores().get(i).getId().toString(),row.getCell(colKey));
						}
						if(isUseWeight){
							expStr += (expStr.length()==0?"":"+")+"c"+(row.getRowIndex()+1)+"*~"+(row.getRowIndex()+1)+"";
						}else{
							expStr += (expStr.length()==0?"":"+")+"~"+(row.getRowIndex()+1)+"";
						}
							
						logger.info(expStr);
					}
					 
				}
			}
			
			KDTFootManager footRowManager= kdtEntry.getFootManager();
			footRowManager = new KDTFootManager(kdtEntry);
			footRowManager.addFootView();
			kdtEntry.setFootManager(footRowManager);
			
			IRow sumRow = table.addRow();
			IRow footRow = footRowManager.addFootRow(0);
			
			sumRow.getCell(0).setValue("合计");
			sumRow.getCell(2).setExpressions("=SUM(c1:c"+editData.getEntry().size()+")");
			for(int i=3;i<=table.getColumnCount()-2;i++){
				String str = "";
				if(isUseWeight){
					str = "=("+expStr.replaceAll("~",String.valueOf((char)(i+97)))+")/SUM(c1:c"+editData.getEntry().size()+")";
				}else{
					str = "=("+expStr.replaceAll("~",String.valueOf((char)(i+97)))+")";
				}
				logger.info(str);	
				sumRow.getCell(i).setExpressions(str);
				sumRow.getCell(i).getStyleAttributes().setNumberFormat("#0.00");
				sumRow.getCell(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			sumRow.getStyleAttributes().setLocked(true);
			sumRow.getStyleAttributes().setHided(true);
			
			for(int i=2;i<=table.getColumnCount()-2;i++){
				footRow.getCell(i).setValue(sumRow.getCell(i).getValue());
				footRow.getCell(i).getStyleAttributes().setNumberFormat("#0.00");
				footRow.getCell(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			
			sumRow.getStyleAttributes().setLocked(true);
			footRow.getCell(0).setValue("合计");
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
		}
		
	}
	/**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.invite.ExpertAppraiseInfo)ov;
    }

	protected void storeLineFields(KDTable table, IRow row, IObjectValue obj) {
		for(Iterator it=BindCellScoreMap.keySet().iterator();it.hasNext();){
			String infoId = (String)it.next();
			ICell cell = (ICell)BindCellScoreMap.get(infoId);
			
			if(cell!=null){
				ExpertAppraiseEntryScoreInfo info = (ExpertAppraiseEntryScoreInfo)cell.getUserObject();
				if(cell.getValue()==null)
				{
					FDCMsgBox.showWarning(this,"数据没有录入完全；行："+(cell.getRowIndex()+1)+"  列："+cell.getColumnIndex());
					abort();
				}
				info.setScore(FDCHelper.toBigDecimal(cell.getValue()).doubleValue());
			}
			
		}
		for(Iterator it=BindCellDesctMap.keySet().iterator();it.hasNext();){
			String infoId = (String)it.next();
			ICell cell = (ICell)BindCellDesctMap.get(infoId);
			if(cell!=null)
			{
				ExpertAppraiseEntryInfo info = (ExpertAppraiseEntryInfo)cell.getUserObject();
				info.setDescription((String)cell.getValue());
			}
		}
		editData.getEntry().clear();
		table = this.getDetailTable();
		for(int i=0;i<table.getRowCount();i++){
			if(table.getRow(i).getUserObject()!=null&&table.getRow(i).getUserObject() instanceof ExpertAppraiseEntryInfo){
				editData.getEntry().add((ExpertAppraiseEntryInfo)table.getRow(i).getUserObject());
			}
		}
		this.initData.put("hasSave",Boolean.TRUE);
	}

	public boolean isModify() {
		if (STATUS_FINDVIEW.equals(getOprtState())) {
			return false;
		}else{
			if(initData.containsKey("hasSave"))
				return false;
		}
		return super.isModify();
	}

	private void initButtonState()
	{
		boolean flag = false ;
		flag = curOrgUnit.getId().equals(editData.getOrgUnit().getId());
		
		if(getOprtState().equals(OprtState.VIEW))
		{
			if(!flag)
			{
				this.actionEdit.setEnabled(false);
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
			else
			{
				if(editData.getState().equals(FDCBillStateEnum.SAVED))
				{
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
				}
				else if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
				{
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
				}
				else if(editData.getState().equals(FDCBillStateEnum.AUDITTED))
				{
					this.actionEdit.setEnabled(false);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
				else
				{
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
			}
		}
		else if(getOprtState().equals(OprtState.ADDNEW))
		{
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(true);
			this.actionSubmit.setEnabled(true);
			this.actionRemove.setEnabled(false);
		}
		else if(getOprtState().equals(OprtState.EDIT))
		{
			if(editData.getState().equals(FDCBillStateEnum.SAVED))
			{
				this.actionEdit.setEnabled(false);
				this.actionSave.setEnabled(true);
				this.actionSubmit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}
			else if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
			{
				this.actionEdit.setEnabled(false);
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}
		}
		else
		{
			this.actionEdit.setEnabled(false);
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		this.actionAddNew.setEnabled(false);
	}

	protected boolean isShowAttachmentAction() {
		return false ;
	}
	
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception 
	{
		KDTFootManager footRowManager= kdtEntry.getFootManager();
		IRow sumRow = kdtEntry.getRow(kdtEntry.getRowCount()-1);
		IRow footRow = footRowManager.getFootRow(0);
		
		for(int i=2;i<=kdtEntry.getColumnCount()-2;i++){
			footRow.getCell(i).setValue(sumRow.getCell(i).getValue());
			footRow.getCell(i).getStyleAttributes().setNumberFormat("#0.00");
			footRow.getCell(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		
		footRow.getCell(0).setValue("合计");
		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
	}
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK(), this.getDetailTable());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/invite/ExpertAppraiseForPrint";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.ExpertAppraisePrintQuery");
	}
}