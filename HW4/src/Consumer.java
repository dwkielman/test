public class Consumer implements Runnable {
	
	private final static int MAX_SIZE = 1000000;
	private Buffer buffer;
	private int count;
	private double cumulativeTotal;
	
	public Consumer (Buffer b) {
		this.buffer = b;
		this.count = 0;
		this.cumulativeTotal = 0.0;
	}
	
	@Override
	public void run() {
		synchronized (buffer) {
			while (count < MAX_SIZE) {
				
				// Consumes elements from Buffer in the same order as inserted
				// remove from the buffer
				Double bufferElement = buffer.removeFromBuffer();
				
				// Maintains sum of every consumed item
				cumulativeTotal += bufferElement;
				if(((count % 100000) == 0) && (count > 0)){
					System.out.printf("Consumer: Consumed %,d items, Cumulative value of consumed items=%.3f\n", count, cumulativeTotal);
				}
				count++;
			}
			System.out.printf("Consumer: Finished consuming %,d items\n", count);
		}
	}

}
