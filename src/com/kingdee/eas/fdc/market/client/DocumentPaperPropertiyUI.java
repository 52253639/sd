/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Dimension;
import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.QuestionPaperSizeEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class DocumentPaperPropertiyUI extends AbstractDocumentPaperPropertiyUI {
	
	
	private DocumentInfo docInfo;
	
	
	private static final Logger logger = CoreUIObject.getLogger(DocumentPaperPropertiyUI.class);

	
	public DocumentPaperPropertiyUI() throws Exception{
		super();
	}
	
	
	
	public void setDataObject(String key,IObjectValue objectValue){
		if("docInfo".equals(key)){
			docInfo = (DocumentInfo)objectValue;
		}
		super.setDataObject(key,objectValue);
	}


	public void onLoad() throws Exception {
		if(docInfo != null){
			setValues();
		}
		initUI();
		super.onLoad();
		
		this.headerStr.setMaxLength(80);
		this.footerStr.setMaxLength(80);
	}



	/**
	 * output class constructor
	 */
	public DocumentPaperPropertiyUI(DocumentInfo docInfo) throws Exception {
		super();
		this.docInfo = docInfo;
		initUI();
		setValues();
	}
	
	private void initUI(){
		selfHeight.setStepSize(new Integer(1));
		selfWidth.setStepSize(new Integer(1));
		fontSize.setStepSize(new Integer(1));
		topMarge.setStepSize(new Integer(1));
		bottomMarge.setStepSize(new Integer(1));
		leftMarge.setStepSize(new Integer(1));
		rightMarge.setStepSize(new Integer(1));
		columnCount.setStepSize(new Integer(1));
	}

	
	public void setValues(){
		
		int pageW = docInfo.getWidth();
		int pageH = docInfo.getHeight();
		//再预设的页面大小中寻找纸张
		QuestionPaperSizeEnum pageSize = getPrevDefinePageSize(pageW,pageH);
		if(pageSize != null){
			//找到了合适的预定义纸张
			selPageSize.setSelectedItem(pageSize);
			//判断是否横向显示：如果页面的高等于预定义页面宽
			if(pageSize.getValue().startsWith(String.valueOf(pageH))){
				isDown.setSelected(true);
			}
			//
			selPageSize.setEnabled(true);
			isDown.setEnabled(true);
			//将自定义纸张部分设置为空
			isSelf.setSelected(false);
			selfHeight.setValue(new Integer(0));
			selfWidth.setValue(new Integer(0));
			selfHeight.setEnabled(false);
			selfWidth.setEnabled(false);
		}else{
			isSelf.setSelected(true);
			selfHeight.setValue(new Integer(docInfo.getHeight()));
			selfWidth.setValue(new Integer(docInfo.getWidth()));
			selfHeight.setEnabled(true);
			selfWidth.setEnabled(true);
			//
			selPageSize.setEnabled(false);
			isDown.setEnabled(false);
		}
		fontSize.setValue(new Integer(docInfo.getXFontSize()));
		headerStr.setText(docInfo.getHeader());
		footerStr.setText(docInfo.getFooter());
		topMarge.setValue(new Integer(docInfo.getTopMarge()));
		bottomMarge.setValue(new Integer(docInfo.getBottomMarge()));
		leftMarge.setValue(new Integer(docInfo.getLeftMarge()));
		rightMarge.setValue(new Integer(docInfo.getRightMarge()));
		columnCount.setValue( new Integer(docInfo.getColumnCount()));
	}
	
	public void getValues(){
		if(isSelf.isSelected()){
			docInfo.setHeight(((Integer)this.selfHeight.getValue()).intValue());
			docInfo.setWidth(((Integer)this.selfWidth.getValue()).intValue());
		}else{
			QuestionPaperSizeEnum pageSize = (QuestionPaperSizeEnum)selPageSize.getSelectedItem();
			docInfo.setHeight(getBeforeStarOfPage(pageSize,isDown.isSelected()));
			docInfo.setWidth(getBeforeStarOfPage(pageSize,!isDown.isSelected()));
		}
		docInfo.setXFontName("Dialog");
		docInfo.setXFontSize(((Integer)this.fontSize.getValue()).intValue());
		docInfo.setHeader(headerStr.getText());
		docInfo.setFooter(footerStr.getText());
		docInfo.setTopMarge(((Integer)this.topMarge.getValue()).intValue());
		docInfo.setBottomMarge(((Integer)this.bottomMarge.getValue()).intValue());
		docInfo.setLeftMarge(((Integer)this.leftMarge.getValue()).intValue());
		docInfo.setRightMarge(((Integer)this.rightMarge.getValue()).intValue());
		docInfo.setColumnCount(((Integer)this.columnCount.getValue()).intValue());
	}
	
	private QuestionPaperSizeEnum getPrevDefinePageSize(int pageWidth,int pageHeight){
		String value = pageWidth + "*" + pageHeight;
		QuestionPaperSizeEnum re = QuestionPaperSizeEnum.getEnum(value);
		if(re == null){
			value = pageHeight + "*" + pageWidth;
			re = QuestionPaperSizeEnum.getEnum(value);
		}
		return re;
	}
	
	private int getBeforeStarOfPage(QuestionPaperSizeEnum ps , boolean beforeStar){
		String paseSizeString = ps.getValue();
		int i = paseSizeString.indexOf("*");
		if(beforeStar){
			return Integer.parseInt(paseSizeString.substring(0,i));
		}else{
			return Integer.parseInt(paseSizeString.substring(i+1));
		}
	}
	

    /**
     * output btnOK_actionPerformed method
     */
    protected void btnOK_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	getValues();  	
    	this.getUIWindow().close();
    }

    /**
     * output btnCancel_actionPerformed method
     */
    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
       this.getUIWindow().close();
    }


	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output isSelf_actionPerformed method
	 */
	protected void isSelf_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		if(isSelf.isSelected()){

			selfHeight.setEnabled(true);
			selfWidth.setEnabled(true);
			//
			selPageSize.setEnabled(false);
			isDown.setEnabled(false);
		}else{

			selfHeight.setEnabled(false);
			selfWidth.setEnabled(false);
			//
			selPageSize.setEnabled(true);
			isDown.setEnabled(true);
		}
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
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

}