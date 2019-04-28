package ru.ancevt.maed.map;

public class DefaultMapkitItemFiller {
	
	private Mapkit mapkit;
	
	public DefaultMapkitItemFiller(Mapkit mapkit) {
		this.mapkit = mapkit;
	}
	
	public final void fill() {
		add("10010 | 1 | Collision    | area-type = 1");
		add("10010 | 1 | Collision    | area-type = 1");
		add("10020 | 1 | DoorTeleport | area-type = 2");
		add("10030 | 1 | Damaging     | area-type = 3");
		add("10040 | 1 | Checkpoint   | area-type = 4");
		add("10050 | 1 | Hook         | area-type = 5");
		add("10060 | 1 | Trigger      | area-type = 6");
	}
	
	private final void add(String dataLineSource) {
		mapkit.addItem(new MapkitItem(mapkit, DataLine.parse(dataLineSource)));
	}
}
