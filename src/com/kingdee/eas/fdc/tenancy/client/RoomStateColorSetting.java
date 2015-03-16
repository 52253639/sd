package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetInfo;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

public class RoomStateColorSetting implements Serializable
{

	private int roomHeight = 45;

	private int roomWidth = 40;

	private Font font = new Font("宋体", 5, 10);

	private Color frontColor = Color.BLACK;

	private Color initColor = Color.LIGHT_GRAY;

	private Color onShowColor = Color.WHITE;

	private Color prePurColor = Color.YELLOW;

	private Color purColor = Color.ORANGE;

	private Color signColor = Color.RED;

	private Color keepDownColor = Color.GREEN;

	private int attachDisType = 0;

	private String displayField = "number";

	public RoomStateColorSetting()
	{
		// 载入数据库设置
		RoomStateColorSetting disSetting = null;
		try
		{
			RoomDisplaySetCollection priceSetCollection = RoomDisplaySetFactory
					.getRemoteInstance().getRoomDisplaySetCollection();
			if (priceSetCollection.size() > 0)
			{
				byte[] b = priceSetCollection.get(0).getValue();
				ObjectInputStream objectInputStream = new ObjectInputStream(
						new ByteInputStream(b, 0, b.length));
				disSetting = (RoomStateColorSetting) objectInputStream
						.readObject();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (disSetting != null)
		{
			this.roomHeight = disSetting.roomHeight;
			this.roomWidth = disSetting.roomWidth;
			this.font = disSetting.font;
			this.initColor = disSetting.initColor;
			this.frontColor = disSetting.frontColor;
			this.onShowColor = disSetting.onShowColor;
			this.prePurColor = disSetting.prePurColor;
			this.purColor = disSetting.purColor;
			this.signColor = disSetting.signColor;
			this.keepDownColor = disSetting.keepDownColor;
			this.attachDisType = disSetting.attachDisType;
			this.displayField = disSetting.displayField;
		}
	}

	public void save() throws Exception
	{

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				arrayOutputStream);
		objectOutputStream.writeObject(this);
		byte[] b = arrayOutputStream.toByteArray();
		RoomDisplaySetCollection setCol = RoomDisplaySetFactory
				.getRemoteInstance().getRoomDisplaySetCollection();
		if (setCol.size() > 0)
		{
			RoomDisplaySetInfo setInfo = setCol.get(0);
			setInfo.setValue(b);
			RoomDisplaySetFactory.getRemoteInstance().update(
					new ObjectUuidPK(setInfo.getId()), setInfo);
		} else
		{
			RoomDisplaySetInfo setInfo = new RoomDisplaySetInfo();
			setInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			setInfo.setValue(b);
			RoomDisplaySetFactory.getRemoteInstance().addnew(setInfo);
		}
	}

	public int getAttachDisType()
	{
		return attachDisType;
	}

	public void setAttachDisType(int attachDisType)
	{
		this.attachDisType = attachDisType;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Color getFrontColor()
	{
		return frontColor;
	}

	public void setFrontColor(Color frontColor)
	{
		this.frontColor = frontColor;
	}

	public Color getKeepDownColor()
	{
		return keepDownColor;
	}

	public void setKeepDownColor(Color keepDownColor)
	{
		this.keepDownColor = keepDownColor;
	}

	public Color getPrePurColor()
	{
		return prePurColor;
	}

	public void setPrePurColor(Color prePurColor)
	{
		this.prePurColor = prePurColor;
	}

	public Color getPurColor()
	{
		return purColor;
	}

	public void setPurColor(Color purColor)
	{
		this.purColor = purColor;
	}

	public int getRoomHeight()
	{
		return roomHeight;
	}

	public void setRoomHeight(int roomHeight)
	{
		this.roomHeight = roomHeight;
	}

	public int getRoomWidth()
	{
		return roomWidth;
	}

	public void setRoomWidth(int roomWidth)
	{
		this.roomWidth = roomWidth;
	}

	public Color getSignColor()
	{
		return signColor;
	}

	public void setSignColor(Color signColor)
	{
		this.signColor = signColor;
	}

	public Color getOnShowColor()
	{
		return onShowColor;
	}

	public void setOnShowColor(Color onShowColor)
	{
		this.onShowColor = onShowColor;
	}

	public String getDisplayField()
	{
		return displayField;
	}

	public void setDisplayField(String displayField)
	{
		this.displayField = displayField;
	}

	public Color getInitColor()
	{
		return initColor;
	}

	public void setInitColor(Color initColor)
	{
		this.initColor = initColor;
	}

}
