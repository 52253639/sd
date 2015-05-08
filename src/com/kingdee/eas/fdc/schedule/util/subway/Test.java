package com.kingdee.eas.fdc.schedule.util.subway;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

public class Test extends JDialog {

	protected void setUp() {
		setSize(800, 200);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 名称集合
		String[] names = new String[] {
				"节点1111111111111111111111111111111111111111111111111111111",
				"节点22222222222222222222222222222222222222", "节点3", "节点4",
				"节点5", "节点6", "节点7", "节点8", "节点9" };
		// 状态集合，此处使用预设的4种颜色，也可以自己定义
		KDSubwayItemState finish = new KDSubwayItemState("按时完成",
				KDSubwayItemState.DONE);
		KDSubwayItemState doing = new KDSubwayItemState("延时完成",
				KDSubwayItemState.DOING);
		KDSubwayItemState undo = new KDSubwayItemState("延时且未完成",
				KDSubwayItemState.UNDO);
		KDSubwayItemState todo = new KDSubwayItemState("未达到完成日期",
				KDSubwayItemState.TODO);
		KDSubwayItemState[] allStates = new KDSubwayItemState[] { finish,
				doing, undo, todo };
		// 节点状态，对应上面状态集合，可知状态分别为：finish、finish、doing、finish、doing....
		int[] itemStates = new int[] { 0, 0, 1, 0, 1, 1, 2, 3, 3 };
		// 节点详细信息，此处为一个Map数组，其中保存了名称、开始日期等信息，在展示的时候使用TestRenderer解析成html语句
		// 在进度中正式使用的时候，此处可能是里程碑节点的一些关键信息
		Map[] values = new HashMap[9];
		for (int i = 0; i < 9; i++) {
			Map value = new HashMap();
			value.put("name", "aaa" + i);
			value.put("start", new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			value.put("len", "30");
			value.put("admin", "金蝶集团");
			values[i] = value;
		}
		// 构建地铁图
//		KDSubwayMap map = new KDSubwayMap(names, allStates, itemStates, values,
//				100, true);
//		map.setRenderer(new TestRenderer());
//
//		map.setBounds(0, 0, 800, 200);
//		add(map);
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.setUp();
		test.setVisible(true);
	}

}
