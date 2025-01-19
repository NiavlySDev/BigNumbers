package fr.niavlys.dev.bn.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BigNumbers {

	private double entier;
	private String sign;
	private Long number;

	private static HashMap<String, Long> multiple;
	private static List<String> signs;

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	public static void init() {
		multiple = new HashMap<>();
		multiple.put("", 1L);
		multiple.put("K", 1_000L);
		multiple.put("M", 1_000_000L);
		multiple.put("B", 1_000_000_000L);
		multiple.put("T", 1_000_000_000_000L);
		multiple.put("Q", 1_000_000_000_000_000L);

		signs = new ArrayList<>();
		signs.add("");
		signs.add("K");
		signs.add("M");
		signs.add("B");
		signs.add("T");
		signs.add("Q");
	}

	public static Long getMultipleBySign(String mult) {
		return multiple.get(mult);
	}
	public static String getSignByMultiple(Long nb) {
		String signavant = "";
		for(String sign: signs) {
			if(nb >= multiple.get(sign)) {
				signavant = sign;
			}
			else {
				return signavant;
			}
		}
		return null;
	}

	public BigNumbers(double entier, String sign) {
		this.entier = entier;
		this.sign = sign;
	}
	public BigNumbers(int nb) {
		this.sign = getSignByMultiple((long) nb);
		this.entier = nb / (double) multiple.get(this.sign);
		this.number = (long) nb;

	}
	public BigNumbers(long nb) {
		this.sign = getSignByMultiple(nb);
		this.entier = nb / (double) multiple.get(this.sign);
		this.number = (long) nb;
	}

	public double getEntier() {
		return entier;
	}
	public String getSign() {
		return sign;
	}
	public Long getNumber() {
		return number;
	}

	private void recalculate() {
		BigNumbers nb = new BigNumbers(this.number);
		this.number = nb.getNumber();
		this.sign = nb.getSign();
		this.entier = nb.getEntier();
	}
	public void add(Integer nb) {
		add(new BigNumbers(nb));
	}
	public void add(Long nb) {
		add(new BigNumbers(nb));
	}
	public void add(BigNumbers nb) {
		this.number += nb.getNumber();
		recalculate();
	}
	public void remove(Integer nb) {
		remove(new BigNumbers(nb));
	}
	public void remove(Long nb) {
		remove(new BigNumbers(nb));
	}
	public void remove(BigNumbers nb) {
		this.number -= nb.getNumber();
		recalculate();
	}
	public void set(Integer nb) {
		set(new BigNumbers(nb));
	}
	public void set(Long nb) {
		set(new BigNumbers(nb));
	}
	public void set(BigNumbers nb) {
		this.number = nb.getNumber();
		this.entier = nb.getEntier();
		this.sign = nb.getSign();
		recalculate();
	}

	public static List<String> getSigns() {
		return signs;
	}

	public long toEntier() {
		return number;
	}
	@Override
	public String toString() {
		return DECIMAL_FORMAT.format(this.entier) + " " + this.sign;
	}
}