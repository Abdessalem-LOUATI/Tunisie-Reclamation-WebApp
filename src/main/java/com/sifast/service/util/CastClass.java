package com.sifast.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Cette class est cr��e pour fixer le probl�me du Warning suivant:
 * 		Type safety: The expression of type List needs unchecked conversion to conform to List
 */
public class CastClass {

	private CastClass()
	{
		// Pour ne pas avoir la possibilit� de cr�er une instance de type CastClass
	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> result = new ArrayList<T>(c.size());
		for (Object obj : c)
		{
			result.add(clazz.cast(obj));
		}
		return result;
	}
}
