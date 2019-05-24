package ru.ancevt.maed.map;

public class DefaultMapkitItemFiller {
	
	private Mapkit mapkit;
	
	public DefaultMapkitItemFiller(Mapkit mapkit) {
		this.mapkit = mapkit;
	}
	
	public final void fill() {
		add("10010 | 1 | area_collision       | area-type = 1");
		add("10020 | 1 | area_door_teleport   | area-type = 2");
		add("10021 | 1 | area_door_teleport_cp| area-type = 7");
		add("10030 | 1 | area_damaging        | area-type = 3");
		add("10040 | 1 | area_checkpoint      | area-type = 4");
		add("10050 | 1 | area_hook            | area-type = 5");
		add("10060 | 1 | area_trigger         | area-type = 6");
		add("10070 | 1 | area_water           | area-type = 8");
		add("10080 | 1 | area_wind            | area-type = 9");
		add("10090 | 6 | p_health             | pickup-type= 1");
		add("10091 | 6 | p_health_half        | pickup-type= 2");
		add("10092 | 6 | p_key_red            | pickup-type= 3");
		add("10093 | 6 | p_key_green          | pickup-type= 4");
		add("10094 | 6 | p_key_blue           | pickup-type= 5");
		add("10095 | 6 | p_key_yellow         | pickup-type= 6");
		add("10096 | 6 | p_money              | pickup-type= 7");
		add("10097 | 6 | p_extra_money        | pickup-type= 8");
		add("10098 | 6 | p_extra_money2       | pickup-type= 9");
	}
	
	private final void add(String dataLineSource) {
		mapkit.addItem(new MapkitItem(mapkit, DataLine.parse(dataLineSource)));
	}
}
