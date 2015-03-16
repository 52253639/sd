package com.kingdee.eas.fdc.market.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo;

public class XCellsAndSubjectAndAnswer {

	private Map/* <String,ButtonAndText> */detailMap = new HashMap/* <String,ButtonAndText> */();

	public void put(String optionId, JToggleButton buttonComp, JTextComponent textComp) {
		detailMap.put(optionId, new XButtonAndText(optionId, buttonComp, textComp));
	}
	
	public void put(String optionId, XButtonAndText bt) {
		detailMap.put(optionId, bt);
	}

	public XButtonAndText remove(String optionId) {
		return (XButtonAndText) detailMap.remove(optionId);
	}

	/**
	 * 将答案设置到界面上<br>
	 * 循环调用 setPartableAnswer(QuestionPaperAnswerEntryInfo answerEntryInfo) 方法
	 */
	public void setAnswer(QuestionPaperAnswerEntryCollection answerEntryCollection) {
		for (int i = 0; i < answerEntryCollection.size(); i++) {
			setAnswer(answerEntryCollection.get(i));
		}
	}

	/**
	 * 将答案设置到界面<br>
	 * 1、根据 QuestionPaperAnswerEntryInfo.option 找到控件组合 ButtonAndText，
	 * 如果ButtonAndText.buttonComp != NULL,那么就设置该控件为“被选”；<br>
	 * 2、接1。如果 ButtonAndText.textComp != NULL &&
	 * answerEntryInfo.answerEntryInfo.getInputStr() != NULL , 那么就将
	 * answerEntryInfo.answerEntryInfo.getInputStr() 设置到
	 * ButtonAndText.textComp上。<br>
	 */
	public void setAnswer(QuestionPaperAnswerEntryInfo answerEntryInfo) {
		String optionId = answerEntryInfo.getOption();
		XButtonAndText bt = (XButtonAndText) detailMap.get(optionId);
		if (bt != null) {
			if (bt.buttonComp != null) {
				// 将选择控件设置为被选
				bt.buttonComp.setSelected(true);
			}
			if (bt.textComp != null && answerEntryInfo.getInputStr() != null) {
				// 将输入的字符串设置到输入控件上
				bt.textComp.setText(answerEntryInfo.getInputStr());
			}
		}
	}

	public QuestionPaperAnswerEntryCollection getAnswer() {
		QuestionPaperAnswerEntryCollection answerEntryCollection = new QuestionPaperAnswerEntryCollection();
		Iterator btnAndTxtIterator = detailMap.values().iterator();
		while (btnAndTxtIterator.hasNext()) {
			XButtonAndText btnAndTxt = (XButtonAndText) btnAndTxtIterator.next();
			if (haveValueButtonAndText(btnAndTxt)) {
				QuestionPaperAnswerEntryInfo answerEntryInfo = new QuestionPaperAnswerEntryInfo();
				answerEntryInfo.setOption(btnAndTxt.optionId);
				if (btnAndTxt.textComp != null) {
					answerEntryInfo.setInputStr(btnAndTxt.textComp.getText());
				}
				answerEntryCollection.add(answerEntryInfo);
			}

		}
		return answerEntryCollection;
	}

	public static boolean haveValueButtonAndText(XButtonAndText bt) {
		boolean have = false;
		if (bt != null) {
			if (bt.buttonComp != null && bt.buttonComp.isSelected()) {
				have = true;
			} else {
				if (bt.textComp != null && bt.textComp.getText() != null && bt.textComp.getText().length() > 0) {
					have = true;
				}
			}
		}
		return have;
	}
	

}
