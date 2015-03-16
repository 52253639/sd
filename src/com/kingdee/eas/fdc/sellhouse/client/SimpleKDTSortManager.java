package com.kingdee.eas.fdc.sellhouse.client;

import java.text.Collator;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;

/**
 * 业务应用框架的ListUI中对未绑定的列不能排序，直接使用KDTSortManger报错，且较复杂，简单重写KDTSortManager
 * @author zhicheng_jin
 * */
public class SimpleKDTSortManager extends KDTSortManager {
	private Collator comparator = Collator.getInstance(Locale.getDefault());
	private SimpleKDTSortManager(KDTable table) {
		super(table);
		this.setClickCount(1);//单击触发排序
	}
	
	/**
	 * 设置Table可按列排序
	 * 注：该简单排序仅适用于非大数据量的简单Table的排序，
	 * @param table
	 * 			要排序的Table
	 * */
	public static void setTableSortable(KDTable table){
		new SimpleKDTSortManager(table);
	}
	
	public void tableClicked(KDTMouseEvent e) {
		if ((e.getButton() == getButton())
				&& (e.getClickCount() == getClickCount())
				&& (e.getType() == KDTStyleConstants.HEAD_ROW)) {
			setRefresh(false);
			List rows = this.table.getBody().getRows();
			final int col = e.getColIndex();
			//若与上一次点击的列相同，变化排序方式
			if (col == colIndex) {
				if (sortType == ISortManager.SORT_ASCEND) {
					sortType = ISortManager.SORT_DESCEND;
				} else {
					sortType = ISortManager.SORT_ASCEND;
				}
			}

			Collections.sort(rows, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					KDTRow row0 = (KDTRow) arg0;
					KDTRow row1 = (KDTRow) arg1;
					//郁闷，这里居然有空指针，做空处理吧-<房间销售明细表中的“其他应收款”列进行排序时，中断！>
					Object obj0 = row0.getCell(col) == null ? null : row0.getCell(col).getValue();
					Object obj1 = row1.getCell(col) == null ? null : row1.getCell(col).getValue();
					int tmp = doCompare(obj0, obj1);
					if (sortType == ISortManager.SORT_ASCEND) {
						return tmp;
					} else {
						return -tmp;
					}
				}
			});
			changeHeadStatus(colIndex, col, sortType);
			colIndex = col;
			setRefresh(true);
		}
	}
	
	private int doCompare(Object o1, Object o2)
	{
		if (o1 == o2)
		{
			return 0;
		}
		else if (o1 == null && o2 != null)
		{
			return -1;
		}
		else if (o1 != null && o2 == null)
		{
			return 1;
		}
		else
		{
			if (o1 instanceof Number)
			{
				if (o2 instanceof Number)
				{
					double d1 = ((Number) o1).doubleValue();
					double d2 = ((Number) o2).doubleValue();
					if (d1 == d2)
					{
						return 0;
					}
					else if (d1 < d2)
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					return o1.toString().compareTo(o2.toString());
				}
			}
			else if (o1 instanceof String)
			{
				if (o2 instanceof String)
				{
					// 使用Collator实现了中文排序;但可能会有部分汉字不会按拼音排序
					// 这是因为:
					// 在简体中文中我们使用比较多的字符集是 GB2312-80，简称为 GB2312，这个字符集包含了目前最常用的汉字共计 6736 个。其中的汉字分为两大类：
					// 常用汉字,次常用汉字;常用汉字按照汉语拼音来排序，而次常用汉字按照笔画部首进行排序。
					return comparator.compare(o1, o2);
					//return ((String) o1).compareTo((String) o2);
				}
				else
				{
					return ((String) o1).compareTo(o2.toString());
				}
			}
			else if (o1 instanceof Date)
			{
				if (o2 instanceof Date)
				{
					return ((Date) o1).compareTo((Date) o2);
				}
				else
				{
					return o1.toString().compareTo(o2.toString());
				}
			}
			else if (o1 instanceof Calendar)
			{
				if (o2 instanceof Calendar)
				{
					long d1 = ((Calendar) o1).getTimeInMillis();
					long d2 = ((Calendar) o2).getTimeInMillis();
					if (d1 == d2)
					{
						return 0;
					}
					else if (d1 < d2)
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				else
				{
					return o1.toString().compareTo(o2.toString());
				}
			}
			else
			{
				return o1.toString().compareTo(o2.toString());
			}
		}
	}
}
