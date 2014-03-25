package com.levelup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class FilterCollectionJava8 {

	// A couple of definitions
	// Predicate -> function takes in a value then returns true or false

	// Stream -> formal - A stream is a sequence of data items that are produced
	// one at a time. Classes to support functional-style operations, it isn’t
	// meant to replace
	// collection, just make it easier to interact with them

	enum Region {
		NORTH, SOUTH, EAST, WEST;
	}

	class BBTeam {

		int pointScored;
		String teamName;
		Region region;

		public BBTeam(int pointScored, String teamName, Region region) {
			super();
			this.pointScored = pointScored;
			this.teamName = teamName;
			this.region = region;
		}

		@Override
		public String toString() {
			return "BBTeam [pointScored=" + pointScored + ", teamName="
					+ teamName + ", region=" + region + "]";
		}
	}

	List<BBTeam> teams;

	@Before
	public void setUp() {

		teams = new ArrayList<>();

		teams.add(new BBTeam(55, "Wisconsin", Region.WEST));
		teams.add(new BBTeam(65, "Wisconsin", Region.WEST));
		teams.add(new BBTeam(67, "San Diego", Region.WEST));
		teams.add(new BBTeam(43, "Iowa State", Region.EAST));
		teams.add(new BBTeam(43, "Iowa State", Region.EAST));
		teams.add(new BBTeam(43, "Iowa State", Region.EAST));
		teams.add(new BBTeam(75, "Florida", Region.SOUTH));
		teams.add(new BBTeam(64, "Florida", Region.SOUTH));
		teams.add(new BBTeam(98, "Florida", Region.SOUTH));
		teams.add(null);
		teams.add(new BBTeam(98, null, Region.SOUTH));
	}

	// Create a predicate
	Predicate<BBTeam> westRegion = new Predicate<BBTeam>() {
		@Override
		public boolean test(BBTeam t) {
			return t.region == Region.WEST;
		}
	};

	Predicate<BBTeam> eastRegion = (BBTeam p) -> p.region == Region.EAST;

	@Test
	public void filter_by_region() {

		teams.stream().filter(westRegion).forEach(p -> System.out.println(p));

	}

	@Test
	public void filter_by_score() {

		teams.stream().filter(p -> p.pointScored >= 60)
				.forEach(p -> System.out.println(p));
	}

	@Test
	public void filter_by_team_to_collection() {

		Predicate<BBTeam> nonNullPredicate = Objects::nonNull;
		Predicate<BBTeam> nameNotNull = p -> p.teamName != null;
		Predicate<BBTeam> teamWIPredicate = p -> p.teamName.equals("Wisconsin");

		Predicate<BBTeam> fullPredicate = nonNullPredicate.and(nameNotNull)
				.and(teamWIPredicate);

		List<BBTeam> teams2 = teams.stream().filter(fullPredicate)
				.collect(Collectors.toList());

		System.out.println(teams2);
	}

	@Test
	public void check_predicate_on_object() {

		BBTeam team = new BBTeam(100, "Wisconsin", Region.WEST);

		boolean isWestRegionTeam = westRegion.test(team);

		System.out.println(isWestRegionTeam);
	}

}
