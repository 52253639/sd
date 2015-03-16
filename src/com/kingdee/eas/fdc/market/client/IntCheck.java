package com.kingdee.eas.fdc.market.client;



import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class IntCheck {
	public static void IntBigDecimalCheck(KDFormattedTextField txtamount, String string){
		BigDecimal bd=new BigDecimal(0.0);
		if(txtamount!=null&&txtamount.getValue()!=null){
			if(bd.compareTo((BigDecimal) txtamount.getValue())>0){
				txtamount.setValue(null);
	    		MsgBox.showWarning(string+" 不能小于0!");
				SysUtil.abort();
			}
		}
	}
	public static void IntkdtEntrysCheck(KDTable kdtEntrys, String enytryname, String string2){
		BigDecimal bd=new BigDecimal(0.0);
		for(int i=0;i<kdtEntrys.getRowCount();i++)
		{
			if(kdtEntrys.getRow(i).getCell(enytryname)!=null&&kdtEntrys.getRow(i).getCell(enytryname).getValue()!=null){
				if(bd.compareTo((BigDecimal) kdtEntrys.getRow(i).getCell(enytryname).getValue())>0){
					kdtEntrys.getRow(i).getCell(enytryname).setValue(null);
		    		MsgBox.showWarning(string2+" 不能小于0!");
					SysUtil.abort();
				}
			}
		}
	}
	public static void IntOCheck(KDFormattedTextField txtamount, String string) {
		BigDecimal bd=new BigDecimal(0.0);
		if(txtamount!=null&&txtamount.getValue()!=null){
			if(((BigDecimal) txtamount.getValue()).compareTo(bd)<0
					||((BigDecimal) txtamount.getValue()).compareTo(bd)==0){
				txtamount.setValue(null);
	    		MsgBox.showWarning(string+" 不能小于等于0!");
				SysUtil.abort();
			}
			int bdINT=((BigDecimal)(txtamount.getValue())).intValue();
			bd=new BigDecimal(bdINT);
			if(((BigDecimal) txtamount.getValue()).compareTo(bd)!=0){
	    		MsgBox.showWarning(string+" 不能为小数!");
	    		txtamount.setValue(bd);
				SysUtil.abort();
			}
		}
	}
	/**
	 * 不能编辑列*/
	public static void setLockKDT(KDTable kdtEntrys , String string) {
		boolean falg=true;
		if(kdtEntrys.getColumn(string)!=null){
			kdtEntrys.getColumn(string).getStyleAttributes().setLocked(falg);
		}
	}
	/**
	 * 隐藏编辑列*/
	public static void hiKDT(KDTable kdtEntrys , String string,boolean b) {
		boolean falg=false;
		if(kdtEntrys.getColumn(string)!=null){
			kdtEntrys.getColumn(string).getStyleAttributes().setHided(falg);
		}
		
	}
}
