import java.util.Random;

public class Producer implements Runnable {

	private final static int MAX_SIZE = 1000000;
	private Buffer buffer;
	private int count;
	private double cumulativeTotal;
	
	public Producer (Buffer b) {
		this.buffer = b;
		this.count = 0;
		this.cumulativeTotal = 0.0;
	}
	
	@Override
	public void run() {
		synchronized (buffer) {
			// Required to produce 1,000,000 elements in total
			while (count < MAX_SIZE) {
				
				// generate random number
				Random random = new Random();
				Double bufferElement = random.nextDouble() * 100.0;
				
				// add it to the buffer
				buffer.addToBuffer(bufferElement);
				
				// Maintains sum of every produced item
				cumulativeTotal += bufferElement;
				if((count % 100000 == 0) && (count > 0)){
					System.out.printf("Producer: Generated %,d items, Cumulative value of generated items=%.3f\n", count, cumulativeTotal);
				}
				count++;
			}
			System.out.printf("Producer: Finished generating %,d items\n", count);
		}
	}
}
