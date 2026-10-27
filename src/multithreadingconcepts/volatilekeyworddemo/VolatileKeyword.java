package multithreadingconcepts.volatilekeyworddemo;

import multithreadingconcepts.SharedResource;

public class VolatileKeyword {

	public static void main(String[] args) {
		
		SharedResource sharedResource = new SharedResource();
		
		Thread t1 = new Thread(()-> {
			System.out.println("Thread 1 started");
			try {
				System.out.println("Thread 1 logic started");
				Thread.sleep(1000);
				System.out.println("Thread 1 logic completed");
				sharedResource.setFlag(true);
			}catch(Exception e ) {
				e.printStackTrace();
			}
		});
		
		t1.start();
		
		Thread t2 = new Thread(() -> {
			System.out.println("Thread 2 started");
			while(!sharedResource.isFlag()) {
				
			}
			
			System.out.println("Thread 2 logic completed");
		});
		
		t2.start();
	}
}
