package com.kingdee.eas.fdc.sellhouse.client;

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
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetFactory;
import com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

public class SHEFunctionSetting implements Serializable {
	private boolean isRefToNote = false;

	public SHEFunctionSetting() {
		// 载入数据库设置
		SHEFunctionSetting disSetting = null;
		try {
			SHEFunctionSetCollection funs = SHEFunctionSetFactory
					.getRemoteInstance().getSHEFunctionSetCollection();
			if (funs.size() > 0) {
				byte[] b = funs.get(0).getValues();
				ObjectInputStream objectInputStream = new ObjectInputStream(
						new ByteInputStream(b, 0, b.length));
				disSetting = (SHEFunctionSetting) objectInputStream
						.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (disSetting != null) {
			isRefToNote = disSetting.isRefToNote;
		}
	}

	public void save() throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				arrayOutputStream);
		objectOutputStream.writeObject(this);
		byte[] b = arrayOutputStream.toByteArray();
		SHEFunctionSetCollection setCol = SHEFunctionSetFactory
				.getRemoteInstance().getSHEFunctionSetCollection();
		if (setCol.size() > 0) {
			SHEFunctionSetInfo setInfo = setCol.get(0);
			setInfo.setValues(b);
			SHEFunctionSetFactory.getRemoteInstance().update(
					new ObjectUuidPK(setInfo.getId()), setInfo);
		} else {
			SHEFunctionSetInfo setInfo = new SHEFunctionSetInfo();
			setInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			setInfo.setValues(b);
			SHEFunctionSetFactory.getRemoteInstance().addnew(setInfo);
		}
	}

	public boolean isRefToNote() {
		return isRefToNote;
	}

	public void setRefToNote(boolean isRefToNote) {
		this.isRefToNote = isRefToNote;
	}

}
