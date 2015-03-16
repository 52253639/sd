/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CompeteItemEditUI extends AbstractCompeteItemEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CompeteItemEditUI.class);
    
    protected boolean isShowAttachmentAction() {
    	return true;
    }
    
    protected void kdtPriceInfoEntry_editStopped(KDTEditEvent e)
			throws Exception {
		super.kdtPriceInfoEntry_editStopped(e);
		int rowNum = e.getRowIndex();
		int colNum = e.getColIndex();
		if(kdtPriceInfoEntry.getCell(rowNum,kdtPriceInfoEntry.getColumnIndex("pushDate")).getValue()!=null) {
			setValueByPriceInfo(rowNum,colNum);	
		}else {
			MsgBox.showInfo("请先输入推盘日期！");
			return;
		}

		
	}
    
    public RequestContext prepareActionRemove(IItemAction itemAction)
			throws Exception
	{
		// TODO Auto-generated method stub
		return super.prepareActionRemove(itemAction);
	}

	/**
     * 获取最大日期所在的行号
     * @return
     */
    private int getMaxDateRowNum() {
    	if(kdtPriceInfoEntry.getRowCount() == 0)return -1;
    	int maxDateRowNum = 0;
    	Date maxDate = (Date) kdtPriceInfoEntry.getCell(0, "pushDate").getValue();
    	Date elseDate = null;
    	
    	for(int i = 1;i<kdtPriceInfoEntry.getRowCount();i++) {
    		elseDate = (Date)kdtPriceInfoEntry.getCell(i, "pushDate").getValue();
    		if(elseDate.after(maxDate)) {
    			maxDate = elseDate;
    			maxDateRowNum = i;
    		}
    	}
    	return maxDateRowNum;
    }
    /**
     * 根据最大日期所在的行，设置表头上的最新平均价，最新平均总价，最新起价，最新最高价
     * @param rowNum
     * @param colNum
     */
    private void setValueByPriceInfo(int wNum,int colNum) {

    	int dateColNum = kdtPriceInfoEntry.getColumnIndex("pushDate");
		int avgPriceColNum = kdtPriceInfoEntry.getColumnIndex("avgPrice");
		int avgavgAllAmonutColNum = kdtPriceInfoEntry.getColumnIndex("avgAllAmonut");
		int stPriceColNum = kdtPriceInfoEntry.getColumnIndex("stPrice");
		int highestPriceColNum = kdtPriceInfoEntry.getColumnIndex("highestPrice");
		int maxDateRowNum = getMaxDateRowNum();
		if(colNum == dateColNum) {
			this.txtnewestAvrPrice.setValue(kdtPriceInfoEntry.getCell(maxDateRowNum, avgPriceColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, avgPriceColNum).getValue());
			this.txtnewestAvgAllAm.setValue(kdtPriceInfoEntry.getCell(maxDateRowNum, avgavgAllAmonutColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, avgavgAllAmonutColNum).getValue());
			this.txtnewestInitPrice.setValue(kdtPriceInfoEntry.getCell(maxDateRowNum, stPriceColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, stPriceColNum).getValue());
			this.txtnewestHighPrice.setValue(kdtPriceInfoEntry.getCell(maxDateRowNum, highestPriceColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, highestPriceColNum).getValue());
			return;
		}
		if(colNum == avgPriceColNum) {
			BigDecimal num = new BigDecimal("0.00");
			num = num.setScale(2);
			num = (BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, avgPriceColNum).getValue();
			this.txtnewestAvrPrice.setValue(num);
			return;
		}else if(colNum == avgavgAllAmonutColNum) {
			this.txtnewestAvgAllAm.setValue((BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, avgavgAllAmonutColNum).getValue());
			return;
		}else if(colNum == stPriceColNum) {
			this.txtnewestInitPrice.setValue((BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, stPriceColNum).getValue());
			return;
		}else if(colNum == highestPriceColNum) {
			this.txtnewestHighPrice.setValue((BigDecimal)kdtPriceInfoEntry.getCell(maxDateRowNum, highestPriceColNum).getValue());
			return;
		}
		
    }
    
	protected void pkfromTime_dataChanged(DataChangeEvent e) throws Exception 
	{
		
		
		
		
		/*
//		pktoTime.setm
		
		
		Object newValue = e.getNewValue();
		Object oldValue = e.getOldValue();
		Object toTime   = pktoTime.getValue();
		if(newValue==null || toTime==null)
		{
			txttotleYears.setValue(null);
			return;
		}
		
		if(newValue.equals(oldValue))
			return;
		
		Date newDate = (Date)newValue;
		Date toDate = (Date)toTime;

		if(!newDate.after(toDate)) 
		{
			long millons = toDate.getTime() - newDate.getTime();
			long year = millons/1000/60/60/24/365;
			txttotleYears.setValue(new Long(year));
		}else
		{
			txttotleYears.setValue(null);
			
		}
		*/
		
		Date toTime = (Date) pktoTime.getValue();
		Date fromTime = (Date)pkfromTime.getValue();
		getAllYears(toTime,fromTime);
//		if(toTime == null || fromTime == null )return;
//		if(fromTime.after(toTime)) {
//			MsgBox.showError("起始日期不能大于结束日期！");
//			
//			pkfromTime.setValue(null);
//			txttotleYears.setValue(null);
////			pkfromTime.grabFocus();
//			SysUtil.abort(new Exception("起始日期不能大于结束日期！"));
//			return;
//		}
//		long millons = toTime.getTime() - fromTime.getTime();
//		long year = millons/1000/60/60/24/365;
//		txttotleYears.setValue(new Long(year));
		super.pkfromTime_dataChanged(e);
	}
	protected void pktoTime_dataChanged(DataChangeEvent e) throws Exception {
		
		Date toTime = (Date) pktoTime.getValue();
		Date fromTime = (Date)pkfromTime.getValue();
		getAllYears(toTime,fromTime);
//		if(toTime == null || fromTime == null)return;
//		if(fromTime.after(toTime)) {
//			MsgBox.showError("起始日期不能大于结束日期！");
//			pktoTime.setValue(null);
//			txttotleYears.setValue(null);
////			pktoTime.grabFocus();
//			SysUtil.abort(new Exception("起始日期不能大于结束日期！"));
//			return;
//		}
//		
//		long millons = toTime.getTime() - fromTime.getTime();
//		long year = millons/1000/60/60/24/365;
//		txttotleYears.setValue(new Long(year));
		super.pktoTime_dataChanged(e);
	}
	
	private void getAllYears(Date toTime,Date fromTime) {
		if(toTime == null || fromTime == null) {
			txttotleYears.setValue(null);
			return;
		}
		long millons = toTime.getTime() - fromTime.getTime();
		long year = millons/1000/60/60/24/365;
		txttotleYears.setValue(new Long(year));
	}
	public void actionRemoveLine_actionPerformed(ActionEvent e)
    throws Exception
	{
		int maxRowNum=getmaxRowIndex(kdtPriceInfoEntry);

		int avgPriceColNum = kdtPriceInfoEntry.getColumnIndex("avgPrice");
		int avgavgAllAmonutColNum = kdtPriceInfoEntry.getColumnIndex("avgAllAmonut");
		int stPriceColNum = kdtPriceInfoEntry.getColumnIndex("stPrice");
		int highestPriceColNum = kdtPriceInfoEntry.getColumnIndex("highestPrice");


			this.txtnewestAvrPrice.setValue(kdtPriceInfoEntry.getCell(maxRowNum, avgPriceColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxRowNum, avgPriceColNum).getValue());
			this.txtnewestAvgAllAm.setValue(kdtPriceInfoEntry.getCell(maxRowNum, avgavgAllAmonutColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxRowNum, avgavgAllAmonutColNum).getValue());
			this.txtnewestInitPrice.setValue(kdtPriceInfoEntry.getCell(maxRowNum, stPriceColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxRowNum, stPriceColNum).getValue());
			this.txtnewestHighPrice.setValue(kdtPriceInfoEntry.getCell(maxRowNum, highestPriceColNum).getValue() == null ?new BigDecimal("0.00") : (BigDecimal)kdtPriceInfoEntry.getCell(maxRowNum, highestPriceColNum).getValue());
			return;
			
	}

	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		// 先删除超类分录行上的新增或插入行按钮的监听
		//价格信息
		ActionListener[] actionListeners1 = this.kdtPriceInfoEntry_detailPanel
				.getAddNewLineButton().getActionListeners();
		if (actionListeners1 != null && actionListeners1.length > 0) {
			int length2 = actionListeners1.length;

			for (int i = 0; i < length2; i++) {
				this.kdtPriceInfoEntry_detailPanel.getAddNewLineButton()
						.removeActionListener(actionListeners1[i]);
			}
		}
		
		//销售信息
		ActionListener[] actionListeners2 = this.kdtSaleInfoEntry_detailPanel
		.getAddNewLineButton().getActionListeners();

		if (actionListeners2 != null && actionListeners2.length > 0) {
			int length2 = actionListeners2.length;

			for (int i = 0; i < length2; i++) {
				this.kdtSaleInfoEntry_detailPanel.getAddNewLineButton()
						.removeActionListener(actionListeners2[i]);
			}
		}
		//价格信息
		ActionListener[] actionListeners = this.kdtPriceInfoEntry_detailPanel
				.getInsertLineButton().getActionListeners();

		if (actionListeners != null && actionListeners.length > 0) {
			int length1 = actionListeners.length;

			for (int i = 0; i < length1; i++) {
				this.kdtPriceInfoEntry_detailPanel.getInsertLineButton()
						.removeActionListener(actionListeners[i]);
			}

		}
		//销售信息
		ActionListener[] actionListener = this.kdtSaleInfoEntry_detailPanel
				.getInsertLineButton().getActionListeners();

		if (actionListener != null && actionListener.length > 0) {
			int length1 = actionListener.length;

			for (int i = 0; i < length1; i++) {
				this.kdtSaleInfoEntry_detailPanel.getInsertLineButton()
						.removeActionListener(actionListener[i]);
			}

		}
		// 对价格信息 删除行按钮进行监听
		this.kdtPriceInfoEntry_detailPanel.getAddNewLineButton().removeActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				try {

					CompeteItemEditUI.this.kdtSaleInfoEntry_detailPanel.actionRemoveLine_actionPerformed(arg0);
					
					actionRemoveLine_actionPerformed(arg0);
					
//					CompeteItemEditUI.this.kdtSaleInfoEntry.getRow(
//							getCurrRowIndex(0,kdtSaleInfoEntry)).getCell("number")
//							.setValue(new Integer(1));

				} catch (Exception e1) {
					handUIException(e1);
				}

			}
		});
		// 对价格信息新增行按钮进行监听
		this.kdtPriceInfoEntry_detailPanel.getAddNewLineButton().addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						try {

							CompeteItemEditUI.this.kdtPriceInfoEntry_detailPanel
							.actionAddnewLine_actionPerformed(arg0);

							CompeteItemEditUI.this.kdtPriceInfoEntry.getRow(
									getCurrRowIndex(0,kdtPriceInfoEntry)).getCell("number")
									.setValue(new Integer(1));

						} catch (Exception e1) {
							handUIException(e1);
						}

					}

				});
		
		// 对销售信息 新增行按钮进行监听
		this.kdtSaleInfoEntry_detailPanel.getAddNewLineButton().addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						try {

							CompeteItemEditUI.this.kdtSaleInfoEntry_detailPanel
									.actionAddnewLine_actionPerformed(arg0);

							CompeteItemEditUI.this.kdtSaleInfoEntry.getRow(
									getCurrRowIndex(0,kdtSaleInfoEntry)).getCell("number")
									.setValue(new Integer(1));

						} catch (Exception e1) {
							handUIException(e1);
						}

					}

				});
		
		// 对销售信息  插入行按钮进行监听
		this.kdtSaleInfoEntry_detailPanel.getInsertLineButton().addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						try {

							CompeteItemEditUI.this.kdtSaleInfoEntry_detailPanel
									.actionInsertLine_actionPerformed(arg0);

							CompeteItemEditUI.this.kdtSaleInfoEntry.getRow(
									getCurrRowIndex(1,kdtSaleInfoEntry)).getCell("number")
									.setValue(new Integer(1));

						} catch (Exception e1) {
							handUIException(e1);
						}

					}

				});
		
		// 对价格信息 插入行按钮进行监听
		this.kdtPriceInfoEntry_detailPanel.getInsertLineButton().addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						try {

							CompeteItemEditUI.this.kdtPriceInfoEntry_detailPanel
									.actionInsertLine_actionPerformed(arg0);

							CompeteItemEditUI.this.kdtPriceInfoEntry.getRow(
									getCurrRowIndex(1,kdtPriceInfoEntry)).getCell("number")
									.setValue(new Integer(1));

						} catch (Exception e1) {
							handUIException(e1);
						}

					}

				});
		// ......
	    }
	    

	

		// 获得新增行或插入行后的index(没考虑带合计行的分录,如需求变更,请修改如下代码...)
		private int getCurrRowIndex(int arg,KDTable kdtable) {// arg = 0为新增行状态,arg =1为插入行状态

			int rowCount = kdtable.getRowCount();

			int currActiveRow = kdtable.getSelectManager().getActiveRowIndex();

			if (currActiveRow < 0) {// 如果分录上没焦点

				if (rowCount == 0) {// 如果分录没记录,则新增第一行

					return 0;

				} else {// 当前行 为新增行后的最后一行

					return rowCount - 1;

				}
			} else {// 分录行上存在焦点

				if (arg == 0) {// 新增行始终是最后一行

					return rowCount - 1;

				} else {// 插入行是当前选中的行的上一行，即插入行后就为原选中行的rowIndex了
					return currActiveRow;
				}

			}

		}
		
		
		//删除后的最大行号
		private int getmaxRowIndex(KDTable kdtable) {

			int rowCount = kdtable.getRowCount();

			int currActiveRow = kdtable.getSelectManager().getActiveRowIndex();

			if (currActiveRow < 0) {// 如果分录上没焦点

				if (rowCount == 0) {// 如果分录没记录,则新增第一行

					return 0;

				} else {// 当前行 为新增行后的最后一行

					return rowCount;

				}
			} else {// 分录行上存在焦点
				
					return currActiveRow;
				}

			}

	/**
     * output class constructor
     */
    public CompeteItemEditUI() throws Exception
    {
        super();
       
    }
    
    private void updateNum(){
    	for(int i = 0;i<kdtPriceInfoEntry.getRowCount();i++){
    		String number = kdtPriceInfoEntry.getRow(i).getCell("number").getValue().toString();
    		int num = Integer.parseInt(number);
    		int newNum = num + 1;
    		kdtPriceInfoEntry.getRow(i).getCell("number").setValue(new Integer(newNum));
    	}
    }
    
    public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		kdtPriceInfoEntry.getColumn("number").getStyleAttributes().setLocked(true);
		 if(this.oprtState.equals(STATUS_EDIT)){
	        	updateNum();
	        }
