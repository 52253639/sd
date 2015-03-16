/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.market.DocumentFactory;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionFactory;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.IDocument;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.client.GatheringTrendFilterUI;
import com.kingdee.eas.fdc.sellhouse.client.ProductTypeSellStatFilterUI;
import com.kingdee.eas.framework.*;
import com.kingdee.util.DateTimeUtils;
import com.lowagie.text.Row;

/**
 * output class name
 */
public class QuestionStatListUI extends AbstractQuestionStatListUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestionStatListUI.class);
    
	private QuestionStatFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	//存放问题答案的组合
	private Map itemOptionMap = new HashMap();
    
    
    private ItemAction[] hideAction = {this.actionEdit,this.actionAddNew,this.actionRemove,this.actionLocate,this.actionView};
    
    public QuestionStatListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception
    {
    	super.onLoad();
    	
    	this.tblMain.checkParsed();    

		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
	//	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.refresh(null);
		
    	FDCClientHelper.setActionVisible(hideAction,false);
    }
    
    protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
    
    private QuestionStatFilterUI getFilterUI() throws Exception
	{
		if (this.filterUI == null)
		{
			this.filterUI = new QuestionStatFilterUI(this,this.actionOnLoad);
		}
		return this.filterUI;
	}
    
  
    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }


    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

   

	protected ICoreBase getBizInterface() throws Exception
	{
		return null;
	}

	protected String getEditUIName()
	{
		return null;
	}
	
	protected void execQuery()
	{
		String documentId = this.getDocumentId();
		this.addHeadByQuestion(documentId);
		
		
		String paperId = this.getPaperId();
		String projectId = this.getProjectId();
		
		tblMain.removeRows();
		
    	this.fillData(paperId, projectId);
	
	    this.tblMain.repaint();
		
		
	}
	private void fillData(String paperId,String projectId)
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		view.getSelector().add("*");
		view.getSelector().add("entrys.*");
		view.getSelector().add("customer.name");
		view.getSelector().add("customer.certificateNumber");
		view.getSelector().add("customer.phone");
		view.getSelector().add("purchase.*");
		view.getSelector().add("purchase.room.number");
		view.getSelector().add("purchase.room.roomModel.name");
		view.getSelector().add("purchase.payType.name");
		view.getSelector().add("persion.name");
		
		if(projectId != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",projectId));
		}
		filter.getFilterItems().add(new FilterItemInfo("questionPaper.id",paperId));
		
		
		Date beginDate = this.getBeginDate();
		Date endDate = this.getEndDate();
		
		if(beginDate != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("inputDate",beginDate,CompareType.GREATER_EQUALS));
		}
		if(endDate != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("inputDate",FDCDateHelper.getNextDay(endDate),CompareType.LESS));
		}
		
		QuestionPaperAnswerCollection coll = null;
		try
		{
			coll = QuestionPaperAnswerFactory.getRemoteInstance().getQuestionPaperAnswerCollection(view);
		} catch (BOSException e)
		{
			this.abort();
		}
		
		Map map = new HashMap();
		
		for(int i = 0; i < coll.size(); i ++)
		{
			QuestionPaperAnswerInfo info = coll.get(i);
			
			IRow row = this.tblMain.addRow();
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("questionDate").setValue(info.getInputDate());
			
			PurchaseInfo purchase = info.getPurchase();
			if(purchase != null	)
			{
				row.getCell("purchaseDate").setValue(purchase.getPurchaseDate());
				
				RoomInfo room = purchase.getRoom();
				if(room != null)
				{
					row.getCell("roomNo").setValue(room.getNumber());
					
					RoomModelInfo roomModel = room.getRoomModel();
					if(roomModel != null)
					{
						row.getCell("roomModel").setValue(roomModel.getName());
					}
				}
				SHEPayTypeInfo pay = purchase.getPayType();
				if(pay != null)
				{
					row.getCell("payType").setValue(pay.getName());
				}
			}
			FDCCustomerInfo customer =  (FDCCustomerInfo)info.getCustomer();
			if(customer != null)
			{
				row.getCell("customerName").setValue(customer.getName());
				row.getCell("certificateNum").setValue(customer.getCertificateNumber());
				row.getCell("tel").setValue(customer.getPhone());
			}
			
			PersonInfo person = info.getPersion();
			if(person != null)
			{
				row.getCell("salesman").setValue(person.getName());
			}
			
			
			QuestionPaperAnswerEntryCollection entryColl = info.getEntrys();
			if(entryColl != null)
			{
				for(int k = 0; k < entryColl.size();k ++)
				{
					String optionId = entryColl.get(k).getOption();
					
					ICell cell = row.getCell(optionId);
					if(cell != null)
					{
						cell.setValue(new Integer(1));
						
						if(map.get(optionId) != null)
						{
							Integer temp = (Integer)map.get(optionId);
							int tt = temp.intValue();
							++tt;
							temp = new Integer(tt);
							
							map.put(optionId, temp);
						}
						else
						{
							map.put(optionId, new Integer(1));
						}
					}
				}
			}
		}
		
		
		
		
		if(this.tblMain.getRowCount() > 0)
		{
		//小计
		IRow row = this.tblMain.addRow();
		row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		Set set = map.keySet();
		
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			String tempId = (String)it.next();
			Integer t = (Integer)map.get(tempId);
			
			if(row.getCell(tempId) != null)
			{
				row.getCell(tempId).setValue(t);
			}
		}
		row.getCell("id").setValue("小计");
		row.getCell("id").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		int r = row.getRowIndex();
		this.tblMain.getMergeManager().mergeBlock(r, 0, r, this.tblMain.getColumnIndex("salesman"));
		
		
		//合计
		Set itemSet = itemOptionMap.keySet();
		Iterator itemIt = itemSet.iterator();
		
		IRow row2 = this.tblMain.addRow();
		row2.getCell("id").setValue("合计");
		row2.getCell("id").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		row2.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		this.tblMain.getMergeManager().mergeBlock(row2.getRowIndex(), 0, row2.getRowIndex(), this.tblMain.getColumnIndex("salesman"));
		
		//比例
		IRow row3 = this.tblMain.addRow();
		row3.getCell("id").setValue("比例");
		row3.getCell("id").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		row3.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		this.tblMain.getMergeManager().mergeBlock(row3.getRowIndex(), 0, row3.getRowIndex(), this.tblMain.getColumnIndex("salesman"));
		while(itemIt.hasNext())
		{
			int temp = 0;
			String itemId = (String)itemIt.next();
			List list = (ArrayList)itemOptionMap.get(itemId);
			for(int k = 0; k < list.size();  k ++)
			{
				Object obj = row.getCell(list.get(k).toString()).getValue();
				if(obj instanceof Integer)
				{
					int t = ((Integer)obj).intValue();
					temp = temp + t;
				}
				
				this.tblMain.getMergeManager().mergeBlock(row2.getRowIndex(), this.tblMain.getColumnIndex(list.get(0).toString()), row2.getRowIndex(),this.tblMain.getColumnIndex(list.get(k).toString()) );
			}
			//比例
			for(int k = 0; k < list.size();  k ++)
			{
				Object obj = row.getCell(list.get(k).toString()).getValue();
				if(obj instanceof Integer)
				{
					int t = ((Integer)obj).intValue();
					
					float v = (float)t/(float)temp;
					BigDecimal bd = new BigDecimal(v);
					
					String value = bd.setScale(4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
					row3.getCell(list.get(k).toString()).setValue(value);
				}
				
			}
			if(list!=null && list.size()>0 ){
				if(list.get(0) != null && row2.getCell(list.get(0).toString()) != null)
				{
				row2.getCell(list.get(0).toString()).setValue(new Integer(temp));
				row2.getCell(list.get(0).toString()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
				}
			}
			
		}
		
		}
		
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent arg0) throws Exception
	{
		
	}
	
	private String getDocumentId()
	{
		FDCCustomerParams para;
		try
		{
			para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			return ((QuestionStatFilterUI) this.getFilterUI()).getDocumentId(para);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	private Date getBeginDate()
	{
		FDCCustomerParams para;
		try
		{
			para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			
			if(para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
			{
				return ((QuestionStatFilterUI) this.getFilterUI()).getBeginQueryDate(para);
			}
			else
				return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private Date getEndDate()
	{

		FDCCustomerParams para;
		try
		{
			para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			if(para.isNotNull("isShowAll") && !para.getBoolean("isShowAll"))
			{
				return ((QuestionStatFilterUI) this.getFilterUI()).getEndQueryDate(para);
			}
			else
				return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	private String getProjectId()
	{
		FDCCustomerParams para;
		try
		{
			para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			return ((QuestionStatFilterUI) this.getFilterUI()).getProjectId(para);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	private String getPaperId()	
	{
		FDCCustomerParams para;
		try
		{
			para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			return ((QuestionStatFilterUI) this.getFilterUI()).getPaperId(para);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}
    
    private void addHeadByQuestion(String documentId)
    {
    	//去除动态列
    	int total = this.tblMain.getColumnCount();
    	int offSet = 0 ;
    	
    	int col = this.tblMain.getColumnIndex("salesman");
    	for(int i = col+1; i < total; i ++)
    	{
    		this.tblMain.removeColumn(i - offSet);
    		offSet ++;
    	}
    	itemOptionMap.clear();
    	
    	
    	//获取问题
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	view.getSelector().add("*");
    	view.getSelector().add("options.*");
    	
    	filter.getFilterItems().add(new FilterItemInfo("subjectId.documentId.id",documentId));
    	filter.getFilterItems().add(new FilterItemInfo("subjectId.subjectType",DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE));
    	filter.getFilterItems().add(new FilterItemInfo("subjectId.subjectType",DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE));
    	filter.setMaskString(" #0 and (#1 or #2 )");
    	
    	
    	DocumentItemCollection itemColl = null;
		try
		{
			itemColl = DocumentItemFactory.getRemoteInstance().getDocumentItemCollection(view);
		} catch (BOSException e)
		{
			this.abort();
		}
    	
    	for(int i = 0; i < itemColl.size(); i ++)
    	{
    		int count = this.tblMain.getColumnCount();
        	DocumentItemInfo documentItemInfo = itemColl.get(i);
    		
    		DocumentOptionCollection optionColl = documentItemInfo.getOptions();
    		
    		List list = new ArrayList();
    		if(optionColl != null)
    		{
    			int size = optionColl.size();
				for(int j = 0; j < size; j ++)
    			{
    				IColumn tempCol = this.tblMain.addColumn();
    				DocumentOptionInfo documentOptionInfo = optionColl.get(j);
					String optionId = documentOptionInfo.getId().toString();
					tempCol.setKey(optionId);
    				IRow row1 = this.tblMain.getHeadRow(1);
    				row1.getCell(optionId).setValue(documentOptionInfo.getTopic());
    				
    				IRow row0 = this.tblMain.getHeadRow(0);
    				row0.getCell(optionId).setValue(documentItemInfo.getTopic());
    				
    				list.add(optionId);
    			}
				this.tblMain.getHeadMergeManager().mergeBlock(0,count,0,count+size-1);
				
				itemOptionMap.put(documentItemInfo.getId().toString(), list);
    		}
    	}
    	

    }

    public int getRowCountFromDB(){
		return -1;
	}

}