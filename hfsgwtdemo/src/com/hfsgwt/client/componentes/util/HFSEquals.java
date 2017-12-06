package com.hfsgwt.client.componentes.util;

public final class HFSEquals {

	public static boolean areEqual(boolean aThis, boolean aThat) {
		return aThis == aThat;
	}

	public static boolean areEqual(char aThis, char aThat) {
		return aThis == aThat;
	}

	public static boolean areEqual(long aThis, long aThat) {
		return aThis == aThat;
	}

	public static boolean areEqual(float aThis, float aThat) {
		//return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
		return aThis == aThat;
	}

	public static boolean areEqual(double aThis, double aThat) {
		//return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(aThat);
		return aThis == aThat;
	}

	public static boolean areEqual(Object aThis, Object aThat) {
		return aThis == null ? aThat == null : aThis.equals(aThat);
	}
}
