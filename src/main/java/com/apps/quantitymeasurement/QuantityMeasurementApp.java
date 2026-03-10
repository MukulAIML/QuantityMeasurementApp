package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> boolean demonstrateEquality(
            Quantity<U> q1, Quantity<U> q2) {
        boolean result = q1.equals(q2);
        System.out.println(q1 + " == " + q2 + " ? " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> quantity, U targetUnit) {
        Quantity<U> result = quantity.convertTo(targetUnit);
        System.out.println(quantity + " -> " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2) {
        Quantity<U> result = q1.add(q2);
        System.out.println("add(" + q1 + ", " + q2 + ") = " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        Quantity<U> result = q1.add(q2, targetUnit);
        System.out.println("add(" + q1 + ", " + q2 + ", " + targetUnit + ") = " + result);
        return result;
    }
    
    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(
            Quantity<U> q1, Quantity<U> q2) {
        Quantity<U> result = q1.subtract(q2);
        System.out.println("subtract(" + q1 + ", " + q2 + ") = " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(
            Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        Quantity<U> result = q1.subtract(q2, targetUnit);
        System.out.println("subtract(" + q1 + ", " + q2 + ", " + targetUnit + ") = " + result);
        return result;
    }

    public static <U extends IMeasurable> double demonstrateDivision(
            Quantity<U> q1, Quantity<U> q2) {
        double result = q1.divide(q2);
        System.out.println("divide(" + q1 + ", " + q2 + ") = " + result);
        return result;
    }
    

    public static void main(String[] args) {

        System.out.println("Length Operations");
        demonstrateEquality(
            new Quantity<>(1.0, LengthUnit.FEET),
            new Quantity<>(12.0, LengthUnit.INCHES)
        );
        demonstrateEquality(
            new Quantity<>(1.0, LengthUnit.YARDS),
            new Quantity<>(36.0, LengthUnit.INCHES)
        );
        demonstrateConversion(
            new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES
        );
        demonstrateConversion(
            new Quantity<>(30.48, LengthUnit.CENTIMETERS), LengthUnit.FEET
        );
        demonstrateAddition(
            new Quantity<>(1.0, LengthUnit.FEET),
            new Quantity<>(12.0, LengthUnit.INCHES),
            LengthUnit.FEET
        );
        demonstrateAddition(
            new Quantity<>(1.0, LengthUnit.FEET),
            new Quantity<>(12.0, LengthUnit.INCHES),
            LengthUnit.YARDS
        );

        System.out.println("Weight Operations");
        demonstrateEquality(
            new Quantity<>(1.0, WeightUnit.KILOGRAM),
            new Quantity<>(1000.0, WeightUnit.GRAM)
        );
        demonstrateEquality(
            new Quantity<>(500.0, WeightUnit.GRAM),
            new Quantity<>(0.5, WeightUnit.KILOGRAM)
        );
        demonstrateConversion(
            new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM
        );
        demonstrateConversion(
            new Quantity<>(2.0, WeightUnit.POUND), WeightUnit.KILOGRAM
        );
        demonstrateAddition(
            new Quantity<>(1.0, WeightUnit.KILOGRAM),
            new Quantity<>(1000.0, WeightUnit.GRAM),
            WeightUnit.KILOGRAM
        );
        demonstrateAddition(
            new Quantity<>(1.0, WeightUnit.KILOGRAM),
            new Quantity<>(1000.0, WeightUnit.GRAM),
            WeightUnit.GRAM
        );

        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        System.out.println("Quantity(1.0, FEET).equals(Quantity(1.0, KILOGRAM)) ? "
            + length.equals(weight));
        
        System.out.println("Volume Operations");
        demonstrateEquality(
        	    new Quantity<>(1.0, VolumeUnit.LITRE),
        	    new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
        	);
        
        demonstrateConversion(
        	    new Quantity<>(1.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE
        	);
        demonstrateAddition(
        	    new Quantity<>(1.0, VolumeUnit.LITRE),
        	    new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
        	    VolumeUnit.MILLILITRE
        	);
        
        System.out.println("Subtraction Operations");
        
	     // Length subtraction
        demonstrateSubtraction(
            new Quantity<>(10.0, LengthUnit.FEET),
            new Quantity<>(6.0, LengthUnit.INCHES)
        );
        demonstrateSubtraction(
            new Quantity<>(10.0, LengthUnit.FEET),
            new Quantity<>(6.0, LengthUnit.INCHES),
            LengthUnit.INCHES
        );

        // Weight subtraction
        demonstrateSubtraction(
            new Quantity<>(10.0, WeightUnit.KILOGRAM),
            new Quantity<>(5000.0, WeightUnit.GRAM)
        );

        // Volume subtraction
        demonstrateSubtraction(
        	    new Quantity<>(5.0, VolumeUnit.LITRE),
        	    new Quantity<>(2.0, VolumeUnit.LITRE),
        	    VolumeUnit.MILLILITRE
        	);

        System.out.println("Division Operations");

	     // Length division
	     demonstrateDivision(
	         new Quantity<>(10.0, LengthUnit.FEET),
	         new Quantity<>(2.0, LengthUnit.FEET)
	     );
	     demonstrateDivision(
	         new Quantity<>(24.0, LengthUnit.INCHES),
	         new Quantity<>(2.0, LengthUnit.FEET)
	     );
	
	     // Weight division
	     demonstrateDivision(
	         new Quantity<>(10.0, WeightUnit.KILOGRAM),
	         new Quantity<>(5.0, WeightUnit.KILOGRAM)
	     );
	
	     // Volume division
	     demonstrateDivision(
	         new Quantity<>(5.0, VolumeUnit.LITRE),
	         new Quantity<>(10.0, VolumeUnit.LITRE)
	     );
	
    }
}