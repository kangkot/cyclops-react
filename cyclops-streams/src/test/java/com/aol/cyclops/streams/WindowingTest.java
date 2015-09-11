package com.aol.cyclops.streams;

import static com.aol.cyclops.sequence.SequenceM.of;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.cyclops.sequence.streamable.Streamable;

public class WindowingTest {
	SequenceM<Integer> empty;
	SequenceM<Integer> nonEmpty;

	@Before
	public void setup(){
		empty = of();
		nonEmpty = of(1);
	}
	
	@Test
	public void windowWhile(){
		assertThat(SequenceM.of(1,2,3,4,5,6)
				.windowWhile(i->i%3!=0)
				.toList().size(),equalTo(2));
		assertThat(SequenceM.of(1,2,3,4,5,6)
				.windowWhile(i->i%3!=0)
				.toList().get(0).sequenceM().toList(),equalTo(Arrays.asList(1,2,3)));
	}
	@Test
	public void windowUntil(){
		assertThat(SequenceM.of(1,2,3,4,5,6)
				.windowUntil(i->i%3==0)
				.toList().size(),equalTo(2));
		assertThat(SequenceM.of(1,2,3,4,5,6)
				.windowUntil(i->i%3==0)
				.toList().get(0).sequenceM().toList(),equalTo(Arrays.asList(1,2,3)));
	}
	@Test
	public void windowUntilEmpty(){
		assertThat(SequenceM.<Integer>of()
				.windowUntil(i->i%3==0)
				.toList().size(),equalTo(0));
	}
	@Test
	public void windowStatefullyWhile(){
		
		assertThat(SequenceM.of(1,2,3,4,5,6)
				.windowStatefullyWhile((s,i)->s.sequenceM().toList().contains(4) ? true : false)
				.toList().size(),equalTo(5));
		
	}
	@Test
	public void windowStatefullyWhileEmpty(){
		
		assertThat(SequenceM.of()
				.windowStatefullyWhile((s,i)->s.sequenceM().toList().contains(4) ? true : false)
				.toList().size(),equalTo(0));
		
	}
	@Test
	public void sliding() {
		List<List<Integer>> list = SequenceM.of(1, 2, 3, 4, 5, 6).sliding(2).collect(Collectors.toList());

		assertThat(list.get(0), hasItems(1, 2));
		assertThat(list.get(1), hasItems(2, 3));
	}

	@Test
	public void slidingIncrement() {
		List<List<Integer>> list = SequenceM.of(1, 2, 3, 4, 5, 6).sliding(3, 2).collect(Collectors.toList());

		assertThat(list.get(0), hasItems(1, 2, 3));
		assertThat(list.get(1), hasItems(3, 4, 5));
	}

	@Test
	public void grouped() {

		List<List<Integer>> list = SequenceM.of(1, 2, 3, 4, 5, 6).grouped(3).collect(Collectors.toList());

		assertThat(list.get(0), hasItems(1, 2, 3));
		assertThat(list.get(1), hasItems(4, 5, 6));

	}

	@Test
	public void sliding2() {
		

		List<List<Integer>> sliding = SequenceM.of(1, 2, 3, 4, 5).sliding(2).toList();

		assertThat(sliding, contains(asList(1, 2), asList(2, 3), asList(3, 4), asList(4, 5)));
	}

	@Test
	public void slidingOverlap() {
		
		List<List<Integer>> sliding = SequenceM.of(1, 2, 3, 4, 5).sliding(3,2).toList();

		assertThat(sliding, contains(asList(1, 2, 3), asList(3, 4, 5)));
	}

	@Test
	public void slidingEmpty() {
		

		assertThat(SequenceM.of().sliding(1).toList().size(),equalTo(0));
	}

	@Test
	public void slidingWithSmallWindowAtEnd() {
		

		List<List<Integer>> sliding = SequenceM.of(1, 2, 3, 4, 5).sliding(2,2).toList();

		assertThat(sliding, contains(asList(1, 2), asList(3, 4), asList(5)));
	}

	@Test
	public void groupedOnEmpty() throws Exception {
			assertThat( empty.grouped(10).count(),equalTo(0l));
	}

	@Test(expected=IllegalArgumentException.class)
	public void groupedEmpty0() throws Exception {
		empty.grouped(0).toList();
		
	}
	@Test(expected=IllegalArgumentException.class)
	public void grouped0() throws Exception {
		nonEmpty.grouped(0).toList();
		
	}


	@Test
	public void groupedShorter() throws Exception {
		final Streamable<Integer> fixed = Streamable.fromStream(of(5, 7, 9));
		assertThat(fixed.sequenceM().grouped(4).get(0).v1,equalTo(Arrays.asList(5,7,9)));
		assertThat(fixed.sequenceM().grouped(4).count(),equalTo(1l));

		
	}

	@Test
	public void groupedEqualSize() throws Exception {
		final Streamable<Integer> fixed = Streamable.fromStream(of(5, 7, 9));
		assertThat(fixed.sequenceM().grouped(3).elementAt(0).get(),equalTo(Arrays.asList(5,7,9)));
		assertThat(fixed.sequenceM().grouped(3).count(),equalTo(1l));
	}

	@Test
	public void multipleGrouped() throws Exception {
		final Streamable<Integer> fixed = Streamable.fromStream(of(5, 7, 9,10));
		assertThat(fixed.sequenceM().grouped(3).elementAt(0).get(),equalTo(Arrays.asList(5,7,9)));
		assertThat(fixed.sequenceM().grouped(3).count(),equalTo(2l));
		
	}

	
	@Test
	public void return1() throws Exception {
		final Streamable<Integer> fixed = Streamable.fromStream(of(5));
		assertThat(fixed.sequenceM().grouped(3).elementAt(0).get(),equalTo(Arrays.asList(5)));
		assertThat(fixed.sequenceM().grouped(3).count(),equalTo(1l));
	}

	@Test
	public void groupedEmpty() throws Exception {
		
		assertThat(empty.grouped(1).count(),equalTo(0l));
	}

	@Test
	public void groupedInfinite() {
		SequenceM<Integer> infinite = SequenceM.iterate(1, i->i+1);
		
		final SequenceM<List<Integer>> grouped = infinite.grouped(3);
		assertThat(grouped.elementAt(0).get(),equalTo(Arrays.asList(1,2,3)));
	
	}

}


