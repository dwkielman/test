
public class ProducerConsumer {

	public static void main(String[] args) {
		Buffer runningBuffer = new Buffer();
		
		Thread producerThread = new Thread(new Producer(runningBuffer));
		Thread consumerThread = new Thread(new Consumer(runningBuffer));

		producerThread.start();
		consumerThread.start();
		
		// use Thread.join() to specify driver to wait for completion of a Thread
		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("Exiting!");
	}
}
