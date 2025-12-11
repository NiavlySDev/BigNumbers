package fr.niavlys.dev.bn.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Niavlys
 * @version 1.0
 * Class to manage big numbers with suffixes like K, M, B, T, Q, Qa
 * K = Thousand
 * M = Million
 * B = Billion
 * T = Trillion
 * Q = Quadrillion
 * Qa = Quintillion
 * Example: 1.5M = 1,500,000
 */
public class BigNumbers {

	/**
	 * The entier part of the number
	 */
	private double entier;

	/**
	 * The sign of the number (K, M, B, T, Q, Qa)
	 */
	private String sign;

	/**
	 * The number in long format
	 */
	private Long number;

	/**
	 * The map of suffixes and their corresponding values
	 */
	private static HashMap<String, Long> multiple;

	/**
	 * The list of suffixes in order
	 */
	private static List<String> signs;

	/**
	 * Decimal format to display the number
	 */
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

	/**
	 * Initialize the map of suffixes and their corresponding values
	 */
	public static void init() {
		multiple = new HashMap<>();
		multiple.put("", 1L);
		multiple.put("K", 1_000L);
		multiple.put("M", 1_000_000L);
		multiple.put("B", 1_000_000_000L);
		multiple.put("T", 1_000_000_000_000L);
		multiple.put("Q", 1_000_000_000_000_000L);
		multiple.put("Qa", 1_000_000_000_000_000_000L);

		signs = new ArrayList<>();
		signs.add("");
		signs.add("K");
		signs.add("M");
		signs.add("B");
		signs.add("T");
		signs.add("Q");
		signs.add("Qa");
	}

	/**
	 * Get the value corresponding to a suffix
	 * @param mult The suffix
	 * @return The value corresponding to the suffix
	 */
	public static Long getMultipleBySign(String mult) {
		return multiple.get(mult);
	}

	/**
	 * Get the suffix corresponding to a value
	 * @param nb The value
	 * @return The suffix corresponding to the value
	 */
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

	/**
	 * Constructor of BigNumbers (Double version)
	 * @param entier The entier part of the number (Double)
	 * @param sign The suffix of the number (String)
	 */
	public BigNumbers(double entier, String sign) {
		init();
		this.entier = entier;
		this.sign = sign;
		if(sign == null){
			this.sign = "";
		}
		this.number = (long) (entier*getMultipleBySign(this.sign));
	}

	/**
	 * Constructor of BigNumbers (Integer version)
	 * @param nb The number in long format (Integer)
	 */
	public BigNumbers(int nb) {
		init();
		this.sign = getSignByMultiple((long) nb);
		this.entier = nb / (double) multiple.get(this.sign);
		this.number = (long) nb;

	}

	/**
	 * Constructor of BigNumbers (Long version)
	 * @param nb The number in long format (Long)
	 */
	public BigNumbers(long nb) {
		init();
		this.sign = getSignByMultiple(nb);
		this.entier = nb / (double) multiple.get(this.sign);
		this.number = (long) nb;
	}

	/**
	 * Get the entier part of the number
	 */
	public double getEntier() {
		return entier;
	}

	/**
	 * Get the suffix of the number
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Get the number in long format
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * Recalculate the entier part and suffix based on the number
	 */
	private void recalculate() {
		BigNumbers nb = new BigNumbers(this.number);
		this.number = nb.getNumber();
		this.sign = nb.getSign();
		this.entier = nb.getEntier();
	}
	
	/**
	 * Add a number to the current number (Integer version)
	 * @param nb The number to add (Integer)
	 */
	public void add(Integer nb) {
		add(new BigNumbers(nb));
	}

	/**
	 * Add a number to the current number (Long version)
	 * @param nb The number to add (Long)
	 */
	public void add(Long nb) {
		add(new BigNumbers(nb));
	}

	/**
	 * Add a number to the current number (BigNumbers version)
	 * @param nb The number to add (BigNumbers)
	 */
	public void add(BigNumbers nb) {
		this.number += nb.getNumber();
		recalculate();
	}

	/**
	 * Remove a number from the current number (Integer version)
	 * @param nb The number to remove (Integer)
	 */
	public void remove(Integer nb) {
		remove(new BigNumbers(nb));
	}

	/**
	 * Remove a number from the current number (Long version)
	 * @param nb The number to remove (Long)
	 */
	public void remove(Long nb) {
		remove(new BigNumbers(nb));
	}

	/**
	 * Remove a number from the current number (BigNumbers version)
	 * @param nb The number to remove (BigNumbers)
	 */
	public void remove(BigNumbers nb) {
		this.number -= nb.getNumber();
		recalculate();
	}

