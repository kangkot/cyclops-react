package com.aol.cyclops.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

//#http://java.dzone.com/articles/whats-wrong-java-8-currying-vs
public class Uncurry extends UncurryConsumer{

	
	public static <T1,R> Function<T1,R> uncurry(Function<T1,Supplier<R>> func){
		return  (t1) ->func.apply(t1).get();
	}
	public static <T1,T2,R> BiFunction<T1,T2,R> uncurry2(Function<T2,Function<T1,R>> biFunc){
		return  (t2 , t1) -> biFunc.apply(t1).apply(t2);
	}
	
	public static <T1,T2,T3,R> TriFunction<T1,T2,T3,R>   uncurry3(Function<T1,Function<T2,Function<T3,R>>> triFunc){
		return   (t1, t2, t3) -> triFunc.apply(t1).apply(t2).apply(t3);
	}
	public static <T1,T2,T3,T4,R> QuadFunction<T1,T2,T3,T4,R> uncurry4(Function<T1,Function<T2,Function<T3,Function<T4,R>>>> quadFunc){
		return   (t1, t2, t3, t4) -> quadFunc.apply(t1).apply(t2).apply(t3).apply(t4);
	}
	public static <T1,T2,T3,T4,T5,R>  QuintFunction<T1,T2,T3,T4,T5,R> uncurry5(Function<T1,Function<T2,Function<T3,Function<T4,Function<T5,R>>>>>  pentFunc){
		return   (t1, t2, t3, t4, t5) -> pentFunc.apply(t1).apply(t2).apply(t3).apply(t4).apply(t5);
	}
	public static <T1,T2,T3,T4,T5,T6,R> HexFunction<T1,T2,T3,T4,T5,T6,R>  uncurry6(Function<T1,Function<T2,Function<T3,Function<T4,Function<T5,Function<T6,R>>>>>> hexFunc){
		return   (t1, t2 , t3, t4, t5 , t6) -> hexFunc.apply(t1).apply(t2).apply(t3).apply(t4).apply(t5).apply(t6);
	}
	public static <T1,T2,T3,T4,T5,T6,T7,R> HeptFunction<T1,T2,T3,T4,T5,T6,T7,R>  uncurry7(Function<T1,Function<T2,Function<T3,Function<T4,Function<T5,Function<T6,Function<T7,R>>>>>>> heptFunc){
		return   (t1,t2,t3, t4,t5, t6, t7) -> heptFunc.apply(t1).apply(t2).apply(t3).apply(t4).apply(t5).apply(t6).apply(t7);
	}
	public static <T1,T2,T3,T4,T5,T6,T7,T8,R> OctFunction<T1,T2,T3,T4,T5,T6,T7,T8,R>  uncurry8(Function<T1,Function<T2,Function<T3,Function<T4,Function<T5,Function<T6,Function<T7,Function<T8,R>>>>>>>> octFunc){
		return   (t1,t2,t3, t4,t5, t6, t7,t8) -> octFunc.apply(t1).apply(t2).apply(t3).apply(t4).apply(t5).apply(t6).apply(t7).apply(t8);
	}
	
}
