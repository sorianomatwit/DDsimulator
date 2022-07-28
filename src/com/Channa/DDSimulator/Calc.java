package com.Channa.DDSimulator;
import java.util.Random;

public class  Calc {
	static Random random = new Random();
	public static int  RandomRange(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
}
