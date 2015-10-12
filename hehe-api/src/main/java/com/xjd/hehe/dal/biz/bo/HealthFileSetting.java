package com.xjd.hehe.dal.biz.bo;

import java.util.List;

public class HealthFileSetting {
	private List<Item> diabetes_type;
	private List<Item> symptom;
	private List<Item> treatment;
	private List<Item> complication;
	private List<Item> complication2;

	public List<Item> getDiabetes_type() {
		return diabetes_type;
	}

	public void setDiabetes_type(List<Item> diabetes_type) {
		this.diabetes_type = diabetes_type;
	}

	public List<Item> getSymptom() {
		return symptom;
	}

	public void setSymptom(List<Item> symptom) {
		this.symptom = symptom;
	}

	public List<Item> getTreatment() {
		return treatment;
	}

	public void setTreatment(List<Item> treatment) {
		this.treatment = treatment;
	}

	public List<Item> getComplication() {
		return complication;
	}

	public void setComplication(List<Item> complication) {
		this.complication = complication;
	}

	public List<Item> getComplication2() {
		return complication2;
	}

	public void setComplication2(List<Item> complication2) {
		this.complication2 = complication2;
	}

	public static class Item {
		String code;
		String content;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}
}
