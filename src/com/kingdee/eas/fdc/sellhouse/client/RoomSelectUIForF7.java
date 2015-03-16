package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public final class RoomSelectUIForF7 extends RoomSelectUI
{
	public boolean isCanceled = false;
	
	private Boolean isMultiSelect = Boolean.FALSE;
	
	private RoomCollection roomColl = null;

	public RoomSelectUIForF7() throws Exception
	{
		super();
	}
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception
	{
		if (isMultiSelect.booleanValue())
		{
			RoomCollection rooms = new RoomCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++)
			{
				KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
				if (block == null)
				{
					return;
				}
				for (int row = block.getBeginRow(); row <= block.getEndRow(); row++)
				{
					for (int col = block.getBeginCol(); col <= block
							.getEndCol(); col++)
					{
						ICell cell = this.tblMain.getCell(row, col);
						if (cell == null || cell.getUserObject() == null)
						{
							continue;
						} else
						{
							RoomInfo room = (RoomInfo) cell.getUserObject();
							room = SHEHelper.queryRoomInfo(room.getId().toString());
							rooms.add(room);
						}
					}
				}
			}
			if (rooms.size() == 0)
			{
				MsgBox.showInfo("请正确选择房间！");
				return;
			} else
			{
				this.getUIContext().put("rooms", rooms);
				 this.isCanceled = false;
		         this.destroyWindow();
			}
		} else
		{
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			int activeColumnIndex = this.tblMain.getSelectManager().getActiveColumnIndex();
			ICell cell = this.tblMain.getCell(activeRowIndex, activeColumnIndex);
			if (cell == null || cell.getUserObject() == null)
			{
				MsgBox.showInfo("请正确选择房间！");
				return;
			} else
			{
				RoomInfo room = (RoomInfo) cell.getUserObject();
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				this.getUIContext().put("room", room);
				this.isCanceled = false;
				this.destroyWindow();
			}
		}
	}
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception
	{
		isCanceled = true;
		super.btnNo_actionPerformed(e);
	}
	/**
	 * 返回多个房间的值
	 * @return
	 * @author laiquan_luo
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public Object[] getReturnValueArray() throws EASBizException, BOSException, UuidException
	{
		Object obj[] = null;
		if (isMultiSelect.booleanValue())
		{
			RoomCollection rooms = new RoomCollection();
			for (int i = 0; i < this.tblMain.getSelectManager().size(); i++) 
			{
				KDTSelectBlock block = this.tblMain.getSelectManager().get(i);
				if (block == null)
				{
					return null;
				}
				for (int row = block.getBeginRow(); row <= block.getEndRow(); row++)
				{
					for (int col = block.getBeginCol(); col <= block.getEndCol(); col++) {
						ICell cell = this.tblMain.getCell(row, col);
						if (cell == null || cell.getUserObject() == null)
						{
							continue;
						} else 
						{
							RoomInfo room = (RoomInfo) cell.getUserObject();
							room = SHEHelper.queryRoomInfo(room.getId().toString());
							rooms.add(room);
						}
					}
				}
			}
			if (rooms.size() == 0)
			{
				MsgBox.showInfo("请正确选择房间！");
				return null;
			} else
			{
				obj = rooms.toArray();
			}
		} 
		else 
		{
			
		}
		return obj;
	}
	/**
	 * 返回单个房间的值
	 * @return
	 * @author laiquan_luo
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public Object getReturnValue() throws EASBizException, BOSException, UuidException
	{
		Object obj = null;
		if(!this.isMultiSelect.booleanValue())
		{
			int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			int activeColumnIndex = this.tblMain.getSelectManager().getActiveColumnIndex();
			ICell cell = this.tblMain.getCell(activeRowIndex, activeColumnIndex);
			if (cell == null || cell.getUserObject() == null) 
			{
				MsgBox.showInfo("请正确选择房间！");
				return null;
			} else 
			{
				RoomInfo room = (RoomInfo) cell.getUserObject();
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				obj = room;
			}
		}
		else
		{
			
		}
		return obj;
	}
	
	public void onLoad() throws Exception
	{
		super.onLoad();
		
		isMultiSelect = (Boolean) this.getUIContext().get("isMultiSelect");
	}
	/**
	 * 填充房间
	 */
	protected void fillData() throws BOSException
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) 
		{
			return;
		}
		moneySysTypeEnum = (MoneySysTypeEnum) this.getUIContext().get("moneySysTypeEnum");
		if(moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys))
		{
			setting = new RoomDisplaySetting();
		}else if(moneySysTypeEnum.equals(MoneySysTypeEnum.TenancySys))
		{
			setting = new TenancyDisPlaySetting();
		}else{
			setting = new RoomDisplaySetting();
		}
		roomColl = (RoomCollection) this.getUIContext().get("roomColl");
		SellProjectInfo sellProject = (SellProjectInfo)this.getUIContext().get("sellProjectInfo");
		//SHEHelper.fillRoomTableByNode(this.tblMain,node, moneySysTypeEnum, roomColl,setting);
		SHEHelper.fillRoomTableByNode(this.tblMain,node,moneySysTypeEnum,roomColl,sellProject,setting,null);
	}
}
