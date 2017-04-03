package s.l.y.dao.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import s.l.y.dao.bind.iBinder;
import s.l.y.dao.bind.impl.DoubleBinder;
import s.l.y.dao.bind.impl.FloatBinder;
import s.l.y.dao.bind.impl.IntegerBinder;
import s.l.y.dao.bind.impl.LongBinder;
import s.l.y.dao.bind.impl.StringBinder;
import s.l.y.dao.util.NameUtils;

public abstract class ContextInitializer {

	public static Map<String, iBinder> initBinderMap() {
		
		HashMap<String, iBinder> map = new HashMap<String, iBinder>();
		
		//String
		map.put(NameUtils.className(String.class), new StringBinder());
		
		//int
		{
			IntegerBinder binder = new IntegerBinder();
			map.put(NameUtils.className(int.class), binder);
			map.put(NameUtils.className(Integer.class), binder);
		}
		
		//long
		{
			LongBinder binder = new LongBinder();
			map.put(NameUtils.className(long.class), binder);
			map.put(NameUtils.className(Long.class), binder);
		}
		
		//float
		{
			FloatBinder binder = new FloatBinder();
			map.put(NameUtils.className(float.class), binder);
			map.put(NameUtils.className(Float.class), binder);
		}
		
		//double
		{
			DoubleBinder binder = new DoubleBinder();
			map.put(NameUtils.className(double.class), binder);
			map.put(NameUtils.className(Double.class), binder);
		}
		
		
		return Collections.unmodifiableMap(map);
	}
}