	/**
	 * Set the current number to a new number (Integer version)
	 * @param nb The new number (Integer)
	 */
	public void set(Integer nb) {
		set(new BigNumbers(nb));
	}

	/**
	 * Set the current number to a new number (Long version)
	 * @param nb The new number (Long)
	 */
	public void set(Long nb) {
		set(new BigNumbers(nb));
	}

	/**
	 * Set the current number to a new number (BigNumbers version)
	 * @param nb The new number (BigNumbers)
	 */
	public void set(BigNumbers nb) {
		this.number = nb.getNumber();
		this.entier = nb.getEntier();
		this.sign = nb.getSign();
		recalculate();
	}

	/**
	 * Add, remove or set a number to the current number based on the action (Integer version)
	 * @param nb The number to add, remove or set (Integer)
	 * @param addor The action to perform ("add", "remove", "set")
	 */
	public void addor(Integer nb, String addor){
		if(addor.equalsIgnoreCase("add")){
			add(nb);
		} else if(addor.equalsIgnoreCase("remove")){
			remove(nb);
		} else if(addor.equalsIgnoreCase("set")){
			set(nb);
		}
	}

	/**
	 * Add, remove or set a number to the current number based on the action (Long version)
	 * @param nb The number to add, remove or set (Long)
	 * @param addor The action to perform ("add", "remove", "set")
	 */
	public void addor(Long nb, String addor){
		if(addor.equalsIgnoreCase("add")){
			add(nb);
		} else if(addor.equalsIgnoreCase("remove")){
			remove(nb);
		} else if(addor.equalsIgnoreCase("set")){
			set(nb);
		}
	}

	/**
	 * Add, remove or set a number to the current number based on the action (BigNumbers version)
	 * @param nb The number to add, remove or set (BigNumbers)
	 * @param addor The action to perform ("add", "remove", "set")
	 */
	public void addor(BigNumbers nb, String addor){
		if(addor.equalsIgnoreCase("add")){
			add(nb);
		} else if(addor.equalsIgnoreCase("remove")){
			remove(nb);
		} else if(addor.equalsIgnoreCase("set")){
			set(nb);
		}
	}

	/**
	 * Multiply the current number by a number (Integer version)
	 * @param nb The number to multiply by (Integer)
	 */
	public void multiply(Integer nb) {
		this.number *= nb;
		recalculate();
	}

	/**
	 * Multiply the current number by a number (Long version)
	 * @param nb The number to multiply by (Long)
	 */
	public void multiply(Long nb) {
		this.number *= nb;
		recalculate();
	}

	/**
	 * Multiply the current number by a number (BigNumbers version)
	 * @param nb The number to multiply by (BigNumbers)
	 */
	public void multiply(BigNumbers nb) {
		this.number *= nb.getNumber();
		recalculate();
	}

	/**
	 * Divide the current number by a number (Integer version)
	 * @param nb The number to divide by (Integer)
	 */
	public void divide(Integer nb) {
		this.number /= nb;
		recalculate();
	}

	/**
	 * Divide the current number by a number (Long version)
	 * @param nb The number to divide by (Long)
	 */
	public void divide(Long nb) {
		this.number /= nb;
		recalculate();
	}

	/**
	 * Divide the current number by a number (BigNumbers version)
	 * @param nb The number to divide by (BigNumbers)
	 */
	public void divide(BigNumbers nb) {
		this.number /= nb.getNumber();
		recalculate();
	}
	
	/**
	 * Get the list of suffixes
	 * @return The list of suffixes
	 */
	public static List<String> getSigns() {
		return signs;
	}

	/**
	 * Get the number in long format
	 * @return The number in long format
	 */
	public long toEntier() {
		return number;
	}

	/**
	 * Get the string representation of the number
	 * @return The string representation of the number
	 */
	@Override
	public String toString() {
		return DECIMAL_FORMAT.format(this.entier) + " " + this.sign;
	}

	/**
	 * Compare the current number with another number to see if it is greater
	 * @param nb The number to compare with (BigNumbers)
	 */
	public boolean superieur(BigNumbers nb) {
		return this.number > nb.getNumber();
	}

	/**
	 * Compare the current number with another number to see if it is less
	 * @param nb The number to compare with (BigNumbers)
	 */
	public boolean inferieur(BigNumbers nb) {
		return !superieur(nb);
	}

	/**
	 * Compare the current number with another number to see if they are equal
	 * @param nb The number to compare with (BigNumbers)
	 */
	public boolean equals(BigNumbers nb) {
		return (!superieur(nb)) && (!inferieur(nb));
	}
}