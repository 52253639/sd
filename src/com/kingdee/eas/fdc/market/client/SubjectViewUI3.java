/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SubjectViewUI3 extends AbstractSubjectViewUI3
{
    private static final Logger logger = CoreUIObject.getLogger(SubjectViewUI3.class);
    //
    protected DataBinder subjectDataBinder = new DataBinder();
    ValidateHelper subjectHelper ;//=  new ValidateHelper( getDataObject() , this.dataBinder ) ;
    //
    private boolean isClosingWidOk = false;
    
    public final static String SUBJECT_INFO_NAME = "documentSubject";
    
    public final static String INTEM_INFO_NAME = "documentItem";
    
    /**
     * output class constructor
     */
    public SubjectViewUI3() throws Exception {
		super();
	}


    public void loadFields() {
		this.comSubjectNumberShow.setSelected(documentSubject!=null && documentSubject.getIsShowNumber()==1);
        dataBinder.loadFields();
		subjectDataBinder.loadFields();
	}

	public void onLoad() throws Exception {
        registerItemTable();
        registerSubjectValidator();
		super.onLoad();
		this.toolBar.setVisible(false);
		this.txtSubjectTopic.setRequired(true);
	}
	
	public void registerSubjectValidator() {
		ValidateHelper vh = getSubjectValidateHelper();
		vh.setCustomValidator(getValidator());
		vh.registerBindProperty("items", ValidateHelper.ON_SAVE);		
		vh.registerBindProperty("items.optionNumber", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.topic", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.xFontSize", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("items.xFontName", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("topic", ValidateHelper.ON_SAVE);
		vh.registerBindProperty("xFontSize", ValidateHelper.ON_SAVE);
	}
	
	private void whenSubjectItemRowChanged(int currRow, int currColu,int prevRow, int prevColu) {
		if (currRow >= 0 && (prevRow != prevColu || currRow != prevRow)) {
			// 保存数据先
			storeFields();
			//subjectDataBanderStoreFields();
			// 换行操作
			DocumentItemInfo currItem = documentSubject.getItems().get(currRow);
			// 只需设置 OPTION 的表格
			setDataObject(currItem);
			// 只需LOAD OPTION 的表格
			dataBinder.loadFields();
		}
	}
	
	private void whenSubjectItemCellEdited(int currRow,int currColu){
		whenSubjectItemRowChanged(currRow,currColu,-1,-1);
	}
	
	/**
	 * 注册一些监听
	 */
	public void registerItemTable(){
		this.kdtItems.addKDTSelectListener(new SubjectSelectListener());
		kdtItems.addKDTEditListener(new SubjectEditListener());
		subjectDataBinder.registerBinding("items", DocumentItemInfo.class, this.kdtItems, "userObject");
		subjectDataBinder.registerBinding("items.optionNumber", String.class, this.kdtItems, "topic.optionNumber");
		subjectDataBinder.registerBinding("items.topic", String.class, this.kdtItems, "topic.text");
		subjectDataBinder.registerBinding("items.xFontSize", int.class, this.kdtItems, "xFontSize.text");
		subjectDataBinder.registerBinding("items.xFontName", String.class, this.kdtItems, "xFontName.text");
		subjectDataBinder.registerBinding("topic", String.class, this.txtSubjectTopic, "text");
		subjectDataBinder.registerBinding("xFontSize", int.class, this.subjectFontSize, "value");
		subjectDataBinder.registerBinding("subjectType", String.class, this.comSubjectType, "selectedItem");
		subjectDataBinder.registerBinding("alignType", int.class, this.comOptonAlignType, "selectedItem");
		subjectDataBinder.registerBinding("horizontalAlign", int.class, this.comHorizonType, "selectedItem");
		subjectDataBinder.registerBinding("showNumber", int.class, this.txtSubjectNumber, "value");
		kdtItems.putBindContents("editData",new String[] {"topic","xFontSize","xFontName"});
		kdtItems.checkParsed();
	}
	
	
	public final ValidateHelper getSubjectValidateHelper() {
        if( subjectHelper == null ) {
        	subjectHelper = new ValidateHelper( documentSubject , subjectDataBinder ) ;
        }
        //subjectHelper.setDataObject( documentSubject ) ;
        return this.subjectHelper ;
    }
	
	public boolean isModify() {
		return false;
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		subjectDataBanderStoreFields();
		dataBinder.storeFields();
	}
    
    public void subjectDataBanderStoreFields(){
    	subjectDataBinder.storeFields();
    	if(documentSubject!=null){
    		documentSubject.setIsShowNumber(this.comSubjectNumberShow.isSelected()?1:0);
    	}
    }
    
	public void setDataObject(String key, IObjectValue dataObject) {
		if(SUBJECT_INFO_NAME.equals(key) && dataObject!=null){
			subjectDataBinder.setValueObject(dataObject);
		}else if(INTEM_INFO_NAME.equals(key)){
			setDataObject(dataObject);
		}
		super.setDataObject(key, dataObject);
	}

	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
	}

	/**
	 * 新增一个组
	 */
    protected IObjectValue createNewData() {
    	DocumentItemInfo dItem = null;
    	if(documentSubject == null||documentSubject.getItems().size() == 0){
    		//如果当前的题目为空，那么就新增一个题目
    		if(documentSubject == null){
    			documentSubject = new DocumentSubjectInfo();
    			documentSubject.setTopic("新增的题目");
    		}
    		//新增一个分组
    		dItem = new DocumentItemInfo();
        	dItem.setTopic("");
    		documentSubject.getItems().add(dItem);
    		//注册
    		subjectDataBinder.setValueObject(documentSubject);
    	}else{
    		dItem = documentSubject.getItems().get(0);
    	}
		return dItem;
	}
    
    /**
     * 新增一个选项
     */
	protected IObjectValue createNewDetailData(KDTable table) {
		IObjectValue ov = null;
		if(table == kdtOptions){
			DocumentOptionInfo option = new DocumentOptionInfo();
			option = new DocumentOptionInfo();
			option.setXFontName("Dialog");
			option.setXFontSize(12);
			option.setXLength(0);
			option.setIsTopicInverse(false);
			ov = option;
		}else if(table == kdtItems){
			DocumentItemInfo item = new DocumentItemInfo();
			item.setTopic("");
			item.setXFontSize(12);
			item.setXFontName("Dialog");
			ov = item;
		}
		return ov;
	}

	/**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	
    	if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(comSubjectType.getSelectedItem()) == 0){
    		//如果是描述题，将题目标题设置为描述相同
    		this.txtSubjectTopic.setText(this.descriptionText.getText());
    	}
    	storeFields();
    	//需要例行检查，数据的是否完整。
    	//标题不能为空等
		StringBuffer message = new StringBuffer();
		if(!verifyDocumentSubject(documentSubject,message)){
			MsgBox.showWarning(message.toString());
		}else{
			trimDocumentSubject(documentSubject);
			isClosingWidOk = true;
			this.getUIWindow().close();
    	}
		logger.debug(documentSubject.getTopic());
        logger.debug(documentSubject.getSubjectType());
        logger.debug(String.valueOf(documentSubject.getXFontSize()));
        logger.debug(documentSubject.getXFontName());
        logger.debug(documentSubject.getAlignType());
        logger.debug(documentSubject.getHorizontalAlign());
    	for(int i=0 ;i<documentSubject.getItems().size() ;i ++){
    		DocumentItemInfo ic = documentSubject.getItems().get(i);
    		logger.debug(" " + ic.getTopic());
    		for(int j=0 ; j<ic.getOptions().size() ;j++){
    			logger.debug("   " + ic.getOptions().get(j).getTopic());
    		}
    	}
        
    }
    
    private boolean verifyDocumentSubject(DocumentSubjectInfo documentSubject,StringBuffer feekback){
    	boolean ok = true;
    	if(!(ok = !isStringNullOrBlank(documentSubject.getTopic()))){
    		feekback.append("题目标题为必须填写的信息。");
    	}else if(!(ok = isNumberBetween(8,80,documentSubject.getXFontSize()))){
    		feekback.append("字体超出范围：8~80。");
    	}else{
    		DocumentItemCollection ic = documentSubject.getItems();
    		int icCount = ic.size();
    		for(int i=0 ; ok && i<icCount ; i++){
    			ok = verifyDocumentItem(ic.get(i),feekback,icCount,documentSubject.getSubjectType());
    		}
    	}
    	return ok;
    }
    
    private boolean verifyDocumentItem(DocumentItemInfo item,StringBuffer feedback,int allItemsCount,DocumentSubjectTypeEnum  subjectType){
    	boolean ok = true;
    	if(isStringNullOrBlank(item.getTopic())){
    		if(!(ok = allItemsCount==1)){
    			feedback.append("存在多个分组，其各个分组标题是必填的信息。");
    		}
    	}else if(!(ok = isNumberBetween(8,80,item.getXFontSize()))){
			feedback.append("分组中存在字体超出范围：8~80。");
		}
    	if(ok){
			DocumentOptionCollection oc = item.getOptions();
			int optionTopicIsNotNullCount = 0;
			for(int i=0 ; ok && i<oc.size() ; i++){
				DocumentOptionInfo oi = oc.get(i);
				ok = verifyDocumentOption(oi,feedback);
				if(!isStringNullOrBlank(oi.getTopic())){
					optionTopicIsNotNullCount++;
				}
			}
			if(ok){
				if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(subjectType) == 0 || DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL.compareTo(subjectType) == 0 ){
					//对填充题、描述文字不做选项数量上的校验
				}else if(!( ok = (optionTopicIsNotNullCount>1))){
					feedback.append("对于选择题，有效选项的数量必须大于1。");
    				}
    			}
    	}
    	return ok;
    }
    
    private boolean verifyDocumentOption(DocumentOptionInfo option,StringBuffer feedback){
    	boolean ok = true;
    	if(!(ok = (isStringNullOrBlank(option.getTopic()) || isNumberBetween(8,80,option.getXFontSize())))){
    		feedback.append("选项中存在字体超出范围：8~80。");
    	}
    	return ok;
    }
    
    private void trimDocumentSubject(DocumentSubjectInfo documentSubject){
    	if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(documentSubject.getSubjectType()) == 0){
    		documentSubject.getItems().clear();
    	}else{
    		DocumentItemCollection ic = documentSubject.getItems();
    		for(int i=0 ; i<ic.size() ; i++){
    			DocumentItemInfo ii = ic.get(i);
    			ii.setItemNumber(i);
    			DocumentOptionCollection oc = ii.getOptions();
    			for(int j=0 ; j<oc.size() ; j++){
    				DocumentOptionInfo optio = oc.get(j);
    				if(isStringNullOrBlank(optio.getTopic()) && (oc.size()>1 || ic.size()>1)){
    					oc.removeObject(j);
    					j--;
    				}else{
    					optio.setOptionNumber(j);
    				}
    			}
    			if(oc.size() == 0){
    				ic.removeObject(i);
    				i--;
    			}
    			
    		}
    	}
    }
    
    
    
    
    private boolean isStringNullOrBlank(String str){
    	return str == null || "".equals(str.trim());
    }
    
    private boolean isNumberBetween(int min,int max,int num){
    	return min<=num && max>=num;
    }
    

    protected void txtSubjectTopic_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.txtSubjectTopic_actionPerformed(e);
	}

	protected KDTable getDetailTable() {
		return this.kdtOptions;
	}
    
    
    

	protected ICoreBase getBizInterface() throws Exception {
		return DocumentItemFactory.getRemoteInstance();
	}
	

	protected void addLine_actionPerformed(ActionEvent e) throws Exception {
		addLine(kdtOptions);
        storeFields();        
        this.kdtOptions.getRow(kdtOptions.getRowCount()-1).getCell("optionNumber").setValue((kdtOptions.getRowCount()-1)+"");
	}

	protected void btnNO_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

	protected void deleLine_actionPerformed(ActionEvent e) throws Exception {
		this.removeLine(kdtOptions);
	}
	
	/**
	 * 
	 * 描述：增加一个新的分组
	 */
	protected void btnAddItem_actionPerformed(ActionEvent e) throws Exception {
		IObjectValue detailData = createNewDetailData(kdtItems);
        IRow row = kdtItems.addRow();
        //将焦点放在表kdtItems的最后一行的第已个单元格，可以触发事件对上一个分组进行保存
        this.kdtItems.getSelectManager().select(row.getRowIndex(),0);
        subjectDataBinder.loadLineFields(kdtItems, row, detailData);
        //
        //kdtItems.
        //
        setDataObject(detailData);
        dataBinder.loadFields();
        //
        storeFields();
	}

	/**
	 * 删除一个分组
	 */
	protected void btnDeleItem_actionPerformed(ActionEvent e) throws Exception {
		//尚未完成
		if(kdtItems.getRowCount() > 1){
			removeLine(kdtItems);
		}else{
			MsgBox.showWarning("不能删除，至少要保留一个分组！");
		}
		
		
		
	}

	boolean hasDescription = false;//是否曾经选择过描述
	DocumentSubjectTypeEnum prevSubjectEnum = null;
	/**
	 * 选择题型之后
	 */
	protected void comSubjectType_actionPerformed(ActionEvent e) throws Exception {
		DocumentSubjectTypeEnum currSubjectEnum = (DocumentSubjectTypeEnum)comSubjectType.getSelectedItem();
		if(prevSubjectEnum == null || currSubjectEnum.compareTo(prevSubjectEnum) != 0){
			prevSubjectEnum = currSubjectEnum;
			if(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION.compareTo(currSubjectEnum) == 0){
				if(!hasDescription){
					descriptionText.setText(txtSubjectTopic.getText());
				}
				hasDescription = true;
				//隐藏其他控件，显示描述及水平
				hideAndShowSomeing(false);
				this.documentSubject.setIsShowNumber(0);
				kDSeparator7.setVisible(false);
			}else{
				//显示其他控件，隐藏描述及水平
				hideAndShowSomeing(true);
				this.comHorizonType.setSelectedItem(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
				this.documentSubject.setIsShowNumber(1);
				kDSeparator7.setVisible(true);
			}
			
		}
	}
	/**
	 * 
	 */
	private void hideAndShowSomeing(boolean hideDescripAndHorizon){
		//其他
		boolean self = !hideDescripAndHorizon;
		kdtOptions.setVisible(hideDescripAndHorizon);
		kdtItems.setVisible(hideDescripAndHorizon);
		addLine.setVisible(hideDescripAndHorizon);
		deleLine.setVisible(hideDescripAndHorizon);
		kDLabel8.setVisible(hideDescripAndHorizon);
		//txtTopic.setVisible(hideDescripAndHorizon);
		//txtXFontSize.setVisible(hideDescripAndHorizon);
		//txtSubjectTopic.setVisible(hideDescripAndHorizon);
		//subjectFontSize.setVisible(hideDescripAndHorizon);
		//kDSeparator6.setVisible(hideDescripAndHorizon);
		btnAddItem.setVisible(hideDescripAndHorizon);
		btnDeleItem.setVisible(hideDescripAndHorizon);
		//comOptonAlignType.setVisible(hideDescripAndHorizon);
		//描述及水平
//		kDLabel6.setVisible(self);
		comHorizonType.setVisible(self);
		descriptionText.setVisible(self);
	}

	

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		// super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		// super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		// super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		// super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		// super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		// super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception {
		// super.actionMsgFormat_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		// super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		// super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		// super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		// super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	protected boolean isClosingWidOk() {
		return isClosingWidOk;
	}

	protected void setClosingWidOk(boolean isClosingWidOk) {
		this.isClosingWidOk = isClosingWidOk;
	}

	protected DataBinder getSubjectDataBinder() {
		return subjectDataBinder;
	}

	protected void setSubjectDataBinder(DataBinder subjectDataBinder) {
		this.subjectDataBinder = subjectDataBinder;
	}
    
    
    
    
    
    
    /////////// 监听类
    
    class SubjectSelectListener implements  KDTSelectListener{
		//换行监听
		public void tableSelectChanged(KDTSelectEvent e) {
			
			int currRow = -1;
			int currColu = -1;
			if(e.getSelectBlock()!=null){
				currRow = e.getSelectBlock().getTop();
				currColu = e.getSelectBlock().getLeft();
			}
			int prevRow = -1;
			int prevColu = -1;
			if(e.getPrevSelectBlock()!=null){
				prevRow = e.getPrevSelectBlock().getTop();
				prevColu = e.getPrevSelectBlock().getLeft();
			}
			SubjectViewUI3.this.whenSubjectItemRowChanged(currRow,currColu,prevRow,prevColu);
		}
	}
    
    class SubjectEditListener implements KDTEditListener{

		public void editStarting(KDTEditEvent e) {
			// TODO 自动生成方法存根
			
		}

		public void editStarted(KDTEditEvent e) {
			// TODO 自动生成方法存根
			
		}

		public void editValueChanged(KDTEditEvent e) {
			
		}

		public void editStopping(KDTEditEvent e) {
			// TODO 自动生成方法存根
		}

		public void editStopped(KDTEditEvent e) {
			SubjectViewUI3.this.whenSubjectItemCellEdited(e.getRowIndex(),e.getColIndex());
		}

		public void editCanceled(KDTEditEvent e) {
			// TODO 自动生成方法存根
		}}


    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   
}