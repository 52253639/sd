package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;

/***
 @author wei_jiang
 @Email a250@qq.com
 @since 2010-1-7下午06:52:00
 @fileName KDTableTools.java
 ***/
public class KDTableTools {
	/**
	 * 
	 * 描述：返回指定列
	 * 
	 */
	public static IColumn getColumn(KDTable table, String fieldName) {
		IColumn column = null;
		if (table != null && fieldName != null
				&& fieldName.trim().length() != 0) {
			column = table.getColumn(fieldName);
		}
		return column;
	}
	/**
	 * 
	 * 描述：把指定列隐藏
	 */
	public static void setHideFields(KDTable table, String[] fieldNames,
			boolean isHide) {
		if (fieldNames != null) {
			IColumn column = null;
			for (int i = 0, size = fieldNames.length; i < size; i++) {
				column = getColumn(table, fieldNames[i]);
				if (column != null) {
					column.getStyleAttributes().setHided(isHide);
					if (isHide) {
						column.setWidth(-1);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 描述：把指定列锁住.
	 * 
	 * @param table
	 * @param fieldNames
	 * @param isLock
	 *              <p>
	 */
	public static void setLockFields(KDTable table, String[] fieldNames,
			boolean isLock) {
		if (fieldNames != null) {
			IColumn column = null;
			for (int i = 0, size = fieldNames.length; i < size; i++) {
				column = getColumn(table, fieldNames[i]);
				if (column != null) {
					column.getStyleAttributes().setLocked(isLock);
				}
			}
		}
	}
    public static BigDecimal getBigDecimal( Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger ) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
        }
        return ret;
    }

}
