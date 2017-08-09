package cn.liqiankun.hytrix.command;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import rx.Observable;
import rx.Observer;

/**
 * Hello world!
 *
 */
public class CommandHelloWorld extends HystrixCommand<String> {
	
	private final String name;

	public CommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup2"));
		//设置执行超时时间
		/*HystrixCommandProperties.Setter()
		   .withExecutionTimeoutInMilliseconds(9999999);*/
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		/*if("World".equals(name)){
			//throw new RuntimeException();
		}*/
		//Thread.sleep(1900);
		System.out.println("Hello "+name+"!");
		return "Hello "+name+"!";
	}

	@Override
	protected String getFallback() {
		System.out.println("getFallback:"+name);
		return super.getFallback();
	}

	public static class UnitTest {

        @Test
        public void testSynchronous() {
        	System.out.println(Thread.currentThread().getName());
            assertEquals("Hello World!", new CommandHelloWorld("World").execute());
            assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
        }

        @Test
        public void testAsynchronous1() throws Exception {
        	System.out.println(Thread.currentThread().getName());
            assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
            assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
        }

        @Test
        public void testAsynchronous2() throws Exception {

            Future<String> fWorld = new CommandHelloWorld("World").queue();
            Future<String> fBob = new CommandHelloWorld("Bob").queue();
            System.out.println("----------");
            Thread.currentThread().join();
            //assertEquals("Hello World!", fWorld.get(999999999,TimeUnit.MINUTES));
            //assertEquals("Hello Bob!", fBob.get(999999999,TimeUnit.MINUTES));
        }

        @Test
        public void testObservable() throws Exception {

            Observable<String> fWorld = new CommandHelloWorld("World").observe();
            Observable<String> fBob = new CommandHelloWorld("Bob").observe();

            // 1:blocking
            /*assertEquals("Hello World!", fWorld.toBlocking().single());
            assertEquals("Hello Bob!", fBob.toBlocking().single());*/
            
            // 2:non-blocking
            /*fWorld.toBlocking().toFuture();
            fBob.toBlocking().toFuture();*/
            //non-blocking > block get result
            /*assertEquals("Hello World!", fWorld.toBlocking().toFuture().get());
            assertEquals("Hello Bob!", fBob.toBlocking().toFuture().get());*/
            System.out.println("-------------");
            // non-blocking 
            // - this is a verbose anonymous inner-class approach and doesn't do assertions
          fWorld.subscribe(new Observer<String>() {

                @Override
                public void onCompleted() {
                    // nothing needed here
                	System.out.println("onCompleted:"+Thread.currentThread().getName());
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(String v) {
                    System.out.println("onNext: " + v);
                }

            });
          /*
            // non-blocking
            // - also verbose anonymous inner-class
            // - ignore errors and onCompleted signal
            fBob.subscribe(new Action1<String>() {

                @Override
                public void call(String v) {
                    System.out.println("onNext: " + v);
                }

            });*/
            Thread.currentThread().join();

            // non-blocking
            // - using closures in Java 8 would look like this:
            
            //            fWorld.subscribe((v) -> {
            //                System.out.println("onNext: " + v);
            //            })
            
            // - or while also including error handling
            
            //            fWorld.subscribe((v) -> {
            //                System.out.println("onNext: " + v);
            //            }, (exception) -> {
            //                exception.printStackTrace();
            //            })
            
            // More information about Observable can be found at https://github.com/Netflix/RxJava/wiki/How-To-Use

        }
    }
}
