package ru.ancevt.maed.gameobject.action;

public class ActionStatement {
	
	private static final int UNKNOWN = -1;
	
	public static final int 
		RESET 			= 0,
		MOVE  			= 1,
		PAUSE 			= 2,
		SETSPEED 		= 3,
		LOOP 			= 4,
		SETXY 			= 5,
		BEGIN 			= 6,
		DIRECTION 		= 7,
		MOVESTART 		= 8,
		MOVESTOP 		= 9,
		SETCOLLRECT 	= 10,
		ADDHEALTH 		= 11,
		SETDAMPOWER 	= 13,
		SETHEALTH 			= 14,
		SETMAXHEALTH 		= 15,
		EXTRAFRAME 		= 16,
		PLAYSOUND 		= 17,
		SETALPHA 		= 18,
		ROTATE 			= 19,
		SETROTATION 	= 20,
		SETCOLLENABLED 	= 21,
		SETFLOORONLY 	= 22,
		SETPUSHABLE 	= 23,
		SETWEIGHT 		= 24,
		SETGRAVITYENABLED = 25,
		PLAYANIMATION 	= 26,
		SETVISIBLE 		= 27,
		CONTROLLER		= 28,
		SETREACTSONMARKERS = 29,
		SETFACETOFACE   = 30,
		WALKSTART		= 31,
		WALKSTOP		= 32,
		WALKLEFT		= 33,
		WALKRIGHT		= 34,
		
		END = 65536;
		
	
	private String[] values;
	private int key;
	private String command;
	
	
	private static final Object[] data = {
		"MOVE",        MOVE,
		"RESET",       RESET,
		"PAUSE",       PAUSE,
		"SETSPEED",    SETSPEED,
		"LOOP",        LOOP,
		"BEGIN",       BEGIN,
		"END",         END,
		"SETXY",       SETXY,
		"DIRECTION",   DIRECTION,
		"MOVESTART",   MOVESTART,
		"MOVESTOP",    MOVESTOP,
		"SETCOLLRECT", SETCOLLRECT,
		"ADDHEALTH",   ADDHEALTH,
		"SETDAMPOWER", SETDAMPOWER,
		"SETHEALTH",   SETHEALTH,
		"SETMAXHEALTH",SETMAXHEALTH,
		"EXTRAFRAME",  EXTRAFRAME,
		"PLAYSOUND",   PLAYSOUND,
		"SETALPHA",    SETALPHA,
		"ROTATE",      ROTATE,
		"SETROTATION", SETROTATION,
		"SETCOLLENABLED", SETCOLLENABLED,
		"SETFLOORONLY", SETFLOORONLY,
		"SETPUSHABLE",  SETPUSHABLE,
		"SETWEIGHT",    SETWEIGHT,
		"SETGRAVITYENABLED", SETGRAVITYENABLED,
		"PLAYANIMATION", PLAYANIMATION,
		"SETVISIBLE",   SETVISIBLE,
		"CONTROLLER", 	CONTROLLER,
		"SETREACTONMARKERS", SETREACTSONMARKERS,
		"SETFACETOFACE", SETFACETOFACE,
		"WALKSTART",	WALKSTART,
		"WALKSTOP",		WALKSTOP,
		"WALKLEFT",		WALKLEFT,
		"WALKRIGHT",	WALKRIGHT
		
	};

	public ActionStatement(final String statement) {
		parse(statement);
	}
	
	private final void parse(final String statement) {
		final String[] s = statement.split("\\s+", 2);
		command = s[0];
		this.key = parseKey(command);
		
		if(s.length > 1) 
			values = s[1].split("\\s+");
	}
	
	private final int parseKey(final String command) {
		for(int i = 0; i < data.length; i += 2) 
			if(data[i].equals(command)) return (int)data[i+1];
		
		return UNKNOWN;
	}
	
	public final String getCommand() {
		return command;
	}
	
	public final int getKey() {
		return key;
	}
	
	public final int getInt(final int index) {
		return Integer.parseInt(getString(index));
	}
	
	public final float getFloat(final int index) {
		return Float.parseFloat(getString(index));
	}
	
	public final String getString(final int index) {
		return values[index];
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(command);
		sb.append(' ');
		if(values != null)
			for(int i = 0; i < values.length; i ++) {
				sb.append(values[i]);
				if(i != values.length - 1) sb.append(' ');
			}
		return sb.toString();
	}

	
	
}
