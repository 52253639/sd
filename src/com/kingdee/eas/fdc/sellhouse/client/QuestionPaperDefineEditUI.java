/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterResolution;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectSingleKey;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.uiframe.client.UINewFrame;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocumentFactory;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.IDocument;
import com.kingdee.eas.fdc.market.QuestionBaseInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineFactory;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.QuestionStyle;
import com.kingdee.eas.fdc.market.QuestionTypeInfo;
import com.kingdee.eas.fdc.market.SelectQuestionInfo;
import com.kingdee.eas.fdc.market.SelectQuestionItemCollection;
import com.kingdee.eas.fdc.market.SelectQuestionItemInfo;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;
import com.kingdee.eas.fdc.market.client.DocumentPaperPropertiyUI;
import com.kingdee.eas.fdc.market.client.QuestionSelectPromptBox;
import com.kingdee.eas.fdc.market.client.StoreSubjectListUI;
import com.kingdee.eas.fdc.market.client.XDocument;
import com.kingdee.eas.fdc.market.client.XPrintPreviewDialog;
import com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuestionPaperDefineEditUI extends AbstractQuestionPaperDefineEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestionPaperDefineEditUI.class);
    
	XDocument xDocument;
	JScrollPane xScrollPane;
	QuestionSelectPromptBox f7;
	private int questItems = 0;

	// DocumentInfo documentInfo;

	/**
	 * output class constructor
	 */
	public QuestionPaperDefineEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
		QuestionPaperDefineInfo d = (QuestionPaperDefineInfo) getDataBinder().getValueObject();
		if (d != null) {
			try {
				changeDocumentId(d.getDocumentId());
			} catch (Exception e) {
				logger.error(e);
			}
		}

		
		if(this.editData.getId()!=null) 
			this.actionJumpSet.setVisible(true);
		else	
			this.actionJumpSet.setVisible(false);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 打印问卷
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {		
		DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		AttributeSet attributeSet = new HashAttributeSet();
		attributeSet.add(OrientationRequested.LANDSCAPE);
		// 获取所有打印服务
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(docFlavor, attributeSet);
		if(printServices==null || printServices.length==0) {
			FDCMsgBox.showWarning("找不到打印服务，请检查！");
			return;
		}
		
		// 获取默认打印服务
		PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		// printRequestAttributeSet.add(new PrinterResolution(72, 72,
		// PrinterResolution.DPI));
		printRequestAttributeSet.add(xDocument.getMediaPrintableArea());
		printRequestAttributeSet.toArray();
		// 显示打印对话框
		PrintService selection = ServiceUI.printDialog(null, 200, 200, printServices, defaultPrintService, docFlavor, printRequestAttributeSet);
		if (selection == null) {
			// 取消打印
			return;
		}
		PrinterResolution printerResolution = (PrinterResolution) selection.getDefaultAttributeValue(PrinterResolution.class);
		printerResolution.getFeedResolution(PrinterResolution.DPI);
		printRequestAttributeSet.add(printerResolution);
		DocAttributeSet das = new HashDocAttributeSet();
		Doc doc = new SimpleDoc(xDocument, docFlavor, das);
		doc.getAttributes();

		// if(true) return;

		DocPrintJob job = selection.createPrintJob();

		job.getAttributes();
		try {
			job.print(doc, printRequestAttributeSet);
		} catch (PrintException pe) {
			logger.error(pe);
			MsgBox.showError("打印过程出错.");
		}
		
	}

	/**
	 * 预览问卷
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		XPrintPreviewDialog xpd = new XPrintPreviewDialog(getFrame(this), "", true, xDocument);
		xpd.setVisible(true);
		xpd.setVisible(false);
		xpd.dispose();
	}

	/**
	 * 
	 */
	protected void answerResult_actionPerformed(ActionEvent e) throws Exception {
		if (xDocument.getDoc().getId() == null) {
			MsgBox.showWarning("新增问卷无法分析");
			return;
		}
		DocumentSubjectInfo selectedInfo = xDocument.getSelectedSubjectInfo();
		if (selectedInfo != null) {
			// 分析被选题目
		} else {
			// 分析整个问卷
		}

	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		QuestionTypeInfo qtTypeInfo = (QuestionTypeInfo) this.prmtpaperType.getValue();
		if (qtTypeInfo == null) {
			MsgBox.showInfo("问卷类别不能为空！");
			return;
		}
		String number = this.txtNumber.getText().trim();
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		IObjectValue objValue = this.editData;
		if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
			if (number == null || "".equals(number)) {
				MsgBox.showInfo("单据编号不能为空！");
				return;
			}
		}

		Date bizDate = (Date) this.pkBizDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("业务日期不能为空！");
			return;
		}
		String topic = this.txttopric.getText().trim();
		if (topic == null || "".equals(topic)) {
			MsgBox.showInfo("问卷名称不能为空！");
			return;
		}
		
		if(this.combBizScene.getSelectedItem()==null){
			MsgBox.showInfo("业务场景不能为空！");
			return;			
		}
		
		
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (QuestionPaperDefineFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("单据编号不能重复！");
				SysUtil.abort();
			}
		}
		IDocument iDocument = DocumentFactory.getRemoteInstance();
		if (xDocument.getDoc().getId() != null) {
			iDocument.update(new ObjectSingleKey("id", xDocument.getDoc().getId().toString()), xDocument.getDoc());
		} else {
			IObjectPK ipk = iDocument.addnew(xDocument.getDoc());
			if (ipk != null) {
				this.txtdocumentId.setText(ipk.toString());
			} else {
				MsgBox.showInfo("保存出错！");
				return;
			}
		}

		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		QuestionTypeInfo qtTypeInfo = (QuestionTypeInfo) this.prmtpaperType.getValue();
		if (qtTypeInfo == null) {
			MsgBox.showInfo("问卷类别不能为空！");
			return;
		}
		String number = this.txtNumber.getText().trim();
		if (number == null || "".equals(number)) {
			MsgBox.showInfo("单据编号不能为空！");
			return;
		}

		Date bizDate = (Date) this.pkBizDate.getValue();
		if (bizDate == null) {
			MsgBox.showInfo("业务日期不能为空！");
			return;
		}
		String topic = this.txttopric.getText().trim();
		if (topic == null || "".equals(topic)) {
			MsgBox.showInfo("问卷名称不能为空！");
			return;
		}

		if(this.combBizScene.getSelectedItem()==null){
			MsgBox.showInfo("业务场景不能为空！");
			return;			
		}		
		
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if (QuestionPaperDefineFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("单据编号不能重复！");
				SysUtil.abort();
			}
		}
		IDocument iDocument = DocumentFactory.getRemoteInstance();
		if (xDocument.getDoc().getId() != null) {
			iDocument.update(new ObjectSingleKey("id", xDocument.getDoc().getId().toString()), xDocument.getDoc());
		} else {
			IObjectPK ipk = iDocument.addnew(xDocument.getDoc());
			if (ipk != null) {
				this.txtdocumentId.setText(ipk.toString());
			} else {
				MsgBox.showInfo("保存出错！");
				return;
			}
		}

		this.chkMenuItemSubmitAndPrint.setSelected(false);
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.QuestionPaperDefineFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.market.QuestionPaperDefineEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.QuestionPaperDefineInfo objectValue = new com.kingdee.eas.fdc.market.QuestionPaperDefineInfo();
		objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo) (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setOrgUnit((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		xDocument.setEmptyDoc();
		return objectValue;
	}

	public void onLoad() throws Exception {
		xDocument = new XDocument();
		xScrollPane = new JScrollPane(xDocument);
		btnWorkFlowG.setVisible(false);
		btnCreateFrom.setVisible(false);
		btnSignature.setVisible(false);
		btnViewSignature.setVisible(false);
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		separatorFW6.setVisible(false);
		separatorFW9.setVisible(false);
		btnVoucher.setVisible(false);
		btnDelVoucher.setVisible(false);
		btnCopyFrom.setVisible(false);
		btnCreateTo.setVisible(false);
		btnCopy.setVisible(false);
		btnAuditResult.setVisible(false);

		btnWFViewdoProccess.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);

		kdtEntrys.setVisible(false);

		paperPanel.setLayout(new BorderLayout());
		paperPanel.add(xScrollPane, BorderLayout.CENTER);

		super.onLoad();
		this.actionCreateTo.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.prmtpaperType.setRequired(true);
		this.txtNumber.setRequired(true);
		this.pkBizDate.setRequired(true);
		this.txttopric.setRequired(true);
		this.txttopric.setMaxLength(80);
		this.actionSubmitOption.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);

		if (this.oprtState.equals(OprtState.ADDNEW)) {
			questItems = 0;
			
			//从向导传递过来的参数
			QuestPaperBizSceneEnum questBizSceneEnum = (QuestPaperBizSceneEnum)this.getUIContext().get("QuestionBizScene");
			if(questBizSceneEnum!=null) this.combBizScene.setSelectedItem(questBizSceneEnum);
			
			QuestionPaperDefineInfo questPaperDefineInfo = (QuestionPaperDefineInfo)this.getUIContext().get("QuestionPaperDefineInfo");
			DocumentItemCollection docItemColl = (DocumentItemCollection)this.getUIContext().get("DocumentItemCollection");
			if(questPaperDefineInfo!=null) {
				addToTheEmptyPaper(questPaperDefineInfo);
			}else if(docItemColl!=null){
				addToTheEmptyPaper(docItemColl);
			}
		} 
		
		answerResult.setVisible(false);
		handleCodingRule();
		this.bizPromptLastUpdateUser.setEnabled(false);
		this.dateLastUpdateTime.setEnabled(false);
		this.bizPromptLastUpdateUser.setValue(SysContext.getSysContext().getCurrentUserInfo());
		this.dateLastUpdateTime.setValue(new Timestamp(System.currentTimeMillis()));
		
		this.kDLabelContainer2.setVisible(false);
		this.combBizScene.setRequired(true);
		this.actionSave.setVisible(false);
		
		this.actionJumpSet.setVisible(true);
		this.actionJumpSet.setEnabled(true);
		this.actionSimulateAnswer.setEnabled(true);			
		//给”项目“ 加上”组织“的过滤条件  xiaominWang
		EntityViewInfo evInfo=new EntityViewInfo();
        FilterInfo filter=new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().getId()));
        evInfo.setFilter(filter);
		prmtSellProject.setEntityViewInfo(evInfo);
		
		//src start
		this.addNewSubject.setVisible(false);//隐藏增加新题目
		if(this.oprtState.equals(OprtState.ADDNEW)||this.oprtState.equals(OprtState.EDIT)){
			comVisable(true);
		}else{
			comVisable(false);
		}
		//src end
	}
	
	/** 
	 * 按钮显示与否
	 */
	protected void comVisable(boolean flag){
		this.deleteSubject.setEnabled(flag);
		this.updateSubject.setEnabled(flag);
		this.selectSubject.setEnabled(flag);
		this.paperProperty.setEnabled(flag);
		this.btnJumpSet.setEnabled(flag);
	}

	private void addToTheEmptyPaper(QuestionPaperDefineInfo paperDefineInfo){
		this.txttopric.setText(paperDefineInfo.getTopric());
		this.pkBizDate.setValue(paperDefineInfo.getBizDate());
		this.prmtpaperType.setValue(paperDefineInfo.getPaperType());
		this.combBizScene.setSelectedItem(paperDefineInfo.getBizScene());
		this.txtDescription.setText(paperDefineInfo.getDescription());

		try {
			DocumentInfo info = DocumentFactory.getRemoteInstance().getDocumentInfo(" where id = '" + 
										paperDefineInfo.getDocumentId() + "' order by subjects.subjectNumber");
			if (info != null) {
				sortDocumentElements(info);
				DocumentInfo cloneInfo = (DocumentInfo)info.clone();
				//TODO 这里应该要去掉id
				cloneInfo.setId(null);
				DocumentSubjectCollection subColl = cloneInfo.getSubjects();
				for(int i=0;i<subColl.size();i++){
					DocumentSubjectInfo subInfo = subColl.get(i);
					subInfo.setDocumentId(cloneInfo);
					subInfo.setId(null);
					DocumentItemCollection itemColl = subInfo.getItems();
					for (int j = 0; j < itemColl.size(); j++) {
						DocumentItemInfo itemInfo = itemColl.get(j);
						itemInfo.setSubjectId(subInfo);
						itemInfo.setId(null);
						itemInfo.setIsToJump(false);
						itemInfo.setJumpCont(null);
						itemInfo.setChooseOption(null);
						itemInfo.setJumpToItem(null);
						DocumentOptionCollection  optionColl = itemInfo.getOptions();
						for (int k = 0; k < optionColl.size(); k++) {
							DocumentOptionInfo optionInfo = optionColl.get(k);
							optionInfo.setItemId(itemInfo);
							optionInfo.setId(null);
						}
					}
				}
				
				xDocument.setDoc(cloneInfo);
			} 
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}	
	private void addToTheEmptyPaper(DocumentItemCollection itemColl){		
			DocumentSubjectInfo dThree = new DocumentSubjectInfo();
			dThree.setXFontName("Dialog");
			dThree.setTopic("");
			dThree.setXFontSize(12);
			dThree.setColumnCount(1);
			dThree.setSubjectNumber(0);// 对于新增数据，这里必须设置数据小于1
			dThree.setShowNumber(1);
			dThree.setIsShowNumber(1);
			dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);// ALIGN_TYPE_FLOW
			dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
			dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
			DocumentItemInfo dItem = new DocumentItemInfo();
			dItem.setXFontSize(12);
			dItem.setXFontName("Dialog");
			dThree.getItems().add(dItem);
			dThree.copyNewToOld();// 保存一份,用来与新值进行比较
			
			for (int j = 0; j < itemColl.size(); j++) {
				DocumentItemInfo itemInfo = itemColl.get(j);
				if(j==0) {
					if(itemInfo.getSubjectId()!=null)
						dThree.setTopic(itemInfo.getSubjectId().getTopic());
				}
				
				itemInfo.setSubjectId(dThree);
				itemInfo.setId(null);
				DocumentOptionCollection  optionColl = itemInfo.getOptions();
				for (int k = 0; k < optionColl.size(); k++) {
					DocumentOptionInfo optionInfo = optionColl.get(k);
					optionInfo.setItemId(itemInfo);
					optionInfo.setId(null);
				}
			}
			
			dThree.getItems().addCollection(itemColl);
			
			questItems++;
			dThree.setShowNumber(questItems);
			xDocument.insertDSubject(dThree);
	}
	
	
	public void action_DeleteSubject_actionPerformed(ActionEvent e) throws Exception {

		DocumentSubjectInfo dThree = xDocument.getSelectedSubjectInfo();
		if (dThree == null) {
			MsgBox.showWarning("请先选择题目，然后再进行删除.");
			return;
		}
		xDocument.deleteDSubject(dThree);
		questItems--;
		xDocument.reBuild();
	}

	public com.kingdee.eas.base.uiframe.client.UINewFrame getFrame(Container c) {
		UINewFrame uf = null;
		while (!(c instanceof JFrame) && c.getParent() != null) {
			c = c.getParent();
		}
		if (c instanceof UINewFrame) {
			uf = (UINewFrame) c;
		}
		return uf;
	}

	public void action_NewSubject_actionPerformed(ActionEvent e) throws Exception {

		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
		dThree.setXFontName("Dialog");
		dThree.setTopic("");
		dThree.setXFontSize(12);
		dThree.setColumnCount(1);
		dThree.setSubjectNumber(0);// 对于新增数据，这里必须设置数据小于1
		dThree.setShowNumber(1);
		dThree.setIsShowNumber(1);
		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);// ALIGN_TYPE_FLOW
		dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		DocumentItemInfo dItem = new DocumentItemInfo();
		dItem.setXFontSize(12);
		dItem.setXFontName("Dialog");
		dThree.getItems().add(dItem);
		dThree.copyNewToOld();// 保存一份,用来与新值进行比较

		Map myContext = new HashMap();
		myContext.put(SubjectViewSheUI.SUBJECT_INFO_NAME, dThree);
		myContext.put(SubjectViewSheUI.INTEM_INFO_NAME, dThree.getItems().get(0));

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);// "8bCpR/BZR76BNqY/Rc6wMhel7x8="
		uiContext.put("txtSubjectNumber", new Integer(xDocument.getDoc().getSubjects().size()));		
		

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(SubjectViewSheUI.class.getName(), uiContext, myContext, OprtState.ADDNEW);
		uiWindow.show();//
		if (dThree.isValueChange() && ((SubjectViewSheUI) uiWindow.getUIObject()).isClosingWidOk()) {
			questItems++;
			dThree.setShowNumber(questItems);
			xDocument.insertDSubject(dThree);

		}

	}

	public void action_SelectSubject_actionPerformed(ActionEvent e) throws Exception {

		DocumentSubjectCollection dcol=new DocumentSubjectCollection();
//		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
//		dThree.setXFontName("Dialog");
//		dThree.setTopic("");
//		dThree.setXFontSize(12);
//		dThree.setColumnCount(1);
//		dThree.setSubjectNumber(0);// 对于新增数据，这里必须设置数据小于1
//		dThree.setShowNumber(1);
//		dThree.setIsShowNumber(1);
//		//src start 按题库排列方式 
////		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);// ALIGN_TYPE_FLOW
//		//src end
//		dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
////		DocumentItemInfo dItem = new DocumentItemInfo();
////		dThree.getItems().add(dItem);
//		dThree.copyNewToOld();// 保存一份,用来与新值进行比较

		Map myContext = new HashMap();
//		myContext.put(StoreSubjectListUI.can_ToQuestion_Paper, dcol);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);// 
		QuestionTypeInfo questionType = (QuestionTypeInfo) this.prmtpaperType.getValue();
		uiContext.put("questionType", questionType);
		uiContext.put("FromQuestionDefine", "True");	//冲问卷定义这边打开时，窗口模式要做改动
		uiContext.put("xDocument", xDocument);
		uiContext.put(StoreSubjectListUI.can_ToQuestion_Paper, dcol);
		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(StoreSubjectListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();//
		for(int k=0;k<dcol.size();k++){
			DocumentSubjectInfo dThree=dcol.get(k);
			if (dThree.isValueChange()) {
				questItems++;
				dThree.setShowNumber(questItems);
				//src start
//				DocumentSubjectCollection dsCollection = xDocument.getDoc().getSubjects();
//				for(int i = 0;i<dsCollection.size();i++){
//					DocumentSubjectInfo info = dsCollection.get(i);
//					if(dThree.getTopic().equals(info.getTopic())&&dThree.getSubjectType().equals(info.getSubjectType())){
//						MsgBox.showInfo("所选题目重复，请重新从题库选题！");
//						SysUtil.abort();
//					}
//				}
				xDocument.insertDSubject(dThree);
				//src end
			}
//			if (true)
//				return;
//
//			// 弹出选择兑换框
//			if (f7 == null) {
//				f7 = new QuestionSelectPromptBox();
//			}
//			f7.show();
//			Object[] obj = (Object[]) f7.getData();// obj为返回的对象集合
//
//			DocumentSubjectInfo[] infos = parseOriginalQuestion(obj);
//			for (int i = 0; infos != null && i < infos.length; i++) {
//				infos[i].setSubjectNumber(xDocument.getDoc().getMaxSubjectSerialNumber() + 1);
//				xDocument.insertDSubject(infos[i]);
//			}
		}
	}

	public void action_UpdateSubject_actionPerformed(ActionEvent e) throws Exception {
		DocumentSubjectInfo dThree = xDocument.getSelectedSubjectInfo();
		if (dThree == null) {
			MsgBox.showWarning("请先选择题目，然后再进行变更.");
			return;
		}
		dThree.copyNewToOld();// 变更前，先保存一份
		Map myContext = new HashMap();
		myContext.put(SubjectViewSheUI.SUBJECT_INFO_NAME, dThree);
		myContext.put(SubjectViewSheUI.INTEM_INFO_NAME, null);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);// "8bCpR/BZR76BNqY/Rc6wMhel7x8="

		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(SubjectViewSheUI.class.getName(), uiContext, myContext, OprtState.ADDNEW);
		uiWindow.show();
		if (((SubjectViewSheUI) uiWindow.getUIObject()).isClosingWidOk()) {
			if (dThree.isValueChange()) {
				xDocument.updateDSubject(dThree);
			}
		} else {
			dThree.resetValue();
		}
	}

	/**
	 * 变更页面属性
	 */
	protected void paperProperty_actionPerformed(ActionEvent e) throws Exception {
		//
		DocumentInfo docInfo = xDocument.getDoc();
		docInfo.copyNewToOld();
		Map dataObject = new HashMap();
		dataObject.put("docInfo", docInfo);
		// 显示窗口
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(DocumentPaperPropertiyUI.class.getName(), new UIContext(this), dataObject, null);
		uiWindow.show();
		if (docInfo.isValueChange()) {
			// 如果属性变化了，那么需要重新生成 xDocument
			xDocument.setDoc(docInfo);
			// 否则不做任何操作
		}
	}

	public DocumentSubjectInfo[] parseOriginalQuestion(Object[] objs) throws Exception {
		DocumentSubjectInfo[] dSubject = null;
		if (objs != null) {
			dSubject = new DocumentSubjectInfo[objs.length];
			for (int i = 0; i < objs.length; i++) {
				dSubject[i] = parseOriginalQuestion(objs[i]);
			}
		}

		return dSubject;
	}

	int maxSubjectNum = 1;

	public DocumentSubjectInfo parseOriginalQuestion(Object obj) throws Exception {
		DocumentSubjectInfo info = new DocumentSubjectInfo();
		QuestionBaseInfo qbi = (QuestionBaseInfo) obj;
		String style = qbi.getQuestionStyle().getValue();
		if (QuestionStyle.ASKQUESTION_VALUE == style || QuestionStyle.FILLBLANKQUESTION_VALUE == style) {
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
			info.setTopic(qbi.getName());
			info.setXFontName("Dialog");
			info.setXFontSize(14);
			info.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);
			info.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			info.setColumnCount(1);
			info.setShowNumber(maxSubjectNum++);
			info.setIsShowNumber(1);
			info.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		} else if (QuestionStyle.SINGLESELECTQUESTION_VALUE == style || QuestionStyle.JUDGEQUESTION_VALUE == style) {
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
			info.setTopic(qbi.getName());
			info.setXFontName("Dialog");
			info.setXFontSize(14);
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
			info.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);
			info.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			info.setColumnCount(1);
			info.setShowNumber(maxSubjectNum++);
			info.setIsShowNumber(1);
			info.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");//TODO 如何实现？区别是题目类型
			SelectQuestionInfo si = (SelectQuestionInfo) com.kingdee.eas.fdc.market.QuestionHelp.getQuestion(qbi.getId().toString(), qbi.getQuestionStyle().getValue());
			SelectQuestionItemCollection item = si.getItem();
			if (item != null && item.size() > 0) {
				DocumentItemInfo itemInfo = new DocumentItemInfo();
				info.getItems().add(itemInfo);
				for (int i = 0; i < item.size(); i++) {
					SelectQuestionItemInfo sItemInfo = item.get(i);
					DocumentOptionInfo optionInfo = new DocumentOptionInfo();
					optionInfo.setTopic(sItemInfo.getName());
					optionInfo.setXFontName("Dialog");
					optionInfo.setXFontSize(14);
					itemInfo.getOptions().add(optionInfo);
				}
			}

		} else if (QuestionStyle.MUTISELECTQUESTION_VALUE == style) {
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
			info.setTopic(qbi.getName());
			info.setXFontName("Dialog");
			info.setXFontSize(14);
			info.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
			info.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);
			info.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
			info.setColumnCount(1);
			info.setShowNumber(maxSubjectNum++);
			info.setIsShowNumber(1);
			info.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
			SelectQuestionInfo si = (SelectQuestionInfo) com.kingdee.eas.fdc.market.QuestionHelp.getQuestion(qbi.getId().toString(), qbi.getQuestionStyle().getValue());
			SelectQuestionItemCollection item = si.getItem();
			if (item != null && item.size() > 0) {
				DocumentItemInfo itemInfo = new DocumentItemInfo();
				info.getItems().add(itemInfo);
				for (int i = 0; i < item.size(); i++) {
					SelectQuestionItemInfo sItemInfo = item.get(i);
					DocumentOptionInfo optionInfo = new DocumentOptionInfo();
					optionInfo.setTopic(sItemInfo.getName());
					optionInfo.setXFontName("Dialog");
					optionInfo.setXFontSize(14);
					itemInfo.getOptions().add(optionInfo);
				}
			}
		}
		return info;
	}

	String documentId = null;

	/**
	 * 当documentId变更后，调用此函数，用来显示新问卷内容
	 * 
	 * @param documentId
	 *            问卷PK
	 */
	public void changeDocumentId(String documentId) throws Exception {
		if (this.documentId == null || !this.documentId.equals(documentId)) {
			this.documentId = documentId;
			if (documentId != null) {
				IDocument iDocument = DocumentFactory.getRemoteInstance();
				DocumentInfo info = (DocumentInfo) iDocument.getDocumentInfo(" where id = '" + documentId + "' order by subjects.subjectNumber");// subjects

				if (info != null) {
					sortDocumentElements(info);
					xDocument.setDoc(info);
				} else {
					xDocument.setEmptyDoc();
				}
				questItems = info.getSubjects().size();
			} else {
				xDocument.setEmptyDoc();
			}
		}
	}

	/**
	 * 给问卷中的信息排序，题目分组、选项排序。题目之间不需要排序，因为Y被从数据库查询出来时已经ORDER BY了
	 * 
	 * @param info
	 */
	public static void sortDocumentElements(DocumentInfo info) {
		DocumentSubjectCollection subjectCollection = info.getSubjects();
		int subCount = subjectCollection.size();
		for (int subIndex = 0; subIndex < subCount; subIndex++) {
			DocumentSubjectInfo subjectInfo = subjectCollection.get(subIndex);
			DocumentItemCollection itemCollection = subjectInfo.getItems();
			int itemCount = itemCollection.size();
			List itemList = new ArrayList();
			// 循环获取分组，处理分组内部选项
			for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
				DocumentItemInfo itemInfo = itemCollection.get(itemIndex);
				itemList.add(itemInfo);
				DocumentOptionCollection optionCollection = itemInfo.getOptions();
				int optionCount = optionCollection.size();
				List optionList = new ArrayList();
				// 循环获取选项
				for (int optionIndex = 0; optionIndex < optionCount; optionIndex++) {
					DocumentOptionInfo optionInfo = optionCollection.get(optionIndex);
					optionList.add(optionInfo);
				}
				// 选项排序
				Collections.sort(optionList);
				optionCollection.clear();
				for (int optionIndex = 0; optionIndex < optionCount; optionIndex++) {
					optionCollection.add((DocumentOptionInfo) optionList.get(optionIndex));
				}
			}
			// 分组排序
			Collections.sort(itemList);
			itemCollection.clear();
			for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
				itemCollection.add((DocumentItemInfo) itemList.get(itemIndex));
			}
		}
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    } 
    protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!STATUS_ADDNEW.equals(this.oprtState)) {
			return;
		}
		boolean isExist = true;
		IObjectValue objValue = this.editData;
		if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(objValue, currentOrgId);
			} else {
				String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue, currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
							prepareNumber(objValue, number);
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
    
    public void setNumberTextEnabled(){
		if (getNumberCtrl() != null) {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentSaleUnit();
			if (org != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(this.editData, org.getId().toString());

				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
				getNumberCtrl().setRequired(isAllowModify);
			}
		}
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	
	 //通过编码规则获取编码。子类可用。
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              当前用户所属组织不存在时，缺省实现是用集团的
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // 启用了断号支持功能,同时启用了用户选择断号功能
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                    // 没有启用断号支持功能，此时获取了编码规则产生的编码
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //如果启用了用户可修改
                    setNumberTextEnabled();
                }
                return;
            }
           
        } catch (Exception err) {
            //获取编码规则出错，现可以手工输入编码！
            handleCodingRuleError(err);

            //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
            setNumberTextEnabled();
        }

        //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
        setNumberTextEnabled();
    }

    protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		getNumberCtrl().setText(number);
	}
    
    public void actionJumpSet_actionPerformed(ActionEvent e) throws Exception {
    	String selectID = null;
    	if(this.editData.getId()!=null) selectID = this.editData.getId().toString();
    	if(selectID==null) return;
    	
    	UIContext uiContext = new UIContext(this); 
		String opera = OprtState.EDIT;
		uiContext.put(UIContext.ID, selectID);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
								.create(QuestPaperDocItemJumpSetUI.class.getName(), uiContext, null, opera);
			uiWindow.show();
			
			this.documentId = null;
			this.changeDocumentId(this.editData.getDocumentId());
		} catch (UIException ee) {
			ee.printStackTrace();
			SysUtil.abort();
		}
    }

    /* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractQuestionPaperDefineEditUI#actionAddNew_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		comVisable(true);
		super.actionAddNew_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		comVisable(true);
    	CommerceHelper.checkBeforEditOrDelete(this.editData);
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	CommerceHelper.checkBeforEditOrDelete(this.editData);
    	super.actionRemove_actionPerformed(e);
    }
    
    public void actionSimulateAnswer_actionPerformed(ActionEvent e)
    		throws Exception {
    	if(this.editData.getId()==null) return;
    	
    	QuestionPaperDefineInfo questDefineInfo = QuestionPaperDefineFactory.getRemoteInstance()
    						.getQuestionPaperDefineInfo("select documentId where id = '"+this.editData.getId()+"'");
    	UIContext uiContext = new UIContext(this); 		
		uiContext.put("QuestionDefine", questDefineInfo);
		uiContext.put("SimulateAnser", "模拟答题");    	

		this.getUIWindow().close();
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuestionAnswerIndexUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();   
    }
    
    
}