package shared.utils;

public class IDkeyGenerator {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(idToString("st-", 5));
//		System.out.println(idToString("st-", 15));
//		System.out.println(idToString("st-", 115));
//		System.out.println(idToString("st-", 1115));
//		System.out.println(idToString("st-", 11151));
//  String s=idToString("st-", 5);
//		System.out.println(stringToID(s));
//		System.out.println(stringToID(idToString("st-", 15)));
//		System.out.println(stringToID(idToString("st-", 115)));
//		System.out.println(stringToID(idToString("st-", 1115)));
//		System.out.println(stringToID(idToString("st-", 11115)));
//
//		
//
//	}

	public static String idToString(String prefix, int id) {
		String StringKey = null;

		if (id > 0 && id <= 9) {
			StringKey = prefix + "0000" + id;
		} else if (id > 9 && id <= 99) {
			StringKey = prefix + "000" + id;
		} else if (id > 99 && id <= 999) {
			StringKey = prefix + "00" + id;
		} else if (id > 999 && id <= 9999) {
			StringKey = prefix + "0" + id;
		} else if (id > 9999 && id <= 99999) {
			StringKey = prefix + id;
		}
		return StringKey;
	}

	public static int stringToID(String idString) {
		int id = Integer.parseInt(idString.substring(3));
		return id;
	}

}
