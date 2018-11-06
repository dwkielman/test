
public class Buffer {
	
	private double[] buffer;
	private static final int MAX_BUFFER_SIZE = 1000;
	private Producer producer;
	private Consumer consumer;
	private Thread producerThread, consumerThread;
	private int bufferInput, bufferEnd, bufferSize;
	
	public Buffer() {
		buffer = new double[MAX_BUFFER_SIZE];
		bufferSize = 0;
		bufferInput = 0;
		bufferEnd = -1;
	}
	
	// insert elements to the Buffer
	public void addToBuffer(double d) {
		// use synchronized to make sure that Buffer is accessed concurrently without conflict
		synchronized (this) {
			try {
				// if Buffer is full, wait for consumer to consume at least one item
				if (bufferSize ==  MAX_BUFFER_SIZE) {
					wait();
				}
			// find the end of the Buffer to set the next spot that will be open for Producer to add to
			bufferEnd = (bufferEnd + 1) % MAX_BUFFER_SIZE;
			buffer[bufferEnd] = d;
			bufferSize++;
			notify();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// Consumer consumes elements from Buffer in the same order as inserted
	public double removeFromBuffer() {
		double d = -1;
		// use synchronized to make sure that Buffer is accessed concurrently without conflict
		synchronized (this) {
			try {
				// if Buffer is empty, wait for Producer to produce at least one element
				if (bufferSize ==  0) {
					wait();
				}
			// find the correct input for the Buffer to set the next spot that will be filled for Consumer to remove from
			d = buffer[bufferInput];
			bufferInput = (bufferInput + 1) % MAX_BUFFER_SIZE;
			bufferSize--;
			notify();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		return d;
	}
}