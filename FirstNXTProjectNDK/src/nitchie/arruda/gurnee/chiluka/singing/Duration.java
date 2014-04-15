package nitchie.arruda.gurnee.chiluka.singing;

public class Duration {
	
	public static final int WHOLE = 240;
	public static final int HALF = 120;
	public static final int QUARTER = 60;
	public static final int EIGHTH = 120;
	public static final int SIXTEENTH = 120;
	public static final int TRIPLET_QUARTER = 40;
	public static final int TRIPLET_EIGHTH = 20;
	public static final int TRIPLET_SIXTEENTH = 10;
	
	public static int generateLength(double noteLength, double BPM, boolean dotted, boolean articulated) {
		int duration = (int) Math.round((noteLength / BPM) * 1000);
		return dotted ? duration + (duration / 2) : duration;
	}
}
