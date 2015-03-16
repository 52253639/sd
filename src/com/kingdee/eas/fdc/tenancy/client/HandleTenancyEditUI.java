/**
 * 租赁交接界面
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.Room;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntry;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;


/**
 * output class name
 */
public class HandleTenancyEditUI extends AbstractHandleTenancyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(HandleTenancyEditUI.class);
    
    private ItemAction[] hiddenAction = {this.actionEdit,this.actionAddNew,this.actionSave,this.actionCopy
    		,this.actionPrint,this.actionPrintPreview,this.actionFirst,this.actionPre,this.actionNext,
    		this.actionLast,this.actionSubmit,this.actionRemove,this.actionCancel,this.actionCancelCancel,this.actionAttachment};
    
    //接收传过来的合同
	TenancyBillInfo tenancyBillInfo = null;
    
    /**
     * output class constructor
     */
    public HandleTenancyEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected IObjectValue createNewData()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception
	{
		super.onLoad();
		
		this.customerTable.checkParsed();
		this.roomTable.checkParsed();
		this.initUI();
		TenancyClientHelper.hideButton(hiddenAction);
		String tenancyBillId = (String) this.getUIContext().get("tenancyBillId");

		if(tenancyBillId == null)
		{
			logger.error("从上下文中获取 tenancyBillId 参数的时候为空值...");
			MsgBox.showInfo("没有找到待处理的合同，无法进行交接操作！");
			this.abort();
		}
		tenancyBillInfo = TenancyHelper.getTenancyBillInfo(tenancyBillId);
		
		this.fillRoomTableByTenancyBill(tenancyBillInfo);
		this.fillCustomerTableByTenancyBill(tenancyBillInfo);
		this.fillTenancyBillInfo(tenancyBillInfo);
	}
	
	/**
	 * 根据得到的合同信息来填充房间的信息
	 * @param tenancyBillInfo
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void fillRoomTableByTenancyBill(TenancyBillInfo tenancyBillInfo) throws BOSException, EASBizException
	{
		if(tenancyBillInfo == null)
		{
			logger.warn("传过来的合同为空呀，咋搞的哦...");
			return;
		}
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = tenancyBillInfo.getTenancyRoomList();

		for(int i = 0; i< tenancyRoomEntryColl.size(); i ++)
		{
			//一个合同他所对应的房间没有了，这种情况会存在么？
			if(tenancyRoomEntryColl.get(i).getRoom() != null)
			{
				TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
				tenancyRoomEntryInfo.setTenancy(tenancyBillInfo);
				
				IRow row = this.roomTable.addRow();
				
				row.setUserObject(tenancyRoomEntryInfo);
				
				setRoomHandleStateOnRow(row, tenancyRoomEntryInfo);
			}
			else
			{
				logger.warn("该合同所对应的房间没了！晕倒...");
			}
		}
	}
	
	/**
	 * 根据合同信息来填充客户表格
	 * @param tenancyBillInfo
	 */
	private void fillCustomerTableByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
		{
			logger.warn("传过来的合同为空呀，咋搞的哦...");
			return;
		}
		
		TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();

		for(int i = 0; i< tenancyCustomerEntryColl.size(); i ++)
		{
			//一个合同他所对应的客户没有了，这种情况会存在么？
			if(tenancyCustomerEntryColl.get(i).getFdcCustomer() != null)
			{
				FDCCustomerInfo fdcCustomer =tenancyCustomerEntryColl.get(i).getFdcCustomer();
				
				IRow row = this.customerTable.addRow();
				
				row.setUserObject(fdcCustomer);
				row.getCell("proporation").setValue(tenancyCustomerEntryColl.get(i).getPropertyPercent());
				row.getCell("customerName").setValue(fdcCustomer.getName());
				row.getCell("postalCode").setValue(fdcCustomer.getPostalcode());
				row.getCell("communicateTel").setValue(fdcCustomer.getPhone());
				row.getCell("certificateName").setValue(fdcCustomer.getCertificateName());
				row.getCell("certificateId").setValue(fdcCustomer.getCertificateNumber());
				row.getCell("communicateAddress").setValue(fdcCustomer.getAddress());
				row.getCell("bookedDate").setValue(fdcCustomer.getCreateTime());
				row.getCell("remark").setValue(fdcCustomer.getDescription());
			}
			else
			{
				logger.warn("这个合同所对应的客户没了哦...");
			}
		}
	
	}
	
	/**
	 * 填充合同的相关信息
	 * @param tenancyBillInfo
	 */
	private void fillTenancyBillInfo(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return;
		
		this.txtBillNumber.setText(tenancyBillInfo.getNumber());
		if(tenancyBillInfo.getAgency() != null)
		{
			this.txtAgencyCompany.setText(tenancyBillInfo.getAgency().getName());
		}
		if(tenancyBillInfo.getTenancyAdviser() != null)
		{
			this.txtBillAdviser.setText(tenancyBillInfo.getTenancyAdviser().getName());
		}
		this.txtBillCreator.setText(tenancyBillInfo.getCreator().getName());
		this.txtBillLease.setText(new Integer(tenancyBillInfo.getLeaseTime()).toString());
		
		this.txtBillName.setText(tenancyBillInfo.getName());
		if(tenancyBillInfo.getTenancyType() != null)
		{
			this.txtBillType.setText(tenancyBillInfo.getTenancyType().getAlias());
		}
	}
	
	/**
	 * 初始化单元
	 *
	 */
	private void initUI()
	{
	    KDCheckBox roomItem = new KDCheckBox();
	    roomItem.addActionListener(new ActionListener()
	    		{
					public void actionPerformed(ActionEvent arg0)
					{
						//这个用法太神奇了，只是为了让复选框的 焦点转移，以便第二次单击的时候，来触发 table_Clicked事件
						kDPanel1.requestFocus();
					}
	    		});
	    ICellEditor roomEditorItem = new KDTDefaultCellEditor(roomItem);
	    this.roomTable.getColumn("item").setEditor(roomEditorItem);
	    this.customerTable.getColumn("proporation").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    this.customerTable.getColumn("bookedDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	    this.roomTable.getColumn("bizDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
	    
	    for(int i = 0; i < this.customerTable.getColumnCount(); i ++)
	    {
	    	this.customerTable.getColumn(i).getStyleAttributes().setLocked(true);
	    }
	    for(int i = 1; i < this.roomTable.getColumnCount(); i ++)
	    {
	    	this.roomTable.getColumn(i).getStyleAttributes().setLocked(true);
	    }
	}
	
	private void setRoomHandleStateOnRow(IRow row, TenancyRoomEntryInfo tenancyRoomEntryInfo) throws BOSException, EASBizException{
		row.getCell("room").setValue(tenancyRoomEntryInfo.getRoomLongNum());
		row.getCell("item").setValue(Boolean.FALSE);
		
    	//先判断当前这个处理的合同是已收押金首租，如果是这样的话，就说明这次交接的房间是 新租交接的
    	if(/*TenancyBillStateEnum.DepositReved.equals(tenancyBillInfo.getTenancyState())
    			||*/ TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
    	{
    		row.getCell("handleType").setValue("交房");
    		if(tenancyRoomEntryInfo.getActDeliveryRoomDate() != null){
    			row.getCell("item").setValue(Boolean.TRUE);
    			row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActDeliveryRoomDate());
    			row.getStyleAttributes().setLocked(true);
    			return;
    		}
    	}
    	//执行状态的
    	else if(TenancyBillStateEnum.Executing.equals(tenancyBillInfo.getTenancyState()))
    	{
    		//先看下它有没有退租单,有退租单的话，判断其是否已经收房
    		if(this.getQuitTenancyInfoByTenancyBill(this.tenancyBillInfo))
    		{
    			row.getCell("handleType").setValue("收房");
    			if (tenancyRoomEntryInfo.getActQuitTenDate() != null){
    				row.getCell("item").setValue(Boolean.TRUE);
    				row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActQuitTenDate());
    				row.getStyleAttributes().setLocked(true);
    				return;
    			}
    		}else{
    			// 没有退租单，再去查找它是否有符合交接条件的其他合同
    			String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenancyBillInfo.getId().toString());
    			if(targetTenId != null){
    				TenancyBillInfo targetTen = TenancyHelper.getTenancyBillInfo(targetTenId);
    				//目标合同收完首租押金或半执行状态可以进行交接房间
    				if(/*TenancyBillStateEnum.DepositReved.equals(targetTen.getTenancyState())  ||*/  
    						TenancyBillStateEnum.PartExecuted.equals(targetTen.getTenancyState())){
    					row.getCell("handleType").setValue(targetTen.getTenancyType() + "交接");
    					if (tenancyRoomEntryInfo.getActQuitTenDate() != null){
    	    				row.getCell("item").setValue(Boolean.TRUE);
    	    				row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActQuitTenDate());
    	    				row.getStyleAttributes().setLocked(true);
    	    				return;
    	    			}
    				}else{
    					//如果目标合同为审批状态(未收完首租押金),但该房间在目标合同中不存在,可以进行退房操作
    					boolean existInTargetBill = false;
    					TenancyRoomEntryCollection targetTenRooms = targetTen.getTenancyRoomList();
    					for(int j=0; j<targetTenRooms.size(); j++){
    						TenancyRoomEntryInfo targetTenRoom = targetTenRooms.get(j);
    						if(targetTenRoom.getRoomLongNum() != null  &&  targetTenRoom.getRoomLongNum().equals(tenancyRoomEntryInfo.getRoomLongNum())){
    							existInTargetBill = true;
    							break;
    						}
    					}
    					if(!existInTargetBill){
    						row.getCell("handleType").setValue("收房");
    						if (tenancyRoomEntryInfo.getActQuitTenDate() != null){
        	    				row.getCell("item").setValue(Boolean.TRUE);
        	    				row.getCell("bizDate").setValue(tenancyRoomEntryInfo.getActQuitTenDate());
        	    				row.getStyleAttributes().setLocked(true);
        	    				return;
        	    			}
    					}else{
    						row.getCell("handleType").setValue("不能交接");
    						row.getStyleAttributes().setLocked(true);
    					}
    				}
    			}else{
    				row.getCell("handleType").setValue("不能交接");
    				row.getStyleAttributes().setLocked(true);
    			}    			
    		}
    	}// 没有其他状态的
    	else
    	{
    		row.getCell("handleType").setValue("不能交接");
    		row.getStyleAttributes().setLocked(true);
//    		logger.warn("SHIT，找到了即不是执行，不是半执行，也不是已收押金首租状态的合同....");
//    		MsgBox.showInfo("系统逻辑错误，已记录日志，请告知管理员！");
//    		this.abort();
    	}
	}
	
	/**
	 * 判断每一行分录房间能进行的房间交接操作
	 */
	protected void roomTable_tableClicked(KDTMouseEvent e) throws Exception
	{
		/*
		this.roomTable.checkParsed();
	    if(roomTable.getRowCount()<1)
	    {
	    	return;
	    }
	    //只有它是点的那个包含选择框的列，才去进行处理
	    if(this.roomTable.getSelectManager().getActiveColumnIndex() != this.roomTable.getColumnIndex("item"))
	    {	
	    	return;
	    }
	    int rowIndex = this.roomTable.getSelectManager().getActiveRowIndex();
	    IRow row = this.roomTable.getRow(rowIndex);
	    TenancyRoomEntryInfo tenancyRoomEntryInfo = (TenancyRoomEntryInfo) row.getUserObject();
	    //单击之后的结果和原有的结果是相反的
	    if(row.getCell("item").getValue() == Boolean.TRUE)
	    {
	    	return;
	    }//只有复选框选中了，才去进行判断
	    else
	    {
	    	
	    }
	    */
	}
	
	public void onShow() throws Exception
	{
		super.onShow();
	}
	/**
	 * 查找该合同是否存在审批状态的退租单
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean getQuitTenancyInfoByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return false;
		boolean debug = false;
	
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",tenancyBillInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		
		try
		{
			if(QuitTenancyFactory.getRemoteInstance().exists(filter))
				debug = true;
			else
				debug = false;
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return debug;
	}
	
	/**
	 * 在交接操作中去改变一个合同的状态
	 * 分为将合同置为 部分执行 状态， 执行状态，终止状态。
	 * @param tenancyBillInfo
	 */
	private void setTenancyBillExpiration(TenancyBillInfo tenancyBillInfo)
	{	
		if(tenancyBillInfo == null)
			return;
		
		//如果该合同已经是执行中的状态，则证明我们对当前合同正在操作的是收房的操作，即判断房间分录的收房时间即可。
		if(TenancyBillStateEnum.Executing.equals(tenancyBillInfo.getTenancyState()))
		{
			//完成收房
			if(this.isCompleteDeal("shoufang",tenancyBillInfo))
			{
				//置为到期终止状态
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
				
				SelectorItemCollection selectorItemColl = new SelectorItemCollection();
				selectorItemColl.add("tenancyState");
				
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorItemColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}
		}
		//如果该合同是已交押金首租，或者部分执行状态，则证明当前进行的时候交房的操作。
		else if(/*TenancyBillStateEnum.DepositReved.equals(tenancyBillInfo.getTenancyState())
				||*/ TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState()))
		{
			//完成交房
			if(this.isCompleteDeal("jiaofang",tenancyBillInfo))
			{
				//置为执行中状态
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
				
				SelectorItemCollection selectorItemColl = new SelectorItemCollection();
				selectorItemColl.add("tenancyState");
				
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorItemColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}//没完全交房
			else if(this.isCompleteDeal("banjiaofang",tenancyBillInfo))
			{

				//置为半执行中状态
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
				
				SelectorItemCollection selectorItemColl = new SelectorItemCollection();
				selectorItemColl.add("tenancyState");
				
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorItemColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			
			}
		}
	}
	
	/**
	 * 根据合同去找到它下面的所有房间
	 * @param tenancyBillInfo
	 * @return
	 */
	private TenancyRoomEntryCollection getTenancyRoomEntryCollByTenancyBill(TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyBillInfo == null)
			return null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tenancyBillInfo.getId().toString()));
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = null;
		
		try
		{
			tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return tenancyRoomEntryColl;
	}
	
	/**
	 * 通过传入的时间字段名称去判断该合同下的房间有没有完成交接
	 * @param deanName  ex:  jiaofang   shoufang banjiaofang
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean isCompleteDeal(String dealName,TenancyBillInfo tenancyBillInfo)
	{
		boolean debug = false;
		int temp = 0;
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = this.getTenancyRoomEntryCollByTenancyBill(tenancyBillInfo);
		if(tenancyRoomEntryColl == null)
			return false;
		for(int i = 0;i < tenancyRoomEntryColl.size(); i ++)
		{
			TenancyRoomEntryInfo tenancyRoomEntryInfo = tenancyRoomEntryColl.get(i);
			if("jiaofang".equalsIgnoreCase(dealName) || "banjiaofang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActDeliveryRoomDate() != null)
				{
					temp ++;
				}
			}
			else if("shoufang".equalsIgnoreCase(dealName))
			{
				if(tenancyRoomEntryInfo.getActQuitTenDate() != null)
				{
					temp ++;
				}
			}
		}
		// 是否是没有完全交房
		if ("banjiaofang".equalsIgnoreCase(dealName))
		{
			if(temp == 0)
			{
				debug = false;
			}
			else if(temp<tenancyRoomEntryColl.size()&&temp>0)
			{
				debug =true;
			}

		} else
		{
			if (temp == tenancyRoomEntryColl.size())
			{
				debug = true;
			} else
			{
				debug = false;
			}
		}
		return debug;
	}
	
	/**
	 * 判断该房间是否存在诸如改租，续租，转名等有效状态下的合同
	 * 当前正在执行的合同本身应该排除在外
	 * 有效状态 即：已交押金首租，半执行状态。
	 * @param room
	 * @param tenancyBillInfo
	 * @return
	 */
	private boolean isExistValidTenancyBillExceptSelf(TenancyRoomEntryInfo tenancyRoomEntryInfo,TenancyBillInfo tenancyBillInfo)
	{
		if(tenancyRoomEntryInfo == null || tenancyBillInfo == null)
			return false;
		boolean debug = false;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",tenancyRoomEntryInfo.getRoom().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id",tenancyBillInfo.getId().toString(),CompareType.NOTINCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.DEPOSITREVED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",TenancyBillStateEnum.PartExecuted));
		
		filter.setMaskString("(#0 and #1 and (#2 or #3))");
		
		try
		{
			debug = TenancyRoomEntryFactory.getRemoteInstance().exists(filter);
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		return debug;
	}
	
	/**
	 * 点击交接按钮
	 */
	public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception
	{
		if(this.roomTable.getRowCount() < 1)
		{
			MsgBox.showInfo("没有房间可进行交接操作！");
			this.abort();
		}
		/* 在下面对tenancyRoomEntryColl进行判断
		int temp = 0;
		for(int i = 0; i < this.roomTable.getRowCount(); i ++)
		{
			IRow row = this.roomTable.getRow(i);
			if(row.getCell("item").getValue() == Boolean.FALSE)
			{
				temp ++;
			}
		}
		if(temp == this.roomTable.getRowCount())
		{
			MsgBox.showInfo("请选择要交接的房间！");
			this.abort();
		}
		*/
		
		TenancyRoomEntryCollection tenancyRoomEntryColl = new TenancyRoomEntryCollection();
		for(int i = 0; i < this.roomTable.getRowCount(); i ++)
		{
			IRow row = this.roomTable.getRow(i);
			//被锁住的表示之前已经交接过房间,或者不能进行交接
			if(row.getStyleAttributes().isLocked()){
				continue;
			}
			if(row.getCell("item").getValue() == Boolean.TRUE)
			{
				TenancyRoomEntryInfo tenancyRoomEntryInfo = (TenancyRoomEntryInfo) row.getUserObject();
				tenancyRoomEntryColl.add(tenancyRoomEntryInfo);
			}
		}
		
		if(tenancyRoomEntryColl.isEmpty()){
			MsgBox.showInfo(this, "没有需要交接的房间!");
			return;
		}
		
		//TODO 这里有风险，需要在事物下完成
		//服务端完成了新合同，老合同上面交接时间，房源上的状态的改变
		//TenancyBillFactory.getRemoteInstance().handleTenancyRoom(tenancyRoomEntryColl,tenancyBillInfo);
		//更改合同状态
		this.updateTenancyBillState(tenancyBillInfo);
		MsgBox.showInfo("交接成功！");
		this.disposeUIWindow();
	}
	
	/**
	 * 更改合同的状态，这里更改的不仅是传入的合同的状态，还要判断她的后续合同或者乱七八糟什么的合同一起更改
	 * @param tenancyBillInfo
	 */
	private void updateTenancyBillState(TenancyBillInfo billInfo)
	{
		if(billInfo == null)
			return;
		//需要在数据库中重新取值一次
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billInfo.getId().toString()));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("oldTenancyBill.*");
		view.getSelector().add("*");
		view.setFilter(filter);
		
		TenancyBillInfo tenancyBillInfo = null;
		try
		{
			tenancyBillInfo = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view).get(0);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
		if(tenancyBillInfo == null)
		{
			logger.warn("出错啦...");
			MsgBox.showInfo("系统错误，已记录日志！");
			this.abort();
		}
		//如果是半执行状态 或已交押金首租，则说明当前是交房操作，它有可能有旧合同，也
		if(TenancyBillStateEnum.PartExecuted.equals(tenancyBillInfo.getTenancyState())
				/*|| TenancyBillStateEnum.DepositReved.equals(tenancyBillInfo.getTenancyState())*/)
		{
			//旧合同
			TenancyBillInfo oldTenancyBillInfo = tenancyBillInfo.getOldTenancyBill();
			if(oldTenancyBillInfo == null)
			{
				//完成交房
				if(isCompleteDeal("jiaofang",tenancyBillInfo))
				{
					tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
				}
				else
				{
					//是否是半交房
					if(isCompleteDeal("banjiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
			}//有旧合同的情况下
			else
			{
//				完成交房
				if(isCompleteDeal("jiaofang",tenancyBillInfo))
				{
					tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Executing);
				}
				else
				{
					//是否是半交房
					if(isCompleteDeal("banjiaofang",tenancyBillInfo))
					{
						tenancyBillInfo.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
				//处理旧合同
				if(isCompleteDeal("shoufang",oldTenancyBillInfo))
				{
					oldTenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
				}
				SelectorItemCollection selectorColl = new SelectorItemCollection();
				selectorColl.add("tenancyState");
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(oldTenancyBillInfo,selectorColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}
			
		}
		else if(TenancyBillStateEnum.Executing.equals(tenancyBillInfo.getTenancyState()))
		{
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("oldTenancyBill.id",tenancyBillInfo.getId().toString()));
//			filter1.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.DEPOSITREVED_VALUE));
			filter1.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.PARTEXECUTED_VALUE));
			filter1.setMaskString("#0 and (#1 or #2)");
			
			EntityViewInfo view1 = new EntityViewInfo();
			view1.getSelector().add("*");
			view1.setFilter(filter1);
			
			TenancyBillCollection tenancyBillColl = null;
			try
			{
				tenancyBillColl = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view1);
			} catch (BOSException e)
			{
				super.handUIException(e);
			}
			if(tenancyBillColl.size()>1)
			{
				logger.warn("一个正在执行的合同，找到了多个它的状态是已交押金首租或者半执行状态的合同");
				MsgBox.showInfo("程序逻辑错误，已记录日志！");
				this.abort();
			}
			
			TenancyBillInfo newTenancyBillInfo = tenancyBillColl.get(0);
			//是有可能为空的
			if (newTenancyBillInfo != null)
			{
				// 完成交房
				if (isCompleteDeal("jiaofang", newTenancyBillInfo))
				{
					newTenancyBillInfo
							.setTenancyState(TenancyBillStateEnum.Executing);
				} else
				{
					// 是否是半交房
					if (isCompleteDeal("banjiaofang", newTenancyBillInfo))
					{
						newTenancyBillInfo
								.setTenancyState(TenancyBillStateEnum.PartExecuted);
					}
				}
				
				SelectorItemCollection selectorColl = new SelectorItemCollection();
				selectorColl.add("tenancyState");
				try
				{
					TenancyBillFactory.getRemoteInstance().updatePartial(newTenancyBillInfo,selectorColl);
				} catch (EASBizException e)
				{
					super.handUIException(e);
				} catch (BOSException e)
				{
					super.handUIException(e);
				}
			}
			if(isCompleteDeal("shoufang",tenancyBillInfo))
			{
				tenancyBillInfo.setTenancyState(TenancyBillStateEnum.Expiration);
			}
		}
		SelectorItemCollection selectorColl = new SelectorItemCollection();
		selectorColl.add("tenancyState");
		try
		{
			TenancyBillFactory.getRemoteInstance().updatePartial(tenancyBillInfo,selectorColl);
		} catch (EASBizException e)
		{
			super.handUIException(e);
		} catch (BOSException e)
		{
			super.handUIException(e);
		}
	}
}