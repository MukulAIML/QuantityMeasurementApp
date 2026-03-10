package com.apps.quantitymeasurement;


public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        return length1.equals(length2);
    }

    public static boolean demonstrateLengthComparison(
            double value1, LengthUnit unit1,
            double value2, LengthUnit unit2) {

        Length length1 = new Length(value1, unit1);
        Length length2 = new Length(value2, unit2);

        boolean result = demonstrateLengthEquality(length1, length2);

        System.out.println(
                value1 + " " + unit1 +
                " == " +
                value2 + " " + unit2 +
                " ? " + result
        );

        return result;
    }
    
    public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
    	Length fromLength = new Length(value, fromUnit);
    	Length toLength = fromLength.convertTo(toUnit);
    	System.out.println(fromLength + " -> " + toLength);
    	return toLength;
    }
    
    public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
    	Length convertedLength = length.convertTo(toUnit);
    	System.out.println(length + " -> " + convertedLength);
    	return convertedLength;
    }
    
    public static Length demonstrateLengthAddition(Length length1, Length length2) {
        Length result = length1.add(length2);
        System.out.println("add(" + length1 + ", " + length2 + ") = " + result);
        return result;
    }
    
    public static Length demonstrateLengthAddition(Length length1, Length length2, LengthUnit targetUnit) {
        Length result = length1.add(length2, targetUnit);
        System.out.println("add(" + length1 + ", " + length2 + ", " + targetUnit + ") = " + result);
        return result;
    }

    public static void main(String[] args) {

        // Demonstrate Feet and Inches comparison
        demonstrateLengthComparison(
                1.0, LengthUnit.FEET,
                12.0, LengthUnit.INCHES
        );

        // Demonstrate Yards and Inches comparison
        demonstrateLengthComparison(
                1.0, LengthUnit.YARDS,
                36.0, LengthUnit.INCHES
        );

        // Demonstrate Centimeters and Inches comparison
        demonstrateLengthComparison(
                100.0, LengthUnit.CENTIMETERS,
                39.3701, LengthUnit.INCHES
        );

        // Demonstrate Feet and Yards comparison
        demonstrateLengthComparison(
                3.0, LengthUnit.FEET,
                1.0, LengthUnit.YARDS
        );

        // Demonstrate Centimeters and Feet comparison
        demonstrateLengthComparison(
                30.48, LengthUnit.CENTIMETERS,
                1.0, LengthUnit.FEET
        );
        
        // Demonstrate Conversion from Centimeters to Feet
        demonstrateLengthConversion(
        		30, LengthUnit.CENTIMETERS,
        		LengthUnit.FEET
        );
        
        // Demonstrate Conversion from Yards to Inches
        demonstrateLengthConversion(
        		500, LengthUnit.YARDS,
        		LengthUnit.INCHES
        );
        
        // Demonstrate conversion from Feet to Inches using 2 param method
        Length fromLength = new Length(502, LengthUnit.FEET);
        demonstrateLengthConversion(fromLength, LengthUnit.INCHES);
        
        demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(2.0, LengthUnit.FEET));

	     // Cross-unit 1 foot + 12 inches = 2 feet
	     demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES));
	
	     // Cross-unit 12 inches + 1 foot = 24 inches
	     demonstrateLengthAddition(new Length(12.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.FEET));
	     
	     demonstrateLengthAddition(
	    		    new Length(1.0, LengthUnit.FEET),
	    		    new Length(12.0, LengthUnit.INCHES),
	    		    LengthUnit.FEET
	    		);

		// 1 foot + 12 inches → INCHES
		demonstrateLengthAddition(
		    new Length(1.0, LengthUnit.FEET),
		    new Length(12.0, LengthUnit.INCHES),
		    LengthUnit.INCHES
		);


    }
}
