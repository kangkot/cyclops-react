package com.aol.cyclops.collections.extensions.standard;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aol.cyclops.streams.StreamUtils;
import com.aol.cyclops.trampoline.Trampoline;

public interface QueueX<T> extends Queue<T>, MutableCollectionX<T> {
	
	static <T> Collector<T,?,Queue<T>> defaultCollector(){
		return Collectors.toCollection(()-> new LinkedList<>());
	}
	static <T> Collector<T,?,QueueX<T>> toQueueX(){
		return Collectors.collectingAndThen(defaultCollector(), (Queue<T> d)->new QueueXImpl<>(d,defaultCollector()));
		
	}
	public static <T> QueueX<T> empty(){
		return fromIterable((Queue<T>) defaultCollector().supplier().get());
	}
	public static <T> QueueX<T> of(T...values){
		Queue<T> res = (Queue<T>) defaultCollector().supplier().get();
		for(T v: values)
			res.add(v);
		return  fromIterable(res);
	}
	public static <T> QueueX<T> singleton(T value){
		return of(value);
	}
	public static <T> QueueX<T> fromIterable(Iterable<T> it){
		return fromIterable(defaultCollector(),it);
	}
	public static <T> QueueX<T> fromIterable(Collector<T,?,Queue<T>>  collector,Iterable<T> it){
		if(it instanceof QueueX)
			return (QueueX)it;
		if(it instanceof Deque)
			return new QueueXImpl<T>( (Queue)it, collector);
		return new QueueXImpl<T>(StreamUtils.stream(it).collect(collector),collector);
	}
	
	public <T> Collector<T,?,Queue<T>> getCollector();
	
	default <T1> QueueX<T1> from(Collection<T1> c){
		return QueueX.<T1>fromIterable(getCollector(),c);
	}
	
	default <X> QueueX<X> fromStream(Stream<X> stream){
		return new QueueXImpl<>(stream.collect(getCollector()),getCollector());
	}
	

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#reverse()
	 */
	@Override
	default QueueX<T> reverse() {
		
		return (QueueX)MutableCollectionX.super.reverse();
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#filter(java.util.function.Predicate)
	 */
	@Override
	default QueueX<T> filter(Predicate<? super T> pred) {
		
		return (QueueX)MutableCollectionX.super.filter(pred);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#map(java.util.function.Function)
	 */
	@Override
	default <R> QueueX<R> map(Function<? super T, ? extends R> mapper) {
		
		return (QueueX)MutableCollectionX.super.map(mapper);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#flatMap(java.util.function.Function)
	 */
	@Override
	default <R> QueueX<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		
		return (QueueX)MutableCollectionX.super.flatMap(mapper);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#limit(long)
	 */
	@Override
	default QueueX<T> limit(long num) {
	
		return (QueueX)MutableCollectionX.super.limit(num);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#skip(long)
	 */
	@Override
	default QueueX<T> skip(long num) {
		
		return (QueueX)MutableCollectionX.super.skip(num);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#takeWhile(java.util.function.Predicate)
	 */
	@Override
	default QueueX<T> takeWhile(Predicate<? super T> p) {
		
		return (QueueX)MutableCollectionX.super.takeWhile(p);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#dropWhile(java.util.function.Predicate)
	 */
	@Override
	default QueueX<T> dropWhile(Predicate<? super T> p) {
		
		return (QueueX)MutableCollectionX.super.dropWhile(p);
	}
	default  QueueX<T> takeRight(int num){
		return (QueueX)MutableCollectionX.super.takeRight(num);
	}
	default  QueueX<T> dropRight(int num){
		return  (QueueX)MutableCollectionX.super.dropRight(num);
	}
	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#takeUntil(java.util.function.Predicate)
	 */
	@Override
	default QueueX<T> takeUntil(Predicate<? super T> p) {
		
		return (QueueX)MutableCollectionX.super.takeUntil(p);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#dropUntil(java.util.function.Predicate)
	 */
	@Override
	default QueueX<T> dropUntil(Predicate<? super T> p) {
		
		return (QueueX)MutableCollectionX.super.dropUntil(p);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#trampoline(java.util.function.Function)
	 */
	@Override
	default <R> QueueX<R> trampoline(Function<? super T, ? extends Trampoline<? extends R>> mapper) {
		
		return (QueueX)MutableCollectionX.super.trampoline(mapper);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#slice(long, long)
	 */
	@Override
	default QueueX<T> slice(long from, long to) {
		
		return (QueueX)MutableCollectionX.super.slice(from, to);
	}

	/* (non-Javadoc)
	 * @see com.aol.cyclops.collections.extensions.standard.MutableCollectionX#sorted(java.util.function.Function)
	 */
	@Override
	default <U extends Comparable<? super U>> QueueX<T> sorted(Function<? super T, ? extends U> function) {
		
		return (QueueX)MutableCollectionX.super.sorted(function);
	}
	default QueueX<T> plus(T e){
		add(e);
		return this;
	}
	
	default QueueX<T> plusAll(Collection<? extends T> list){
		addAll(list);
		return this;
	}
	
	default QueueX<T> minus(Object e){
		remove(e);
		return this;
	}
	
	default QueueX<T> minusAll(Collection<?> list){
		removeAll(list);
		return this;
	}
}
