package ru.ancevt.maed.map;

public class DefaultMapkitItemFiller {
	
	private Mapkit mapkit;
	
	public DefaultMapkitItemFiller(Mapkit mapkit) {
		this.mapkit = mapkit;
	}
	
	public final void fill() {
		add("10010 | 1 | Collision    | area-type = 1");
		add("10020 | 1 | DoorTeleport | area-type = 2");
		add("10021 | 1 | DoorTeleport2| area-type = 7");
		add("10030 | 1 | Damaging     | area-type = 3");
		add("10040 | 1 | Checkpoint   | area-type = 4");
		add("10050 | 1 | Hook         | area-type = 5");
		add("10060 | 1 | Trigger      | area-type = 6");
		add("10070 | 1 | Water        | area-type = 8");
		add("10080 | 1 | Wind         | area-type = 9");
		add("10090 | 6 | p_health     | pickup-type= 1");
	}
	
	private final void add(String dataLineSource) {
		mapkit.addItem(new MapkitItem(mapkit, DataLine.parse(dataLineSource)));
	}
}