//		this.actionAttachment.setEnabled(true);
	}
    
	/**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
//        this.actionAttachment.setVisible(true);
//        this.actionAttachment.setEnabled(true);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	
        super.actionSave_actionPerformed(e);
    }
 
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	FDCClientVerifyHelper.verifyEmpty(this,this.txtNumber);
    	FDCClientVerifyHelper.verifyEmpty(this,this.txtName);
    	FDCClientVerifyHelper.verifyEmpty(this,this.pkfromTime);
    	FDCClientVerifyHelper.verifyEmpty(this,"com.kingdee.eas.fdc.market.MarketResource",this.pktoTime,"endDateNotEmpty");
    	Date startDate = (Date)this.pkfromTime.getValue();
    	Date endDate =(Date)this.pktoTime.getValue();
    	FDCClientVerifyHelper.verifyDateFromTo(this,"com.kingdee.eas.fdc.market.MarketResource",startDate,endDate,"endDateLessStartDate");
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        updateNum();
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	if(getOprtState().equals(OprtState.ADDNEW)){
    		return;
    	}
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.CompeteItemFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.CompeteItemInfo objectValue = new com.kingdee.eas.fdc.market.CompeteItemInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
//		objectValue.setName("4");
        return objectValue;
    }
	protected KDBizPromptBox getSellProjectCtrl() {
		// TODO Auto-generated method stub
		return this.prmtsellproject;
	}
	protected FDCDataBaseInfo getEditData() {
		// TODO Auto-generated method stub
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl()
	{
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	
	public void verifyData() throws Exception
	{
		super.verifyData();
	}
	protected void verifyInput(ActionEvent e) throws Exception {

	
//		if(this.pkfromTime.getValue()!=null && this.pktoTime.getTimestamp()!=null){
//    		if(this.pkfromTime.getTimestamp().after(this.pktoTime.getTimestamp())){
//    			MsgBox.showInfo("结束日期不能小于开始日期！");
//    			SysUtil.abort();
//    		}else{
//            double milis=this.pktoTime.getTimestamp().getTime() - this.pkfromTime.getTimestamp().getTime();
//    			
//            double years=milis/1000/60/60/24/365;
//            this.txttotleYears.setText(new Float(years).toString());
//    		}
//		    }
		super.verifyInput(e);
	
		
		
		
	}

	
	
//    protected void myVerifyInput(ActionEvent e) throws Exception {
//		// 编码是否为空
//    	if (isUseNameBlankCheck()){
//    		KDTextField txtNumber = this.getNumberCtrl();
//    		if (txtNumber.isEnabled() && txtNumber.isEditable()&&(txtNumber.getText() == null || txtNumber.getText().trim().length() < 1)) {
//    			txtNumber.requestFocus(true);
//    			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//    		}
//    	}
//
//	}
}