package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	public static class Feet {
		private final double value;
		
		public Feet(double value) {
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Feet other = (Feet) obj;
			return Double.compare(this.value, other.value) == 0;
		}
		
		// if you override equals hashCode should be overriden too
		@Override
		public int hashCode() {
		    return Double.hashCode(value);
		}
	}
	public static void main(String[] args) {
		Feet length1 = new Feet(100);
		Feet length2 = new Feet(200);
		Feet length3 = new Feet(100);
		
		System.out.println("Is length1 equal to length3: " + length1.equals(length3));
		System.out.println("Is length2 equal to length3: " + length2.equals(length3));
	}
}
